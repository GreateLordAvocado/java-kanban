package tasktracker.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    void shouldReturnInitializedTaskManager() {
        assertNotNull(Managers.getDefault(), "Метод getDefault() должен возвращать инициализированный экземпляр TaskManager");
    }

    @Test
    void shouldReturnInitializedHistoryManager() {
        assertNotNull(Managers.getDefaultHistory(), "Метод getDefaultHistory() должен возвращать инициализированный экземпляр HistoryManager");
    }
} //