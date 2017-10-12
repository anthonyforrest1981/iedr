package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class ResetPassCleanupJob extends AbstractJob {

    private final NicHandleAppService service;

    public ResetPassCleanupJob(NicHandleAppService service) {
        this.service = service;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws Exception {
        service.cleanupResetPassword(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
