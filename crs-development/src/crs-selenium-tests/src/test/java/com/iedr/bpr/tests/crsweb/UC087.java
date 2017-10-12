package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.gui.CrsWebGui;
import com.iedr.bpr.tests.pages.crsweb.AccountPage;
import com.iedr.bpr.tests.pages.crsweb.EditNicHandlePage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.crsweb.ToolTipsUtils;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC087 extends SeleniumTest {

    public UC087(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc087_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc087_data.sql";
    }

    @Test
    public void test_uc087_sc08() {
        User userInt = this.internal;
        String ticketName = "uc087-abá.ie";
        String domainName = "uc087-aaá.ie";
        String ticketPunyValue = "xn--uc087-ab-gza.ie";
        String domainPunyValue = "xn--uc087-aa-gza.ie";

        ViewTicketPage ttp = new ViewTicketPage(userInt);
        ttp.viewTicket(ticketName);
        openToolTipsBox(ticketPunyValue);

        crsweb().login(userInt);
        ttp.viewReviseAndEditTicket(ticketName);
        openToolTipsBox(ticketPunyValue);

        ViewDomainPage vdp = new ViewDomainPage();
        vdp.view(domainName);
        openToolTipsBox(domainPunyValue);
        vdp.edit(domainName);
        openToolTipsBox(domainPunyValue);

        crsweb().view(CrsWebGui.SiteId.DomainsHistory);
        crsweb().fillInput("historical-domain-search_searchCriteria_domainName", domainName);
        crsweb().clickElement(By.id("historical-domain-search_0"));
        crsweb().clickElement(By.cssSelector("img[title='View']"));
        openToolTipsBox(domainPunyValue);
    }

    @Test
    public void test_uc087_sc09() {
        User userInt = this.internal;
        String domainName = "uc087-ab.ie";

        String dnsPuny = "ns1.xn--dns-gla.ie";

        crsweb().login(userInt);
        crsweb().view(CrsWebGui.SiteId.TicketsSearch);
        crsweb().fillInput("tickets-search_searchCriteria_domainName", domainName);
        crsweb().clickElement(By.id("tickets-search_0"));

        String trXPath = ticketRowXPath(domainName);
        crsweb().clickElement(By.xpath(trXPath + "//img[@title='View']"));
        openToolTipsBox(dnsPuny);

        ViewTicketPage ttp = new ViewTicketPage(userInt);
        ttp.viewReviseAndEditTicket(domainName);
        openToolTipsBox(dnsPuny);

        ViewDomainPage vdp = new ViewDomainPage();
        vdp.view(domainName);
        openToolTipsBox(dnsPuny);
        vdp.edit(domainName);
        openToolTipsBox(dnsPuny);

        crsweb().view(CrsWebGui.SiteId.DomainsHistory);
        crsweb().fillInput("historical-domain-search_searchCriteria_domainName", domainName);
        crsweb().clickElement(By.id("historical-domain-search_0"));
        crsweb().clickElement(By.cssSelector("img[title='View']"));
        openToolTipsBox(dnsPuny);
    }

    @Test
    public void test_uc087_sc10() {
        User userInt = this.internal;
        User user = new User("UC087-IEDR", "Passw0rd!", false, "uc087_aa@íedr.ie");
        String emailPuny = "uc087_aa@xn--edr-qma.ie";

        crsweb().login(userInt);

        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.view("UC087-IEDR");
        openToolTipsBox(emailPuny);

        crsweb().clickElement(By.id("nic-handle-edit_nic-handle-edit_save"));
        openToolTipsBox(emailPuny);

        crsweb().view(CrsWebGui.SiteId.NicHandlesCreate);
        crsweb().fillInput(By.name("nicHandleWrapper.email"), "uc087_aa@íedr.ie");
        openToolTipsBox(emailPuny);

        AccountPage dap = new AccountPage(user);
        dap.view();
        openToolTipsBox(emailPuny);

        crsweb().clickElement(By.id("account-view-view_account-edit-input"));
        openToolTipsBox(emailPuny);

        crsweb().view(CrsWebGui.SiteId.AccountsCreate);
        crsweb().clickElement(By.id("openSearchExtendedDialogbillingContact"));
        crsweb().fillInput("contacts-search-extended_searchCriteria_name", "Registrar (Vat Account)");
        String dialogXPath = "//div[contains(@id, 'contactSearchExtendedDialogbillingContact')]";
        crsweb().clickElement(By.xpath(dialogXPath + "//input[contains(@type, 'submit')]"));
        By nhBy = By.cssSelector("a[onclick*='UC087-IEDR']");
        crsweb().clickElementWithEventTriggered(nhBy);
        openToolTipsBox(emailPuny);
    }

    @Test
    public void test_uc087_sc11() throws SQLException, MessagingException, IOException {
        User userInt = this.internal;
        String domainName = "uc087-ác.ie";
        scheduler().runJob(SchedulerMonitor.SchedulerJob.AUTORENEWAL);
        scheduler().runJob(SchedulerMonitor.SchedulerJob.INVOICING);
        String invoiceNumber = db().getSettledInvoiceNumber(domainName);
        crsweb().login(userInt);
        crsweb().view(CrsWebGui.SiteId.ReportRevenueAssurance);

        crsweb().fillInput(By.id("viewExtendedReservations-search_searchCriteria_invoiceNumber"), invoiceNumber);
        crsweb().clickElement(By.id("viewExtendedReservations-search_0"));

        String bodyText = wd().findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(domainName));
    }

    @Test
    public void test_uc087_sc12() throws SQLException, MessagingException, IOException {
        User userInt = this.internal;
        String domainName = "uc087-ád.ie";
        scheduler().runJob(SchedulerMonitor.SchedulerJob.AUTORENEWAL);
        scheduler().runJob(SchedulerMonitor.SchedulerJob.INVOICING);
        String invoiceNumber = db().getSettledInvoiceNumber(domainName);
        crsweb().login(userInt);
        crsweb().view(CrsWebGui.SiteId.ReportBankReconciliation);

        crsweb().fillInput(By.id("bankReceipts-search_searchCriteria_invoiceNumberFrom"), invoiceNumber);
        crsweb().clickElement(By.id("bankReceipts-search_0"));

        String bodyText = wd().findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(domainName));
    }

    @Test
    public void test_uc087_sc14() throws SQLException, MessagingException, IOException {
        User userInt = this.internal;
        String domainName = "uc087-áf.ie";
        crsweb().login(userInt);
        crsweb().view(CrsWebGui.SiteId.ReportNrpDomains);

        crsweb().fillInput(By.id("nrpDomains-search_searchCriteria_domainName"), domainName);
        crsweb().clickElement(By.id("nrpDomains-search_0"));

        String bodyText = wd().findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(domainName));
    }

    @Test
    public void test_uc087_sc15() throws SQLException, MessagingException, IOException {
        String initialAllowIdn = db().getAppConfigValue("allow_idn_domains");
        db().setAppConfigStringValue("allow_idn_domains", "1");
        try {
            User userInt = this.internal;
            User user = registrar;
            int paymentPeriod = 1;
            String domainName = "uc087-ág.ie";

            crsweb().login(userInt);
            findAmount(user, 3);

            console().login(user);
            registerDomain(user, domainName, paymentPeriod);
            ViewTicketPage ttp = new ViewTicketPage(userInt);
            ttp.triplePassTicketAccept(domainName);

            findAmount(user, 4);
        } finally {
            db().setAppConfigStringValue("allow_idn_domains", initialAllowIdn);
        }
    }

    private void openToolTipsBox(String expectedPunyCode) {
        WebElement toolBox = wd().findElement(By.className("tooltip-icon"));
        ToolTipsUtils.openToolTips(toolBox);
        String punyCode = wd().findElement(By.className("idn-tooltip-punycode")).getText();
        assertEquals(expectedPunyCode, punyCode);
    }

    private String ticketRowXPath(String domainName) {
        return String.format("//table[@id='ticketRow']//td[text()='%s']/..", domainName);
    }

    private void registerDomain(User user, String domainName, int paymentPeriod) throws SQLException {
        console().login(user);
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        DomainRegistrationDetails details =
                new DomainRegistrationDetails(user, domainName, paymentDetails, paymentPeriod, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private void findAmount(User user, int domains) {
        crsweb().view(CrsWebGui.SiteId.ReportTotalDomains);
        int actual = 0;
        try {
            String amount =
                    wd().findElement(By.xpath("//td[contains(text(),'" + user.login + "')]/following-sibling::td[2]"))
                            .getText();
            actual = Integer.parseInt(amount);
        } catch (NoSuchElementException e) {
        }
        assertEquals(domains, actual);
    }

}
