package com.iedr.bpr.tests.pages.console;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.forms.*;
import com.iedr.bpr.tests.forms.console.ConsoleDomainContactsForm;
import com.iedr.bpr.tests.forms.console.ConsoleNameserverForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ViewDomainPage implements SubmittableForm {

    private static String operationPrefix = "ViewDomainModel_domain";

    public DomainContactsForm contactsForm;
    public NameserverForm nameserverForm;
    public TextField holderField;
    public SelectionField holderClassField;
    public SelectionField holderCategoryField;
    public SelectionField renewalModeField;
    public TextField remarksField;

    public static By modificationButton = By.cssSelector("input[id='mod']");
    public static By enterToNrpButton = By.id("enter_to_nrp");
    public static By removeFromNrpButton = By.id("remove_from_nrp");

    public ViewDomainPage() {
        this.contactsForm = new ConsoleDomainContactsForm(operationPrefix + "_adminContacts_0_nicHandle",
                operationPrefix + "_adminContacts_1_nicHandle", operationPrefix + "_techContacts_nicHandle",
                "Admin Contact 1", "Admin Contact 2", "Technical Contact");
        this.nameserverForm = new ConsoleNameserverForm(true);
        this.holderField = new TextField(console(), By.id(operationPrefix + "_holder"), ErrorMessageSelector.IN_TABLE,
                true);
        this.holderClassField = new SelectionField(console(), By.id(operationPrefix + "_holderClass"),
                ErrorMessageSelector.IN_TABLE);
        this.holderCategoryField = new SelectionField(console(), By.id(operationPrefix + "_holderCategory"),
                ErrorMessageSelector.IN_TABLE);
        this.renewalModeField = new SelectionField(console(), By.id(operationPrefix + "_dsmState_renewalMode"),
                ErrorMessageSelector.IN_TABLE);
        this.remarksField = new TextField(console(), By.id(operationPrefix + "_remark"), ErrorMessageSelector.IN_TABLE,
                true);
    }

    public void initModification() {
        WebElement button = wd().findElement(modificationButton);
        console().clickElement(button);
        new WebDriverWait(wd(), 10).until(ExpectedConditions.invisibilityOfAllElements(Arrays.asList(button)));
    }

    public void editTechContact() {
        console().clickElement(By.id("nicWidgetEdt_domain_techContacts_nicHandle"));
        if (console().isAlertPresent()) {
            wd().switchTo().alert().dismiss();
        }
    }

    public void addNewTechContact() throws SQLException {
        console().clickElement(By.id("nicWidgetNew_domain_techContacts_nicHandle"));
    }

    public void enterToNrp() {
        console().clickElement(enterToNrpButton);
    }

    public void removeFromNrp() {
        console().clickElement(removeFromNrpButton);
    }

    @Override
    public void submit() {
        console().clickAndWaitForPageToLoad(By.id("submitButton"));
    }

    public static void waitForConfirmation(String domainName, boolean ticket) {
        String message;
        if (ticket) {
            message = String.format("Your modification request for %s has been submitted and will be processed "
                    + "by our Registrations team shortly.", domainName);
        } else {
            message = "Modify domain is successful.";
        }
        console().waitForTextPresentOnPage(message, 60);
    }

    public void submitAndWaitForSuccess(String domainName, boolean ticket) {
        submit();
        waitForConfirmation(domainName, ticket);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(contactsForm, nameserverForm, holderField, holderClassField, holderCategoryField,
                renewalModeField, remarksField);
    }
}
