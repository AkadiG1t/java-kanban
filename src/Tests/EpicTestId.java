package Tests;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTestId {
    Epic epic = new Epic("newEpic", "newDescription");
    Task epic2 = new Epic("newEpic", "newDescription");

    @Test
    public void epicEquals() {
        epic.setId(1);
        epic2.setId(1);
        Assertions.assertEquals(epic, epic2);
    }

    static class InMemoryTaskManagerTest {
    
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = inMemoryTaskManager.createTask(new Task("newTask", "newDescription", String.valueOf(Status.NEW)
            ));
        Epic epic = inMemoryTaskManager.createEpic(new Epic("newEpic", "newDescription"));
        Task subTask = inMemoryTaskManager.createSubTask(new SubTask("newSubTask", "newDescription",
                    String.valueOf(Status.NEW)), epic);
    
        @Test
        void createTask() {
            assertNotNull(task);
        }
    
        @Test
        void getTask() {
            assertNotNull(inMemoryTaskManager.getTask(task.getId()));
        }
    
        @Test
        void createEpic() {
            assertNotNull(epic);
        }
    
        @Test
        void getEpic() {
            assertNotNull(inMemoryTaskManager.getEpic(epic.getId()));
        }
    
        @Test
        void getSubtask() {
            assertNotNull(inMemoryTaskManager.getSubtask(subTask.getId()));
        }
    
        @Test
        void createSubTask() {
            assertNotNull(subTask);
        }
    }
}
