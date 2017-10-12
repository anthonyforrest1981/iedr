package pl.nask.crs.payment.service;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.payment.AbstractContextAwareTest;

public class GetNextInvoiceNumberTest extends AbstractContextAwareTest {

    @Resource
    InvoiceNumberService invoiceNumberService;

    @Test
    public void getForExistingYearTest() {
        Date date = new Date(120, 6, 6);
        int nextInvoiceNumber = invoiceNumberService.getNextInvoiceNumber(date);
        AssertJUnit.assertEquals(6, nextInvoiceNumber);
        invoiceNumberService.getNextInvoiceNumber(date);
        nextInvoiceNumber = invoiceNumberService.getNextInvoiceNumber(date);
        AssertJUnit.assertEquals(8, nextInvoiceNumber);
    }

    @Test
    public void getForNonExistingYear() {
        int nextInvoiceNumber = invoiceNumberService.getNextInvoiceNumber();
        AssertJUnit.assertEquals(1, nextInvoiceNumber);
        invoiceNumberService.getNextInvoiceNumber();
        nextInvoiceNumber = invoiceNumberService.getNextInvoiceNumber();
        AssertJUnit.assertEquals(3, nextInvoiceNumber);
    }
}
