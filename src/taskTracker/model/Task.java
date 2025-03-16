package tasktracker.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;
    private final LocalDateTime createdAt;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setStatus(Status status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Задача №" + id +
                ": " + name + " " +
                ", Описание: " + description + " " +
                " Текущий статус: " + status +
                ", Создано: " + createdAt;
    }
}