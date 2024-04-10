package model;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Epic extends Task {
    private Set<SubTask> subtasks = new LinkedHashSet<>();

    public Epic(String name, String description) {
        super(name, description, null);
    }


    public Set<SubTask> getSubtasks() {
        return (LinkedHashSet<SubTask>) subtasks;
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                '}';
    }
}
