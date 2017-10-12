package pl.nask.crs.secondarymarket.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestNotification;
import pl.nask.crs.secondarymarket.BuyRequestNotificationType;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BuyRequestNotificationDAOTest extends AbstractContextAwareTest {

    @Resource
    BuyRequestNotificationDAO buyRequestNotificationDAO;
    @Resource
    BuyRequestDAO buyRequestDAO;

    @Test
    public void testNotificationCreation() {
        BuyRequest buyRequest = buyRequestDAO.get(21L);
        int notificationPeriod = 10;
        BuyRequestNotificationType type = BuyRequestNotificationType.REQUEST;
        assertFalse(buyRequestNotificationDAO.notificationSent(buyRequest.getId(), notificationPeriod, type));
        buyRequestNotificationDAO.create(new BuyRequestNotification(buyRequest, notificationPeriod, type));
        assertTrue(buyRequestNotificationDAO.notificationSent(buyRequest.getId(), notificationPeriod, type));
    }

}
