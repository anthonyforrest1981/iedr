package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.forms.CheckboxField;
import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.console.NicHandleForm;
import com.iedr.bpr.tests.forms.console.PasswordForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class NewDirectAccountPage implements SubmittableForm {

    private final String operationPrefix = "Nichandle_Details";
    private final String passwordOperationPrefix = "NewPassword";

    public NicHandleForm nicHandleForm;
    public PasswordForm passwordForm;
    public CheckboxField useTfaField;

    public NewDirectAccountPage() {
        nicHandleForm = new NicHandleForm(operationPrefix, false);
        passwordForm = new PasswordForm(passwordOperationPrefix, ErrorMessageSelector.IN_TABLE);
        useTfaField = new CheckboxField(console(), By.id(passwordOperationPrefix + "_useTwoFactorAuthentication"),
                ErrorMessageSelector.IN_TABLE);
    }

    public void view() {
        console().view(console().url.newDirectAccount);
    }

    public void fillNewAccountForm(NicHandleDetails details, String password) {
        view();
        nicHandleForm.fillNicHandleForm(details);
        passwordForm.fillPassword(password);
        useTfaField.uncheck();
        console().triggerFormValidation();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(nicHandleForm, passwordForm, useTfaField);
    }

    @Override
    public void submit() {
        console().clickElement(By.cssSelector("input[name='action'][value='Create']"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        new WebDriverWait(wd(), 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id(operationPrefix
                + "_name")));
    }
}
