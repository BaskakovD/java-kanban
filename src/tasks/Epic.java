package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс описывает задачи типа "Epic".
 */
public class Epic extends Task {
    ArrayList<SubTask> subTasks = new ArrayList<>();
    //Конструкторы класс Epic
    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
    }
    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.taskType = TaskType.EPIC;
    }
    public Epic(String name, String description, Integer id, Status status) {
        super(name, description, id, status);
        this.taskType = TaskType.EPIC;
    }
    // Метод проверки статуса эпика в зависимости от статуса подзадачи
    private void checkStatusEpic() {
        if (subTasks.isEmpty() || isStatus(subTasks, Status.NEW)) {
            status = Status.NEW;
        } else if (isStatus(subTasks, Status.DONE)) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
    }
    //Метод установления статуса эпика в зависимости от статуса задачи
    private boolean isStatus(ArrayList<SubTask> tasks, Status status) {
        for (SubTask task : tasks) {
            if (!task.getStatus().equals(status)) {
                return false;
            }
        }
        return true;
    }
    //Метод добавления подзадачи с проверкой уникальности идентификатора
    public void addSubTask(SubTask subTask) {
        for (SubTask task : subTasks) {
            if (task.getId().equals(subTask.getId())) {
                subTasks.remove(task);
                break;
            }
        }
        subTasks.add(subTask);
        checkStatusEpic();
    }

    // Метод удаления подзадачи с обновлением статуса эпика
    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        checkStatusEpic();
    }
    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
        checkStatusEpic();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Epic epic = (Epic) object;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "subTasks=" + subTasks +
                ", taskType=" + taskType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
