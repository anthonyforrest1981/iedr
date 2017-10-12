package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.forms.*;
import com.iedr.bpr.tests.forms.console.ConsoleNameserverForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ResellerDefaultsPage implements SubmittableForm {

    private String operationPrefix = "RegistrarDetailsModel";

    public TextField techContactField;
    public NameserverForm nameserverForm;
    public TextField notificationPeriodField;
    public RadioButtonField invoiceFormatField;

    public ResellerDefaultsPage() {
        techContactField = new TextField(console(), By.id(operationPrefix + "_techContact"),
                ErrorMessageSelector.IN_TABLE, true);
        nameserverForm = new ConsoleNameserverForm(false);
        notificationPeriodField = new TextField(console(), By.id(operationPrefix + "_dnsNotificationPeriod"),
                ErrorMessageSelector.IN_TABLE, false);

        String xpath = String.format("//*[@id='%s']/..", "yt" + operationPrefix + "_emailInvoiceFormat");
        invoiceFormatField = new RadioButtonField(console(), By.xpath(xpath), ErrorMessageSelector.IN_TABLE,
                operationPrefix + "[emailInvoiceFormat]");
    }

    public void view() {
        console().viewMyAccount();
        console().clickElement(By.xpath("//a[normalize-space()='Edit Registrar Default Values']"));
    }

    public void fill(String nicHandle, NameserverFormDetails details, String notificationPeriod, String value) {
        techContactField.fill(nicHandle);
        nameserverForm.fillNameserverDetails(details);
        notificationPeriodField.fill(notificationPeriod);
        invoiceFormatField.choose(value);
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForTextPresentOnPage(
                "Modify data is successful. " + "These changes will take effect after relogin.");
    }

    @Override
    public void submit() {
        console().clickAndWaitForPageToLoad(By.id("update"));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(techContactField, nameserverForm, notificationPeriodField, invoiceFormatField);
    }
}
