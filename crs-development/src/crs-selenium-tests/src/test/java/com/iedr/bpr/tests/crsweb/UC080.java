package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UC080 extends SeleniumTest {

    public UC080(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc080_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc080_data.sql";
    }

    @Ignore
    @Test
    public void test_uc080_sc03() throws SQLException {
        User user = new User("UC080AB-IEDR", null);
        resetPassword(user);
        assertEquals(0, db().getNicHandleLevel(user.login));
    }

    @Test
    public void test_uc080_sc04() {
        User user = new User("UC080AA-IEDR", "Passw0rd!", true, "uc080_aa@iedr.ie");
        String newPassword = "NewPassw0rd!";
        changePassword(user, newPassword);
        user.password = newPassword;
        console().login(user);
        assertFalse(wd().getCurrentUrl().equals(console().url.login));
    }

    private void resetPassword(User user) {
        crsweb().login(this.internal);
        crsweb().viewNicHandle(user.login);
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-reset-password_input"));
        crsweb().clickElement(By.id("nic-handle-reset-password__resetPassword"));
    }

    private void changePassword(User user, String newPassword) {
        crsweb().login(this.internal);
        crsweb().viewNicHandle(user.login);
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-reset-password_input"));
        crsweb().fillInput(By.id("nic-handle-reset-password_newPassword1"), newPassword);
        crsweb().fillInput(By.id("nic-handle-reset-password_newPassword2"), newPassword);
        crsweb().clickElement(By.id("nic-handle-reset-password__saveNewPassword"));
        crsweb().waitForElementPresent(By.id("nic-handle-view_nic-handle-reset-password_input"));

    }

}
