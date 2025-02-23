package taskTracker.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.model.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddAndRetrieveTasksById() {
        Task task = new Task("Task 1", "Description 1");
        int taskId = taskManager.addTask(task);

        assertEquals(task, taskManager.getTask(taskId), "Добавленная задача должна находиться по ID");
    }
}