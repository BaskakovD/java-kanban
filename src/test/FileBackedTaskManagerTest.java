package test;

import main.enums.Status;
import main.manager.FileBackedTaskManager;
import main.manager.Managers;
import main.manager.TaskManager;
import main.tasks.SubTask;
import main.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends InMemoryTaskManagerTest  {
    File file;

    public Path getFileToSourse() {
        return file.toPath();
    }

    Path path = Path.of("temp");

    @BeforeEach
    public void BeforeEach() {
        //Создание временного файла
        {
            try {
                file = File.createTempFile("test", ".csv", path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        inMemoryTaskManager = new FileBackedTaskManager(file, Managers.getDefaultHistory());
    }
// Тест сохранения задачи, подзадачи и эпики в файл. Проверяем, что subTask не создается на основе несуществующего эпика
    @Test
    public void SaveTaskEpicSubTask () {
        Task task1 = new Task("Ракета", "Create the rocket in 2026", inMemoryTaskManager.generatedID(), Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.getTaskById(1);
        Task task2 = new Task("Самолет", "Создание самолета в  2025 году", inMemoryTaskManager.generatedID(), Status.IN_PROGRESS);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.getTaskById(2);
        assertEquals(inMemoryTaskManager.getListAllTasks().size(), 2);
        SubTask subTask1 = new SubTask("Фундамент", "Строительство фундамента дома", inMemoryTaskManager.generatedID(), Status.NEW, 3);
        inMemoryTaskManager.createSubTask(subTask1);
        assertEquals(inMemoryTaskManager.getListAllEpics().size(), 0, "не создается subTask на основе несуществующего эпика");
        System.out.println(file);
    }

    // Тест загрузки данных в TaskManager из файла
        @Test
        public void loadFromFileForBackUp() {
        Task task3 = new Task("Пушка", "Создание пушки в 2027 году", inMemoryTaskManager.generatedID(), Status.NEW);
        inMemoryTaskManager.createTask(task3);
        inMemoryTaskManager.getTaskById(1);
        Task task4 = new Task("Корабль", "Проектирование корабля самолета в  2025 году", inMemoryTaskManager.generatedID(), Status.IN_PROGRESS);
        inMemoryTaskManager.createTask(task4);
        inMemoryTaskManager.getTaskById(2);



//        Task task3FromFile = fileBackedTaskManager.getTaskById(1);
//        assertEquals(task3FromFile, task3);





        }

//




}
