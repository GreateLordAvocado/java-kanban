package taskTracker.model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description);
        this.setStatus(status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Подзадача №" + getId()
                + ": " + getName() + " "
                + ", Описание: " + getDescription()
                + " " + " Текущий статус: " + getStatus()
                + ", Номер подзадачи " + epicId;
    }
}