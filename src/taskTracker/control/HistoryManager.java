package tasktracker.control;

import tasktracker.model.*;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(Integer id);

    List<Task> getHistory();
}