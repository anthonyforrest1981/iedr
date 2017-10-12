package pl.nask.crs.domains;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainNotification {

    private String domainName;

    private int notificationPeriod;

    private Date expirationDate;

    public DomainNotification() {}

    public DomainNotification(String domainName, int notificationPeriod, Date expirationDate) {
        this.domainName = domainName;
        this.notificationPeriod = notificationPeriod;
        this.expirationDate = expirationDate;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getNotificationPeriod() {
        return notificationPeriod;
    }

    public void setNotificationPeriod(int notificationPeriod) {
        this.notificationPeriod = notificationPeriod;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return String.format("DomainNotification[domainName: %s, period: %s, expiration; %s]", domainName,
                notificationPeriod, expirationDate);
    }
}
