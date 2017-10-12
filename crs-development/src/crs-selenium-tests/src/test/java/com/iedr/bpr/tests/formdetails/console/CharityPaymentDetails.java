package com.iedr.bpr.tests.formdetails.console;

public class CharityPaymentDetails extends PaymentDetails {
    private final String authCode;

    public CharityPaymentDetails(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    @Override
    public PaymentMethod getMethod() {
        return PaymentMethod.CHARITY;
    }

}
