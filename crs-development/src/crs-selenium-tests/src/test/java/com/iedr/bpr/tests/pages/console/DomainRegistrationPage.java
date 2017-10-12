package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class DomainRegistrationPage implements SubmittableForm {

    public TextField domainNameField = new TextField(console(), By.id("Domains_RegNew_domain_names"),
            ErrorMessageSelector.BELOW, true);

    public void startRegistration(String domainName) {
        checkNewDomainName(domainName, true);
        console().waitForElementPresent(By.id("DomainDetailsForm"));
    }

    public void checkNewDomainName(String domainName) {
        checkNewDomainName(domainName, false);
    }

    public void checkNewDomainName(String domainName, boolean success) {
        view();
        domainNameField.fill(domainName);
        if (success) {
            submitAndWait();
        } else {
            submit();
        }
    }

    public void view() {
        console().view(console().url.regNewDomain);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(domainNameField);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    private void submitAndWait() {
        console().clickAndWaitForPageToLoad(By.name("yt0"));
    }
}
