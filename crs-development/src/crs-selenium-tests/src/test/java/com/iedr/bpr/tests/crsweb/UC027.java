package com.iedr.bpr.tests.crsweb;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class UC027 extends SeleniumTest {

    public UC027(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc027_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    public void test_uc027_sc01() {
        emails.add(emailSummaryGenerator.getChangeOfVatRateEmail());
        test_create_vat_rate();
    }

    private void test_create_vat_rate() {
        crsweb().login(this.internal);
        crsweb().view(SiteId.VatCreate);
        crsweb().selectOptionByValue(By.id("vatCreate-input_wrapper_category"), "A");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String date = sdf.format(new Date());
        crsweb().fillInputAndTriggerChangeAndBlurEvents(By.id("jscal_input_wrapper_fromDate"), date);
        crsweb().fillInput("vatCreate-input_wrapper_vatRate", "0.05");
        crsweb().clickElement(By.id("vatCreate-input_vatCreate-create"));
        new WebDriverWait(wd(), 5).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return driver.getCurrentUrl().endsWith("vatView-input.action");
            }
        });
        checkAndResetEmails(emails);
    }

}
