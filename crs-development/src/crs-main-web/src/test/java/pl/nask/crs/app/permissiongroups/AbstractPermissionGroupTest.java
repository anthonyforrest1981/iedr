package pl.nask.crs.app.permissiongroups;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.app.NicHandleTestHelp;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

/**
 * Calls service methods to check, if the user has enough privileges to do this. As it only tests privileges, all
 * exceptions other than AccessDeniedException are ignored
 *
 * @author Marianna Mysiorska, Artur Gniadzik
 */
public abstract class AbstractPermissionGroupTest extends AbstractCrsTest {

    public static final String REMARK = "remark";
    public static final long ACC_ID = 1L;
    public static final long TICKET_ID = 256625L;
    public static final long TICKET_OUT_ID = 256744L;
    public static final String NIC_ID = "AA11-IEDR";
    public static final String DOMAIN_NAME = "Tommy.ie";
    public static final AccountStatus STATUS = AccountStatus.Active;
    public static final NicHandleStatus NIC_STATUS = NicHandleStatus.Active;

    @Resource(name = "authenticationService")
    AuthenticationService authenticationService;
    @Autowired
    AccountAppService accountAppService;
    @Autowired
    DomainAppService domainAppService;
    @Autowired
    NicHandleAppService nicHandleAppService;
    @Autowired
    TicketAppService ticketAppService;
    @Autowired
    UserAppService userAppService;
    @Autowired
    DomainDAO domainDAO;
    @Autowired
    NicHandleDAO nicHandleDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    CommonAppService commonAppService;
    @Autowired
    ReportsAppService reportsAppService;

    protected void getAccount(AuthenticatedUser user) throws Exception {
        accountAppService.get(user, ACC_ID);
    }

    protected void getAccountHistory(AuthenticatedUser user) throws Exception {
        accountAppService.history(user, ACC_ID);
    }

    protected void alterStatusAccount(AuthenticatedUser user) throws Exception {
        accountAppService.alterStatus(user, ACC_ID, STATUS, REMARK);
    }

    protected void saveAccount(AuthenticatedUser user) throws Exception {
        Account account = accountDAO.get(ACC_ID);
        AccountWrapper accountWrapper = new AccountWrapper(account);
        accountAppService.save(user, account, accountWrapper, REMARK);
    }

    protected void createAccount(AuthenticatedUser user) throws Exception {
        CreateAccountContener cac = new CreateAccountContener("a", "a", "AAA442-IEDR", "a", false, false);
        accountAppService.create(user, cac, REMARK);
    }

    protected void viewDomain(AuthenticatedUser user) throws Exception {
        domainAppService.view(user, DOMAIN_NAME);
    }

    protected void viewDomain(AuthenticatedUser user, String domainName) throws Exception {
        domainAppService.view(user, domainName);
    }

    protected void editDomain(AuthenticatedUser user, String domainName) throws Exception {
        domainAppService.edit(user, domainName);
    }

    protected void editDomain(AuthenticatedUser user) throws Exception {
        domainAppService.edit(user, DOMAIN_NAME);
    }

    protected void saveDomain(AuthenticatedUser user) throws Exception {
        Domain domain = domainDAO.get(DOMAIN_NAME);
        domainAppService.save(user, domain);
    }

    protected void saveDomain(AuthenticatedUser user, String domainName) throws Exception {
        Domain domain = domainDAO.get(domainName);
        domain.setRemark("new remark");
        domainAppService.save(user, domain);
    }

//    protected void alterStatusDomain(AuthenticatedUser user) throws Exception{
//        domainAppService.alterStatus(user, DOMAIN_NAME, REMARK, DOMAIN_STATUS);
//    }
//
//    protected void alterStatusDomain(AuthenticatedUser user, String domainName) throws Exception{
//        domainAppService.alterStatus(user, domainName, REMARK, DOMAIN_STATUS);
//    }

//    protected void transferDomain(AuthenticatedUser user) throws Exception{
//        Domain domain = domainDAO.get(DOMAIN_NAME);
//        domain.getBillingContacts().set(0, new Contact("AAE359-IEDR"));
//        domainAppService.transfer(user, domain);
//    }
//
//    protected void transferDomain(AuthenticatedUser user, String domainName) throws Exception{
//        Domain domain = domainDAO.get(domainName);
//        domain.setRemark("remark");
//        domainAppService.transfer(user, domain);
//    }

    protected void getNicHandle(AuthenticatedUser user) throws Exception {
        nicHandleAppService.get(user, NIC_ID);
    }

    protected void getNicHandle(AuthenticatedUser user, String nh) throws Exception {
        nicHandleAppService.get(user, nh);
    }

    protected void getNicHandleHistory(AuthenticatedUser user) throws Exception {
        nicHandleAppService.history(user, NIC_ID, 0, 0);
    }

    protected void getNicHandleHistory(AuthenticatedUser user, String nh) throws Exception {
        nicHandleAppService.history(user, nh, 0, 0);
    }

    protected void alterStatusNicHandle(AuthenticatedUser user) throws Exception {
        nicHandleAppService.alterStatus(user, NIC_ID, NIC_STATUS, REMARK);
    }

    protected void alterStatusNicHandle(AuthenticatedUser user, String nh) throws Exception {
        nicHandleAppService.alterStatus(user, nh, NIC_STATUS, REMARK);
    }

    protected void saveNicHandle(AuthenticatedUser user) throws Exception {
        NewNicHandle newNicHandle = NicHandleTestHelp.createNewNicHandle();
        nicHandleAppService.modifyNicHandle(user, NIC_ID, ACC_ID, newNicHandle, REMARK);
    }

    protected void saveNicHandleOwnAccount(AuthenticatedUser user, String nh) throws Exception {
        NewNicHandle newNicHandle = NicHandleTestHelp.createNewNicHandle();
        nicHandleAppService.modifyNicHandleOwnAccount(user, nh, newNicHandle, REMARK);
    }

    protected void saveNicHandleOwnAccountViolation(AuthenticatedUser user) throws Exception {
        NewNicHandle newNicHandle = NicHandleTestHelp.createNewNicHandle();
        nicHandleAppService.modifyNicHandleOwnAccount(user, NIC_ID, newNicHandle, REMARK);
    }

    protected void saveNewPasswordNicHandle(AuthenticatedUser user) throws Exception {
        nicHandleAppService.saveNewPassword(user, NIC_ID, "123456Az.", "123456Az.");
    }

    protected void saveNewPasswordNicHandle(AuthenticatedUser user, String nh) throws Exception {
        nicHandleAppService.saveNewPassword(user, nh, "123456Az.", "123456Az.");
    }

    protected void generateNewPasswordNicHandle(AuthenticatedUser user) throws Exception {
        nicHandleAppService.resetPassword(user, NIC_ID, "1.1.1.1");
    }

    protected void generateNewPasswordNicHandle(AuthenticatedUser user, String nh) throws Exception {
        nicHandleAppService.resetPassword(user, nh, "1.1.1.1");
    }

    protected void createNicHandleOwnAccount(AuthenticatedUser user, String nhid) throws Exception {
        NicHandleWrapper wrapper = new NicHandleWrapper(nicHandleDAO.get(nhid));
        nicHandleAppService.createNicHandleOwnAccount(user, wrapper.makeNewNicHandle(), REMARK, true);
    }

    protected void createNicHandle(AuthenticatedUser user) throws Exception {
        NicHandleWrapper wrapper = new NicHandleWrapper(nicHandleDAO.get(NIC_ID));
        nicHandleAppService.createNicHandle(user, 101L, wrapper.makeNewNicHandle(), REMARK);
    }

    protected void viewTicket(AuthenticatedUser user) throws Exception {
        ticketAppService.view(user, TICKET_ID);
    }

    protected void viewTicket(AuthenticatedUser user, long ticketId) throws Exception {
        ticketAppService.view(user, ticketId);
    }

    protected void viewTicketHistory(AuthenticatedUser user) throws Exception {
        ticketAppService.history(user, TICKET_ID);
    }

    protected void viewTicketHistory(AuthenticatedUser user, long ticketId) throws Exception {
        ticketAppService.history(user, ticketId);
    }

    protected void reviseTicket(AuthenticatedUser user) throws Exception {
        ticketAppService.revise(user, TICKET_ID);
    }

    protected void reviseTicket(AuthenticatedUser user, long ticketId) throws Exception {
        ticketAppService.revise(user, ticketId);
    }

    protected void editTicket(AuthenticatedUser user) throws Exception {
        ticketAppService.edit(user, TICKET_ID);
    }

    protected void editTicket(AuthenticatedUser user, long ticketId) throws Exception {
        ticketAppService.edit(user, ticketId);
    }

    protected void checkOutTicket(AuthenticatedUser user) throws Exception {
        ticketAppService.checkOut(user, TICKET_ID);
    }

    protected void checkOutTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.checkOut(user, ticketId);
        } catch (TicketAlreadyCheckedOutException ex) {
        }
    }

    protected void checkInTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.checkIn(user, TICKET_OUT_ID, AdminStatus.PASSED);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void checkInTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.checkIn(user, ticketId, AdminStatus.PASSED);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void alterStatusTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.alterStatus(user, TICKET_OUT_ID, AdminStatus.PASSED);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void alterStatusTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.alterStatus(user, ticketId, AdminStatus.PASSED);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void reassignTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.reassign(user, TICKET_OUT_ID, NIC_ID);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
        }
    }

    protected void reassignTicket(AuthenticatedUser user, long ticketId, String nhid) throws Exception {
        ticketAppService.reassign(user, ticketId, nhid);
    }

    protected void saveTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.save(user, TICKET_OUT_ID, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        } catch (IllegalArgumentException ex) {
        }
    }

    protected void saveTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.save(user, ticketId, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        } catch (IllegalArgumentException ex) {
        }
    }

    protected void acceptTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.accept(user, TICKET_OUT_ID, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void acceptTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.accept(user, ticketId, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void rejectTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.reject(user, TICKET_OUT_ID, null, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void rejectTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.reject(user, ticketId, null, null, REMARK);
        } catch (TicketCheckedOutToOtherException ex) {
        } catch (InvalidStatusException ex) {
        }
    }

    protected void updateTicket(AuthenticatedUser user) throws Exception {
        try {
            ticketAppService.updateAsAdmin(user, TICKET_OUT_ID, null, null, REMARK, false, false);
        } catch (TicketCheckedOutToOtherException ex) {
        }
    }

    protected void updateTicket(AuthenticatedUser user, long ticketId) throws Exception {
        try {
            ticketAppService.updateAsAdmin(user, ticketId, null, null, REMARK, false, false);
        } catch (TicketCheckedOutToOtherException ex) {
        } catch (TicketEditFlagException ex) {
        }
    }

    protected void changePermissionGroups(AuthenticatedUser user) throws Exception {
        User user2 = userDAO.get("AA11-IEDR");
        userAppService.changePermissionGroups(user, user2.getUsername(), user2.getPermissionGroups());
    }

    protected void getUserHistory(AuthenticatedUser user) throws Exception {
        userAppService.getHistory(user, null, 0, 0);
    }

    protected void getHmUsage(AuthenticatedUser user) throws Exception {
        try {
            reportsAppService.search(user, null, 0, 10, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract public void getAccount() throws Exception;

    abstract public void getAccountHistory() throws Exception;

    abstract public void alterStatusAccount() throws Exception;

    abstract public void saveAccount() throws Exception;

    abstract public void createAccount() throws Exception;

    abstract public void viewDomain() throws Exception;

    abstract public void editDomain() throws Exception;

    abstract public void saveDomain() throws Exception;

    //    abstract public void transferDomain() throws Exception;

    abstract public void getNicHandle() throws Exception;

    abstract public void getNicHandleHistory() throws Exception;

    abstract public void alterStatusNicHandle() throws Exception;

    abstract public void saveNicHandle() throws Exception;

    abstract public void saveNewPasswordNicHandle() throws Exception;

    abstract public void generateNewPasswordNicHandle() throws Exception;

    abstract public void createNicHandle() throws Exception;

    abstract public void viewTicket() throws Exception;

    abstract public void viewTicketHistory() throws Exception;

    abstract public void reviseTicket() throws Exception;

    abstract public void editTicket() throws Exception;

    abstract public void checkOutTicket() throws Exception;

    abstract public void checkInTicket() throws Exception;

    abstract public void alterStatusTicket() throws Exception;

    abstract public void reassignTicket() throws Exception;

    abstract public void saveTicket() throws Exception;

    abstract public void acceptTicket() throws Exception;

    abstract public void rejectTicket() throws Exception;

    abstract public void updateTicket() throws Exception;

    abstract public void changePermissionGroups() throws Exception;

    abstract public void getUserHistory() throws Exception;

}
