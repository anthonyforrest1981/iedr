package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;

public class UC051 extends SeleniumTest {

    public UC051(Browser browser) {
        super(browser);
    }

    boolean accountModified = false;
    String initialVatCategory = null;
    int lastAccountId;
    OutputFiles outputFiles;

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc051_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc051_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initialVatCategory = db().getCountryVatCategory("Afghanistan");
        db().setCountryVatCategory("Afghanistan", "B");
        lastAccountId = db().getLastAccountId();
        outputFiles = new OutputFiles(ssh().crsws);
    }

    @Override
    public void tearDown() throws Exception {
        db().setCountryVatCategory("Afghanistan", initialVatCategory);
        // Remove created XML file.
        try {
            if (accountModified) {
                outputFiles.clearAccountXmls();
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc051_sc01() throws SQLException, JSchException, IOException {
        final User user = new User("UC051AA-IEDR", "Passw0rd!", false, "uc051_aa@iedr.ie");
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        Modification modification = new Modification() {
            public void modify() {
                console().fillInput("Nichandle_Details_phones", "123123123");
                console().fillInput("Nichandle_Details_faxes", "123123123");
                console().fillInput("Nichandle_Details_address", "1 The Road, Some Street Modified");
                console().selectOptionByText(By.id("Nichandle_Details_countyId"), "Co. Carlow");
            }

            public void after() throws SQLException, IOException, JSchException {
                console().waitForElementPresent(By.id("nic_updated_message"));
                console().waitForTextPresentOnPage("Account saved successfully");
                checkAndResetEmails(emails);
                outputFiles.checkAccountXml(lastAccountId, user.login);
                accountModified = true;
            }
        };
        test_modify_account(user, modification);
    }

    @Test
    public void test_uc051_sc02() throws SQLException, JSchException, IOException {
        User user = new User("UC051AA-IEDR", "Passw0rd!", false, "uc051_aa@iedr.ie");
        Modification modification = new Modification() {
            public void modify() {
                console().fillInput("Nichandle_Details_phones", "123123123");
                console().fillInput("Nichandle_Details_faxes", "123123123");
                console().fillInput("Nichandle_Details_address", "1 The Road, Some Street Modified");
                console().selectOptionByText(By.id("Nichandle_Details_countryId"), "Afghanistan");
                console().selectOptionByText(By.id("Nichandle_Details_countyId"), "N/A");
            }

            public void after() {
                String errorMessage = "Error. Country modification is not allowed since it changes vat category.";
                console().waitForTextPresentOnPage(errorMessage);
            }
        };
        test_modify_account(user, modification);
    }

    private void test_modify_account(User user, Modification modification)
            throws SQLException, JSchException, IOException {
        console().login(user);
        console().viewProfile(user.login);
        console().clickAndWaitForPageToLoad(By.linkText("Edit Account"));
        modification.modify();
        console().triggerFormValidation();
        console().clickAndWaitForPageToLoad(By.name("yt0"));
        modification.after();
    }

    private interface Modification {
        public void modify();

        public void after() throws SQLException, IOException, JSchException;
    }
}
