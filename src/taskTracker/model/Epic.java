package tasktracker.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;

public class Epic extends Task {

    private List<Integer> subtasksId = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(Integer id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
        this.subtasksId = new ArrayList<>();
    }

    public void addSubtask(Integer subtaskId) {
        if (subtaskId == null || subtaskId.equals(this.getId())) {
            return;
        }
        if (!subtasksId.contains(subtaskId)) {
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

    public void deleteAllSubtasks() {
        subtasksId.clear();
    }

    public void setSubtasksId(List<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return String.join(",",
                getId().toString(),
                TaskType.EPIC.toString(),
                getName(),
                getDescription() != null ? getDescription() : "",
                getStatus() != null ? getStatus().toString() : TaskStatus.NEW.toString(),
                "",
                getStartTime() != null ? getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "",
                getDuration() != null ? String.valueOf(getDuration().toMinutes()) : "0");
    }

    public static Epic parseEpicFromString(String[] words) {
        Epic epic = new Epic(
                Integer.parseInt(words[0]),
                words[2],
                words[3]
        );
        epic.setStatus(TaskStatus.valueOf(words[4]));
        epic.setStartTime(Epic.parseDateTime(words[6]));
        epic.setDuration(Epic.parseDuration(words[7]));
        return epic;
    }
}