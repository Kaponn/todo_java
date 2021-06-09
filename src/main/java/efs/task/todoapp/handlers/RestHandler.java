package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RestHandler {
    void handleGet(HttpExchange exchange) throws IOException;
    void handlePost(HttpExchange exchange) throws IOException;
    void handlePut(HttpExchange exchange) throws IOException;
    void handleDelete(HttpExchange exchange) throws IOException;
    void unhandledMethod(HttpExchange exchange) throws IOException;
}
