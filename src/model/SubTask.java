package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
    private Epic epic;
    private int epicId;



    public SubTask(String name, String description, Duration duration) {
        super(name, description, duration);
        setStartTime(LocalDateTime.now());
    }

    public SubTask(String name, String description, Duration duration, int epicId) {
        super(name, description, duration);
        setStartTime(LocalDateTime.now());
        this.epicId = epicId;
    }

    public SubTask(String name, String desription, Duration duration, LocalDateTime startTime, int epicId) {
        super(name, desription, duration, startTime);
        this.epicId = epicId;
    }

    @Override
    public Type getType() {
        return Type.SUBTASK;
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
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
        SubTask subTask = (SubTask) o;
        return Objects.equals(epic, subTask.getEpic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epic);
    }
}