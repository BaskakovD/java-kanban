package main.manager;

import java.io.File;



public class Managers {
    // Метод возвращения по умолчанию InMemoryTaskManager
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    // Метод возвращения по умолчанию InMemoryHistoryManager
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBacked() {
        return FileBackedTaskManager.loadFromFile(new File("src/backup/backup.csv"));
    }
}
