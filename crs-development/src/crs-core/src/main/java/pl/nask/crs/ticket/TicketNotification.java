package pl.nask.crs.ticket;

public class TicketNotification {

    private long ticketId;

    private int notificationPeriod;

    public TicketNotification() {}

    public TicketNotification(long ticketId, int notificationPeriod) {
        this.ticketId = ticketId;
        this.notificationPeriod = notificationPeriod;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public int getNotificationPeriod() {
        return notificationPeriod;
    }

    public void setNotificationPeriod(int notificationPeriod) {
        this.notificationPeriod = notificationPeriod;
    }

    @Override
    public String toString() {
        return String.format("TicketNotification[ticket: %s, period: %s]", ticketId, notificationPeriod);
    }
}
