package modeltest;

import model.Task;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.Managers;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(Managers.getDefaultHistory());

    @Test
    void addNewTask() {
        Task task = inMemoryTaskManager.createTask(new Task("name", "descr", Duration.ofMinutes(20)));
        final int taskId = task.getId();

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask);
        assertEquals(task, savedTask);

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }
}