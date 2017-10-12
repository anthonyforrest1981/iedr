package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class UpdaterJob extends AbstractJob {

    private SchedulerCron cron;

    public void setCron(SchedulerCron cron) {
        this.cron = cron;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException, NicHandleNotFoundException {
        cron.forceReload();
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
