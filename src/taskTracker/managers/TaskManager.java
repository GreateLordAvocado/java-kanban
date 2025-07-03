package tasktracker.managers;

import tasktracker.models.*;
import java.util.*;

public interface TaskManager {

    //Методы получения списков
    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    //Методы для удаления
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    //Методы для получения по id
    Task getTaskById(Integer idTask);

    Epic getEpicById(Integer idEpic);

    Subtask getSubtaskById(Integer idSubtask);

    //Методы для создания
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    //Методы обновления
    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    //Методы удаления
    void deleteTask(Integer idTask);

    void deleteEpic(Integer idEpic);

    void deleteSubtask(Integer idSubtask);

    //Метод получения списка всех подзадач определённого эпика
    List<Subtask> getSubtaskByEpic(Integer idEpic);

    //Метод для отображения последних просмотренных задач
    List<Task> getHistory();

    //Метод возвращает список отсортированных задач по приоритету
    List<Task> getPrioritizedTasks();
}