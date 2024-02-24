package tests;

import main.enums.Status;
import main.manager.*;
import main.manager.Managers;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();


    //Проверка, что два разных экземпляра Task не равны между собой.
    @Test
    void notEqualTasks() {
        //Проверяем, что разные задачи не равны по всем полям.
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("TaskNam_2", "TaskDescription_2", 2, Status.DONE));
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getName(), inMemoryTaskManager.getTaskById(2).getName(), "Проверка неравенства имен разных задач");
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getDescription(), inMemoryTaskManager.getTaskById(2).getDescription(), "Проверка неравенства описания разных задач");
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getId(), inMemoryTaskManager.getTaskById(2).getId(), "Проверка неравенства ID разных задач");
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getStatus(), inMemoryTaskManager.getTaskById(2).getStatus(), "Проверка неравенства статусов разных задач");
        assertNotEquals(inMemoryTaskManager.getTaskById(1), inMemoryTaskManager.getTaskById(2), "Проверка неравенства задача с помощью метода Equals");
    }

    //Проверка равенства двух задач по Id.
    @Test
    void equalTasksById() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("TaskNam_2", "TaskDescription_2", 2, Status.DONE));
        Task task3 = new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW);
        assertEquals(inMemoryTaskManager.getTaskById(1), task3, "Проверка равенства задача с одинаковым ID с помощью метода Equals");
    }

    //Проверка равенства двух подзадач (наследников класса Task) по Id.
    @Test
    void equalSubTasksById() {
        Epic epic1 = inMemoryTaskManager.createEpic(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        SubTask subTask2 = inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 5));
        SubTask subTask3 = new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5);
        assertEquals(inMemoryTaskManager.getSubTaskById((3)), subTask3, "Проверка равенства задача с одинаковым ID с помощью метода Equals");
    }

    //Проверка равенства/неравенства двух эпиков (наследников класса Task) по Id.
    @Test
    void equalEpicById() {
        Epic epic1 = inMemoryTaskManager.createEpic(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        Epic epic2 = inMemoryTaskManager.createEpic(new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        Epic epic3 = (new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        assertEquals(epic3, inMemoryTaskManager.getEpicById(9), "Проверка равенства двух эпиков с одинаковым ID с помощью метода Equals");
    }

    //Проверка невозможности создания задачи без существующего epicId.
    @Test
    void notCreatedSubTasksByIncorrectEpicId() {
        SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        SubTask subTask2 = inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 6));
        assertEquals(0, inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size(), "SubTask с несуществующими epicId просто не добавляются");
    }

    //Проверка создания подзадач (наследников класса Task) по существующим epicId.
    @Test
    void createSubTasksOnlyWithCorrectEpicId() {
        Epic epic1 = inMemoryTaskManager.createEpic(new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 9));
        SubTask subTask2 = inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 9));
        assertEquals(1, inMemoryTaskManager.getEpics().size(), "Проверка наличия HashMap epics");
        assertEquals(2, inMemoryTaskManager.getSubTasks().size(), "Проверка наличия в subTasks двух подзадач с корректным epicId");
    }

    //Проверка возврата проинициализированных экземпляров менеджеров на примере нескольких экземпляров
    @Test
    void returnInMemoryHistoryManagerAndInMemoryHistoryManager() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Epic epic1 = inMemoryTaskManager.createEpic(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        InMemoryTaskManager inMemoryTaskManager1 = (InMemoryTaskManager) Managers.getDefault();
        InMemoryTaskManager inMemoryTaskManager2 = (InMemoryTaskManager) Managers.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        assertNotEquals(true, inMemoryTaskManager.equals(inMemoryTaskManager1), "Проверка неравенства экземпляров InMemoryTaskManager");
        assertNotEquals(true, inMemoryTaskManager1.equals(inMemoryTaskManager2), "Проверка неравенства экземпляров  InMemoryTaskManager");
        assertNotEquals(true, inMemoryTaskManager.getInMemoryHistoryManager().equals(inMemoryHistoryManager), "Проверка неравенства экземпляров InMemoryHistoryManager");
        LinkedList<Task> linkedList = inMemoryHistoryManager.getHistory();
        assertEquals(0, linkedList.size(), "Проверка истории просмотров задач на нулевой размер");
        inMemoryHistoryManager.addToTaskToHistory(task1);
        inMemoryHistoryManager.addToTaskToHistory(epic1);
        assertEquals(2, linkedList.size(), "Добавили в историю просмотра задачу и эпик");
        assertEquals(2, linkedList.size(), "Проверка истории просмотров задач на размер2");
    }

    // Проверяем добавление задачи в историю. История с размером 1 и 2.
    @Test
    void addTasksToHistory() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.getTaskById(1);
        assertNotNull(inMemoryTaskManager.getInMemoryHistoryManager().getHistory(), "История не пустая");
        assertEquals(1, inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size(), "История не пустая, размер равен 1");
        inMemoryTaskManager.getTaskById(1);
        assertEquals(2, inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size(), "История не пустая, размер равен 2");
    }

    //Создание задачи с заданным Id и сгенерированным ID и отстутствие конфликта
    @Test
    void createTaskByIdAndCreateTaskWithGeneratedID() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("TaskName_2", "TaskDescription_2", Status.DONE));
        Task task3 = inMemoryTaskManager.createTask(new Task("TaskName_3", "TaskDescription_3", Status.DONE));
        assertEquals(3, inMemoryTaskManager.getTasks().size(), "Три задачи добавлены в tasks");
    }

    // Проверка неизменности задачи при добавлении в Task
    @Test
    void equalsTaskInTaskWhenCreated() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Task task2 = (new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        assertEquals(task2, inMemoryTaskManager.getTaskById(1), "Равенство задач при добавлении в Task по всем полям");
        assertEquals(task2.getName(), inMemoryTaskManager.getTaskById(1).getName(), "Проверка равенства по имени");
        assertEquals(task2.getDescription(), inMemoryTaskManager.getTaskById(1).getDescription(), "Проверка равенства по описанию");
        assertEquals(task2.getId(), inMemoryTaskManager.getTaskById(1).getId(), "Проверка равенства по ID");
        assertEquals(task2.getStatus(), inMemoryTaskManager.getTaskById(1).getStatus(), "Проверка равенства по Статусу");
    }

    //сохранение в history предыдущей версии задачи
    @Test
    void differentVersionTaskInHistory() {
        Task task1 = inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.getTaskById(1);
        Task task2 = inMemoryTaskManager.updateTask(new Task("TaskName_NEW", "TaskDescription_NEW", 1, Status.IN_PROGRESS));
        inMemoryTaskManager.getTaskById(1);
        Task task3 = inMemoryTaskManager.updateTask(new Task("TaskName_NEW_NEW", "TaskDescription_NEW_NEW", 1, Status.IN_PROGRESS));
        inMemoryTaskManager.getTaskById(1);
        assertEquals(3, inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size(), "История измнения задачи сохранилась");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManagerTest that = (InMemoryTaskManagerTest) o;
        return Objects.equals(tasks, that.tasks) && Objects.equals(subTasks, that.subTasks) && Objects.equals(epics, that.epics) && Objects.equals(inMemoryTaskManager, that.inMemoryTaskManager);
    }
}