package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class FinanceLeadTest extends AbstractPermissionGroupTest {

    AuthenticatedUser financeLead;

    @BeforeMethod
    public void setUp() throws Exception {
        financeLead = authenticationService.authenticate("JB007-IEDR", "Passw0rd!", false, "1.1.1.1", false, null,
                true, "ws");
    }

    @Test
    public void getAccount() throws Exception {
        getAccount(financeLead);
    }

    @Test
    public void getAccountHistory() throws Exception {
        getAccountHistory(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws Exception {
        alterStatusAccount(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccount() throws Exception {
        saveAccount(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createAccount() throws Exception {
        createAccount(financeLead);
    }

    @Test
    public void viewDomain() throws Exception {
        viewDomain(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editDomain() throws Exception {
        editDomain(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveDomain() throws Exception {
        saveDomain(financeLead);
    }

    @Test
    public void getNicHandle() throws Exception {
        getNicHandle(financeLead);
    }

    @Test
    public void getNicHandleHistory() throws Exception {
        getNicHandleHistory(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandle() throws Exception {
        alterStatusNicHandle(financeLead);
    }

    @Test
    public void saveNicHandle() throws Exception {
        saveNicHandle(financeLead);
    }

    @Test
    public void saveNewPasswordNicHandle() throws Exception {
        saveNewPasswordNicHandle(financeLead, "JB007-IEDR");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateNewPasswordNicHandle() throws Exception {
        generateNewPasswordNicHandle(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createNicHandle() throws Exception {
        createNicHandle(financeLead);
    }

    @Test
    public void viewTicket() throws Exception {
        viewTicket(financeLead);
    }

    @Test
    public void viewTicketHistory() throws Exception {
        viewTicketHistory(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reviseTicket() throws Exception {
        reviseTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editTicket() throws Exception {
        editTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() throws Exception {
        checkOutTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() throws Exception {
        checkInTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() throws Exception {
        alterStatusTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() throws Exception {
        reassignTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveTicket() throws Exception {
        saveTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() throws Exception {
        acceptTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() throws Exception {
        rejectTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateTicket() throws Exception {
        updateTicket(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {
        changePermissionGroups(financeLead);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getUserHistory() throws Exception {
        getUserHistory(financeLead);
    }

}
