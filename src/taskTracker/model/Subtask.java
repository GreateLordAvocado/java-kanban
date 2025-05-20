package tasktracker.model;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(Integer id, String name, String description, Status status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(Integer id, String name, String description, Integer epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }


    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        if (epicId != this.getId()) {
            this.epicId = epicId;
        }
    }

    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return String.join(",",
                getId().toString(),
                TaskType.SUBTASK.toString(),
                getName(),
                getStatus().toString(),
                getDescription(),
                getEpicId().toString());
    }
}