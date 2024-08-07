package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final HashMap<Integer, Node<Task>> nodeMap = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    private void linkLast(Task task) {
        final Node<Task> f = first;
        final Node<Task> newNode = new Node<>(null, task, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            linkLast(task);
            nodeMap.put(task.getId(), new Node<>(first, task, last));
        }
    }

    private void getTasks() {
        for (Node<Task> node : nodeMap.values()) {
            tasks.add(node.element);
        }
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            nodeMap.remove(node.element.getId());
        }
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
        tasks.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        getTasks();
        return new ArrayList<>(tasks);
    }

    private static class Node<T> {
        Task element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, Task element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}