package tasktracker.managers;

import tasktracker.models.*;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(Integer id);

    List<Task> getHistory();

}