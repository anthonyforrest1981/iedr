package com.iedr.bpr.tests.formdetails.console;

import org.joda.time.LocalDate;

public class CardPaymentDetails extends PaymentDetails {
    private final String cardHolder;
    private final String cardNumber;
    private final LocalDate expDate;
    private final String authCode;
    private final PaymentMethod method;

    public CardPaymentDetails(PaymentMethod method, String cardHolder, String cardNumber, LocalDate expDate,
            String authCode) {
        this.method = method;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.authCode = authCode;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expDate;
    }

    public String getAuthCode() {
        return authCode;
    }

    @Override
    public PaymentMethod getMethod() {
        return method;
    }

}
