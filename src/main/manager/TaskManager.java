package main.manager;

import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    /*
        Методы для задач согласно заданию
         */

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, SubTask> getSubTasks();

    HashMap<Integer, Epic> getEpics();

    int generatedID();

    // получение списка всех задач
    List<Task> getListAllTasks();

    //Метод получения задачи по идентификатору
    Task getTaskById(Integer id);

    // Метод создания новой задачи посредством передачи задачи в качестве параметра метода
    Task createTask(Task task1);

    //Метод обновления задачи
    Task updateTask(Task task1);

    // Метод удаления всех задач все типов
    void deleteAllTasks();

    // Метод удаления задачи по идентификатору
    void deleteTaskById(Integer id);

    /*
        Методы для подзадач согласно заданию
         */

    //Метод получение списка всех подзадач
    List<Object> getListAllSubTasks();

    //Метод возвращает подзадачу по идентификатору
    SubTask getSubTaskById(Integer id);

    // Метод получения списка всех подзадач определенного эпика
    List<SubTask> getListAllTasksOfEpic(Epic epic);

    //Метод создания новой подзадачи
    SubTask createSubTask(SubTask subTask);

    //Метод обновления подзадачи
    void upDataSubTask(SubTask subTask);

    //Метод удаления всех подзадач
    void deleteAllSubTasks();

    // Метод удаления подзадачи по идентификатору
    void deleteSubTaskById(Integer id);

    /*
        Методы для эпиков согласно заданию
         */

    //Метод получения списка всех эпиков
    List<Epic> getListAllEpics();

    // метод получение эпика по идентификатору
    Epic getEpicById(Integer id);

    //Метод создания нового эпика
    Epic createEpic(Epic epic);

    //Метод обновления эпика
    void updateEpic(SubTask subTask);

    //Метод удаления всех эпиков
    void deleteAllEpics();

    //Метод удаления эпика по идентификатору
    void deleteEpicById(Integer id);
}
