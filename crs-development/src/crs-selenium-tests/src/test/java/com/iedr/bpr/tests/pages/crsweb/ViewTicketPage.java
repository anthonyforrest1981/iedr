package com.iedr.bpr.tests.pages.crsweb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.crsweb.DomainModificationDetails;
import com.iedr.bpr.tests.forms.DomainContactsForm;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.crsweb.CrsWebDomainContactsForm;
import com.iedr.bpr.tests.forms.crsweb.CrsWebTicketNameserverForm;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertTrue;

public class ViewTicketPage implements SubmittableForm {

    private User user;

    private final String submitButtonId = "ticketedit-input__forceSave";

    public DomainContactsForm contactsForm = new CrsWebDomainContactsForm("adminContact1", "adminContact2",
            "techContact");
    public NameserverForm nameserverForm = new CrsWebTicketNameserverForm();

    public ViewTicketPage(User user) {
        this.user = user;
    }

    public boolean triplePassTicketAccept(String domainName) {
        viewReviseAndEditTicket(domainName);
        acceptRevisedTicket();
        return isTicketPresent(domainName) == false;
    }

    public boolean triplePassTicketReject(String domainName, String reason) {
        viewReviseAndEditTicket(domainName);
        rejectRevisedTicket(reason);
        return isTicketPresent(domainName) == true;
    }

    public boolean isTicketPresent(String domainName) {
        if (noResults()) {
            return false;
        }
        if (isTicketVisibleOnFirstPage(domainName)) {
            return true;
        }
        if (!isPaginationPresent()) {
            return false;
        }
        crsweb().fillInput("tickets-search_searchCriteria_domainName", domainName);
        crsweb().clickAndWaitForPageToLoad(By.id("tickets-search_0"));
        // We could use isTicketPresent() recursively, but if provided domainName is not a full domain name as expected,
        // then at this point there still can be multiple pages of results and calling isTicketPresent() would lead to
        // an endless loop.
        return !noResults() && isTicketVisibleOnFirstPage(domainName);
    }

    public void viewReviseAndEditTicket(String domainName) {
        crsweb().login(user);
        crsweb().view(SiteId.TicketsSearch);
        reviseTicket(domainName);
    }

    public void viewTicket(String domainName){
        crsweb().login(user);
        crsweb().view(SiteId.TicketsSearch);
        crsweb().fillInput("tickets-search_searchCriteria_domainName", domainName);
        crsweb().clickElement(By.id("tickets-search_0"));

        String trXPath = ticketRowXPath(domainName);
        crsweb().clickElement(By.xpath(trXPath + "//img[@title='View']"));
    }


    public void edit(String domainName) {
        viewReviseAndEditTicket(domainName);
        crsweb().clickElement(By.id("ticketrevise-input_forceEdit"));
        crsweb().waitForTextPresentOnPage(
                "This ticket has been restricted from editing as the Registrar has not signed Exhibit 3 of the Registrar Agreement");
        crsweb().clickElement(By.id("ticketrevise-input__edit"));
        crsweb().waitForTextPresentOnPage("Ticket Edit");
    }

    public void fillTicketDetails(DomainModificationDetails details) {
        if (details.getDomainHolder() != null) {
            crsweb().fillInput(By.name("ticketWrapper.domainHolder.value"), details.getDomainHolder());
        }
        if (details.getDomainClass() != null) {
            crsweb().selectOptionByText(By.name("ticketWrapper.domainHolderClass.id"), details.getDomainClass());
        }
        if (details.getDomainCategory() != null) {
            String classId = wd().findElement(By.name("ticketWrapper.domainHolderClass.id")).getAttribute("value");
            String categorySelectContainerId = "categorySelect_ticketEditHolderEntities_" + classId;
            crsweb().selectOptionByText(By.cssSelector(String.format("#%s > select", categorySelectContainerId)),
                    details.getDomainCategory());
        }
        if (details.getContactDetails() != null) {
            contactsForm.fillDomainContacts(details.getContactDetails());
        }
        if (details.getDnsDetails() != null) {
            nameserverForm.fillNameserverDetails(details.getDnsDetails());
        }

        crsweb().fillInput(By.id("hostmasterMessage"), details.getRemark());
        submit();
        crsweb().waitForTextPresentOnPage("Ticket Revise");
    }

    public void generateTicketHoldUp(String domainName) throws NumberFormatException, SQLException {
        moveTicketTowardExpiration(domainName, db().getMaxExpiringTicketNotificationPeriod());
    }

    public void expireTicket(String domainName) throws NumberFormatException, SQLException {
        moveTicketTowardExpiration(domainName, 0);
    }

    private void moveTicketTowardExpiration(String domainName, int toExpirationDate)
            throws NumberFormatException, SQLException {
        int ticketExpirationPeriod = Integer.valueOf(db().getAppConfigValue("ticket_expiration_period"));
        int ticketId = db().getTicketId(domainName);
        DateTime now = new DateTime();
        DateTime notificationDate = now.minusDays(ticketExpirationPeriod - toExpirationDate);
        db().changeTicketCreatedTimestamp(ticketId, notificationDate.toDate());
    }

    private boolean noResults() {
        // This element is always present - both when the results are also present and when they're not. Fetching it and
        // comparing its text will be instantaneous in both cases.
        String resultsText = wd().findElement(By.cssSelector("#mainMenu > .group > .GC > .GCCA")).getText();
        return "Nothing found to display.".equals(resultsText.trim());
    }

    private boolean isTicketVisibleOnFirstPage(String domainName) {
        String trXPath = ticketRowXPath(domainName);
        return crsweb().isElementPresentInstantaneously(By.xpath(trXPath));
    }

    private boolean isPaginationPresent() {
        return crsweb().isElementPresentInstantaneously(By.cssSelector(".pagelinks > a"));
    }

    private void reviseTicket(String domainName) {
        String trXPath = ticketRowXPath(domainName);
        assertTrue(String.format("Ticket for domain '%s' not in the system", domainName), isTicketPresent(domainName));
        crsweb().clickElement(By.xpath(trXPath + "//img[@title='Check Out']"));
        crsweb().clickAndWaitForPageToLoad(By.xpath(trXPath + "//img[@title='Revise and Edit']"));
    }

    private String ticketRowXPath(String domainName) {
        return String.format("//table[@id='ticketRow']//td[text()='%s']/..", domainName);
    }

    private void acceptRevisedTicket() {
        setTicketStatus("accept");
    }

    private void rejectRevisedTicket(String reason) {
        crsweb().fillInput("hostmasterMessage", reason);
        crsweb().clickElement(By.id("openRejectDialog"));
        String xpath = "//select[@id='ticketrevise-input_newAdminStatus']/option[text()='" + reason + "']";
        if (!wd().findElement(By.xpath(xpath)).isSelected()) {
            crsweb().clickElement(By.xpath(xpath));
        }
        setTicketStatus("reject");
    }

    private void setTicketStatus(String method) {
        crsweb().clickElement(By.id("ticketrevise-input__" + method));
    }

    @Override
    public void submit() {
        crsweb().clickElement(By.id(submitButtonId));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<>();
    }
}
