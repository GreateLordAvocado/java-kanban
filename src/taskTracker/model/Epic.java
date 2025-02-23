package taskTracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksEpic;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksEpic = new ArrayList<>();
    }

    public List<Integer> getSubtasksEpic() {
        return subtasksEpic;
    }

    public void addSubtaskEpic(int subtaskId) {
        subtasksEpic.add(subtaskId);
    }

    public void removeSubtask(int subtaskId) {
        subtasksEpic.remove(Integer.valueOf(subtaskId));
    }

    @Override
    public String toString() {
        return "Эпик №"
                + getId() + ": "
                + getName() + ", Описание: "
                + getDescription() + " Текущий статус "
                + getStatus() + ", Подзадачи "
                + subtasksEpic;
    }
}