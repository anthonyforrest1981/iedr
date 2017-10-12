package pl.nask.crs.payment;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentResponseTest {

    @Test
    public void paymentResponseToString() {
        PaymentResponse response = new PaymentResponse();
        response.setResult("resultValue");
        response.setMessage("messageValue");
        response.setAuthcode("authCodeValue");
        response.setPasref("pasrefValue");
        response.setBank("bankValue");
        response.setCountry("countryValue");

        String expected = "PaymentResponse:" + "\n  result:   resultValue" + "\n  message:  messageValue"
                + "\n  authcode: authCodeValue" + "\n  pasref:   pasrefValue" + "\n  bank:     bankValue"
                + "\n  country:  countryValue";

        Assert.assertEquals(response.toString(), expected);
    }
}
