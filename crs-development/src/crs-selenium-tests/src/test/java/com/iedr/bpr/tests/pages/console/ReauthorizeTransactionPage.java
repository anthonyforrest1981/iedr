package com.iedr.bpr.tests.pages.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.google.common.collect.ImmutableSet;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.console.PaymentForm;
import com.iedr.bpr.tests.utils.console.PaymentUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ReauthorizeTransactionPage implements SubmittableForm {

    private String operationPrefix = "ReauthorizeCCTransactionPayModel";
    public PaymentForm paymentForm = new PaymentForm(operationPrefix,
            ImmutableSet.of(PaymentDetails.PaymentMethod.CARD));

    public void reauthorizeTransaction(int transactionId) {
        PaymentUtils.viewInvalidatedTransaction(transactionId);
        paymentForm.fillPaymentDetails(PredefinedPayments.VALID_CREDIT_CARD);
        submitAndWaitForSuccess();
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForTextPresentOnPage("Transaction Successful");
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<Form>(Arrays.asList(paymentForm));
    }
}
