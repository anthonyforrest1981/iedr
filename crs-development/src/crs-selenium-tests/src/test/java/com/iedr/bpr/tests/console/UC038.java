package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC038 extends SeleniumTest {

    public UC038(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc038_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc038_data.sql";
    }

    @Test
    public void test_uc038_sc01() throws SQLException {
        User user = this.registrar;
        test_query_actual_balance(user);
    }

    @Test
    public void test_uc038_sc02() throws SQLException {
        User user = this.registrar;
        test_query_available_balance(user);
    }

    @Test
    public void test_uc038_nosc01() {
        User user = new User("UC038AA-IEDR", "Passw0rd!", true, "uc038_aa@iedr.ie");
        console().login(user);
        testFiltering("15", 2, false, false);
        testFiltering("<script>alert(document.cookie);</script>", 1, false, true);
        testFiltering("<script>alert(document.cookie);</script>", 1, true, true);
    }

    private void test_query_actual_balance(User user) throws SQLException {
        console().login(user);
        console().view(console().url.depositBalance);
        String content = wd().findElement(By.id("content")).getText();

        float dbDepositAmount = db().getDepositAmount(user.login);
        assertEquals(dbDepositAmount, extractDepositBalance(content), 1e-2);
    }

    private void test_query_available_balance(User user) throws SQLException {
        console().login(user);
        console().view(console().url.depositBalance);
        String content = wd().findElement(By.id("content")).getText();

        float dbDepositAmount = db().getDepositAmount(user.login);
        float dbReservedFunds = db().getReservedDepositFunds(user.login);
        assertEquals(dbDepositAmount - dbReservedFunds, extractAvailableBalance(content), 1e-2);
    }

    private float extractDepositBalance(String content) {
        // (char) 8364 = euro symbol
        return extractValue(content, "Deposit Balance: " + (char) 8364 + " ");
    }

    private float extractAvailableBalance(String content) {
        // (char) 8364 = euro symbol
        return extractValue(content, "Available Balance: " + (char) 8364 + " ");
    }

    private float extractValue(String content, String linePrefix) {
        String value = null;
        List<String> lines = Arrays.asList(content.split("\n"));
        for (String line : lines) {
            if (line.startsWith(linePrefix)) {
                value = line.replace(linePrefix, "");
            }
        }
        return Float.parseFloat(value);
    }

    private void testFiltering(String phrase, int expectedCount, boolean force, boolean error) {
        console().view(console().url.depositTopUps);
        filterTopUps(phrase, force);
        waitForRowsCount(expectedCount);
        if (error) {
            checkErrorMessage();
            String defaultValue = "10";
            assertEquals(defaultValue, wd().findElement(By.id("days")).getAttribute("value"));
        }
    }

    private void filterTopUps(String phrase, boolean force) {
        WebElement input = wd().findElement(By.id("days"));
        if (force) {
            // Set field's value, bypassing its limitation for value length.
            JavascriptExecutor js = (JavascriptExecutor) wd();
            js.executeScript("arguments[0].setAttribute('value', '" + phrase + "')", input);
        } else {
            console().fillInput("days", phrase);
            if (phrase.length() > 3) {
                assertEquals(phrase.substring(0, 3), input.getAttribute("value"));
            }
        }
        console().clickElement(By.name("yt0"));
    }

    private void waitForRowsCount(final int count) {
        new WebDriverWait(wd(), 5).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                List<WebElement> rows = driver.findElements(By.cssSelector("td[aria-describedby='thisJqGrid_rn']"));
                return rows.size() == count;
            }
        });
    }

    private void checkErrorMessage() {
        By error = By.id("DepositTopUpHistoryModel_searchParams_em_");
        new WebDriverWait(wd(), 10).until(ExpectedConditions.visibilityOfElementLocated(error));
        assertEquals("Days must be an integer between 1 and 999.", wd().findElement(error).getText());
    }

}
