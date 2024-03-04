import main.enums.Status;
import main.manager.InMemoryTaskManager;
import main.manager.Managers;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Просто проверяем работу программы
          Scanner scanner = new Scanner(System.in);
          InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

          inMemoryTaskManager.createTask(new Task("Ракета", "Создание ракеты в 2024 году", 1, Status.NEW));
          inMemoryTaskManager.createTask(new Task("Пушка", "Проектирование пушки в 2025 году", 2, Status.IN_PROGRESS));
          inMemoryTaskManager.createTask(new Task("Самолет", "Утилизация самолета в 2023 году", 3,  Status.DONE));

        inMemoryTaskManager.createEpic(new Epic("Новый дом", "Строительство нового дома в 2024-2025 гг", 4, Status.NEW));
        inMemoryTaskManager.createEpic(new Epic("Отделка дома", "Отделка дома после постройки в 2026 г.", 5,  Status.NEW));

        inMemoryTaskManager.createSubTask(new SubTask("Фундамент", "Заливка фундамента весной 2024", 6,  Status.IN_PROGRESS,  4));
       inMemoryTaskManager.createSubTask(new SubTask("Коробка дома", "Закупка кирпича и строительство коробки дома летом 2023", 7, Status.NEW, 4));
        inMemoryTaskManager.createSubTask( new SubTask("Разводка", "Создание внутренней проводки весной 2025", 8, Status.NEW, 5));
       inMemoryTaskManager.createSubTask(new SubTask("Электричество", "Создание внешней проводки весной 2026", 9, Status.NEW, 5));

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getTaskById(3);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getTaskById(3);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getSubTaskById(6);
        inMemoryTaskManager.getSubTaskById(7);
        inMemoryTaskManager.getSubTaskById(8);
        inMemoryTaskManager.getSubTaskById(9);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getSubTaskById(6);
        inMemoryTaskManager.getSubTaskById(7);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getEpicById(4);
        inMemoryTaskManager.getEpicById(4);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        inMemoryTaskManager.deleteTaskById(1);
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println("Размер истории до удаления эпика с двумя подзадачами: " + inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        System.out.println("Количество подзадач: " + inMemoryTaskManager.getListAllSubTasks().size());
        inMemoryTaskManager.deleteEpicById(4);
        System.out.println("После удаления эпика с двумя подзадачами количество подзадач: " + inMemoryTaskManager.getListAllSubTasks().size());
        System.out.println("Размер истории после удаления эпика с двумя подзадачами: " + inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        System.out.println("Создаем новую подзадачу");
        inMemoryTaskManager.createSubTask(new SubTask("Сад, огород", "Создание сада и огорода вокруг дома в 2025 году", 10, Status.NEW, 5));
        inMemoryTaskManager.getSubTaskById(10);
        System.out.println("Количество подзадач: " + inMemoryTaskManager.getListAllSubTasks().size());
        System.out.println("Размер истории после запуска метода getId: " + inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());






//
//        System.out.println(inMemoryTaskManager.getListAllTasks());
//
//        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
//        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());


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
