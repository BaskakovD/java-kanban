package test;

import main.enums.Status;
import main.manager.FileBackedTaskManager;
import main.manager.InMemoryHistoryManager;
import main.manager.InMemoryTaskManager;
import main.manager.Managers;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class InMemoryTaskManagerTest {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();


    InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
//    FileBackedTaskManager fileBackedTaskManager = Managers.getDefaultFileBacked();

    //Проверка, что два разных экземпляра Task не равны между собой.
    @Test
    public void notEqualTasks() {
        //Проверяем, что разные задачи не равны по всем полям.
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.createTask(new Task("TaskNam_2", "TaskDescription_2", 2, Status.DONE));
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getName(), inMemoryTaskManager.getTaskById(2).getName());
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getDescription(), inMemoryTaskManager.getTaskById(2).getDescription());
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getId(), inMemoryTaskManager.getTaskById(2).getId());
        assertNotEquals(inMemoryTaskManager.getTaskById(1).getStatus(), inMemoryTaskManager.getTaskById(2).getStatus());
        assertNotEquals(inMemoryTaskManager.getTaskById(1), inMemoryTaskManager.getTaskById(2));
    }

    //Проверка равенства двух задач по Id.
    @Test
    public void equalTasksById() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.createTask(new Task("TaskNam_2", "TaskDescription_2", 2, Status.DONE));
        Task task3 = new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW);
        assertEquals(inMemoryTaskManager.getTaskById(1), task3, "Проверка равенства задача с одинаковым ID с помощью метода Equals");
    }

    //Проверка равенства двух подзадач (наследников класса Task) по Id.
    @Test
    public void equalSubTasksById() {
        inMemoryTaskManager.createEpic(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 5));
        SubTask subTask3 = new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5);
        assertEquals(inMemoryTaskManager.getSubTaskById((3)), subTask3, "Проверка равенства задача с одинаковым ID с помощью метода Equals");
    }

    //Проверка равенства/неравенства двух эпиков (наследников класса Task) по Id.
    @Test
    public void equalEpicById() {
        inMemoryTaskManager.createEpic(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        inMemoryTaskManager.createEpic(new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        Epic epic3 = (new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        assertEquals(epic3, inMemoryTaskManager.getEpicById(9), "Проверка равенства двух эпиков с одинаковым ID с помощью метода Equals");
    }

    //Проверка невозможности создания задачи без существующего epicId.
    @Test
    public void notCreatedSubTasksByIncorrectEpicId() {
        inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 6));
        assertEquals(0, inMemoryTaskManager.getHistoryManager().getHistory().size(), "SubTask с несуществующими epicId просто не добавляются");
    }

    //Проверка создания подзадач (наследников класса Task) по существующим epicId.
    @Test
    public void createSubTasksOnlyWithCorrectEpicId() {
        inMemoryTaskManager.createEpic(new Epic("2_Epic", "2_Epic", 9, Status.DONE));
        inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 9));
        inMemoryTaskManager.createSubTask(new SubTask("2_SubTaskName", "2_SubTaskDescription", 4, Status.NEW, 9));
        assertEquals(1, inMemoryTaskManager.getEpics().size(), "Проверка наличия HashMap epics");
        assertEquals(2, inMemoryTaskManager.getSubTasks().size(), "Проверка наличия в subTasks двух подзадач с корректным epicId");
    }

    //Проверка возврата проинициализированных экземпляров менеджеров на примере нескольких экземпляров
    @Test
    public void returnInMemoryHistoryManagerAndInMemoryHistoryManager() {
        inMemoryTaskManager.createSubTask(new SubTask("1_SubTaskName", "1_SubTaskDescription", 3, Status.NEW, 5));
        InMemoryTaskManager inMemoryTaskManager1 = (InMemoryTaskManager) Managers.getDefault();
        InMemoryTaskManager inMemoryTaskManager2 = (InMemoryTaskManager) Managers.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        assertNotEquals(true, inMemoryTaskManager.equals(inMemoryTaskManager1), "Проверка неравенства экземпляров InMemoryTaskManager");
        assertNotEquals(true, inMemoryTaskManager1.equals(inMemoryTaskManager2), "Проверка неравенства экземпляров  InMemoryTaskManager");
        assertNotEquals(true, inMemoryTaskManager.getHistoryManager().equals(inMemoryHistoryManager), "Проверка неравенства экземпляров InMemoryHistoryManager");
        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "Проверка истории просмотров задач на нулевой размер");
        inMemoryHistoryManager.add(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryHistoryManager.add(new Epic("1_Epic", "1_Epic", 5, Status.DONE));
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Добавили в историю просмотра задачу и эпик. Проверка истории просмотров задач на размер2");
    }

    // Проверяем добавление задачи в историю. Задача с одинаковым Id в истории не дублируется и сохраняется крайняя версия просмотра задачи.
    @Test
    public void addTasksToHistoryNotRepeat() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        assertEquals(0, inMemoryTaskManager.getHistoryManager().getHistory().size(), "Проверка истории просмотров задач на нулевой размер");
        inMemoryTaskManager.getTaskById(1);
        assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory(), "История не пустая");
        inMemoryTaskManager.createTask(new Task("TaskName_2", "TaskDescription_2", 2, Status.DONE));
        inMemoryTaskManager.getTaskById(2);
        assertEquals(2, inMemoryTaskManager.getHistoryManager().getHistory().size(), "История не пустая, размер равен 1");
        inMemoryTaskManager.getTaskById(1);
        assertEquals(2, inMemoryTaskManager.getHistoryManager().getHistory().size(), "История не пустая, размер по-прежнему равен 2");
    }

    // Проверяем добавление задачи в историю. Задача с одинаковым Id в истории не дублируется и сохраняется крайняя версия просмотра.
    @Test
    public void addTasksToHistory() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.getTaskById(1);
        assertNotNull(inMemoryTaskManager.getHistoryManager().getHistory(), "История не пустая");
        assertEquals(1, inMemoryTaskManager.getHistoryManager().getHistory().size(), "История не пустая, размер равен 1");
        inMemoryTaskManager.getTaskById(1);
        assertEquals(1, inMemoryTaskManager.getHistoryManager().getHistory().size(), "История не пустая, размер равен 2");
    }

    //Создание задачи с заданным Id и сгенерированным ID и отсутствие конфликта
    @Test
    public void createTaskByIdAndCreateTaskWithGeneratedID() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", Status.NEW));
        inMemoryTaskManager.createTask(new Task("TaskName_2", "TaskDescription_2", Status.DONE));
        inMemoryTaskManager.createTask(new Task("TaskName_3", "TaskDescription_3", Status.DONE));
        assertEquals(3, inMemoryTaskManager.getTasks().size(), "Три задачи добавлены в tasks");
    }

    // Проверка неизменности задачи при добавлении в Task
    @Test
    public void equalsTaskInTaskWhenCreated() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        Task task2 = (new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        assertEquals(task2, inMemoryTaskManager.getTaskById(1), "Равенство задач при добавлении в Task по всем полям");
        assertEquals(task2.getName(), inMemoryTaskManager.getTaskById(1).getName(), "Проверка равенства по имени");
        assertEquals(task2.getDescription(), inMemoryTaskManager.getTaskById(1).getDescription(), "Проверка равенства по описанию");
        assertEquals(task2.getId(), inMemoryTaskManager.getTaskById(1).getId(), "Проверка равенства по ID");
        assertEquals(task2.getStatus(), inMemoryTaskManager.getTaskById(1).getStatus(), "Проверка равенства по Статусу");
    }

    //В истории сохраняется только последний просмотр задачи
    @Test
    public void differentVersionTaskInHistory() {
        inMemoryTaskManager.createTask(new Task("TaskName_1", "TaskDescription_1", 1, Status.NEW));
        inMemoryTaskManager.createTask(new Task("TaskName_2", "TaskDescription_2", 2, Status.NEW));
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.updateTask(new Task("TaskName_NEW", "TaskDescription_NEW", 1, Status.IN_PROGRESS));
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.updateTask(new Task("TaskName_NEW_NEW", "TaskDescription_NEW_NEW", 1, Status.IN_PROGRESS));
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        assertEquals(2, inMemoryTaskManager.getHistoryManager().getHistory().size(), "История изменения задачи сохранилась, размер истории по-прежнему 1");
        assertTrue(inMemoryTaskManager.getTaskById(1).getName().equals("TaskName_NEW_NEW"), "В истории сохранилась последняя версия задачи");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManagerTest that = (InMemoryTaskManagerTest) o;
        return Objects.equals(tasks, that.tasks) && Objects.equals(subTasks, that.subTasks) && Objects.equals(epics, that.epics) && Objects.equals(inMemoryTaskManager, that.inMemoryTaskManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, subTasks, epics, inMemoryTaskManager);
    }
}