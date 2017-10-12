package pl.nask.crs.api.triplepass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

import static pl.nask.crs.api.Helper.createBasicCreditCard;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

public class TriplePassTransferTest extends TriplePassTest {

    @Autowired
    DomainDAO domainDao;

    @Autowired
    HistoricalDomainDAO historicalDomainDao;

    @Autowired
    DnsNotificationDAO dnsNotificationDAO;

    @Autowired
    DnsNotificationService dnsNotificationService;

    @Test
    public void uc07sc01Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    private void updateTicketStatuses(long ticketId, AdminStatus as, TechStatus ts)
            throws TicketNotFoundException {
        updateStatuses(ticketId, as, ts, null, TestOpInfo.DEFAULT);
    }

    private void updateTicketStatuses(long ticketId, AdminStatus as, TechStatus ts, FinancialStatus fs)
            throws TicketNotFoundException {
        updateStatuses(ticketId, as, ts, fs, TestOpInfo.DEFAULT);
    }

    @Test
    public void uc07sc02Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc03Test() throws Exception {
        String domainName = "transferDomainUC006SC03.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 25);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc04Test() throws Exception {
        String domainName = "transferDomainUC007SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 113);

        long ticketId = transferDomainCharity(domainName, gainingNH);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);
        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationNotCreated(ticketId);
        assertDomainTransferred(domainName, gainingNH, 113);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc05Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 18);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc06Test() throws Exception {
        String domainName = "transferDomainUC006SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 19);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc07Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 20);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc08Test() throws Exception {
        String domainName = "transferDomainUC007SC08.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 21);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc09Test() throws Exception {
        String domainName = "transferDomainUC006SC07.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 81);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc10Test() throws Exception {
        String domainName = "transferDomainUC007SC10.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 49);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc12Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainADPWrongNameserver(domainName, gainingNH);

        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.STALLED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, t.getFinancialStatus());
    }

    @Test
    public void uc07sc13Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 390);

        ticketService.updateAdminStatus(ticketId, AdminStatus.CANCELLED, TestOpInfo.DEFAULT);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);
        assertDomainState(domainName, 17);
    }

    @Test
    public void uc07sc14Test() throws Exception {
        String domainName = "transferDomainUC006SC03.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 25);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc15Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);
        ticketService.updateFinancialStatus(ticketId, FinancialStatus.NEW, TestOpInfo.DEFAULT);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 1);
        triplePassSupportService.triplePass(user, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.STALLED, t.getFinancialStatus());
    }

    @Test
    public void uc07sc16Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 18);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 438);

        ticketService.updateAdminStatus(ticketId, AdminStatus.CANCELLED, TestOpInfo.DEFAULT);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);
        assertDomainState(domainName, 18);
    }

    @Test
    public void uc07sc17Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 20);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 486);

        ticketService.updateAdminStatus(ticketId, AdminStatus.CANCELLED, TestOpInfo.DEFAULT);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);
    }

    @Test
    public void uc37sc01Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.STALLED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc37sc02Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED, FinancialStatus.STALLED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        triplePassSupportService.triplePass(user, ticketId);

        assertTransferReservationCreatedAndReady(ticketId, domainName);
        assertDomainTransferred(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc37sc03Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.STALLED, FinancialStatus.NEW);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 1);
        triplePassSupportService.triplePass(user, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.STALLED, t.getFinancialStatus());
    }

    @Test
    public void uc37sc04Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED, FinancialStatus.STALLED);

        AuthenticatedUserVO user = authenticate("AAA906-IEDR");
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 0); // no email should be sent here!
        triplePassSupportService.triplePass(user, ticketId);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.STALLED, t.getFinancialStatus());
    }


    @Test
    public void testTransferWithFewerDNSThanOriginally() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        // transactions cannot revert this setting of third nameserver due to fact
        // fact triplePassAppService resets transaction, so transaction revert is of the
        // state from line 454
        Domain domain = domainDao.get(domainName);
        if (domain.getNameservers().size() > 2) {
            List<Nameserver> nameservers = new ArrayList<>(domain.getNameservers().subList(0, 2));
            domain.setNameservers(nameservers);
            domainDao.updateUsingHistory(historicalDomainDao.create(domain, new Date(),
                    TestOpInfo.DEFAULT.getActorName()));
            domain = domainDao.get(domainName);
        }
        Assert.assertEquals(domain.getNameservers().size(), 2);
        domain.getNameservers().add(new Nameserver("ns.testdns.ie", "123.123.123.123", null));
        domainDao.updateUsingHistory(historicalDomainDao.create(domain, new Date(),
                TestOpInfo.DEFAULT.getActorName()));
        domain = domainDao.get(domainName);
        Assert.assertEquals(domain.getNameservers().size(), 3);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.PASSED, FinancialStatus.STALLED);

        triplePassSupportService.triplePass(authenticate("AAA906-IEDR"), ticketId);

        domain = domainDao.get(domainName);
        final List<Nameserver> domainNameservers = domain.getNameservers();
        Assert.assertEquals(domainNameservers.size(), 2);
        TransferRequestVO transferRequestVO = createBasicTransferRequest(domainName, null, false, 0, null);
        final List<Nameserver> transferRequest = transferRequestVO.getNameservers();
        Assert.assertEquals(domainNameservers.get(0).getName(), transferRequest.get(0).getName());
        Assert.assertEquals(domainNameservers.get(0).getIpv4Address(), transferRequest.get(0).getIpv4Address());
        Assert.assertEquals(domainNameservers.get(0).getIpv6Address(), transferRequest.get(0).getIpv6Address());
        Assert.assertEquals(domainNameservers.get(1).getName(), transferRequest.get(1).getName());
        Assert.assertEquals(domainNameservers.get(1).getIpv4Address(), transferRequest.get(1).getIpv4Address());
        Assert.assertEquals(domainNameservers.get(1).getIpv6Address(), transferRequest.get(1).getIpv6Address());
    }

    @Test
    public void removeDnsNotificationsAfterPromotingTransferTicket() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";

        long ticketId = transferDomainWithADPPayment(domainName, "APITEST-IEDR", 1);
        updateTicketStatuses(ticketId, AdminStatus.PASSED, TechStatus.NEW);

        DnsNotification notification1 = new DnsNotification(domainName, null, "ns1.dns.ie", new Date(), "error");
        DnsNotification notification2 = new DnsNotification(domainName, ticketId, "ns2.dns.ie", new Date(), "error");
        dnsNotificationService.createNotification(notification1);
        dnsNotificationService.createNotification(notification2);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 2);
        triplePassSupportService.triplePass(authenticate("AAA906-IEDR"), ticketId);
        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 0);
    }

    private long transferDomainWithADPPayment(String domainName, String gainingNH, int period) throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT).getAuthCode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, period, authCode);
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private long transferDomainWithCCPayment(String domainName, String gainingNH, int period) throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT).getAuthCode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, period, authCode);
        CreditCardVO creditCard = createBasicCreditCard();
        return crsCommonAppService.transferDomain(user, request, creditCard);
    }

    private long transferDomainCharity(String domainName, String gainingNH) throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT).getAuthCode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, true, 0, authCode);
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private long transferDomainADPWrongNameserver(String domainName, String gainingNH) throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT).getAuthCode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, 1, authCode);
        request.updateNameservers(Collections.singletonList(new Nameserver("bad." + domainName, "127.1.1.1", null)));
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private AuthenticatedUserVO authenticate(String nicHandleId)
            throws IllegalArgumentException, AuthenticationException, IncorrectUtf8FormatException {
        return crsAuthenticationService.authenticate(nicHandleId, "Passw0rd!", "1.1.1.1", null);
    }

    private void assertDomainState(String domainName, int expectedDSMState) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(expectedDSMState, domain.getDsmState().getId());
    }

    private void assertDomainTransferred(String domainName, String gainingNH, int expectedDSMState) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(gainingNH, domain.getBillingContacts().get(0).getNicHandle());
        AssertJUnit.assertEquals(expectedDSMState, domain.getDsmState().getId());
        AssertJUnit.assertNull(domain.getAuthCode());
    }

    private void assertTransferReservationCreatedAndReady(long ticketId, String domainName) {
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        AssertJUnit.assertNotNull(reservation);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());
        AssertJUnit.assertEquals(domainName, reservation.getDomainName());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
    }

    private void assertTransferReservationNotCreated(long ticketId) {
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        AssertJUnit.assertNull(reservation);
    }

}
