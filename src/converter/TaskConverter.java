package converter;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;

public class TaskConverter {

    private TaskConverter() {

    }

    public static String toString(Task task) {

        return task.getId() + "," + task.getType() + "," + task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," + task.getEpicId();
    }

    public static Task fromString(String value, TaskManager taskManager) {
        Task task = null;
        String[] classFromString = value.split(",");

        switch (classFromString[1]) {
            case "TASK" -> {
                task = taskManager.createTask(new Task(classFromString[2], classFromString[4]));
                task.setId(Integer.parseInt(classFromString[0]));
                task.setStatus(Status.valueOf(classFromString[3]));
            }
            case "EPIC" -> {
                Epic epic = taskManager.createEpic(new Epic(classFromString[2], classFromString[4]));
                epic.setId(Integer.parseInt(classFromString[0]));
                epic.setStatus(Status.valueOf(classFromString[3]));
                task = epic;

            }
            default -> {
                SubTask subTask = taskManager.createSubTask(new SubTask(classFromString[2], classFromString[4]),
                        taskManager.getEpic(Integer.parseInt(classFromString[5])));
                subTask.setId(Integer.parseInt(classFromString[0]));
                subTask.setStatus(Status.valueOf(classFromString[3]));
                task = subTask;
            }
        }

        return task;
    }
}
