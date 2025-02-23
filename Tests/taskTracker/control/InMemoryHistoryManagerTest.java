package taskTracker.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.model.*;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldStorePreviousVersionOfTask() {
        Task task = new Task("Task 1", "Description 1");
        task.setId(1);
        historyManager.add(task);

        assertTrue(historyManager.getHistory().contains(task), "История должна содержать добавленную задачу");
    }
}
