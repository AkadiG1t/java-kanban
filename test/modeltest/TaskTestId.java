package modeltest;

import model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTestId {
    Task task = new Task("newTask", "newDescription");

    Task task2 = new Task("newTask", "newDescription");


    @Test
    void taskEquals() {
        task.setId(1);
        task2.setId(1);
        assertEquals(task, task2);
    }
}