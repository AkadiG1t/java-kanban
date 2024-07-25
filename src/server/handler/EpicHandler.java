package server.handler;

import com.sun.net.httpserver.HttpExchange;
import exception.NotFoundException;
import model.Epic;
import service.TaskManager;

import java.io.IOException;

public class EpicHandler extends BaseTaskHandler {

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void handlePost(HttpExchange exchange, String[] uriSegments) throws IOException {
        Epic epic = deserializeRequestBody(exchange, Epic.class);

        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getTasks().contains(taskManager.getTask(Integer.parseInt(id)))) {
                    taskManager.updateTask(epic);
                    sendText(exchange, gson.toJson("Задача обновлена " + id), 201);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            taskManager.createEpic(epic);
            sendText(exchange, "Задача создана " + epic.getId(), 201);
        }
    }


    @Override
    void handleGet(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getEpics().contains(taskManager.getEpic(Integer.parseInt(id)))) {
                    sendText(exchange, gson.toJson(taskManager.getEpic(Integer.parseInt(id))), 200);
                    sendText(exchange, gson.toJson(taskManager.getEpic(Integer.parseInt(id)).getSubtasks()), 200);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            sendText(exchange, gson.toJson(taskManager.getEpics()), 200);
        }
    }

    @Override
    void handleDelete(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getEpics().contains(taskManager.getEpic(Integer.parseInt(id)))) {
                    taskManager.removeEpic(Integer.parseInt(id));
                    sendText(exchange, "Подзадача с id " + id + " была удалена", 200);
                } else {
                    sendText(exchange, "Подзадача не найдена", 404);
                    throw new NotFoundException("Подзадача не найдена");
                }
            }
        } else {
            taskManager.deleteEpics();
            sendText(exchange, "Все задачи были удалены", 200);
        }
    }
}


