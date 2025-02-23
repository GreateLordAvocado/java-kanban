package taskTracker;

import taskTracker.control.TaskManager;
import taskTracker.control.Managers;
import taskTracker.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Купить продукты", "Купить яйца и молоко");
        taskManager.addTask(task1);
        Task task2 = new Task("Сделать зарядку", "Сделать 5 подходов по 30 повторений");
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Переезд", "Подготовка к переезду");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать чемоданы", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1);

        // Просмотр задач
        System.out.println(taskManager.getTask(task1.getId()));
        System.out.println(taskManager.getTask(task2.getId()));
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getSubtask(subtask1.getId()));

        // Вывод истории просмотров
        System.out.println("История задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}