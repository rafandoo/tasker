package br.dev.rplus.manager;

import br.dev.rplus.model.ScheduledTask;

import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;

public class TaskProcessor implements Runnable {

    private final PriorityQueue<ScheduledTask> taskQueue;
    private final ExecutorService executorService;

    public TaskProcessor(PriorityQueue<ScheduledTask> taskQueue, ExecutorService executorService) {
        this.taskQueue = taskQueue;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        while (true) {
            ScheduledTask task = null;
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                task = taskQueue.poll();
            }
            if (task != null) {
                executorService.submit(new TaskRunner(task));
            }
        }
    }
}