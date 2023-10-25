package world.ntdi.todotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import world.ntdi.todotracker.service.ConsoleService;
import world.ntdi.todotracker.util.TodoRunnable;

@SpringBootApplication
public class TodoTrackerApplication {

	public static void main(String[] args) {
		final ApplicationContext applicationContext = SpringApplication.run(TodoTrackerApplication.class, args);

		final ConsoleService consoleService = applicationContext.getBean(ConsoleService.class);

		final TodoRunnable todoRunnable = new TodoRunnable() {
			@Override
			public void run() {
				final ConsoleService.Action action = consoleService.askAction();

				consoleService.respondToAction(action);
			}
		}.runTaskTimer(0, 500);
	}

}
