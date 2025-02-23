package taskTracker.control;

import taskTracker.model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private static final int HISTORY_LIMIT = 10;

    @Override
    public void add(Task task) {
        if (task != null) {
            history.remove(task);
            if (history.size() >= HISTORY_LIMIT) {
                history.remove(0);
            }
            history.add(task);
        }
    }

    @Override
    public void remove(Integer id) {
        history.removeIf(task -> task.getId() == id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}