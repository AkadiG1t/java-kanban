package service;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node<Task> {
        Task element;
        Node<Task> next;
        Node<Task> prev;
        Node(Node<Task> prev, Task element, Node<Task> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

    }
    private ArrayList<Task> tasks = new ArrayList<>();
    private HashMap<Integer, Node> map = new HashMap<>();
    Node first;
    Node last;

    private void linkLast(Task task) {
        final Node f = first;
        final Node newNode = new Node<>(null, task, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
    }



    @Override
    public void add(Task task) {
        linkLast(task);
        map.put(task.getId(), new Node<>(first, task, last));
    }

    private void getTasks() {
        for (Node node : map.values()) {
            tasks.add((Task) node.element);
        }
    }
    private void removeNode(Node node) {
        map.remove(node);
    }

    @Override
    public void remove(int id) {
        removeNode(map.get(id));
        tasks.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        getTasks();
        return new ArrayList<>(tasks);
    }

}
