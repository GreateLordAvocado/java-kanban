package tasktracker.control;

import tasktracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    public void eforeEach() {
        taskManager = createTaskManager();
    }

    @Test
    public void shouldCreateAndFindTaskById() {
        Task task = new Task("Задача", "Описание задачи");

        taskManager.createTask(task);
        Task found = taskManager.getTaskById(task.getId());

        assertEquals(task, found, "Задача должна быть создана и находиться по id!");
    }

    @Test
    public void shouldCreateAndFindEpicById() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Epic found = taskManager.getEpicById(epic.getId());
        assertEquals(epic, found, "Эпик должен быть создан и находиться по id!");
    }

    @Test
    public void shouldCreateAndFindSubtaskById() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", epic.getId());
        taskManager.createSubtask(subtask);
        Subtask found = taskManager.getSubtaskById(subtask.getId());
        assertEquals(subtask, found, "Подзадача должна быть создана и находиться по id!");
    }

    @Test
    public void shouldUpdateTaskStatus() {
        Task task = new Task("Задача", "Описание задачи");
        taskManager.createTask(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);
        Task updated = taskManager.getTaskById(task.getId());
        assertEquals(TaskStatus.IN_PROGRESS, updated.getStatus(), "Статус задачи должен обновиться");
    }

    @Test
    public void shouldCheckEpicStatusCalculation() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW");

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        assertEquals(TaskStatus.DONE, epic.getStatus(), "Статус эпика должен быть DONE");

        subtask1.setStatus(TaskStatus.NEW);
        taskManager.updateSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    public void shouldCheckTimeConflict() {
        LocalDateTime now = LocalDateTime.now();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        task1.setStartTime(now);
        task1.setDuration(Duration.ofMinutes(30));

        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task2.setStartTime(now.plusMinutes(15));
        task2.setDuration(Duration.ofMinutes(30));

        taskManager.createTask(task1);

        assertThrows(IllegalStateException.class,
                () -> taskManager.createTask(task2),
                "Должно быть исключение при конфликте времени");
    }

    @Test
    public void checkDeleteTaskById() {
        Task task = new Task("Задача для удаления", "Описание задачи");
        taskManager.createTask(task);
        taskManager.deleteTask(task.getId());
        assertFalse(taskManager.getAllTasks().contains(task), "Задача должна быть удалена!");
    }

    @Test
    public void checkDeleteEpicById() {
        Epic epic = new Epic("Эпик для удаления", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        taskManager.deleteEpic(epic.getId());

        assertFalse(taskManager.getAllEpics().contains(epic), "Эпик должен быть удален!");
        assertNull(taskManager.getEpicById(epic.getId()), "Эпик не должен находиться по id после удаления!");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Все подзадачи эпика должны быть удалены!");
        assertNull(taskManager.getSubtaskById(subtask1.getId()), "Подзадача 1 должна быть удалена");
        assertNull(taskManager.getSubtaskById(subtask2.getId()), "Подзадача 2 должна быть удалена");
    }

    @Test
    public void checkDeleteSubtaskById() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Простая подзадача", "Описание подзадачи", epic.getId());
        Subtask subtaskForDelete = new Subtask("Подзадача 2", "Подзадача для удаления", epic.getId());
        taskManager.createSubtask(subtask);
        taskManager.createSubtask(subtaskForDelete);

        taskManager.deleteSubtask(subtaskForDelete.getId());

        assertEquals(1, taskManager.getAllSubtasks().size(), "Ожидался список из 1 подзадачи");
        assertNull(taskManager.getSubtaskById(subtaskForDelete.getId()), "Подзадача 2 должна быть удалена!");
    }

    @Test
    public void checkDeleteAllTasks() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Task task3 = new Task("Задача 3", "Описание задачи 3");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        taskManager.deleteAllTasks();

        assertTrue(taskManager.getAllTasks().isEmpty(), "Все задачи должны быть удалены!");
    }

    @Test
    public void checkDeleteAllEpics() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик с подзадачами", "Описание эпика");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", epic2.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", epic2.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        taskManager.deleteAllEpics();

        assertTrue(taskManager.getAllEpics().isEmpty(), "Все эпики должны быть удалены!");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Подзадачи эпика должны быть удалены!");
        assertNull(taskManager.getSubtaskById(subtask1.getId()), "Подзадача 1 должна быть удалена");
        assertNull(taskManager.getSubtaskById(subtask2.getId()), "Подзадача 2 должна быть удалена");
    }

    @Test
    public void checkDeleteAllSubtasks() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        taskManager.deleteAllSubtasks();

        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Все подзадачи должны быть удалены!");
        assertEquals(epic, taskManager.getEpicById(epic.getId()),
                "Эпик не должен быть удалён после удаления всех подзадач!");
    }

    @Test
    public void checkConflictSpecifiedIdAndGeneratedIdTasks() {
        Task taskTest2 = new Task(4, "Задача с заданным id", "Описание задачи", TaskStatus.NEW);
        taskManager.createTask(taskTest2);
        Task taskTest3 = new Task("Задача с сгенерированным id", "Описание задачи");
        taskManager.createTask(taskTest3);

        assertNotEquals(taskTest2.getId(), taskTest3.getId(),
                "Задачи с заданным и сгенерированным id не должны конфликтовать!");
    }

    @Test
    public void checkConflictSpecifiedIdAndGeneratedIdEpics() {
        Epic epicTest2 = new Epic(6, "Эпик с заданным id", "Описание эпика", TaskStatus.NEW);
        taskManager.createEpic(epicTest2);
        Epic epicTest3 = new Epic("Эпик с сгенерированным id", "Описание эпика");
        taskManager.createEpic(epicTest3);

        assertNotEquals(epicTest2.getId(), epicTest3.getId(),
                "Эпики с заданным и сгенерированным id не должны конфликтовать!");
    }

    @Test
    public void checkConflictSpecifiedIdAndGeneratedIdSubtasks() {
        Epic epicTest4 = new Epic("Эпик с подзадачами", "Описание эпика");
        taskManager.createEpic(epicTest4);
        Subtask subtaskTest2 = new Subtask(8, "Подзадача с заданным id",
                "Описание подзадачи", TaskStatus.NEW, epicTest4.getId());
        taskManager.createSubtask(subtaskTest2);
        Subtask subtaskTest3 = new Subtask("Подзадача с сгенерированным id",
                "Описание подзадачи", epicTest4.getId());
        taskManager.createSubtask(subtaskTest3);

        assertNotEquals(subtaskTest2.getId(), subtaskTest3.getId(),
                "Подзадачи с заданным и сгенерированным id не должны конфликтовать!");
    }

    @Test
    void epicShouldNotContainRemovedSubtaskIds() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача эпика", "Описание подзадачи", epic.getId());
        taskManager.createSubtask(subtask);

        taskManager.deleteSubtask(subtask.getId());

        assertFalse(epic.getSubtasksId().contains(subtask.getId()),
                "Эпик не должен содержать ID удаленной подзадачи");
    }
}