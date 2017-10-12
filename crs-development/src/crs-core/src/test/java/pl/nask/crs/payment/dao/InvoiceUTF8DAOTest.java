package pl.nask.crs.payment.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.payment.*;

public class InvoiceUTF8DAOTest extends AbstractContextAwareTest {

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    CountryDAO countryDAO;

    @Resource
    CountyDAO countyDAO;

    @Test
    public void testGetUnnormalizedInvoice() {
        // Check internalInvoice resultMap has string handlers (used in selectInvoiceById, selectInvoiceByNumber,
        // selectAll, findInvoices, countFindInvoices selects).
        Invoice invoice = invoiceDAO.get(998);
        Assert.assertEquals(invoice.getInvoiceNumber(), "ÄTEST998");
        Assert.assertEquals(invoice.getAccountName(), "ÄAPI TESTS");
        Assert.assertEquals(invoice.getAddress1(), "ÄAddress1");
        Assert.assertEquals(invoice.getAddress2(), "ÄAddress2");
        Assert.assertEquals(invoice.getAddress3(), "ÄAddress3");
        Assert.assertEquals(invoice.getBillingNicHandle(), "ÄIDL2-IEDR");
        Assert.assertEquals(invoice.getBillingNicHandleName(), "ÄIRISH DOMAINS LTD");
        Assert.assertEquals(invoice.getCountry().getName(), "Unnormalized côuṅtry");
        Assert.assertEquals(invoice.getCounty().getName(), "Unnormalized coúnţy");
        Assert.assertEquals(invoice.getCrsVersion(), "Ä3379");
        Assert.assertEquals(invoice.getMD5(), "ÄMD5");
    }

    @Test
    public void testGetUnnormalizedInvoiceInfo() {
        // Check if domainInfo resultMap has string handlers (used in getInvoiceInfo select).
        String invoiceNumber = "ÄTEST997";
        List<DomainInfo> invoiceInfo = invoiceDAO.getInvoiceInfo(invoiceNumber);
        Assert.assertEquals(invoiceInfo.size(), 1);
        DomainInfo domainInfo = invoiceInfo.get(0);
        Assert.assertEquals(domainInfo.getDomainName(), "äpaymentutf8daotest.ie");
        Assert.assertEquals(domainInfo.getOrderId(), "ÄorderId997");
    }

    @Test
    public void testInsertUnnormalizedInvoice() {
        // Check if insertInvoice insert has string handlers.
        Country country = countryDAO.get(999);
        County county = countyDAO.get(164);
        Invoice invoice = new Invoice(-1, "A\u0308TEST996", "A\u0308API TESTS", 101, "A\u0308Address1",
                "A\u0308Address2", "A\u0308Address3", "A\u0308IDL2-IEDR", null, country, county, "A\u03083379",
                new Date(), "A\u0308MD5", false, null, MoneyUtils.getRoundedAndScaledValue(50),
                MoneyUtils.getRoundedAndScaledValue(40), MoneyUtils.getRoundedAndScaledValue(10), null, null);
        int newId = invoiceDAO.createInvoice(invoice);
        Invoice dbInvoice = invoiceDAO.get(newId);
        Assert.assertEquals(dbInvoice.getInvoiceNumber(), "ÄTEST996");
        Assert.assertEquals(dbInvoice.getAccountName(), "ÄAPI TESTS");
        Assert.assertEquals(dbInvoice.getAddress1(), "ÄAddress1");
        Assert.assertEquals(dbInvoice.getAddress2(), "ÄAddress2");
        Assert.assertEquals(dbInvoice.getAddress3(), "ÄAddress3");
        Assert.assertEquals(dbInvoice.getBillingNicHandle(), "ÄIDL2-IEDR");
        Assert.assertEquals(dbInvoice.getBillingNicHandleName(), "ÄIRISH DOMAINS LTD");
        Assert.assertEquals(dbInvoice.getCountry().getName(), "Unnormalized côuṅtry");
        Assert.assertEquals(dbInvoice.getCounty().getName(), "Unnormalized coúnţy");
        Assert.assertEquals(dbInvoice.getCrsVersion(), "Ä3379");
        Assert.assertEquals(dbInvoice.getMD5(), "ÄMD5");
    }

    @Test
    public void testGetInvoiceByUnnormalizedNumber() {
        // Check if selectInvoiceByNumber select has string handlers.
        String invoiceNumber = "U\u0308TEST999";
        Invoice invoice = invoiceDAO.getByNumber(invoiceNumber);
        Assert.assertNotNull(invoice);
    }

    @Test
    public void testFindInvoiceByUnnormalizedCriteria() {
        // Check if invoicesCriteria sql frag has string handlers (used in findInvoices, countFindInvoices,
        // findSimpleInvoices selects).
        String invoiceNumber = "U\u0308TEST999";
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceNumber(invoiceNumber);
        criteria.setAccountName("U\u0308API TESTS");
        criteria.setBillingNH("U\u0308IDL2-IEDR");
        criteria.setInvoiceNumberFrom(invoiceNumber);
        criteria.setInvoiceNumberTo(invoiceNumber);
        LimitedSearchResult<PlainInvoice> limitedResults = invoiceDAO.findPlain(criteria, 0, 5, null);
        List<PlainInvoice> results = limitedResults.getResults();
        Assert.assertEquals(limitedResults.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
        PlainInvoice invoice = results.get(0);
        Assert.assertEquals(invoice.getId(), 999);
    }

    @Test
    public void testGetInvoiceInfoByUnnormalizedNumber() {
        // Check if getInvoiceInfo select has string handlers.
        String invoiceNumber = "U\u0308TEST999";
        List<DomainInfo> invoiceInfo = invoiceDAO.getInvoiceInfo(invoiceNumber);
        Assert.assertEquals(invoiceInfo.size(), 1);
    }

    @Test
    public void testUpdateInvoiceWithUnnormalizedData() {
        // Check if updateInvoice update has string handlers.
        Invoice invoice = invoiceDAO.get(999);
        invoice.setMD5("A\u0308Updated");
        invoiceDAO.update(invoice);
        Invoice dbInvoice = invoiceDAO.get(999);
        Assert.assertEquals(dbInvoice.getMD5(), "ÄUpdated");
    }

}
