package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.constants.HttpStatus;
import efs.task.todoapp.service.UserService;
import efs.task.todoapp.util.HttpException;
import efs.task.todoapp.util.HttpHandlerUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class UserRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(UserRestHandler.class.getName());

    private final UserService userService;

    public UserRestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed for user");
    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        LOGGER.info("HTTP User Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("request body: " + body);
            Map<String, String> userData = HttpHandlerUtil.validateRequestBody(body);
            userService.addUser(userData);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), 0);
            exchange.close();
        } catch (HttpException httpException) {
            exchange.sendResponseHeaders(httpException.getHttpStatus().getStatus(), 0);
            exchange.close();
        }

    }

    @Override
    public void handlePut(HttpExchange exchange) {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed for user");
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed for user");
    }

    @Override
    public void unhandledMethod(HttpExchange exchange) throws HttpException {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Unrecognized HTTP method");
    }
}
