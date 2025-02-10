package taskTracker;

import taskTracker.control.TaskManager;
import taskTracker.types.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();

        Task task1 = manager.createTask(new Task("Купить продукты", "Купить яйца и молоко"));
        Task task2 = manager.createTask(new Task("Сделать зарядку", "сделать 5 подходов по 30 повторений"));

        Epic epic1 = manager.createEpic(new Epic("Переезд", "Подготовка к переезду"));
        Epic epic2 = manager.createEpic(new Epic("Подготовка к экзамену", "Выучить материал"));

        Subtask subtask1 = manager.createSubtask(new Subtask("Собрать вещи", "Упаковать чемоданы", epic1));
        Subtask subtask2 = manager.createSubtask(new Subtask("Найти грузчиков", "Найти машину", epic1));

        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все подзадачи: " + manager.getAllSubtasks());

        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        System.out.println("Эпик после обновления подзадачи: " + manager.getEpicById(epic1.getId()));

        manager.deleteEpic(epic1.getId());
        System.out.println("Все эпики после удаления: " + manager.getAllEpics());
    }
}

