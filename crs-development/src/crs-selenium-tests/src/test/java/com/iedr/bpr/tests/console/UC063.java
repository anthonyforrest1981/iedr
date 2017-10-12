package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.console.CreateNicHandlePage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class UC063 extends SeleniumTest {

    public UC063(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc063_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc063_data.sql";
    }

    @Test
    public void test_uc063_sc01() throws SQLException {
        User user = this.registrar;
        String emailAddress = "uc063@iedr.ir";
        test_register_nic(user, emailAddress);
    }

    @Test
    public void test_uc063_nosc01() {
        // UC#063: Register Nic - Register Nic via Console - XSS
        User user = this.registrar;
        String contactNhName = "UC063 new nic";
        String emailAddress = "uc063@iedr.ir";
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        NicHandleDetails details = new NicHandleDetails(contactNhName, emailAddress);
        details.setCountry("Ireland");
        details.setCounty("Co. Carlow");
        console().login(user);
        cnhp.view();
        cnhp.fillNewNicForm(details);
        console().triggerFormValidation();
        String script = "<script>alert(document.cookie);</script>";
        switchOption("Co. Carlow", script);
        cnhp.submit();
        console().waitForTextPresentOnPage("Invalid county for chosen country.");
        switchOption("Ireland", script);
        cnhp.submit();
        console().waitForTextPresentOnPage("Invalid country name.");
    }

    private void switchOption(String textToFind, String newValue) {
        WebElement option = wd().findElement(By.xpath("//option[.='" + textToFind + "']"));
        JavascriptExecutor js = (JavascriptExecutor) wd();
        js.executeScript("arguments[0].setAttribute('value', '" + newValue + "')", option);
    }

    private void test_register_nic(User user, String emailAddress) throws SQLException {
        String contactNhName = "UC063 new nic";
        CreateNicHandlePage cnhp = new CreateNicHandlePage();
        NicHandleDetails details = new NicHandleDetails(contactNhName, emailAddress);
        console().login(user);
        cnhp.view();
        cnhp.fillNewNicForm(details);
        cnhp.submitAndWaitForSuccess();
        String nh = db().getNicHandleByName(contactNhName);
        User newUser = new User(nh, null, false, emailAddress);
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(newUser));
        checkAndResetEmails(emails);
    }

}
