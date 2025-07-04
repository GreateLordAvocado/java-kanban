package tasktracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {

    static Task task1 = new Task(3, "Задача 1", "Описание задачи 1", TaskStatus.NEW);
    static Task task2 = new Task(3, "Задача 2", "Описание задачи 2", TaskStatus.DONE);

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны!");
    }
}