package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GlobalHttpHandler implements HttpHandler {

    private final RestHandler handler;

    public GlobalHttpHandler(RestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            switch (exchange.getRequestMethod()) {
                case "PUT":
                    handler.handlePut(exchange);
                    break;
                case "POST":
                    handler.handlePost(exchange);
                    break;
                case "GET":
                    handler.handleGet(exchange);
                    break;
                case "DELETE":
                    handler.handleDelete(exchange);
                    break;
                default:
                    handler.unhandledMethod(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

