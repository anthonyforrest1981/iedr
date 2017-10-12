package com.iedr.bpr.tests.utils.console;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.forms.console.PasswordForm;
import com.iedr.bpr.tests.pages.console.RequestPasswordChangePage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static com.iedr.bpr.tests.utils.console.TextFieldUtils.clearField;
import static com.iedr.bpr.tests.utils.console.TextFieldUtils.fillFieldWithInvalidValue;
import static com.iedr.bpr.tests.utils.console.TextFieldUtils.fillFieldWithValidValue;
import static com.iedr.bpr.tests.utils.console.TextFieldUtils.waitForErrorMessageChanged;
import static org.junit.Assert.assertTrue;

public class PasswordUtils {

    private static String VALID_PASSWORD = "Passw0rd!";
    private static String NOT_MATCHING_PASSWORDS_MESSAGE = "Repeat Password must be repeated exactly.";

    public static void requestPasswordReset(User user) {
        RequestPasswordChangePage rpcp = new RequestPasswordChangePage();
        rpcp.view();
        rpcp.fill(user.login);
        rpcp.submitAndWaitForSuccess();
    }

    public static String getResetPasswordUrl(Set<ActualEmailSummary> actualEmails) {
        ActualEmailSummary email = new ArrayList<>(actualEmails).get(0);
        Pattern p = Pattern.compile("a href=\"([^\"]+)\"");
        Matcher m = p.matcher(email.text);
        assertTrue(m.find());
        String rawUrl = m.group(1);
        return localizeUrl(rawUrl);
    }

    public static void verifyMatchingPasswordsValidation(PasswordForm passwordForm) {
        verifyNotMatchingPasswords(passwordForm, "Passw0rd??", VALID_PASSWORD, false, true);
        verifyNotMatchingPasswords(passwordForm, VALID_PASSWORD, "Passw0rd??", true, false);
        verifyMatchingPasswords(passwordForm, false);
    }

    private static String localizeUrl(String rawUrl) {
        return rawUrl.replace("https://console.iedr.ie/index.php", console().url.index);
    }

    private static void verifyNotMatchingPasswords(PasswordForm passwordForm, String password, String repeatPassword,
                boolean mainPasswordValid, boolean initial) {
        WebElement passwordField = wd().findElement(passwordForm.passwordField.getSelector());
        WebElement repeatPasswordField = wd().findElement(passwordForm.repeatPasswordField.getSelector());
        String emptyPasswordMessage = getEmptyPasswordMessage(false);
        String emptyRepeatPasswordMessage = getEmptyPasswordMessage(true);
        resetToValidState(passwordField, initial, emptyPasswordMessage);
        resetToValidState(repeatPasswordField, initial, emptyRepeatPasswordMessage);
        clearField(passwordField, emptyPasswordMessage);
        clearField(repeatPasswordField, emptyRepeatPasswordMessage);
        console().sendKeys(passwordField, password);
        if (mainPasswordValid) {
            console().triggerValidationForElementAndWait(passwordField);
        } else {
            console().triggerValidationForElement(passwordField);
            waitForErrorMessageChanged(passwordField, emptyPasswordMessage);
        }
        fillFieldWithInvalidValue(repeatPasswordField, repeatPassword, NOT_MATCHING_PASSWORDS_MESSAGE,
                emptyRepeatPasswordMessage);
    }

    private static void verifyMatchingPasswords(PasswordForm passwordForm, boolean initial) {
        WebElement passwordField = wd().findElement(passwordForm.passwordField.getSelector());
        WebElement repeatPasswordField = wd().findElement(passwordForm.repeatPasswordField.getSelector());
        resetToValidState(passwordField, initial, getEmptyPasswordMessage(false));
        resetToValidState(repeatPasswordField, initial, getEmptyPasswordMessage(true));
    }

    private static void resetToValidState(WebElement field, boolean initial, String emptyPasswordMessage) {
        if (!initial) {
            clearField(field, emptyPasswordMessage);
        }
        fillFieldWithValidValue(field, VALID_PASSWORD);
    }

    private static String getEmptyPasswordMessage(boolean repeat) {
        return String.format("%sPassword cannot be blank.", repeat ? "Repeat " : "");
    }
}
