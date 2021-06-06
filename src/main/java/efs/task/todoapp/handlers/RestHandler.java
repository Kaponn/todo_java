package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;

public interface RestHandler {
    void handleGet(HttpExchange exchange);
    void handlePost(HttpExchange exchange);
    void handlePut(HttpExchange exchange);
    void handleDelete(HttpExchange exchange);
}
