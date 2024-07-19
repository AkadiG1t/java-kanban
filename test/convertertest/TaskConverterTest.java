package convertertest;

import converter.TaskConverter;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class TaskConverterTest {
    TaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

    @Test
    void toStringTest() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Task task = taskManager.createTask(new Task("name", "desc", Duration.of(20
                , ChronoUnit.MINUTES)));
        String stringTask = task.getId() + "," + task.getType().toString() + "," + task.getName() + "," +
                task.getStatus().toString() + "," +
                task.getDescription() + "," + task.getEpicId() + "," + task.getStartTime().format(dateTimeFormatter)
                + "," + task.getDuration().toMinutes();
        Assertions.assertEquals(TaskConverter.toString(task), stringTask);
    }

    @Test
    void fromStringTest() {
        Assertions.assertNotNull(TaskConverter.fromString("4,TASK,newTask,NEW,newDesc,null,18.07.2024 14:55:35,20"
                , taskManager));
    }
}
