package efs.task.todoapp.handlers;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.handlers.RestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserRestHandler implements RestHandler {
    @Override
    public void handleGet(HttpExchange exchange) {

    }

    @Override
    public void handlePost(HttpExchange exchange) {

    }

    @Override
    public void handlePut(HttpExchange exchange) {
        System.out.println("**** /todo/task");
        try {
            System.out.println("Method: " + exchange.getRequestMethod());
            System.out.println("URI" + exchange.getRequestURI());

            final List<String> delayHeaderValues = exchange.getRequestHeaders().get("delay");
            final long delaySeconds = delayHeaderValues == null || delayHeaderValues.isEmpty() ? 0L : Long.parseLong(delayHeaderValues.get(0));
            System.out.println("Delay: " + delaySeconds);

            InputStream requestBodyStream = exchange.getRequestBody();
            final String requestBody = new String(requestBodyStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request body: " + requestBody);

            TimeUnit.SECONDS.sleep(delaySeconds);

            final String responseText = new String(
                    UUID.randomUUID() + ":" + requestBody);
            final byte[] responseTextBytes = responseText.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, responseTextBytes.length);
            exchange.getResponseBody().write(responseTextBytes);
            exchange.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {

    }
}
