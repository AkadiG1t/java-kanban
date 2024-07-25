package server.handler;

import com.sun.net.httpserver.HttpExchange;
import service.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseTaskHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void handlePost(HttpExchange exchange, String[] URISegments) throws IOException {
        sendText(exchange, "Этого метода нет у истории.", 405);
    }

    @Override
    void handleGet(HttpExchange exchange, String[] URISegments) throws IOException {
        if (taskManager.getHistory().isEmpty()) {
            sendText(exchange, gson.toJson(taskManager.getHistory()), 200);
        } else {
            sendText(exchange, "История еще не создана", 404);
        }
    }

    @Override
    void handleDelete(HttpExchange exchange, String[] URISegments) throws IOException {
        sendText(exchange, "Этого метода нет у истории", 405);
    }

}
