package tasktracker.managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ManagersTest {

    @Test
    public void getDefaultShouldBeInitializeInMemoryHistoryManager() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "Экземпляр класса Managers должен быть проинициализирован и" +
                " готов к работе");
        assertNotNull(historyManager, "Экземпляр класса Managers должен быть проинициализирован и" +
                " готов к работе");
    }
}