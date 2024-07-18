import model.Task;
import service.Managers;
import java.time.Duration;
import model.Epic;
import model.SubTask;
import service.TaskManager;
import static java.lang.System.*;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();


        Task task = taskManager.createTask(new Task("newTask", "newDescription"));

        out.println("Create task " + task);

        Epic epic = taskManager.createEpic(new Epic("Новый Эпик", "Описание эпика"));
        out.println("Create epic: " + epic);

        Task subTask = taskManager.createSubTask(new SubTask("new subTask1", "task", Duration.ZERO), epic);
        out.println("Create SubTask: " + subTask);
        SubTask subTask1 = taskManager.createSubTask(new SubTask("newSubtask2", "task2", Duration.ZERO), epic);

        out.println("Create SubTask2: " + subTask1);
        out.println("Задачи:");
        out.println(taskManager.getTasks());

        out.println("Эпики:");
        out.println(taskManager.getEpics());

        out.println("--> " + taskManager.getAllSubTasksForEpic(epic));

        out.println("Подзадачи:");
        out.println(taskManager.getAllSubTasks());

        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getEpic(2);
        taskManager.getSubtask(4);
        taskManager.getSubtask(4);

        out.println("История:");
        out.println(taskManager.getHistory());
    }
}