package service;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private  List<? extends Task> history = new LinkedList<>();

    HistoryManager historyManager = Managers.getDefaultHistory();
    private int id = 0;

    private int generateID() {
        return ++id;
    }

    private void checkEpicStatus(Epic epic) {
        for (SubTask check : epic.getSubtasks()) {
            int allSubTasks = 0;
            if(check.getStatus().equals("DOWN")) {
                allSubTasks++;
                if(epic.getSubtasks().size() == allSubTasks) {
                    epic.setStatus(String.valueOf(Status.DONE));
                } else if (allSubTasks == 0) {
                    epic.setStatus(String.valueOf(Status.NEW));
                } else {
                    epic.setStatus(String.valueOf(Status.IN_PROGRESS));
                }
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
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsValue(task)){
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateID());
        epic.setStatus(String.valueOf(Status.NEW));
        epics.put(epic.getId(), epic);

        return epic;
    }

    @Override
    public Epic getEpic (int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
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
    public void deleteEpic(int id) {
        for(Integer search : epics.keySet()) {
            if (search == id) {
                epics.get(id).getSubtasks().clear();
            }
        }
        epics.remove(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        checkEpicStatus(epic);
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksForEpic(Epic epic) {
        return epic.getSubtasks();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for(Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            checkEpicStatus(epic);
        }

    }

    @Override
    public void removeSubTask(int id) {
        if (subTasks.containsKey(id)) {
            SubTask removeSub = subTasks.get(id);
            subTasks.remove(id);
            for (Epic epic : epics.values()) {
                if (epic.getSubtasks().equals(removeSub)) {
                    epic.getSubtasks().remove(removeSub);
                }
            }
            checkEpicStatus(removeSub.getEpic());
        }

    }

    @Override
    public SubTask getSubtask(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        subTask.setId(generateID());
        subTask.setStatus(String.valueOf(Status.NEW));
        subTasks.put(subTask.getId(), subTask);
        subTask.setEpic(epic);
        epic.addSubTask(subTask);
        checkEpicStatus(epic);
        return subTask;
    }

    @Override
    public SubTask updateSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
        checkEpicStatus(subTask.getEpic());
        return subTask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}