package com.iedr.bpr.tests.pages.console;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.console.PasswordForm;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class ResetPasswordPage implements SubmittableForm {

    private PasswordForm passwordForm;

    private String url;

    public ResetPasswordPage(String url) {
        String operationPrefix = "ResetPasswordForm";
        passwordForm = new PasswordForm(operationPrefix, ErrorMessageSelector.BELOW);
        this.url = url;
    }

    public void view() {
        console().view(url);
    }

    public void fillResetPasswordForm(String newPassword) {
        passwordForm.fillPassword(newPassword);
        console().triggerFormValidation();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Collections.<Form>singletonList(passwordForm);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForTextPresentOnPage("Password changed");
    }

}
