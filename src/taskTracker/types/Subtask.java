package taskTracker.types;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public int getEpicId() {
        return epic.getId();
    }

    @Override
    public String toString() {
        return "Подзадача " + getId() + ": " + getName() + " , " + getDescription() +
                " , Текущий статус " + getStatus() + " , Номер подзадачи " + getEpicId();
    }
}