package modeltest;

import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    Epic epic2 = new Epic("newEpic2", "newDescription2");
    SubTask subTask;
    SubTask subTask1;
    InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void init() {
        inMemoryTaskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
        epic = new Epic("Epic Task", "Epic Description");
        inMemoryTaskManager.createEpic(epic);
        subTask = inMemoryTaskManager.createSubTask(new SubTask("SubTask 1", "Description 1"
                , Duration.ofMinutes(20), LocalDateTime.MIN, epic.getId()));
        subTask1 = inMemoryTaskManager.createSubTask(new SubTask("SubTask 2", "Description 2"
                , Duration.ofMinutes(50), epic.getId()));
    }

    @Test
    void epicEquals() {
        epic.setId(1);
        epic2.setId(1);
        Assertions.assertNotEquals(epic, epic2);
    }

    @Test
    void epicNEWStatusOfAllSubtasks() {
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(Status.NEW, epic.getStatus());

    }

    @Test
    void epicDONEAndNewStatusOfSubtasks() {

        subTask1.setStatus(Status.NEW);
        subTask.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.updateSubTasks(subTask);
        inMemoryTaskManager.updateSubTasks(subTask1);
        inMemoryTaskManager.updateEpic(epic);


        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}
