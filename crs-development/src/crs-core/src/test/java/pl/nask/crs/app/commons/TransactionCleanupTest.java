package pl.nask.crs.app.commons;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
public class TransactionCleanupTest extends AbstractTransactionalTestNGSpringContextTests {

    static final long TICKET_ID = 259927;

    @Resource
    PaymentAppService paymentAppService;

    @Resource
    CommonAppService commonAppService;

    @Resource
    TicketAppService ticketAppService;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ApplicationConfig applicationConfiguration;

    @Resource
    TicketDAO ticketDao;

    @Resource
    AuthenticationService authenticationService;

    private AuthenticatedUser user;

    @BeforeClass
    public void setUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @BeforeMethod
    public void removeAllTickets() {
        int ticketExpirationPeriod = applicationConfiguration.getTicketExpirationPeriod();
        final TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTo(DateUtils.getCurrDate(-ticketExpirationPeriod));
        SearchResult<Ticket> tickets = ticketDao.find(criteria);
        for (Ticket t : tickets.getResults()) {
            if (t.getId() != TICKET_ID) {
                ticketDao.deleteById(t.getId());
            }
        }
    }

    @Test
    public void testCleanupTicketsAndTransactionsNonExpiredReservation() throws Exception {
        Date creationDate = new Date();
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", "authcode", "pasref", false));
        long reservationId = reservationDAO.createReservation(new Reservation(-1, "Test1-IEDR", "testDomain1.ie", 12,
                creationDate, new ProductPriceWithVat(Period.fromYears(1), 13, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", creationDate, MoneyUtils.getScaledVatValue(0.19))),
                true, false, null, null, transactionId, OperationType.RENEWAL, PaymentMethod.CC, null, null, null, false));

        doTransactionCleanup();

        assertNotNull(transactionDAO.get(transactionId));
        assertNotNull(reservationDAO.get(reservationId));
    }

    @Test
    public void testCleanupTicketsAndTransactionsExpiredReservation() throws Exception {
        int reservationExpiration = applicationConfiguration.getReservationExpirationPeriod();
        Date creationDate = DateUtils.getCurrDate(-1 * reservationExpiration);
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", "authcode", "pasref", false));
        long reservationId = reservationDAO.createReservation(new Reservation(-1, "Test1-IEDR", "testDomain1.ie", 12,
                creationDate, new ProductPriceWithVat(Period.fromYears(1), 13, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", creationDate, MoneyUtils.getScaledVatValue(0.19))),
                true, false, null, null, transactionId, OperationType.RENEWAL, PaymentMethod.CC, null, null, null,
                false));

        doTransactionCleanup();

        assertNull(transactionDAO.get(transactionId));
        assertNull(reservationDAO.get(reservationId));
    }

    @Test
    public void testCleanupTicketsAndTransactionsExpiredTicketReservation() throws Exception {
        Ticket t = ticketDao.get(TICKET_ID);
        int ticketExpiration = applicationConfiguration.getTicketExpirationPeriod();
        Date ticketCreationDate = DateUtils.getCurrDate(-1 * ticketExpiration);

        Ticket newTicket = new Ticket(-1, t.getOperation(), t.getAdminStatus(), ticketCreationDate, t.getTechStatus(),
                ticketCreationDate, t.getRequestersRemark(), t.getHostmastersRemark(), t.getCreator(),
                ticketCreationDate, ticketCreationDate, null, t.isClikPaid(), false, t.getDomainPeriod(), null,
                false, t.getFinancialStatus(), ticketCreationDate, CustomerStatus.NEW, new Date());
        long newTicketId = ticketDao.createTicket(newTicket);

        int reservationExpiration = applicationConfiguration.getReservationExpirationPeriod();
        Date reservationCreationDate = DateUtils.getCurrDate(-1 * reservationExpiration);
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", "authcode", "pasref", false));
        long reservationId = reservationDAO.createReservation(new Reservation(-1, "APITEST-IEDR",
                "createCCDomainTechPassed.ie", 12, reservationCreationDate, new ProductPriceWithVat(Period.fromYears(1),
                13, MoneyUtils.getRoundedAndScaledValue(65), new Vat(1, "A", reservationCreationDate,
                MoneyUtils.getScaledVatValue(0.19))), true, false, null, newTicketId, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC, null, null, null, true));

        doTicketCleanup();
        doTransactionCleanup();

        assertNull(transactionDAO.get(transactionId));
        assertNull(reservationDAO.get(reservationId));
        assertNull(ticketDao.get(newTicketId));
    }

    private void doTicketCleanup() throws Exception {
        for (Ticket ticket : ticketAppService.findTicketsToCleanup(user)) {
            commonAppService.cleanupTicket(user, TestOpInfo.DEFAULT, ticket.getId());
        }
    }

    private void doTransactionCleanup() throws Exception {
        for (Transaction transaction : paymentAppService.findTransactionsToCleanup(user)) {
            paymentAppService.cleanupTransaction(user, transaction);
        }
    }

}
