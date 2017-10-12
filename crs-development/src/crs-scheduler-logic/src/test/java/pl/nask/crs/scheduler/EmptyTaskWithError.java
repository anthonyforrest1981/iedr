package pl.nask.crs.scheduler;

import org.apache.log4j.Logger;

import pl.nask.crs.scheduler.jobs.AbstractJob;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class EmptyTaskWithError extends AbstractJob {

    @Override
    public String getJobName() {
        return "emptyTaskWithError";
    }

    @Override
    public void runJob(AuthenticatedUser user) throws Exception {
        Logger.getLogger(EmptyTaskWithError.class).error("Error message");
    }
}
