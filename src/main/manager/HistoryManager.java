package main.manager;

import main.tasks.Task;


import java.util.List;

public interface HistoryManager {

    // Получение истории просмотров
    List<Task> getHistory();

    //добавление задачи в historyList
    void addToTaskToHistory(Task task);

    //удаление задачи из истории просмотра по Id
    void deleteTaskInHistory(int id);
}
