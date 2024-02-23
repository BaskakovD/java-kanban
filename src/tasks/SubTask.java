package tasks;

import enums.Status;
import enums.TaskType;

/*
Класс для задач типа "Subtask".
 */
public class SubTask extends Task {
    private Integer epicId;

    //Конструкторы класс Subtask с разными полями при иницилизации задачи
    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(String name, String description, Status status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }


    public SubTask(String name, String description, Integer id, Status status, Integer epicId) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "taskType=" + taskType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
