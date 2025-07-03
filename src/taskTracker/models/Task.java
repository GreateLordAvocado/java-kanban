package tasktracker.models;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.Duration;

public class Task {

    private Integer id;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime startTime;
    private Duration duration;

    public Task(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(Integer id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = TaskStatus.NEW;
    }

    public Task(Integer id,
                String name,
                String description,
                TaskStatus status,
                LocalDateTime startTime,
                Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, TaskStatus status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }


    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public static Task parseTaskFromString(String[] words) {
        Task task = new Task(Integer.parseInt(words[0]),
                words[2],
                words[3]
        );
        task.setStatus(TaskStatus.valueOf(words[4]));
        task.setStartTime(parseDateTime(words[6]));
        task.setDuration(parseDuration(words[7]));
        return task;
    }

    @Override
    public String toString() {
        return String.join(",",
                getId().toString(),
                TaskType.TASK.toString(),
                getName(),
                getDescription() != null ? getDescription() : "",
                getStatus() != null ? getStatus().toString() : TaskStatus.NEW.toString(),
                "",
                getStartTime() != null ? getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "",
                getDuration() != null ? String.valueOf(duration.toMinutes()) : "0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected static LocalDateTime parseDateTime(String value) {
        return value.isEmpty() ? null :
                LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    protected static Duration parseDuration(String value) {
        return Duration.ofMinutes(Long.parseLong(value.isEmpty() ? "0" : value));
    }
}