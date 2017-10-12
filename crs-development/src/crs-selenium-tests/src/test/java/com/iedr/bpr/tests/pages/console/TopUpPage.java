package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.google.common.collect.ImmutableSet;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.forms.console.PaymentForm;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class TopUpPage implements SubmittableForm {

    private String operationPrefix = "AccountTopUpModel";

    public PaymentForm paymentForm = new PaymentForm(operationPrefix,
            ImmutableSet.of(PaymentDetails.PaymentMethod.CARD));
    public TextField topUpAmountField = new TextField(console(), By.id(operationPrefix + "_euros_amount"),
            ErrorMessageSelector.IN_TABLE, false);

    public void view() {
        console().view(console().url.topUp);
    }

    public void fillPaymentDetails(String topUpAmount, PaymentDetails details) {
        paymentForm.fillPaymentDetails(details);
        topUpAmountField.fill(topUpAmount);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(paymentForm, topUpAmountField);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        console().waitForTextPresentOnPage("Transaction Successful");
    }

}
