package pl.nask.crs.dnscheck.dao.ibatis;

import java.util.*;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;

public class DnsNotificationIbatisDAO extends GenericIBatisDAO<DnsNotification, Integer> implements DnsNotificationDAO {

    public DnsNotificationIbatisDAO() {
        setCreateQueryId("notification.insertNotification");
        setGetQueryId("notification.getNotificationById");
        setDeleteQueryId("notification.deleteById");
    }

    @Override
    public List<DnsNotification> getByDomainName(String domainName) {
        return performQueryForList("notification.getNotificationByDomainName", domainName);
    }

    @Override
    public List<DnsNotification> getAll() {
        return performQueryForList("notification.getAll");
    }

    @Override
    public DnsNotificationDate getNotificationDate(String nicHandleId) {
        return performQueryForObject("notification.getNotificationDate", nicHandleId);
    }

    @Override
    public void createNotificatioDate(DnsNotificationDate notificationDate) {
        performInsert("notification.createNotificationDate", notificationDate);
    }

    @Override
    public void update(DnsNotificationDate notificationDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("nicHandleId", notificationDate.getNicHandle());
        params.put("date", notificationDate.getNextNotificationDate());
        performUpdate("notification.updateNotificationDate", params);
    }

    @Override
    public void removeObsoleteNotificationsForTicket(long ticketNumber, Set<String> nsNames) {
        Map<String, Object> params = new HashMap<>();
        params.put("ticketNumber", ticketNumber);
        params.put("nsNames", new ArrayList<>(nsNames));
        performDelete("notification.removeObsoleteNotificationsForTicket", params);
    }

    @Override
    public void removeObsoleteNotificationsForDomain(String domainName, Set<String> nsNames) {
        Map<String, Object> params = new HashMap<>();
        params.put("domainName", domainName);
        params.put("nsNames", new ArrayList<>(nsNames));
        performDelete("notification.removeObsoleteNotificationsForDomain", params);
    }

}
