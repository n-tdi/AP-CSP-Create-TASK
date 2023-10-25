package world.ntdi.todotracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import world.ntdi.todotracker.model.Task;

import java.util.Scanner;

@Service
@AllArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {
    private final TaskService m_taskService;
    private final Scanner m_scanner = new Scanner(System.in);

    @Override
    public Action askAction() {
        print("What would you like to do? " + Action.prettyValue());

        boolean validResponse = false;

        Action action = null;

        while (!validResponse) {
            final String actionResponse = m_scanner.nextLine();

            if (actionResponse.isEmpty()) {
                continue;
            }

            try {
                action = Action.valueOf(actionResponse.toUpperCase());
            } catch (final Exception p_e) {
                print("Sorry, please enter a valid response.");
                action = null;
            }

            if (action != null) {
                validResponse = true;
            }
        }

        return action;
    }

    @Override
    public UpdateAction askUpdateAction() {
        print("What would you like to update? " + UpdateAction.prettyValue());

        boolean validResponse = false;

        UpdateAction updateAction = null;

        while (!validResponse) {
            final String actionResponse = retrieveText();

            try {
                updateAction = UpdateAction.valueOf(actionResponse.toUpperCase());
            } catch (final Exception p_e) {
                print("Sorry, please enter a valid response.");
                updateAction = null;
            }

            if (updateAction != null) {
                validResponse = true;
            }
        }

        return updateAction;
    }

    @Override
    public void respondToAction(final Action p_action) {
        switch (p_action) {
            case EXIT -> {
                m_scanner.close();
                System.exit(0);
            }
            case VIEW -> print("Here are your current tasks:\n" + showTasksFormatted());
            case CREATE -> {
                print("Please enter the NAME of your new task:");
                final String name = retrieveText();

                print("Please enter the DESCRIPTION of your new task:");
                final String description = retrieveText();

                print("Please enter the DUE DATE of your new task:");
                final String dueDate = retrieveText();

                m_taskService.addTask(name, description, dueDate);
                print("Successfully created task " + name + ".");
            }
            case REMOVE -> {
                final int taskIndex = retrieveTask();

                m_taskService.removeTask(taskIndex);

                print("Successfully deleted task.");
            }
            case UPDATE -> {
                final UpdateAction updateAction = askUpdateAction();

                switch (updateAction) {
                    case CANCEL -> print("Okay, cancelled.");
                    case NAME -> {
                        final int taskIndex = retrieveTask();

                        print("What would you like to RENAME this task to?");
                        final String name = retrieveText();

                        m_taskService.updateTaskName(taskIndex, name);
                        print("Successfully updated the name to " + name + ".");
                    }
                    case DESCRIPTION -> {
                        final int taskIndex = retrieveTask();

                        print("What would you like to set the DESCRIPTION of this task to?");
                        final String description = retrieveText();

                        m_taskService.updateTaskDescription(taskIndex, description);
                        print("Successfully updated the description to " + description + ".");
                    }
                    case DUE_DATE -> {
                        final int taskIndex = retrieveTask();

                        print("What would you like to set the DUE DATE of this task to?");
                        final String dueDate = retrieveText();

                        m_taskService.updateTaskDueDate(taskIndex, dueDate);
                        print("Successfully updated the due date to " + dueDate + ".");
                    }
                }
            }
        }
    }

    @Override
    public int retrieveTask() {
        print("Enter the number of the task you wish to select\n" + getTasksList());

        boolean validTask = false;

        int task = -1;

        while (!validTask) {
            final int selection = retrieveInt();

            if (m_taskService.taskExists(selection)) {
                validTask = true;
                task = selection;
            }
        }

        return task;
    }

    @Override
    public int retrieveInt() {
        boolean validResponse = false;

        int selection = -1;

        while (!validResponse) {
            try {
                selection = m_scanner.nextInt();
            } catch (final Exception p_e) {
                print("Please try again,");
                continue;
            }
            validResponse = true;
        }
        return selection;
    }

    @Override
    public String retrieveText() {
        boolean validResponse = false;
        String response = null;

        while (!validResponse) {
            final String scannerResponse = m_scanner.nextLine();

            if (scannerResponse.isEmpty()) {
                continue;
            }

            response = scannerResponse;
            validResponse = true;
        }
        return response;
    }

    @Override
    public String showTasksFormatted() {
        final StringBuilder stringBuilder = new StringBuilder();

        final String separator = "-----------------";

        stringBuilder.append(separator);

        for (final Task task : m_taskService.getTasks()) {
            stringBuilder.append("\n|\n");
            stringBuilder.append("|\t").append("NAME: ").append(task.getName()).append("\n");
            stringBuilder.append("|\t").append("DESCRIPTION: ").append(task.getDescription()).append("\n");
            stringBuilder.append("|\t").append("DUE DATE: ").append(task.getDueDate()).append("\n");
            stringBuilder.append("|\n");
            stringBuilder.append(separator);
        }

        return stringBuilder.toString();
    }

    private String getTasksList() {
        final StringBuilder stringBuilder = new StringBuilder();

        int index = 0;

        for (final Task task : m_taskService.getTasks()) {
            stringBuilder.append(index).append(": ").append(task.getName()).append("\n");
            index++;
        }

        return stringBuilder.toString();
    }

    private void print(final String p_string) {
        System.out.println("(Todo Tracker) " + p_string);
    }
}
