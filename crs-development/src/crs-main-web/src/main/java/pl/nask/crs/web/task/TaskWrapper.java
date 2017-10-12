package pl.nask.crs.web.task;

import pl.nask.crs.scheduler.JobConfig;

/**
 * @author Artur Kielak
 */
public class TaskWrapper {
    private int id;
    private String name;
    private String schedule;
    private String runningNicHandle;

    public TaskWrapper() {}

    public TaskWrapper(JobConfig jobConfig) {
        this.id = jobConfig.getId();
        this.name = jobConfig.getName();
        this.schedule = jobConfig.getSchedulePattern();
        this.runningNicHandle = jobConfig.getRunningNicHandle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRunningNicHandle() {
        return runningNicHandle;
    }

    public void setRunningNicHandle(String runningNicHandle) {
        this.runningNicHandle = runningNicHandle;
    }
}
