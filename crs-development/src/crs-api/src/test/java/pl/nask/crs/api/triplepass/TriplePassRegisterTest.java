package pl.nask.crs.api.triplepass;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.HistoricalTicketDAO;
import pl.nask.crs.ticket.dao.TicketDAO;

import static pl.nask.crs.api.Helper.createBasicCreateRequest;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassRegisterTest extends TriplePassTest {

    @Resource
    DepositDAO depositDAO;

    @Resource
    DomainDAO domainDAO;

    @Resource
    TicketDAO ticketDAO;

    @Resource
    HistoricalTicketDAO historicalTicketDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    DnsNotificationDAO dnsNotificationDAO;

    @Resource
    CommonAppService commonAppService;

    String domainName = "registerdomainfortriplepass.ie";
    AuthenticatedUserVO user;
    AuthenticatedUserVO technicalUser;

    @BeforeMethod
    public void authenticate() throws AuthenticationException, IncorrectUtf8FormatException {
        user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        technicalUser = crsAuthenticationService.authenticate("AAA906-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void checkAdminPassedRequired() throws Exception {
        long ticketId = registerDomainWithAdpPayment(user, domainName);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(AdminStatus.NEW, t.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.NEW, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, t.getFinancialStatus());
    }

    @Test
    public void ticketWithADPPaymentTest() throws Exception {
        long ticketId = registerDomainWithAdpPayment(user, domainName);
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    @Test
    public void ticketWithCCPaymentTest() throws Exception {
        long ticketId = 259930L;
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        assertTicketClosed(ticketId);
        assertDomainCreated("registerCCDomainForTripplePass.ie");
    }

    @Test
    public void charityTest() throws Exception {
        long ticketId = registerCharityDomain();
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    @Test
    public void wrongNameserverTest() throws Exception {
        long ticketId = registerAdpDomainWrongNameserver();
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.STALLED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, t.getFinancialStatus());
    }

    @Test
    public void insufficientDepositFundsTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        long ticketId = registerDomainWithAdpPayment(user, domainName);
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        ticketService.updateFinancialStatus(ticketId, FinancialStatus.NEW, TestOpInfo.DEFAULT);
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_NREG, 1);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.STALLED, t.getFinancialStatus());
        depositService.viewDeposit("SWD2-IEDR");
    }

    @Test
    public void adminCheckFailedTest() throws Exception {
        long ticketId = registerDomainWithAdpPayment(user, domainName);
        ticketService.updateAdminStatus(ticketId, AdminStatus.STALLED, TestOpInfo.DEFAULT);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.NEW, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, t.getFinancialStatus());
    }

    @Test
    public void invalidatedCCTransactionTest() throws Exception {
        long ticketId = 259930L;
        String domainName = "registerCCDomainForTripplePass.ie";

        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);

        ReservationSearchCriteria rCriteria = new ReservationSearchCriteria();
        rCriteria.setDomainName(domainName);
        Reservation reservation = reservationDAO.getReservations(rCriteria, 0, 1, null).getResults().get(0);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        transaction.markInvalidated();
        transactionDAO.update(transaction);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.STALLED, t.getFinancialStatus());
    }

    @Test
    public void initialTechStalledTest() throws Exception {
        long ticketId = registerDomainWithAdpPayment(user, domainName);
        updateStatuses(ticketId, AdminStatus.PASSED, TechStatus.STALLED, null, TestOpInfo.DEFAULT);
        // do the work
        triplePassSupportService.triplePass(technicalUser, ticketId);

        // check the results
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    @Test
    public void initialTechStalledWrongNameserverTest() throws Exception {
        long ticketId = registerAdpDomainWrongNameserver();
        updateStatuses(ticketId, AdminStatus.PASSED, TechStatus.STALLED, null, TestOpInfo.DEFAULT);

        triplePassSupportService.triplePass(technicalUser, ticketId);

        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.STALLED, t.getTechStatus());
    }

    @Test
    public void initialTechStalledCharityTest() throws Exception {
        long ticketId = registerCharityDomain();
        updateStatuses(ticketId, AdminStatus.PASSED, TechStatus.STALLED, null, TestOpInfo.DEFAULT);

        triplePassSupportService.triplePass(technicalUser, ticketId);

        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
    }

    @Test
    public void dnsNotificationTest() throws Exception {
        long ticketId = registerAdpDomainWrongNameserver();
        OpInfo opInfo = new OpInfo(user, "remark");
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, opInfo);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.STALLED, ticket.getTechStatus());
        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        AssertJUnit.assertEquals(2, notifications.size());
        AssertJUnit.assertEquals("bad1." + domainName, notifications.get(0).getNsName());
        AssertJUnit.assertTrue(notifications.get(0).getErrorMessage().endsWith(
                "FATAL message for starts with 'bad', so it's bad\n"));
        AssertJUnit.assertEquals("bad2." + domainName, notifications.get(1).getNsName());
        AssertJUnit.assertTrue(notifications.get(1).getErrorMessage().endsWith(
                "FATAL message for starts with 'bad', so it's bad\n"));
        ticket.getOperation().getNameserversField().get(0).getName().setNewValue("ok1." + domainName);
        ticket.getOperation().getNameserversField().get(1).getName().setNewValue("ok2." + domainName);
        ticket.updateChangeDate();
        long changeId = historicalTicketDAO.create(ticket, ticket.getChangeDate(), opInfo.getActorName());
        ticketDAO.updateUsingHistory(changeId);
        triplePassSupportService.triplePass(technicalUser, ticketId);
        assertTicketClosed(ticketId);
        assertDomainCreated(domainName);
        notifications = dnsNotificationDAO.getByDomainName(domainName);
        AssertJUnit.assertTrue(notifications.isEmpty());
    }

    private long registerAdpDomainWrongNameserver() throws Exception {
        RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, "APITEST-IEDR");
        registrationRequestVO.updateNameservers(Arrays.asList(new Nameserver("bad1." + domainName, "127.1.1.1", null),
                new Nameserver("bad2." + domainName, "127.1.1.1", null)));
        crsCommonAppService.registerDomain(user, registrationRequestVO, null);
        return ticketAppService.getTicketForDomain(user, domainName).getId();
    }

    private long registerCharityDomain() throws Exception {
        RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, "APITEST-IEDR");
        registrationRequestVO.setCharityCode("chy123");
        crsCommonAppService.registerDomain(user, registrationRequestVO, null);
        return ticketAppService.getTicketForDomain(user, domainName).getId();
    }

    private long registerDomainWithAdpPayment(AuthenticatedUserVO user, String domainName) throws Exception {
        RegistrationRequestVO registrationRequestVO = createBasicCreateRequest(domainName, user.getUsername());
        crsCommonAppService.registerDomain(user, registrationRequestVO, null);
        return ticketAppService.getTicketForDomain(user, domainName).getId();
    }

    private void assertDomainCreated(String domainName) throws DomainNotFoundException {
        Domain d = domainSearchService.getDomain(domainName);
        assertDomainCreated(d);
    }

    private void assertDomainCreated(Domain domain) throws DomainNotFoundException {
        AssertJUnit.assertNotNull(domain);
        AssertJUnit.assertEquals("Domain is active", NRPStatus.Active, domain.getDsmState().getNrpStatus());
    }

}
