package br.dev.rplus.manager;

import br.dev.rplus.model.ScheduledTask;
import br.dev.rplus.util.DatabaseManager;

public class TaskRunner implements Runnable {
    private final ScheduledTask task;
    private static final String DB_URL = "jdbc:sqlite:fake.db";

    public TaskRunner(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println("Executando a tarefa: " + task.toString());

        DatabaseManager.executeSql(DB_URL, task.getScript());
    }
}