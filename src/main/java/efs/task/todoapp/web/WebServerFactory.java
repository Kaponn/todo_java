package efs.task.todoapp.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WebServerFactory {
    public static HttpServer createServer() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

            server.createContext("/todo/user", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    exchange.sendResponseHeaders(201, 0);
                    exchange.close();
                }
            });

            server.createContext("/todo/task", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            return server;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }
}
