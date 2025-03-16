package tasktracker.control;

import tasktracker.model.*;
import java.util.*;

public interface TaskManager {
    int addTask(Task task);
    void addEpic(Epic epic);
    void addSubtask(Subtask subtask);

    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();
    List<Task> getHistory();
}//