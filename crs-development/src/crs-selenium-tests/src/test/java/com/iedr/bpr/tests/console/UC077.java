package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC077 extends SeleniumTest {

    public UC077(Browser browser) {
        super(browser);
    }

    int currentTimeout;
    boolean timeoutChanged = false;

    @Override
    protected String getResetDataScript() {
        return null;
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Override
    public void tearDown() throws Exception {
        try {
            if (timeoutChanged) {
                db().setUserSessionTimeout(currentTimeout);
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc077_sc01() throws SQLException, InterruptedException {
        User user = this.direct;
        currentTimeout = Integer.parseInt(db().getAppConfigValue("user_session_timeout_minutes"));
        timeoutChanged = true;
        db().setUserSessionTimeout(1);
        console().view(console().url.index);
        console().login(user);
        // Wait 1 minute for session to expire.
        Thread.sleep(61000);
        // Navigate to page accessible to logged in users only.
        wd().get(console().url.allDomains);
        // Verify that alert is present.
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        assertEquals("Service session timeout.", alert.getText());
        alert.accept();
        // Verify that we get redirected to login page.
        console().waitForCurrentUrlEquals(console().url.login);
    }

    @Test
    public void test_uc077_sc02_1() {
        User user = this.direct;
        test_logging_out(user, console().url.allDomains);
    }

    @Test
    public void test_uc077_sc02_2() {
        User user = this.direct;
        test_logging_out(user, console().url.regNewDomain);
    }

    @Test
    public void test_uc077_sc02_3() {
        User user = this.direct;
        test_logging_out(user, console().url.requestTransfer);
    }

    @Test
    public void test_uc077_sc02_4() {
        User user = this.direct;
        test_logging_out(user, console().url.tickets);
    }

    @Test
    public void test_uc077_sc02_5() {
        User user = this.direct;
        test_logging_out(user, console().url.index);
    }

    public void test_logging_out(User user, String page) {
        console().view(console().url.index);
        console().login(user);
        console().view(page);
        WebElement iframe = wd().findElement(By.xpath("//iframe[@src='/index.php?r=site/summaryframe']"));
        wd().switchTo().frame(iframe);
        console().clickElement(By.xpath("//a[starts-with(., 'Logout')]"));
        wd().switchTo().defaultContent();
        console().waitForCurrentUrlEquals(console().url.login);
    }

}
