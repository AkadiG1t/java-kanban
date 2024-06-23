package test.modeltests;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskTestId {
    Task task = new Task("newTask", "newDescription");

    Task task2 = new Task("newTask", "newDescription");


    @Test
    public void taskEquals() {
        task.setId(1);
        task2.setId(1);
        Assertions.assertEquals(task, task2);
    }
}