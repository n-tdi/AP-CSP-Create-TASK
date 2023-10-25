package world.ntdi.todotracker.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class TodoRunnable implements Runnable {

    private ScheduledExecutorService service;

    public void runTaskLater(long delay) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(this, delay, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    public Thread runTaskAsync() {
        final Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    public TodoRunnable runTaskTimer(long delay, long period) {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
        return this;
    }

    public void cancel() {
        service.shutdownNow();
    }
}
