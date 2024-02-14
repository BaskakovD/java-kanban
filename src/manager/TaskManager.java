package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class TaskManager {
    private static int ID = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

   /*
   Реализация методов для задач согласно техническому заданию 4-го спринта
    */

    public static int createID() {
        return ++ID;
    }

    // получение списка всех задач
    public ArrayList<Task> getArrayListTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Метод удаления всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    //Метод получения задачи по идентификатору
    public void returnTaskById(Integer id) {
        tasks.get(id);
    }

    // Метод создания новой задачи посредством передачи задачи в качестве параметра метода
    public Task createTask(Task task) {
        if (task.getId() == null) {
            task.setId(createID());
        }
        return tasks.put(task.getId(), task);
    }

    //Метод обновления задачи
    public Task updateTask(Task task) {
        return tasks.put(task.getId(), task);
    }

    // Метод удаления задачи по идентификатору
    public Task removeTaskById(Integer id) {
        return tasks.remove(id);
    }

    /*
    Реализация методов для подзадач согласно техническому заданию
     */
    //Метод получение списка всех подзадач
    public List<Object> getArrayListSubTask() {
        if (subTasks.size() == 0) {
            return Collections.emptyList();
        }
        return new ArrayList<>(subTasks.values());
    }

    //Метод удаления всех подзадач
    public void removeAllSubTasks() {
        subTasks.clear();
    }

    //Метод возвращает подзадачу по идентификатору
    public SubTask getSubTaskId(Integer id) {
        return subTasks.get(id);
    }

    //Метод создания новой подзадачи
    public SubTask createSubTask(SubTask subTask) {
        if (subTask.getId() == null) {
            subTask.setId(createID());
        }
        subTasks.put(subTask.getId(), subTask);
        updateEpic(subTask);
        return subTask;
    }

    //Метод обновления подзадачи
    public void upDataSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpic(subTask);
    }

    // Метод удаления подзадачи по идентификатору
    public void removeSubTask(Integer id) {
        SubTask deleteSubTask = subTasks.get(id);
        if (deleteSubTask != null) {
            subTasks.remove(id);
            int epicId = deleteSubTask.getEpicId();
            Epic epicUpdate = getEpicById(epicId);
            epicUpdate.removeSubTask(deleteSubTask);
        }
    }

    /*
    Реализация методов для эпика согласно техническому ТЗ
     */
    //Метод получения списка всех эпиков
    public ArrayList<Epic> getArrayListEpics() {
        return new ArrayList<>(epics.values());
    }

    //Метод удаления всех эпиков
    public void removeAllEpics() {
        epics.clear();
    }

    // метод получение эпика по идентификатору
    public Epic getEpicById(Integer id) {
        if (epics.get(id) == null) {
            return null;
        }
        return epics.get(id);
    }

    //Метод создания нового эпика
    public Epic createEpic(Epic epic) {
        if (epic.getId() == null) {
            epic.setId(createID());
        }
        return epics.put(epic.getId(), epic);
    }

    //Метод обновления эпика
    public void updateEpic(SubTask subTask) {
        Epic epicByID = getEpicById(subTask.getEpicId());
        if (epicByID != null) {
            epicByID.addSubTask(subTask);
        }
    }

    //Метод удаления эпика по идентификатору
    public void removeEpic(Integer id) {
        epics.remove(id);
    }
}


