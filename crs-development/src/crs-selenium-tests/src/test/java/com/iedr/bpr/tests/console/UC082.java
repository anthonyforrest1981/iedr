package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.console.DomainDnsModificationPage;
import com.iedr.bpr.tests.pages.console.DomainDnsPage;
import com.iedr.bpr.tests.pages.console.EditNicHandlePage;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC082 extends SeleniumTest {

    public UC082(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc082_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc082_data.sql";
    }

    @Test
    public void test_uc082_sc01_registrar() throws SQLException {
        User contact = new User("YYY001-IEDR", "Passw0rd!", false, "uc082_registrar_main@iedr.ie");
        User owner = this.registrar;
        _test_uc082_sc01(contact, owner, "uc082-billable-registrar.ie");
    }

    @Test
    public void test_uc082_sc01_direct() throws SQLException {
        User contact = new User("YYY002-IEDR", "Passw0rd!", false, "uc082_direct_main@iedr.ie");
        User owner = this.direct;
        _test_uc082_sc01(contact, owner, "uc082-billable-direct.ie");
    }

    private void _test_uc082_sc01(User contact, User owner, String domainName) throws SQLException {
        emails.add(getDnsModificationEmail(contact, owner, domainName));
        console().login(contact);
        console().checkTacMenuItems();
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.modifyDns(new NameserverFormDetails(Arrays.asList(new DomainNameServer("ns1.dns.ie"),
                new DomainNameServer("ns2.dns.ie"))));
        assertEquals(0, db().getTicketsCountForDomain(domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc082_sc02_registrar() throws SQLException {
        User contact = new User("YYY001-IEDR", "Passw0rd!", false, "uc082_registrar_main@iedr.ie");
        User owner = this.registrar;
        _test_uc082_sc02(contact, owner);
    }

    @Test
    public void test_uc082_sc02_direct() throws SQLException {
        User contact = new User("YYY002-IEDR", "Passw0rd!", false, "uc082_direct_main@iedr.ie");
        User owner = this.direct;
        _test_uc082_sc02(contact, owner);
    }

    private void _test_uc082_sc02(User contact, User owner) throws SQLException {
        String modifiedName = "UC082 modified";
        String modifiedEmail = "uc082_modified@iedr.ie";
        console().login(contact);
        console().checkTacMenuItems();
        EditNicHandlePage enhp = new EditNicHandlePage();
        NicHandleDetails details = new NicHandleDetails(modifiedName, modifiedEmail);
        enhp.editProfile(contact, details);
        assertEquals(modifiedName, db().getNicHandleName(contact.login));
        contact.email = modifiedEmail;
        emails.add(getNhDetailsAmendedEmail(owner, contact));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc082_sc03_registrar() {
        User contact = new User("YYY001-IEDR", "Passw0rd!", false, "uc082_registrar_main@iedr.ie");
        _test_uc082_sc03(contact, "uc082-billable-registrar.ie");
    }

    @Test
    public void test_uc082_sc03_direct() {
        User contact = new User("YYY002-IEDR", "Passw0rd!", false, "uc082_direct_main@iedr.ie");
        _test_uc082_sc03(contact, "uc082-billable-direct.ie");
    }

    private void _test_uc082_sc03(User contact, String techContactDomainName) {
        console().login(contact);
        console().checkTacMenuItems();

        // Check if admin buttons are hidden for tech contact.
        ViewDomainUtils.viewDomain(techContactDomainName, ContactType.TECH);
        console().assertElementNotPresent(ViewDomainPage.enterToNrpButton);
        console().assertElementNotPresent(ViewDomainPage.modificationButton);
    }

    @Test
    public void test_uc082_nosc01() {
        // UC#082: Tech Role - Tech user edits own contacts details - Email validation
        User user = this.adminContact;
        console().login(user);
        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.view(user);
        NicHandleUtils.verifyEmail(enhp.nicHandleForm);
    }

    private ExpectedEmailSummary getDnsModificationEmail(User contact, User billC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getDnsModificationEmail(contact, billC, billC, contact, true, domainName);
    }

    private ExpectedEmailSummary getNhDetailsAmendedEmail(User billC, User contact) throws SQLException {
        User registrar = billC.isRegistrar ? billC : null;
        return emailSummaryGenerator.getNhDetailsUpdatedTacEmail(billC, contact, registrar, contact);
    }

}
