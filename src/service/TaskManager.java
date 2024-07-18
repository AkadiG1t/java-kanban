package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<model.Task> getTasks();

    void deleteTasks();

    void removeTask(int id);

    model.Task createTask(Task task);

    Task getTask(int id);

    void updateTask(Task task);

    Epic createEpic(Epic epic);

    Epic getEpic(int id);

    ArrayList<Epic> getEpics();

    void deleteEpics();

    void removeEpic(int id);

    void updateEpic(Epic epic);

    List<SubTask> getAllSubTasksForEpic(Epic epic);

    ArrayList<SubTask> getAllSubTasks();

    void deleteAllSubTasks();

    void removeSubTask(int id);

    SubTask getSubtask(int id);

    SubTask createSubTask(SubTask subTask, Epic epic);

    void updateSubTasks(SubTask subTask);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();
}
