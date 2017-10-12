package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RenewalNotificationJob extends AbstractJob {

    private final DomainAppService domainAppService;

    public RenewalNotificationJob(DomainAppService domainAppService) {
        this.domainAppService = domainAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        domainAppService.runNotificationProcess(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
