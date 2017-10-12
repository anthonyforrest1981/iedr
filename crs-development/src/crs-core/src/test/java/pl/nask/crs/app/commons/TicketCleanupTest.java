package pl.nask.crs.app.commons;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.invoicing.PaymentSenderTestImpl;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;
import pl.nask.crs.ticket.services.impl.TransferTicketEmailParameters;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
@SuppressWarnings("nullness")
public class TicketCleanupTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TicketDAO ticketDao;

    @Resource
    private CommonAppService commonAppService;

    @Resource
    private TicketService ticketService;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    PaymentSearchService paymentSearchService;

    @Mocked
    private EmailTemplateSenderImpl sender;

    @Mocked
    PaymentSenderTestImpl paymentSender;

    private long xferTicketId = 259924;
    private AuthenticatedUser user;

    @BeforeMethod
    public void setUpUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void notificationEmail103ShouldBeSentWhenRemovingExpiredTransferTicket() throws Exception {
        // having a not canceled ticket
        ticketService.updateCustomerStatus(xferTicketId, CustomerStatus.NEW, TestOpInfo.DEFAULT);
        ticketService.updateAdminStatus(xferTicketId, AdminStatus.NEW, TestOpInfo.DEFAULT);

        // expect
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(),
                        withInstanceOf(TicketEmailParameters.class));
                minTimes = 1;
                sender.sendEmail(EmailTemplateNamesEnum.TRANSFER_REQUEST_EXPIRED.getId(),
                        withInstanceOf(TransferTicketEmailParameters.class));
                minTimes = 1;
            }
        };

        // when the ticket is cleaned
        commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, xferTicketId);
    }

    @Test
    public void notificationEmail103ShouldBeSentWhenRemovingCustomerCanceledTicket() throws Exception {
        // having a cancelled ticket
        ticketService.updateCustomerStatus(xferTicketId, CustomerStatus.CANCELLED, TestOpInfo.DEFAULT);

        // expect
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(),
                        withInstanceOf(TicketEmailParameters.class));
                minTimes = 1;
            }
        };

        // when the ticket is cleaned
        commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, xferTicketId);
    }

    @Test
    public void notificationEmail103ShouldBeSentWhenRemovingAdminCanceledTicet() throws Exception {
        // having a cancelled ticket
        ticketService.updateAdminStatus(xferTicketId, AdminStatus.CANCELLED, TestOpInfo.DEFAULT);

        // expect
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(),
                        withInstanceOf(TicketEmailParameters.class));
                minTimes = 1;
            }
        };

        // when the ticket is cleaned
        commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, xferTicketId);
    }

    @Test
    public void testTicketCleanupWithRealexFailureAndExpiredTransaction() throws Exception {
        setupPaymentSenderFailure();
        long ticketId = 259927L;
        assertNotNull(ticketDao.get(ticketId));
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        assertNotNull(transactionDAO.get(reservation.getTransactionId()));

        commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, ticketId);

        assertNull(transactionDAO.get(reservation.getTransactionId()));
        assertNull(ticketDao.get(ticketId));
    }

    @Test
    public void testTicketCleanupWithRealexFailureAndValidTransaction() throws Exception {
        setupPaymentSenderFailure();
        Ticket t = ticketDao.get(259927L);
        Date creationDate = new Date();

        // Create the ticket as cancelled, so that TicketAndTransactionCleanupJob tries to clean it.
        Ticket newTicket = new Ticket(-1, t.getOperation(), AdminStatus.CANCELLED, creationDate, t.getTechStatus(),
                creationDate, t.getRequestersRemark(), t.getHostmastersRemark(), t.getCreator(), creationDate,
                creationDate, null, t.isClikPaid(), false, t.getDomainPeriod(), null, false, t.getFinancialStatus(),
                creationDate, CustomerStatus.NEW, new Date());
        long newTicketId = ticketDao.createTicket(newTicket);
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", "authcode", "pasref", false));
        long reservationId = reservationDAO.createReservation(new Reservation(-1, "Test1-IEDR", "testDomain1.ie", 12,
                creationDate, new ProductPriceWithVat(Period.fromYears(1), 13, MoneyUtils.getRoundedAndScaledValue(65),
                new Vat(1, "A", creationDate, MoneyUtils.getScaledVatValue(0.19))), true, false, null, newTicketId,
                transactionId, OperationType.RENEWAL, PaymentMethod.CC, null, null, null, false));

        assertNotNull(ticketDao.get(newTicketId));
        Reservation reservation = paymentSearchService.getReservationForTicket(newTicketId);
        assertEquals(reservation.getId(), reservationId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertEquals(transaction.getId(), transactionId);

        try {
            commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, newTicketId);
            fail("Cleaning up should fail with a CardPaymentException");
        } catch (CardPaymentException e) {
            // expected
        }

        assertNotNull(reservationDAO.get(reservationId));
        assertNotNull(transactionDAO.get(transactionId));
        assertNotNull(ticketDao.get(newTicketId));
    }

    private void setupPaymentSenderFailure() {
        new NonStrictExpectations() {
            {
                paymentSender.send(anyString);
                result = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                        + "<response timestamp=\"20120319115651\">\n"
                        + "<result>508</result>\n"
                        + "<message>Error processing payment due due to bad data or bad format of payment request</message>\n"
                        + "<authcode>null</authcode>\n"
                        + "<pasref>null</pasref>\n"
                        + "<bank>null</bank>\n"
                        + "<country>null</country>\n"
                        + "</response>";
            }
        };
    }

}
