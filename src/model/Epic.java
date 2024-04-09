package model;
import java.util.LinkedHashSet;
import java.util.Set;

public class Epic extends Task {
    private Set<SubTask> subtasks;

    public Epic(String name, String description, SubTask subTask) {
        super(name, description);
    }


    public LinkedHashSet<SubTask> getSubtasks() {
        return (LinkedHashSet<SubTask>) subtasks;
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask);
    }
}
