package pl.nask.crs.payment.email;

import java.util.Date;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;

public class InvoiceEmailParametersTest {

    private InvoiceEmailParameters params;

    @BeforeMethod
    public void prepareInvoiceEmailParams() {
        params = new InvoiceEmailParameters(newNic(), new Date(), null);
    }

    private NicHandle newNic() {
        return new NicHandle("TEST-IEDR", "NAME", new Account(1), "companyName", "address", null, null, null,
                null, "email@domain.com", NicHandleStatus.Active, new Date(), null, new Date(), false, null,
                null, null, null, false);
    }

    @Test
    public void billingNhParamShouldBePresent() {
        assertParamNamePresent("BILL-C_NAME", params);
    }

    @Test
    public void billingNHEmailParamShouldBePresent() {
        assertParamNamePresent("BILL-C_EMAIL", params);
    }

    @Test
    public void invoiceDateParamShouldBePopulated() {
        assertParamNamePresent("INVOICE_DATE", params);
    }

    @Test
    public void nullInvoiceDateShouldBePresendAsNull() {
        InvoiceEmailParameters params = new InvoiceEmailParameters(newNic(), null, null);
        String value = params.getParameterValue("INVOICE_DATE", false);
        AssertJUnit.assertNull("Param name: " + "INVOICE_DATE", value);
    }

    private void assertParamNamePresent(String name, InvoiceEmailParameters params) {
        String value = params.getParameterValue(name, false);
        AssertJUnit.assertNotNull("Param name: " + name, value);
    }
}
