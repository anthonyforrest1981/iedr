package pl.nask.crs.accounts.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import pl.nask.crs.account.ModVersionService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.exporter.XmlExporter;

import ie.domainregistry.account_update_1.AccType;
import ie.domainregistry.account_update_1.AccountType;
import ie.domainregistry.account_update_1.ObjectFactory;

public class AccountUpdateExporter extends XmlExporter<AccType> {
    private final static Logger log = Logger.getLogger(AccountUpdateExporter.class);
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

    private ApplicationConfig applicationConfig;
    private ModVersionService modVersionService;

    public AccountUpdateExporter(ApplicationConfig applicationConfig, ModVersionService modVersionService)
            throws JAXBException, SAXException {
        super("account-update.xsd", "ie.domainregistry.account_update_1");
        this.applicationConfig = applicationConfig;
        this.modVersionService = modVersionService;
    }

    public void exportAccount(long accountNumber, String accName, String vatNo, String address, String billC,
            String country, String county, Date chngDate, String phone, String fax, String email, String vatCategory)
            throws ExportException {
        int nextId = modVersionService.getNextModVersion();
        AccType accType = convert(accountNumber, accName, vatNo, address, billC, country, county,
                chngDate, nextId, phone, fax, email, vatCategory);

        JAXBElement<AccType> jaxbElement = new ObjectFactory().createAcc(accType);

        OutputStream destination = destinationFor(nextId, chngDate);
        serialize(jaxbElement, destination, null);
    }

    private OutputStream destinationFor(int id, Date changeDate) throws AccountExportException {
        ExportConfiguration config = applicationConfig.getAccountUpdateExportConfig();
        String formattedName = NameFormatter.getFormattedName(id, NameFormatter.NamePrefix.ACC,
                NameFormatter.NamePostfix.xml);
        TargetFileInfo fileConfig = config.fileConfig(formattedName, changeDate);
        try {
            File f = fileConfig.getTargetFile(true);
            return new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            String msg = "Error exporting account update while creating output file" + fileConfig;
            log.error(msg);
            log.debug(msg, e);
            throw new AccountExportException(msg, e);
        }
    }

    private AccType convert(long accountNumber, String accName, String vatNo, String address, String billC,
            String country, String county, Date chngDate, int nextId, String phone, String fax, String email,
            String vatCategory) {
        AccType accType = new AccType();
        accType.setId(NameFormatter.getFormattedName(nextId, NameFormatter.NamePrefix.ACC));
        accType.setTimestamp(FORMATTER.format(chngDate));
        AccountType accountType = new AccountType();
        accountType.setAccountNo(accountNumber);
        address = address.replaceAll("\r?\n", " ");
        accountType.setAddress1(address);
        accountType.setBillC(billC);
        accountType.setCountry(country);
        accountType.setCounty(county);
        accountType.setName(accName);
        accountType.setVatNo(vatNo);
        accountType.setPhone(phone);
        accountType.setFax(fax);
        accountType.setEmail(email);
        accountType.setVatCategory(vatCategory);
        accType.setAccount(accountType);
        return accType;
    }
}
