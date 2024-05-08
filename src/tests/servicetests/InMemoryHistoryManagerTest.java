package tests.servicetests;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    Task task = new Task("newTask", "newDecription");


    @Test
    void add() {
        inMemoryHistoryManager.add(task);
        final List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history);
        assertEquals(1, history.size());
    }

    static class ManagersTest {
    
        @Test
        void getDefault() {
            Assertions.assertNotNull(Managers.getDefault());
        }
    
        @Test
        void getDefaultHistory() {
            Assertions.assertNotNull(Managers.getDefaultHistory());
        }
    }
}