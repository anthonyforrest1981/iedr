package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.domains.PushQSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class PushQ extends AbstractJob {
    private final PushQSupportService pushQSupportService;

    public PushQ(PushQSupportService pushQSupportService) {
        this.pushQSupportService = pushQSupportService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        pushQSupportService.pushQ(user, new OpInfo(user));
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
