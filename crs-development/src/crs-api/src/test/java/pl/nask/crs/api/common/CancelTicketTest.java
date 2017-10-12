package pl.nask.crs.api.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

import static pl.nask.crs.api.Helper.createBasicCreateRequest;
import static pl.nask.crs.api.Helper.createBasicCreditCard;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
@SuppressWarnings("nullness")
public class CancelTicketTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TicketService ticketService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    DomainService domainService;

    String domainName = "registerdomain.ie";
    AuthenticatedUserVO user;

    @BeforeMethod
    public void authenticate() throws AuthenticationException, IncorrectUtf8FormatException {
        user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void uc048sc01Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.NEW, ticket.getAdminStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        cancelTicket(newTicketId);
    }

    private void cancelTicket(long newTicketId)
            throws TicketNotFoundException, CancelTicketException, CardPaymentException, AuthenticationException,
            SessionExpiredException, DomainNotFoundException, DomainIllegalStateException, IncorrectUtf8FormatException {

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setDomainName(domainName);
        List<Reservation> res = reservationDAO.getAllReservations(criteria);
        AssertJUnit.assertFalse(res.isEmpty());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(res.get(0).getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc02Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();
        ticketService.updateAdminStatus(newTicketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        cancelTicket(newTicketId);
    }

    @Test
    public void uc048sc03Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, null, TestOpInfo.DEFAULT);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        cancelTicket(newTicketId);
    }

    @Test
    public void uc048sc05Test() throws Exception {
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, null, TestOpInfo.DEFAULT);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setDomainName(domainName);
        AssertJUnit.assertFalse(reservationDAO.exists(criteria));

        crsCommonAppService.cancel(user, newTicketId);

        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc06Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        long newTicketId = transferDomainWithCCPayment(domainName);

        Domain domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        Reservation reservation = reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);

        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc07Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        long newTicketId = transferDomainWithCCPayment(domainName);
        ticketService.updateAdminStatus(newTicketId, AdminStatus.PASSED, new TestOpInfo("test"));

        Domain domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(438, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation) reservationDAO.getReservations(criteria, 0, 1, null).getResults()
                .get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(18, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc08Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        long newTicketId = transferDomainWithCCPayment(domainName);
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, null, TestOpInfo.DEFAULT);

        Domain domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(486, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        Reservation reservation = reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(20, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test(expectedExceptions = CancelTicketException.class)
    public void uc048sc09Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        long newTicketId = transferDomainWithCCPayment(domainName);
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, FinancialStatus.PASSED, TestOpInfo.DEFAULT);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.PASSED, ticket.getFinancialStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        Reservation reservation = reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);
    }

    @Test
    public void uc048sc10Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1,
                authcode.getAuthCode());
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, null, TestOpInfo.DEFAULT);

        Domain domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());
        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setDomainName(domainName);
        AssertJUnit.assertFalse(reservationDAO.exists(criteria));

        crsCommonAppService.cancel(user, newTicketId);

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc11Test() throws Exception {
        String domainName = "thedomain-modpending.ie";
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName(domainName);
        Ticket ticket = ticketSearchService.find(criteria, 0, 10, null).getResults().get(0);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.MOD, ticket.getOperation().getType());

        crsCommonAppService.cancel(user, ticket.getId());
        ticket = ticketSearchService.getTicket(ticket.getId());
        AssertJUnit.assertEquals(CustomerStatus.CANCELLED, ticket.getCustomerStatus());
    }

    @Test(expectedExceptions = CancelTicketException.class)
    public void uc048sc12Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        long newTicketId = transferDomainWithCCPayment(domainName);
        updateStatuses(newTicketId, AdminStatus.PASSED, TechStatus.PASSED, FinancialStatus.PASSED, TestOpInfo.DEFAULT);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.PASSED, ticket.getFinancialStatus());
        AssertJUnit.assertEquals(CustomerStatus.NEW, ticket.getCustomerStatus());

        crsCommonAppService.cancel(user, newTicketId);
    }

    private long registerDomainWithCCPayment() throws Exception {
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        return crsCommonAppService.registerDomain(user, request, creditCard);
    }

    private long transferDomainWithCCPayment(String domainName) throws Exception {
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1,
                authcode.getAuthCode());
        CreditCardVO creditCard = createBasicCreditCard();
        return crsCommonAppService.transferDomain(user, request, creditCard);
    }

    private void updateStatuses(long ticketId, AdminStatus newAdminStatus, TechStatus newTechStatus,
            FinancialStatus newFinancialStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        ticketService.updateAdminStatus(ticketId, newAdminStatus, opInfo);
        ticketService.updateTechStatus(ticketId, newTechStatus, opInfo);
        ticketService.updateFinancialStatus(ticketId, newFinancialStatus, opInfo);
    }

}
