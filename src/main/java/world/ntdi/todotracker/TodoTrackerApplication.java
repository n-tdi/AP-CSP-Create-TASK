package world.ntdi.todotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import world.ntdi.todotracker.service.IConsoleService;

@SpringBootApplication
public class TodoTrackerApplication {

	public static void main(String[] args) {
		final ApplicationContext applicationContext = SpringApplication.run(TodoTrackerApplication.class, args);

		final IConsoleService iConsoleService = applicationContext.getBean(IConsoleService.class);

		while (true) {
			final IConsoleService.Action action = iConsoleService.askAction();

			iConsoleService.respondToAction(action);
		}
	}

}
