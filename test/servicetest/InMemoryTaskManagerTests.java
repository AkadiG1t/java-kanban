package servicetest;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.Managers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTaskManagerTests {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(Managers.getDefaultHistory());

        Task task = inMemoryTaskManager.createTask(new Task("newTask", "newDescription1"));
        Epic epic = inMemoryTaskManager.createEpic(new Epic("newEpic", "newDescription2"));
        SubTask subTask = inMemoryTaskManager.createSubTask(new SubTask("newSubTask", "newDescription3",
                        Duration.of(20, ChronoUnit.MINUTES)),
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

        @Test
        void getTasks() {
            assertNotNull(inMemoryTaskManager.getTasks());
        }

        @Test
        void deleteTasks() {
            inMemoryTaskManager.deleteTasks();
            assertTrue(inMemoryTaskManager.getTasks().isEmpty());
        }

        @Test
        void removeTask() {

            inMemoryTaskManager.removeTask(task.getId());

            boolean rlyTaskdlt = inMemoryTaskManager.getTasks().stream()
                    .anyMatch(task1 -> task1.equals(task));

            assertFalse(rlyTaskdlt);
        }

        @Test
        void updateTask() {
            Task task1 = inMemoryTaskManager.createTask(new Task("NewTask"
                    ,task.getDescription(), task.getDuration()));
            task1.setId(subTask.getId());
            inMemoryTaskManager.updateTask(task1);
            assertNotEquals(task1, task);
        }

        @Test
        void getEpics() {
            assertNotNull(inMemoryTaskManager.getEpics());
            assertFalse(inMemoryTaskManager.getEpics().isEmpty());
        }

        @Test
        void deleteEpics() {
            inMemoryTaskManager.deleteEpics();
            assertTrue(inMemoryTaskManager.getEpics().isEmpty());
        }

        @Test
        void removeEpic() {
                inMemoryTaskManager.removeEpic(epic.getId());

            boolean rlyEpicDlt = inMemoryTaskManager.getEpics().stream()
                    .anyMatch(epic1 -> epic1.equals(epic));

            assertFalse(rlyEpicDlt);
        }

        @Test
        void updateEpic() {
            Epic epic1 = inMemoryTaskManager.createEpic(new Epic("NewEpic"
                    , subTask.getDescription()));
            epic1.setId(epic.getId());
            inMemoryTaskManager.updateEpic(epic1);
            assertNotEquals(epic1, epic);
        }

        @Test
        void getAllSubTasksForEpic() {
            assertNotNull(epic.getSubtasks());
            assertFalse(epic.getSubtasks().isEmpty());
        }

        @Test
        void getAllSubTasks() {
            assertFalse(inMemoryTaskManager.getAllSubTasks().isEmpty());
            assertNotNull(inMemoryTaskManager.getAllSubTasks());
        }

        @Test
        void deleteAllSubTasks() {
            inMemoryTaskManager.deleteAllSubTasks();
            assertTrue(inMemoryTaskManager.getAllSubTasks().isEmpty());
        }

        @Test
        void removeSubTask() {
            inMemoryTaskManager.removeSubTask(subTask.getId());

            boolean rlySubtaskDlt = inMemoryTaskManager.getAllSubTasks().stream()
                    .anyMatch(subTask1 -> subTask1.equals(subTask));

            assertFalse(rlySubtaskDlt);
        }

        @Test
        void updateSubTasks() {
            SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("NewSubTask"
                    , subTask.getDescription(), subTask.getDuration()), epic);
            subTask1.setStatus(Status.DONE);
            inMemoryTaskManager.updateSubTasks(subTask1);
            assertNotEquals(subTask1, subTask);
        }

        @Test
        void testGetPrioritizedTasks() {
            assertNotNull(inMemoryTaskManager.getPrioritizedTasks());
        }

        @Test
        void testGetHistory() {
            assertNotNull(inMemoryTaskManager.getHistory());
        }
    }
