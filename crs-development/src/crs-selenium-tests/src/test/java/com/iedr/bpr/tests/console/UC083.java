package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.EmailIdTestWatcher;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.CreateNicHandlePage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.pages.crsweb.DocumentsPage;
import com.iedr.bpr.tests.pages.crsweb.EditNicHandlePage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.*;
import com.iedr.bpr.tests.utils.IncomingDocs.DocumentType;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.PaymentUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.E_ID;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailVerifier;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailVerifier.ReceivedEmailType;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailsVerifier;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC083 extends SeleniumTest {

    private Integer eId;
    private boolean disabled;
    private boolean suppressable; // See CRS-212.
    private User disablingUser;
    private User secondRegistrar;

    private ExpectedEmailSummary expectedEmail;

    private boolean checkEmailOnTearDown;

    @Rule
    public EmailIdTestWatcher eIdWatcher = new EmailIdTestWatcher();

    public UC083(Browser browser, boolean disabled) {
        super(browser);
        this.disabled = disabled;
        this.suppressable = true;
        disablingUser = new User("UC083AA-IEDR", "Passw0rd!", true, "uc083_aa@iedr.ie");
        secondRegistrar = new User("UC083AB-IEDR", "Passw0rd!", true, "uc083_ab@iedr.ie");
        expectedEmail = null;
    }

    @Parameters(name = "{0}, email disabled: {1}")
    public static List<Object[]> disabled() throws IOException {
        List<Browser> browsers = SeleniumTest.getBrowsers();
        List<Object[]> parameters = new ArrayList<>();
        for (Browser browser : browsers) {
            parameters.add(new Object[] {browser, true});
            parameters.add(new Object[] {browser, false});
        }
        return parameters;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        eId = eIdWatcher.getEmailId();
        if (eId != null) {
            if (eIdWatcher.isConfigurable()) {
                configureEmailSending(eId, disabled);
                checkEmailOnTearDown = true;
            } else {
                checkConfigurationDisabled();
            }
        }
    }

    @Override
    public void tearDown() throws Exception {
        try {
            if (checkEmailOnTearDown) {
                checkEmail();
            }
        } finally {
            super.tearDown();
        }
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc083_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc083_data.sql";
    }

    @Test
    @E_ID(59)
    public void test_uc083_eid59() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid59.ie";
        registerDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        db().setDepositAmount(user.login, 0);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getRegistrationInsufficientFundsEmail(user, domainName);
    }

    @Test
    @E_ID(73)
    public void test_uc083_eid73() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        db().setDepositAmount(user.login, 0);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getBillingTransferInsufficientFundsEmail(user, domainName);
    }

    @Test
    @E_ID(2)
    public void test_uc083_eid2() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals";
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        payForDomain(user, domainPrefix, paymentDetails, false);
        expectedEmail = emailSummaryGenerator.getRenewalsPaymentEmail(user, paymentDetails.getMethod());
    }

    @Test
    @Ignore("Transfer pending domains can no longer be put into vNRP. Test can be removed after TestRail is updated.")
    @E_ID(10)
    public void test_uc083_eid10() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getBillingTransferPaymentEmail(user, PaymentMethod.ADP);
    }

    @Test
    @E_ID(7)
    public void test_uc083_eid7() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid7.ie";
        registerDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getRegistrationPaymentEmail(user, PaymentMethod.ADP, domainName);
    }

    @Test
    @E_ID(1)
    public void test_uc083_eid1() throws SQLException {
        User user = disablingUser;
        PaymentUtils.topUpAccount(user, "1000");
        expectedEmail = emailSummaryGenerator.getDepositTopUpEmail(user);
    }

    @Test
    @E_ID(value = 33, configurable = false)
    public void test_uc083_eid33() {}

    @Test
    @E_ID(38)
    public void test_uc083_eid38() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getBillingTransferDnsEmail(user, user, domainName);
    }

    @Test
    @E_ID(40)
    public void test_uc083_eid40() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid40.ie";
        registerDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        expectedEmail = emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName);
    }

    @Test
    @E_ID(35)
    public void test_uc083_eid35() throws SQLException {
        User gainingUser = disablingUser;
        User losingUser = registrar;
        String domainName = "uc083-transfer.ie";
        transferDomain(gainingUser, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.generateTicketHoldUp(domainName);
        scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
        expectedEmail = emailSummaryGenerator.getBillingTransferHoldUpEmail(gainingUser, losingUser, gainingUser,
                domainName);
    }

    @Test
    @E_ID(41)
    public void test_uc083_eid41() throws NumberFormatException, SQLException {
        User user = disablingUser;
        String domainName = "uc083-modify.ie";
        modifyDomain(user, domainName);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.generateTicketHoldUp(domainName);
        scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
        expectedEmail = emailSummaryGenerator.getDomainModificationHoldUpEmail(user, user, domainName);
    }

    @Test
    @E_ID(42)
    public void test_uc083_eid42() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid42.ie";
        registerDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.generateTicketHoldUp(domainName);
        scheduler().runJob(SchedulerJob.EXPIRING_TICKET_EMAIL);
        ttp.triplePassTicketReject(domainName, "Hold Update");
        expectedEmail = emailSummaryGenerator.getRegistrationHoldUpEmail(user, user, domainName);
    }

    @Test
    @E_ID(43)
    public void test_uc083_eid43() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid43.ie";
        registerDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getRegistrationAcceptedEmail(user, user, domainName);
    }

    @Test
    @E_ID(45)
    public void test_uc083_eid45() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid45.ie";
        registerDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getRegistrationDnsVerifiedEmail(user, user, domainName);
    }

    @Test
    @E_ID(65)
    public void test_uc083_eid65() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals-a";
        String domainName = domainPrefix + ".ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        db().setDsmStateForDomain(domainName, 18);
        payForDomain(user, domainPrefix, paymentDetails, true);
        expectedEmail = emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, true,
                paymentDetails.getMethod());
    }

    @Test
    @E_ID(66)
    public void test_uc083_eid66() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals-a";
        String domainName = domainPrefix + ".ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        db().setDsmStateForDomain(domainName, 20);
        payForDomain(user, domainPrefix, paymentDetails, true);
        expectedEmail = emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, false,
                paymentDetails.getMethod());
    }

    @Test
    @E_ID(67)
    public void test_uc083_eid67() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals";
        String domainName = domainPrefix + ".ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        db().setDsmStateForDomain(domainName, 18);
        payForDomain(user, domainPrefix, paymentDetails, true);
        expectedEmail = emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, true,
                paymentDetails.getMethod());
    }

    @Test
    @E_ID(68)
    public void test_uc083_eid68() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals-a";
        String domainName = domainPrefix + ".ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        db().setDsmStateForDomain(domainName, 20);
        payForDomain(user, domainPrefix, paymentDetails, true);
        expectedEmail = emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, false,
                paymentDetails.getMethod());
    }

    @Test
    @E_ID(15)
    public void test_uc083_eid15() throws SQLException {
        User user = disablingUser;
        User adminContact = secondRegistrar;
        String domainName = "uc083-eid15.ie";
        console().login(user);
        ViewDomainUtils.removeDomainFromNrp(domainName, ContactType.BILL);
        expectedEmail = emailSummaryGenerator.getNrpVoluntaryReactivationEmail(user, adminContact, domainName);
    }

    @Test
    @E_ID(11)
    public void test_uc083_eid11() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid11.ie";
        scheduler().runJob(SchedulerJob.PUSH_Q);
        ExpectedEmailSummary email = emailSummaryGenerator.getNrpNotificationEmail(user, user, domainName, false);
        expectedEmail = new DetailedExpectedEmailSummary.SubjectContains(email, domainName);
    }

    @Test
    @E_ID(13)
    public void test_uc083_eid13() throws SQLException {
        suppressable = false;
        User user = disablingUser;
        String domainName = "uc083-eid13.ie";
        enterDomainToNrp(user, domainName);
        expectedEmail = emailSummaryGenerator.getNrpNotificationEmail(user, user, domainName, true);
    }

    @Test
    @E_ID(value = 14, configurable = false)
    public void test_uc083_eid14() throws SQLException {}

    @Test
    @E_ID(value = 64, configurable = false)
    public void test_uc083_eid64() throws SQLException {}

    @Test
    @E_ID(3)
    public void test_uc083_eid3() throws SQLException {
        User user = disablingUser;
        String domainPrefix = "uc083-renewals";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        payForDomain(user, domainPrefix, paymentDetails, false);
        expectedEmail = emailSummaryGenerator.getRenewalsPaymentEmail(user, paymentDetails.getMethod());
    }

    @Test
    @E_ID(5)
    public void test_uc083_eid5() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid5.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        registerDomain(user, domainName, paymentDetails);
        expectedEmail = emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName);
    }

    @Test
    @E_ID(8)
    public void test_uc083_eid8() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        transferDomain(user, domainName, paymentDetails);
        expectedEmail = emailSummaryGenerator.getBillingTransferPaymentEmail(user, paymentDetails.getMethod());
    }

    @Test
    @E_ID(16)
    public void test_uc083_eid16() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid16.ie";
        scheduler().runJob(SchedulerJob.PUSH_Q);
        expectedEmail = emailSummaryGenerator.getRenewalDatePassedEmail(user, domainName);
    }

    @Test
    @E_ID(value = 17, configurable = false)
    public void test_uc083_eid17() {}

    @Test
    @E_ID(value = 18, configurable = false)
    public void test_uc083_eid18() {}

    @Test
    @E_ID(value = 19, configurable = false)
    public void test_uc083_eid19() {}

    @Test
    @E_ID(value = 24, configurable = false)
    public void test_uc083_eid24() {}

    @Test
    @E_ID(value = 25, configurable = false)
    public void test_uc083_eid25() {}

    @Test
    @E_ID(28)
    public void test_uc083_eid28() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-modify.ie";
        String reason = "Cancelled";
        modifyDomain(user, domainName);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, reason);
        expectedEmail = emailSummaryGenerator.getDomainModificationRemarksEmail(user, domainName, reason);
    }

    @Test
    @E_ID(36)
    public void test_uc083_eid36() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        expectedEmail = emailSummaryGenerator.getBillingTransferAcceptedEmail(user, user, domainName);
    }

    @Test
    @E_ID(value = 39, configurable = false)
    public void test_uc083_eid39() {}

    @Test
    @E_ID(value = 46, configurable = false)
    public void test_uc083_eid46() {}

    @Test
    @E_ID(47)
    public void test_uc083_eid47() throws SQLException, JSchException, IOException {
        User user = disablingUser;
        IncomingDocs incomingDocs = new IncomingDocs(config.getProperty("documents_dir_path"));
        Map<String, String> initialAppConfig = incomingDocs.getCurrentAppConfig();
        String domainName = "uc083-eid47.ie";
        String source = "Source";
        try {
            String newFileName = UUID.randomUUID().toString() + ".pdf";
            String fileContent = UUID.randomUUID().toString();
            String tempDirName = incomingDocs.populateDocDirs(domainName, newFileName, fileContent);
            incomingDocs.setIncomingDocsDirectory(tempDirName, initialAppConfig);
            DocumentsPage dp = new DocumentsPage(this.internal);
            dp.viewDocumentsList(domainName, false);
            int accountNumber = db().getAccountForDomain(domainName);
            dp.assignDocument(DocumentType.ATTACHMENT_NEW, newFileName, domainName, accountNumber, source);
        } finally {
            incomingDocs.updateAppConfig(initialAppConfig, initialAppConfig);
            incomingDocs.removeCreatedDocDirs();
        }
        expectedEmail = emailSummaryGenerator.getDocumentationReceivedEmail(user, domainName, source);
    }

    @Test
    @E_ID(value = 49, configurable = false)
    public void test_uc083_eid49() {}

    @Test
    @E_ID(value = 50, configurable = false)
    public void test_uc083_eid50() {}

    @Test
    @E_ID(value = 51)
    public void test_uc083_eid51() throws Exception {
        String contactEmail = "uc083_eid51@iedr.ie";
        String contactNhName = "UC083 EID=51 new nic";
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        NicHandleDetails details = new NicHandleDetails(contactNhName, contactEmail);
        console().login(this.disablingUser);
        cnhp.view();
        cnhp.fillNewNicForm(details);
        cnhp.submitAndWaitForSuccess();
        String nh = db().getNicHandleByName(contactNhName);
        User newUser = new User(nh, null, false, contactEmail);
        expectedEmail = emailSummaryGenerator.getNhRegistrationEmail(newUser);
    }

    @Test
    @E_ID(value = 56, configurable = false)
    public void test_uc083_eid56() {}

    @Test
    @E_ID(value = 57, configurable = false)
    public void test_uc083_eid57() {}

    @Test
    @E_ID(value = 60, configurable = false)
    public void test_uc083_eid60() {}

    @Test
    @E_ID(value = 61, configurable = false)
    public void test_uc083_eid61() {}

    @Test
    @E_ID(71)
    public void test_uc083_eid71() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid71.ie";
        registerDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, "Cancelled");
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        expectedEmail = emailSummaryGenerator.getApplicationFailedEmail(user, true);
    }

    @Test
    @E_ID(72)
    public void test_uc083_eid72() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, "Cancelled");
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        expectedEmail = emailSummaryGenerator.getApplicationFailedEmail(user, false);
    }

    @Test
    @E_ID(79)
    public void test_uc083_eid79() throws SQLException {
        User user = disablingUser;
        crsweb().login(this.internal);
        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.addSelectFieldChange(By.id("nic-handle-edit_wrapper_vatCategory"), "A");
        enhp.editNicHandle(user.login);
        expectedEmail = emailSummaryGenerator.getNhVatStatusChanged(user);
    }

    @Test
    @E_ID(value = 82, configurable = false)
    public void test_uc083_eid82() {}

    @Test
    @E_ID(value = 83, configurable = false)
    public void test_uc083_eid83() {}

    @Test
    @E_ID(value = 84, configurable = false)
    public void test_uc083_eid84() {}

    @Test
    @E_ID(85)
    public void test_uc083_eid85() throws NumberFormatException, SQLException {
        User user = disablingUser;
        String domainName = "uc083-modify.ie";
        modifyDomain(user, domainName);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.expireTicket(domainName);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        expectedEmail = emailSummaryGenerator.getDomainModificationExpiredEmail(user, user, domainName);
    }

    @Test
    @E_ID(86)
    public void test_uc083_eid86() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid86.ie";
        registerDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.expireTicket(domainName);
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        expectedEmail = emailSummaryGenerator.getRegistrationExpiredEmail(user, user, domainName);
    }

    @Test
    @E_ID(91)
    public void test_uc083_eid91() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-eid91.ie";
        crsweb().login(this.internal);
        com.iedr.bpr.tests.pages.crsweb.ViewDomainPage vdp = new com.iedr.bpr.tests.pages.crsweb.ViewDomainPage();
        vdp.changeDomainHolderType(domainName, "Charity");
        expectedEmail = emailSummaryGenerator.getDomainModificationReceivedEmail(user, domainName);
    }

    @Test
    @E_ID(value = 92, configurable = false)
    public void test_uc083_eid92() {}

    @Test
    @E_ID(value = 101, configurable = false)
    public void test_uc083_eid101() {}

    @Test
    @E_ID(value = 102, configurable = false)
    public void test_uc083_eid102() {}

    @Test
    @E_ID(value = 103, configurable = false)
    public void test_uc083_eid103() {}

    @Test
    @E_ID(104)
    public void test_uc083_eid104() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, "Cancelled");
        scheduler().runJob(SchedulerJob.TICKET_AND_TRANSACTION_CLEANUP);
        expectedEmail = emailSummaryGenerator.getTicketRemovalEmail(user, domainName);
    }

    @Test
    @E_ID(value = 105, configurable = false)
    public void test_uc083_eid105() {}

    @Test
    @E_ID(value = 120, configurable = false)
    public void test_uc083_eid120() {}

    @Test
    @E_ID(value = 129, configurable = false)
    public void test_uc083_eid129() {}

    @Test
    @E_ID(value = 131, configurable = false)
    public void test_uc083_eid131() {}

    @Test
    @E_ID(value = 133, configurable = false)
    public void test_uc083_eid133() {}

    @Test
    @E_ID(141)
    public void test_uc083_eid141() throws SQLException {
        User user = disablingUser;
        User techAndAdminContact = secondRegistrar;
        String domainName = "uc083-modify-b.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillRow(0, new DomainNameServer("ns1.dns.ie"));
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, false);
        expectedEmail = emailSummaryGenerator.getDnsModificationEmail(user, user, techAndAdminContact,
                techAndAdminContact, false, domainName);
    }

    @Test
    @E_ID(value = 142, configurable = true)
    public void test_uc083_eid142() throws SQLException {
        suppressable = false;
        User user = disablingUser;
        String domainName = "uc083-modify-a.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.ADMIN);
        vdp.initModification();
        vdp.nameserverForm.fillRow(0, new DomainNameServer("ns1.dns.ie"));
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, false);
        expectedEmail = emailSummaryGenerator.getDnsModificationEmail(user, secondRegistrar, user, secondRegistrar,
                true, domainName);
    }

    @Test
    @E_ID(value = 151, configurable = false)
    public void test_uc083_eid151() {}

    @Test
    @E_ID(value = 170, configurable = false, active = false)
    public void test_uc083_eid170() {
        User user = disablingUser;
        configureEmailSending(1, false);
        expectedEmail = emailSummaryGenerator.getEmailDisablerSettingChangedEmail(user);
    }

    @Test
    @E_ID(value = 171, configurable = false)
    public void test_uc083_eid171() {}

    @Test
    @E_ID(186)
    public void test_uc083_eid186() throws SQLException {
        User user = disablingUser;
        String domainName = "uc083-transfer.ie";
        String reason = "Cancelled";
        transferDomain(user, domainName, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketReject(domainName, reason);
        expectedEmail = emailSummaryGenerator.getBillingTransferRemarksEmail(user, domainName, reason);
    }

    private void configureEmailSending(int eId, boolean disabled) {
        console().login(disablingUser);
        console().view(console().url.emailDisabler);
        String value = disabled ? "disabled" : "enabled";
        String message = String.format("Configuration of E_ID=%s should be allowed", eId);
        String css = String.format("tr[id='%s'] input[type='radio']", eId);
        assertTrue(message, crsweb().isElementPresentInstantaneously(By.cssSelector(css)));
        By inputBy = By.cssSelector(String.format("%s[value='%s']", css, value));
        WebElement input = wd().findElement(inputBy);
        if (!input.isSelected()) {
            console().selectElement(inputBy);
            console().clickAndWaitForPageToLoad(By.name("yt1"));
            console().waitForTextPresentOnPage("Successfully updated status of 1 email.");
        }
    }

    private void checkConfigurationDisabled() {
        console().login(disablingUser);
        console().view(console().url.emailDisabler);
        String message = String.format("Configuration of E_ID=%s should not be allowed", eId);
        String css = String.format("tr[id='%s'] input[type='radio']", eId);
        assertFalse(message, crsweb().isElementPresentInstantaneously(By.cssSelector(css)));
        By row = By.cssSelector(String.format("tr[id='%s']", eId));
        if (eIdWatcher.isActive()) {
            WebElement tr = wd().findElement(row);
            assertTrue(tr.getText().contains("Cannot be disabled"));
        } else {
            assertFalse(String.format("E_ID=%s is inactive and should not be displayed", eId), crsweb()
                    .isElementPresentInstantaneously(row));
        }
    }

    private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, paymentDetails);
    }

    private void transferDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        DomainTransferUtils.transferDomain(domainName, user, paymentDetails);
    }

    private void modifyDomain(User user, String domainName) {
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.holderField.fill("Modified Holder");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
    }

    private void payForDomain(User user, String domainPrefix, PaymentDetails paymentDetails, boolean nrp)
            throws SQLException {
        console().login(user);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(Arrays.asList(new Domain(domainPrefix + ".ie", 1)), nrp,
                0);
        pfd.payForDomainsSuccess(user, domainPrefix, paymentDetails);
    }

    private void enterDomainToNrp(User user, String domainName) {
        console().login(user);
        ViewDomainUtils.enterDomainToNrp(domainName, ContactType.BILL);
    }

    private void checkEmail() throws SQLException {
        ActualEmailSummary actualEmail = getActualEmail();
        ReceivedEmailVerifier rev = new ReceivedEmailVerifier(expectedEmail, actualEmail);
        ReceivedEmailType receiverType = rev.getReceiverType(actualEmail);
        if (disabled && suppressable) {
            assertTrue(String.format("External email E_ID=%s sent (%s)", eId, actualEmail),
                    receiverType == ReceivedEmailType.INTERNAL || receiverType == ReceivedEmailType.BOTH);
        } else {
            assertTrue(String.format("External email E_ID=%s not sent (%s)", eId, actualEmail),
                    receiverType == ReceivedEmailType.EXTERNAL || receiverType == ReceivedEmailType.BOTH);
        }
        if (!disabled) {
            // Perform full verification in case this email is not generated in
            // another test case.
            rev.verify();
        }
    }

    private ActualEmailSummary getActualEmail() {
        smtpServer.stop();
        Set<ActualEmailSummary> emails = smtpServer.getEmailSummaries();
        Set<Integer> emailIds = ReceivedEmailsVerifier.getEmailIds(emails);
        assertTrue(String.format("Email E_ID=%s not sent", eId), emailIds.contains(eId));
        Set<ExpectedEmailSummary> expectedEmails = new HashSet<ExpectedEmailSummary>(Arrays.asList(expectedEmail));
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(expectedEmails, emails);
        ActualEmailSummary actualEmail = rev.getActualEmail(expectedEmail);
        smtpServer.start();
        return actualEmail;
    }

}
