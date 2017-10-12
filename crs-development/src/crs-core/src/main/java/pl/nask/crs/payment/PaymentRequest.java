package pl.nask.crs.payment;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;

public class PaymentRequest {

    private final String currency;
    private int amountInLowestCurrencyUnit;
    private CreditCard creditCard;

    public static PaymentRequest createPaymentRequestWithLowestCurrencyUnit(String currency,
            int amountInLowestCurrencyUnit, CreditCard creditCard) {
        return new PaymentRequest(currency, amountInLowestCurrencyUnit, creditCard);
    }

    public static PaymentRequest createPaymentRequestWithLowestCurrencyUnit(String currency,
            int amountInLowestCurrencyUnit, String cardNumber, String cardExpDate, String cardType,
            String cardHolderName, String cvnNumber, Integer cvnPresenceIndicator) {
        return new PaymentRequest(currency, amountInLowestCurrencyUnit, new CreditCard(cardNumber, cardExpDate,
                cardType, cardHolderName, cvnNumber, cvnPresenceIndicator));
    }

    public static PaymentRequest createPaymentRequestWithStandardCurrencyUnit(String currency,
            BigDecimal amountInStandardCurrencyUnit, CreditCard creditCard) {
        return new PaymentRequest(currency, MoneyUtils.getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit),
                creditCard);
    }

    public static PaymentRequest createPaymentRequestWithStandardCurrencyUnit(String currency,
            BigDecimal amountInStandardCurrencyUnit, String cardNumber, String cardExpDate, String cardType,
            String cardHolderName, String cvnNumber, Integer cvnPresenceIndicator) {
        return new PaymentRequest(currency, MoneyUtils.getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit),
                new CreditCard(cardNumber, cardExpDate, cardType, cardHolderName, cvnNumber, cvnPresenceIndicator));
    }

    private PaymentRequest(String currency, int amountInLowestCurrencyUnit, CreditCard creditCard) {
        this.currency = currency;
        this.amountInLowestCurrencyUnit = amountInLowestCurrencyUnit;
        this.creditCard = creditCard;
    }

    public String getCurrency() {
        return currency;
    }

    public int getAmountInLowestCurrencyUnit() {
        return amountInLowestCurrencyUnit;
    }

    public BigDecimal getAmountInStandardCurrencyUnit() {
        return MoneyUtils.getValueInStandardCurrencyUnit(amountInLowestCurrencyUnit);
    }

    public String getCardNumber() {
        return creditCard.getCardNumber();
    }

    public String getCardExpDate() {
        return creditCard.getCardExpDate();
    }

    public String getCardType() {
        return creditCard.getCardType();
    }

    public String getCardHolderName() {
        return creditCard.getCardHolderName();
    }

    public String getCvnNumber() {
        return creditCard.getCvnNumber();
    }

    public Integer getCvnPresenceIndicator() {
        return creditCard.getCvnPresenceIndicator();
    }

    public void setAmountInLowestCurrencyUnit(int amountInLowestCurrencyUnit) {
        this.amountInLowestCurrencyUnit = amountInLowestCurrencyUnit;
    }

    public void setAmountInStandardCurrencyUnit(BigDecimal amountInStandardCurrencyUnit) {
        this.amountInLowestCurrencyUnit = MoneyUtils.getValueInLowestCurrencyUnit(amountInStandardCurrencyUnit);
    }
}
