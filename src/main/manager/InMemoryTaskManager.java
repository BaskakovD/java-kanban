package main.manager;

import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected static int id = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {

    }

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
    public int generatedID() {
        return ++id;
    }

    @Override
    public List<Task> getListAllTasks() {
        if (tasks.size() == 0) {
            return Collections.emptyList();
        }
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
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }

        return task;
    }

    // Метод создания новой задачи посредством передачи задачи в качестве параметра метода
    @Override
    public Task createTask(Task task) {
        if (task.getId() == null) {
            task.setId(generatedID());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    //Метод обновления задачи
    @Override
    public Task updateTask(Task task) {
        return tasks.put(task.getId(), task);
    }

    // Метод удаления задачи по идентификатору
    @Override
    public void deleteTaskById(Integer id) {
        historyManager.deleteTaskInHistory(id);
        tasks.remove(id);
    }

    /*
        Реализация методов для подзадач согласно техническому заданию
         */
    //Метод получение списка всех подзадач
    @Override
    public List<SubTask> getListAllSubTasks() {
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
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    //Метод создания новой подзадачи
    @Override
    public SubTask createSubTask(SubTask subTask) {
        Integer idTemp = subTask.getId();
        if (subTask.getId() == null) {
            subTask.setId(generatedID());
        }
        if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
        } else if (!epics.containsKey(subTask.getEpicId())) {
            return null;
        }
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
    public void deleteSubTaskById(Integer id) {
        SubTask deleteSubTask = subTasks.get(id);
        if (deleteSubTask != null) {
            subTasks.remove(id);
            int epicId = deleteSubTask.getEpicId();
            Epic epicUpdate = getEpicById(epicId);
            epicUpdate.removeSubTask(deleteSubTask);
        }
        historyManager.deleteTaskInHistory(id);
    }

    /*
    Реализация методов для эпика согласно ТЗ
     */
    //Метод получения списка всех эпиков
    @Override
    public List<Epic> getListAllEpics() {
        if (epics.size() == 0) {
            return Collections.emptyList();
        }
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
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    // метод получения списка всех подзадач определенного эпика
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
            epic.setId(generatedID());
        }
        epics.put(epic.getId(), epic);
        return epic;
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
    public void deleteEpicById(Integer id) {
        HashMap<Integer, SubTask> subTasksTemp = new HashMap<>(subTasks);
        for (Map.Entry<Integer, SubTask> subTaskEntry : subTasks.entrySet()) {
            if (subTaskEntry.getValue().getEpicId() == id) {
                Integer tempId = subTaskEntry.getValue().getId();
                subTasksTemp.remove(tempId);
                historyManager.deleteTaskInHistory(tempId);
            }
        }
        historyManager.deleteTaskInHistory(id);
        subTasks.clear();
        subTasks.putAll(subTasksTemp);
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }


    //метод добавления задачи из файла бэкапа в историю просмотров
    public void addTaskToHistory(Integer id) {
        if (getTaskById(id) != null) {
            historyManager.add(getTaskById(id));
        } else if (getSubTaskById(id) != null) {
            historyManager.add(getSubTaskById(id));
        } else if (getEpicById(id) != null) {
            historyManager.add(getEpicById(id));
        }


    }

}




