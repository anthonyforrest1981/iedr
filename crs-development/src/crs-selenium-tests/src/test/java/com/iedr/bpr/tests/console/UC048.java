package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.TicketUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@IgnoredBrowsers({SeleniumTest.Browser.Edge})
public class UC048 extends SeleniumTest {

    public UC048(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc048_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc048_data.sql";
    }

    @Test
    public void test_uc048_sc02() throws SQLException {
        final User user = this.direct;
        final String domainName = "uc048-sc02.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        emails.add(emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName));
        emails.add(getApplicationFailedEmail(user, true));
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                registerDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) throws SQLException {
                db().changeTicketAdminStatus(ticketId, "Passed", new Date());
                db().changeTicketTechStatus(ticketId, "Stalled", new Date());
            }
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_sc06() throws SQLException {
        final User user = this.direct;
        User losingUser = this.registrar;
        final String domainName = "uc048-sc06.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferCardPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getApplicationFailedEmail(user, false));
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) {}
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_sc08() throws SQLException {
        final User user = this.direct;
        User losingUser = this.registrar;
        final String domainName = "uc048-sc08.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferCardPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getApplicationFailedEmail(user, false));
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) throws SQLException {
                int reservationId = db().getReservationId(domainName);
                int transactionId = db().getReservationTransactionId(reservationId);
                db().invalidateTransaction(transactionId, new Date());
                db().changeTicketAdminStatus(ticketId, "Passed", new Date());
                db().changeTicketTechStatus(ticketId, "Passed", new Date());
                db().changeTicketFinancialStatus(ticketId, "Stalled", new Date());
            }
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_sc10() throws SQLException {
        final User user = this.registrar;
        User losingUser = this.registrarNonVat;
        final String domainName = "uc048-sc10.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) throws SQLException {
                db().changeTicketAdminStatus(ticketId, "Passed", new Date());
                db().changeTicketTechStatus(ticketId, "Stalled", new Date());
            }
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_qa14732() throws SQLException {
        final User user = this.direct;
        final String domainName = "uc048-qa14832.ie";
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() {
                console().login(user);
                ViewDomainPage vdp = new ViewDomainPage();
                ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
                vdp.initModification();
                vdp.holderField.fill("Modified Holder");
                vdp.remarksField.fill("Remark");
                vdp.submitAndWaitForSuccess(domainName, true);
            }

            public void beforeCancel(int ticketId) {}
        };
        test_ticket_cancel(user, domainName, null, cancel);
    }

    @Test
    public void test_uc048_qa14735() throws SQLException {
        final User user = this.direct;
        User losingUser = this.registrar;
        final String domainName = "uc048-qa14835.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferCardPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getApplicationFailedEmail(user, false));
        emails.add(getTicketRemovalEmail(user, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) {}
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_nosc01() throws SQLException {
        final User user = this.direct;
        User losingUser = this.registrar;
        final String domainName = "uc048-nosc01.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferCardPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getApplicationFailedEmail(user, false));
        emails.add(getTicketRemovalEmail(user, domainName));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestFailedEmail(user, losingUser, false, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) {}
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    @Test
    public void test_uc048_nosc02() throws SQLException {
        final User user = this.direct;
        User losingUser = this.registrar;
        final String domainName = "uc048-nosc02.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        emails.add(getTransferCardPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(getTransferRequestReceivedEmail(user, losingUser));
        emails.add(getApplicationFailedEmail(user, false));
        emails.add(getTicketRemovalEmail(user, domainName));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestFailedEmail(user, losingUser, true, domainName));
        TicketCancel cancel = new TicketCancel() {
            public void createTicket() throws SQLException {
                transferDomain(user, domainName, paymentDetails);
            }

            public void beforeCancel(int ticketId) {}
        };
        test_ticket_cancel(user, domainName, paymentDetails.getMethod(), cancel);
    }

    private void test_ticket_cancel(User user, String domainName, PaymentMethod method, TicketCancel cancel)
            throws SQLException {
        console().login(user);
        cancel.createTicket();
        int ticketId = db().getTicketId(domainName);
        cancel.beforeCancel(ticketId);
        TicketUtils.cancelTicket(domainName);
        checkDb(domainName, ticketId, method);
        // Run TicketAndTransactionCleanup to generate email with E_ID=104.
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        checkAndResetEmails(emails);
    }

    private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private void transferDomain(User user, String domainName, PaymentDetails method) throws SQLException {
        DomainTransferUtils.transferDomain(domainName, user, method);
    }

    private void checkDb(String domainName, int ticketId, PaymentMethod method) throws SQLException {
        if (method == PaymentMethod.CARD) {
            int reservationId = db().getReservationId(domainName);
            int transactionId = db().getReservationTransactionId(reservationId);
            assertTrue(db().transactionCancelled(transactionId));
        }
        assertEquals("Cancelled", db().getTicketCustomerStatus(ticketId));
    }

    private interface TicketCancel {
        public void createTicket() throws SQLException;

        public void beforeCancel(int ticketId) throws SQLException;
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

    private ExpectedEmailSummary getNrpTransferRequestFailedEmail(User gainingUser, User losingUser, boolean voluntary,
            String domainName) throws SQLException {
        return emailSummaryGenerator.getNrpTransferRequestFailedEmail(gainingUser, losingUser, losingUser, domainName,
                voluntary);
    }
}
