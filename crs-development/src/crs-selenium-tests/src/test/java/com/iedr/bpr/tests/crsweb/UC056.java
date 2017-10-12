package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.crsweb.CreateNicHandlePage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.EmailAddressUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class UC056 extends SeleniumTest {

    public UC056(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc056_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    public void test_uc056_sc01() throws SQLException, JSchException, IOException {
        String email = "uc056@iedr.ie";
        NicHandleDetails details = new NicHandleDetails("UC056 test user", email);
        String nh = createNicHandle(details);
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(new User(nh, null, false, email)));
        changeAccessLevel();
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc056_nosc01() throws SQLException, JSchException, IOException {
        // UC#056: Create New Direct Account - Email validation (crsweb)
        crsweb().login(this.internal);
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        NicHandleDetails details = new NicHandleDetails("UC056 test user", "");
        for (String email : EmailAddressUtils.getInvalidEmailAddressList()) {
            cnhp.view();
            details.setEmail(email);
            cnhp.fillNicHandleDetails(details);
            crsweb().waitForTextPresentOnPage("E-mail is not valid");
        }
    }

    private String createNicHandle(NicHandleDetails details) throws SQLException {
        crsweb().login(this.internal);
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        cnhp.view();
        cnhp.fillNicHandleDetails(details);
        crsweb().waitForTextPresentOnPage("Nic Handle Id");
        By nhLabel = By.xpath("//div[contains(@class, 'ctrl-label') " + "and contains(text(), 'Nic Handle Id')]"
                + "//following::div[contains(@class, 'ctrl-field')]");
        String nh = wd().findElement(nhLabel).getText();
        return nh;
    }

    private void changeAccessLevel() {
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-access-level_input"));
        crsweb().clickElement(By.id("nic-handle-access-level_nic-handle-access-editGroups-edit"));
        crsweb().selectElement(By.id("nic-handle-access-editGroups_wrapper_permissionGroupsWrapper_Direct"));
        crsweb().clickElement(By.id("nic-handle-access-editGroups__save"));
        crsweb().waitForElementPresent(By.id("nic-handle-access-level_nic-handle-access-editGroups-edit"));
    }

}
