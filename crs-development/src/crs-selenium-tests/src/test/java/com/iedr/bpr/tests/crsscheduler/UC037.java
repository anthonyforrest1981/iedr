package com.iedr.bpr.tests.crsscheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.PaymentUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;

public class UC037 extends SeleniumTest {

    private String initialCrsschedulerCkdns;
    private String initialCrswebCkdns;

    public UC037(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc037_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc037_data.sql";
    }

    @Test
    public void uc037_sc01() throws JSchException, IOException, SQLException {
        String domainName = "uc037-sc01.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        try {
            _uc037_sc01(domainName);
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCrswebCkdns);
            ssh().scheduler.restoreCkdnsScript(initialCrsschedulerCkdns);
        }
    }

    private void _uc037_sc01(String domainName) throws SQLException, JSchException, IOException {
        User user = this.direct;
        transferDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        ssh().scheduler.spoofCkDnsScript(new ArrayList<String>());
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        assertEquals(user.login, db().getContactForDomain(domainName, "Billing"));
    }

    @Test
    public void uc037_sc02() throws SQLException {
        User user = this.registrar;
        String domainName = "uc037-sc02.ie";
        db().setDepositAmount(user.login, 0);
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
        console().login(user);
        PaymentUtils.topUpAccount(user, "1000");
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
    }

    @Test
    public void uc037_sc03() throws JSchException, IOException, SQLException {
        String domainName = "uc037-sc03.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        try {
            _uc037_sc03(domainName);
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCrswebCkdns);
            ssh().scheduler.restoreCkdnsScript(initialCrsschedulerCkdns);
        }
    }

    private void _uc037_sc03(String domainName) throws SQLException, JSchException, IOException {
        User user = this.registrar;
        db().setDepositAmount(user.login, 0);
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        ssh().scheduler.spoofCkDnsScript(new ArrayList<String>());
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        crsweb().login(this.internal);
        crsweb().view(SiteId.TicketsSearch);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
    }

    @Test
    public void uc037_sc04() throws SQLException {
        User user = this.registrar;
        String domainName = "uc037-sc04.ie";
        db().setDepositAmount(user.login, 0);
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        crsweb().login(this.internal);
        crsweb().view(SiteId.TicketsSearch);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
    }

    @Test
    public void uc037_sc05() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc037-sc05.ie";
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, paymentDetails);
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        db().invalidateTransaction(transactionId, new Date());
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        crsweb().login(this.internal);
        crsweb().view(SiteId.TicketsSearch);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
    }

    private void transferDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, paymentDetails);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
    }

}
