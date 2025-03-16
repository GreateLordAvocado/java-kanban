package tasktracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tasktracker.model.Epic;
import tasktracker.model.Status;

class EpicTest {

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("Epic 1", "Epic Description 1");
        Epic epic2 = new Epic("Epic 1", "Epic Description 1");
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
    }

    @Test
    void epicShouldStoreSubtaskIds() {
        Epic epic = new Epic("Epic 1", "Epic Description 1");
        epic.setId(1);

        Subtask subtask = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, epic.getId());
        subtask.setId(2);

        epic.addSubtaskEpic(subtask.getId()); // Здесь исправлено

        assertTrue(epic.getSubtasksEpic().contains(subtask.getId()), "Эпик должен хранить ID подзадачи");
    }
}//