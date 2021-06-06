package efs.task.todoapp.web;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerFactory {
    public static HttpServer createServer() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

            server.createContext("/todo/task", new GlobalHttpHandler(new TaskRestHandler()));

            server.createContext("/todo/user", new GlobalHttpHandler(new UserRestHandler()));

            return server;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }
}
