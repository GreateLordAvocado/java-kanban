package taskTracker.control;

import taskTracker.model.*;
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

    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();
    List<Task> getHistory();
}