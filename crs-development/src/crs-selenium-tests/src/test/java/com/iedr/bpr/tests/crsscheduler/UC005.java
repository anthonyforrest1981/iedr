package com.iedr.bpr.tests.crsscheduler;

import java.sql.SQLException;
import java.util.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.AllDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UC005 extends SeleniumTest {

    public UC005(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc005_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc005_data.sql";
    }

    @Test
    public void uc005_sc01() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 49);
        testDomainRenewed(domainName);
        emails.addAll(getRenewalEmails(user, domainName));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_sc02() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 81);
        testDomainRenewed(domainName);
        emails.addAll(getRenewalEmails(user, domainName));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_sc04() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 82);
        testDomainRenewed(domainName);
        emails.addAll(getRenewalEmails(user, domainName));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, true, PaymentMethod.ADP));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_sc05() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 83);
        testDomainRenewed(domainName);
        emails.addAll(getRenewalEmails(user, domainName));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, true, PaymentMethod.ADP));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_sc06() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 33);
        testDomainRenewed(domainName);
        emails.addAll(getRenewalEmails(user, domainName));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_sc09() throws SQLException {
        User user = this.registrar;
        String domainName = "uc005-autorenew-current.ie";
        db().setDsmStateForDomain(domainName, 49);
        db().setDepositAmount(user.login, 0);
        testDomainNotRenewed(domainName);
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_renarcrem3() throws SQLException {
        User user = this.registrar;
        int dsmState = 81;
        testUnsetAutorenew(user, dsmState);
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_renrrcrem4() throws SQLException {
        User user = this.registrar;
        int dsmState = 49;
        testUnsetAutorenew(user, dsmState);
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_renarrcset5() throws SQLException {
        User user = this.registrar;
        int monthsToRenewal = 6;
        List<Domain> charityDomains = Arrays.asList(new Domain("uc005-norenew-a.ie", monthsToRenewal), new Domain(
                "uc005-norenew-b.ie", monthsToRenewal));
        List<Domain> nonBillableDomains = Arrays.asList(new Domain("uc005-norenew-c.ie", monthsToRenewal), new Domain(
                "uc005-norenew-d.ie", monthsToRenewal));
        setDsmStateForDomains(charityDomains, 113);
        setDsmStateForDomains(nonBillableDomains, 305);
        String ce = "Error. Domain is not billable: %s, status is Charity";
        String ne = "Error. Domain is not billable: %s, status is Non-Billable";
        assertEquals(String.format(ce, charityDomains.get(0).name),
                enableRenewFailure(user, charityDomains.get(0), true));
        assertEquals(String.format(ce, charityDomains.get(1).name),
                enableRenewFailure(user, charityDomains.get(1), false));
        assertEquals(String.format(ne, nonBillableDomains.get(0).name),
                enableRenewFailure(user, nonBillableDomains.get(0), true));
        assertEquals(String.format(ne, nonBillableDomains.get(1).name),
                enableRenewFailure(user, nonBillableDomains.get(1), false));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_nrparrcset6() throws SQLException {
        User user = this.registrar;
        int monthsToRenewal = 6;
        String domainPrefix = "uc005-autorenew-future";
        List<Domain> autoRenewDomains = Arrays.asList(new Domain("uc005-autorenew-future-a.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-b.ie", monthsToRenewal));
        List<Domain> renewOnceDomains = Arrays.asList(new Domain("uc005-autorenew-future-c.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-d.ie", monthsToRenewal));
        List<Domain> allDomains = new ArrayList<Domain>();
        allDomains.addAll(autoRenewDomains);
        allDomains.addAll(renewOnceDomains);
        setDsmStateForDomains(autoRenewDomains, 81);
        setDsmStateForDomains(renewOnceDomains, 49);
        console().login(user);
        for (Domain domain : allDomains) {
            ViewDomainUtils.enterDomainToNrp(domain.name, ContactType.BILL);
            assertEquals("N", db().getRenewalModeForDomain(domain.name));
            assertEquals("VM", db().getNrpStatusForDomain(domain.name));
        }
        checkDomainsAvailableForPayment(allDomains, domainPrefix, monthsToRenewal, true);
        emails.addAll(getNrpEmails(allDomains, this.adminContact, user));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_nrparrcfail7() throws SQLException {
        User user = this.registrar;
        int monthsToRenewal = 6;
        List<Domain> vmNrpDomains = Arrays.asList(new Domain("uc005-autorenew-future-a.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-b.ie", monthsToRenewal));
        List<Domain> vsNrpDomains = Arrays.asList(new Domain("uc005-autorenew-future-c.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-d.ie", monthsToRenewal));
        setDsmStateForDomains(vmNrpDomains, 20);
        setDsmStateForDomains(vsNrpDomains, 21);
        String e = "Error. Current domain state prohibits renewal mode modification for a domain: %s";
        assertEquals(String.format(e, vmNrpDomains.get(0).name), enableRenewFailure(user, vmNrpDomains.get(0), true));
        assertEquals(String.format(e, vmNrpDomains.get(1).name), enableRenewFailure(user, vmNrpDomains.get(1), false));
        assertEquals(String.format(e, vsNrpDomains.get(0).name), enableRenewFailure(user, vsNrpDomains.get(0), true));
        assertEquals(String.format(e, vsNrpDomains.get(1).name), enableRenewFailure(user, vsNrpDomains.get(1), false));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_nrparrcnp7() throws SQLException {
        User user = this.registrar;
        int monthsToRenewal = 6;
        String domainPrefix = "uc005-autorenew-future";
        List<Domain> vmNrpDomains = Arrays.asList(new Domain("uc005-autorenew-future-a.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-b.ie", monthsToRenewal));
        List<Domain> vsNrpDomains = Arrays.asList(new Domain("uc005-autorenew-future-c.ie", monthsToRenewal),
                new Domain("uc005-autorenew-future-d.ie", monthsToRenewal));
        List<Domain> allDomains = new ArrayList<Domain>();
        allDomains.addAll(vmNrpDomains);
        allDomains.addAll(vsNrpDomains);
        setDsmStateForDomains(vmNrpDomains, 20);
        setDsmStateForDomains(vsNrpDomains, 21);
        console().login(user);
        for (Domain domain : allDomains) {
            ViewDomainUtils.removeDomainFromNrp(domain.name, ContactType.BILL);
        }
        checkDomainsAvailableForPayment(allDomains, domainPrefix, monthsToRenewal, false);
        enableRenewSuccess(user, vmNrpDomains.get(0), true);
        enableRenewSuccess(user, vmNrpDomains.get(1), false);
        enableRenewSuccess(user, vsNrpDomains.get(0), true);
        enableRenewSuccess(user, vsNrpDomains.get(1), false);
        for (Domain domain : allDomains) {
            emails.add(getNrpReactivationEmail(user, domain));
        }
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_failainrp8() throws SQLException {
        int monthsToRenewal = 0;
        Domain rDomain = new Domain("uc005-failainrp8-a.ie", monthsToRenewal);
        Domain aDomain = new Domain("uc005-failainrp8-b.ie", monthsToRenewal);
        Domain nDomain = new Domain("uc005-failainrp8-c.ie", monthsToRenewal);
        scheduler().runJob(SchedulerJob.AUTORENEWAL);
        assertEquals(1, db().getReservationsCount(rDomain.name));
        assertEquals(1, db().getReservationsCount(aDomain.name));
        assertEquals(0, db().getReservationsCount(nDomain.name));
        scheduler().runJob(SchedulerJob.PUSH_Q);
        assertEquals(18, db().getDsmStateForDomain(nDomain.name));
        db().deleteReservation(db().getReservationId(rDomain.name));
        db().deleteReservation(db().getReservationId(aDomain.name));
        scheduler().runJob(SchedulerJob.PUSH_Q);
        assertEquals(18, db().getDsmStateForDomain(rDomain.name));
        assertEquals(82, db().getDsmStateForDomain(aDomain.name));
        assertEquals(18, db().getDsmStateForDomain(nDomain.name));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc005_xferarrcd9() throws SQLException {
        User user = this.registrar;
        User losing = this.registrarNonVat;
        List<Domain> domains = getDomainsForTransferTest();
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int dsmState = 17;
        testTransferDomain(user, domains, paymentDetails, dsmState);
        emails.addAll(getTransferEmails(user, losing, paymentDetails.getMethod(), domains));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_xferarrcc10() throws SQLException {
        User user = this.registrar;
        User losing = this.registrarNonVat;
        List<Domain> domains = getDomainsForTransferTest();
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int dsmState = 17;
        testTransferDomain(user, domains, paymentDetails, dsmState);
        emails.addAll(getTransferEmails(user, losing, paymentDetails.getMethod(), domains));
        checkAndResetEmails(emails);
    }

    @Test
    public void uc005_xferardcc11() throws SQLException {
        User user = this.direct;
        User losing = this.registrarNonVat;
        List<Domain> domains = getDomainsForTransferTest();
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int dsmState = 25;
        testTransferDomain(user, domains, paymentDetails, dsmState);
        emails.addAll(getTransferEmails(user, losing, paymentDetails.getMethod(), domains));
        checkAndResetEmails(emails);
    }

    private void testDomainRenewed(String domainName) throws SQLException {
        scheduler().runJob(SchedulerJob.AUTORENEWAL);
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        assertFalse(db().reservationSettled(reservationId));
        assertEquals(12, db().getReservationDuration(reservationId));
        assertTrue(db().transactionFinanciallyPassed(transactionId));
        scheduler().runJob(SchedulerJob.INVOICING);
        try {
            db().getSettledInvoiceNumber(domainName);
        } catch (SQLException e) {
            fail("Invoice not generated");
        }
    }

    private void testDomainNotRenewed(String domainName) throws SQLException {
        scheduler().runJob(SchedulerJob.AUTORENEWAL);
        assertEquals(0, db().getReservationsCount(domainName));
        scheduler().runJob(SchedulerJob.INVOICING);
        try {
            db().getSettledInvoiceNumber(domainName);
        } catch (SQLException e) {
            return;
        }
        fail("Invoice shouldn't be generated");
    }

    private void testUnsetAutorenew(User user, int dsmState) throws SQLException {
        String domainPrefix = "uc005-autorenew-future";
        int monthsToRenewal = 6;
        List<Domain> domains = Arrays.asList(new Domain(domainPrefix + "-a.ie", monthsToRenewal), new Domain(
                domainPrefix + "-b.ie", monthsToRenewal), new Domain(domainPrefix + "-c.ie", monthsToRenewal));
        setDsmStateForDomains(domains, dsmState);
        unsetAutorenew(user, domains, domainPrefix);
        checkDomainsAvailableForPayment(domains, domainPrefix, monthsToRenewal, false);
    }

    private void setDsmStateForDomains(List<Domain> domains, int dsmState) throws SQLException {
        for (Domain domain : domains) {
            db().setDsmStateForDomain(domain.name, dsmState);
        }
    }

    private List<String> getDomainNames(List<Domain> domains) {
        List<String> domainNames = new ArrayList<String>();
        for (Domain domain : domains) {
            domainNames.add(domain.name);
        }
        return domainNames;
    }

    private void unsetAutorenew(User user, List<Domain> domains, String domainPrefix) {
        List<String> domainNames = getDomainNames(domains);
        console().login(user);
        console().view(console().url.viewAutorenewDomains);
        AllDomainsPage adp = new AllDomainsPage();
        adp.selectDomainsFromList(domainNames, domainPrefix, "gs_A");
        console().clickElement(By.id("gridaction_noautorenew"));
        console().clickElement(By.id("proceedSubmitButton"));
        console().waitForTextPresentOnPage("Confirmation");
        console().clickElement(By.xpath("//a[@class='button' and .='Return']"));
    }

    private String enableRenewFailure(User user, Domain domain, boolean auto) {
        enableRenew(user, domain, auto);
        console().assertElementPresent(By.cssSelector(".errorMessage"));
        WebElement error = wd().findElement(By.cssSelector(".errorMessage"));
        assertTrue(error.isDisplayed());
        return error.getText();
    }

    private void enableRenewSuccess(User user, Domain domain, boolean auto) {
        enableRenew(user, domain, auto);
        ViewDomainPage.waitForConfirmation(domain.name, false);
    }

    private void enableRenew(User user, Domain domain, boolean auto) {
        String value = auto ? "Autorenew" : "RenewOnce";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domain.name, ContactType.BILL);
        vdp.initModification();
        vdp.renewalModeField.fill(value);
        vdp.submit();
    }

    private void checkDomainsAvailableForPayment(List<Domain> domains, String domainPrefix, int monthsToRenewal,
            boolean nrp) {
        List<String> domainNames = getDomainNames(domains);
        PaymentForDomainsPage pfdp = new PaymentForDomainsPage(domains, nrp, monthsToRenewal);
        pfdp.view();
        AllDomainsPage adp = new AllDomainsPage();
        adp.filterDomainsIfNeeded(domainPrefix, "gs_PK");
        assertTrue(adp.getVisibleDomainNames().containsAll(domainNames));
    }

    private List<Domain> getDomainsForTransferTest() {
        int monthsToRenewal = 6;
        return Arrays.asList(new Domain("uc005-transfer-a.ie", monthsToRenewal), new Domain("uc005-transfer-b.ie",
                monthsToRenewal));
    }

    private void testTransferDomain(User user, List<Domain> domains, PaymentDetails paymentDetails, int dsmState)
            throws SQLException {
        console().login(user);
        for (Domain domain : domains) {
            DomainTransferUtils.transferDomain(domain.name, user, paymentDetails);
        }
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        for (Domain domain : domains) {
            assertEquals(dsmState, db().getDsmStateForDomain(domain.name));
        }
    }

    private Set<ExpectedEmailSummary> getRenewalEmails(User user, String domainName) throws SQLException {
        Set<ExpectedEmailSummary> emails = new HashSet<ExpectedEmailSummary>();
        String orderId = db().getSettledOrderId(domainName);
        String accountName = db().getAccountName(db().getNicHandleAccountNumber(user.login));
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator.getRenewalsPaymentEmail(user,
                PaymentMethod.ADP), orderId));
        emails.add(new DetailedExpectedEmailSummary.BodyContains(emailSummaryGenerator.getInvoiceEmail(user,
                PaymentMethod.ADP), accountName));
        return emails;
    }

    private Set<ExpectedEmailSummary> getNrpEmails(List<Domain> domains, User adminC, User billC) throws SQLException {
        Set<ExpectedEmailSummary> emails = new HashSet<ExpectedEmailSummary>();
        for (Domain domain : domains) {
            ExpectedEmailSummary email = getNrpNotificationEmail(billC, adminC, domain.name);
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(email, domain.name));
        }
        return emails;
    }

    private ExpectedEmailSummary getNrpNotificationEmail(User billC, User adminC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getNrpNotificationEmail(billC, adminC, domainName, true);
    }

    private Set<ExpectedEmailSummary> getTransferEmails(User gaining, User losing, PaymentMethod method,
            List<Domain> domains) throws SQLException {
        Set<ExpectedEmailSummary> emails = new HashSet<ExpectedEmailSummary>();
        for (Domain domain : domains) {
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator
                    .getBillingTransferRequestEmail(gaining, losing, losing), domain.name));
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(getPaymentEmail(gaining, method), domain.name));
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator
                    .getBillingTransferCompletedEmail(gaining, losing, gaining, gaining, domain.name), domain.name));
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator
                    .getBillingTransferDnsEmail(gaining, gaining, domain.name), domain.name));
        }
        return emails;
    }

    private ExpectedEmailSummary getPaymentEmail(User gainingUser, PaymentMethod method) throws SQLException {
        return emailSummaryGenerator.getBillingTransferPaymentEmail(gainingUser, method);
    }

    private ExpectedEmailSummary getNrpReactivationEmail(User billC, Domain domain) throws SQLException {
        ExpectedEmailSummary base = emailSummaryGenerator.getNrpVoluntaryReactivationEmail(billC, this.adminContact,
                domain.name);
        return new DetailedExpectedEmailSummary.SubjectContains(base, domain.name);
    }

}
