package pl.nask.crs.payment;

import org.testng.annotations.Test;

public class ExtendedPaymentRequestTest {
    @Test
    public void paymentAccountFieldMayBeEmpty() {
        // regression: not expecting any validation exceptions here!
        ExtendedPaymentRequest.authorisedInstance("", "merchantId", "password", TransactionType.TYPE_SETTLE, "authcode",
                "pasref", "orderId");
    }
}
