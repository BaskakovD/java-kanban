package manager;

import tasks.Task;

import java.util.LinkedList;

public interface HistoryManager {

    // Получение истории просмотров
    LinkedList<Task> getHistory();

    //добавление задачи в historyList
    void addToTaskToHistory(Task task);
}
