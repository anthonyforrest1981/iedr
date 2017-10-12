package pl.nask.crs.dnscheck.dao;

import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DnsNotificationDAO extends GenericDAO<DnsNotification, Integer> {

    List<DnsNotification> getByDomainName(String domainName);

    List<DnsNotification> getAll();

    DnsNotificationDate getNotificationDate(String nicHandleId);

    void createNotificatioDate(DnsNotificationDate notificationDate);

    void update(DnsNotificationDate notificationDate);

    /**
     * Deletes all notifications for given ticket except for those about passed nameservers.
     * @param ticketNumber ticket number for which notifications should be deleted.
     * @param nsNames Set of nameservers for which notifications should be kept.
     */
    void removeObsoleteNotificationsForTicket(long ticketNumber, Set<String> nsNames);

    /**
     * Deletes all notifications for given domain except for those about passed nameservers.
     * @param domainName domain name for which notifications should be deleted.
     * @param nsNames Set of nameservers for which notifications should be kept.
     */
    void removeObsoleteNotificationsForDomain(String domainName, Set<String> nsNames);

}
