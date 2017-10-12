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

public class RequestPasswordChangePage implements SubmittableForm {

    public TextField nicHandleField;

    public RequestPasswordChangePage() {
        nicHandleField = new TextField(console(), By.id("ResetPasswordForm_username"), ErrorMessageSelector.BELOW, true);
    }

    public void view() {
        wd().get(console().url.requestPasswordChange);
    }

    public void fill(String nicHandle) {
        nicHandleField.fill(nicHandle);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(nicHandleField);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    public void submitAndWaitForSuccess() {
        console().clickAndWaitForPageToLoad(By.name("yt0"));
    }

}
