package service;


import java.io.File;

public class Managers {

    private Managers() {

    }

    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile
                (new File("resources/task.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
