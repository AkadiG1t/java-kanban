package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Epic extends Task {
    private final List<SubTask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public Duration getDuration() {
        return subtasks.stream()
                .map(SubTask::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public LocalDateTime getStartTime() {
        Optional<LocalDateTime> optionalLocalDateTimeTask = subtasks.stream()
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo);
        return optionalLocalDateTimeTask.orElse(null);
    }

    @Override
    public LocalDateTime getEndTime() {
        Optional<LocalDateTime> optionalLocalDateTime = subtasks.stream()
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo);
        return optionalLocalDateTime.orElse(null);
    }

    @Override
    public Type getType() {
        return Type.EPIC;
    }

    @Override
    public Integer getEpicId() {
        return getId();
    }



    public List<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks);
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
