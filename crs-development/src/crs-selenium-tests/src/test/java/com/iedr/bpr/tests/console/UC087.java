package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.*;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.TicketUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.crsweb.ToolTipsUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

@IgnoredBrowsers({SeleniumTest.Browser.Edge})
public class UC087 extends SeleniumTest {

    public UC087(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc087_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc087_data.sql";
    }

    public ExpectedEmailSummary getRegistrationEmail(User user, String domainName) throws SQLException {
        return emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName);
    }

    @Test
    public void test_uc087_sc01() throws SQLException {
        User user = this.registrar;
        String domainName = "uc087-áb.ie";
        int paymentPeriod = 1;
        console().login(user);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        db().setAppConfigStringValue("allow_idn_domains", "0");
        drp.checkNewDomainName(domainName);
        console().waitForTextPresentOnPage("uc087-áb.ie is not a valid IEDR domain address");
        db().setAppConfigStringValue("allow_idn_domains", "1");
        registerDomain(user, domainName, paymentPeriod);
    }

    @Test
    public void test_uc087_sc02() throws SQLException {
        db().setAppConfigStringValue("allow_idn_domains", "1");
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        User userInt = this.internal;
        String domainName = "uc087-áa.ie";
        int paymentPeriod = 1;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        String authcode = db().getAuthcodeForDomain("uc087-áa.ie");
        DomainTransferDetails details =
                new DomainTransferDetails(domainName, gaining, authcode, paymentDetails, paymentPeriod);

        emails.add(emailSummaryGenerator.getBillingTransferRequestEmail(gaining, losing, losing));
        emails.add(emailSummaryGenerator.getBillingTransferAcceptedEmail(gaining, domainName));
        emails.add(emailSummaryGenerator.getBillingTransferDnsEmail(gaining, domainName));
        emails.add(emailSummaryGenerator.getBillingTransferPaymentEmail(gaining, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getBillingTransferCompletedEmail(gaining, losing, domainName));

        console().login(gaining);
        DomainTransferUtils.transferDomain(domainName, gaining, details);
        ViewTicketPage ttp = new ViewTicketPage(userInt);
        ttp.triplePassTicketAccept(domainName);

        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc087_sc03() throws SQLException {
        User user = this.registrar;
        User userInt = this.internal;
        String domainName = "uc087-áb.ie";
        int paymentPeriod = 1;
        console().login(user);
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        DomainRegistrationDetails details =
                new DomainRegistrationDetails(user, domainName, paymentDetails, paymentPeriod, true);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        drp.checkNewDomainName(domainName);
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        drdp.fillDomainRegistrationDetails(details);
        drdp.verifyDNS();
        String closeText = wd().findElement(By.cssSelector(".flash-notice > a")).getText();
        assertEquals(closeText, "Close");
        String dnsMessage = wd().findElement(By.cssSelector(".flash-notice")).getText().replace(closeText, "").trim();
        assertEquals(dnsMessage, "DNS Check passed");
        drdp.submit();
        drdp.waitForConfirmation(domainName);
        ViewTicketPage ttp = new ViewTicketPage(userInt);
        ttp.triplePassTicketAccept(domainName);

    }

    @Test
    public void test_uc087_sc04() throws SQLException {
        User user = new User("UC087-IEDR", "Passw0rd!", true, "uc087_aa@íedr.ie");
        console().login(user);
        String domainName = "uc087-aa.ie";
        int paymentPeriod = 1;
        registerDomain(user, domainName, paymentPeriod);
        user.email = "uc087_aa@xn--edr-qma.ie";
        emails.add(getRegistrationEmail(user, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc087_sc05() throws SQLException {
        //view domain
        User user = this.registrar;
        String domainName = "uc087-ác.ie";
        String domainPunyCode = "xn--uc087-c-mwa.ie";
        String ticketName = "uc087-ád.ie";
        String TicketPunyCode = "xn--uc087-d-mwa.ie";
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        openTooltipAndCompareContent(domainPunyCode);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.initModification();
        openTooltipAndCompareContent(domainPunyCode);
        TicketUtils.viewTicket(ticketName);
        openTooltipAndCompareContent(TicketPunyCode);
        TicketUtils.initEditTicket(ticketName);
        openTooltipAndCompareContent(TicketPunyCode);
    }

    @Test
    public void test_uc087_sc06() throws SQLException {
        User user = this.registrar;
        String nonexistentDomain = "uc087-af.ie";
        String notOwnDomain = "uc087-ah.ie";
        String ownDomain = "uc087-ag.ie";
        String dnsValue = "ns1.uc087-ág.ie";
        String dnsPunycode = "ns1.xn--uc087-g-mwa.ie";
        String ticketDnsPunycode = "ns1.xn--bc1-dla.ie";
        int paymentPeriod = 1;

        console().login(user);
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        DomainRegistrationPage drp = new DomainRegistrationPage();
        drp.checkNewDomainName(nonexistentDomain);
        DomainRegistrationDetails details =
                new DomainRegistrationDetails(user, "uc087-áf.ie", paymentDetails, paymentPeriod, true);
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        drdp.fillDomainRegistrationDetails(details);
        openTooltipAndCompareContent("ns1.xn--uc087-f-mwa.ie");
        console().clickElement(By.name("yt8"));

        DomainTransferPage dtp = new DomainTransferPage();
        dtp.checkTransferDomain(notOwnDomain);
        console().fillInput(By.id("ns_0"), dnsValue);
        openTooltipAndCompareContent(dnsPunycode);

        ViewDomainUtils.viewDomain(ownDomain, ContactType.BILL);
        openTooltipAndCompareContent(dnsPunycode);

        TicketUtils.viewTicket(ownDomain);
        openTooltipAndCompareContent(ticketDnsPunycode);

        TicketUtils.initEditTicket(ownDomain);
        openTooltipAndCompareContent(ticketDnsPunycode);

        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(ownDomain);
        console().fillInput(By.id("ns_0"), dnsValue);
        openTooltipAndCompareContent(dnsPunycode);
    }

    @Test
    public void test_uc087_sc07() {
        User user = new User("UC087-IEDR", "Passw0rd!", true, "uc087_aa@íedr.ie");
        String domainName = "uc087-ah.ie";
        String emailPunyCode = "uc087_aa@xn--edr-qma.ie";
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);

        openTooltipAndCompareContent(emailPunyCode);

        console().viewProfile(user.login);
        openTooltipAndCompareContent(emailPunyCode);

        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.view(user);
        openTooltipAndCompareContent(emailPunyCode);

        NewDirectAccountPage ndap = new NewDirectAccountPage();
        ndap.view();
        console().fillInput(By.id("Nichandle_Details" + "_email"), "uc087_aa@íedr.ie");
        openTooltipAndCompareContent(emailPunyCode);
    }

    public void registerDomain(User user, String domainName, int paymentPeriod) throws SQLException {
        console().login(user);
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        DomainRegistrationDetails details =
                new DomainRegistrationDetails(user, domainName, paymentDetails, paymentPeriod, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private void openTooltipAndCompareContent(String expectedPunyCode) {
        WebElement toolBox = wd().findElement(By.xpath("//img[@src='images/tooltipicon.png']"));
        ToolTipsUtils.openToolTips(toolBox);
        String punyCode = wd().findElement(By.className("idn-tooltip-punycode")).getText();
        assertEquals(expectedPunyCode, punyCode);
    }
}
