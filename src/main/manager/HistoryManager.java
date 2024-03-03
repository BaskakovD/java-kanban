package main.manager;

import main.tasks.Task;

import java.util.Deque;

public interface HistoryManager {

    // Получение истории просмотров
    Deque<Task> getHistory();

    //добавление задачи в historyList
    void addToTaskToHistory(Task task1);
}
