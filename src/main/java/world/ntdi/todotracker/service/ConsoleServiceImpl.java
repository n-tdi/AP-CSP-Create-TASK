package world.ntdi.todotracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import world.ntdi.todotracker.model.Task;

import java.util.Scanner;

@Service
@AllArgsConstructor
public class ConsoleServiceImpl implements IConsoleService {
    private final ITaskService m_iTaskService;
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
        return null;
    }

    @Override
    public void respondToAction(final Action p_action) {

    }

    @Override
    public Task retrieveTask() {
        return null;
    }

    @Override
    public String retrieveText() {
        return null;
    }



    private void print(final String p_string) {
        System.out.println("(Todo Tracker) " + p_string);
    }
}
