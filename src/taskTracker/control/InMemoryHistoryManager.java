package taskTracker.control;

import taskTracker.model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    private Node head;
    private Node tail;
    private final Map<Integer, Node> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) return;

        remove(task.getId()); // Удаляем старый просмотр (если есть)
        Node newNode = new Node(task);
        linkLast(newNode); // Добавляем в конец списка
        historyMap.put(task.getId(), newNode); // Запоминаем узел
    }

    @Override
    public void remove(Integer id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            unlink(node); // Удаляем узел из списка
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    private void linkLast(Node node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private void unlink(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }
}