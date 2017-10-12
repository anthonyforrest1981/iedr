package pl.nask.crs.secondarymarket.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.secondarymarket.BuyRequestNotification;
import pl.nask.crs.secondarymarket.BuyRequestNotificationType;

public interface BuyRequestNotificationDAO extends GenericDAO<BuyRequestNotification, Long> {

    boolean notificationSent(long buyRequestId, int notificationPeriod, BuyRequestNotificationType notificationType);

}
