package service;

import exception.NotFoundException;
import exception.ValidateException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    final Map<Integer, Task> tasks = new HashMap<>();
    final Map<Integer, Epic> epics = new HashMap<>();
    final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager;
    private final TreeSet<Task> prioritizeTask = new TreeSet<>
            ((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
    private int id = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int generateID() {
        return ++id;
    }

    private void checkEpicStatus(Epic epic) {
        long allSubTasksDone = epic.getSubtasks().stream()
                .filter(subTask -> subTask.getStatus() == Status.DONE)
                .count();

        long allSubTasksInProgress = epic.getSubtasks().stream()
                .filter(subTask -> subTask.getStatus() == Status.IN_PROGRESS)
                .count();

        if (epic.getSubtasks().size() == allSubTasksDone) {
            epic.setStatus(Status.DONE);
        } else if (allSubTasksDone == 0 && allSubTasksInProgress == 0) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
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
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public Task createTask(Task task) {
        if (task == null) {
            return null;
        }

        task.setId(generateID());
        tasks.put(task.getId(), task);
        addToPrioritized(task);

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
            checkOverlayTime(task);
            addToPrioritized(task);
        } else {
            throw new NotFoundException("Задача не найдена");
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        if (epic == null) {
            return null;
        }

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
        prioritizeTask.removeAll(epics.values());
        prioritizeTask.removeAll(subTasks.values());
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void removeEpic(int id) {
        if (epics.containsKey(id)) {

            prioritizeTask.remove(epics.get(id));
            epics.get(id).getSubtasks().clear();
            epics.remove(id);
            if (historyManager.getHistory().contains(epics.get(id))) {
                historyManager.remove(id);
            }
        } else {
            throw new NotFoundException("Эпик не найден");
        }
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());

        if (saved == null) {
            throw new NotFoundException("Не найден эпик " + epic.getId());
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        saved.getSubtasks().clear();
        epic.getSubtasks().forEach(saved::addSubTask);
        addToPrioritized(saved);
        epics.put(epic.getEpicId(), saved);
        checkEpicStatus(saved);

        return saved;
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
        subTasks.values().forEach(prioritizeTask::remove);
        subTasks.clear();

        epics.values().forEach(epic -> {
            epic.getSubtasks().clear();
            checkEpicStatus(epic);
            addToPrioritized(epic);
        });
    }

    @Override
    public void removeSubTask(int id) {
        if (subTasks.containsKey(id)) {
            SubTask removeSub = subTasks.get(id);
            subTasks.remove(id);
            if (historyManager.getHistory().contains(removeSub)) {
                historyManager.remove(id);
            }
            prioritizeTask.remove(removeSub);

            epics.values().forEach(epic -> epic.removeSubTask(removeSub));
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
        if (subTask == null) {
            return null;
        }
        subTask.setId(generateID());
        subTask.setStatus(Status.NEW);
        subTasks.put(subTask.getId(), subTask);
        subTask.setEpic(epic);
        epic.addSubTask(subTask);
        checkEpicStatus(epic);
        addToPrioritized(subTask);
        addToPrioritized(subTask.getEpic());
        return subTask;
    }

    @Override
    public SubTask updateSubTasks(SubTask subTask) {
        SubTask saved = subTasks.get(subTask.getId());
        if (saved == null) {
            throw new NotFoundException("Подзадача не найдена");
        } else {
            saved.setStatus(subTask.getStatus());
            saved.setStartTime(subTask.getStartTime());
            saved.setEpic(subTask.getEpic());
            saved.setName(subTask.getName());
            saved.setDescription(subTask.getDescription());
            saved.getEpic().getSubtasks().remove(subTask);
            saved.getEpic().getSubtasks().add(saved);
            addToPrioritized(saved);
            subTasks.put(saved.getId(), saved);
            checkEpicStatus(saved.getEpic());

            return saved;
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private boolean checkOverlayTime(Task currentTask) {
        return prioritizeTask.stream()
                .anyMatch(task -> currentTask != null && task != null
                        && currentTask.getStartTime().isBefore(task.getEndTime())
                        && currentTask.getEndTime().isAfter(task.getStartTime()));
    }

    public void addToPrioritized(Task task) {
        if (task.getStartTime() != null && !task.getDuration().isZero() && prioritizeTask.size() > 1){
            if (!checkOverlayTime(task)) {
                prioritizeTask.remove(task);
                prioritizeTask.add(task);
            } else {
                throw new ValidateException("Ошибка валидации: нельзя выполнять более одной задачи единовременно");
            }
        }
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizeTask);
    }
}