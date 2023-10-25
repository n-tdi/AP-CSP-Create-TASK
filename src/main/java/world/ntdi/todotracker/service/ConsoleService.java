package world.ntdi.todotracker.service;

import world.ntdi.todotracker.model.Task;

public interface ConsoleService {
    Action askAction();
    UpdateAction askUpdateAction();
    void respondToAction(final Action p_action);
    int retrieveTask();
    int retrieveInt();
    String retrieveText();
    String showTasksFormatted();

    enum Action {
        CREATE, REMOVE, UPDATE, VIEW, EXIT;

        public static String prettyValue() {
            final StringBuilder stringBuilder = new StringBuilder("(");

            final int size = values().length;
            int iteration = 0;

            for (final Action action : values()) {
                stringBuilder.append(action);
                iteration++;

                if (iteration < size) {
                    stringBuilder.append(", ");
                }
            }

            stringBuilder.append(")");

            return stringBuilder.toString();
        }
    }

    enum UpdateAction {
        NAME, DESCRIPTION, DUE_DATE, CANCEL;

        public static String prettyValue() {
            final StringBuilder stringBuilder = new StringBuilder("(");

            final int size = values().length;
            int iteration = 0;

            for (final UpdateAction updateAction : values()) {
                stringBuilder.append(updateAction);
                iteration++;

                if (iteration < size) {
                    stringBuilder.append(", ");
                }
            }

            stringBuilder.append(")");

            return stringBuilder.toString();
        }
    }
}
