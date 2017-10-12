package pl.nask.crs.app.dnscheck;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DnsNotificationAppService {

    void sendNotifications(AuthenticatedUser user) throws AccessDeniedException;

}
