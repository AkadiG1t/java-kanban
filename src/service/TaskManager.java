package service;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.HashMap;
import java.util.Set;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int id = 0;

    public TaskManager() {
        this.epics = new HashMap<>();
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

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

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void removeTasks(int id) {
        tasks.remove(id);

    }

    public Task createTask(Task task) {
        task.setId(generateID());
        task.setStatus(String.valueOf(Status.NEW));
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Task updateTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateID());
        epic.setStatus(String.valueOf(Status.NEW));
        epics.put(epic.getId(), epic);

        return epic;
    }

    public Epic getEpic (int id) {
        return epics.get(id);
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void removeEpics(int id) {
        for(Integer search : epics.keySet()) {
            if (search == id) {
                epics.get(id).getSubtasks().clear();
            }
        }
        epics.remove(id);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        checkEpicStatus(epic);
    }

    public Set<SubTask> getAllSubTasksForEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public HashMap<Integer, SubTask> getAllSubTasks() {
        return subTasks;
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        for(Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            checkEpicStatus(epic);
        }

    }
    public void removeSubTask(int id) {
        SubTask removeSub = subTasks.get(id);
        subTasks.remove(id);
        for (Epic epic : epics.values()) {
            if (epic.getSubtasks().equals(removeSub)) {
                epic.getSubtasks().remove(removeSub);
            }
        }
        checkEpicStatus(removeSub.getEpic());
    }

    public SubTask getSubtask(int id) {
        return subTasks.get(id);
    }

    public SubTask createSubTask(SubTask subTask, Epic epic) {
        subTask.setId(generateID());
        subTask.setStatus(String.valueOf(Status.NEW));
        subTasks.put(subTask.getId(), subTask);
        subTask.setEpic(epic);
        epic.addSubTask(subTask);
        return subTask;
    }



    public SubTask updateSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
        checkEpicStatus(subTask.getEpic());
        return subTask;
    }
}
