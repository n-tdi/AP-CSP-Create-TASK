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

            try {
                action = Action.valueOf(actionResponse);
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
            final String actionResponse = m_scanner.nextLine();

            try {
                updateAction = UpdateAction.valueOf(actionResponse);
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
            case EXIT -> System.exit(0);
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
                        retrieveText();
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
            final int selection = m_scanner.nextInt();

            if (m_taskService.taskExists(selection)) {
                validTask = true;
                task = selection;
            }
        }

        return task;
    }

    @Override
    public String retrieveText() {
        return m_scanner.nextLine();
    }

    @Override
    public String showTasksFormatted() {
        final StringBuilder stringBuilder = new StringBuilder();

        final String separator = "-----------------";

        stringBuilder.append(separator).append("\n|\n");

        for (final Task task : m_taskService.getTasks()) {
            stringBuilder.append("|\t").append("NAME: ").append(task.getName()).append("\n");
            stringBuilder.append("|\t").append("DESCRIPTION: ").append(task.getDescription()).append("\n");
            stringBuilder.append("|\t").append("DUE DATE: ").append(task.getDueDate()).append("\n");
            stringBuilder.append("|\n");
            stringBuilder.append(separator).append("\n");
            stringBuilder.append("|\n");
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
