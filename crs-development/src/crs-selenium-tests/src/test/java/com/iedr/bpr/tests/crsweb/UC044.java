package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.console.DomainRegistrationPage;
import com.iedr.bpr.tests.utils.User;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UC044 extends SeleniumTest {

    int productId;
    float initialProductPrice;
    boolean initialProductActive;

    public UC044(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc044_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        productId = db().getProductId(10, true, true);
        initialProductPrice = db().getProductPrice(productId);
        initialProductActive = db().getProductActive(productId);
    }

    @Override
    public void tearDown() throws Exception {
        try {
            db().setProductPrice(productId, initialProductPrice);
            db().setProductActive(productId, initialProductActive);
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc044_sc01() throws SQLException {
        User user = this.registrar;
        String domainName = "uc044-sc01.ie";
        emails.add(emailSummaryGenerator.getChangeOfPricingEmail());
        db().setProductActive(productId, false);
        checkRegistrationPeriodPresent(user, domainName, 10, false, productId);
        createProductPricing(10, user.isRegistrar, "UC04410YR", (float) 100.00);
        checkRegistrationPeriodPresent(user, domainName, 10, true, db().getProductIdByCode("UC04410YR"));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc044_sc02() throws SQLException {
        float productPrice = db().getProductPrice(productId);
        float newProductPrice = productPrice + 100;
        emails.add(emailSummaryGenerator.getChangeOfPricingEmail());
        updateProductPrice(productId, newProductPrice);
        assertEquals(newProductPrice, db().getProductPrice(productId), 1e-2);
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc044_sc03() {
        createProductPricing(10, true, "UC04410YR", (float) 100.123);
        try {
            crsweb().waitForTextPresentOnPage("No more than 2 decimal digits allowed");
        } catch (TimeoutException e) {
            fail("A price with 3 decimal digits shouldn't have passed.");
        }
    }

    private void checkRegistrationPeriodPresent(User user, String domainName, int period, boolean present,
            int productId) {
        console().login(user);
        DomainRegistrationPage reg = new DomainRegistrationPage();
        reg.startRegistration(domainName);

        String optionXPath = "//select[@id='Domains_Creation_Details_registration_period']/option[contains(@value, '"
                + productId + "')]";
        if (present) {
            console().assertElementPresent(By.xpath(optionXPath));
            String text = wd().findElement(By.xpath(optionXPath)).getAttribute("innerHTML");
            assertTrue(text, text.contains(String.valueOf(period)));
        } else {
            console().assertElementNotPresent(By.xpath(optionXPath));
        }
    }

    private void createProductPricing(int period, boolean isRegistrar, String productCode, float price) {
        crsweb().login(this.internal);
        crsweb().view(SiteId.ProductPricingCreate);
        crsweb().fillInput("priceCreate-input_wrapper_code", productCode);
        crsweb().fillInput("priceCreate-input_wrapper_description", "Product description");
        crsweb().fillInput("priceCreate-input_wrapper_price", String.valueOf(price));
        crsweb().fillInput("priceCreate-input_wrapper_duration", String.valueOf(period));
        selectProductDates();
        if (isRegistrar) {
            crsweb().clickElement(By.id("priceCreate-input_wrapper_forRegistration"));
        } else {
            crsweb().clickElement(By.id("priceCreate-input_wrapper_direct"));
        }
        crsweb().clickElement(By.id("priceCreate-input_priceCreate-create"));
    }

    private void selectProductDates() {
        selectProductDateFrom();
        selectProductDateTo();
    }

    private void selectProductDateFrom() {
        crsweb().clickElement(By.id("jscal_trigger_wrapper_validFrom"));
        WebElement calendar = wd().findElement(
                By.xpath("//div[contains(@class, 'calendar') and contains(@style, 'display: block')]"));
        crsweb().clickElement(calendar.findElement(By.xpath(".//td[contains(@class, 'today')]")));
    }

    private void selectProductDateTo() {
        crsweb().clickElement(By.id("jscal_trigger_wrapper_validTo"));
        WebElement calendar = wd().findElement(
                By.xpath("//div[contains(@class, 'calendar') and contains(@style, 'display: block')]"));
        crsweb().clickElement(calendar.findElement(
                By.xpath("(.//thead/tr[contains(@class, 'headrow')]/td[contains(@class, 'button')])[4]/div")));
        crsweb().clickElement(calendar.findElement(
                By.xpath(".//tr[contains(@class, 'daysrow')]/td[contains(@class, 'day')]")));
    }

    private void updateProductPrice(int productId, float price) throws SQLException {
        crsweb().login(this.internal);
        viewProduct(productId);
        crsweb().fillInput("priceEdit-input_wrapper_price", String.valueOf(price));
        crsweb().clickElement(By.id("priceEdit-input_priceEdit-save"));
        crsweb().waitForTextPresentOnPage("Product Pricing View");
    }

    private void viewProduct(int productId) throws SQLException {
        String productCode = db().getProductCode(productId);
        crsweb().view(SiteId.ProductPricingView);
        int page = 1;
        while (!productVisible(productCode)) {
            page += 1;
            crsweb().clickElement(By.xpath(String.format("//a[@title='Go to page %s')]", String.valueOf(page))));
        }
        assertTrue(String.format("Product '%s' not found", productCode), productVisible(productCode));
        crsweb().clickElement(By.xpath(String.format("%s/..//img[@title='Edit']", productTdXpath(productCode))));

    }

    private boolean productVisible(String productCode) {
        return crsweb().isElementPresent(By.xpath(productTdXpath(productCode)));
    }

    private String productTdXpath(String productCode) {
        return String.format("//table[@id='priceRow']//td[text()[contains(., '%s')]]", productCode);
    }
}
