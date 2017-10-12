package com.iedr.bpr.tests.crsscheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.CharityPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UC030 extends SeleniumTest {

    private String initialCrsschedulerCkdns;
    private String initialCrswebCkdns;

    public UC030(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc030_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        List<String> nonAuthoritativeDomains = Arrays.asList("uc030-sc01.ie", "uc030-sc02.ie", "uc030-sc03.ie");
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(nonAuthoritativeDomains);
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(nonAuthoritativeDomains);
    }

    @Override
    public void tearDown() throws Exception {
        try {
            ssh().crsweb.restoreCkdnsScript(initialCrswebCkdns);
            ssh().scheduler.restoreCkdnsScript(initialCrsschedulerCkdns);
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void uc030_sc01() throws SQLException, JSchException, IOException {
        User user = this.registrar;
        String domainName = "uc030-sc01.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        assertFalse(ttp.triplePassTicketAccept(domainName));
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        ssh().scheduler.spoofCkDnsScript(new ArrayList<String>());
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        checkDomainCreated(domainName, true);
    }

    @Test
    public void uc030_sc02() throws SQLException {
        User user = this.registrar;
        String domainName = "uc030-sc02.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        assertFalse(ttp.triplePassTicketAccept(domainName));
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        checkDomainCreated(domainName, false);
    }

    @Test
    public void uc030_sc03() throws SQLException, JSchException, IOException {
        User user = this.direct;
        String domainName = "uc030-sc03.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, new CharityPaymentDetails("123"));
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        assertFalse(ttp.triplePassTicketAccept(domainName));
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        ssh().scheduler.spoofCkDnsScript(new ArrayList<String>());
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        checkDomainCreated(domainName, true);
    }

    @Test
    public void uc030_sc05() throws SQLException {
        User user = this.registrar;
        String domainName = "uc030-sc05.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.VALID_CREDIT_CARD);
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        db().invalidateTransaction(transactionId, new Date());
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        assertFalse(ttp.triplePassTicketAccept(domainName));
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
        db().validateTransaction(transactionId);
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        checkDomainCreated(domainName, true);
    }

    private void checkDomainCreated(String domainName, boolean created) throws SQLException {
        Map<String, String> exactCriteria = new HashMap<String, String>();
        exactCriteria.put("D_Name", domainName);
        int count = db().getDomainsCount(exactCriteria, new HashMap<String, List<Date>>(), true);
        if (created) {
            assertEquals("Domain not created on retry", 1, count);
        } else {
            assertEquals("Domain created on retry", 0, count);
        }
    }

}
