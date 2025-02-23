package taskTracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import taskTracker.model.Epic;
import taskTracker.model.Status;

class EpicTest {
    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("Epic 1", "Epic Description 1");
        Epic epic2 = new Epic("Epic 1", "Epic Description 1");
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
    }
}