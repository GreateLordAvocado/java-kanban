package tasktracker.handlers;

import com.sun.net.httpserver.HttpExchange;
import tasktracker.models.Subtask;
import tasktracker.exceptions.NotFoundException;
import tasktracker.managers.TaskManager;

import java.io.IOException;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler {

    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    handleGetRequest(exchange, path);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange, path);
                    break;
                default:
                    sendMethodNotAllowed(exchange, path);
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    // Метод для обработки получения подзадач
    private void handleGetRequest(HttpExchange exchange, String path) throws IOException {
        try {
            if (path.equals("/subtasks")) {
                List<Subtask> subtasks = taskManager.getAllSubtasks();
                sendText(exchange, subtasks);
            } else {
                int subtaskId = parseIdFromPath(path);
                Subtask subtask = taskManager.getSubtaskById(subtaskId);
                if (subtask == null) {
                    throw new NotFoundException("Подзадача с ID " + subtaskId + " не найдена");
                }
                sendText(exchange, subtask);
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    // Метод для обработки создания или обновления подзадач
    private void handlePostRequest(HttpExchange exchange) throws IOException {
        try {
            Subtask subtask = parseRequestBody(exchange, Subtask.class);

            if (subtask.getName() == null || subtask.getName().isBlank()) {
                sendErrorRequest(exchange, "Имя подзадачи не должно быть пустым");
                return;
            }
            if (subtask.getDescription() == null || subtask.getDescription().isBlank()) {
                sendErrorRequest(exchange, "Описание подзадачи не должно быть пустым");
                return;
            }
            if (subtask.getEpicId() == null || taskManager.getEpicById(subtask.getEpicId()) == null) {
                sendErrorRequest(exchange, "Указан некорректный epicId");
                return;
            }

            if (subtask.getId() == null || subtask.getId() == 0) {
                taskManager.createSubtask(subtask);
            } else {
                taskManager.updateSubtask(subtask);
            }
            sendCreateOrUpdateItem(exchange);

        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    // Метод для обработки удаления подзадач
    private void handleDeleteRequest(HttpExchange exchange, String path) throws IOException {
        try {
            if (path.equals("/subtasks")) {
                taskManager.deleteAllSubtasks();
                sendText(exchange, "Все подзадачи успешно удалены");
            } else {
                int subtaskId = parseIdFromPath(path);
                taskManager.deleteSubtask(subtaskId);
                sendText(exchange, "Подзадача успешно удалена");
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }
}