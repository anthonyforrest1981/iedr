package com.iedr.bpr.tests.forms.console;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Joiner;
import com.iedr.bpr.tests.formdetails.console.CardPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.CharityPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.RadioButtonField;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.gui.ConsoleGui.CardTypeXPath;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class PaymentForm implements Form {

    static private Map<PaymentMethod, String> paymentMethodRadioValues = new HashMap<>();
    static {
        paymentMethodRadioValues.put(PaymentMethod.ADP, "ADP");
        paymentMethodRadioValues.put(PaymentMethod.CARD, "CC");
    }

    public RadioButtonField paymentTypeField;

    public TextField cardHolderField;
    public TextField cardNumberField;
    public TextField expirationMonthField;
    public TextField expirationYearField;
    public TextField cvnField;

    public TextField charityCodeField;

    private PaymentMethod selectedMethod;

    private Set<PaymentMethod> supportedPaymentMethods;

    public PaymentForm(String operationPrefix, Set<PaymentMethod> supportedMethods) {
        this.supportedPaymentMethods = Collections.unmodifiableSet(supportedMethods);
        if (supportedPaymentMethods.size() > 1) {
            this.paymentTypeField = new RadioButtonField(console(), By.className("payment_type_box"),
                    ErrorMessageSelector.BELOW, operationPrefix + "[paymentType]");
        } else if (supportedPaymentMethods.size() == 1) {
            selectedMethod = supportedPaymentMethods.iterator().next();
        }
        this.cardHolderField = new TextField(console(), By.id(operationPrefix + "_cardholder"),
                ErrorMessageSelector.IN_TABLE, true);
        this.cardNumberField = new TextField(console(), By.id(operationPrefix + "_creditcardno"),
                ErrorMessageSelector.IN_TABLE, true);
        this.expirationMonthField = new TextField(console(), By.id(operationPrefix + "_exp_month"),
                ErrorMessageSelector.IN_TABLE, false);
        this.expirationYearField = new TextField(console(), By.id(operationPrefix + "_exp_year"),
                ErrorMessageSelector.IN_TABLE, false);
        this.cvnField = new TextField(console(), By.id(operationPrefix + "_cvn"), ErrorMessageSelector.IN_TABLE, false);
        this.charityCodeField = new TextField(console(), By.id("Domains_Creation_Details_charitycode"),
                ErrorMessageSelector.IN_TABLE, false);
    }

    private void fillCardPayment(CardPaymentDetails cardDetails) {
        String cardMonth = String.valueOf(cardDetails.getExpirationDate().getMonthOfYear());
        String cardYear = String.valueOf(cardDetails.getExpirationDate().getYearOfCentury());
        this.cardHolderField.fill(cardDetails.getCardHolder());
        this.cardNumberField.fill(cardDetails.getCardNumber());
        this.expirationMonthField.fill(cardMonth);
        this.expirationYearField.fill(cardYear);
        this.cvnField.fill(cardDetails.getAuthCode());
        console().triggerFormValidation();

        String cardTypeSelector = cardDetails.getMethod() == PaymentMethod.CARD ? CardTypeXPath.VISA
                : CardTypeXPath.DEBIT_VISA;
        console().clickElement(By.xpath(cardTypeSelector));
    }

    private void fillCharityPayment(String chyCode) {
        new WebDriverWait(wd(), 10)
                .until(ExpectedConditions.visibilityOfElementLocated(charityCodeField.getSelector()));
        charityCodeField.fill(chyCode);
        console().triggerFormValidation();
    }

    public void fillPaymentDetails(PaymentDetails details) {
        PaymentMethod paymentMethod = details.getMethod();
        switch (paymentMethod) {
            case ADP:
                selectPaymentType(paymentMethod);
                break;
            case CARD:
                selectPaymentType(paymentMethod);
                CardPaymentDetails cardDetails = (CardPaymentDetails) details;
                fillCardPayment(cardDetails);
                break;
            case CHARITY:
                final CharityPaymentDetails charityDetails = (CharityPaymentDetails) details;
                fillCharityPayment(charityDetails.getAuthCode());
        }
    }

    public void selectPaymentType(PaymentMethod method) {
        if (!supportsMethod(method)) {
            throw new IllegalArgumentException(method + " is not supported, only supported payment methods are "
                    + Joiner.on(", ").join(getSupportedPaymentMethods()));
        }
        if (paymentTypeField != null) {
            paymentTypeField.choose(paymentMethodRadioValues.get(method));
        }
        selectedMethod = method;
    }

    private boolean supportsMethod(final PaymentMethod method) {
        return this.supportedPaymentMethods.contains(method);
    }

    public List<PaymentMethod> getSupportedPaymentMethods() {
        return new ArrayList<>(supportedPaymentMethods);
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        List<Form> subForms = new ArrayList<>();
        if (supportedPaymentMethods.size() > 1) {
            subForms.add(paymentTypeField);
        }
        if (selectedMethod == PaymentMethod.CARD) {
            subForms.addAll(Arrays.asList(cardHolderField, cardNumberField, expirationMonthField, expirationYearField,
                    cvnField));
        } else if (selectedMethod == PaymentMethod.CHARITY) {
            subForms.add(charityCodeField);
        }
        return subForms;
    }
}
