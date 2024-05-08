package tests.modeltests;

import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubTaskTestId {
    SubTask subTask = new SubTask("new subTask", "task");
    SubTask subTask2 = new SubTask("new subTask", "task");

    @Test
    public void epicEquals() {
        subTask.setId(1);
        subTask2.setId(1);
        Assertions.assertEquals(subTask, subTask2);
    }
}