package com.iedr.bpr.tests.utils.console;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.TopUpPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class PaymentUtils {

    public static String topUpAccount(User user, String topUpAmount) {
        return topUpAccount(user, topUpAmount, PredefinedPayments.VALID_CREDIT_CARD);
    }

    public static String topUpAccount(User user, String topUpAmount, PaymentDetails details) {
        TopUpPage topUp = new TopUpPage();
        console().login(user);
        topUp.view();
        topUp.fillPaymentDetails(topUpAmount, details);
        topUp.submitAndWaitForSuccess();

        String td = "//strong[text()='OrderId']/../following-sibling::td";
        return wd().findElement(By.xpath(td)).getText();
    }

    public static void viewInvalidatedTransaction(int transactionId) {
        console().view(console().url.reauthorizeCcTransaction);
        console().clickElement(By.linkText(String.valueOf(transactionId)));
    }
}
