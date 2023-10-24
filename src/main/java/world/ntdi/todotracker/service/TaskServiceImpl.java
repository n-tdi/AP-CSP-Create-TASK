package world.ntdi.todotracker.service;

import org.springframework.stereotype.Service;
import world.ntdi.todotracker.model.Task;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {
    private final List<Task> m_tasks = new ArrayList<>();

    @Override
    public void addTask(final String p_name, final String p_description, final String p_dueDate) {
        final Task task = new Task(p_name, p_description, p_dueDate);

        m_tasks.add(task);
    }

    @Override
    public void removeTask(final int p_index) throws IndexOutOfBoundsException {
        m_tasks.remove(p_index);
    }

    @Override
    public List<Task> getTasks() {
        return m_tasks;
    }

    @Override
    public void updateTaskName(final int p_index, final String p_name) throws IndexOutOfBoundsException {
        final Task task = m_tasks.get(p_index);

        task.setName(p_name);
    }

    @Override
    public void updateTaskDescription(final int p_index, final String p_description) throws IndexOutOfBoundsException {
        final Task task = m_tasks.get(p_index);

        task.setDescription(p_description);
    }

    @Override
    public void updateTaskDueDate(final int p_index, final String p_dueDate) throws IndexOutOfBoundsException {
        final Task task = m_tasks.get(p_index);

        task.setDueDate(p_dueDate);
    }

    @Override
    public boolean taskExists(final int p_index) {
        return !(p_index < 0 || p_index >= m_tasks.size());
    }
}
