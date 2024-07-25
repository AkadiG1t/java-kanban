package server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exception.ErrorMethod;
import exception.ProcessingExcetpion;
import server.register.SerializerDeserializerReg;
import service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

abstract class BaseTaskHandler implements HttpHandler {
    Gson gson = SerializerDeserializerReg.createCustomGson();
    TaskManager taskManager;

    public BaseTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void sendText(HttpExchange exchange, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(code, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    public <T> T deserializeRequestBody(HttpExchange exchange, Class<T> tClass) throws IOException {
        return gson.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8), tClass);
    }

    abstract void handlePost(HttpExchange exchange, String[] URISegments) throws IOException;

    abstract void handleGet(HttpExchange exchange, String[] URISegments) throws IOException;

    abstract void handleDelete(HttpExchange exchange, String[] URISegments) throws IOException;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = String.valueOf(exchange.getRequestMethod());
            String path = exchange.getRequestURI().getPath();
            String[] URISegments = path.split("/");

            switch (method) {
                case "POST" -> handlePost(exchange, URISegments);
                case "GET" -> handleGet(exchange, URISegments);
                case "DELETE" -> handleDelete(exchange, URISegments);
                default -> {
                    ErrorMethod errorMethod = new ErrorMethod("Выбран неверный метод запроса");
                    sendText(exchange, gson.toJson(errorMethod), 405);
                }
            }
        } catch (Exception e) {
            try {
                ProcessingExcetpion processingExcetpion = new ProcessingExcetpion("Ошибка обработки запроса");
                sendText(exchange, gson.toJson(processingExcetpion), 500);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
