package main.manager;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static class HistoryLinkedList {
        private final Map<Integer, Node> nodeHashMap = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLastTask(Task task) {
            Node node = new Node();
            node.setTask(task);
            if (nodeHashMap.containsKey(task.getId())) {
                removeNode(nodeHashMap.get(task.getId()));
            }
            if (head == null) {
                tail = node;
                head = node;
                node.setNext(null);
                node.setPrev(null);
            } else {
                node.setPrev(tail);
                node.setNext(null);
                tail.setNext(node);
                tail = node;
            }
            nodeHashMap.put(task.getId(), node);
        }

        private List<Task> getTasks() {
            List<Task> taskList = new ArrayList<>();
            Node node = head;
            while (node != null) {
                taskList.add(node.getTask());
                node = node.getNext();
            }
            return taskList;
        }

        private void removeNode(Node node) {
            if (node != null) {
                nodeHashMap.remove(node.getTask().getId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                }
                if (tail == node) {
                    tail = node.getPrev();
                }
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        public Node getNode(int id) {
            return nodeHashMap.get(id);
        }
    }

    private final HistoryLinkedList historyLinkedList = new HistoryLinkedList();


    //реализация метода получения истории просмотров задач
    @Override
    public List<Task> getHistory() {
        return historyLinkedList.getTasks();
    }


    //метод добавления задачи в историю
    @Override
    public void add(Task task) {
        historyLinkedList.linkLastTask(task);
    }

    // метод удаления задачи из истории
    @Override
    public void deleteTaskInHistory(int id) {
        historyLinkedList.removeNode(historyLinkedList.getNode(id));
    }
}

class Node {
    private Task task;
    private Node prev;
    private Node next;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

}



