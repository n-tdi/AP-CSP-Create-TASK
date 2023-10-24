package world.ntdi.todotracker.service;

import world.ntdi.todotracker.model.Task;

import java.util.List;

public interface ITaskService {
    void addTask(final String p_name, final String p_description, final String p_dueDate);
    void removeTask(final int p_index);
    List<Task> getTasks();
    void updateTaskName(final int p_index, final String p_name);
    void updateTaskDescription(final int p_index, final String p_description);
    void updateTaskDueDate(final int p_index, final String p_dueDate);
    boolean taskExists(final int p_index);
}
