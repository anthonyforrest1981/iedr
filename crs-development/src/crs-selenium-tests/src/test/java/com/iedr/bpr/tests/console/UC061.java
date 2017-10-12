package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC061 extends SeleniumTest {

    public UC061(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc061_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc061_data.sql";
    }

    @Test
    public void test_uc061_sc01() throws SQLException {
        User user = this.direct;
        _test_uc061_sc01(user, "uc061-sc01a.ie", "uc061-sc01b.ie");
    }

    public void _test_uc061_sc01(User user, String domainNameA, String domainNameB) throws SQLException {
        console().login(user);
        console().view(console().url.tickets);

        // Verify that both tickets are visible.
        new WebDriverWait(wd(), 1).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("td[title='"
                + domainNameA + "']")));
        new WebDriverWait(wd(), 1).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("td[title='"
                + domainNameB + "']")));

        // Open one of them and check it's content.
        console().clickElement(By.cssSelector("td[title='" + domainNameA + "'] > a"));

        assertEquals(domainNameA, wd().findElement(By.id("ViewTicketModel_domainName")).getAttribute("value"));
        int ticketId = db().getTicketId(domainNameA);
        assertEquals(Integer.toString(ticketId), wd().findElement(By.id("ViewTicketModel_id")).getAttribute("value"));
        assertEquals("Domain modify request.", wd().findElement(By.id("ViewTicketModel_requestersRemark"))
                .getAttribute("value"));
        assertEquals(
                user.login,
                wd().findElement(
                        By.xpath("//label[contains(@for, 'ViewTicketModel_adminContact_0')]/../following-sibling::td//input"))
                        .getAttribute("value"));
        assertEquals(
                user.login,
                wd().findElement(
                        By.xpath("//label[contains(@for, 'ViewTicketModel_techContact')]/../following-sibling::td//input"))
                        .getAttribute("value"));
        assertEquals(
                user.login,
                wd().findElement(
                        By.xpath("//label[contains(@for, 'ViewTicketModel_billingContact')]/../following-sibling::td//input"))
                        .getAttribute("value"));
        assertEquals(user.login, wd().findElement(By.id("ViewTicketModel_creator")).getAttribute("value"));
    }

}
