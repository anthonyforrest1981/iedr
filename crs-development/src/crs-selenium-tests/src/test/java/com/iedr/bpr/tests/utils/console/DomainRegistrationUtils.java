package com.iedr.bpr.tests.utils.console;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.pages.console.DomainRegistrationDetailsPage;
import com.iedr.bpr.tests.pages.console.DomainRegistrationPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DomainRegistrationUtils {

    public static void registerDomain(String domainName, User user, DomainRegistrationDetails details)
            throws SQLException {
        registerDomainNoConfirmation(domainName, user, details);
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        drdp.waitForConfirmation(domainName);
    }

    public static void registerDomain(String domainName, User user, PaymentDetails paymentDetails) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, false);
        registerDomain(domainName, user, details);
    }

    public static void registerDomainNoConfirmation(String domainName, User user, DomainRegistrationDetails details)
            throws SQLException {
        DomainRegistrationPage drp = new DomainRegistrationPage();
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        drp.startRegistration(domainName);
        drdp.fillDomainRegistrationDetailsAndValidate(details);
        if (details.isValidatePrice()) {
            validatePrice(details.getPaymentPeriod(), user);
        }
        drdp.submit();
    }

    public static void verifyEmptyHolder(User user, String domainName) {
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName);
        details.setDomainHolder("");
        drdp.fillDomainRegistrationDetailsAndValidate(details);
        drdp.submit();
        try {
            console().waitForTextPresentOnPage("Legal “owner” cannot be blank.");
        } catch (TimeoutException e) {
            fail("Domain with empty holder name shouldn't have passed.");
        }
    }

    private static void validatePrice(int paymentPeriod, User user) throws SQLException {
        // Check if price and VAT is correct.
        float price;
        if (user.isRegistrar) {
            price = db().getPriceForRegistrar(paymentPeriod, true);
        } else {
            price = db().getPriceForDirect(paymentPeriod, true);
        }
        float vatRate = db().getVatRate(user.login);
        float feeVal = price;
        float vatVal = vatRate * price;
        float totalVal = (1 + vatRate) * price;
        float computedFeeVal = Float.parseFloat(wd().findElement(By.id("feeVal")).getText().substring(2));
        float computedvatVal = Float.parseFloat(wd().findElement(By.id("vatVal")).getText().substring(2));
        float computedTotalVal = Float.parseFloat(wd().findElement(By.id("totalVal")).getText().substring(2));
        assertEquals(feeVal, computedFeeVal, 1e-2);
        assertEquals(vatVal, computedvatVal, 1e-2);
        assertEquals(totalVal, computedTotalVal, 1e-2);
    }

}
