package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    final Map<Integer, Task> tasks = new HashMap<>();
    final Map<Integer, Epic> epics = new HashMap<>();
    final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager;
    private int id = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int generateID() {
        return ++id;
    }

    private void checkEpicStatus(Epic epic) {
        int allSubTasks = 0;
        for (SubTask check : epic.getSubtasks()) {
            if (check.getStatus() == Status.DONE) {
                allSubTasks++;
            }

            if (epic.getSubtasks().size() == allSubTasks) {
                epic.setStatus(Status.DONE);
            } else if (allSubTasks == 0) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }


    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void removeTasks(int id) {
        tasks.remove(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task getTask(int id) {

        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            throw new NotFoundException("Задача не найдена");
        }
    }

    @Override
    public Task updateTask(Task task) {

        if (tasks.containsValue(task)) {
            tasks.put(task.getId(), task);
        } else {
            throw new NotFoundException("Задача не найдена");
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateID());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);

        return epic;
    }

    @Override
    public Epic getEpic(int id) {

        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else {
            throw new NotFoundException("Такой эпик не найден");
        }

    }


    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            for (Integer search : epics.keySet()) {
                if (search == id) {
                    epics.get(id).getSubtasks().clear();
                }
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            throw new NotFoundException("Эпик не найден");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());

        if (saved == null) {
            throw new NotFoundException("Не найден эпик " + epic.getId());
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        checkEpicStatus(epic);
    }

    @Override
    public List<SubTask> getAllSubTasksForEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            checkEpicStatus(epic);
        }

    }

    @Override
    public void removeSubTask(int id) {

        if (subTasks.containsKey(id)) {
            SubTask removeSub = subTasks.get(id);
            subTasks.remove(id);
            historyManager.remove(id);

            for (Epic epic : epics.values()) {
                epic.removeSubTask(removeSub);
            }
            checkEpicStatus(removeSub.getEpic());
        } else {
            throw new NotFoundException("Подзадача не найдена " + id);
        }

    }

    @Override
    public SubTask getSubtask(int id) {
        if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        } else {
            throw new NotFoundException("Подзадача не найдена");
        }
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        subTask.setId(generateID());
        subTask.setStatus(Status.NEW);
        subTasks.put(subTask.getId(), subTask);
        subTask.setEpic(epic);
        epic.addSubTask(subTask);
        checkEpicStatus(epic);
        return subTask;
    }

    @Override
    public SubTask updateSubTasks(SubTask subTask) {
        SubTask saved = subTasks.get(subTask.getId());
        if (saved == null) {
            throw new NotFoundException("Подзадача не найдена");
        } else {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
        checkEpicStatus(subTask.getEpic());
        return subTask;
        }
    }



    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}