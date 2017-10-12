package pl.nask.crs.payment.dao;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.*;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareInvoices;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceDAOTest extends AbstractContextAwareTest {

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    CountryDAO countryDAO;

    @Resource
    CountyDAO countyDAO;

    @Test
    public void createTest() {
        List<Invoice> invoices = invoiceDAO.getAll();
        int invoicesCount = invoices.size();
        Country country = countryDAO.get(199);
        County county = countyDAO.get(0);
        Invoice invoice = new Invoice(-1, "123", "account name", 1, "address1", null, null, "BillingNH", null,
                country, county, "crsVersion", new Date(), "MD5", false, null, MoneyUtils.getRoundedAndScaledValue(50),
                MoneyUtils.getRoundedAndScaledValue(40), MoneyUtils.getRoundedAndScaledValue(10), null, null);
        int newId = invoiceDAO.createInvoice(invoice);
        invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(invoicesCount + 1, invoices.size());
        Invoice fromDB = invoiceDAO.get(newId);
        compareInvoices(invoice, fromDB);
    }

    @Test
    public void getTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals("100", invoice.getInvoiceNumber());
        AssertJUnit.assertEquals(1, invoice.getTransactions().size());
        Transaction transaction = invoice.getTransactions().get(0);
        AssertJUnit.assertEquals(7, transaction.getId());
        AssertJUnit.assertEquals(5, (int) transaction.getInvoiceId());
        Assert.assertNotNull(invoice.getSettlementDate());
        AssertJUnit.assertEquals(PaymentMethod.ADP, invoice.getPaymentMethod());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findTest() {
        invoiceDAO.find(null, 0, 1);
    }

    @Test
    public void findPlainAllTest() {
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("id", true));
        LimitedSearchResult<PlainInvoice> invoices = invoiceDAO.findPlain(null, 0, 10, sortBy);
        AssertJUnit.assertEquals(10, invoices.getTotalResults());
        AssertJUnit.assertEquals(10, invoices.getResults().size());

        AssertJUnit.assertEquals(5, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(10, invoices.getResults().get(1).getId());

        sortBy = Arrays.asList(new SortCriterion("id", false));
        invoices = invoiceDAO.findPlain(null, 0, 10, sortBy);

        AssertJUnit.assertEquals(999, invoices.getResults().get(0).getId());
        AssertJUnit.assertEquals(998, invoices.getResults().get(1).getId());

    }

    @Test
    public void findWithCriteriaTest() {
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        LimitedSearchResult<PlainInvoice> invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(10, invoices.getTotalResults());
        AssertJUnit.assertEquals(10, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setCustomerType(CustomerType.Registrar);
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(8, invoices.getTotalResults());
        AssertJUnit.assertEquals(8, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setPaymentMethod(PaymentMethod.ADP);
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, invoices.getTotalResults());
        AssertJUnit.assertEquals(7, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setBillingNH("APIT1-IEDR");
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, invoices.getTotalResults());
        AssertJUnit.assertEquals(1, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setSettledFrom(new Date(101, 0, 1));
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setSettledTo(new Date(101, 0, 1));
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(0, invoices.getTotalResults());
        AssertJUnit.assertEquals(0, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceNumber("102");
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, invoices.getTotalResults());
        AssertJUnit.assertEquals(1, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceNumberFrom("101");
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(9, invoices.getTotalResults());
        AssertJUnit.assertEquals(9, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateTo(DateUtils.endOfDay(new Date(112, 0, 1)));
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(3, invoices.getTotalResults());
        AssertJUnit.assertEquals(3, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateFrom(DateUtils.startOfDay(new Date(112, 5, 21)));
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, invoices.getTotalResults());
        AssertJUnit.assertEquals(7, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateFrom(DateUtils.startOfDay(new Date(112, 0, 1)));
        criteria.setInvoiceDateTo(DateUtils.endOfDay(new Date(112, 5, 21)));
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(4, invoices.getTotalResults());
        AssertJUnit.assertEquals(4, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateLike("2012-06");
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        criteria.setSettlementDateLike("2012");
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, invoices.getTotalResults());
        AssertJUnit.assertEquals(5, invoices.getResults().size());

        criteria = new PlainInvoiceSearchCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, Calendar.JUNE, 21);
        Date date = calendar.getTime();
        criteria.setInvoiceDateFrom(date);
        criteria.setInvoiceDateTo(date);
        criteria.setSettledFrom(date);
        criteria.setSettledTo(date);
        invoices = invoiceDAO.findPlain(criteria, 0, 10, null);
        AssertJUnit.assertEquals(2, invoices.getTotalResults());
        AssertJUnit.assertEquals(2, invoices.getResults().size());
    }

    @Test
    public void countTest() {
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateLike("2012-06");
        AssertJUnit.assertEquals(2, invoiceDAO.count(criteria));
    }

    @Test
    public void existsTest() {
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateLike("2012-06");
        AssertJUnit.assertTrue(invoiceDAO.exists(criteria));
    }

    @Test
    public void notExistsTest() {
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        criteria.setInvoiceDateLike("2012-07");
        AssertJUnit.assertFalse(invoiceDAO.exists(criteria));
    }

    @Test
    public void totalVatMapTest() {
        Invoice invoice = invoiceDAO.get(10);
        Map<BigDecimal, BigDecimal> map = invoice.getTotalVatMap();
        BigDecimal total = map.get(MoneyUtils.getRoundedAndScaledValue(21.40));
        Assert.assertNotNull(total);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(65.27), total);
    }

    @Test
    public void getInvoiceInfoTest() {
        List<DomainInfo> domainInfoList = invoiceDAO.getInvoiceInfo("101");
        AssertJUnit.assertEquals(7, domainInfoList.size());
    }

    @Test
    public void getByNumberTest() {
        Invoice invoice = invoiceDAO.getByNumber("102");
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(48.56), invoice.getTotalCost());
    }

    @Test
    public void lockTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);

        AssertJUnit.assertTrue(invoiceDAO.lock(5));
    }

    @Test
    public void updateTest() {
        Invoice invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals("", invoice.getMD5());
        AssertJUnit.assertFalse(invoice.isCompleted());

        String md5String = "12345";
        invoice.setMD5(md5String);
        invoice.setCompleted(true);
        invoiceDAO.update(invoice);

        invoice = invoiceDAO.get(5);
        AssertJUnit.assertNotNull(invoice);
        AssertJUnit.assertEquals(md5String, invoice.getMD5());
        AssertJUnit.assertTrue(invoice.isCompleted());
    }

    @Test
    public void testLooseUtf8Validation() {
        Invoice invoice = invoiceDAO.getByNumber("BadUtf8Order\01");
        Assert.assertNotNull(invoice);
        Assert.assertEquals(invoice.getAddress1(), "BadUtf8 Address 1\01");
    }

}
