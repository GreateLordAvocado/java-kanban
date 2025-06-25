package tasktracker.control;

import tasktracker.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> allTasks = new HashMap<>();
    protected final Map<Integer, Epic> allEpics = new HashMap<>();
    protected final Map<Integer, Subtask> allSubtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Comparator<Task> taskComparator = Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder()));
    private final Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);
    protected Integer id = 1;

    @Override
    public List<Task> getAllTasks() {
        return List.copyOf(allTasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return List.copyOf(allEpics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return List.copyOf(allSubtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : allTasks.keySet()) {
            historyManager.remove(id);
            prioritizedTasks.remove(allTasks.get(id));
        }
        allTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : allEpics.keySet()) {
            historyManager.remove(id);
        }
        for (Integer id : allSubtasks.keySet()) {
            historyManager.remove(id);
            prioritizedTasks.remove(allSubtasks.get(id));
        }
        allSubtasks.clear();
        allEpics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Integer id : allSubtasks.keySet()) {
            historyManager.remove(id);
            prioritizedTasks.remove(allSubtasks.get(id));
        }
        for (Epic epic : allEpics.values()) {
            epic.getSubtasksId().clear();
            updateEpicDuration(epic);
            actualizeEpicStatus(epic.getId());
        }
        allSubtasks.clear();
    }

    @Override
    public Task getTaskById(Integer idTask) {
        Task task = allTasks.get(idTask);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(Integer idEpic) {
        Epic epic = allEpics.get(idEpic);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer idSubtask) {
        Subtask subtask = allSubtasks.get(idSubtask);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не должна быть null");
        }

        if (task.getStartTime() != null && checkIntersection(task)) {
            throw new IllegalStateException("Задача пересекается по времени с уже добавленными");
        }

        task.setId(generateId());
        allTasks.put(task.getId(), task);

        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не должен быть null");
        }

        epic.setId(generateId());
        allEpics.put(epic.getId(), epic);
        updateEpicDuration(epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Подзадача не должна быть null");
        }

        if (!allEpics.containsKey(subtask.getEpicId())) {
            throw new IllegalArgumentException("Не найден эпик с id=" + subtask.getEpicId());
        }

        if (subtask.getStartTime() != null && checkIntersection(subtask)) {
            System.out.println("Подзадача пересекается по времени с уже добавленными");
            return;
        }

        subtask.setId(generateId());
        allSubtasks.put(subtask.getId(), subtask);

        if (subtask.getStartTime() != null) {
            prioritizedTasks.add(subtask);
        }

        Epic epic = allEpics.get(subtask.getEpicId());
        epic.addSubtask(subtask.getId());
        updateEpicDuration(epic);
        actualizeEpicStatus(epic.getId());
    }

    @Override
    public void updateTask(Task task) {
        if (task == null || !allTasks.containsKey(task.getId())) {
            System.out.println("Задача не найдена!");
            return;
        }

        Task existing = allTasks.get(task.getId());
        prioritizedTasks.remove(existing);

        if (task.getStartTime() != null && checkIntersection(task)) {
            System.out.println("Задача пересекается по времени с уже добавленными");
            prioritizedTasks.add(existing);
            return;
        }

        allTasks.put(task.getId(), task);

        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !allEpics.containsKey(epic.getId())) {
            System.out.println("Эпик не найден!");
            return;
        }

        Epic existing = allEpics.get(epic.getId());
        existing.setName(epic.getName());
        existing.setDescription(epic.getDescription());
        updateEpicDuration(existing);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null || subtask.getEpicId() == null) {
            System.out.println("Подзадача не найдена или не привязана к эпику!");
            return;
        }
        if (!allEpics.containsKey(subtask.getEpicId())) {
            System.out.println("Подзадача привязана к несуществующему эпику!");
            return;
        }

        Subtask existing = allSubtasks.get(subtask.getId());
        prioritizedTasks.remove(existing);

        if (subtask.getStartTime() != null && checkIntersection(subtask)) {
            System.out.println("Подзадача пересекается по времени с уже добавленными");
            return;
        }

        allSubtasks.put(subtask.getId(), subtask);

        if (subtask.getStartTime() != null) {
            prioritizedTasks.add(subtask);
        }

        Epic epic = allEpics.get(subtask.getEpicId());
        updateEpicDuration(epic);
        actualizeEpicStatus(epic.getId());
    }

    @Override
    public void deleteTask(Integer idTask) {
        final Task task = allTasks.remove(idTask);

        if (task == null) {
            System.out.println("Задачи с id " + idTask + " не существует!");
            return;
        }

        prioritizedTasks.remove(task);
        historyManager.remove(idTask);
    }

    @Override
    public void deleteEpic(Integer idEpic) {
        if (idEpic == null || !allEpics.containsKey(idEpic)) {
            System.out.println("Эпика с id " + idEpic + " не существует!");
            return;
        }

        Epic epic = allEpics.remove(idEpic);

        epic.getSubtasksId().forEach(this::deleteSubtask);

        historyManager.remove(idEpic);
    }

    @Override
    public void deleteSubtask(Integer idSubtask) {
        Subtask subtask = allSubtasks.remove(idSubtask);
        if (subtask == null) return;

        Epic epic = allEpics.get(subtask.getEpicId());
        if (epic != null) {
            epic.deleteSubtaskById(idSubtask);
            actualizeEpicStatus(epic.getId());
        }

        prioritizedTasks.remove(subtask);
        historyManager.remove(idSubtask);
    }

    @Override
    public List<Subtask> getSubtaskByEpic(Integer idEpic) {
        Epic epic = allEpics.get(idEpic);

        if (epic == null) {
            System.out.println("Эпика с id " + idEpic + " не существует");
            return null;
        }

        return epic.getSubtasksId().stream().map(allSubtasks::get).toList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return List.copyOf(prioritizedTasks);
    }

    public void actualizeEpicStatus(Integer idEpic) {
        Integer countOfNew = 0;
        Integer countOfDone = 0;
        Epic epic = allEpics.get(idEpic);
        List<Integer> currentSubtaskList = epic.getSubtasksId();

        for (Integer id : currentSubtaskList) {
            switch (allSubtasks.get(id).getStatus()) {
                case NEW -> countOfNew++;
                case DONE -> countOfDone++;
                default -> {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                    return;
                }
            }
        }

        int subtaskListSize = currentSubtaskList.size();

        if (countOfNew == subtaskListSize) {
            epic.setStatus(TaskStatus.NEW);
        } else if (countOfDone == subtaskListSize) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private void updateEpicDuration(Epic epic) {
        List<Subtask> activeSubtasks = getSubtaskByEpic(epic.getId()).stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .toList();

        if (activeSubtasks.isEmpty()) {
            epic.setStartTime(null);
            epic.setEndTime(null);
            epic.setDuration(Duration.ZERO);
            return;
        }

        LocalDateTime startTime = activeSubtasks.stream()
                .map(Subtask::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        epic.setStartTime(startTime);

        LocalDateTime endTime = activeSubtasks.stream()
                .map(Subtask::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        epic.setEndTime(endTime);

        Duration duration = Duration.between(startTime, endTime);
        epic.setDuration(duration);
    }

    private boolean checkIntersection(Task newTask) {
        if (newTask.getStartTime() == null || newTask.getDuration() == null) {
            return false;
        }

        LocalDateTime newStart = newTask.getStartTime();
        LocalDateTime newEnd = newTask.getEndTime();

        return prioritizedTasks.stream()
                .filter(task -> task.getStartTime() != null && task.getDuration() != null)
                .anyMatch(task -> {
                    LocalDateTime taskStart = task.getStartTime();
                    LocalDateTime taskEnd = task.getEndTime();
                    return newStart.isBefore(taskEnd) && newEnd.isAfter(taskStart);
                });
    }

    private Integer generateId() {
        return this.id++;
    }
}