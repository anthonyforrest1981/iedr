package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.console.EditTicketPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.NameserverUtils;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;
import com.iedr.bpr.tests.utils.console.TicketUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UC060 extends SeleniumTest {

    public UC060(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc060_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc060_data.sql";
    }

    @Test
    public void test_uc060_sc01() throws SQLException {
        User user = this.registrar;
        String domainName = "uc060.ie";
        String holder = "New holder";
        String remark = "Ticket modification";

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        etp.holderField.fill(holder);
        etp.remarksField.fill(remark);
        etp.submitAndWaitForSuccess();
        com.iedr.bpr.tests.pages.console.ViewTicketPage vtp = new com.iedr.bpr.tests.pages.console.ViewTicketPage();
        vtp.checkFormValue("Domain Holder", holder);
        vtp.checkFormValue("Ticket Remark", remark);

        // Check if values are stored in DB.
        int ticketId = db().getTicketId(domainName);
        assertEquals(holder, db().getTicketDomainHolder(ticketId));
        assertEquals(remark, db().getTicketRemark(ticketId));
    }

    @Test
    public void test_uc060_sc02() {
        User user = this.registrar;
        String domainName = "uc060.ie";
        String holder = "New holder";
        String remark = "This is ticket modification remark";

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        etp.holderField.fill(holder);
        etp.remarksField.fill(remark);
        etp.submitAndWaitForSuccess();

        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.viewReviseAndEditTicket(domainName);
        List<WebElement> modifications = wd().findElements(By.cssSelector(".FCC-value.modification"));
        List<String> modifiedValues = new ArrayList<String>();
        for (WebElement modification : modifications) {
            modifiedValues.add(modification.getText().trim());
        }
        assertTrue(modifiedValues.toString(), modifiedValues.contains(holder));
        console().waitForTextPresentOnPage(remark);
    }

    @Test
    public void test_uc060_nosc01() {
        // UC#060: Modify Ticket - Modify Admin C / Tech C
        User user = this.registrar;
        String domainName = "uc060.ie";

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        etp.findAndFillAdminContact(this.adminContact);
        assertEquals(this.adminContact.login,
                wd().findElement(By.id("EditTicketModel_adminContact_0")).getAttribute("value"));
        etp.submitAndWaitForSuccess();
        com.iedr.bpr.tests.pages.console.ViewTicketPage vtp = new com.iedr.bpr.tests.pages.console.ViewTicketPage();
        vtp.checkFormValue("Admin Contact 1", this.adminContact.login);
    }

    @Test
    public void test_uc060_nosc02() {
        // UC#060: Modify Ticket - Modify Ticket via Console - Domain fields validation
        User user = this.registrar;
        String domainName = "uc060.ie";

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        etp.holderField.fill("");
        etp.submit();
        try {
            console().waitForTextPresentOnPage("Domain Holder cannot be blank.");
        } catch (TimeoutException e) {
            fail("Empty holder name shouldn't have passed.");
        }
        DomainContactsUtils.verifyAll(user, "UC060SU-IEDR", etp.contactsForm, etp, console());
    }

    @Test
    public void test_uc060_nosc03() {
        // UC#060: Modify Ticket - Modify Ticket via CRS-WEB - DNS validation
        User user = this.registrar;
        String domainName = "uc060.ie";

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        NameserverUtils.verifyAll(domainName, etp.nameserverForm, etp, console());
        NameserverUtils.verifyCkdns(etp.nameserverForm, console());
    }

    @Test
    public void test_uc060_sc04() throws SQLException {
        // UC#060: Modify Ticket - Modify Ticket via Console - IPv6 support
        User user = this.registrar;
        String domainName = "uc060.ie";
        DomainNameServer dns = new DomainNameServer(String.format("ns1.%s", domainName), null, "::ffff:10.10.1.1");

        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        etp.nameserverForm.fillRow(0, dns);
        etp.submitAndWaitForSuccess();
        com.iedr.bpr.tests.pages.console.ViewTicketPage vtp = new com.iedr.bpr.tests.pages.console.ViewTicketPage();
        vtp.checkFormValue("Nameserver 1", dns.name);
        vtp.checkFormValue("IPv4 Address 1", dns.ipv4);
        vtp.checkFormValue("IPv6 Address 1", dns.ipv6);

        // Check if values are stored in DB.
        int ticketId = db().getTicketId(domainName);
        assertEquals(dns, db().getDnsListForTicket(ticketId).get(0));
    }

}
