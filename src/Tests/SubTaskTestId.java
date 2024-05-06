package Tests;

import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubTaskTestId {
    SubTask subTask = new SubTask("new subTask", "task", String.valueOf(Status.NEW));
    SubTask subTask2 = new SubTask("new subTask", "task", String.valueOf(Status.NEW));

    @Test
    public void epicEquals() {
        subTask.setId(1);
        subTask2.setId(1);
        Assertions.assertEquals(subTask, subTask2);
    }
}