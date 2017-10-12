package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface DnsNotificationTestMethods {

    void sendNotifications() throws AccessDeniedException;

}
