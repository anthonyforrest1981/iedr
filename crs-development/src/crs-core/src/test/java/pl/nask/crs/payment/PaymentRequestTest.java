package pl.nask.crs.payment;

import org.junit.Assert;
import org.junit.Test;

import pl.nask.crs.commons.MoneyUtils;

public class PaymentRequestTest {

    @Test
    public void cardNumberNormalization() {
        CreditCard card = new CreditCard(" 1234 5678 99 ", "05/2016", "Visa", "Test", "123", 1);
        Assert.assertEquals("Credit card number should not contain any whitespace", "1234567899", card.getCardNumber());
    }

    @Test
    public void currencyUnitConversion() {
        CreditCard card = new CreditCard("1234567899", "05/2016", "Visa", "Test", "123", 1);
        PaymentRequest request = PaymentRequest.createPaymentRequestWithStandardCurrencyUnit("EUR",
                MoneyUtils.getRoundedAndScaledValue(123), card);
        Assert.assertEquals(12300, request.getAmountInLowestCurrencyUnit());
        request = PaymentRequest.createPaymentRequestWithLowestCurrencyUnit("EUR", 4567, card);
        Assert.assertEquals(request.getAmountInStandardCurrencyUnit(), MoneyUtils.getRoundedAndScaledValue(45.67));
    }
}
