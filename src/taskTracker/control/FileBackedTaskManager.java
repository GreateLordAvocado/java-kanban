package tasktracker.control;

import tasktracker.exceptions.*;
import tasktracker.model.*;


import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

import static tasktracker.model.Epic.parseEpicFromString;
import static tasktracker.model.Subtask.parseSubtaskFromString;
import static tasktracker.model.Task.parseTaskFromString;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        if (file == null) {
            throw new ManagerReadException("Файл не инициализирован");
        }
        this.file = file;
    }

    //Методы для создания задач, эпиков и подзадач
    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }


    //Методы обновления задач, эпиков и подзадач
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    //Методы для удаления всех задач, эпиков и подзадач
    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    //Методы удаления задач по идентификатору
    @Override
    public void deleteTask(Integer idTask) {
        super.deleteTask(idTask);
        save();
    }

    @Override
    public void deleteEpic(Integer idEpic) {
        super.deleteEpic(idEpic);
        save();
    }

    @Override
    public void deleteSubtask(Integer idSubtask) {
        super.deleteSubtask(idSubtask);
        save();
    }

    @Override
    public void actualizeEpicStatus(Integer idEpic) {
        super.actualizeEpicStatus(idEpic);
        save();
    }

    // Метод, который восстанавливает состояние менеджера из файла
    public static FileBackedTaskManager loadFromFile(File file) throws ManagerReadException {
        if (file == null || !file.exists()) {
            throw new ManagerReadException("Файл не существует");
        }

        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            bufferedReader.readLine();

            List<Task> tasks = new ArrayList<>();

             while ((line = bufferedReader.readLine()) != null) {
                Task task = fromString(line);
                if (task != null) {
                    tasks.add(task);
                }
            }

            int maxId = 0;
            for (Task task : tasks) {
                int taskId = task.getId();
                maxId = Math.max(maxId, taskId);

                switch (task.getTaskType()) {
                    case TASK -> manager.allTasks.put(taskId, task);
                    case EPIC -> manager.allEpics.put(taskId, (Epic) task);
                    case SUBTASK -> {
                        Subtask subtask = (Subtask) task;
                        manager.allSubtasks.put(taskId, subtask);
                        Epic epic = manager.allEpics.get(subtask.getEpicId());
                        if (epic != null) {
                            epic.addSubtask(subtask.getId());
                        }
                    }
                }
            }
            manager.id = maxId;
        } catch (IOException e) {
            throw new ManagerReadException("Ошибка при чтении данных из файла: " + e.getMessage());
        }
        return manager;
    }

    // Метод, который сохраняет текущее состояние менеджера в указанный файл
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id,type,title,description,status,epic,startTime,duration\n");

            for (Task task : getAllTasks()) {
                writer.write(task.toString() + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(epic.toString() + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(subtask.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    // Метод создания задачи из строки
    private static Task fromString(String value) {
        String[] words = value.split(",", -1);

        try {
            TaskType type = TaskType.valueOf(words[1]);
            return switch (type) {
                case TASK -> parseTaskFromString(words);
                case EPIC -> parseEpicFromString(words);
                case SUBTASK -> parseSubtaskFromString(words);
            };
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга строки: " + value, e);
        }
    }
} //