package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> historyList = new LinkedList<>();
        /*
    Реализация методов работы с историей задач
     */
    //добавление задачи в historyList
    @Override
    public void addToTaskToHistory(Task task) {
        if (historyList.size() > 9) {
            historyList.removeFirst();
        }
        historyList.addLast(task);
    }

    // Метод получения historyList
    @Override
    public LinkedList<Task> getHistory() {
        return historyList;
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyList=" + historyList +
                '}';
    }
}
