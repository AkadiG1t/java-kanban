package modeltest;

import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

class SubTaskTestId {
    SubTask subTask = new SubTask("new subTask", "task", Duration.of(20, ChronoUnit.MINUTES));
    SubTask subTask2 = new SubTask("new subTask", "task", Duration.of(20, ChronoUnit.MINUTES));

    @Test
    void epicEquals() {
        subTask.setId(1);
        subTask2.setId(1);
        Assertions.assertEquals(subTask, subTask2);
    }
}