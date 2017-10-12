package pl.nask.crs.dnscheck;

import java.util.Set;

public interface DnsNotificationService {

    void createNotification(DnsNotification dnsNotification);

    void sendNotifications();

    void removeObsoleteNotifications(String domainName, Long ticketNumber, Set<String> currentFailedNameservers);

}
