package pl.nask.crs.payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.services.AccountHelperService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.config.*;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.CountyNotExistsException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.invoicing.service.impl.InvoiceXMLExporter;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.dao.VatDAO;

public class XmlConversionTest extends AbstractContextAwareTest {
    @Autowired
    VatDAO vatDAO;

    @Autowired
    private DomainPricingDAO domainPricingDAO;

    @Autowired
    private DomainSearchService domainSearchService;

    @Autowired
    private AccountHelperService accountHelperService;

    @Autowired
    private NicHandleSearchService nicHandleSearchService;

    @Autowired
    private CountryFactory countryFactory;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void testXmlExport() throws Exception {
        InvoiceXMLExporter exporter = new InvoiceXMLExporter(vatDAO, applicationConfig, domainPricingDAO,
                domainSearchService, accountHelperService, nicHandleSearchService);
        // validate against schema
        exporter.setValidateOutput(true);
        Invoice invoice = prepareInvoice();
        exporter.export(invoice);
    }

    private Invoice prepareInvoice() throws CountyNotExistsException, InvalidCountryException {
        Date invoiceDate = new Date();
        List<Transaction> transactions = new ArrayList<Transaction>();
        List<Reservation> reservations1 = new ArrayList<Reservation>();

        Vat vat = new Vat(1, "A", invoiceDate, MoneyUtils.getScaledVatValue(20));
        DomainPrice domainPrice = domainPricingDAO.getDomainPriceByCode("RM2Yr", new Date());
        ProductPriceWithVat price = new ProductPriceWithVat(Period.fromYears(1), domainPrice.getId(),
                MoneyUtils.getRoundedAndScaledValue(100), vat);
        reservations1.add(reservation(1, "nic1", "suka.ie", invoiceDate, price));
        reservations1.add(reservation(2, "nic1", "pizzaonline.ie", invoiceDate, price));

        List<Reservation> reservations2 = new ArrayList<Reservation>();
        reservations2.add(reservation(3, "nic1", "theweb.ie", invoiceDate, price));
        reservations2.add(reservation(4, "nic1", "theweb2.ie", invoiceDate, price));

        Transaction trans1 = new Transaction(1, "nic1", 1, "1", "01012001-S-0001", true, true,
                MoneyUtils.getRoundedAndScaledValue(2400), MoneyUtils.getRoundedAndScaledValue(2000),
                MoneyUtils.getRoundedAndScaledValue(400), false, null, invoiceDate, reservations1, false, null, null,
                new Date(), PaymentMethod.ADP, OperationType.RENEWAL, null, null);
        Transaction trans2 = new Transaction(2, "nic1", 1, "1", "01012001-S-0002", true, true,
                MoneyUtils.getRoundedAndScaledValue(3600), MoneyUtils.getRoundedAndScaledValue(3000),
                MoneyUtils.getRoundedAndScaledValue(600), false, null, invoiceDate, reservations2, false, null, null,
                new Date(), PaymentMethod.ADP, OperationType.RENEWAL, null, null);

        transactions.add(trans1);
        transactions.add(trans2);
        Invoice inv = new Invoice(1, "01", "account 1", 101, "addr1", "addr2", "addr3", "IDL2-IEDR", null,
                countryFactory.getCountry(199), countryFactory.getCounty(0), "crsVersion", invoiceDate, "aaabbbcccmd5",
                true, transactions, MoneyUtils.getRoundedAndScaledValue(6000), MoneyUtils.getRoundedAndScaledValue(5000),
                MoneyUtils.getRoundedAndScaledValue(1000), null, PaymentMethod.ADP);
        return inv;
    }

    private Reservation reservation(long id, String billC, String domainName, Date invoiceDate,
            ProductPriceWithVat price) {
        Reservation res = new Reservation(id, billC, domainName, 12, invoiceDate, price, true, true, invoiceDate, 111L,
                1L, OperationType.RENEWAL, PaymentMethod.ADP, "1", null, null, true);
        res.setStartDate(invoiceDate);
        res.setEndDate(DateUtils.addYears(invoiceDate, 1));
        return res;
    }

    @Test
    public void testExportConfiguration() {
        Date d = new Date();
        ExportConfiguration cfg = new ExportConfiguration("target", false);
        String formattedName = NameFormatter.getFormattedName(1, NameFormatter.NamePrefix.INV,
                NameFormatter.NamePostfix.xml);
        TargetFileInfo fileConfig = cfg.fileConfig(formattedName, d);
        AssertJUnit.assertEquals("target", fileConfig.getDirectory().getName());
        AssertJUnit.assertEquals("INV0000001.xml", fileConfig.getFilename());

        cfg = new ExportConfiguration("target", true);
        fileConfig = cfg.fileConfig(formattedName, d);
        String expectedDirName = DateFormatUtils.format(d, "yyyy-MM-dd");
        AssertJUnit.assertEquals(expectedDirName, fileConfig.getDirectory().getName());
        AssertJUnit.assertEquals("target", fileConfig.getDirectory().getParent());
        AssertJUnit.assertEquals("INV0000001.xml", fileConfig.getFilename());
    }

    @Test
    public void testInvoiceExportConfiguration() {
        Date d = new Date();
        InvoiceExportConfiguration cfg = new InvoiceExportConfiguration("write", "read", false);
        String formattedName = NameFormatter.getFormattedName(1, NameFormatter.NamePrefix.INV,
                NameFormatter.NamePostfix.xml);
        TargetFileInfo writeConfig = cfg.fileConfig(formattedName, d);
        AssertJUnit.assertEquals("write", writeConfig.getDirectory().getName());
        AssertJUnit.assertEquals("INV0000001.xml", writeConfig.getFilename());
        TargetFileInfo readConfig = cfg.archiveFileConfig(formattedName, d);
        AssertJUnit.assertEquals("read", readConfig.getDirectory().getName());
        AssertJUnit.assertEquals("INV0000001.xml", readConfig.getFilename());

        cfg = new InvoiceExportConfiguration("write", "read", true);
        writeConfig = cfg.fileConfig(formattedName, d);
        String expectedDirName = DateFormatUtils.format(d, "yyyy-MM-dd");
        AssertJUnit.assertEquals(expectedDirName, writeConfig.getDirectory().getName());
        AssertJUnit.assertEquals("write", writeConfig.getDirectory().getParent());
        AssertJUnit.assertEquals("INV0000001.xml", writeConfig.getFilename());

        readConfig = cfg.archiveFileConfig(formattedName, d);
        AssertJUnit.assertEquals(expectedDirName, readConfig.getDirectory().getName());
        AssertJUnit.assertEquals("read", readConfig.getDirectory().getParent());
        AssertJUnit.assertEquals("INV0000001.xml", readConfig.getFilename());

    }

}
