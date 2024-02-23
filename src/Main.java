import enums.Status;
import manager.InMemoryTaskManager;
import manager.Managers;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        Task task1 = new Task("Ракета", "Создание ракеты в 2024 году", Status.NEW);
        Task task2 = new Task("Пушка", "Проектирование пушки в 2025 году", Status.IN_PROGRESS);
        Task task3 = new Task("Самолет", "Утилизация самолета в 2023 году", Status.DONE);

        SubTask subTask1 = new SubTask("Фундамент", "Заливка фундамента весной 2024", Status.IN_PROGRESS, 4);
        SubTask subTask2 = new SubTask("Коробка дома", "Закупка кирпича и строительство коробки дома летом 2023", Status.NEW, 4);
        SubTask subTask3 = new SubTask("Разводка", "Создание внутренней проводки весной 2025", Status.NEW, 5);
        SubTask subTask4 = new SubTask("Электричество", "Создание внешней проводки весной 2026", Status.NEW, 5);

        Epic epic1 = new Epic("Новый дом", "Строительство нового дома в 2024-2025 гг", Status.NEW);
        Epic epic2 = new Epic("Отделка дома", "Отделка дома после постройки в 2026 г.", Status.NEW);

        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createTask(task3);

        inMemoryTaskManager.createSubTask(subTask1);
        inMemoryTaskManager.createSubTask(subTask2);
        inMemoryTaskManager.createSubTask(subTask3);
        inMemoryTaskManager.createSubTask(subTask4);


        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic2);

        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getTaskById(2));
        System.out.println(inMemoryTaskManager.getTaskById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getSubTaskById(5));
        System.out.println(inMemoryTaskManager.getSubTaskById(6));
        System.out.println(inMemoryTaskManager.getSubTaskById(7));
        System.out.println(inMemoryTaskManager.getEpicById(8));
        System.out.println(inMemoryTaskManager.getEpicById(9));
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getTaskById(2));
        System.out.println(inMemoryTaskManager.getEpicById(9));
        System.out.println(inMemoryTaskManager.getEpicById(9));
        System.out.println(inMemoryTaskManager.getEpicById(9));


        System.out.println(inMemoryTaskManager.getListAllTasks());

        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());


//        Managers.getDefault().createTask(task2);
//        Managers.getDefault().createTask(task3);
//        Epic epic1 = new Epic("Новый дом", "Строительство нового дома в 2024-2025 гг", Status.NEW);
//        Epic epic2 = new Epic("Отделка дома", "Отделка дома после постройки в 2026 г.", Status.NEW);
//        Managers.getDefault().createEpic(epic1);
//        Managers.getDefault().createEpic(epic2);
//        SubTask subTask1 = new SubTask("Фундамент", "Заливка фундамента весной 2024", Status.IN_PROGRESS, 4);
//        SubTask subTask2 = new SubTask("Коробка дома", "Закупка кирпича и строительство коробки дома летом 2023", Status.NEW, 4);
//        SubTask subTask3 = new SubTask("Электричество", "Создание внутренней проводки весной 2025", Status.NEW, 5);
//        inMemoryTaskManager.createSubTask(subTask1);
//        Managers.getDefault().createSubTask(subTask1);
//        inMemoryTaskManager.createSubTask(subTask2);
//        inMemoryTaskManager.createSubTask(subTask3);
//        System.out.println(inMemoryTaskManager.getListAllEpics());
//        System.out.println(inMemoryTaskManager.getListAllTasks());
//        System.out.println(inMemoryTaskManager.getListAllSubTasks());
//        subTask3.setStatus(Status.DONE);
//        System.out.println(inMemoryTaskManager.getListAllEpics());
//        inMemoryTaskManager.deleteSubTask(8);
//        System.out.println(inMemoryTaskManager.getListAllEpics());
//
//        SubTask subTask4 = new SubTask("Уравнение", "Решение уравнения", 15, Status.NEW);
//        inMemoryTaskManager.createSubTask(subTask4);
//        System.out.println(inMemoryTaskManager.getSubTaskById(15));
    }

}
