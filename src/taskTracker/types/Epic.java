package taskTracker.types;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasksEpic;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtasksEpic = new ArrayList<>();
    }

    public List<Subtask> getSubtasksEpic() {
        return subtasksEpic;
    }

    public void addSubtaskEpic(Subtask subtask) {
        subtasksEpic.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasksEpic.remove(subtask);
    }

    @Override
    public String toString() {
        return "Эпик № " + getId() + ": " + getName() + " , Описание: " + getDescription() +
                " Текущий статус " + getStatus() + " , Подзадача " + subtasksEpic;
    }
}