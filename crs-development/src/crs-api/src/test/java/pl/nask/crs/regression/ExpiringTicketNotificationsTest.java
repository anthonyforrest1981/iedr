package pl.nask.crs.regression;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@SuppressWarnings("nullness")
public class ExpiringTicketNotificationsTest extends AbstractEmailsTest {

    static final Map<DomainOperationType, Long> ticketIdByType = new HashMap<>();
    {
        ticketIdByType.put(DomainOperationType.REG, new Long(259927));
        ticketIdByType.put(DomainOperationType.MOD, new Long(259918));
        ticketIdByType.put(DomainOperationType.XFER, new Long(259924));
    }

    @Autowired
    TicketAppService ticketAppService;

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    TicketDAO ticketDAO;

    @Autowired
    AuthenticationService authenticationService;

    private AuthenticatedUser user;

    @BeforeMethod
    public void setUpUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void sendingRegistrationTicketNotification() throws Exception {
        int period = 10;
        long ticketId = createTicket(period, DomainOperationType.REG);
        assertNull(ticketDAO.getTicketNotification(ticketId, period));
        createExpectations(EmailTemplateNamesEnum.REG_TICKET_EXPIRATION.getId(),
                createPopulatedValues(period, String.format("%s days", period)), 1, 1);
        ticketAppService.sendTicketExpirationEmail(user, new TicketNotification(ticketId, period));
        assertNotNull(ticketDAO.getTicketNotification(ticketId, period));
    }

    @Test
    public void sendingModificationTicketNotification() throws Exception {
        int period = 5;
        long ticketId = createTicket(period, DomainOperationType.MOD);
        assertNull(ticketDAO.getTicketNotification(ticketId, period));
        createExpectations(EmailTemplateNamesEnum.MOD_TICKET_EXPIRATION.getId(),
                createPopulatedValues(period, String.format("%s days", period)), 1, 1);
        ticketAppService.sendTicketExpirationEmail(user, new TicketNotification(ticketId, period));
        assertNotNull(ticketDAO.getTicketNotification(ticketId, period));
    }

    @Test
    public void sendingTransferTicketNotification() throws Exception {
        int period = 1;
        long ticketId = createTicket(period, DomainOperationType.XFER);
        createExpectations(EmailTemplateNamesEnum.XFER_TICKET_EXPIRATION.getId(),
                createPopulatedValues(period, String.format("%s day", period)), 1, 1);
        ticketAppService.sendTicketExpirationEmail(user, new TicketNotification(ticketId, period));
        assertNotNull(ticketDAO.getTicketNotification(ticketId, period));
    }

    @Test
    public void ticketNotificationShouldNotBeSentTwice() throws Exception {
        long ticketId = createTicket(10, DomainOperationType.REG);
        TicketNotification notification = new TicketNotification(ticketId, 10);
        createExpectations(42, new HashMap<ParameterNameEnum, String>(), 1, 1);
        ticketAppService.sendTicketExpirationEmail(user, notification);
        createExpectations(42, new HashMap<ParameterNameEnum, String>(), 0, 0);
        ticketAppService.sendTicketExpirationEmail(user, notification);
    }

    @Test
    public void ticketNotificationShouldBeSentForOneMatchingPeriodOnly() throws Exception {
        long ticket3days = createTicket(3, DomainOperationType.REG);
        long ticket5days = createTicket(5, DomainOperationType.REG);
        long ticket10days = createTicket(10, DomainOperationType.REG);
        List<TicketNotification> notifications = ticketAppService.findTicketNotifications(user);
        assertEquals(notifications.size(), 3);
        checkNotification(notifications.get(0), ticket3days, 3);
        checkNotification(notifications.get(1), ticket5days, 5);
        checkNotification(notifications.get(2), ticket10days, 10);
    }

    @Test
    public void ticketNotificationShouldNotBeSentIfTicketExpired() throws Exception {
        createTicket(0, DomainOperationType.REG);
        List<TicketNotification> notifications = ticketAppService.findTicketNotifications(user);
        assertEquals(notifications.size(), 0);
    }

    @Test
    public void ticketNotificationShouldBeSentIfADayWasSkipped() throws Exception {
        long ticketId = createTicket(6, DomainOperationType.REG);
        List<TicketNotification> notifications = ticketAppService.findTicketNotifications(user);
        assertEquals(notifications.size(), 1);
        checkNotification(notifications.get(0), ticketId, 10);
    }

    private long createTicket(int daysToExpiration, DomainOperationType type) {
        Ticket t = ticketDAO.get(ticketIdByType.get(type));
        int ticketExpiration = applicationConfig.getTicketExpirationPeriod();
        Date ticketCreationDate = DateUtils.getCurrDate(daysToExpiration - ticketExpiration);

        Ticket newTicket = new Ticket(-1, t.getOperation(), t.getAdminStatus(), ticketCreationDate, t.getTechStatus(),
                ticketCreationDate, t.getRequestersRemark(), t.getHostmastersRemark(), t.getCreator(),
                ticketCreationDate, ticketCreationDate, null, t.isClikPaid(), false, t.getDomainPeriod(), null, false,
                t.getFinancialStatus(), ticketCreationDate, CustomerStatus.NEW, ticketCreationDate);
        return ticketDAO.createTicket(newTicket);
    }

    private Map<ParameterNameEnum, String> createPopulatedValues(int daysRemaining, String daysRemainingWithSuffix) {
        Map<ParameterNameEnum, String> populatedValues = new HashMap<>();
        int ticketExpiration = applicationConfig.getTicketExpirationPeriod();
        int daysPassed = ticketExpiration - daysRemaining;
        populatedValues.put(ParameterNameEnum.DAYS_PASSED, Integer.toString(daysPassed));
        populatedValues.put(ParameterNameEnum.DAYS_REMAINING_WITH_DAYS_SUFFIX, daysRemainingWithSuffix);
        return populatedValues;
    }

    private void checkNotification(TicketNotification notification, long expectedTicketId,
            int expectedPeriod) {
        assertEquals(notification.getTicketId(), expectedTicketId);
        assertEquals(notification.getNotificationPeriod(), expectedPeriod);
    }

}
