package br.dev.rplus.manager;

import br.dev.rplus.model.ScheduledTask;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager {

    private final ScheduledExecutorService scheduler;

    public TaskManager(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public ScheduledTask processRequest(JSONObject request) {
        try {
            return new ScheduledTask(
                    request.getInt("task_code"),
                    request.getString("script"),
                    request.has("priority") ? request.getInt("priority") : 0,
                    request.has("cron_script") ? request.getString("cron_script") : null
            );
        } catch (Exception e) {
            return null;
        }
    }

    public void scheduleTask(ScheduledTask task) {
        Runnable taskRunnable = new TaskRunner(task);

        long initialDelay = computeInitialDelay(task.getCronScript());

        if (initialDelay > 0) {
            this.scheduler.schedule(taskRunnable, initialDelay, TimeUnit.MILLISECONDS);
        } else {
            System.out.println("A data/hora especificada para a tarefa já passou.");
        }
    }

    private long computeInitialDelay(String cronScript) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime targetDateTime = LocalDateTime.parse(cronScript, formatter);
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(now, targetDateTime);
        return duration.toMillis();
    }
}
