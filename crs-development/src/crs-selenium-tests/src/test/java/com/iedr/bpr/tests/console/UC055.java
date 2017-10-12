package com.iedr.bpr.tests.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC055 extends SeleniumTest {

    public UC055(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc055_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc055_data.sql";
    }

    @Test
    public void test_uc055_sc01() {
        User user = new User("UC055AB-IEDR", "Passw0rd!", true, "uc055_ab@iedr.ie");
        List<String> columns = Arrays.asList("Date", "Payment Type", "Remark", "Order ID", "Total Amount",
                "Deposit Balance");
        final String orderId = "UC055-SC01";
        View view = new View(columns) {
            public void checkData() {
                List<WebElement> rows = wd().findElements(By.xpath("//td[@title='" + orderId + "']/.."));
                assertEquals(4, rows.size());
                console().fillInput("gs_A_from", (new LocalDate()).minusDays(1).toString());
                wd().findElement(By.id("gs_A_from")).sendKeys(Keys.RETURN);
                new WebDriverWait(wd(), 3).until(new Predicate<WebDriver>() {
                    @Override
                    public boolean apply(WebDriver driver) {
                        List<WebElement> rows = wd().findElements(By.xpath("//td[@title='" + orderId + "']/.."));
                        return rows.size() == 2;
                    }
                });
            }
        };
        testViewHistory(user, console().url.depositStatement, view);
    }

    @Test
    public void test_uc055_sc02() {
        User user = new User("UC055AB-IEDR", "Passw0rd!", true, "uc055_ab@iedr.ie");
        List<String> columns = Arrays.asList("Domain", "Reservation Type", "Duration", "Creation Date", "Fee Amount",
                "Vat Amount", "Total Amount", "Order ID", "Ticket", "Financial Status");
        View view = new View(columns) {
            public void checkData() {
                wd().findElement(By.linkText("uc055-sc02a.ie"));
                wd().findElement(By.linkText("uc055-sc02b.ie"));
            }
        };
        testViewHistory(user, console().url.todaysDepositReservations, view);
    }

    @Test
    public void test_uc055_sc03() {
        User user = new User("UC055AB-IEDR", "Passw0rd!", true, "uc055_ab@iedr.ie");
        testViewPaymentHistory(user, "INVUC055c");
    }

    @Test
    public void test_uc055_sc04() {
        User user = new User("UC055AA-IEDR", "Passw0rd!", true, "uc055_aa@iedr.ie");
        testViewPaymentHistory(user, "INVUC055d");
    }

    private void testViewPaymentHistory(User user, final String invoice) {
        // There's an additional column with no name at the end (it's for
        // viewing invoices);
        List<String> columns = Arrays.asList("Settlement Date", "Order ID", "Amount", "Invoice Number",
                "Payment Method", "");
        View view = new View(columns) {
            public void checkData() {
                wd().findElement(By.linkText(invoice));
            }
        };
        testViewHistory(user, console().url.paymentHistory, view);
    }

    private void testViewHistory(User user, String page, View view) {
        console().login(user);
        console().view(page);
        view.checkColumns();
        view.checkData();
    }

    private abstract class View {
        List<String> columns;

        public View(List<String> columns) {
            this.columns = columns;
        }

        @SuppressWarnings("unchecked")
        public void checkColumns() {
            List<String> names = getColumnNames();
            // Take in to account ID column.
            assertEquals(StringUtils.join(names), columns.size() + 1, names.size());
            for (String column : columns) {
                assertTrue(String.format("Column \"%s\" not visible (%s)", column, StringUtils.join(names)),
                        names.contains(column));
            }
        }

        public void checkData() {

        }

        private List<String> getColumnNames() {
            List<WebElement> headers = wd().findElements(By.cssSelector(".ui-jqgrid-labels > th"));
            List<String> names = new ArrayList<String>();
            for (WebElement header : headers) {
                names.add(header.getText().trim());
            }
            return names;
        }
    }

}
