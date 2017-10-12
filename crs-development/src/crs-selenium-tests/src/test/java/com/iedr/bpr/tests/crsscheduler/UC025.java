package com.iedr.bpr.tests.crsscheduler;

import java.sql.SQLException;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UC025 extends SeleniumTest {

    public UC025(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc025_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc025_data.sql";
    }

    @Test
    public void uc025_sc01() throws SQLException {
        User user = this.registrar;
        User losing = this.direct;
        String domainName = "uc025-sc01.ie";
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.expireTicket(domainName);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        emails.add(emailSummaryGenerator.getBillingTransferRequestEmail(user, losing, losing));
        emails.add(emailSummaryGenerator.getBillingTransferRequestExpiredEmail(user, losing, user, domainName));
        emails.add(emailSummaryGenerator.getTicketRemovalEmail(user, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc025_nosc01() throws SQLException {
        // UC#025: Domain Ticket Cleanup - Basic Ticket Cleanup Scenario -
        // Registration
        User user = this.registrar;
        String domainName = "uc025-nosc01.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.expireTicket(domainName);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        emails.add(emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationExpiredEmail(user, user, domainName));
        emails.add(emailSummaryGenerator.getTicketRemovalEmail(user, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc025_nosc02() throws NumberFormatException, SQLException {
        // UC#025: Domain Ticket Cleanup - Basic Ticket Cleanup Scenario -
        // Modification
        User user = this.registrar;
        String domainName = "uc025-nosc02.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.holderField.fill("Modified Holder");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.expireTicket(domainName);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        emails.add(emailSummaryGenerator.getDomainModificationExpiredEmail(user, user, domainName));
        emails.add(emailSummaryGenerator.getTicketRemovalEmail(user, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc025_nosc03() throws SQLException {
        // UC#025: Domain Ticket Cleanup - Expired renewal transaction - CC
        int reservationId = db().getReservationId("uc025-sc01.ie");
        int transactionId = db().getReservationTransactionId(reservationId);
        assertFalse(db().transactionCancelled(transactionId));
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        assertEquals(0, db().getReservationsCount("uc025-sc01.ie"));
        assertFalse(db().transactionExists(transactionId));
    }

}
