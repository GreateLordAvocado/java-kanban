package taskTracker.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.model.*;
import java.util.List;

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

    @Test
    void shouldStoreHistoryWithoutLimit() {
        Task task1 = new Task("Task 1", "Description 1");
        task1.setId(1);
        Task task2 = new Task("Task 2", "Description 2");
        task2.setId(2);
        Task task3 = new Task("Task 3", "Description 3");
        task3.setId(3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        assertEquals(3, historyManager.getHistory().size(), "История должна хранить все просмотренные задачи без ограничения");
    }

    @Test
    void shouldRemoveTaskFromHistory() {
        Task task = new Task("Task 1", "Description 1");
        task.setId(1);
        historyManager.add(task);
        historyManager.remove(1);

        assertFalse(historyManager.getHistory().contains(task), "История не должна содержать удаленную задачу");
    }
}