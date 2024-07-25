package service;

import converter.TaskConverter;
import exception.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file, HistoryManager historyManager) {
        super(historyManager);
        this.file = file;
    }


    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
        }


    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void removeSubTask(int id) {
        super.removeSubTask(id);
        save();
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask;
    }

    @Override
    public void updateSubTasks(SubTask subTask) {
        super.updateSubTasks(subTask);
        save();
    }



    public void save() { try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,
            StandardCharsets.UTF_8))) {

        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            bufferedWriter.append(TaskConverter.toString(entry.getValue()));
            bufferedWriter.newLine();
        }

        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            bufferedWriter.append(TaskConverter.toString(entry.getValue()));
            bufferedWriter.newLine();
        }

        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            bufferedWriter.append(TaskConverter.toString(entry.getValue()));
            bufferedWriter.newLine();
        }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл: " + file);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file, new InMemoryHistoryManager());

        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            for (String line : lines) {
                TaskConverter.fromString(line, taskManager);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла " + file);
        }

        return taskManager;
    }
}