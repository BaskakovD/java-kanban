import main.enums.Status;
import main.manager.InMemoryTaskManager;
import main.manager.Managers;
import main.tasks.Epic;
import main.tasks.SubTask;
import main.tasks.Task;

public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        // создание двух задач
        Task task1 = inMemoryTaskManager.createTask(new Task("Ракета", "Создание ракеты в 2024 году", Status.NEW));
        Task task2 = inMemoryTaskManager.createTask(new Task("Пушка", "Проектирование пушки в 2025 году", Status.IN_PROGRESS)); // в create task добавить возврат задачи
        // создание эпика
        Epic epic = inMemoryTaskManager.createEpic(new Epic("Новый дом", "Строительство нового дома в 2024-2025 гг", Status.NEW));
        // создание трех подзадач для эпика
        SubTask subTask1 = inMemoryTaskManager.createSubTask(new SubTask("Фундамент", "Заливка фундамента весной 2024", Status.IN_PROGRESS, epic.getId()));
        SubTask subTask2 = inMemoryTaskManager.createSubTask(new SubTask("Коробка дома", "Закупка кирпича и строительство коробки дома летом 2023", Status.NEW, epic.getId()));
        SubTask subTask3 = inMemoryTaskManager.createSubTask(new SubTask("Разводка", "Создание внутренней проводки весной 2025", Status.NEW, epic.getId()));

        //String Format
        // Запрос вызова для задачи №1 и проверка размера истории
        String resultGetTaskId1 = String.format("Вызываем метод getId для задачи : %s", inMemoryTaskManager.getTaskById(task1.getId()));
        String history1Size = String.format("Размер истории равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());

        //Запрос вызова для задачи №2 и проверка размера истории
        String resultGetTaskId2 = String.format("Вызываем метод getId для задачи : %s", inMemoryTaskManager.getTaskById(task2.getId()));
        String history2Size = String.format("Размер истории равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        // Запрос вызова для эпика №1 и проверка размера истории
        String resultGetEpisId = String.format("Вызываем метод getId для задачи : %s", inMemoryTaskManager.getEpicById(epic.getId()));
        String history3Size = String.format("Размер истории равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        // Запрос вызова для сабтаски №1 и проверка размера истории
        String resultGetSubTaskId1 = String.format("Вызываем метод getId для задачи : %s", inMemoryTaskManager.getSubTaskById(subTask1.getId()));
        String history6Size = String.format("Размер истории равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        // Запрос вызова для сабтаски №2 и проверка размера истории
        String resultGetSubTaskId2 = String.format("Вызываем метод getId для задачи : %s", inMemoryTaskManager.getSubTaskById(subTask1.getId()));
        String history7Size = String.format("Размер истории равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        //Повторный запрос истории для задачи №1 и проверка неизменности истории
        String resultGetTaskId1Repeat = String.format("Вызываем метод getId ПОВТОРНО для задачи : %s", inMemoryTaskManager.getSubTaskById(subTask1.getId()));
        String history1SizeRepeat = String.format("Размер истории НЕ ИЗМЕНИЛСЯ и равен: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());
        String printHistory = String.format("История задач: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        //Удаление эпика с двумя подзадачами
        String deleteEpic = String.format("Удаляем эпик %s c подзадачей %s и %s", inMemoryTaskManager.getEpicById(epic.getId()), inMemoryTaskManager.getSubTaskById(subTask1.getId()), inMemoryTaskManager.getSubTaskById(subTask2.getId()));
        inMemoryTaskManager.deleteEpicById(epic.getId());
        String printHistoryDeleteEpic = String.format("История задач: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory());
        String historySizeDeleteEpic = String.format("Размер истории стал равным: %s", inMemoryTaskManager.getInMemoryHistoryManager().getHistory().size());

        System.out.println(resultGetTaskId1);
        System.out.println(history1Size);
        System.out.println(printHistory);

        System.out.println(resultGetTaskId2);
        System.out.println(history2Size);
        System.out.println(printHistory);

        System.out.println(resultGetEpisId);
        System.out.println(history3Size);
        System.out.println(printHistory);

        System.out.println(resultGetSubTaskId1);
        System.out.println(history6Size);
        System.out.println(printHistory);

        System.out.println(resultGetSubTaskId2);
        System.out.println(history7Size);
        System.out.println(printHistory);

        System.out.println(resultGetTaskId1Repeat);
        System.out.println(history1SizeRepeat);
        System.out.println(printHistory);

        System.out.println(deleteEpic);

        System.out.println(printHistoryDeleteEpic);
        System.out.println(historySizeDeleteEpic);

    }

}
