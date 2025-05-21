package tasktracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
        this.subtasksId = new ArrayList<>();
    }

    public void addSubtask(Integer subtaskId) {
        if (subtaskId != this.getId()) {
            subtasksId.add(subtaskId);
        }
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void clearSubtasksId() {
        subtasksId.clear();
    }

    public void deleteSubtaskById(Integer id) {
        subtasksId.remove(id);
    }

    public void setSubtasksId(List<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return String.join(",",
                getId().toString(),
                TaskType.EPIC.toString(),
                getName(),
                getStatus().toString(),
                getDescription(),
                "");
    }
}