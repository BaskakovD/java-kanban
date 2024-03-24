package test;

import main.enums.Status;
import main.manager.FileBackedTaskManager;
import main.manager.Managers;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends InMemoryTaskManagerTest {

    // Тест сохранения задачи, подзадачи и эпики в файл. Проверяем, что subTask не создается на основе несуществующего эпика
    @Test
    public void saveTaskEpicSubTask() {
        File file;
        Path path = Path.of("temp");
        //Создание временного файла
        try {
            file = File.createTempFile("test", ".csv", path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inMemoryTaskManager = new FileBackedTaskManager(file, Managers.getDefaultHistory());
        Task task1 = new Task("Ракета", "Create the rocket in 2026", 1, Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.getTaskById(1);
        Task task2 = new Task("Самолет", "Создание самолета в  2025 году", 2, Status.IN_PROGRESS);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.getTaskById(2);
        assertEquals(inMemoryTaskManager.getListAllTasks().size(), 2);
        SubTask subTask1 = new SubTask("Фундамент", "Строительство фундамента дома", 3, Status.NEW, 3);
        inMemoryTaskManager.createSubTask(subTask1);
        assertEquals(inMemoryTaskManager.getListAllEpics().size(), 0, "не создается subTask на основе несуществующего эпика");
    }

    // Тест загрузки непустых данных в TaskManager из файла
    @Test
    public void loadFromFileForBackUp() {
        File file;
        Path path = Path.of("temp");
        //Создание временного файла
        try {
            file = File.createTempFile("test", ".csv", path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inMemoryTaskManager = new FileBackedTaskManager(file, Managers.getDefaultHistory());
        Task task3 = new Task("Пушка", "Создание пушки в 2027 году", 3, Status.NEW);
        inMemoryTaskManager.createTask(task3);
        inMemoryTaskManager.getTaskById(1);
        Task task4 = new Task("Корабль", "Проектирование корабля самолета в  2025 году", 4, Status.IN_PROGRESS);
        inMemoryTaskManager.createTask(task4);
        inMemoryTaskManager.getTaskById(2);
        Epic epic1 = new Epic("Дом", "Строительство многоквартирного дома в 2025-2030 гг", 5, Status.NEW);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.getEpicById(3);
        SubTask subTask1 = new SubTask("Фундамент", "Строительство фундамента многоквартирного дома", 6, Status.NEW, epic1.getId());
        inMemoryTaskManager.createSubTask(subTask1);
        inMemoryTaskManager.getSubTaskById(4);
        FileBackedTaskManager fileBackedTaskManager = FileBackedTaskManager.loadFromFile(file);
        Task task3FromFile = fileBackedTaskManager.getTaskById(3);
        assertEquals(task3FromFile, task3, "равенство созданной Task3 созданной и Task3, заруженной из файла");
        Epic epic1FromFile = fileBackedTaskManager.getEpicById(5);
        assertEquals(epic1FromFile, epic1, "равенство созданного epic1 и epic1 из файла");
        SubTask subTask1FromFile = fileBackedTaskManager.getSubTaskById(6);
        assertEquals(subTask1FromFile, subTask1, "равенство созданной subtask и subtask, загруженной из файла");
    }

    //Тест загрузки пустого файла backup
    @Test
    public void loadEmptyBackupFile() {
        File file;
        Path path = Path.of("temp");
        //Создание временного файла
        try {
            file = File.createTempFile("test", ".csv", path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inMemoryTaskManager = new FileBackedTaskManager(file, Managers.getDefaultHistory());
        FileBackedTaskManager fileBackedTaskManager = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileBackedTaskManager.getListAllEpics().size(), 0, "количество эпиков равно 0");
        assertEquals(fileBackedTaskManager.getListAllTasks().size(), 0, "количество задач равно 0");
        assertEquals(fileBackedTaskManager.getListAllSubTasks().size(), 0, "количество подзадач равно 0");
    }

}
