package com.iedr.bpr.tests.crsscheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;

public class UC047 extends SeleniumTest {

    private String initialCrsschedulerCkdns;
    private String initialCrswebCkdns;

    public UC047(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc047_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc047_data.sql";
    }

    @Override
    public void tearDown() throws Exception {
        try {
            restoreCkdns();
        } finally {
            super.tearDown();
        }
    }

    private void restoreCkdns() throws JSchException, IOException {
        if (initialCrswebCkdns != null) {
            ssh().crsweb.restoreCkdnsScript(initialCrswebCkdns);
        }
        if (initialCrsschedulerCkdns != null) {
            ssh().scheduler.restoreCkdnsScript(initialCrsschedulerCkdns);
        }
    }

    @Test
    public void uc047_nosc01() throws JSchException, IOException, SQLException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - scenario 1
        User user = this.registrar;
        String domainName = "uc047-sc01.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        crsweb().login(this.internal);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.performDnsCheck(domainName);
        crsweb().waitForTextPresentOnPage("DNS Check complete: failed");
        ExpectedEmailSummary e = emailSummaryGenerator.getDnsCheckFailure(user, domainName);
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        assertEquals(0, db().getDnsCheckNotificationsCountForDomain(domainName));
        assertEquals(0, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
    }

    @Test
    public void uc047_nosc02() throws JSchException, IOException, SQLException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - scenario 2
        String domainName = "uc047-sc02.ie";
        User user = this.registrar;
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        registerDomain(user, domainName);
        ExpectedEmailSummary e = emailSummaryGenerator.getDnsCheckFailure(user, domainName);
        assertEquals(0, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        triplePass(domainName);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        assertEquals(0, db().getDnsCheckNotificationsCountForDomain(domainName));
    }

    @Test
    public void uc047_nosc03() throws JSchException, IOException, SQLException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - scenario 3
        User user = this.registrar;
        String domainName = "uc047-sc03.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        registerDomain(user, domainName);
        triplePass(domainName);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        ExpectedEmailSummary e = emailSummaryGenerator.getDnsCheckFailure(user, domainName);
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, "10.10.1.3");
        ssh().crsweb.spoofCkDnsScript(map);
        ssh().scheduler.spoofCkDnsScript(map);
        crsweb().login(this.internal);
        editTicketDnsAndAccept(domainName, "10.10.1.3");
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        assertEquals(0, db().getDnsCheckNotificationsCountForDomain(domainName));
    }

    @Test
    public void uc047_nosc04() throws JSchException, IOException, SQLException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - scenario 4
        User user = this.registrar;
        String domainName = "uc047-sc04.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        registerDomain(user, domainName);
        triplePass(domainName);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, "10.10.1.3");
        ssh().crsweb.spoofCkDnsScript(map);
        ssh().scheduler.spoofCkDnsScript(map);
        editTicketDnsAndAccept(domainName, "10.10.1.3");
        ExpectedEmailSummary e = emailSummaryGenerator.getDnsCheckFailure(user, domainName);
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        assertEquals(0, db().getDnsCheckNotificationsCountForDomain(domainName));
    }

    @Test
    public void uc047_nosc05() throws JSchException, IOException, SQLException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check - Scheduler - IPv6 support
        User user = this.registrar;
        String domainName = "uc047-sc05.ie";
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, "::ffff:10.10.1.1");
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(map);
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(map);
        crsweb().login(this.internal);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.performDnsCheck(domainName);
        crsweb().waitForTextPresentOnPage("DNS Check complete: failed");
        ExpectedEmailSummary e = emailSummaryGenerator.getDnsCheckFailure(user, domainName);
        assertEquals(1, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());
        assertEquals(0, db().getDnsCheckNotificationsCountForDomain(domainName));
        assertEquals(0, countEmailsFromJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION, Arrays.asList(e)).get(e.id)
                .intValue());

    }

    private void registerDomain(User user, String domainName) throws SQLException {
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
    }

    private void triplePass(String domainName) {
        crsweb().login(this.internal);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
    }

    private void editTicketDnsAndAccept(String domainName, String dns) {
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.viewReviseAndEditTicket(domainName);
        crsweb().clickElement(By.id("ticketrevise-input_forceEdit"));
        if (crsweb().isElementPresent(By.id("ticketrevise-input__edit"))) {
            crsweb().clickElement(By.id("ticketrevise-input__edit"));
        }
        crsweb().waitForTextPresentOnPage("Ticket Edit");
        crsweb().fillInput(By.name("ticketWrapper.newNameserverWrappers[0].ipv4"), dns);
        crsweb().fillInput(By.id("hostmasterMessage"), "Remark");
        crsweb().clickElement(By.id("ticketedit-input__forceSave"));
        crsweb().waitForTextPresentOnPage("Ticket Revise");
        crsweb().clickElement(By.id("ticketrevise-input__accept"));
    }

}
