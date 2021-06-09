package efs.task.todoapp.web;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.handlers.*;
import efs.task.todoapp.repository.TaskRepository;
import efs.task.todoapp.repository.UserRepository;
import efs.task.todoapp.service.TaskService;
import efs.task.todoapp.service.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerFactory {

    public static HttpServer createServer() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 2137), 0);

            UserService userService = new UserService(new UserRepository());
            TaskService taskService = new TaskService(new TaskRepository());

            server.createContext("/todo/task", new GlobalHttpHandler(new TaskRestHandler(taskService, userService)));

            server.createContext("/todo/user", new GlobalHttpHandler(new UserRestHandler(userService)));

            return server;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }
}
