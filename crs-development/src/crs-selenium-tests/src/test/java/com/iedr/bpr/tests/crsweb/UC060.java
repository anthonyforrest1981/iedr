package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.crsweb.DomainModificationDetails;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.*;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;

public class UC060 extends SeleniumTest {

    public UC060(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc060_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc060_data.sql";
    }

    @Test
    public void test_uc060_nosc01() throws SQLException {
        // UC#060: Modify Ticket - ADMIN PASS - Hold Update
        User user = this.registrar;
        String domainName = "uc060-nosc01.ie";
        String reason = "Hold Update";
        emails.add(emailSummaryGenerator.getDomainModificationRemarksEmail(user, domainName, reason));
        emails.add(emailSummaryGenerator.getDomainModificationHoldUpEmail(user, user, domainName));
        console().login(user);
        modifyDomain(user, domainName);
        modifyTicket(domainName);
        holdUpdate(domainName, reason);
        scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc060_nosc02() throws SQLException {
        // UC#060: Modify Ticket - Modify Ticket via CRS-WEB - Basic
        User user = this.registrar;
        String domainName = "uc060-nosc02.ie";
        String newHolder = "New Holder";
        String newClass = "Sole Trader";
        String newCategory = "Discretionary Name";
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.edit(domainName);
        NameserverFormDetails dnsDetails = new NameserverFormDetails(domainName);
        DomainModificationDetails details = new DomainModificationDetails(newHolder, newClass, newCategory, "Remark",
                new DomainContactFormDetails(user), dnsDetails);
        ttp.fillTicketDetails(details);
        int ticketId = db().getTicketId(domainName);
        assertEquals(newHolder, db().getTicketDomainHolder(ticketId));
        Map<String, String> contactMap = db().getContactMapForTicket(ticketId);
        assertEquals(user.login, contactMap.get("A"));
        assertEquals(user.login, contactMap.get("T"));
        List<DomainNameServer> dnsList = db().getDnsListForTicket(ticketId);
        assertEquals(dnsDetails.getDnsList(), dnsList);
    }

    @Test
    public void test_uc060_nosc03() throws SQLException {
        // UC#060: Modify Ticket - Modify Ticket via CRS-WEB - Domain fields validation
        User user = this.adminContact;
        String domainName = "uc060-nosc03.ie";
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.edit(domainName);
        DomainContactsUtils.verifyAll(user, "UC060SU-IEDR", ttp.contactsForm, ttp, crsweb());
    }

    @Test
    public void test_uc060_nosc04() throws SQLException {
        // UC#060: Modify Ticket - Modify Ticket via CRS-WEB - IPv6 support
        String domainName = "uc060-nosc04.ie";
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.edit(domainName);
        NameserverUtils.verifyAll(domainName, ttp.nameserverForm, ttp, crsweb());
        NameserverUtils.verifyCkdns(ttp.nameserverForm, crsweb());
    }

    @Test
    public void test_uc060_nosc05() throws SQLException {
        // UC#060: Modify Ticket - Modify Ticket via CRS-WEB - IPv6 support
        String domainName = "uc060-nosc05.ie";
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.edit(domainName);
        List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName), null,
                "::ffff:10.10.1.1"),
                new DomainNameServer(String.format("ns2.%s", domainName), null, "::ffff:10.10.1.2"));
        DomainModificationDetails details = new DomainModificationDetails("Remark");
        details.setDnsDetails(new NameserverFormDetails(dnsList));
        ttp.fillTicketDetails(details);
        int ticketId = db().getTicketId(domainName);
        assertEquals(dnsList.subList(0, 2), db().getDnsListForTicket(ticketId));
    }

    private void modifyDomain(User user, String domainName) {
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.holderField.fill("Modified Holder");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
    }

    private void modifyTicket(String domainName) throws NumberFormatException, SQLException {
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.generateTicketHoldUp(domainName);
    }

    private void holdUpdate(String domainName, String reason) {
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, reason);
    }

}
