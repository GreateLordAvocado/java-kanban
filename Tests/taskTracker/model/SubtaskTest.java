package taskTracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import taskTracker.model.Subtask;
import taskTracker.model.Status;

class SubtaskTest {
    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, 1);
        Subtask subtask2 = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, 1);
        subtask1.setId(1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }
}