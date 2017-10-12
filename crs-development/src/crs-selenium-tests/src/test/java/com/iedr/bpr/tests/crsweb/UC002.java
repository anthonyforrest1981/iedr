package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.CharityPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC002 extends SeleniumTest {

    public UC002(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc002_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc002_data.sql";
    }

    @Test
    public void test_uc002_sc01() throws SQLException {
        User user = this.registrarNonVat;
        PaymentDetails paymentMethod = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        String domainName = "uc002-sc01.ie";
        TriplePass triplePass = new HappyTriplePass(paymentMethod.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentMethod.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentMethod, triplePass);
    }

    @Test
    public void test_uc002_sc02() throws SQLException {
        User user = new User("UC002AA-IEDR", "Passw0rd!", false, "uc002_aa@iedr.ie");
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-sc02.ie";
        TriplePass triplePass = new HappyTriplePass(paymentDetails.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_sc04() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        String domainName = "uc002-sc04.ie";
        TriplePass triplePass = new HappyTriplePass(paymentDetails.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_sc05() throws JSchException, IOException, SQLException {
        String domainName = "uc002-sc05.ie";
        List<String> nonAuthoritativeDomains = Arrays.asList(domainName);
        String initialCkDnsScript = ssh().crsweb.spoofCkDnsScript(nonAuthoritativeDomains);
        try {
            _test_uc002_sc05(domainName);
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    private void _test_uc002_sc05(String domainName) throws SQLException {
        User user = this.registrarNonVat;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TriplePass triplePass = new TriplePass(true, false) {
            @Override
            public void before(String domainName) throws SQLException {}

            @Override
            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_sc06() throws SQLException {
        final User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        String domainName = "uc002-sc06.ie";
        TriplePass triplePass = new TriplePass(true, false) {
            @Override
            public void before(String domainName) throws SQLException {
                db().setDepositAmount(user.login, 0);
            }

            @Override
            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationInsufficientFundsEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_sc07() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        String domainName = "uc002-sc07.ie";
        String reason = "Hold Paperwork";
        TriplePass triplePass = new TriplePassReject(reason);
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getTicketRejectionEmail(user, domainName, reason));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_sc10() throws SQLException {
        User user = new User("UC002AA-IEDR", "Passw0rd!", false, "uc002_aa@iedr.ie");
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-sc10.ie";
        TriplePass triplePass = new TriplePass(true, false) {
            @Override
            public void before(String domainName) throws SQLException {
                int reservationId = db().getReservationId(domainName);
                int transactionId = db().getReservationTransactionId(reservationId);
                db().invalidateTransaction(transactionId, new Date());
            }

            @Override
            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_qa1232() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-qa1232.ie";
        TriplePass triplePass = new HappyTriplePass(paymentDetails.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Ignore
    @Test
    public void test_uc002_qa1234() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-qa1234.ie";
        TriplePass triplePass = new TriplePass(true, false) {
            @Override
            public void before(String domainName) throws SQLException {
                int reservationId = db().getReservationId(domainName);
                int transactionId = db().getReservationTransactionId(reservationId);
                db().invalidateTransactionRealexAuthcode(transactionId);
            }

            @Override
            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Failed");
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_qa12310() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        String domainName = "uc002-qa12310.ie";
        String reason = "Cancelled";
        TriplePass triplePass = new TriplePassReject(reason);
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getTicketRejectionEmail(user, domainName, reason));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_qa12311() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-qa12311.ie";
        String reason = "Hold Update";
        TriplePass triplePass = new TriplePassReject(reason);
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getTicketRejectionEmail(user, domainName, reason));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_nosc01() throws SQLException {
        // UC#002: Triple-PASS Domain Registration Ticket - ADMIN PASS - Hold
        // Update
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        String domainName = "uc002-nosc01.ie";
        String reason = "Hold Update";
        TriplePass triplePass = new TriplePass(reason) {
            @Override
            public void before(String domainName) throws SQLException {
                ViewTicketPage ttp = new ViewTicketPage(internal);
                ttp.generateTicketHoldUp(domainName);
                scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
            }

            @Override
            public void after(String domainName) throws SQLException {}
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getTicketRejectionEmail(user, domainName, reason));
        emails.add(emailSummaryGenerator.getRegistrationHoldUpEmail(user, user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_nosc02() throws SQLException {
        // UC#002: Triple-PASS Domain Registration Ticket - hostmaster remark
        // field
        final User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        String domainName = "uc002-nosc02.ie";
        TriplePass triplePass = new TriplePass(true, false) {
            @Override
            public void before(String domainName) throws SQLException {
                db().setDepositAmount(user.login, 0);
            }

            @Override
            public void after(String domainName) throws SQLException {
                int ticketId = db().getTicketId(domainName);
                scheduler().runJob(SchedulerJob.TRIPLE_PASS);
                assertEquals("Financial status changed", db().getTicketHostmasterRemark(ticketId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationInsufficientFundsEmail(user, domainName));
        testTriplePassDomainRegistrationTicket(user, domainName, paymentDetails, triplePass);
    }

    @Test
    public void test_uc002_nosc03() throws SQLException {
        // UC#002: Triple-PASS Domain Registration Ticket - 7 nameservers
        final User user = this.registrar;
        String domainName = "uc002-nosc03.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int dnsCount = Integer.valueOf(db().getAppConfigValue("nameserver_max_count"));
        final List<DomainNameServer> dnsList = getDefaultDnsList(domainName, dnsCount);
        TriplePass triplePass = new TriplePassCheckDns(dnsList, paymentDetails.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList, paymentDetails, 1,
                true);
        testTriplePassDomainRegistrationTicket(user, domainName, details, triplePass);
    }

    @Test
    public void test_uc002_nosc04() throws SQLException {
        // UC#002: Triple-PASS Domain Registration Ticket - IPv6 support
        final User user = this.registrar;
        String domainName = "uc002-nosc04.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        final List<DomainNameServer> dnsList = getDefaultDnsList(domainName, 1);
        dnsList.add(new DomainNameServer(String.format("ns2.%s", domainName), null, "::ffff:10.10.1.2"));
        TriplePass triplePass = new TriplePassCheckDns(dnsList, paymentDetails.getMethod());
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(getPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(getRegistrationAcceptedEmail(user, domainName));
        emails.add(getApplicationDnsVerifiedEmail(user, domainName));
        emails.add(getApplicationCompletedEmail(user, domainName));
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList, paymentDetails, 1,
                true);
        testTriplePassDomainRegistrationTicket(user, domainName, details, triplePass);
    }

    private List<DomainNameServer> getDefaultDnsList(String domainName, int count) {
        List<DomainNameServer> list = new ArrayList<DomainNameServer>();
        for (int i = 0; i < count; i++) {
            list.add(new DomainNameServer(String.format("ns%s.%s", i + 1, domainName), String.format("10.10.1.%s",
                    i + 1), null));
        }
        return list;
    }

    private void testTriplePassDomainRegistrationTicket(User user, String domainName,
            DomainRegistrationDetails details, TriplePass triplePass) throws SQLException {
        // Create registration ticket.
        createRegistrationTicket(user, domainName, details);

        // Perform actions before triple-passing the ticket.
        triplePass.before(domainName);

        // Triple pass the ticket.
        boolean success;
        ViewTicketPage ttp = new ViewTicketPage(internal);
        if (triplePass.isTicketAccepted()) {
            success = ttp.triplePassTicketAccept(domainName);
        } else {
            success = ttp.triplePassTicketReject(domainName, triplePass.getRejectionReason());
        }

        if (triplePass.isSuccess() || !triplePass.isTicketAccepted()) {
            assertTrue(success);
        } else {
            assertFalse(success);
        }
        triplePass.after(domainName);
        checkAndResetEmails(emails);
    }

    private void testTriplePassDomainRegistrationTicket(User user, String domainName, PaymentDetails paymentDetails,
            TriplePass triplePass) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, true);
        testTriplePassDomainRegistrationTicket(user, domainName, details, triplePass);
    }

    private abstract class TriplePass {

        private boolean accept;
        private boolean success;
        private String rejectionReason;

        public TriplePass(boolean accept, boolean success) {
            this.accept = accept;
            this.success = success;
            this.rejectionReason = null;
        }

        public TriplePass(String rejectionReason) {
            this.accept = false;
            this.rejectionReason = rejectionReason;
        }

        public abstract void before(String domainName) throws SQLException;

        public abstract void after(String domainName) throws SQLException;

        public boolean isTicketAccepted() {
            return accept;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

    }

    private class HappyTriplePass extends TriplePass {

        private PaymentMethod method;

        public HappyTriplePass(PaymentMethod method) {
            super(true, true);
            this.method = method;
        }

        @Override
        public void before(String domainName) throws SQLException {}

        @Override
        public void after(String domainName) throws SQLException {
            if (method != PaymentMethod.CHARITY) {
                checkDatabaseState(domainName);
            }
            assertEquals(0, db().getTicketsCountForDomain(domainName));
        }

    }

    private class TriplePassReject extends TriplePass {

        public TriplePassReject(String rejectionReason) {
            super(rejectionReason);
        }

        @Override
        public void before(String domainName) {}

        @Override
        public void after(String domainName) throws SQLException {
            assertEquals(1, db().getTicketsCountForDomain(domainName));
            crsweb().checkTicketStatus(domainName, getRejectionReason(), "New", "New");
        }
    }

    private class TriplePassCheckDns extends HappyTriplePass {

        private List<DomainNameServer> dnsList;

        public TriplePassCheckDns(List<DomainNameServer> dnsList, PaymentMethod method) {
            super(method);
            this.dnsList = dnsList;
        }

        @Override
        public void after(String domainName) throws SQLException {
            super.after(domainName);
            List<DomainNameServer> dbDnsList = db().getDnsListForDomain(domainName);
            assertEquals(dnsList, dbDnsList);
        }
    }

    private void createRegistrationTicket(User user, String domainName, DomainRegistrationDetails details)
            throws SQLException {
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private void checkDatabaseState(String domainName) throws SQLException {
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        assertEquals(0, db().getTicketsCountForDomain(domainName));
        assertTrue(db().reservationReadyForSettlement(reservationId));
        assertFalse(db().reservationSettled(reservationId));
        assertTrue(db().transactionFinanciallyPassed(transactionId));
    }

    private ExpectedEmailSummary getRegistrationEmail(User user, String domainName) throws SQLException {
        return emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName);
    }

    private ExpectedEmailSummary getPaymentEmail(User user, PaymentMethod method, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getRegistrationPaymentEmail(user, method, domainName);
    }

    private ExpectedEmailSummary getTicketRejectionEmail(User user, String domainName, String reason) {
        return emailSummaryGenerator.getRegistrationRemarksEmail(user, domainName, reason);
    }

    private ExpectedEmailSummary getApplicationDnsVerifiedEmail(User user, String domainName) {
        return emailSummaryGenerator.getRegistrationDnsVerifiedEmail(user, user, domainName);
    }

    private ExpectedEmailSummary getApplicationCompletedEmail(User user, String domainName) throws SQLException {
        return emailSummaryGenerator.getRegistrationCompletedEmail(user, user, user, domainName);
    }

    private ExpectedEmailSummary getRegistrationAcceptedEmail(User user, String domainName) throws SQLException {
        return emailSummaryGenerator.getRegistrationAcceptedEmail(user, user, domainName);
    }
}
