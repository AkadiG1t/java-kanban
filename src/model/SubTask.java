package model;

public class SubTask extends Task {
    public SubTask(String name, String description) {
        super(name, description);
    }

    private Epic epic;

    public Epic getEpic() {
        return epic;
    }

}


