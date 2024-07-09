package br.dev.rplus.model;

import java.util.Objects;

public class ScheduledTask implements Comparable<ScheduledTask> {
    private int taskCode;
    private String script;
    private int priority;
    private String cronScript;

    public ScheduledTask(int taskCode, String script, int priority, String cronScript) {
        this.taskCode = taskCode;
        this.script = script;
        this.priority = priority;
        this.cronScript = cronScript;
    }

    public int getTaskCode() {
        return taskCode;
    }

    public String getScript() {
        return script;
    }

    public int getPriority() {
        return priority;
    }

    public String getCronScript() {
        return cronScript;
    }

    public void setCronScript(String cronScript) {
        this.cronScript = cronScript;
    }

    public boolean isScheduled() {
        return getCronScript() != null && !getCronScript().isEmpty() && !Objects.equals(getCronScript(), "");
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return Integer.compare(this.priority, o.priority);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ScheduledTask: {")
                .append("taskCode: ").append(taskCode)
                .append(", script: \'").append(script).append('\'')
                .append(", priority: ").append(priority)
                .append(", cronScript: \'").append(cronScript).append('\'')
                .append('}');
        return sb.toString();
    }
}