package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.dnscheck.DnsNotificationAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DnsCheckFailureNotificationJob extends AbstractJob {

    private DnsNotificationAppService dnsNotificationAppService;

    public DnsCheckFailureNotificationJob(DnsNotificationAppService dnsNotificationAppService) {
        this.dnsNotificationAppService = dnsNotificationAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        dnsNotificationAppService.sendNotifications(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
