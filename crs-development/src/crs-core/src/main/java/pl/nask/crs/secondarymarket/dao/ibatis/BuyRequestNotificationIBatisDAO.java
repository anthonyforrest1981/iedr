package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.secondarymarket.BuyRequestNotification;
import pl.nask.crs.secondarymarket.BuyRequestNotificationType;
import pl.nask.crs.secondarymarket.dao.BuyRequestNotificationDAO;

public class BuyRequestNotificationIBatisDAO extends GenericIBatisDAO<BuyRequestNotification, Long> implements
        BuyRequestNotificationDAO {

    public BuyRequestNotificationIBatisDAO() {
        setCreateQueryId("secondary-market-buy-request-notification.create");
    }

    @Override
    public boolean notificationSent(long buyRequestId, int notificationPeriod,
            BuyRequestNotificationType notificationType) {
        Map<String, Object> params = new HashMap<>();
        params.put("buyRequestId", buyRequestId);
        params.put("notificationPeriod", notificationPeriod);
        params.put("notificationType", notificationType);
        return performQueryForObject("secondary-market-buy-request-notification.notificationSent", params);
    }
}
