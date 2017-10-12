package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.*;
import com.iedr.bpr.tests.forms.console.ConsoleDomainContactsForm;
import com.iedr.bpr.tests.forms.console.ConsoleNameserverForm;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class EditTicketPage implements SubmittableForm {

    private static String operationPrefix = "EditTicketModel";

    public DomainContactsForm contactsForm;
    public NameserverForm nameserverForm;
    public TextField holderField;
    public TextField remarksField;
    public SelectionField categoryField;

    public EditTicketPage() {
        this.contactsForm = new ConsoleDomainContactsForm(operationPrefix + "_adminContact_0", operationPrefix
                + "_adminContact_1", operationPrefix + "_techContact", "Admin Contact 1", "Admin Contact 2",
                "Tech Contact");
        this.nameserverForm = new ConsoleNameserverForm(true);
        this.holderField = new TextField(console(), By.id(operationPrefix + "_domainHolder"),
                ErrorMessageSelector.IN_TABLE, true);
        this.remarksField = new TextField(console(), By.id(operationPrefix + "_requestersRemark"),
                ErrorMessageSelector.IN_TABLE, true);
        this.categoryField = new SelectionField(console(), By.id(operationPrefix + "_applicant"),
                ErrorMessageSelector.IN_TABLE);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt9"));
    }

    public void submitAndWaitForSuccess() {
        console().triggerFormValidation();
        submit();
        console().waitForTextPresentOnPage("Ticket modified successfully.");
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(contactsForm, nameserverForm, holderField, remarksField, categoryField);
    }

    public void findAndFillAdminContact(User contact) {
        console().clickElement(By.id("nicWidgetFnd_adminContact_0"));
        console().clickElement(By.id(contact.login));
    }
}
