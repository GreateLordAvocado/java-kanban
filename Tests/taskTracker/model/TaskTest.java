package taskTracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import taskTracker.model.Task;
import taskTracker.model.Status;

class TaskTest {
    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 1", "Description 1");
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }

    @Test
    void taskShouldHaveCorrectStatus() {
        Task task = new Task("Task 1", "Description 1");
        task.setStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, task.getStatus(), "Задача должна иметь корректный статус");
    }
}