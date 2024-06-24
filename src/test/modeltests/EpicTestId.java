package test.modeltests;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.Managers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTestId {
    Epic epic = new Epic("newEpic1", "newDescription1");
    Task epic2 = new Epic("newEpic2", "newDescription2");

    @Test
    public void epicEquals() {
        epic.setId(1);
        epic2.setId(1);
        Assertions.assertEquals(epic, epic2);
    }

    static class InMemoryTaskManagerTest {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Task task = inMemoryTaskManager.createTask(new Task("newTask", "newDescription1"));
        Epic epic = inMemoryTaskManager.createEpic(new Epic("newEpic", "newDescription2"));
        Task subTask = inMemoryTaskManager.createSubTask(new SubTask("newSubTask", "newDescription3"),
                epic);

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
