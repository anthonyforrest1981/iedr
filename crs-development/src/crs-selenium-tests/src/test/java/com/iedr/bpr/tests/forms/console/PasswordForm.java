package com.iedr.bpr.tests.forms.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class PasswordForm implements Form {

    public TextField passwordField;
    public TextField repeatPasswordField;

    public PasswordForm(String operationPrefix, By errorMessagePlacement) {
        passwordField = new TextField(console(), By.id(operationPrefix + "_new_password"), errorMessagePlacement, true);
        repeatPasswordField = new TextField(console(), By.id(operationPrefix + "_repeat_new_password"),
                errorMessagePlacement, true);
    }

    public void fillPassword(String password) {
        passwordField.fill(password);
        repeatPasswordField.fill(password);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(passwordField, repeatPasswordField);
    }

}
