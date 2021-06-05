package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GlobalHttpHandler implements HttpHandler {

    private RestHandler handler;

    public GlobalHttpHandler(RestHandler handler) {
        this.handler = handler;
    }

    public void handle(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "PUT": System.out.println("go to put handler");
                handler.handlePut();
                break;
            case "POST": System.out.println("go to post handler");
                handler.handlePost();
                break;
            case "GET": System.out.println("go to get handler");
                handler.handleGet();
                break;
            case "DELETE": System.out.println("go to delete handler");
                handler.handleDelete();
                break;
        }
    }

}

