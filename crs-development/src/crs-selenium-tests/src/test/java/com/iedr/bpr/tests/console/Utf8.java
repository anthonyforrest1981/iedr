package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.forms.console.PaymentForm;
import com.iedr.bpr.tests.pages.console.*;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.PasswordUtils;
import com.iedr.bpr.tests.utils.console.PaymentUtils;
import com.iedr.bpr.tests.utils.console.TicketUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@IgnoredBrowsers({SeleniumTest.Browser.Edge})
public class Utf8 extends SeleniumTest {

    private static String FOUR_BYTES_CHAR = "\uD83D\uDCA9";
    private static String ALLOW_IDN_CFG_KEY = "allow_idn_domains";
    private String initialAllowIdn;

    public Utf8(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/utf8_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/utf8_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initialAllowIdn = db().getAppConfigValue(ALLOW_IDN_CFG_KEY);
        db().setAppConfigStringValue(ALLOW_IDN_CFG_KEY, "1");
    }

    @Override
    public void tearDown() throws Exception {
        try {
            if (initialAllowIdn != null) {
                db().setAppConfigStringValue(ALLOW_IDN_CFG_KEY, initialAllowIdn);
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void testLoginPage() {
        User user = new User("UTF8AA-IE\u0301DR", "Pa\u0301ssw0rd!");
        final LoginPage lp = new LoginPage();
        lp.view();
        testForm(lp);
        lp.fillForm(user.login, user.password);
        lp.submitAndWaitForSuccess();
        assertTrue(lp.wasLoginSuccessful());
    }

    @Test
    public void testResetPasswordPage() throws SQLException {
        User user = registrar;
        RequestPasswordChangePage rpcp = new RequestPasswordChangePage();
        rpcp.view();
        testForm(rpcp);
        rpcp.fill(user.login);
        rpcp.submitAndWaitForSuccess();

        emails.add(emailSummaryGenerator.getNhPasswordResetEmail(user));
        Set<ActualEmailSummary> actualEmails = checkAndResetEmails(emails);
        String url = PasswordUtils.getResetPasswordUrl(actualEmails);
        ResetPasswordPage rpp = new ResetPasswordPage(url);
        rpp.view();
        testForm(rpp);
    }

    @Test
    public void testChangePasswordPage() {
        ChangePasswordPage cpp = new ChangePasswordPage();
        console().login(registrar);
        cpp.view();
        testForm(cpp);
    }

    @Test
    public void testAuthcodePortalPage() {
        final AuthcodePortalPage page = new AuthcodePortalPage();
        page.view();
        testForm(page);
    }

    @Test
    public void testTopUpPage() {
        TopUpPage topUp = new TopUpPage();
        console().login(registrar);
        topUp.view();
        testForm(topUp);
    }

    @Test
    public void testResellerDefaultsPage() throws SQLException {
        ResellerDefaultsPage rdp = new ResellerDefaultsPage();
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        String period = "10";
        String invoiceType = "PDF";
        console().login(registrar);
        rdp.view();
        testForm(rdp);
        rdp.fill("UTF8ENH-IEDR", dnsDetails, period, invoiceType);
        rdp.submitAndWaitForSuccess();
        verifyNormalizedNameserverDetails(db().getDnsListForReseller(registrar.login));
    }

    @Test
    public void testEditNicHandlePage() throws SQLException {
        EditNicHandlePage enhp = new EditNicHandlePage();
        User user = new User("UTF8ENH-IEDR", "Passw0rd!");
        NicHandleDetails details = new NicHandleDetails("UTF8CNHP User A\u0303", "utf8enhp@ie\u0323dr.ie", "1234",
                "12345", "Registrar O\u0302E\u0304 Limited", "1 Som\u0301e Street", "Ireland", "Co. Dublin");
        console().login(user);
        enhp.view(user);
        testForm(enhp);
        enhp.fillNicHandleForm(details);
        enhp.submitAndWaitForSuccess();
        verifyNicHandleInDb(user.login, "UTF8CNHP User Ã", "utf8enhp@iẹdr.ie", "Registrar ÔĒ Limited", "1 Soḿe Street");
    }

    @Test
    public void testCreateNicHandlePage() throws SQLException {
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        NicHandleDetails details = new NicHandleDetails("UTF8CNHP User A\u0303", "utf8cnhp@ie\u0323dr.ie", "1234",
                "12345", "Registrar O\u0302E\u0304 Limited", "1 Som\u0301e Street", "Ireland", "Co. Dublin");
        console().login(registrar);
        cnhp.view();
        testForm(cnhp);
        cnhp.fillNewNicForm(details);
        cnhp.submitAndWaitForSuccess();
        String nicHandle = db().getNicHandleByName("UTF8CNHP User Ã");
        verifyNicHandleInDb(nicHandle, "UTF8CNHP User Ã", "utf8cnhp@iẹdr.ie", "Registrar ÔĒ Limited", "1 Soḿe Street");
    }

    @Test
    public void testNewDirectAccountPage() throws SQLException {
        NewDirectAccountPage nap = new NewDirectAccountPage();
        NicHandleDetails details = new NicHandleDetails("UTF8NAP User A\u0303", "utf8nap@ie\u0323dr.ie", "1234",
                "12345", "Registrar O\u0302E\u0304 Limited", "1 Som\u0301e Street", "Ireland", "Co. Dublin");
        String password = "Passw0rd!";
        console().login(registrar);
        nap.view();
        testForm(nap);
        nap.fillNewAccountForm(details, password);
        nap.submitAndWaitForSuccess();
        NewDirectAccountConfirmationPage nacp = new NewDirectAccountConfirmationPage();
        nacp.submitAndWaitForSuccess();
        String nicHandle = db().getNicHandleByName("UTF8NAP User Ã");
        verifyNicHandleInDb(nicHandle, "UTF8NAP User Ã", "utf8nap@iẹdr.ie", "Registrar ÔĒ Limited", "1 Soḿe Street");
    }

    @Test
    public void testRegistrationPage() {
        String unnormalizedDomain = "utf8-trdp-doma\u0301in.ie";
        String normalizedDomain = "utf8-trdp-domáin.ie";
        console().login(this.registrar);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        drp.view();
        testForm(drp);
        drp.checkNewDomainName(unnormalizedDomain);
        WebElement option = wd().findElement(By.cssSelector("#domainname option[selected='selected']"));
        assertEquals(normalizedDomain, option.getAttribute("value"));
        assertEquals(normalizedDomain, option.getText());
    }

    @Test
    public void testRegistrationDetailsPage() throws SQLException {
        User user = this.registrar;
        String unnormalizedDomainName = "utf8-trdp-doma\u0301in.ie";
        String domainName = "utf8-trdp-domáin.ie";
        String domainHolder = "Holde\u0301r";
        String remarks = "Re\u0301marks";
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        DomainContactFormDetails contactsDetails = new DomainContactFormDetails(user);
        DomainRegistrationDetails details = new DomainRegistrationDetails(domainHolder, "Company", true, remarks,
                contactsDetails, dnsDetails, PredefinedPayments.VALID_CREDIT_CARD, 1, false);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        console().login(user);
        drp.startRegistration(unnormalizedDomainName);
        testForm(drdp);
        drdp.fillDomainRegistrationDetailsAndValidate(details);
        drdp.submitAndWaitForSuccess(domainName);
        verifyTicketInDb(domainName, "Holdér", "Rémarks");
    }

    @Test
    public void testViewDomainPage() throws SQLException {
        User user = this.registrar;
        String domainName = "utf8-vdp.ie";
        String domainHolder = "Holde\u0301r";
        String remarks = "Re\u0301marks";
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        ViewDomainPage vdp = new ViewDomainPage();
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        testForm(vdp);

        vdp.holderField.fill(domainHolder);
        vdp.remarksField.fill(remarks);
        vdp.nameserverForm.fillNameserverDetails(dnsDetails);
        vdp.submitAndWaitForSuccess(domainName, true);
        verifyTicketInDb(domainName, "Holdér", "Rémarks");
    }

    @Test
    public void testEditTicketPage() throws SQLException {
        User user = this.registrar;
        String domainName = "utf8-etp.ie";
        EditTicketPage etp = new EditTicketPage();
        console().login(user);
        TicketUtils.initEditTicket(domainName);
        testForm(etp);

        String domainHolder = "Holde\u0301r";
        String remarks = "Re\u0301marks";
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        etp.holderField.fill(domainHolder);
        etp.remarksField.fill(remarks);
        etp.nameserverForm.fillNameserverDetails(dnsDetails);
        etp.submitAndWaitForSuccess();

        verifyTicketInDb(domainName, "Holdér", "Rémarks");
    }

    @Test
    public void testDomainTransferPage() {
        String unnormalizedDomain = "utf8-transfer-doma\u0301in.ie";
        String normalizedDomain = "utf8-transfer-domáin.ie";
        console().login(this.registrar);
        DomainTransferPage drp = new DomainTransferPage();
        drp.view();
        testForm(drp);
        drp.checkTransferDomain(unnormalizedDomain);
        WebElement option = wd().findElement(By.cssSelector("#domain_name option[selected='selected']"));
        assertEquals(normalizedDomain, option.getAttribute("value"));
        // getText returns empty string on invisible elements, and this one is invisible due to selectric
        assertEquals(normalizedDomain, option.getAttribute("innerHTML"));
    }

    @Test
    public void testDomainTransferDetailsPage() throws SQLException {
        User user = this.registrar;
        String unnormalizedDomain = "utf8-transfer-doma\u0301in.ie";
        String normalizedDomain = "utf8-transfer-domáin.ie";
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        DomainContactFormDetails contactsDetails = new DomainContactFormDetails(user);
        DomainTransferDetails details = new DomainTransferDetails("a\u0301uthcode", contactsDetails, dnsDetails,
                PredefinedPayments.VALID_CREDIT_CARD, 1);
        DomainTransferPage drp = new DomainTransferPage();
        DomainTransferDetailsPage drdp = new DomainTransferDetailsPage(false);
        console().login(user);
        drp.startTransfer(unnormalizedDomain);
        testForm(drdp);
        drdp.fillTransferDomainDetails(details);
        drdp.submitAndWaitForSuccess(normalizedDomain);
        int ticketId = db().getTicketId(normalizedDomain);
        verifyNormalizedNameserverDetails(db().getDnsListForTicket(ticketId));
    }

    @Test
    public void testDomainDnsModificationPage() throws SQLException {
        User user = this.registrar;
        String domainName = "utf8-vdp.ie";
        NameserverFormDetails dnsDetails = createUnnormailzedNameserverDetails();
        DomainDnsPage ddp = new DomainDnsPage();
        console().login(user);
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.modifyDns(dnsDetails);
        verifyNormalizedNameserverDetails(db().getDnsListForDomain(domainName));
    }

    @Test
    public void testReauthorizeTransactionPage() throws SQLException {
        User user = this.registrar;
        String domainName = "utf8-etp.ie";
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        ReauthorizeTransactionPage rtp = new ReauthorizeTransactionPage();
        console().login(user);
        PaymentUtils.viewInvalidatedTransaction(transactionId);
        testForm(rtp);
    }

    private NameserverFormDetails createUnnormailzedNameserverDetails() {
        DomainNameServer ns1 = new DomainNameServer("ns1.doma\u0301in.ie");
        DomainNameServer ns2 = new DomainNameServer("ns2.doma\u0301in.ie");
        return new NameserverFormDetails(Arrays.asList(ns1, ns2));
    }

    private void verifyNormalizedNameserverDetails(List<DomainNameServer> nameserverList) {
        assertEquals(2, nameserverList.size());
        assertEquals("ns1.domáin.ie", nameserverList.get(0).name);
        assertEquals("ns2.domáin.ie", nameserverList.get(1).name);
    }

    private void verifyNicHandleInDb(String nicHandle, String name, String email, String companyName, String address)
            throws SQLException {
        Map<String, String> dbNicHandleMap = db().getNicHandleMapById(nicHandle);
        assertEquals(name, dbNicHandleMap.get("NH_Name"));
        assertEquals(email, dbNicHandleMap.get("NH_Email"));
        assertEquals(companyName, dbNicHandleMap.get("Co_Name"));
        assertEquals(address, dbNicHandleMap.get("NH_Address"));
    }

    private void verifyTicketInDb(String domainName, String domainHolder, String remarks) throws SQLException {
        int ticketId = db().getTicketId(domainName);
        assertEquals(domainHolder, db().getTicketDomainHolder(ticketId));
        assertEquals(remarks, db().getTicketRemark(ticketId));
        verifyNormalizedNameserverDetails(db().getDnsListForTicket(ticketId));
    }

    private void testForm(final SubmittableForm form) {
        testForm(form, new Runnable() {
            @Override
            public void run() {
                form.submit();
            }
        });
    }

    private void testForm(Form form, Runnable submit) {
        if (form.isPrimitive() && form instanceof TextField) {
            TextField field = (TextField) form;
            if (field.acceptsAnyString()) {
                testTextField(field, submit);
            }
        } else if (form instanceof PaymentForm) {
            PaymentForm pform = (PaymentForm) form;
            for (PaymentMethod method : pform.getSupportedPaymentMethods()) {
                pform.selectPaymentType(method);
                testSubForms(form, submit);
            }
        } else {
            testSubForms(form, submit);
        }
    }

    private void testSubForms(Form form, Runnable submit) {
        for (Form subForm : form.getSubForms()) {
            testForm(subForm, submit);
        }
    }

    private void testTextField(TextField field, Runnable submit) {
        WebElement fieldElement = wd().findElement(field.getSelector());
        console().sendKeys(fieldElement, Keys.END.toString() + FOUR_BYTES_CHAR);
        submit.run();
        assertTrue("No error detected in field: " + field.getSelector(), field.hasErrorMessage());
        try {
            console().waitForTextPresentInElement("Forbidden UTF-8 character", field.getErrorMessageElement());
        } catch (AssertionError e) {
            throw new AssertionError(field.getSelector().toString(), e);
        }
        // If the page was reloaded after submitting, we have to fetch the element again.
        fieldElement = wd().findElement(field.getSelector());
        console().sendKeys(fieldElement, Keys.END.toString() + Keys.BACK_SPACE.toString());
        if ("password".equals(fieldElement.getAttribute("type"))) {
            // When filled with four bytes character, password fields will display as if they have two characters (high
            // and low surrogate). We have to erase both of them.
            console().sendKeys(fieldElement, Keys.END.toString() + Keys.BACK_SPACE.toString());
        }
    }

}
