package tasks;

import enums.Status;
import enums.TaskType;

import java.util.Objects;

/*
Класс описывает задачи с типом "Task".
 */
public class Task {
    protected TaskType taskType; //тип задачи
    protected Integer id; // идентификатор задачи
    protected String name; // имя задачи
    protected String description; //описание задачи
    protected Status status; // статус задачи

    public TaskType getTaskType() {
        return taskType;
    }

    // Конструкторы класса Task
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = TaskType.TASK;
    }

    public Task(String name, String description, Integer id, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = TaskType.TASK;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Переопределение метода equals
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return taskType == task.taskType && Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    // Переопределение метода hasCode
    @Override
    public int hashCode() {
        return Objects.hash(taskType, id, name, description, status);
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "tasks.Task{" +
                "taskType=" + taskType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
