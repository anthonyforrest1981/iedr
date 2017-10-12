package com.iedr.bpr.tests.utils.console;

import java.sql.SQLException;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.pages.console.DomainTransferDetailsPage;
import com.iedr.bpr.tests.pages.console.DomainTransferPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class DomainTransferUtils {

    public static void transferDomain(String domainName, User user, PaymentDetails method) throws SQLException {
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode, method, 1);
        transferDomain(domainName, user, details);
    }

    public static void transferDomain(String domainName, User user, DomainTransferDetails details) throws SQLException {
        transferDomainNoConfirmation(domainName, user, details);
        // argument for DomainTransferDetailsPage is irrelevant because it's only used for waitForConfirmation.
        DomainTransferDetailsPage dtdp = new DomainTransferDetailsPage(false);
        dtdp.waitForConfirmation(domainName);
    }

    public static void transferDomainNoConfirmation(String domainName, User user, DomainTransferDetails details)
            throws SQLException {
        DomainTransferPage dtp = new DomainTransferPage();
        dtp.startTransfer(domainName);
        DomainTransferDetailsPage dtdp = new DomainTransferDetailsPage(db().isDomainCharity(domainName));
        dtdp.fillTransferDomainDetails(details);
        validatePrice(user, details);
        dtdp.submit();
    }

    public static void validatePrice(User user, DomainTransferDetails details) throws SQLException {
        if (details.getPaymentMethod().getMethod() == PaymentDetails.PaymentMethod.CHARITY)
            return;

        float price;
        int paymentPeriod = details.getPaymentPeriod();
        if (user.isRegistrar) {
            price = db().getPriceForRegistrar(paymentPeriod, false);
        } else {
            price = db().getPriceForDirect(paymentPeriod, false);
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
