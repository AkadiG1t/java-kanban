package Tests;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskTestId {
    Task task = new Task("newTask", "newDescription", String.valueOf(Status.NEW), 1);
    Task task2 = new Task("newTask", "newDescription", String.valueOf(Status.NEW), 1);


    @Test
    public void taskEquals() {
        Assertions.assertEquals(task, task2);
    }
}