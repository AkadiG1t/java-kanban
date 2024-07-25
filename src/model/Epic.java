package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Epic extends Task {
    private final List<SubTask> subtasks = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, null);

    }

    @Override
    public Duration getDuration() {
        if (!subtasks.isEmpty()) {
            return subtasks.stream()
                    .map(SubTask::getDuration)
                    .reduce(Duration.ZERO, Duration::plus);
        }
        return Duration.ZERO;
    }

    @Override
    public LocalDateTime getStartTime() {
        Optional<LocalDateTime> optionalLocalDateTimeTask = subtasks.stream()
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo);
        return optionalLocalDateTimeTask.orElse(LocalDateTime.now());
    }


    @Override
    public Type getType() {
        return Type.EPIC;
    }

    @Override
    public Integer getEpicId() {
        return getId();
    }

    @Override
    public void setDuration(Duration duration) {
        super.setDuration(duration);
    }



    public List<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
        subTask.setEpic(getEpicId());
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