package com.iedr.bpr.tests.pages.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.console.ConsoleNameserverForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DomainDnsModificationPage implements SubmittableForm {

    public NameserverForm nameserverForm = new ConsoleNameserverForm(false);

    public void modifyDns(NameserverFormDetails details) {
        nameserverForm.fillNameserverDetails(details);
        submitAndWaitForSuccess();
    }

    public void verify() {
        console().clickElement(By.id("ns_ver_buttton"));
        console().waitForTextPresentOnPage("DNS Check passed");
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt9"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForTextPresentOnPage("DNS Modified successfully");
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<Form>(Arrays.asList(nameserverForm));
    }
}
