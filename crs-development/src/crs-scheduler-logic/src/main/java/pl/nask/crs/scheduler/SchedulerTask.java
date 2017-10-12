package pl.nask.crs.scheduler;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import pl.nask.crs.commons.log4j.EventObserver;
import pl.nask.crs.scheduler.jobs.AbstractJob;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SchedulerTask extends Task {
    private Logger log = Logger.getLogger(getClass());

    private final AbstractJob jobImplementation;

    private final String taskName;

    private final int jobConfigId;

    private final AuthenticatedUser user;

    public SchedulerTask(JobConfig jobConfig, AuthenticatedUser user, AbstractJob jobImplementation) {
        this.jobConfigId = jobConfig.getId();
        this.taskName = jobConfig.getName();
        this.user = user;
        this.jobImplementation = jobImplementation;
    }

    @Override
    public final void execute(TaskExecutionContext arg0) throws RuntimeException {
        EventObserver observer = new EventObserver(Priority.ERROR);
        try {
            jobImplementation.run(observer, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (observer.eventCount() > 0) {
            arg0.setStatusMessage("ERROR");
        }
    }

    public int getJobId() {
        return jobConfigId;
    }

    public String getJobName() {
        return taskName;
    }

    @Override
    public boolean supportsStatusTracking() {
        return true;
    }
}
