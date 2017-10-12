package com.iedr.bpr.tests.formdetails.console;

import org.joda.time.LocalDate;

import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;

public class PredefinedPayments {

    public static final DepositPaymentDetails DEPOSIT_PAYMENT_DETAILS = new DepositPaymentDetails();
    public static final CardPaymentDetails VALID_CREDIT_CARD = new CardPaymentDetails(PaymentMethod.CARD,
            "Credit Card Test Holder 0001", "4263971921001307", LocalDate.now().plusYears(2), "123");
    public static final CardPaymentDetails INVALID_CREDIT_CARD = new CardPaymentDetails(PaymentMethod.CARD,
            "Credit Card Test Holder 0001", "4000126842489127", LocalDate.now().plusYears(2), "123");
    // Debit card payment was removed in CRS-174 and CRS-797, but some test scenarios were left in TestRail. As agreed
    // with IEDR, those scenarios should use credit card numbers.
    public static final CardPaymentDetails VALID_DEBIT_CARD = new CardPaymentDetails(PaymentMethod.CARD,
            "Debit Card Test Holder 0001", "4263971921001307", LocalDate.now().plusYears(2), "123");
    public static final CardPaymentDetails INVALID_DEBIT_CARD = new CardPaymentDetails(PaymentMethod.CARD,
            "Debit Card Test Holder 0001", "4000126842489127", LocalDate.now().plusYears(2), "123");

}
