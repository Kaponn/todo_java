package efs.task.todoapp.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.constants.HttpStatus;
import efs.task.todoapp.repository.TaskEntity;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.service.TaskService;
import efs.task.todoapp.service.UserService;
import efs.task.todoapp.util.Base64Utils;
import efs.task.todoapp.util.HttpException;
import efs.task.todoapp.util.HttpHandlerUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskRestHandler implements RestHandler {

    private final TaskService taskService;
    private final UserService userService;

    public TaskRestHandler(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    private String[] getUserFromAuth(String auth) throws HttpException{
        String[] splitAuth = auth.split(":");

        if (splitAuth.length != 2){
            throw new HttpException(HttpStatus.BAD_REQUEST, "Incorrect auth header");
        }

        String[] result = new String[2];

        for (int i = 0; i < 2; i++){
            try {
                if (splitAuth[i] == null || splitAuth[i].isBlank()){
                    throw new Exception();
                }
                result[i] = Base64Utils.decode(splitAuth[i]);
            }
            catch (Exception e) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Error during decoding");
            }
        }
        return result;
    }
    private String[] validateAuth(Headers headers){

        String auth;
        try {
            if (headers == null) {
                throw new Exception();
            }
            List<String> headerList = headers.get("auth");
            if (headerList == null || headerList.size() < 1) {
                throw new Exception();
            }
            auth = headerList.get(0);
            if (null == auth || auth.isBlank()) {
                throw new Exception();
            }
        }
        catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Auth header missing");
        }

        return getUserFromAuth(auth);

    }

    private UserEntity getUserData(String[] userData) throws HttpException {

        String username = userData[0];
        String password = userData[1];
        UserEntity user = userService.getUser(username);
        if (user == null){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (!user.getPassword().equals(password)){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        return user;
    }

    private UUID getTaskId(String address, boolean canBeEmpty) {
        try {
            final String[] splitAddress = address.split("/");
            if (splitAddress.length < 4) {
                if (canBeEmpty) {
                    return null;
                }
                throw new Exception();
            }
            final String taskId = splitAddress[splitAddress.length - 1];
            if (taskId == null || taskId.isBlank()) {
                throw new Exception();
            }

            return UUID.fromString(taskId);

        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Wrong task ID");
        }
    }

    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        String[] userData = validateAuth(exchange.getRequestHeaders());
        final UserEntity user = getUserData(userData);
        byte[] response;
        final UUID taskId = getTaskId(exchange.getRequestURI().getPath(), true);

        TaskEntity task = null;
        if (taskId != null) {
            task = taskService.getTask(taskId);

            if (task == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Task not found");
            }
        }

        if (task != null) {
            task.validateUser(user.getUsername());

            response =  new Gson().toJson(task).getBytes(StandardCharsets.UTF_8);
        } else {
            final List<TaskEntity> taskList = taskService.getTasksOfUser(user.getUsername());
            response = new Gson().toJson(taskList).getBytes(StandardCharsets.UTF_8);
        }

        HttpStatus status = HttpStatus.OK;
        exchange.sendResponseHeaders(status.getStatus(), response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }


    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        String[] userData = validateAuth(exchange.getRequestHeaders());
        UserEntity user = getUserData(userData);
        HttpStatus status;
        byte[] response;

        final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        final Map<String, String> taskData = HttpHandlerUtil.validateRequestBody(body);
        UUID taskId = taskService.addTask(taskData, user.getUsername());


        String responseText = "{ \"id\": " + new Gson().toJson(taskId) + " }";
        System.out.println(responseText);
        response = responseText.getBytes(StandardCharsets.UTF_8);

        status = HttpStatus.CREATED;

        exchange.sendResponseHeaders(status.getStatus(), response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    @Override
    public void handlePut(HttpExchange exchange) throws IOException {
        final UUID taskId = getTaskId(exchange.getRequestURI().getPath(), false);
        HttpStatus status;
        byte[] response;

        final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        final Map<String, String> taskData = HttpHandlerUtil.validateRequestBody(body);

        TaskEntity task = taskService.getTask(taskId);
        if (task == null) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Task not found");
        }

        String[] userData = validateAuth(exchange.getRequestHeaders());
        UserEntity user = getUserData(userData);
        task.validateUser(user.getUsername());

        final TaskEntity updatedTask = new TaskEntity();
        updatedTask.setDescription(taskData.get("description"));
        updatedTask.setDue(taskData.get("due"));

        task = taskService.updateTask(taskId, updatedTask);
        response = new Gson().toJson(task).getBytes(StandardCharsets.UTF_8);
        status = HttpStatus.OK;

        exchange.sendResponseHeaders(status.getStatus(), response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    @Override
    public void handleDelete(HttpExchange exchange) throws IOException {
        final UUID taskId = getTaskId(exchange.getRequestURI().getPath(), false);
        HttpStatus status;

        TaskEntity task = taskService.getTask(taskId);
        if (task == null) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Task not found");
        }

        String[] userData = validateAuth(exchange.getRequestHeaders());
        UserEntity user = getUserData(userData);
        task.validateUser(user.getUsername());

        taskService.deleteTask(taskId);
        status = HttpStatus.OK;

        exchange.sendResponseHeaders(status.getStatus(), 0);
        exchange.close();
    }

    @Override
    public void unhandledMethod(HttpExchange exchange) throws HttpException {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Unrecognized HTTP method");
    }
}
