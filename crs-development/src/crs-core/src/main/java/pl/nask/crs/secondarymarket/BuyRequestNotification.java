package pl.nask.crs.secondarymarket;

public class BuyRequestNotification {

    private BuyRequest buyRequest;
    private int notificationPeriod;
    private BuyRequestNotificationType notificationType;

    public BuyRequestNotification(BuyRequest buyRequest, int notificationPeriod,
            BuyRequestNotificationType notificationType) {
        this.buyRequest = buyRequest;
        this.notificationPeriod = notificationPeriod;
        this.notificationType = notificationType;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public int getNotificationPeriod() {
        return notificationPeriod;
    }

    public BuyRequestNotificationType getNotificationType() {
        return notificationType;
    }

}
