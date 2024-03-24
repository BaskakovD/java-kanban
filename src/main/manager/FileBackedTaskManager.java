package main.manager;

import main.enums.Status;
import main.enums.TaskType;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static File file;

    static File getFile() {
        return file;
    }

    public FileBackedTaskManager(File file, HistoryManager historyManager) {
        super(historyManager);
        this.file = file;
    }

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    public FileBackedTaskManager() {
    }


    //Метод сохранения всех задач и их статуса в файл
    public void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            bufferedWriter.write("id,type,name,status,description,epic \n");
            //записываем задачи
            for (Task task : getListAllTasks()) {
                bufferedWriter.write(toString(task) + "\n");
            }
            //записываем эпики
            for (Epic epic : getListAllEpics()) {
                bufferedWriter.write(toString(epic) + "\n");
            }
            // записываем подзадачи
            for (SubTask subTask : getListAllSubTasks()) {
                bufferedWriter.write(toString(subTask) + "\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Запись не удалась. Попробуйте снова!" + e);
        }
    }

    //Метод загрузки данных состояния FileBackedTaskManager из файла
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String tempLine = bufferedReader.readLine();
                if (tempLine.isEmpty()) {
                    break;
                }
                Task tempTask = taskFromString(tempLine);
                if (tempTask instanceof Epic) {
                    taskManager.newEpic((Epic) tempTask);
                } else if (tempTask instanceof SubTask) {
                    taskManager.newSubTask((SubTask) tempTask);
                } else if (tempTask instanceof Task) {
                    taskManager.newTask(tempTask);
                } else {
                    System.out.println("Это не задача");
                }
            }


            String historyString = bufferedReader.readLine();

            for (Integer id : historyFromString(historyString)) {
                taskManager.addTaskToHistory(id);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Загрузка не удалась. Повторите операцию! " + e);
        }
        return taskManager;
    }


    //Метод получения типа задачи
    public TaskType getType(Task task) {
        if (task instanceof Epic) {
            return TaskType.TASK.EPIC;
        } else if (task instanceof SubTask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    //Метод получения эпика в виде строки, если задача является Subtask или Epic
    public String getEpicOfTask(Task task) {
        if (task instanceof SubTask) {
            return Integer.toString(((SubTask) task).getEpicId());
        } else if (task instanceof Epic) {
            return "";
        }
        return "";
    }


    // Метод преобразования задачи в строку
    public String toString(Task task) {
        String[] arrayStringTask = {Integer.toString(task.getId()),
                getType(task).toString(),
                task.getName().toString(),
                task.getStatus().toString(),
                task.getDescription().toString(),
                getEpicOfTask(task)
        };
        return String.join(",", arrayStringTask);
    }


    //Метод записи истории в строку
    static String historyToString(HistoryManager historyManager) {
        List<Task> historyManagerList = historyManager.getHistory();
        StringBuilder stringBuilder = new StringBuilder();
        if (historyManagerList.isEmpty()) {
            return "";
        }

        for (Task task : historyManagerList) {
            stringBuilder.append(task.getId()).append(",");
        }
        if (stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        String temp = stringBuilder.toString();
        return stringBuilder.toString();
    }

    // Метод получения истории из строки
    static List<Integer> historyFromString(String value) {
        List<Integer> historyListFromString = new ArrayList<>();
        if (value != null) {
            String[] values = value.split(",");
            for (String val : values) {
                historyListFromString.add(Integer.parseInt(val));
            }
        }
        return historyListFromString;
    }

    // Метод получения задачи из строки в зависимости от типа
    static Task taskFromString(String templine) {
        String[] valuesOfTask = templine.split(",");
        String id = valuesOfTask[0];
        String type = valuesOfTask[1];
        String name = valuesOfTask[2];
        String status = valuesOfTask[3];
        String description = valuesOfTask[4];
        //создание задачи, сабтаски или эпика
        switch (type) {
            case "TASK":
                Task task = new Task(name, description, Status.valueOf(status));
                task.setId(Integer.parseInt(id));
                return task;
            case "SUBTASK":
                Integer idOfEpic = type.equals(TaskType.SUBTASK.toString()) ? Integer.valueOf(valuesOfTask[5]) : null;
                SubTask subTask = new SubTask(name, description, Status.valueOf(status), idOfEpic);
                subTask.setId(Integer.parseInt(id));
                return subTask;
            case "EPIC":
                Epic epic = new Epic(name, description, Status.valueOf(status));
                epic.setId(Integer.parseInt(id));
                return epic;

            default:
                return null;
        }

    }


    @Override
    public HashMap<Integer, Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return super.getSubTasks();
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public int generatedID() {
        return super.generatedID();
    }

    @Override
    public List<Task> getListAllTasks() {
        return super.getListAllTasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;

    }

    @Override
    public Task updateTask(Task task) {
        Task updateTask = super.updateTask(task);
        save();
        return updateTask;
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public List<SubTask> getListAllSubTasks() {
        return super.getListAllSubTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public SubTask getSubTaskById(Integer id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        SubTask newSubTask = super.createSubTask(subTask);
        save();
        return newSubTask;
    }

    @Override
    public void upDataSubTask(SubTask subTask) {
        super.upDataSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public List<Epic> getListAllEpics() {
        return super.getListAllEpics();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public List<SubTask> getListAllTasksOfEpic(Epic epic) {
        return super.getListAllTasksOfEpic(epic);
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public void updateEpic(SubTask subTask) {
        super.updateEpic(subTask);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }


    //Метод создания задачи из файла
    public Task newTask(Task temptask) {
        return super.createTask(temptask);
    }

    //Метод создания подзадачи из файла
    public SubTask newSubTask(SubTask temptask) {
        return super.createSubTask(temptask);
    }

    //Метод создания эпика из файла
    public Epic newEpic(Epic temptask) {
        return super.createEpic(temptask);
    }

    static File createTempFile() throws IOException {
        File tempFile = File.createTempFile("MyTempFile", "");
        return tempFile;
    }


}
