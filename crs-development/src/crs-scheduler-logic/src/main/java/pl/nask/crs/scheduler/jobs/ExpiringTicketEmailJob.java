package pl.nask.crs.scheduler.jobs;

import org.apache.log4j.Logger;

import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.secondarymarket.BuyRequestNotification;
import pl.nask.crs.secondarymarket.BuyRequestNotificationType;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.TicketNotification;

public class ExpiringTicketEmailJob extends AbstractJob {

    private static final Logger LOGGER = Logger.getLogger(ExpiringTicketEmailJob.class);

    private final TicketAppService ticketAppService;
    private final SecondaryMarketAppService secondaryMarketAppService;

    public ExpiringTicketEmailJob(TicketAppService ticketAppService,
            SecondaryMarketAppService secondaryMarketAppService) {
        this.ticketAppService = ticketAppService;
        this.secondaryMarketAppService = secondaryMarketAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        boolean failure = false;
        try {
            sendTicketNotifications(user);
        } catch (Exception e) {
            failure = true;
            LOGGER.warn("Sending ticket notifications failed", e);
        }
        try {
            sendBuyRequestNotifications(user, BuyRequestNotificationType.REQUEST);
        } catch (Exception e) {
            failure = true;
            LOGGER.warn("Sending request notifications failed", e);
        }
        try {
            sendBuyRequestNotifications(user, BuyRequestNotificationType.AUTHCODE);
        } catch (Exception e) {
            failure = true;
            LOGGER.warn("Sending authcode notifications failed", e);
        }
        if (failure) {
            throw new RuntimeException("Job failed");
        }
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

    private void sendTicketNotifications(AuthenticatedUser user) throws AccessDeniedException {
        for (TicketNotification notification : ticketAppService.findTicketNotifications(user)) {
            try {
                ticketAppService.sendTicketExpirationEmail(user, notification);
            } catch (Exception e) {
                LOGGER.warn(String.format("Sending notification for ticketId=%s and notificationPeriod=%s failed",
                        notification.getTicketId(), notification.getNotificationPeriod()), e);
            }
        }
    }

    private void sendBuyRequestNotifications(AuthenticatedUser user, BuyRequestNotificationType notificationType)
            throws AccessDeniedException {
        for (BuyRequestNotification notification : secondaryMarketAppService.findBuyRequestNotifications(user,
                notificationType)) {
            try {
                secondaryMarketAppService.sendBuyRequestNotification(user, notification);
            } catch (Exception e) {
                LOGGER.warn(String.format("Sending notification for buyRequestId=%s and notificationPeriod=%s failed",
                        notification.getBuyRequest().getId(), notification.getNotificationPeriod()), e);
            }
        }
    }

}
