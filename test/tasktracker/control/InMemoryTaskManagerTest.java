package tasktracker.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.*;

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

    @Test
    void shouldUpdateTask() {
        Task task = new Task("Task 1", "Description 1");
        int taskId = taskManager.addTask(task);
        Task updatedTask = new Task("Updated Task", "Updated Description");
        updatedTask.setId(taskId);

        taskManager.updateTask(updatedTask);

        assertEquals(updatedTask, taskManager.getTask(taskId), "Обновленная задача должна заменять старую");
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task("Task 1", "Description 1");
        int taskId = taskManager.addTask(task);
        taskManager.deleteTask(taskId);

        assertNull(taskManager.getTask(taskId), "Удаленная задача не должна существовать в менеджере");
    }

}