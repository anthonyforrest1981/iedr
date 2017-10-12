package com.iedr.bpr.tests.gui;

import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.utils.User;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class CrsWebGui extends Gui {

    String baseUrl;

    public CrsWebGui(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String getTicketAdminStatus(WebElement tr) {
        return getTicketColumn(tr, "5");
    }

    private String getTicketTechStatus(WebElement tr) {
        return getTicketColumn(tr, "6");
    }

    private String getTicketFinancialStatus(WebElement tr) {
        return getTicketColumn(tr, "7");
    }

    public String getTicketColumn(WebElement tr, String columnNumber) {
        return tr.findElement(By.cssSelector(String.format("td:nth-child(%s)", columnNumber))).getText();
    }

    private WebElement getTicketRow(String domainName) {
        String trXPath = "//table[@id='ticketRow']//td[text()='" + domainName + "']/..";
        return wd().findElement(By.xpath(trXPath));
    }

    public void viewNicHandle(String nicHandle) {
        view(SiteId.NicHandlesSearch);
        By by = By.xpath(String.format("//table[@id='nicHandleRow']//td[.='%s']/..//img[@title='View']", nicHandle));
        if (!isElementPresentInstantaneously(by)) {
            fillInput("nic-handles-search_searchCriteria_nicHandleId", nicHandle);
            clickElement(By.id("nic-handles-search_0"));
        }
        clickAndWaitForPageToLoad(by);
    }

    public void login(User user) {
        wd().get(baseUrl);
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().startsWith(baseUrl);
            }
        });
        if (wd().getCurrentUrl().equals(baseUrl + "log-input.action")) {
            fillInput("log-in_username", user.login);
            fillInput("log-in_password", user.password);
            clickElement(By.id("log-in_0"));
        } else {
            String loginInfo = wd().findElement(By.className("loginInfo")).getText().replaceAll("\\s+", " ");
            String expectedPrefix = String.format("Logged in as: %s", user.login);
            if (!loginInfo.startsWith(expectedPrefix)) {
                logout();
                login(user);
            }
        }
    }

    public void logout() {
        clickAndWaitForPageToLoad(By.linkText("Logout"));
    }

    public enum SiteId {
        TicketsSearch("Tickets Search"),
        TicketsHistory("Tickets History Search"),
        TicketsReports("Hostmaster Usage"),

        DomainsSearch("Domains Search"),
        DomainsHistory("Historical Domains Search"),
        DomainsExportAuthcodes("Export Authcodes"),
        LockingService("Domains With Locking Service"),

        EmailsView("Search email templates"),
        EmailsGroups("View email groups"),

        NicHandlesSearch("Nic Handles Search"),
        NicHandlesCreate("Nic Handle Create"),

        DocumentsSearch("Search Documents"),
        DocumentsNew("New Documents"),

        AccountsSearch("Search Accounts"),
        AccountsCreate("Create Account"),

        DsmForceEvent("Force DSM Event"),
        DsmForceState("Force DSM State"),

        VatView("View vat rate"),
        VatCreate("Create vat rate"),

        TaskView("View task"),
        TaskCreate("Create task"),
        TaskJob("View Job"),
        TaskJobHist("View Job Hitory"),

        ProductPricingView("View product price"),
        ProductPricingCreate("Create product price"),

        CrsConfigurationView("View application configuration"),

        BulkTransferCreate("New bulk transfer"),
        BulkTransferView("View bulk transfers"),

        ReportDeposit("View deposit actual/available balance"),
        ReportDoa("View DOA"),
        ReportInvoices("View Invoices"),
        ReportRevenueAssurance("View Revenue Assurance"),
        ReportBankReconciliation("View Receipts: Bank Reconciliation"),
        ReportNrpDomains("View NRP Domains"),
        ReportCharityDomains("View Charity Domains"),
        ReportTotalDomains("View Total Domains per Registrar Report"),
        ReportRegistrations("View Registrations Per Month/Year for Registrar/All Registrars"),
        ReportPerClass("Domains Per Class/category"),
        ReportDeletedDomains("Deleted Domains"),
        ReportAutorenews("Autorenews"),
        ProlongLockingService("Prolong Locking Service By Rolling Renewal Date");
        ;

        private final String title;

        private SiteId(String s) {
            title = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : title.equals(otherName);
        }

        public String toString() {
            return title;
        }
    }

    public void view(SiteId siteId) {
        if (!wd().getCurrentUrl().startsWith(baseUrl))
            wd().get(baseUrl);
        clickAndWaitForPageToLoad(By.xpath(String.format("//a[@title='%s']", siteId.title)));
    }

    public void checkTicketStatus(String domainName, String admin, String tech, String financial) {
        WebElement tr = getTicketRow(domainName);
        assertEquals(admin, getTicketAdminStatus(tr));
        assertEquals(tech, getTicketTechStatus(tr));
        assertEquals(financial, getTicketFinancialStatus(tr));
    }

}
