package com.iedr.bpr.tests.crsscheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static com.iedr.bpr.tests.TestEnvironment.ssh;

public class UC040 extends SeleniumTest {

    private String initialCrsschedulerCkdns;
    private String initialCrswebCkdns;

    public UC040(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc040_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc040_data.sql";
    }

    @Test
    public void uc040_sc01() throws JSchException, IOException, SQLException {
        String domainName = "uc040-sc01.ie";
        initialCrswebCkdns = ssh().crsweb.spoofCkDnsScript(Arrays.asList(domainName));
        initialCrsschedulerCkdns = ssh().scheduler.spoofCkDnsScript(Arrays.asList(domainName));
        try {
            _uc040_sc01(domainName);
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCrswebCkdns);
            ssh().scheduler.restoreCkdnsScript(initialCrsschedulerCkdns);
        }
    }

    private void _uc040_sc01(String domainName) throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        transferDomain(user, domainName, paymentDetails);
        crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
        resetEmails();
        scheduler().runJob(SchedulerJob.DNS_CHECK_FAILURE_NOTIFICATION);
        emails.add(emailSummaryGenerator.getDnsCheckFailure(user, domainName));
        checkAndResetEmails(emails);
    }

    private void transferDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, paymentDetails);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
    }

}
