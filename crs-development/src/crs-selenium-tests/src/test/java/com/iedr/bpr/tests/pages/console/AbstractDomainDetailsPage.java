package com.iedr.bpr.tests.pages.console;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.iedr.bpr.tests.forms.*;
import com.iedr.bpr.tests.forms.console.ConsoleDomainContactsForm;
import com.iedr.bpr.tests.forms.console.ConsoleNameserverForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public abstract class AbstractDomainDetailsPage implements SubmittableForm {
    public SelectionField renewalPeriodField = null;
    public DomainContactsForm contactsForm = new ConsoleDomainContactsForm(getOperationPrefix()
            + "_admin_contact_nic_1", getOperationPrefix() + "_admin_contact_nic_2", getOperationPrefix()
            + "_tech_contact", "Admin Contact 1", "Admin Contact 2", "Technical Contact");
    public NameserverForm nameserverForm = new ConsoleNameserverForm(true);
    public CheckboxField tncField = new CheckboxField(console(), By.id(getOperationPrefix() + "_accept_tnc"),
            ErrorMessageSelector.IN_TABLE);

    protected abstract String getOperationPrefix();

    protected void selectRenewalPeriod(int period) {
        String searchedOption = period + " Year";
        String optionValue = null;
        Select e = new Select(wd().findElement(renewalPeriodField.getSelector()));
        List<WebElement> options = e.getOptions();
        for (WebElement element : options) {
            if (element.getAttribute("innerHTML").startsWith(searchedOption)) {
                optionValue = element.getAttribute("value");
            }
        }
        renewalPeriodField.fill(optionValue);
    }

    @Override
    public void submit() {
        console().clickElement(wd().findElement(By.name("yt9")));
    }

    public void waitForConfirmation(String domainName) {
        console().waitForTextPresentOnPage(console().getNewTicketMessage(domainName));
    }

    public void submitAndWaitForSuccess(String domainName) {
        submit();
        waitForConfirmation(domainName);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }
}
