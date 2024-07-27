package server.handler;

import com.sun.net.httpserver.HttpExchange;
import exception.NotFoundException;
import model.Task;
import service.TaskManager;

import java.io.IOException;

public class TaskHandler extends BaseTaskHandler {
    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void handlePost(HttpExchange exchange, String[] uriSegments) throws IOException {
        Task task = deserializeRequestBody(exchange, Task.class);

        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getTasks().contains(taskManager.getTask(Integer.parseInt(id)))) {
                    taskManager.updateTask(task);
                    sendText(exchange, gson.toJson("Задача обновлена " + id), 201);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            taskManager.createTask(task);
            sendText(exchange, "Задача создана " + task.getId(), 201);
        }
    }

    @Override
    void handleGet(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getTasks().contains(taskManager.getTask(Integer.parseInt(id)))) {
                    sendText(exchange, gson.toJson(taskManager.getTask(Integer.parseInt(id))), 200);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            sendText(exchange, gson.toJson(taskManager.getTasks()), 200);
        }
    }


    @Override
    void handleDelete(HttpExchange exchange, String[] uriSegments) throws IOException {
        if (uriSegments.length > 2) {
            String id = uriSegments[2];
            if (!id.isEmpty()) {
                if (taskManager.getTasks().contains(taskManager.getTask(Integer.parseInt(id)))) {
                    taskManager.removeTask(Integer.parseInt(id));
                    sendText(exchange, "Задача с id " + id + " была удалена", 200);
                } else {
                    sendText(exchange, "Задача не найдена", 404);
                    throw new NotFoundException("Задача не найдена");
                }
            }
        } else {
            taskManager.deleteTasks();
            sendText(exchange, "Все задачи были удалены", 200);
        }
    }
}