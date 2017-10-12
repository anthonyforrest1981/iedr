package com.iedr.bpr.tests.formdetails.console;

public class DepositPaymentDetails extends PaymentDetails {

    @Override
    public PaymentMethod getMethod() {
        return PaymentMethod.ADP;
    }

}
