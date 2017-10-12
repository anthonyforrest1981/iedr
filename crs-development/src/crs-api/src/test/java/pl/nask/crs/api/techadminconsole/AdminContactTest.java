package pl.nask.crs.api.techadminconsole;

import org.testng.annotations.Test;

import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class AdminContactTest extends AbstractTestBase {

    @Override
    protected String getNic() {
        // A candidate for an Admin: AAI538-IEDR, AHC714-IEDR
        return "AAI538-IEDR";
    }

    @Override
    protected String getOwnDomainName() {
        return "castlebargolfclub.ie";
    }

    @Override
    protected String getNewDomainName() {
        return "admin-new-domain.ie";
    }

    @Override
    protected long getTicketId() {
        return 401000L;
    }

    // Access granted, but modification ticket is pending
    @Override
    @Test(expectedExceptions = DomainModificationPendingException.class)
    public void shouldModifyOwnDomainHolder() throws Exception {
        super.shouldModifyOwnDomainHolder();
    }

    @Override
    @Test(expectedExceptions = DomainModificationPendingException.class)
    public void shouldModifyOwnDomainAdminContacts() throws Exception {
        super.shouldModifyOwnDomainAdminContacts();
    }

    @Override
    @Test(expectedExceptions = DomainModificationPendingException.class)
    public void shouldModifyOwnDomainTechContacts() throws Exception {
        super.shouldModifyOwnDomainTechContacts();
    }

    // Only a billing contact is allowed to enter domain into VNRP
    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldEnterVnrpOwnDomain() throws Exception {
        super.shouldEnterVnrpOwnDomain();
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldLeaveVnrpOwnDomain() throws Exception {
        super.shouldLeaveVnrpOwnDomain();
    }
}
