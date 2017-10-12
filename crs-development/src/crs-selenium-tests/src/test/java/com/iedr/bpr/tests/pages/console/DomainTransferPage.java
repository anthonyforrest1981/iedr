package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DomainTransferPage implements SubmittableForm {
    public TextField domainNameField = new TextField(console(), By.id("DomainsTransferRegNew_domain_name"),
            ErrorMessageSelector.BELOW, true);

    public void startTransfer(final String domainName) {
        checkTransferDomain(domainName);
        console().waitForElementPresent(By.id("RequestTransferDetailsForm"));
    }

    public void checkTransferDomain(final String domainName) {
        view();
        domainNameField.fill(domainName);
        submit();
    }

    public void view() {
        console().view(console().url.requestTransfer);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(domainNameField);
    }
}
