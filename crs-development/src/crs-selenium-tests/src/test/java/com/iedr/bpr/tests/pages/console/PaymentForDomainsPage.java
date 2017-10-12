package com.iedr.bpr.tests.pages.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableSet;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.forms.console.PaymentForm;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class PaymentForDomainsPage extends DomainGridPage {

    private List<Domain> domains;
    private boolean nrp;
    private int monthsToRenewal;
    private String operationPrefix;

    private PaymentForm paymentForm;

    public PaymentForDomainsPage(List<Domain> domains, boolean nrp, int monthsToRenewal) {
        this.domains = domains;
        this.nrp = nrp;
        this.monthsToRenewal = monthsToRenewal;
        this.operationPrefix = getOperationPrefix();
        // This page can either have a CARD form or none (choosing ADP payment results in no paymentForm component
        // at all, that's the reason for only listing CARD and if-ing in payForDomains.
        this.paymentForm = new PaymentForm(operationPrefix, ImmutableSet.of(PaymentDetails.PaymentMethod.CARD));
    }

    private String getOperationPrefix() {
        if (nrp) {
            return "CurrentNRPStatusesSelectionModel";
        } else if (monthsToRenewal == 0) {
            return "CurrentRenewalsSelectionModel";
        } else {
            return "GridSelectionModel";
        }
    }

    public void view() {
        if (nrp) {
            console().view(console().url.currentNrp);
        } else {
            selectDomainsScreen(monthsToRenewal);
        }
    }

    public void payForDomains(User user, String domainPrefix, PaymentDetails paymentDetails, boolean doubleClick)
            throws SQLException {
        openPaymentScreen(user, domainPrefix, paymentDetails.getMethod());
        new WebDriverWait(wd(), 60).until(ExpectedConditions.presenceOfElementLocated(By.id("ConfirmAction")));
        validatePrice(user);
        if (paymentDetails.getMethod() == PaymentMethod.CARD)
            paymentForm.fillPaymentDetails(paymentDetails);

        WebElement button = wd().findElement(By.name("yt0"));
        if (doubleClick) {
            new Actions(wd()).doubleClick(button).perform();
        } else {
            console().clickElement(button);
        }
    }

    public void openPaymentScreen(User user, String domainPrefix, PaymentMethod method) throws SQLException {
        view();
        selectDomains(domainPrefix);
        selectPaymentPeriod(user);
        // Make sure that we marked all domains.
        String buttonText = String.format("Pay %d Domains", domains.size());
        By buttonXpath = By.xpath("//form//input[contains(@value, '" + buttonText + "')]");
        new WebDriverWait(wd(), 3).until(ExpectedConditions.presenceOfElementLocated(buttonXpath));
        selectPaymentMethod(method, monthsToRenewal);
    }

    public void waitForConfirmation() {
        new WebDriverWait(wd(), 10).until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//input[contains(@value, 'Print This Page')]")));
    }

    public void payForDomainsSuccess(User user, String domainPrefix, PaymentDetails paymentDetails) throws SQLException {
        payForDomains(user, domainPrefix, paymentDetails, false);
        waitForConfirmation();
    }

    public void selectPaymentMethod(PaymentMethod method, int monthsToRenewal) {
        String prefix = "gridaction_";
        if (monthsToRenewal == 0) {
            prefix = "gridactionpay_";
        }
        if (method != null) {
            switch (method) {
                case ADP:
                    console().clickElement(By.id(prefix + "paydeposit"));
                    break;
                case CARD:
                    console().clickElement(By.id(prefix + "payonline"));
                    break;
                case CHARITY:
            }
        }
    }

    private void selectDomainsScreen(int monthsToRenewal) {
        if (monthsToRenewal == 0) {
            console().view(console().url.currentRenewals);
            console().selectOptionByValue(By.id("CurrentRenewalsModel_renewalDateType"), "RENEWAL_TODAY");
        } else {
            console().view(console().url.futureRenewals);
            // Under option "April 2000" there are domains renewed in 04.2000
            // but also 04.2001, 04.2002 etc.
            monthsToRenewal %= 12;
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
            String dateStr = formatter.print((new LocalDate()).plusMonths(monthsToRenewal));
            console().selectOptionByValue(By.id("AccountFutureRenewalByMonthModel_date"), dateStr);
        }
        console().clickAndWaitForPageToLoad(By.name("yt0"));
    }

    private void selectDomains(String domainPrefix) {
        List<String> domainNames = new ArrayList<String>();
        for (Domain domain : domains) {
            domainNames.add(domain.name);
        }
        selectDomainsFromList(domainNames, domainPrefix, "gs_PK");
    }

    private void selectPaymentPeriod(User user) throws SQLException {
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            int productId = db().getProductId(domain.paymentPeriod, false, user.isRegistrar);
            console().selectOptionByValue(By.id("period_" + domain.name), Integer.toString(productId));
        }
    }

    private void validatePrice(User user) throws SQLException {
        // Check total price.
        float sum = 0;
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            float price;
            if (user.isRegistrar) {
                price = db().getPriceForRegistrar(domain.paymentPeriod, false);
            } else {
                price = db().getPriceForDirect(domain.paymentPeriod, false);
            }
            float vatRate = db().getVatRate(user.login);
            float total = (1 + vatRate) * price;
            sum += total;
            WebElement totalValElement = wd().findElement(By.id("total_" + domain.name));
            console().waitForElementTextNotEmpty(totalValElement);
            float computedTotalVal = Float.parseFloat(totalValElement.getText().substring(2).replace(",", ""));
            assertEquals(total, computedTotalVal, 1e-2);
        }
        WebElement totalTotalValElement = wd().findElement(By.id("totaltotal"));
        console().waitForElementTextNotEmpty(totalTotalValElement);
        float computedTotalTotalVal = Float.parseFloat(totalTotalValElement.getText().substring(2).replace(",", ""));
        assertEquals(sum, computedTotalTotalVal, 1e-2);
    }

    public static class Domain {
        public String name;
        public int paymentPeriod;

        public Domain(String name, int paymentPeriod) {
            this.name = name;
            this.paymentPeriod = paymentPeriod;
        }
    }

}
