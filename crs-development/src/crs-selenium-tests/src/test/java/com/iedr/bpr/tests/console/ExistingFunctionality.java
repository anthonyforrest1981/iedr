package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.*;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.*;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.console.*;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailsVerifier;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExistingFunctionality extends SeleniumTest {

    public ExistingFunctionality(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/ef_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/ef_data.sql";
    }

    @Test
    public void test_ef_nosc01() throws SQLException {
        // Accounting Reports - View Transfer In
        testTransfersReport(this.registrar, this.registrarNonVat, "ef-nosc01.ie", true);
    }

    @Test
    public void test_ef_nosc02() throws SQLException {
        // Accounting Reports - View Transfer Away
        testTransfersReport(this.registrarNonVat, this.registrar, "ef-nosc02.ie", false);
    }

    @Test
    public void test_ef_nosc03() {
        // DNS - Modify DNS
        User user = this.registrar;
        String domainName = "ef-nosc03.ie";
        console().login(user);
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.nameserverForm.fillNameserverDetails(new NameserverFormDetails(Arrays.asList(new DomainNameServer(
                "ns3.dns.ie"), new DomainNameServer("ns4.dns.ie"))));
        ddmp.verify();
        ddmp.submitAndWaitForSuccess();
    }

    @Test
    public void test_ef_nosc04() {
        // Modify DNS - XSS (scenario1)
        User user = this.registrar;
        console().login(user);
        console().view(console().url.dns);
        console().clickElement(By.cssSelector(".jqgrow input[type='checkbox']"));
        WebElement input = wd().findElement(By.id("gridaction_domainlist"));
        JavascriptExecutor js = (JavascriptExecutor) wd();
        js.executeScript("arguments[0].setAttribute('value', '<script>alert(document.cookie);</script>')", input);
        console().clickElement(By.id("gridaction_dnsMod"));
        String error = wd().findElement(By.className("flash-error")).getText();
        assertEquals("Invalid domain list supplied.", error);
    }

    @Test
    public void test_ef_nosc05() {
        // Modify DNS - XSS (scenario2)
        User user = this.registrar;
        String domainName = "ef-nosc05.ie";
        console().login(user);
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.nameserverForm.fillRow(0, new DomainNameServer("<script>alert(document.cookie);</script>"));
        ddmp.submit();
        String expectedError = "Nameserver 1 is not a valid host name";
        console().waitForTextPresentOnPage(expectedError);
        String error = wd().findElement(By.id("ns_0_err")).getText();
        assertEquals(expectedError, error);
    }

    @Test
    public void test_ef_nosc06() {
        // Transfers In, Transfers Away, View Deposit Top-ups - XSS
        User user = this.registrar;
        console().login(user);
        checkDaysFilter(console().url.viewTransfersIn, "TransfersInModel_days", "Days must be an integer.", "30");
        checkDaysFilter(console().url.viewTransfersAway, "TransfersAwayModel_days", "Days must be an integer.", "30");
        checkDaysFilter(console().url.depositTopUps, "days", "Days must be an integer between 1 and 999.", "10");
    }

    @Test
    public void test_ef_nosc07() {
        // Modify DNS - XSS (scenario3)
        User user = this.registrar;
        String domainName = "ef-nosc05.ie";
        console().login(user);
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.nameserverForm.fillNameserverDetails(new NameserverFormDetails(Arrays.asList(new DomainNameServer(
                "ns1.dns.ie"), new DomainNameServer("ns2.dns.ie"))));
        WebElement input = wd().findElement(By.id("domainlist"));
        JavascriptExecutor js = (JavascriptExecutor) wd();
        String invalidValue = "<script>alert(document.cookie);</script>";
        js.executeScript(String.format("arguments[0].setAttribute('value', '%s')", invalidValue), input);
        ddmp.submit();
        console().waitForTextPresentOnPage("Please fix the following input errors");
        for (String singleInvalidValue : invalidValue.split(";")) {
            console().waitForTextPresentOnPage(String.format("%s is not a valid domain address", singleInvalidValue));
        }
    }

    @Test
    public void test_ef_nosc08() throws SQLException {
        // Grid Pagination Limit
        User user = this.registrar;
        console().login(user);
        List<String> urlsToCheck = Arrays.asList(console().url.allDomains, console().url.tickets,
                console().url.futureRenewals, console().url.currentRenewals, console().url.newDomains,
                console().url.viewAutorenewDomains, console().url.viewTransfersIn, console().url.viewTransfersAway,
                console().url.viewCharityDomains);
        List<String> errors = getDefaultPaginationLimitErrors(urlsToCheck);
        int ticketsCount = db().getTicketsCountForNicHandle(user.login);
        String ticketsPaginationAllLimitError = getPaginationLimitError(console().url.tickets, "1000000", ticketsCount);
        if (ticketsPaginationAllLimitError != null) {
            errors.add(ticketsPaginationAllLimitError);
        }
        assertTrue(StringUtils.join(errors, "\n"), errors.isEmpty());
    }

    @Test
    public void test_ef_nosc09() throws SQLException {
        // Reservations - Pending domain should be linked to Ticket
        User user = this.registrar;
        String domainName = "ef-nosc09.ie";
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, PredefinedPayments.VALID_CREDIT_CARD);
        console().view(console().url.todaysCcReservations);
        console().clickElement(By.linkText(domainName));
        console().waitForTextPresentOnPage("Tickets – View Ticket");
    }

    @Test
    @IgnoredBrowsers({Browser.Edge})
    public void test_ef_nosc10() throws NumberFormatException, SQLException {
        // ResellerDefaults - support 2-7 nameservers
        User user = new User("EF10AA-IEDR", "Passw0rd!", true, "ef10aa@iedr.ie");
        console().login(user);
        ResellerDefaultsPage rdp = new ResellerDefaultsPage();
        rdp.view();
        NameserverUtils.testNameserversCount(false, rdp.nameserverForm, console());
        rdp.submitAndWaitForSuccess();
        console().forceLogout();
        console().login(user);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        drp.startRegistration("ef-nosc10.ie");
        testNameserversPopulated(7);
    }

    @Test
    public void test_ef_nosc11_reg() {
        // Edit Ticket - Edit Reg/Xfer/Mod ticket
        User user = this.registrar;
        String domainName = "ef-nosc11-reg.ie";
        testTicketModification(user, domainName, false);
    }

    @Test
    public void test_ef_nosc11_xfer() {
        // Edit Ticket - Edit Reg/Xfer/Mod ticket
        User user = this.registrar;
        String domainName = "ef-nosc11-xfer.ie";
        testTicketModification(user, domainName, true);
    }

    @Test
    public void test_ef_nosc11_mod() {
        // Edit Ticket - Edit Reg/Xfer/Mod ticket
        User user = this.registrar;
        String domainName = "ef-nosc11-mod.ie";
        testTicketModification(user, domainName, false);
    }

    @Test
    public void test_ef_nosc12() {
        // ResellerDefaults - DNS validationn
        User user = this.registrar;
        console().login(user);
        ResellerDefaultsPage rdp = new ResellerDefaultsPage();
        rdp.view();
        NameserverUtils.verifyNoIpScenarios(rdp.nameserverForm, rdp, console());
    }

    @Test
    public void test_ef_nosc13() throws SQLException {
        // Reservations - Renewal - domain should be linked to Domain
        User user = this.registrar;
        String domain = "ef-nosc13.ie";
        List<PaymentForDomainsPage.Domain> domains = Arrays.asList(new PaymentForDomainsPage.Domain(domain, 1));
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, false, 0);
        console().login(user);
        pfd.payForDomainsSuccess(user, domain, paymentDetails);
        console().view(console().url.todaysCcReservations);
        console().clickElement(By.linkText(domain));
        console().waitForTextPresentOnPage("Domains – View Domain");
    }

    @Test
    public void test_ef_nosc14() throws SQLException {
        // Reservations - Transfer - domain should be linked to Ticket
        User user = this.registrar;
        String domain = "ef-nosc14.ie";
        console().login(user);
        DomainTransferUtils.transferDomain(domain, user, PredefinedPayments.VALID_CREDIT_CARD);
        console().view(console().url.todaysCcReservations);
        console().clickElement(By.linkText(domain));
        console().waitForTextPresentOnPage("Tickets – View Ticket");
    }

    @Test
    public void test_ef_nosc15() {
        // All Domains Report - Total Domains by Contact Type
        User user = this.registrar;
        Map<ContactType, Set<String>> domains = new HashMap<>();
        String common = "ef-nosc15.ie";
        domains.put(ContactType.ADMIN, new HashSet<>(Arrays.asList("ef-nosc15-admin.ie", common)));
        domains.put(ContactType.BILL, new HashSet<>(Arrays.asList("ef-nosc15-bill.ie", common)));
        domains.put(ContactType.TECH, new HashSet<>(Arrays.asList("ef-nosc15-tech.ie", common)));
        console().login(user);
        AllDomainsPage adp = new AllDomainsPage();
        adp.view();
        for (ContactType type : ContactType.values()) {
            adp.switchDomainContactType(type);
            adp.filterDomainsByName("ef-nosc15");
            List<String> visibleDomains = adp.getVisibleDomainNames();
            assertEquals(domains.get(type), new HashSet<>(visibleDomains));
        }
    }

    @Test
    public void test_ef_nosc16() throws SQLException {
        // Accounting Reports - View Transfer Away - Historical Data
        User losing = this.registrar;
        User gaining = this.direct;
        String domainName = "ef-nosc16.ie";
        String oldHolder = "Test Holder 0001";
        String newHolder = "Test Holder 0002";
        // Transferring domain
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        console().login(gaining);
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, gaining, authcode, paymentDetails, 1);
        DomainTransferUtils.transferDomain(domainName, gaining, details);
        ViewTicketPage ttp = new ViewTicketPage(internal);
        boolean success = ttp.triplePassTicketAccept(domainName);
        assertTrue(success);
        // Modifying domain holder
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.holderField.fill(newHolder);
        vdp.remarksField.fill("Domain Holder modified");
        vdp.submitAndWaitForSuccess(domainName, true);
        success = ttp.triplePassTicketAccept(domainName);
        assertTrue(success);
        // Verifying that old domain holder is displayed in "Tranfers away" report
        console().login(losing);
        console().view(console().url.viewTransfersAway);
        ViewTransfersPage vtp = new ViewTransfersPage();
        vtp.filterDomainsByName(domainName);
        String reportHolder = vtp.getHolder(domainName);
        assertEquals(oldHolder, reportHolder);
    }

    @Test
    public void test_ef_nosc17() throws SQLException, IOException, JSchException {
        // Email sending error - E_ID=185
        User user = this.registrar;
        String domainName = "ef-nosc17.ie";
        EmailAttemptsTest test = new EmailAttemptsTest(user, domainName);
        console().login(user);
        test.generateInvoice();
        smtpServer.stop();
        test.sendInvoice();
        test.renameInvoice();
        test.waitForFileNotFoundException();
        smtpServer.start();
        test.waitForAttemptsLimitReached();
        waitForEmailReceived();
        emails.add(emailSummaryGenerator.getEmailSendingFatalErrorEmail());
        checkAndResetEmails(emails);
    }

    @Test
    public void test_ef_nosc18() throws Exception {
        // Additional BCC addressee
        User user = this.registrar;
        String domainName = "ef-nosc17.ie";
        EmailAttemptsTest test = new EmailAttemptsTest(user, domainName);
        console().login(user);
        smtpServer.stop();
        // Generate email.
        PasswordUtils.requestPasswordReset(user);
        test.waitForSmtpHostTimeoutException();
        // Wait 10 more seconds to make sure, that multiple attempts have been made.
        Thread.sleep(10000);
        smtpServer.start();
        waitForEmailReceived();

        ExpectedEmailSummary expectedEmail = emailSummaryGenerator.getNhPasswordResetEmail(user);
        emails.add(expectedEmail);
        Set<ActualEmailSummary> actualEmails = partiallyCheckAndResetEmails(emails);
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(emails, actualEmails);
        ActualEmailSummary actualEmail = rev.getActualEmail(expectedEmail);

        List<String> recipients = actualEmail.smtpMessage.getRecipients();
        String additionalBccAddress = config.getProperty("additionalbccaddress");
        // Count occurrences of additionalBccAddress on recipients list.
        assertEquals(StringUtils.join(recipients, ", "), 1,
                Collections.frequency(recipients, String.format("<%s>", additionalBccAddress)));
    }

    @Test
    public void test_ef_nosc19() throws Exception {
        // Transfers In, Transfers Away - filtering by columns
        User user = this.registrar;
        Date date = new LocalDate().minusDays(5).toDate();
        Date renDate = new LocalDate(date).plusMonths(12).toDate();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String renDateString = new SimpleDateFormat("yyyy-MM-dd").format(renDate);
        console().login(user);
        ViewTransfersPage vtp = new ViewTransfersPage();
        vtp.viewTransfersIn();
        testFilter(vtp, "gs_A", "ef-nosc08-e-9", 11);
        testFilter(vtp, "gs_B", "Test Holder 09", 11);
        testFilter(vtp, "gs_C", dateString, 10);
        testFilter(vtp, "gs_D", dateString, 10);
        testFilter(vtp, "gs_E", renDateString, 10);
        vtp.viewTransfersAway();
        testFilter(vtp, "gs_A", "ef-nosc08-f-9", 11);
        testFilter(vtp, "gs_B", "Test Holder 09", 11);
        testFilter(vtp, "gs_C", dateString, 10);
        testFilter(vtp, "gs_D", dateString, 10);
        testFilter(vtp, "gs_E", renDateString, 10);
    }


    private class EmailAttemptsTest {

        private User user;
        private String domainName;
        private String invoiceNumber;
        private OutputFiles files = new OutputFiles(ssh().crsws);

        public EmailAttemptsTest(User user, String domainName) {
            this.user = user;

            this.domainName = domainName;
        }

        public void generateInvoice() throws SQLException {
            registerDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
            ViewTicketPage ttp = new ViewTicketPage(internal);
            ttp.triplePassTicketAccept(domainName);
            scheduler().runJob(SchedulerJob.INVOICING);
            smtpServer.restart();
            invoiceNumber = db().getSettledInvoiceNumber(domainName);
        }

        public void sendInvoice() {
            InvoicePage ip = new InvoicePage();
            ip.view();
            assertTrue(ip.sendInvoice(invoiceNumber));
        }

        public void renameInvoice() throws SQLException, IOException, JSchException {
            files.renameInvoice(invoiceNumber);
        }

        public void waitForFileNotFoundException() throws SQLException {
            final String invoicePdfPath = files.getInvoicePdfPath(invoiceNumber);
            waitForLineInLog(String.format("java.io.FileNotFoundException: %s", invoicePdfPath));
        }

        public void waitForAttemptsLimitReached() {
            waitForLineInLog("Error sending email, attempt limit reached, email cancelled");
        }

        public void waitForSmtpHostTimeoutException() {
            waitForLineInLog("javax.mail.MessagingException: Could not connect to SMTP host");
        }

        private void waitForLineInLog(final String text) {
            new WebDriverWait(wd(), 60, 1000).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    String log = null;
                    try {
                        log = ssh().crsws.getTomcatLogTail(30);
                        return log.contains(text);
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        }

        private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
            DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, true);
            DomainRegistrationUtils.registerDomain(domainName, user, details);
        }
    }

    private void testNameserversPopulated(int count) {
        for (int i = 0; i < count; i++) {
            String id = String.format("ns_%s", i);
            String value = wd().findElement(By.id(id)).getAttribute("value");
            assertEquals(id, String.format("ns%s.dns.ie", i), value);
        }
    }

    private void testTransfersReport(User gaining, User losing, String domainName, boolean inbound) throws SQLException {
        console().login(gaining);
        DomainTransferUtils.transferDomain(domainName, gaining, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        scheduler().runJob(SchedulerJob.TRIPLE_PASS);
        ViewTransfersPage vtp = new ViewTransfersPage();
        if (inbound) {
            console().login(gaining);
            vtp.viewTransfersIn();
        } else {
            console().login(losing);
            vtp.viewTransfersAway();
        }
        vtp.filterDomainsByName(domainName);
        String transferDate = vtp.getTransferDate(domainName);
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), transferDate);
    }

    private void checkDaysFilter(String pageUrl, String fieldId, String expectedError, String defaultValue) {
        String value = "<script>alert(document.cookie);</script>";
        console().view(pageUrl);
        console().fillInput(fieldId, value);
        console().clickAndWaitForPageToLoad(By.cssSelector("input[value='Update']"));
        String error = wd().findElement(By.className("errorMessage")).getText();
        assertEquals(expectedError, error);
        assertEquals(defaultValue, wd().findElement(By.id(fieldId)).getAttribute("value"));
    }

    private List<String> getDefaultPaginationLimitErrors(List<String> urlsToCheck) {
        List<String> errors = new ArrayList<String>();
        for (String url : urlsToCheck) {
            int limit = 250;
            String error = getPaginationLimitError(url, String.valueOf(limit), limit);
            if (error != null) {
                errors.add(error);
            }
        }
        return errors;
    }

    private String getPaginationLimitError(String url, String limit, int expected) {
        String error = null;
        try {
            checkPaginationLimit(url, limit, expected);
        } catch (PaginationLimitException e) {
            error = e.getMessage();
        }
        return error;
    }

    private void checkPaginationLimit(String url, String limit, int expected) throws PaginationLimitException {
        console().view(url);
        By selectBy = By.cssSelector(".ui-pg-selbox[role='listbox']");
        Select select = new Select(wd().findElement(selectBy));
        String current = select.getFirstSelectedOption().getAttribute("value");
        waitForLimitApplied(url, current, Integer.valueOf(current));
        console().selectOptionByValue(selectBy, limit);
        try {
            waitForLimitApplied(url, limit, expected);
        } finally {
            console().selectOptionByValue(selectBy, current);
            try {
                waitForLimitApplied(url, current, Integer.valueOf(current));
            } catch (PaginationLimitException e) {
                throw new RuntimeException("Failed to revert the limit to old "
                        + "value, other pagination limit checks will not be " + "stable");
            }
        }
    }

    private void waitForLimitApplied(String url, String limit, final int expected) throws PaginationLimitException {
        final By rowSelector = By.cssSelector("#thisJqGrid tbody .jqgrow");
        try {
            new WebDriverWait(wd(), 15).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    return driver.findElements(rowSelector).size() == expected;
                }
            });
        } catch (TimeoutException e) {
            int actual = wd().findElements(rowSelector).size();
            String timeoutError = String.format("URL: %s. Expected %s rows, got %s (limit set to %s)", url, expected,
                    actual, limit);
            throw new PaginationLimitException(timeoutError, e);
        }
    }

    private class PaginationLimitException extends Exception {
        public PaginationLimitException(String msg, Exception e) {
            super(msg, e);
        }

        private static final long serialVersionUID = 2406247732664831280L;
    }

    private void testTicketModification(User user, String domainName, boolean isTransfer) {
        console().login(user);
        TicketUtils.initEditTicket(domainName);
        EditTicketPage etp = new EditTicketPage();
        String holder = "New Holder";
        String remark = "Remark";
        DomainNameServer dns = new DomainNameServer("ns1.dns.ie");

        if (!isTransfer) {
            etp.holderField.fill(holder);
        }
        etp.remarksField.fill(remark);
        etp.nameserverForm.fillRow(0, dns);
        etp.contactsForm.admin2Field.fill(user.login);
        etp.contactsForm.techField.fill(this.adminContact.login);
        etp.findAndFillAdminContact(this.adminContact);
        etp.submitAndWaitForSuccess();

        com.iedr.bpr.tests.pages.console.ViewTicketPage vtp = new com.iedr.bpr.tests.pages.console.ViewTicketPage();
        if (!isTransfer) {
            vtp.checkFormValue("Domain Holder", holder);
        }
        vtp.checkFormValue("Ticket Remark", remark);
        vtp.checkFormValue("Nameserver 1", dns.name);
        vtp.checkFormValue("Admin Contact 1", this.adminContact.login);
        vtp.checkFormValue("Admin Contact 2", user.login);
        vtp.checkFormValue("Tech Contact", this.adminContact.login);
    }

    private void testFilter(DomainGridPage page, String filterId, String prefix, int expectedCount) {
        int total = page.getVisibleDomainNames().size();
        assertTrue(total > expectedCount);
        page.filterDomains(prefix, filterId);
        assertEquals(expectedCount, page.getVisibleDomainNames().size());
        page.clearFilter(filterId);
        assertEquals(total, page.getVisibleDomainNames().size());
    }

}
