package tasktracker.handlers;

import com.sun.net.httpserver.HttpExchange;
import tasktracker.models.Task;
import tasktracker.managers.TaskManager;

import java.io.IOException;
import java.util.List;

public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager taskManager) {
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

    // Метод для обработки получения задач
    private void handleGetRequest(HttpExchange exchange, String path) throws IOException {
        try {
            if (path.equals("/tasks")) {
                List<Task> tasks = taskManager.getAllTasks();
                sendText(exchange, tasks);
            } else {
                int taskId = parseIdFromPath(path);
                Task task = taskManager.getTaskById(taskId);
                if (task == null) {
                    sendNotFound(exchange, "Задача с ID " + taskId + " не найдена");
                    return;
                }
                sendText(exchange, task);
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    // Метод для обработки создания или обновления задач
    private void handlePostRequest(HttpExchange exchange) throws IOException {
        try {
            Task task = parseRequestBody(exchange, Task.class);
            if (task.getName() == null || task.getName().isBlank()) {
                sendErrorRequest(exchange, "Заголовок задачи не может быть пустым");
                return;
            }

            if (task.getId() == null || task.getId() == 0) {
                taskManager.createTask(task);
                sendCreateOrUpdateItem(exchange);
            } else {
                taskManager.updateTask(task);
                sendCreateOrUpdateItem(exchange);
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    // Метод для обработки удаления задач
    private void handleDeleteRequest(HttpExchange exchange, String path) throws IOException {
        try {
            if (path.equals("/tasks")) {
                taskManager.deleteAllTasks();
                sendText(exchange, "Все задачи успешно удалены");
            } else {
                int taskId = parseIdFromPath(path);
                taskManager.deleteTask(taskId);
                sendText(exchange, "Задача успешно удалена");
            }
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }
}