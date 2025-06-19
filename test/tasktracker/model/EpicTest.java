package tasktracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EpicTest {

    static Epic epic1 = new Epic(5, "Эпик 1", "Описание эпика 1", TaskStatus.NEW);
    static Epic epic2 = new Epic(5, "Эпик 2", "Описание эпика 2", TaskStatus.IN_PROGRESS);

    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        assertEquals(epic1, epic2, "Наследники класса task.Task с одинаковым id должны быть равны!");
    }
}