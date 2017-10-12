package pl.nask.crs.dnscheck;

import java.util.Date;

public class DnsNotification {

    private int id;
    private String domainName;
    private Long ticketNumber;
    private String nsName;
    private String errorMessage;
    private Date timeOfCheck;

    public DnsNotification() {}

    public DnsNotification(String domainName, Long ticketNumber, String nsName, Date timeOfCheck, String errorMessage) {
        this.domainName = domainName;
        this.ticketNumber = ticketNumber;
        this.nsName = nsName;
        this.errorMessage = errorMessage;
        this.timeOfCheck = timeOfCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getNsName() {
        return nsName;
    }

    public void setNsName(String nsName) {
        this.nsName = nsName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getTimeOfCheck() {
        return timeOfCheck;
    }

    public void setTimeOfCheck(final Date timeOfCheck) {
        this.timeOfCheck = timeOfCheck;
    }

    @Override
    public String toString() {
        return String.format("DnsNotification[id: %s, domainName: %s, ticket: %s, nsName: %s]", id, domainName,
                ticketNumber == null ? "null" : ticketNumber, nsName);
    }
}
