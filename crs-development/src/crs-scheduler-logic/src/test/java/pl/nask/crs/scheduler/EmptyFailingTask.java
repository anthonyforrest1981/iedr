package pl.nask.crs.scheduler;

import pl.nask.crs.scheduler.jobs.AbstractJob;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class EmptyFailingTask extends AbstractJob {

    @Override
    public String getJobName() {
        return "emptyFailingTask";
    }

    @Override
    public void runJob(AuthenticatedUser user) throws Exception {
        throw new Exception("Failing!!!!");
    }
}
