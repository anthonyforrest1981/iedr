package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.console.AllDomainsPage;
import com.iedr.bpr.tests.pages.console.DomainRegistrationPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UC086 extends SeleniumTest {

    public UC086(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc086_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc086_data.sql";
    }

    @Test
    public void test_uc086_sc11() throws SQLException {
        User user = this.registrar;
        String domainName = "uc086-ab.ie";

        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);

        assertEquals(wd().findElements(By.cssSelector("input[type='submit'][value='Show authcode']")).size(), 0);
        assertEquals(wd().findElements(By.cssSelector("input[type='submit'][value='Send authcode by email']")).size(),
                0);
    }

    @Test
    @IgnoredBrowsers({Browser.Edge})
    public void test_uc086_sc13() throws SQLException {
        User user = this.registrar;
        String domainName = "uc086-ae.ie";
        String domainName1 = "uc086-af.ie";
        List<String> domainNames = Arrays.asList(domainName, domainName1);

        console().login(user);
        AllDomainsPage adp = new AllDomainsPage();
        adp.view();
        adp.selectDomainsFromList(domainNames, "uc086-", "gs_A");
        console().clickElement(By.id("gridaction_authcodedownload"));
        String message = "Please confirm you want to \"Download Authcodes\", skipping the following domains: \n" +
                "uc086-ae.ie (domain is locked)";
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        assertEquals(message, alertText);
    }

    @Test
    public void test_uc086_sc14() throws SQLException {
        User user = this.registrar;
        String domainName = "uc086-ac.ie";

        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);

        assertEquals(wd().findElements(By.id("enter_to_nrp")).size(), 0);
    }

    @Test
    public void test_uc086_sc17() throws SQLException {
        User user = this.registrar;
        String domainName = "uc086-ad.ie";

        console().login(user);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        drp.checkNewDomainName(domainName);

        String message = domainName + " is already registered or has a pending ticket";
        try {
            console().waitForTextPresentOnPage(message);
        } catch (TimeoutException e) {
            fail("Domain \"" + domainName + "\" should have been rejected.");
        }
    }
}
