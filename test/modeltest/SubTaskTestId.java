package modeltest;

import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class SubTaskTestId {
    SubTask subTask1 = new SubTask("name", "dest", Duration.ZERO);
    SubTask subTask2 = new SubTask("name", "dest", Duration.ZERO);


    @Test
    void epicEquals() {
        subTask1.setId(1);
        subTask2.setId(1);
        Assertions.assertEquals(subTask1, subTask2);
    }
}