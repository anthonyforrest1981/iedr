package pl.nask.crs.api.common;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;

import static pl.nask.crs.api.Helper.createBasicCreditCard;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class TransferDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    DomainService domainService;

    @Test
    public void transferDomainTest() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, new OpInfo(user));
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());

        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1,
                authcode.getAuthCode());
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(390, domain.getDsmState().getId());
    }

    @Test
    public void transferDomainCCTest() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, new OpInfo(user));
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());

        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1,
                authcode.getAuthCode());
        CreditCardVO creditCard = createBasicCreditCard();
        long newTicketId = crsCommonAppService.transferDomain(user, request, creditCard);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation) reservationDAO.getReservations(criteria, 0, 1, null).getResults()
                .get(0);
        AssertJUnit.assertEquals("APITEST-IEDR", reservation.getNicHandleId());
        AssertJUnit.assertEquals(domainName, reservation.getDomainName());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(4), reservation.getVatAmount());
        Transaction t = transactionDAO.get(reservation.getTransactionId());

        Assert.assertNotNull(t.getRealexPasRef());
        Assert.assertNotNull(t.getRealexAuthCode());
        Assert.assertNotNull(reservation.getTicketId());
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
    }

    @Test
    public void transferDomainCharityTest() throws Exception {
        String domainName = "transferDomainUC006SC06.ie";

        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(121, domain.getDsmState().getId());

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(domainName, new OpInfo(user)).getAuthCode();
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", true, 1, authCode);
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(403, domain.getDsmState().getId());
    }
}
