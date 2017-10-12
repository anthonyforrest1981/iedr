package pl.nask.crs.ticket.services;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.permissions.Helper;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.*;

@SuppressWarnings("nullness")
public class TicketServiceTest extends AbstractContextAwareTest {

    private static final long TEST_TICKET_ID = 256744L;
    private static final long TRANSFER_TICKET_ID = 259924L;
    private static final long TICKET_WITH_SUBCATEGORY_ID = 400002L;
    private static final String LONGEST_LEGAL_DOMAIN_NAME = "63-characters-is-the-longest-possible-domain-name-for-a-"
            + "website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-"
            + "possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-we.ie";
    @Resource
    private TicketService ticketService;

    @Resource
    private TicketSearchService ticketSearchService;

    @Resource
    private TicketHistorySearchService historySearchService;

    @Resource
    private DomainSearchService domainSearchService;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private EntityService entityService;

    final AuthenticatedUser user = new AuthenticatedUser() {
        @Override
        public String getUsername() {
            return "IDL2-IEDR";
        }

        @Override
        public String getSuperUserName() {
            return null;
        }

        @Override
        public String getAuthenticationToken() {
            return null;
        }
    };

    @Test
    public void testCheckIn() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.HOLD_PAPERWORK, "admin status");

        ticketService.checkIn(TEST_TICKET_ID, AdminStatus.RENEW, new TestOpInfo("GEORGE-IEDR"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.RENEW, "admin status");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    private void compareWithHistory(Ticket ticket) {
        Ticket histTicket = historySearchService.getTicketHistory(ticket.getId()).get(0).getObject();
        TicketHelper.compareHistoricalTickets(histTicket, ticket);
    }

    @Test(expectedExceptions = TicketNotCheckedOutException.class)
    public void testCheckInWhenTicketNotCheckedOut() throws Exception {
        ticketService.checkIn(256813L, AdminStatus.RENEW, new TestOpInfo("GEORGE-IEDR"));
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testCheckInWhenTicketCheckedOutToOtherNH() throws Exception {
        ticketService.checkIn(TEST_TICKET_ID, AdminStatus.RENEW, new TestOpInfo("NTG1-IEDR"));
    }

    @Test
    public void testCheckOut() throws Exception {
        ticketService.checkOut(256814L, new TestOpInfo("GEORGE-IEDR"));
        Ticket ticket = ticketSearchService.getTicket(256814L);
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
        compareWithHistory(ticket);
    }

    @Test
    public void testCheckOutCalledTwice() throws Exception {
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        ticketService.checkOut(256814L, new TestOpInfo("GEORGE-IEDR"));
        ticketService.checkOut(256814L, new TestOpInfo("GEORGE-IEDR"));
        Ticket ticket = ticketSearchService.getTicket(256814L);
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
    }

    @Test(expectedExceptions = TicketAlreadyCheckedOutException.class)
    public void testCheckOutWhenTicketAlreadyCheckedOut() throws Exception {
        ticketService.checkOut(TEST_TICKET_ID, new TestOpInfo("NTG1-IEDR"));
    }

    @Test
    public void testAlterStatus() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.HOLD_PAPERWORK, "admin status");

        ticketService.alterStatus(user, TEST_TICKET_ID, AdminStatus.RENEW, new TestOpInfo("GEORGE-IEDR"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.RENEW, "admin status");
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testAlterStatusWhenTicketCheckedOutToOtherNH() throws Exception {
        ticketService.alterStatus(user, TEST_TICKET_ID, AdminStatus.RENEW, new TestOpInfo("NTG1-IEDR"));
    }

    @Test
    public void testAccept() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.HOLD_PAPERWORK, "admin status");
        Assert.assertNotNull(ticket.getCheckedOutTo(), "check out to");

        ticketService.accept(user, TEST_TICKET_ID, ticket.getOperation(), new TestOpInfo("GEORGE-IEDR", "accepted"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.PASSED, "admin status");
        Assert.assertEquals(ticket.getHostmastersRemark(), "accepted", "hostmaster remark");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void testAcceptWithSubcategory() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertNull(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue());

        EntitySubcategory subcategory = new EntitySubcategory();
        subcategory.setId(1);
        ticket.getOperation().getDomainHolderSubcategoryField().setNewValue(subcategory);
        ticketService.accept(user, TEST_TICKET_ID, ticket.getOperation(), new TestOpInfo("GEORGE-IEDR", "accepted"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                subcategory.getId());
        compareWithHistory(ticket);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testAcceptWhenTicketCheckedOutToOtherNH() throws Exception {
        ticketService.accept(null, TEST_TICKET_ID, null, new TestOpInfo("NTG1-IEDR", "accept"));
    }

    @Test(expectedExceptions = CategoryDoesNotMatchSubcategoryException.class)
    public void testAcceptWithCategoryMismatch() throws Exception {
        EntitySubcategory subcategory = new EntitySubcategory();
        subcategory.setId(1);
        Ticket ticket = ticketSearchService.getTicket(TRANSFER_TICKET_ID);
        ticket.getOperation().getDomainHolderSubcategoryField().setNewValue(subcategory);
        ticketService.checkOut(TRANSFER_TICKET_ID, new TestOpInfo("IDL2-IEDR", "check out"));
        ticketService.accept(user, TRANSFER_TICKET_ID, ticket.getOperation(), new TestOpInfo("IDL2-IEDR", "accept"));
    }

    @Test(expectedExceptions = TicketNotFoundException.class)
    public void testDelete() throws Exception {
        Ticket ticket;
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        ticketService.delete(ticket, new TestOpInfo("GEORGE-IEDR"));
        ticketSearchService.getTicket(TEST_TICKET_ID);
    }

    @Test
    public void testReject() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);

        ticketService.reject(user, TEST_TICKET_ID, AdminStatus.FINANCE_HOLDUP, ticket.getOperation(),
                new TestOpInfo("GEORGE-IEDR", "rejected"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.FINANCE_HOLDUP, "admin status");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void testRejectWithSubcategory() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertNull(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue());

        EntitySubcategory subcategory = new EntitySubcategory();
        subcategory.setId(1);
        ticket.getOperation().getDomainHolderSubcategoryField().setNewValue(subcategory);
        ticketService.reject(user, TEST_TICKET_ID, AdminStatus.FINANCE_HOLDUP, ticket.getOperation(),
                new TestOpInfo("GEORGE-IEDR", "rejected"));
        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                subcategory.getId());
        compareWithHistory(ticket);
    }

    @Test(expectedExceptions = InvalidStatusException.class)
    public void testRejectWithInvalidStatus() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);

        ticketService.reject(user, TEST_TICKET_ID, AdminStatus.PASSED, ticket.getOperation(),
                new TestOpInfo("GEORGE-IEDR", "rejected"));
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void testRejectWithEmptyRemark() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);

        ticketService.reject(user, TEST_TICKET_ID, AdminStatus.FINANCE_HOLDUP, ticket.getOperation(),
                new TestOpInfo("GEORGE-IEDR", ""));
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testRejectWhenTicketCheckedOutToOtherNH() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);

        ticketService.reject(null, TEST_TICKET_ID, AdminStatus.FINANCE_HOLDUP, ticket.getOperation(),
                new TestOpInfo("NTG1-IEDR", "rejected"));
    }

    @Test(expectedExceptions = TicketUserCannotCheckOutException.class)
    public void testReassignToUserWithBadPermissions() throws Exception {
        ticketService.reassign(TEST_TICKET_ID, "NTG1-IEDR", TestOpInfo.DEFAULT);
    }

    @Test
    public void testReassign() throws Exception {
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        ticketService.reassign(TEST_TICKET_ID, "IDL2-IEDR", TestOpInfo.DEFAULT);
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertNotNull(ticket.getCheckedOutTo(), "check out to");
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "IDL2-IEDR", "check out to");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void testSave() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 1);
        DomainOperation domainOperation = ticket.getOperation();
        Assert.assertNull(domainOperation.getDomainNameField().getFailureReason(), "domain name failure reason");

        domainOperation.getDomainNameField().setFailureReason(FailureReason.INCORRECT);

        ticketService.save(TEST_TICKET_ID, domainOperation, new TestOpInfo("GEORGE-IEDR", "save failure reasons"));

        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainNameField().getFailureReason().getDescription(), "Incorrect",
                "domain name failure reason description");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void testSaveWithSubcategory() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertNull(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue());

        EntitySubcategory subcategory = new EntitySubcategory();
        subcategory.setId(1);
        ticket.getOperation().getDomainHolderSubcategoryField().setNewValue(subcategory);
        ticketService.save(TEST_TICKET_ID, ticket.getOperation(),
                new TestOpInfo("GEORGE-IEDR", "save failure reasons"));

        ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                subcategory.getId());
        compareWithHistory(ticket);
    }

    private TicketEdit prepareTicketEdit(long ticketId) throws TicketNotFoundException {
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        return new TicketEdit(ticket.getOperation());
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testSaveWhenTicketCheckedOutToOtherNH() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        DomainOperation domainOperation = ticket.getOperation();
        domainOperation.getDomainNameField().setFailureReason(FailureReason.INCORRECT);

        ticketService.save(TEST_TICKET_ID, domainOperation, new TestOpInfo("NTG1-IEDR", "save failure reasons"));
    }

    @Test
    public void testUpdate() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainNameField().setFailureReason(FailureReason.INCORRECT);
        ticketEdit.getDomainHolderField().setNewValue("new domain holder");

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "save failure reasons"),
                        true, false);

        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainNameField().getFailureReason().getDescription(), "Incorrect",
                "domain name failure reason description");
        Assert.assertEquals(ticket.getOperation().getDomainHolderField().getNewValue(), "new domain holder",
                "domain holder field value");
        Assert.assertEquals(historySearchService.getTicketHistory(TEST_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Resource AccountDAO accountDAO;

    @Test
    public void testUpdateTransferNameserverChanged() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TRANSFER_TICKET_ID);
        ticketEdit.getNameserversField().get(0).getName().setNewValue("ns.nameserver.ie");
        ticketService
                .updateAsOwner(TRANSFER_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "hostmaster"), true,
                        false);
        Ticket ticket = ticketSearchService.getTicket(TRANSFER_TICKET_ID);
        Assert.assertEquals(ticket.getTechStatus(), TechStatus.NEW);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.PASSED);
        Assert.assertEquals(historySearchService.getTicketHistory(TRANSFER_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void testUpdateTransferAdminContactChanged() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TRANSFER_TICKET_ID);
        ticketEdit.setAdminContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, "ADK030-IEDR")));
        ticketService
                .updateAsOwner(TRANSFER_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "hostmaster"), true,
                        false);
        Ticket ticket = ticketSearchService.getTicket(TRANSFER_TICKET_ID);
        Assert.assertEquals(ticket.getTechStatus(), TechStatus.STALLED);
        Assert.assertEquals(ticket.getAdminStatus(), AdminStatus.PASSED);
        Assert.assertEquals(historySearchService.getTicketHistory(TRANSFER_TICKET_ID).size(), 2);
        compareWithHistory(ticket);
    }

    @Test(expectedExceptions = HolderRemarkTooLongException.class)
    public void testUpdateHolderRemarkTooLong() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        char[] charArray = new char[10001];
        Arrays.fill(charArray, 'A');
        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, new String(charArray),
                new TestOpInfo("GEORGE-IEDR", "holder remark too long"), true, false);
    }

    @Test(expectedExceptions = DomainHolderMandatoryException.class)
    public void testUpdateEmptyHolder() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderField().setNewValue(null);

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "empty holder"), true,
                        false);
    }

    @Test(expectedExceptions = HolderCategoryMandatoryException.class)
    public void testUpdateEmptyHolderCategory() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderCategoryField().setNewValue(null);

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "empty holder category"),
                        true, false);
    }

    @Test(expectedExceptions = HolderClassMandatoryException.class)
    public void testUpdateEmptyHolderClass() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderClassField().setNewValue(null);

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "empty holder class"),
                        true, false);
    }

    @Test(expectedExceptions = HolderCategoryNotExistException.class)
    public void testUpdateIncorrectHolderCategory() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderCategoryField().setNewValue(-1L);

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "incorrect holder category"), true, false);
    }

    @Test(expectedExceptions = HolderClassNotExistException.class)
    public void testUpdateIncorrectHolderClass() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderClassField().setNewValue(-1L);

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "incorrect holder class"), true, false);
    }

    @Test(expectedExceptions = ClassDoesNotMatchCategoryException.class)
    public void testUpdateClassDontMatchCategory() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainHolderClassField().setNewValue(1L);
        ticketEdit.getDomainHolderCategoryField().setNewValue(2L);

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "holder class doesn't match holder category"), true, false);
    }

    @Test
    public void testUpdateTransferHolderChangeAsAdmin() throws Exception {
        ticketService.checkOut(TRANSFER_TICKET_ID, new TestOpInfo("GEORGE-IEDR"));
        TicketEdit ticketEdit = prepareTicketEdit(TRANSFER_TICKET_ID);
        ticketEdit.getDomainHolderField().setNewValue("Another Holder");
        ticketEdit.getDomainHolderClassField().setNewValue(3L);
        ticketEdit.getDomainHolderCategoryField().setNewValue(7L);

        ticketService
                .updateAsAdmin(TRANSFER_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "holder changed"),
                        true, true);
        Ticket ticket = ticketSearchService.getTicket(TRANSFER_TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getDomainHolderField().getNewValue(), "Another Holder");
        Assert.assertEquals(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(), 3L);
        Assert.assertEquals(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(), 7L);
    }

    @Test(expectedExceptions = TicketHolderChangeException.class)
    public void testUpdateTransferHolderChangeAsOwner() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TRANSFER_TICKET_ID);
        ticketEdit.getDomainHolderField().setNewValue("Another Holder");

        ticketService
                .updateAsOwner(TRANSFER_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "holder changed"),
                        true, false);
    }

    @Test(expectedExceptions = TooFewContactsException.class)
    public void testUpdateNoAdminContact() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setAdminContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, null),
                new SimpleDomainFieldChange<String>(null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "no admin contact"),
                true, false);
    }

    @Test(expectedExceptions = TooFewContactsException.class)
    public void testUpdateNoTechContact() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setTechContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, null)));

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "no tech contact"), true,
                        false);
    }

    @Test(expectedExceptions = DuplicatedContactException.class)
    public void testUpdateDuplicateAdminContact() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setAdminContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, "AAE359-IEDR"),
                new SimpleDomainFieldChange<String>(null, "AAE359-IEDR")));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "duplicate admin contacts"), true, false);
    }

    @Test(expectedExceptions = ContactSyntaxException.class)
    public void testUpdateSyntaxErrorInAdminContact() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setAdminContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, "AAE359-IDR")));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "syntax error in admin contact"), true, false);
    }

    @Test(expectedExceptions = NicHandleNotFoundException.class)
    public void testUpdateNonExistentAdminContact() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setAdminContactField(Arrays.asList(new SimpleDomainFieldChange<String>(null, "AAA000-IEDR"),
                new SimpleDomainFieldChange<String>(null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "non-existent admin contact"), true, false);
    }

    @Test
    public void testUpdateNameserversGlue() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        List<NameserverChange> expectedNameservers =
                Arrays.asList(nsGlueChange(0, domainName), nsGlueChange(1, domainName), nsGlueChange(2, domainName),
                        nsGlueChange(3, domainName));
        testUpdateNameservers(expectedNameservers);
    }

    @Test
    public void testUpdateNameserversWithFailureReasonsGlue() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TEST_TICKET_ID);
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        Assert.assertEquals(ticketEdit.getNameserversField().size(), 3);

        final NameserverChange nameserverChange1 = nsGlueChange(0, domainName);
        nameserverChange1.getName().setFailureReason(FailureReason.INCORRECT);
        nameserverChange1.getIpv4Address().setFailureReason(IpFailureReason.IP_DESTINATION_NET_UNREACHABLE);
        List<NameserverChange> expectedNameservers = Arrays.asList(nameserverChange1, nsGlueChange(1, domainName));
        ticketEdit.setNameserversField(Arrays.asList(nameserverChange1, nsGlueChange(1, domainName)));

        testUpdateNameservers(expectedNameservers);
    }

    @Test
    public void testUpdateNameserversNonGlue() throws Exception {
        List<NameserverChange> expectedNameservers = Arrays.asList(nsChange(0), nsChange(1), nsChange(2), nsChange(3));
        testUpdateNameservers(expectedNameservers);
    }

    @Test
    public void testUpdateNameserversWithFailureReasonsNonGlue() throws Exception {
        final NameserverChange nameserverChange1 = nsChange(0);
        nameserverChange1.getName().setFailureReason(FailureReason.INCORRECT);
        nameserverChange1.getIpv4Address().setFailureReason(IpFailureReason.IP_DESTINATION_NET_UNREACHABLE);
        nameserverChange1.getIpv6Address().setFailureReason(IpFailureReason.IP_DESTINATION_NET_UNREACHABLE);
        List<NameserverChange> expectedNameservers = Arrays.asList(nameserverChange1, nsChange(1));

        testUpdateNameservers(expectedNameservers);
    }

    @Test(expectedExceptions = TooFewNameserversException.class)
    public void testUpdateWithOneNameserver() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(Arrays.asList(nsChange(0)));

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "Only one ns"), true,
                        false);
    }

    @Test(expectedExceptions = TooManyNameserversException.class)
    public void testUpdateWithTooManyNameservers() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(
                Arrays.asList(nsChange(0), nsChange(1), nsChange(2), nsChange(3), nsChange(4), nsChange(5), nsChange(6),
                        nsChange(7)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "Eight nss"), true,
                false);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void testUpdateWithDuplicateNameservers() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        String domainName = ticketEdit.getDomainNameField().getNewValue();
        ticketEdit.setNameserversField(Arrays.asList(nsGlueChange(0, 0, domainName), nsGlueChange(0, 1, domainName)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "Eight nss"), true,
                false);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void testUpdateIncorrectNameserverName() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(Arrays.asList(nsChange("incorrect$nameserver1", null, null),
                nsChange("incorrect$nameserver2", null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver name"), true, false);
    }

    @Test(expectedExceptions = IpSyntaxException.class)
    public void testUpdateIncorrectNameserverIPv4() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        String domainName = ticketEdit.getDomainNameField().getNewValue();
        ticketEdit.setNameserversField(Arrays.asList(nsChange(nameserverGlueName(0, domainName), "192.168.0.256", null),
                nsChange(nameserverGlueName(1, domainName), "192.168.0.257", null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver IP"), true, false);
    }

    @Test(expectedExceptions = IpSyntaxException.class)
    public void testUpdateIncorrectNameserverIPv6() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        String domainName = ticketEdit.getDomainNameField().getNewValue();
        ticketEdit.setNameserversField(Arrays.asList(nsChange(nameserverGlueName(0, domainName), null, ":"),
                nsChange(nameserverGlueName(1, domainName), null, "::")));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver IP"), true, false);
    }

    @Test(expectedExceptions = GlueNotAllowedException.class)
    public void testUpdateNameserverGlueIpv4Redundant() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(Arrays.asList(nsChange(nameserverName(0), nameserverIpv4(0), null),
                nsChange(nameserverName(1), null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver IP"), true, false);
    }

    @Test(expectedExceptions = GlueNotAllowedException.class)
    public void testUpdateNameserverGlueIpv6Redundant() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(Arrays.asList(nsChange(nameserverName(0), null, nameserverIpv6(1)),
                nsChange(nameserverName(1), null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver IP"), true, false);
    }

    @Test(expectedExceptions = GlueRequiredException.class)
    public void testUpdateNameserverGlueMissing() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        String domainName = ticketEdit.getDomainNameField().getNewValue();
        ticketEdit.setNameserversField(Arrays.asList(nsChange(nameserverGlueName(0, domainName), null, null),
                nsChange(nameserverGlueName(1, domainName), null, null)));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null,
                new TestOpInfo("GEORGE-IEDR", "Incorrect nameserver IP"), true, false);
    }

    @Test(expectedExceptions = TicketEditFlagException.class)
    public void testUpdateWhenTicketEditFlagNotSet() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(256813L);
        ticketEdit.getDomainNameField().setFailureReason(FailureReason.INCORRECT);
        ticketEdit.getDomainHolderField().setNewValue("new domain holder");

        ticketService.checkOut(256813L, new TestOpInfo("GEORGE-IEDR"));
        ticketService
                .updateAsAdmin(256813L, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "save failure reasons"), true,
                        false);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testUpdateWhenTicketCheckedOutToOtherNH() throws Exception {
        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.getDomainNameField().setFailureReason(FailureReason.INCORRECT);

        ticketService
                .updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("NTG1-IEDR", "save failure reasons"),
                        true, true);
    }

    @Test(expectedExceptions = DomainNameSyntaxException.class)
    public void testCreateUppercaseDomain() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketRequest ticketRequest = Helper.prepareTicketRequest("testCreateTicket.ie", "IDL2-IEDR");
        ticketService.createRegistrationTicket(user, ticketRequest, 101L);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void testCreateWithDNSTooLong() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketRequest ticketRequest = Helper.prepareTicketRequest("testcreateticketdomain2.ie", "IDL2-IEDR",
                "63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie");
        ticketService.createRegistrationTicket(user, ticketRequest, 101L);
    }

    @Test
    public void testCreateWithMaxDNSLength() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketRequest ticketRequest =
                Helper.prepareTicketRequest("testcreateticketdomain2.ie", "IDL2-IEDR", LONGEST_LEGAL_DOMAIN_NAME);
        long newTicketId = ticketService.createRegistrationTicket(user, ticketRequest, 101L);
        Ticket newTicket = ticketSearchService.getTicket(newTicketId);
        Assert.assertEquals(newTicket.getOperation().getNameserversField().get(0).getNewNameserver().getName(),
                LONGEST_LEGAL_DOMAIN_NAME);
    }

    @Test
    public void testCreateCharity() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        String domainName = "testcreateticketdomain1.ie";
        String remark = "domain creation request";
        TicketRequest ticketRequest =
                Helper.prepareTicketRequest(domainName, "IDL2-IEDR", "dns1.com", 1L, 5, "CHY123", remark);
        long newTicketId = ticketService.createRegistrationTicket(user, ticketRequest, 101L);

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);

        Assert.assertEquals(ticketFromDB.getOperation().getDomainNameField().getNewValue(), domainName,
                "ticket domain name field");
        Assert.assertEquals(ticketFromDB.getRequestersRemark(), remark, "requester remark");
        Assert.assertEquals(ticketFromDB.getDomainPeriod().getYears(), 5, "domain period");
        Assert.assertEquals(ticketFromDB.getCharityCode(), "CHY123", "charity code");
    }

    @Test
    public void testCreateCheckOwnerType() throws Exception {
        String domainName = "newdomain.ie";
        long ownerTypeId = 2L;
        TicketRequest ticketRequest = Helper.prepareTicketRequest(domainName, "IDL2-IEDR", "dns1.com", ownerTypeId, 1,
                "CHY123", "remark");
        long ticketId = ticketService.createRegistrationTicket(user, ticketRequest, 101L);
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        OwnerType ownerType = entityService.getOwnerType(ownerTypeId);
        Assert.assertEquals(ticket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                ownerType.getClassId());
        Assert.assertEquals(ticket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                ownerType.getCategoryId());
    }

    @Test
    public void testCreateNonCharity() throws Exception {
        String domainName = "testcreateticketdomain2.ie";
        String remark = "domain creation request";
        TicketRequest ticketRequest =
                Helper.prepareTicketRequest(domainName, "IDL2-IEDR", "dns1.com", 1L, null, null, remark);
        long newTicketId = ticketService.createRegistrationTicket(user, ticketRequest, 101L);

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);

        Assert.assertEquals(ticketFromDB.getOperation().getDomainNameField().getNewValue(), domainName,
                "ticket domain name field");
        Assert.assertEquals(ticketFromDB.getRequestersRemark(), remark, "requester remark");
        Assert.assertNull(ticketFromDB.getDomainPeriod(), "domain period");
        Assert.assertNull(ticketFromDB.getCharityCode(), "charity code");
    }

    @Test(expectedExceptions = CharityCodeTooLongException.class)
    public void testCreateCharityCodeTooLong() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        String domainName = "testcreateticketdomain1.ie";
        TicketRequest ticketRequest =
                Helper.prepareTicketRequest(domainName, "IDL2-IEDR", "dns1.com", 1L, 5, "CHARITYCODE1234567890", "remark");
        ticketService.createRegistrationTicket(user, ticketRequest, 101L);
    }

    @Test
    public void updateTechStatusTest() throws Exception {
        Ticket actual = ticketSearchService.getTicket(259926L);
        Assert.assertEquals(historySearchService.getTicketHistory(259926L).size(), 1);
        Assert.assertEquals(actual.getTechStatus(), TechStatus.PASSED);

        ticketService.updateTechStatus(259926L, TechStatus.STALLED, new TestOpInfo("GEORGE-IEDR"));

        actual = ticketSearchService.getTicket(259926L);

        Assert.assertEquals(actual.getTechStatus(), TechStatus.STALLED);
        Assert.assertTrue(DateUtils.isSameDay(new Date(), actual.getTechStatusChangeDate()));
        Assert.assertEquals(historySearchService.getTicketHistory(259926L).size(), 2);
        compareWithHistory(actual);
    }

    @Test
    public void updateCustomerStatusTest() throws Exception {
        long ticketId = 259926L;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        Assert.assertEquals(historySearchService.getTicketHistory(ticketId).size(), 1);
        Assert.assertEquals(ticket.getCustomerStatus(), CustomerStatus.NEW);

        ticketService.updateCustomerStatus(ticketId, CustomerStatus.CANCELLED, TestOpInfo.DEFAULT);

        ticket = ticketSearchService.getTicket(ticketId);
        Assert.assertEquals(ticket.getCustomerStatus(), CustomerStatus.CANCELLED);
        Assert.assertTrue(DateUtils.isSameDay(new Date(), ticket.getCustomerStatusChangeDate()));
        Assert.assertEquals(historySearchService.getTicketHistory(ticketId).size(), 2);
        compareWithHistory(ticket);
    }

    @Test
    public void createRegistrationTicketCheckSubcategory() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        String domainName = "testcreateticketdomain.ie";
        TicketRequest ticketRequest = Helper.prepareTicketRequest(domainName, "IDL2-IEDR");
        long newTicketId = ticketService.createRegistrationTicket(user, ticketRequest, 101L);

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);
        Assert.assertNull(ticketFromDB.getOperation().getDomainHolderSubcategoryField().getNewValue());
    }

    @Test
    public void createTransferTicketCheckSubcategory() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        String domainName = "domain-with-subcategory-to-transfer.ie";
        Domain domain = domainSearchService.getDomain(domainName);
        TicketRequest ticketRequest = Helper.prepareTicketRequest(domainName, "IDL2-IEDR");
        long newTicketId = ticketService.createTransferTicket(user, ticketRequest, 101L);

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);
        Assert.assertEquals(ticketFromDB.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                domain.getHolderSubcategory().getId());
    }

    @Test
    public void createModificationTicketCheckSubcategory() throws Exception {
        AuthenticatedUser user = authenticationService
                .authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        String domainName = "domain-with-subcategory.ie";
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = ticketService.createModificationTicket(user, domainName, "New holder",
                getContactsAsStrings(domain.getAdminContacts()), getContactsAsStrings(domain.getTechContacts()),
                domain.getNameservers(), "Remark");

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);
        Assert.assertEquals(ticketFromDB.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                domain.getHolderSubcategory().getId());
    }

    @Test
    public void testUpdateAsOwnerWithoutEntityChange() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(TICKET_WITH_SUBCATEGORY_ID);
        TicketEdit ticketEdit = prepareTicketEdit(TICKET_WITH_SUBCATEGORY_ID);
        ticketEdit.getDomainHolderField().setNewValue("New holder");
        ticketService
                .updateAsOwner(TICKET_WITH_SUBCATEGORY_ID, ticketEdit, "Remark", new TestOpInfo("SAM-IEDR", "Remark"),
                        true, false);
        Ticket updatedTicket = ticketSearchService.getTicket(TICKET_WITH_SUBCATEGORY_ID);
        Assert.assertEquals(updatedTicket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                ticket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId());
    }

    @Test
    public void testUpdateAsAdminCheckSubcategory() throws Exception {
        OpInfo opInfo = new TestOpInfo("IDL2-IEDR", "Remark");
        TicketEdit ticketEdit = prepareTicketEdit(TICKET_WITH_SUBCATEGORY_ID);
        ticketEdit.getDomainHolderClassField().setNewValue(2L);
        ticketEdit.getDomainHolderCategoryField().setNewValue(11L);
        ticketEdit.getDomainHolderSubcategoryField().setNewValue(2L);
        ticketService.checkOut(TICKET_WITH_SUBCATEGORY_ID, opInfo);
        ticketService.updateAsAdmin(TICKET_WITH_SUBCATEGORY_ID, ticketEdit, "Remark", opInfo, true, false);
        Ticket updatedTicket = ticketSearchService.getTicket(TICKET_WITH_SUBCATEGORY_ID);
        Assert.assertEquals(updatedTicket.getOperation().getDomainHolderClassField().getNewValue().getId(),
                ticketEdit.getDomainHolderClassField().getNewValue().longValue());
        Assert.assertEquals(updatedTicket.getOperation().getDomainHolderCategoryField().getNewValue().getId(),
                ticketEdit.getDomainHolderCategoryField().getNewValue().longValue());
        Assert.assertEquals(updatedTicket.getOperation().getDomainHolderSubcategoryField().getNewValue().getId(),
                ticketEdit.getDomainHolderSubcategoryField().getNewValue().longValue());
    }

    private void testUpdateNameservers(List<NameserverChange> expectedNameservers) throws Exception {
        List<HistoricalObject<Ticket>> history = historySearchService.getTicketHistory(TEST_TICKET_ID);
        Assert.assertEquals(history.size(), 1);

        TicketEdit ticketEdit = prepareTicketEdit(TEST_TICKET_ID);
        ticketEdit.setNameserversField(getCopy(expectedNameservers));

        ticketService.updateAsAdmin(TEST_TICKET_ID, ticketEdit, null, new TestOpInfo("GEORGE-IEDR", "Eight nss"), true,
                false);
        Ticket dbTicket = ticketSearchService.getTicket(TEST_TICKET_ID);
        TicketHelper
                .compareNameserverChanges(dbTicket.getOperation().getNameserversField(), getCopy(expectedNameservers));
        history = historySearchService.getTicketHistory(TEST_TICKET_ID);
        Assert.assertEquals(history.size(), 2);
        TicketHelper.compareNameserverChanges(history.get(1).getObject().getOperation().getNameserversField(),
                getExpectedPrevNameservers());
        TicketHelper.compareNameserverChanges(history.get(0).getObject().getOperation().getNameserversField(),
                getCopy(expectedNameservers));
    }

    private List<NameserverChange> getCopy(List<NameserverChange> nsList) {
        List<NameserverChange> copy = new ArrayList<>();
        for (NameserverChange ns : nsList) {
            copy.add(nsChange(ns.getName().getNewValue(), ns.getName().getFailureReason(),
                    ns.getIpv4Address().getNewValue(), ns.getIpv4Address().getFailureReason(),
                    ns.getIpv6Address().getNewValue(), ns.getIpv6Address().getFailureReason()));
        }
        return copy;
    }

    private List<NameserverChange> getExpectedPrevNameservers() {
        return Arrays.asList(nsChange("NS1.JNJ.COM", null, null), nsChange("NS3.JNJ.COM", null, null),
                nsChange("NS5.JNJ.COM", null, null));
    }

    private NameserverChange nsChange(int i) {
        return nsChange(nameserverName(i), null, null);
    }

    private NameserverChange nsChange(String nameserverName, /*>>>@Nullable*/ String ipv4, /*>>>@Nullable*/
            String ipv6) {
        return nsChange(nameserverName, null, ipv4, null, ipv6, null);
    }

    private NameserverChange nsChange(String nameserverName,
    /*>>>@Nullable*/ FailureReason nameFailure,
    /*>>>@Nullable*/ String ipv4,
    /*>>>@Nullable*/ IpFailureReason ipv4Failure,
    /*>>>@Nullable*/ String ipv6,
    /*>>>@Nullable*/ IpFailureReason ipv6Failure) {
        return new NameserverChange(new SimpleDomainFieldChange<>(null, nameserverName, nameFailure),
                new IpFieldChange(null, ipv4, ipv4Failure), new IpFieldChange(null, ipv6, ipv6Failure));
    }

    private NameserverChange nsGlueChange(int i, String domainName) {
        return nsGlueChange(i, i, domainName);
    }

    private NameserverChange nsGlueChange(int i, int j, String domainName) {
        return nsChange(nameserverGlueName(i, domainName), nameserverIpv4(j), nameserverIpv6(j));
    }

    private String nameserverName(int i) {
        return nameserverGlueName(i, "example.ie");
    }

    private String nameserverGlueName(int i, String domainName) {
        return "n" + Integer.toString(i) + "." + domainName;
    }

    private String nameserverIpv4(int i) {
        return "192.168.0." + Integer.toString(i);
    }

    private String nameserverIpv6(int i) {
        return "::" + Integer.toString(i);
    }

    private List<String> getContactsAsStrings(List<Contact> contacts) {
        return new ArrayList<>(Collections2.transform(contacts, new Function<Contact, String>() {
            @Override
            public String apply(Contact contact) {
                return contact.getNicHandle();
            }
        }));
    }
}
