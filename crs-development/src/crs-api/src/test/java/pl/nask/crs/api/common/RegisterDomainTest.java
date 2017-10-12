package pl.nask.crs.api.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;

import static pl.nask.crs.api.Helper.createBasicCreateRequest;
import static pl.nask.crs.api.Helper.createBasicCreditCard;

/**
 *
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class RegisterDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    TicketSearchService ticketSearchService;

    String domainName = "registerdomain.ie";

    @Test
    public void registerDomainTest() throws Exception {
        List<Reservation> reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        int noOfReservations = reservations.size();
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        Assert.assertNull(ticket.getCharityCode());

        reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        // no new reservations
        AssertJUnit.assertEquals(noOfReservations, reservations.size());
    }

    private List<Reservation> getAllReservationsForBillingNH(String billingNH) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH(billingNH);
        return reservationDAO.getAllReservations(criteria);
    }

    @Test
    public void registerCCDomainTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        long newTicketId = crsCommonAppService.registerDomain(user, request, creditCard);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        Assert.assertNull(ticket.getCharityCode());


        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setDomainName(domainName);
        List<Reservation> res = reservationDAO.getAllReservations(criteria);
        AssertJUnit.assertFalse(res.isEmpty());
        Reservation reservation = res.get(0);
        Transaction t = transactionDAO.get(reservation.getTransactionId());
        // new reservation added
        AssertJUnit.assertEquals("APITEST-IEDR", reservation.getNicHandleId());
        AssertJUnit.assertEquals(domainName.toLowerCase(), reservation.getDomainName());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(4), reservation.getVatAmount());
        Assert.assertNotNull(t.getRealexPasRef());
        Assert.assertNotNull(t.getRealexAuthCode());
        Assert.assertNotNull(reservation.getTicketId());
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainEmptyCardNumberTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        creditCard.setCardNumber("");
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainEmptyCardHolderTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        creditCard.setCardHolderName("");
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainEmptyCvnTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        // Empty CVN number, non empty CVN presence indicator
        creditCard.setCvnNumber("");
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainEmptyCvnPresIndTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        // Non empty CVN number, empty CVN presence indicator
        creditCard.setCvnNumber(null);
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainNotNumberCvnTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        creditCard.setCvnNumber("ABC");
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test(expectedExceptions = GenericValidationException.class)
    public void registerCCDomainCvnTooLongTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        CreditCardVO creditCard = createBasicCreditCard();
        creditCard.setCvnNumber("1234");
        crsCommonAppService.registerDomain(user, request, creditCard);
    }

    @Test
    public void registerCharityDomain() throws Exception {
        List<Reservation> reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        int noOfReservations = reservations.size();
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        request.setCharityCode("CHY123");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        AssertJUnit.assertEquals("CHY123", ticket.getCharityCode());

        // no reservation was added!
        reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        AssertJUnit.assertEquals(noOfReservations, reservations.size());
    }
}
