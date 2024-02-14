import enums.Status;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Ракета", "Создание ракеты в 2024 году", Status.NEW);
        Task task2 = new Task("Пушка", "Проектирование пушки в 2025 году", Status.IN_PROGRESS);
        Task task3 = new Task("Самолет", "Утилизация самолета в 2023 году", Status.DONE);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        Epic epic1 = new Epic("Новый дом", "Строительство нового дома в 2024-2025 гг", Status.NEW);
        Epic epic2 = new Epic("Отделка дома", "Отделка дома после постройки в 2026 г.", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Фундамент", "Заливка фундамента весной 2024", Status.IN_PROGRESS, 4);
        SubTask subTask2 = new SubTask("Коробка дома", "Закупка кирпича и строительство коробки дома летом 2023", Status.NEW, 4);
        SubTask subTask3 = new SubTask("Электричество", "Создание внутренней проводки весной 2025", Status.NEW, 5);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);
        System.out.println(taskManager.getArrayListEpics());
        System.out.println(taskManager.getArrayListTasks());
        System.out.println(taskManager.getArrayListSubTask());
        subTask3.setStatus(Status.DONE);
        System.out.println(taskManager.getArrayListEpics());
        taskManager.removeSubTask(8);
        System.out.println(taskManager.getArrayListEpics());

        SubTask subTask4 = new SubTask("Уравнение", "Решение уравнения", 15, Status.NEW);
        taskManager.createSubTask(subTask4);
        System.out.println(taskManager.getSubTaskId(15));
    }
}
