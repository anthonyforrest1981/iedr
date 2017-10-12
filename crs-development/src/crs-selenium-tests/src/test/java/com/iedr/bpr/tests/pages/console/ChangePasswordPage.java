package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.forms.console.PasswordForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ChangePasswordPage implements SubmittableForm {

    private String operationPrefix = "ChangePasswordForm";
    public TextField oldPasswordField;
    public PasswordForm passwordForm;

    public ChangePasswordPage() {
        oldPasswordField = new TextField(console(), By.id(operationPrefix + "_old_password"),
                ErrorMessageSelector.BELOW, true);
        passwordForm = new PasswordForm(operationPrefix, ErrorMessageSelector.BELOW);
    }

    public void view() {
        wd().get(console().url.changePassword);
    }

    public void fill(String oldPassword, String newPasssword) {
        oldPasswordField.fill(oldPassword);
        passwordForm.fillPassword(newPasssword);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(oldPasswordField, passwordForm);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForCurrentUrlEquals(console().url.index);
    }

}
