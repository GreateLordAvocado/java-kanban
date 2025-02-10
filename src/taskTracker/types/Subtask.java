package taskTracker.types;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epicId = epic.getId();
    }

    public int getEpicId() {
        return epicId;
    }
}