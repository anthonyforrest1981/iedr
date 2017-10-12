package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.fail;

public class LoginPage implements SubmittableForm {

    public TextField loginField;
    public TextField passwordField;

    public LoginPage() {
        loginField = new TextField(console(), By.id("LoginForm_username"), ErrorMessageSelector.BELOW, true);
        passwordField = new TextField(console(), By.id("LoginForm_password"), ErrorMessageSelector.BELOW, true);
    }

    public void view() {
        wd().get(console().url.login);
    }

    public void login(User user) {
        login(user.login, user.password);
    }

    public void login(String login, String password) {
        if (console().loggedInAs(login)) {
            return;
        }
        view();
        fillForm(login, password);
        submitAndWaitForSuccess();
        if (!wasLoginSuccessful()) {
            fail("Logging in unsuccessful");
        }
    }

    public void fillForm(String login, String password) {
        loginField.fill(login);
        passwordField.fill(password);
    }

    public boolean wasLoginSuccessful() {
        try {
            console().waitForCurrentUrlEquals(console().url.index);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public void submit() {
        console().clickElement(getSubmitButton());
    }

    public void submitAndWaitForSuccess() {
        console().clickAndWaitForPageToLoad(getSubmitButton());
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(loginField, passwordField);
    }

    private By getSubmitButton() {
        return By.name("yt0");
    }
}
