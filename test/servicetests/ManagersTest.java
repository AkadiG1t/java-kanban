package test.servicetests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.Managers;

class ManagersTest {

    @Test
    void getDefault() {
        Assertions.assertNotNull(Managers.getDefault());
    }


    @Test
    void getDefaultHistory() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}
