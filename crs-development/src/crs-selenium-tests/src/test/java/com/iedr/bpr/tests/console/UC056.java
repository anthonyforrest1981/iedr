package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.console.NewDirectAccountPage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;
import com.iedr.bpr.tests.utils.console.PasswordUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.wd;

public class UC056 extends SeleniumTest {

    public UC056(Browser browser) {
        super(browser);
    }

    String nh = null;

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc056_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    public void test_uc056_sc01() throws SQLException, JSchException, IOException {
        String emailAddress = "uc056@iedr.ir";
        AccountCreation creation = new AccountCreation();
        test_create_new_account(emailAddress, "Passw0rd!", creation);
    }

    @Test
    public void test_uc056_qa15631to15634_1() throws SQLException, JSchException, IOException {
        String emailAddress = "uc056@iedr.ir";
        AccountCreation creation = new AccountCreation("NewPassword_new_password",
                "Password is too short (minimum is 8 characters).");
        test_create_new_account(emailAddress, "T_3t", creation);
    }

    @Test
    public void test_uc056_qa15631to15634_2() throws SQLException, JSchException, IOException {
        String emailAddress = "uc056@iedr.ir";
        AccountCreation creation = new AccountCreation();
        test_create_new_account(emailAddress, "Yummy_89", creation);
    }

    @Test
    public void test_uc056_nosc01() {
        // UC#056: Create New Direct Account - Email validation (console)
        NewDirectAccountPage nap = new NewDirectAccountPage();
        nap.view();
        NicHandleUtils.verifyEmail(nap.nicHandleForm);
    }

    @Test
    public void test_uc056_nosc02() {
        // UC#056: Create New Direct Account - Repeat Password field
        NewDirectAccountPage nap = new NewDirectAccountPage();
        nap.view();
        PasswordUtils.verifyMatchingPasswordsValidation(nap.passwordForm);
    }

    private void test_create_new_account(String emailAddress, String password, AccountCreation creation)
            throws SQLException, JSchException, IOException {
        String nhName = "UC056 test user";
        if (creation.success) {
            nh = NicHandleUtils.createNewAccount(nhName, emailAddress, password);
            User user = new User(nh, password, false, emailAddress);
            emails.add(emailSummaryGenerator.getNhRegistrationEmail(user));
            checkAndResetEmails(emails);
        } else {
            NicHandleDetails details = new NicHandleDetails(nhName, emailAddress);
            NewDirectAccountPage nap = new NewDirectAccountPage();
            nap.fillNewAccountForm(details, password);
            nap.submit();
            new WebDriverWait(wd(), 10).until(ExpectedConditions.textToBePresentInElementLocated(
                    By.id(creation.fieldName + "_em_"), creation.errorMessage));
        }
    }

    private class AccountCreation {
        public boolean success;
        public String fieldName = null;
        public String errorMessage = null;

        public AccountCreation() {
            this.success = true;
        }

        public AccountCreation(String fieldName, String errorMessage) {
            this.success = false;
            this.fieldName = fieldName;
            this.errorMessage = errorMessage;
        }
    }
}
