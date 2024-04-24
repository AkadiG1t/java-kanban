package service;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
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

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList.add((Task) tasks.values());
        return taskArrayList;
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void removeTasks(int id) {
        tasks.remove(id);

    }

    public Task createTask(Task task) {
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Task updateTask(Task task) {
        if (tasks.containsValue(task)){
            tasks.put(task.getId(), task);
        }
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

    public void deleteEpic(int id) {
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

    public ArrayList<SubTask> getAllSubTasksForEpic(Epic epic) {
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

    public SubTask getSubtask(int id) {
        return subTasks.get(id);
    }

    public SubTask createSubTask(SubTask subTask, Epic epic) {
        subTask.setId(generateID());
        subTask.setStatus(String.valueOf(Status.NEW));
        subTasks.put(subTask.getId(), subTask);
        subTask.setEpic(epic);
        epic.addSubTask(subTask);
        checkEpicStatus(epic);
        return subTask;
    }



    public SubTask updateSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
        checkEpicStatus(subTask.getEpic());
        return subTask;
    }
}
