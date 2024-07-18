package converter;
import exception.NotFoundException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskConverter {
    private static final Map<Integer, List<SubTask>> tempSubTaskMap = new HashMap<>();


    private TaskConverter() {

    }

    public static String toString(Task task) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String dateTime = task.getStartTime().format(dateTimeFormatter);

        return task.getId() + "," + task.getType() + "," + task.getName() + "," +
                task.getStatus() + "," + task.getDescription() + "," + task.getEpicId() + "," + dateTime
                + "," + task.getDuration().toMinutes();
    }

    public static Task fromString(String value, TaskManager taskManager) {
        Task task;
        String[] classFromString = value.split(",");
        long dur = Long.parseLong(classFromString[7]);
        Duration duration = Duration.ofMinutes(dur);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(classFromString[6], dateTimeFormatter);

        switch (classFromString[1]) {
            case "TASK" -> {
                task = taskManager.createTask(new Task(classFromString[2], classFromString[4], duration));
                task.setStartTime(dateTime);
                task.setId(Integer.parseInt(classFromString[0]));
                task.setStatus(Status.valueOf(classFromString[3]));

            } case "EPIC" -> {
                Epic epic = new Epic(classFromString[2], classFromString[4]);
                epic.setId(Integer.parseInt(classFromString[0]));
                epic.setStatus(Status.valueOf(classFromString[3]));
                epic.setStartTime(dateTime);
                task = epic;

                Integer epicId = epic.getId();
                if (tempSubTaskMap.containsKey(epicId)) {
                    List<SubTask> subTasks = tempSubTaskMap.get(epicId);

                       for (SubTask subTask : subTasks) {
                            epic.addSubTask(subTask);
                       }

                        tempSubTaskMap.remove(epicId);
                }

            } case "SUBTASK" -> {
                    SubTask subTask = taskManager.createSubTask(new SubTask(classFromString[2], classFromString[4]
                        , duration));
                    subTask.setId(Integer.parseInt(classFromString[0]));
                    subTask.setStatus(Status.valueOf(classFromString[3]));
                    subTask.setStartTime(dateTime);
                    task = subTask;

                    int epicId = Integer.parseInt(classFromString[5]);
                    for (Epic epic : taskManager.getEpics()){
                        if (epic.getId() == epicId) {
                            subTask.setEpic(epic);
                            epic.addSubTask(subTask);
                        } else {
                            tempSubTaskMap.keySet().stream()
                                    .filter(key -> key == epicId)
                                    .forEach(key -> tempSubTaskMap.get(key).add(subTask));
                    }
                }
            } default -> throw new NotFoundException("Обнаружен новый объект");

        }

        return task;
    }
}