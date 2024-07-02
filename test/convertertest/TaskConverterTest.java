package convertertest;

import converter.TaskConverter;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

class TaskConverterTest {
    TaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

    @Test
    void toStringTest() {
        Task task = taskManager.createTask(new Task("name", "desc"));
        String stringTask = task.getId() + "," + task.getType().toString() + "," + task.getName() + "," +
                task.getStatus().toString() + "," +
                task.getDescription() + "," + task.getEpicId();
        Assertions.assertEquals(TaskConverter.toString(task), stringTask);
    }

    @Test
    void fromStringTest() {
        Assertions.assertNotNull(TaskConverter.fromString("4,TASK,newTask,NEW,newDesc,null", taskManager));
    }
}
