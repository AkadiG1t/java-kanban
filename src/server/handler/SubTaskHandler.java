package server.handler;

import com.sun.net.httpserver.HttpExchange;
import exception.NotFoundException;
import model.SubTask;
import service.TaskManager;

import java.io.IOException;

public class SubTaskHandler extends BaseTaskHandler {
    public SubTaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void handlePost(HttpExchange exchange, String[] uriSegments) throws IOException {
        SubTask subTask = deserializeRequestBody(exchange,SubTask.class);

        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getTasks().contains(taskManager.getTask(Integer.parseInt(id)))) {
                    taskManager.updateTask(subTask);
                    sendText(exchange, gson.toJson("Задача обновлена " + id), 201);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            taskManager.createSubTask(subTask);
            sendText(exchange, "Задача создана " + subTask.getId(), 201);
        }
    }


    @Override
    void handleGet(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getAllSubTasks().contains(taskManager.getSubtask(Integer.parseInt(id)))) {
                    sendText(exchange, gson.toJson(taskManager.getSubtask(Integer.parseInt(id))), 200);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            sendText(exchange, gson.toJson(taskManager.getAllSubTasks()), 200);
        }
    }

    @Override
    void handleDelete(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getAllSubTasks().contains(taskManager.getSubtask(Integer.parseInt(id)))) {
                    taskManager.removeSubTask(Integer.parseInt(id));
                    sendText(exchange, "Подзадача с id " + id + " была удалена", 200);
                } else {
                    sendText(exchange, "Подзадача не найдена", 404);
                    throw new NotFoundException("Подзадача не найдена");
                }
            }
        } else {
            taskManager.deleteAllSubTasks();
            sendText(exchange, "Все задачи были удалены", 200);
        }
    }
}

