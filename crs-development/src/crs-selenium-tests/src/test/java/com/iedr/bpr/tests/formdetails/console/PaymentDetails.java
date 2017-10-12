package com.iedr.bpr.tests.formdetails.console;

public abstract class PaymentDetails {

    public enum PaymentMethod {
        ADP, CARD, CHARITY
    }

    abstract public PaymentMethod getMethod();
}
