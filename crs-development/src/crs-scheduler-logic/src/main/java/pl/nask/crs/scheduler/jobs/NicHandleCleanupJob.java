package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class NicHandleCleanupJob extends AbstractJob {
    private final NicHandleAppService service;

    public NicHandleCleanupJob(NicHandleAppService service) {
        super();
        this.service = service;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        service.removeDeletedNichandles(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
