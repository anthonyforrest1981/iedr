package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;

public class UC048 extends SeleniumTest {

    private static String REASON = "Cancelled";

    public UC048(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc048_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc048_data.sql";
    }

    @Test
    public void test_uc048_nosc01() throws SQLException {
        // UC#048: Cancel Ticket - Cancel Transfer - IM
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        final String domainName = "uc048-nosc01.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.addAll(getNrpTransferCancelledEmails(gaining, losing, true, false, paymentDetails.getMethod(),
                domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(gaining, domainName, paymentDetails);
            }
        };
        test_ticket_cancel(gaining, domainName, REASON, cancel);
    }

    @Test
    public void test_uc048_nosc02() throws SQLException {
        // UC#048: Cancel Ticket - Cancel Transfer - VM
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        final String domainName = "uc048-nosc02.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.addAll(getNrpTransferCancelledEmails(gaining, losing, true, true, paymentDetails.getMethod(), domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(gaining, domainName, paymentDetails);
            }
        };
        test_ticket_cancel(gaining, domainName, REASON, cancel);
    }

    @Test
    public void test_uc048_nosc03() throws SQLException {
        // UC#048: Cancel Ticket - Cancel Modification
        final User user = this.registrar;
        final String domainName = "uc048-nosc03.ie";
        emails.add(getTicketRemovalEmail(user, domainName));
        emails.add(emailSummaryGenerator.getDomainModificationRemarksEmail(user, domainName, REASON));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                console().login(user);
                ViewDomainPage vdp = new ViewDomainPage();
                ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
                vdp.initModification();
                vdp.holderField.fill("Modified Holder");
                vdp.remarksField.fill("Remark");
                vdp.submitAndWaitForSuccess(domainName, true);
            }
        };
        test_ticket_cancel(user, domainName, REASON, cancel);
    }

    @Test
    public void test_uc048_nosc04() throws SQLException {
        // UC#048: Cancel Ticket - Cancel Transfer on CRS-WEB
        final User gaining = this.registrar;
        User losing = this.registrarNonVat;
        final String domainName = "uc048-nosc04.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.addAll(getNrpTransferCancelledEmails(gaining, losing, false, false, paymentDetails.getMethod(),
                domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(gaining, domainName, paymentDetails);
            }
        };
        test_ticket_cancel(gaining, domainName, REASON, cancel);
    }

    @Test
    public void test_uc048_nosc05() throws SQLException {
        // UC#048: Cancel Ticket - Cancel Registration on CRS-WEB
        final User user = this.registrar;
        final String domainName = "uc048-nosc05.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName));
        emails.add(getApplicationFailedEmail(user, true));
        emails.add(getTicketRemovalEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationRemarksEmail(user, domainName, REASON));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                registerDomain(user, domainName, paymentDetails);
            }
        };
        test_ticket_cancel(user, domainName, REASON, cancel);
    }

    private void test_ticket_cancel(User user, String domainName, String reason, TicketCancel cancel)
            throws SQLException {
        console().login(user);
        cancel.createTicket();
        cancelTicket(domainName, reason);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        checkAndResetEmails(emails);
    }

    private void cancelTicket(String domainName, String reason) {
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, reason);
    }

    private void transferDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        DomainTransferUtils.transferDomain(domainName, user, paymentDetails);
    }

    private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private interface TicketCancel {
        public void createTicket() throws SQLException;
    }

    private ExpectedEmailSummary getTransferCardPaymentEmail(User user, PaymentMethod method) throws SQLException {
        return emailSummaryGenerator.getBillingTransferPaymentEmail(user, method);
    }

    private ExpectedEmailSummary getTransferRequestReceivedEmail(User gainingUser, User losingUser) throws SQLException {
        return emailSummaryGenerator.getBillingTransferRequestEmail(gainingUser, losingUser, losingUser);
    }

    private ExpectedEmailSummary getApplicationFailedEmail(User billC, boolean registration) throws SQLException {
        return emailSummaryGenerator.getApplicationFailedEmail(billC, registration);
    }

    private ExpectedEmailSummary getTicketRemovalEmail(User billC, String domainName) throws SQLException {
        return emailSummaryGenerator.getTicketRemovalEmail(billC, domainName);
    }

    private ExpectedEmailSummary getNrpTransferRequestEmail(User gainingUser, User losingUser) throws SQLException {
        return emailSummaryGenerator.getNrpTransferRequestEmail(gainingUser, losingUser, losingUser);
    }

    private ExpectedEmailSummary getNrpTransferRequestFailedEmail(User gainingUser, User losingUser, String domainName,
            boolean voluntary) throws SQLException {
        return emailSummaryGenerator.getNrpTransferRequestFailedEmail(gainingUser, losingUser, losingUser, domainName,
                voluntary);
    }

    private List<ExpectedEmailSummary> getNrpTransferCancelledEmails(User gaining, User losing, boolean nrp,
            boolean voluntary, PaymentMethod method, String domainName) throws SQLException {
        List<ExpectedEmailSummary> emails = new ArrayList<ExpectedEmailSummary>();
        emails.add(getTransferCardPaymentEmail(gaining, method));
        emails.add(getTransferRequestReceivedEmail(gaining, losing));
        emails.add(getApplicationFailedEmail(gaining, false));
        emails.add(getTicketRemovalEmail(gaining, domainName));
        if (nrp) {
            emails.add(getNrpTransferRequestEmail(gaining, losing));
            emails.add(getNrpTransferRequestFailedEmail(gaining, losing, domainName, voluntary));
        }
        emails.add(emailSummaryGenerator.getBillingTransferRemarksEmail(gaining, domainName, REASON));
        return emails;
    }

}
