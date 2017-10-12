package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.console.CharityPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC007 extends SeleniumTest {

    public UC007(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc007_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc007_data.sql";
    }

    @Test
    public void test_uc007_sc01() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc01.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 390, 17);
    }

    @Test
    public void test_uc007_sc02() throws SQLException {
        User gaining = this.direct;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc02.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 390, 25);
    }

    @Test
    public void test_uc007_sc03() throws SQLException {
        User gaining = this.registrar;
        User losing = this.direct;
        String domainName = "uc007-sc03.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 391, 17);
    }

    @Test
    public void test_uc007_sc04() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc04.ie";
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 402, 113);
    }

    @Test
    public void test_uc007_sc05() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc05.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 438, 17);
    }

    @Test
    public void test_uc007_sc06() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc06.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 2, 438, 17);
    }

    @Test
    public void test_uc007_sc10() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc10.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 2, 390, 17);
    }

    @Test
    public void test_uc007_sc09() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc09.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 3, 390, 17);
    }

    @Test
    public void test_uc007_sc08() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc08.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 2, 486, 17);
    }

    @Test
    public void test_uc007_sc07() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc07.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 2, 486, 17);
    }

    @Test
    public void test_uc007_sc11() throws SQLException {
        final User gaining = this.direct;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc11.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        TransferFailure failure = new TransferFailure() {
            public void before(String domainName) throws SQLException {
                int reservationId = db().getReservationId(domainName);
                int transactionId = db().getReservationTransactionId(reservationId);
                db().invalidateTransaction(transactionId, new Date());
            }

            public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
            }
        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        test_transfer_failure(gaining, domainName, paymentDetails, 1, 390, 390, failure);
    }

    @Test
    public void test_uc007_sc12() throws JSchException, IOException, SQLException {
        List<String> nonAuthoritativeDomains = Arrays.asList("uc007-sc12.ie");
        String initialCkDnsScript = ssh().crsweb.spoofCkDnsScript(nonAuthoritativeDomains);
        try {
            _test_uc007_sc12();
        } finally {
            ssh().crsweb.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    private void _test_uc007_sc12() throws SQLException {
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc12.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferFailure failure = new TransferFailure() {
            public void before(String domainName) throws SQLException {}

            public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Stalled", "New");
            }
        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        test_transfer_failure(gaining, domainName, paymentDetails, 1, 390, 390, failure);
    }

    @Test
    public void test_uc007_sc13() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc13.ie";
        final PaymentDetails method = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferRejection rejection = new TransferRejection() {
            public String getReason() {
                return "Cancelled";
            }

            public void before(String domainName) {}

            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, getReason(), "New", "New");
                assertEquals(1, db().getTicketsCountForDomain(domainName));
                scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
                assertEquals(0, db().getTicketsCountForDomain(domainName));
            }

        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(emailSummaryGenerator.getBillingTransferRemarksEmail(gaining, domainName, rejection.getReason()));
        emails.add(emailSummaryGenerator.getTicketRemovalEmail(gaining, domainName));
        test_transfer_rejection(gaining, domainName, method, 1, 390, 17, rejection);
    }

    @Test
    public void test_uc007_sc14() throws SQLException {
        User gaining = this.direct;
        User losing = new User("UC007AA-IEDR", "Passw0rd!", false, "uc007_aa@iedr.ie");
        String domainName = "uc007-sc14.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));
        test_transfer_success(gaining, domainName, paymentDetails, 1, 391, 25);
    }

    @Test
    public void test_uc007_sc15() throws SQLException {
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc15.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferFailure failure = new TransferFailure() {
            public void before(String domainName) throws SQLException {
                db().setDepositAmount(gaining.login, 0);
            }

            public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
            }
        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(emailSummaryGenerator.getBillingTransferInsufficientFundsEmail(gaining, domainName));
        test_transfer_failure(gaining, domainName, paymentDetails, 1, 390, 390, failure);
    }

    @Test
    public void test_uc007_sc16() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc16.ie";
        PaymentDetails method = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferRejection rejection = new TransferRejection() {
            public String getReason() {
                return "Cancelled";
            }

            public void before(String domainName) {}

            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, getReason(), "New", "New");
                assertEquals(1, db().getTicketsCountForDomain(domainName));
            }

        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(emailSummaryGenerator.getBillingTransferRemarksEmail(gaining, domainName, rejection.getReason()));
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(emailSummaryGenerator.getNrpTransferRequestFailedEmail(gaining, losing, adminContact, domainName,
                false));
        test_transfer_rejection(gaining, domainName, method, 1, 438, 18, rejection);
    }

    @Test
    public void test_uc007_sc17() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc17.ie";
        PaymentDetails method = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferRejection rejection = new TransferRejection() {
            public String getReason() {
                return "Cancelled";
            }

            public void before(String domainName) {}

            public void after(String domainName) throws SQLException {
                crsweb().checkTicketStatus(domainName, getReason(), "New", "New");
                assertEquals(1, db().getTicketsCountForDomain(domainName));
            }

        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(emailSummaryGenerator.getBillingTransferRemarksEmail(gaining, domainName, rejection.getReason()));
        emails.add(emailSummaryGenerator.getNrpTransferRequestEmail(gaining, losing, adminContact));
        emails.add(emailSummaryGenerator.getNrpTransferRequestFailedEmail(gaining, losing, adminContact, domainName,
                true));
        test_transfer_rejection(gaining, domainName, method, 1, 486, 20, rejection);
    }

    @Ignore
    @Test
    public void test_uc007_qa1234() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-qa1234.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        TransferFailure failure = new TransferFailure() {
            public void before(String domainName) throws SQLException {
                int reservationId = db().getReservationId(domainName);
                int transactionId = db().getReservationTransactionId(reservationId);
                db().invalidateTransactionRealexAuthcode(transactionId);
            }

            public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException {
                crsweb().checkTicketStatus(domainName, "Passed", "Passed", "Stalled");
            }
        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        test_transfer_failure(gaining, domainName, paymentDetails, 1, 390, 390, failure);
    }

    @Test
    public void test_uc007_nosc01() throws SQLException {
        // UC#007: Triple-PASS Domain Billing Transfer Ticket - highlighting
        // changes
        User user = this.registrar;
        String domainName = "uc007-nosc01.ie";
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(authcode, new DomainContactFormDetails(user,
                adminContact.login), new NameserverFormDetails(domainName), PredefinedPayments.DEPOSIT_PAYMENT_DETAILS,
                1);
        createTransferTicket(user, domainName, details);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.viewReviseAndEditTicket(domainName);

        for (int i = 0; i < 2; i++) {
            String namePrefix = String.format("ticketWrapper.newNameserverWrappers[%s]", String.valueOf(i));
            console().assertElementPresent(
                    By.cssSelector(String.format("input.modification[name='%s.name']", namePrefix)));
            console().assertElementPresent(
                    By.cssSelector(String.format("input.modification[name='%s.ipv4']", namePrefix)));
        }
        List<String> modifiedFields = Arrays.asList("Account Name", "Admin Contact", "Tech Contact", "Billing Contact");
        for (String fieldName : modifiedFields) {
            console().assertElementPresent(By.xpath(getFieldXpath(fieldName, 1)));
        }
        // Check 2nd admin contact.
        console().assertElementPresent(By.xpath(getFieldXpath("Admin Contact", 2)));
    }

    @Test
    public void test_uc007_nosc02() throws NumberFormatException, SQLException {
        // UC#007: Triple-PASS Domain Billing Transfer Ticket - ADMIN PASS -
        // Hold Update
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc01.ie";
        PaymentDetails method = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferRejection rejection = new TransferRejection() {
            public String getReason() {
                return "Hold Update";
            }

            public void before(String domainName) throws SQLException {
                ViewTicketPage ttp = new ViewTicketPage(internal);
                ttp.generateTicketHoldUp(domainName);
            }

            public void after(String domainName) throws SQLException {
                scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
            }

        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(emailSummaryGenerator.getBillingTransferHoldUpEmail(gaining, losing, gaining, domainName));
        emails.add(emailSummaryGenerator.getBillingTransferRemarksEmail(gaining, domainName, rejection.getReason()));
        test_transfer_rejection(gaining, domainName, method, 1, 390, 390, rejection);
    }

    @Test
    public void test_uc007_nosc03() throws SQLException {
        // UC#007: Triple-PASS Domain Billing Transfer Ticket - hostmaster
        // remark field
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-sc01.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        TransferFailure failure = new TransferFailure() {
            public void before(String domainName) throws SQLException {
                db().setDepositAmount(gaining.login, 0);
            }

            public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException {
                scheduler().runJob(SchedulerJob.TRIPLE_PASS);
                int ticketId = db().getTicketId(domainName);
                assertEquals("Financial status changed", db().getTicketHostmasterRemark(ticketId));
            }
        };
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(emailSummaryGenerator.getBillingTransferInsufficientFundsEmail(gaining, domainName));
        test_transfer_failure(gaining, domainName, paymentDetails, 1, 390, 390, failure);

    }

    @Test
    public void test_uc007_nosc04() throws SQLException {
        // UC#007: Triple-PASS Domain Billing Transfer Ticket - IPv6 support
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc007-nosc04.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getTransferAcceptedEmail(gaining, domainName));
        emails.add(getTransferDnsVerifiedEmail(gaining, domainName));
        emails.add(getPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(getTransferCompletedEmail(gaining, losing, domainName));

        String authcode = db().getAuthcodeForDomain(domainName);
        List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName), null,
                "::ffff:10.10.1.1"),
                new DomainNameServer(String.format("ns2.%s", domainName), null, "::ffff:10.10.1.2"));
        DomainTransferDetails details = new DomainTransferDetails(authcode, new DomainContactFormDetails(gaining),
                new NameserverFormDetails(dnsList), PredefinedPayments.DEPOSIT_PAYMENT_DETAILS, 1);
        createTransferTicket(gaining, domainName, details);
        triplePassTicketAcceptAndCheck(gaining, domainName, 390, 17);
        assertEquals(dnsList, db().getDnsListForDomain(domainName));
    }

    public void test_transfer_success(User user, String domainName, PaymentDetails paymentDetails, int paymentPeriod,
            int dsmBefore, int dsmAfter) throws SQLException {
        // Create billing transfer ticket.
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode, paymentDetails,
                paymentPeriod);
        createTransferTicket(user, domainName, details);

        triplePassTicketAcceptAndCheck(user, domainName, dsmBefore, dsmAfter);
    }

    private void test_transfer_failure(User user, String domainName, PaymentDetails paymentDetails, int paymentPeriod,
            int dsmBefore, int dsmAfter, TransferFailure failure) throws SQLException {
        // Create billing transfer ticket.
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode, paymentDetails,
                paymentPeriod);
        createTransferTicket(user, domainName, details);

        // Check domain's DSM state.
        assertEquals(dsmBefore, db().getDsmStateForDomain(domainName));

        // Prepare failure.
        failure.before(domainName);

        // Triple pass the ticket.
        triplePassTicketAccept(domainName);

        failure.after(user, domainName, dsmBefore, dsmAfter);
        assertEquals(dsmAfter, db().getDsmStateForDomain(domainName));
        checkAndResetEmails(emails);
    }

    private void test_transfer_rejection(User user, String domainName, PaymentDetails paymentDetails,
            int paymentPeriod, int dsmBefore, int dsmAfter, TransferRejection rejection) throws SQLException {
        // Create billing transfer ticket.
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode, paymentDetails,
                paymentPeriod);
        createTransferTicket(user, domainName, details);

        // Check domain's DSM state.
        assertEquals(dsmBefore, db().getDsmStateForDomain(domainName));

        rejection.before(domainName);

        // Triple pass the ticket.
        triplePassTicketReject(domainName, rejection.getReason());

        rejection.after(domainName);
        assertEquals(dsmAfter, db().getDsmStateForDomain(domainName));
        checkAndResetEmails(emails);
    }

    private interface TransferFailure {
        public void before(String domainName) throws SQLException;

        public void after(User user, String domainName, int dsmBefore, int dsmAfter) throws SQLException;
    }

    private interface TransferRejection {
        public String getReason();

        public void before(String domainName) throws SQLException;

        public void after(String domainName) throws SQLException;

    }

    private void createTransferTicket(User user, String domainName, DomainTransferDetails transferDetails)
            throws SQLException {
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, transferDetails);
    }

    private boolean triplePassTicketAccept(String domainName) {
        return triplePassTicket(domainName, true, null);
    }

    private boolean triplePassTicketReject(String domainName, String reason) {
        return triplePassTicket(domainName, false, reason);
    }

    private boolean triplePassTicket(String domainName, boolean accept, String reason) {
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        if (accept) {
            return ttp.triplePassTicketAccept(domainName);
        } else {
            return ttp.triplePassTicketReject(domainName, reason);
        }
    }

    private void triplePassTicketAcceptAndCheck(User user, String domainName, int dsmBefore, int dsmAfter)
            throws SQLException {
        // Check domain's DSM state.
        assertEquals(dsmBefore, db().getDsmStateForDomain(domainName));

        // Triple pass the ticket.
        boolean success = triplePassTicketAccept(domainName);

        // Check that the ticket is no longer visible under "Tickets" >
        // "Search".
        assertTrue(success);

        // Check database state.
        assertEquals(0, db().getTicketsCountForDomain(domainName));
        assertEquals(dsmAfter, db().getDsmStateForDomain(domainName));

        checkAndResetEmails(emails);

        // Check if domain is listed under new account.
        console().login(user);
        console().view(console().url.allDomains);
        console().fillInput("gs_A", domainName);
        wd().findElement(By.id("gs_A")).sendKeys(Keys.RETURN);
        wd().findElement(By.linkText(domainName));
    }

    private String getFieldXpath(String fieldName, int index) {
        index = getBrowserBasedFieldIndex(index);
        return String.format("(//*[text()[contains(., '%s')]])[%s]/../../.." + "//*[contains(@class, 'modification')]",
                fieldName, String.valueOf(index));
    }

    private int getBrowserBasedFieldIndex(int index) {
        // IE < 10 enumerates xpath elements starting with [0]. According to W3C
        // it should be [1].
        Capabilities cap = ((RemoteWebDriver) wd()).getCapabilities();
        String browserName = cap.getBrowserName();
        int version = Integer.valueOf(cap.getVersion().split("\\.")[0]);
        if ("internet explorer".equals(browserName) && version < 10) {
            index -= 1;
        }
        return index;
    }

    private ExpectedEmailSummary getPaymentEmail(User gainingUser, PaymentMethod method) throws SQLException {
        return emailSummaryGenerator.getBillingTransferPaymentEmail(gainingUser, method);
    }

    private ExpectedEmailSummary getTransferRequestReceivedEmail(User gaining, User losing) throws SQLException {
        return emailSummaryGenerator.getBillingTransferRequestEmail(gaining, losing, adminContact);
    }

    private ExpectedEmailSummary getTransferAcceptedEmail(User gaining, String domainName) throws SQLException {
        return emailSummaryGenerator.getBillingTransferAcceptedEmail(gaining, gaining, domainName);
    }

    private ExpectedEmailSummary getTransferDnsVerifiedEmail(User gaining, String domainName) throws SQLException {
        return emailSummaryGenerator.getBillingTransferDnsEmail(gaining, gaining, domainName);
    }

    private ExpectedEmailSummary getTransferCompletedEmail(User gaining, User losing, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getBillingTransferCompletedEmail(gaining, losing, gaining, gaining, domainName);
    }

}
