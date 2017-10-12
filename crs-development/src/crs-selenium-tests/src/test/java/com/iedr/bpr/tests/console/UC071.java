package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.ReauthorizeTransactionPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;

public class UC071 extends SeleniumTest {

    public UC071(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc071_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc071_data.sql";
    }

    @Test
    public void test_uc071_sc01() throws SQLException {
        User user = new User("UC071AA-IEDR", "Passw0rd!", false, "uc071_aa@iedr.ie");
        String domainName = "uc071-sc01.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;

        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName));
        emails.add(emailSummaryGenerator.getApplicationFailedEmail(user, true));
        emails.add(emailSummaryGenerator.getTransactionInvalidationEmail(user));
        emails.add(new DetailedExpectedEmailSummary.MatchEverything(emailSummaryGenerator.getRegistrationAcceptedEmail(
                user, user, domainName)));
        emails.add(emailSummaryGenerator.getRegistrationDnsVerifiedEmail(user, user, domainName));
        emails.add(new DetailedExpectedEmailSummary.MatchEverything(emailSummaryGenerator.getRegistrationAcceptedEmail(
                user, user, domainName)));
        emails.add(emailSummaryGenerator.getRegistrationCompletedEmail(user, user, user, domainName));

        test(user, domainName, paymentDetails);
    }

    private void test(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        registerDomain(user, domainName, paymentDetails);

        db().setVatCategory(user.login, "B");
        scheduler().runJob(SchedulerJob.TRANSACTION_INVALIDATION);

        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
        crsweb().logout();

        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        ReauthorizeTransactionPage rtp = new ReauthorizeTransactionPage();
        rtp.reauthorizeTransaction(transactionId);

        ttp.triplePassTicketAccept(domainName);
        checkAndResetEmails(emails);
    }

    private void registerDomain(User user, String domainName, PaymentDetails method) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, method, 1, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

}
