package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Epic extends Task {
    private final List<SubTask> subtasks = new ArrayList<>();

    @Override
    public Type getType() {
        return Type.EPIC;
    }

    @Override
    public Integer getEpicId() {
        return getId();
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }
}
