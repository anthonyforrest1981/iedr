package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.console.*;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC081 extends SeleniumTest {

    private User registrarContact = new User("ZZZ001-IEDR", "Passw0rd!", false, "uc081_registrar_main@iedr.ie");
    private User directContact = new User("ZZZ002-IEDR", "Passw0rd!", false, "uc081_direct_main@iedr.ie");

    public UC081(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc081_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc081_data.sql";
    }

    @Test
    public void test_uc081_sc01_registrar() throws SQLException {
        User contact = registrarContact;
        User owner = this.registrar;
        _test_uc081_sc01(contact, owner, "uc081-billable-registrar.ie");
    }

    @Test
    public void test_uc081_sc01_direct() throws SQLException {
        User contact = directContact;
        User owner = this.direct;
        _test_uc081_sc01(contact, owner, "uc081-billable-direct.ie");
    }

    private void _test_uc081_sc01(User contact, User owner, String domainName) throws SQLException {
        emails.add(getNrpEmail(contact, owner, domainName));
        console().login(contact);
        console().checkTacMenuItems();
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        console().assertElementNotPresent(ViewDomainPage.enterToNrpButton);
    }

    @Test
    public void test_uc081_sc02_registrar() throws SQLException {
        User contact = registrarContact;
        User owner = this.registrar;
        _test_uc081_sc02(contact, owner, "uc081-charity-registrar.ie");
    }

    @Test
    public void test_uc081_sc02_direct() throws SQLException {
        User contact = directContact;
        User owner = this.direct;
        _test_uc081_sc02(contact, owner, "uc081-charity-direct.ie");
    }

    private void _test_uc081_sc02(User contact, User owner, String domainName) throws SQLException {
        String newContactName = "UC081 new";
        String newContactEmail = "uc081_new@iedr.ie";
        emails.add(getDomainModificationEmail(contact, owner, domainName));
        console().login(contact);
        console().checkTacMenuItems();
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        NicHandleDetails details = new NicHandleDetails(newContactName, newContactEmail);
        vdp.addNewTechContact();
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        cnhp.fillNewNicForm(details);
        cnhp.submit();
        vdp.remarksField.fill("Tech contact modification");
        vdp.submitAndWaitForSuccess(domainName, true);
        String nh = db().getNicHandleByName(details.getNhName());
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(new User(nh, null, false, newContactEmail)));
        assertEquals(1, db().getTicketsCountForDomain(domainName));

        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc081_sc03_registrar() throws SQLException {
        User contact = registrarContact;
        User owner = this.registrar;
        _test_uc081_sc03(contact, owner, "uc081-billable-registrar.ie");
    }

    @Test
    public void test_uc081_sc03_direct() throws SQLException {
        User contact = directContact;
        User owner = this.direct;
        _test_uc081_sc03(contact, owner, "uc081-billable-direct.ie");
    }

    private void _test_uc081_sc03(User contact, User owner, String domainName) throws SQLException {
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
    public void test_uc081_sc04_registrar() throws SQLException {
        User contact = registrarContact;
        User owner = this.registrar;
        _test_uc081_sc04(contact, owner, "uc081-billable-registrar.ie");
    }

    @Test
    public void test_uc081_sc04_direct() throws SQLException {
        User contact = directContact;
        User owner = this.direct;
        _test_uc081_sc04(contact, owner, "uc081-billable-direct.ie");
    }

    private void _test_uc081_sc04(User contact, User owner, String domainName) throws SQLException {
        emails.add(getDomainModificationEmail(contact, owner, domainName));
        console().login(contact);
        console().checkTacMenuItems();
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        vdp.holderField.fill("Test Holder 0001 - Modified");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
        assertEquals(1, db().getTicketsCountForDomain(domainName));

        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        checkAndResetEmails(emails);
        assertEquals(0, db().getTicketsCountForDomain(domainName));
    }

    @Test
    public void test_uc081_sc05_registrar() {
        User contact = registrarContact;
        _test_uc081_sc05(contact, "uc081-charity-registrar.ie", "uc081-charity-registrar-2.ie");
    }

    @Test
    public void test_uc081_sc05_direct() {
        User contact = directContact;
        _test_uc081_sc05(contact, "uc081-charity-direct.ie", "uc081-charity-direct-2.ie");
    }

    private void _test_uc081_sc05(User contact, String adminContactDomainName, String techContactDomainName) {
        console().login(contact);
        console().checkTacMenuItems();

        // Check if admin buttons are visible for admin contact.
        ViewDomainUtils.viewDomain(adminContactDomainName, ContactType.ADMIN);
        console().assertElementNotPresent(ViewDomainPage.enterToNrpButton);
        console().assertElementPresent(ViewDomainPage.modificationButton);

        // Check if admin buttons are hidden for tech contact.
        ViewDomainUtils.viewDomain(techContactDomainName, ContactType.TECH);
        console().assertElementNotPresent(ViewDomainPage.enterToNrpButton);
        console().assertElementNotPresent(ViewDomainPage.modificationButton);
    }

    @Test
    public void test_uc081_sc06_registrar() throws SQLException {
        User contact = registrarContact;
        User owner = this.registrar;
        _test_uc081_sc06(contact, owner);
    }

    @Test
    public void test_uc081_sc06_direct() throws SQLException {
        User contact = directContact;
        User owner = this.direct;
        _test_uc081_sc06(contact, owner);
    }

    private void _test_uc081_sc06(User contact, User owner) throws SQLException {
        String modifiedName = "UC081 modified";
        String modifiedEmail = "uc081_modified@iedr.ie";
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
    public void test_uc081_sc07() {
        User contact = directContact;
        _test_uc081_sc07(contact, "uc081-sc07a.ie", "uc081-sc07b.ie");
    }

    private void _test_uc081_sc07(User contact, String domainNameA, String domainNameB) {
        console().login(contact);
        console().checkTacMenuItems();
        console().view(console().url.tickets);

        // Verify that user can see tickets he created.
        console().assertElementPresent(By.cssSelector("td[title='" + domainNameA + "']"));
        // Verify he cannot see tickets created by others, even if he is an
        // admin contact in the relevant domains.
        console().assertElementNotPresent(By.cssSelector("td[title='" + domainNameB + "']"));
        // Verify if he has the option to cancel the ticket.
        console().clickElement(By.cssSelector("td[title='" + domainNameA + "'] > a"));
        console().assertElementPresent(By.linkText("Cancel Ticket"));
    }

    @Test
    public void test_uc081_nosc01() {
        User contact = directContact;
        String domainName = "uc081-billable-direct.ie";
        console().login(contact);
        console().checkTacMenuItems();
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        // Verify user doesn't have access to Find/Edit nics links.
        console().assertElementNotPresent(By.linkText("Find"));
        console().assertElementNotPresent(By.linkText("Edit"));
    }

    @Test
    public void test_uc081_nosc02() {
        // UC#081: Admin Role - Admin user updates their own contact details  - Email validation
        User user = this.adminContact;
        console().login(user);
        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.view(user);
        NicHandleUtils.verifyEmail(enhp.nicHandleForm);
    }

    @Test
    public void test_uc081_nosc03() throws SQLException {
        // UC#081: Admin Role - Admin user edits DNS - IPv6 support
        User contact = registrarContact;
        String domainName = "uc081-nosc03.ie";
        DomainNameServer dns = new DomainNameServer(String.format("ns1.%s", domainName), null, "::ffff:10.10.1.1");
        console().login(contact);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        vdp.nameserverForm.fillRow(0, dns);
        vdp.remarksField.fill("DNS change");
        vdp.submitAndWaitForSuccess(domainName, false);
        assertEquals(dns, db().getDnsListForDomain(domainName).get(0));
    }

    private ExpectedEmailSummary getNrpEmail(User adminC, User billC, String domainName) throws SQLException {
        return emailSummaryGenerator.getNrpNotificationEmail(billC, adminC, domainName, true);
    }

    private ExpectedEmailSummary getDomainModificationEmail(User contact, User billC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getDomainModificationSuccessfulEmail(contact, billC, contact, domainName);
    }

    private ExpectedEmailSummary getDnsModificationEmail(User contact, User billC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getDnsModificationEmail(contact, billC, contact, billC, true, domainName);
    }

    private ExpectedEmailSummary getNhDetailsAmendedEmail(User billC, User contact) throws SQLException {
        User registrar = billC.isRegistrar ? billC : null;
        return emailSummaryGenerator.getNhDetailsUpdatedTacEmail(billC, contact, registrar, contact);
    }

}
