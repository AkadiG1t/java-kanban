package Tests;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", String.valueOf(Status.NEW));
        final int taskId = inMemoryTaskManager.createTask(task).getId();

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask);
        assertEquals(task, savedTask);

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }
}