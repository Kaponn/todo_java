package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.util.HttpException;

import java.io.IOException;

public class GlobalHttpHandler implements HttpHandler {

    private final RestHandler handler;

    public GlobalHttpHandler(RestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
        } catch (HttpException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(e.getHttpStatus().getStatus(), 0);
            exchange.close();
        }
        exchange.close();
    }

}

