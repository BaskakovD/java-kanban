package main.manager;

import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    public static int ID = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    final Random random = new Random();

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

/*
   Реализация методов для задач согласно техническому заданию 4-го спринта
    */

    // получение списка всех задач

    @Override
    public int createID() {
        ID = random.nextInt(10000);
        return ID;
    }

    @Override
    public ArrayList<Task> getListAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Метод удаления всех задач
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }


    //Метод получения задачи по идентификатору
    @Override
    public Task getTaskById(Integer id) {
        inMemoryHistoryManager.addToTaskToHistory(tasks.get(id));
        return tasks.get(id);
    }

    // Метод создания новой задачи посредством передачи задачи в качестве параметра метода
    @Override
    public Task createTask(Task task1) {
        if (task1.getId() == null) {
            task1.setId(createID());
        }
        return tasks.put(task1.getId(), task1);
    }

    //Метод обновления задачи
    @Override
    public Task updateTask(Task task1) {
        return tasks.put(task1.getId(), task1);
    }

    // Метод удаления задачи по идентификатору
    @Override
    public Task deleteTaskById(Integer id) {
        return tasks.remove(id);
    }

    /*
    Реализация методов для подзадач согласно техническому заданию
     */
    //Метод получение списка всех подзадач
    @Override
    public List<Object> getListAllSubTasks() {
        if (subTasks.size() == 0) {
            return Collections.emptyList();
        }
        return new ArrayList<>(subTasks.values());
    }

    //Метод удаления всех подзадач
    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
    }


    //Метод возвращает подзадачу по идентификатору
    @Override
    public SubTask getSubTaskById(Integer id) {
        subTasks.get(id);
//        inMemoryHistoryManager.addToSubTaskToHistory(subTasks.get(id));
        inMemoryHistoryManager.addToTaskToHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    //Метод создания новой подзадачи
    @Override
    public SubTask createSubTask(SubTask subTask) {
        if (subTask.getId() == null) {
            subTask.setId(createID());
        }
         if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
        } else if (!epics.containsKey(subTask.getEpicId())) {
            return null;
        }
        updateEpic(subTask);
        return subTask;
    }

    //Метод обновления подзадачи
    @Override
    public void upDataSubTask(SubTask subTask) {
        if (subTask.getId() == subTask.getEpicId())
            subTasks.put(subTask.getId(), subTask);
        updateEpic(subTask);
    }

    // Метод удаления подзадачи по идентификатору
    @Override
    public void deleteSubTask(Integer id) {
        SubTask deleteSubTask = subTasks.get(id);
        if (deleteSubTask != null) {
            subTasks.remove(id);
            int epicId = deleteSubTask.getEpicId();
            Epic epicUpdate = getEpicById(epicId);
            epicUpdate.removeSubTask(deleteSubTask);
        }
    }

    /*
    Реализация методов для эпика согласно ТЗ
     */
    //Метод получения списка всех эпиков
    @Override
    public ArrayList<Epic> getListAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //Метод удаления всех эпиков
    @Override
    public void deleteAllEpics() {
        epics.clear();
    }

    // метод получение эпика по идентификатору
    @Override
    public Epic getEpicById(Integer id) {
        if (epics.get(id) == null) {
            return null;
        }
//        inMemoryHistoryManager.addToEpicToHistory(epics.get(id));
        inMemoryHistoryManager.addToTaskToHistory(epics.get(id));
        return epics.get(id);
    }

    // получение списка всех подзадач определенного эпика
    public List<SubTask> getListAllTasksOfEpic(Epic epic) {
        List<SubTask> result = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId().equals(epic.getId())) {
                result.add(subTask);
            }
        }
        return result;
    }

    //Метод создания нового эпика
    @Override
    public Epic createEpic(Epic epic) {
        if (epic.getId() == null) {
            epic.setId(createID());
        }
        return epics.put(epic.getId(), epic);
    }

    //Метод обновления эпика
    @Override
    public void updateEpic(SubTask subTask) {
        Epic epicByID = getEpicById(subTask.getEpicId());
        if (epicByID != null) {
            epicByID.addSubTask(subTask);
        }
    }

    //Метод удаления эпика по идентификатору
    @Override
    public void deleteEpic(Integer id) {
        epics.remove(id);
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }


}


