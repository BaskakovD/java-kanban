package main.manager;

import main.tasks.Task;

import java.util.Deque;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> historyList = new LinkedList<>() {};
        /*
    Реализация методов работы с историей задач
     */
    //добавление задачи в historyList
    @Override
    public void addToTaskToHistory(Task task1) {
        if (historyList.size() > 9) {
            historyList.removeFirst();
        }
        historyList.addLast(task1);
    }

    // Метод получения historyList
    @Override
    public Deque<Task> getHistory() {
        return historyList;
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyList=" + historyList +
                '}';
    }
}
