package pl.nask.crs.app.triplepass;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class TriplePassServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    TriplePassSupportService triplePassSupportService;

    @Resource
    AuthenticationService authenticationService;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    TicketService ticketService;

    @Resource
    PaymentSearchService paymentSearchService;

    @Resource
    DomainService domainService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    TriplePassAppService tripplePassSupportService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    CommonAppService commonAppService;

    private ReservationSearchCriteria readyCriteria;

    private ReservationSearchCriteria notReadyCriteria;

    {
        readyCriteria = new ReservationSearchCriteria();
        readyCriteria.setReadyForSettlement(true);
        readyCriteria.setSettled(false);
        readyCriteria.setBillingNH("APITEST-IEDR");
        notReadyCriteria = new ReservationSearchCriteria();
        notReadyCriteria.setReadyForSettlement(false);
        notReadyCriteria.setSettled(false);
        notReadyCriteria.setBillingNH("APITEST-IEDR");
    }

    @Test
    public void charityDomainFinancialCheckTest() throws Exception {
        long ticketId = 259926;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, ticket.getFinancialStatus());

        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        triplePassSupportService.triplePass(user, ticketId);
        AssertJUnit.assertTrue(domainSearchService.exists("charitydomaintechpassed.ie"));
    }

    @Test
    public void CCDomainFinancialCheckTest() throws Exception {
        long ticketId = 259927;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null)
                .getResults();
        List<Reservation> readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null)
                .getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(5, readyReservationsBefore);
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        Assert.assertNull(transaction.getFinanciallyPassedDate());

        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        triplePassSupportService.triplePass(user, ticketId);
        AssertJUnit.assertTrue(domainSearchService.exists("createCCDomainTechPassed.ie"));
        notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(4, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        reservation = paymentSearchService.getReservationForTicket(ticketId);
        transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void ADPDomainFinanacialCheckWithReservationTest() throws Exception {
        long ticketId = 259928L;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null)
                .getResults();
        List<Reservation> readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null)
                .getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(5, readyReservationsBefore);
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        Assert.assertNull(transaction.getFinanciallyPassedDate());

        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        triplePassSupportService.triplePass(user, ticketId);
        AssertJUnit.assertTrue(domainSearchService.exists("adpDomain.ie"));
        notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(notReadyReservationsBefore - 1, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        reservation = paymentSearchService.getReservationForTicket(ticketId);
        transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void ADPDomainFinanacialCheckWithoutReservationTest() throws Exception {
        long ticketId = 259929;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(AdminStatus.PASSED, ticket.getAdminStatus());
        AssertJUnit.assertEquals(TechStatus.PASSED, ticket.getTechStatus());
        AssertJUnit.assertEquals(FinancialStatus.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null)
                .getResults();
        List<Reservation> readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null)
                .getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(5, readyReservationsBefore);

        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        triplePassSupportService.triplePass(user, ticketId);
        AssertJUnit.assertTrue(domainSearchService.exists("createDomainRegistrarBasic3.ie"));
        notReadyReservations = paymentSearchService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentSearchService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(notReadyReservationsBefore, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void testPromoteTicketToDomain() throws Exception {
        long ticketId = 259929;
        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        ticketService.updateFinancialStatus(ticketId, FinancialStatus.PASSED, TestOpInfo.DEFAULT);
        String domainName = tripplePassSupportService.promoteTicketToDomain(user, ticketId);
        // the ticket should be deleted and the domain object should be created

        try {
            ticketSearchService.getTicket(ticketId);
            AssertJUnit.fail("There should be no ticket with id=" + ticketId + " anymore");
        } catch (TicketNotFoundException e) {
            // this was desired
        }

        Domain d = domainSearchService.getDomain(domainName);
        AssertJUnit.assertNotNull(d);
    }

    @Test(expectedExceptions = TicketIllegalStateException.class)
    public void testPromoteTicketToDomainFail() throws Exception {
        long ticketId = 259929;
        AuthenticatedUser user = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1",
                false, null, true, "ws");
        // Illegal state exception should be thrown
        tripplePassSupportService.promoteTicketToDomain(user, ticketId);
    }

    @Test
    public void modifyTicketTriplePassTest() throws Exception {
        long ticketId = 259922;
        ticketService.updateAdminStatus(ticketId, AdminStatus.PASSED, TestOpInfo.DEFAULT);

        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "crs");

        String domainName = "thedomain-modpending.ie";
        triplePassSupportService.triplePass(user, ticketId);
        AssertJUnit.assertTrue(domainSearchService.exists(domainName));

        try {
            ticketSearchService.getTicket(ticketId);
            AssertJUnit.fail("There should be no ticket with id=" + ticketId + " anymore");
        } catch (TicketNotFoundException e) {
            // this was desired
        }

        Domain d = domainSearchService.getDomain(domainName);

        AssertJUnit.assertNotNull(d);
        for (Contact contact : d.getAdminContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
        for (Contact contact : d.getTechContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
        for (Contact contact : d.getBillingContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
    }

    @Test
    public void testRegistrationCopiesHolderInfo() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "crs");
        long ticketId = 400002;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        triplePassSupportService.triplePass(user, ticketId);
        Domain createdDomain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                createdDomain.getHolderClass().getId());
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                createdDomain.getHolderCategory().getId());
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                createdDomain.getHolderSubcategory().getId());
    }

    @Test
    public void testTransferIgnoresHolderInfo() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "crs");
        long ticketId = 400003;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertNotSame(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                domain.getHolderClass().getId());
        AssertJUnit.assertNotSame(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                domain.getHolderCategory().getId());
        AssertJUnit.assertNotNull(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue());
        AssertJUnit.assertNull(domain.getHolderSubcategory());
        triplePassSupportService.triplePass(user, ticketId);
        Domain updatedDomain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(domain.getHolderClass().getId(), updatedDomain.getHolderClass().getId());
        AssertJUnit.assertEquals(domain.getHolderCategory().getId(), updatedDomain.getHolderCategory().getId());
        AssertJUnit.assertNull(updatedDomain.getHolderSubcategory());
    }

    @Test
    public void testModificationCopiesHolderInfo() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "crs");
        long ticketId = 400004;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.MOD, ticket.getOperation().getType());
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertNotSame(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                domain.getHolderClass().getId());
        AssertJUnit.assertNotSame(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                domain.getHolderCategory().getId());
        AssertJUnit.assertNotNull(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue());
        AssertJUnit.assertNull(domain.getHolderSubcategory());
        triplePassSupportService.triplePass(user, ticketId);
        Domain updatedDomain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                updatedDomain.getHolderClass().getId());
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                updatedDomain.getHolderCategory().getId());
        AssertJUnit.assertEquals(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                updatedDomain.getHolderSubcategory().getId());
    }

}
