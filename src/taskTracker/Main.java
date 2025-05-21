package tasktracker;

import tasktracker.control.*;
import tasktracker.model.*;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final TaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) throws IOException {

        File file = File.createTempFile("task",".csv");

        TaskManager taskManager = Managers.loadFromFile(file);

        // Создание двух задач, эпика с тремя подзадачами и эпика без подзадач
        Task task1 = new Task("Диплом", "Защитить диплом в универе");
        taskManager.createTask(task1);
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Пройти курсы по Java", "Отучиться на Яндекс Практикуме");
        taskManager.createTask(task2);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Переписать с 0 трекер задач", "Забыть что делал в прошлой когорте");
        taskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Офигеть и начать с начала", epic1.getId());
        taskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Понять что все плохо", epic1.getId());
        taskManager.createSubtask(subtask2);
        inMemoryTaskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Как-то с гоем пополам написать код", epic1.getId());
        taskManager.createSubtask(subtask3);
        inMemoryTaskManager.createSubtask(subtask3);

        Epic epic2 = new Epic("Не вылететь", "Остался последний шанс :(");
        taskManager.createEpic(epic2);
        inMemoryTaskManager.createEpic(epic2);

        // Проверка, создания задач
        printAllTasks();
        System.out.println();

        //Изменение статусов
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.IN_PROGRESS);

        // Обновление статусов
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        taskManager.updateEpic(epic1);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        // Вывод списков с измененными статусами
        System.out.println();
        System.out.println("После обновления:");
        printAllTasks();

        //Вывод истории просмотров
        System.out.println();
        printHistoryView();

        //Вывод истории после удаления задачи и эпика с тремя подзадачами
        System.out.println();
        taskManager.deleteTask(1);
        inMemoryTaskManager.deleteTask(1);
        taskManager.deleteEpic(3);
        inMemoryTaskManager.deleteEpic(3);
        printHistoryAfterDelete();
    }

    //Вывод списков задач, эпиков и подзадач
    public static void printAllTasks() {
        System.out.println("Все задачи:");
        for (Task task : inMemoryTaskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Все эпики:");
        for (Epic epic : inMemoryTaskManager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : inMemoryTaskManager.getSubtaskByEpic(epic.getId())) {
                System.out.println("-->" + task);
            }
        }
        System.out.println("Все подзадачи:");
        for (Task subtask : inMemoryTaskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }

    //Вывод истории последних просмотренных задач
    public static void printHistoryView() {
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubtaskById(6);
        inMemoryTaskManager.getSubtaskById(4);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getSubtaskById(5);
        inMemoryTaskManager.getEpicById(7);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getSubtaskById(4);

        System.out.println();
        System.out.println("История просмотров:");
        for (Task task : inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }

    public static void printHistoryAfterDelete() {
        System.out.println();
        System.out.println("История просмотров после удаления задачи и эпика с подзадачами: ");
        for (Task task : inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }
}