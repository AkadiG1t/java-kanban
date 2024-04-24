import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Новая задача", "Описание",
                String.valueOf(Status.NEW)));
        System.out.println("Create task " + task);

        Epic epic = taskManager.createEpic(new Epic("Новый Эпик", "Описание эпика"));
        System.out.println("Create epic: " + epic);

        SubTask subTask = taskManager.createSubTask(new SubTask("new subTask1", "task",
                String.valueOf(Status.NEW)), epic);
        System.out.println("Create SubTask: " + subTask);
        SubTask subTask1 = taskManager.createSubTask(new SubTask("newSubtask2", "task2",
                String.valueOf(Status.NEW)), epic);
        System.out.println("Create SubTask2: " + subTask1);
        System.out.println(epic.getSubtasks());

        taskManager.deleteEpic(epic.getId());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(epic.getSubtasks());


    }
}
