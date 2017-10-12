package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.console.LoginPage;
import com.iedr.bpr.tests.pages.console.ResetPasswordPage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.PasswordUtils;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC080 extends SeleniumTest {

    public UC080(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc080_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc080_data.sql";
    }

    @Test
    public void test_uc080_sc01() throws SQLException {
        User user = new User("UC080AA-IEDR", "Passw0rd!", true, "uc080_aa@iedr.ie");
        String newPassword = "NewPassw0rd!";
        String url = requestResetPassword(user);
        emails.add(emailSummaryGenerator.getNhPasswordChangedEmail(user));
        ResetPasswordPage rpp = new ResetPasswordPage(url);
        rpp.view();
        rpp.fillResetPasswordForm(newPassword);
        rpp.submitAndWaitForSuccess();
        checkAndResetEmails(emails);

        // Login with new credentials.
        user.password = newPassword;
        console().login(user);
        assertFalse(wd().getCurrentUrl().equals(console().url.login));
    }

    @Test
    public void test_uc080_sc02() throws NumberFormatException, SQLException {
        User user = new User("UC080AA-IEDR", "Passw0rd!", true, "uc080_aa@iedr.ie");
        String newPassword = "NewPassw0rd!";
        DateTime now = new DateTime();
        String url = requestResetPassword(user);
        emails.add(emailSummaryGenerator.getNhPasswordChangedEmail(user));
        invalidateToken(user, now);
        ResetPasswordPage rpp = new ResetPasswordPage(url);
        rpp.view();
        rpp.fillResetPasswordForm(newPassword);
        rpp.submit();
        String message = "Token not valid, please request a new one.";
        console().waitForTextPresentOnPage(message);

        // Verify that user cannot log in with new credentials.
        LoginPage lp = new LoginPage();
        lp.view();
        lp.fillForm(user.login, newPassword);
        lp.submit();

        console().waitForElementValidationFailed(wd().findElement(By.id("LoginForm_password")), 10);
        String errorMessage = wd().findElement(By.id("LoginForm_password_em_")).getText();
        assertEquals("Incorrect Account Number (Nic-Handle) or password", errorMessage);
        assertTrue(wd().getCurrentUrl().equals(console().url.login));
    }

    private String requestResetPassword(User user) throws SQLException {
        emails.add(emailSummaryGenerator.getNhPasswordResetEmail(user));
        PasswordUtils.requestPasswordReset(user);
        console().waitForTextPresentOnPage("Thank you for your request.");
        Set<ActualEmailSummary> actualEmails = checkAndResetEmails(emails);
        return PasswordUtils.getResetPasswordUrl(actualEmails);
    }

    private void invalidateToken(User user, DateTime now) throws NumberFormatException, SQLException {
        DateTime expiredDate = now.minusHours(Integer.parseInt(db().getAppConfigValue(
                "password_reset_token_expiry_period")));
        db().setPasswordResetTokenTimestamp(user.login, expiredDate.toDate());
    }

}
