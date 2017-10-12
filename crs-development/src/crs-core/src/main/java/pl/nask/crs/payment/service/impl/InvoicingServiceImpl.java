package pl.nask.crs.payment.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.InvoiceExportConfiguration;
import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.commons.config.TargetFileInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.pdfmerge.PdfMergeTool;
import pl.nask.crs.commons.pdfmerge.PdfMergeToolException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.config.VersionInfo;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.*;
import pl.nask.crs.payment.email.InvoiceEmailParameters;
import pl.nask.crs.payment.exceptions.InvoiceEmailException;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.InvoiceNumberService;
import pl.nask.crs.payment.service.InvoicingService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class InvoicingServiceImpl implements InvoicingService {

    private static Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

    private final InvoiceDAO invoiceDAO;
    private final ReservationDAO reservationDAO;
    private final TransactionDAO transactionDAO;
    private final TransactionHistDAO transactionHistDAO;
    private final ReservationHistDAO reservationHistDAO;

    private final InvoiceNumberService invoiceNumberService;
    private final NicHandleSearchService nicHandleSearchService;
    private final ResellerDefaultsService resellerDefaultsService;

    private final PdfMergeTool pdfMergeTool;
    private final EmailTemplateSender emailTemplateSender;
    private final ApplicationConfig applicationConfig;

    public InvoicingServiceImpl(InvoiceDAO invoiceDAO, ReservationDAO reservationDAO, TransactionDAO transactionDAO,
            TransactionHistDAO transactionHistDAO, ReservationHistDAO reservationHistDAO,
            InvoiceNumberService invoiceNumberService, NicHandleSearchService nicHandleSearchService,
            ResellerDefaultsService resellerDefaultsService,  PdfMergeTool pdfMergeTool,
            EmailTemplateSender emailTemplateSender, ApplicationConfig applicationConfig) {
        Validator.assertNotNull(invoiceDAO, "invoiceDAO");
        Validator.assertNotNull(reservationDAO, "reservation DAO");
        Validator.assertNotNull(transactionDAO, "transactionDAO");
        Validator.assertNotNull(transactionHistDAO, "transactionHistDAO");
        Validator.assertNotNull(reservationHistDAO, "reservationHistDAO");
        Validator.assertNotNull(invoiceNumberService, "invoicingService");
        Validator.assertNotNull(nicHandleSearchService, "nicHandle search service");
        Validator.assertNotNull(resellerDefaultsService, "resellerDefaultsService");
        Validator.assertNotNull(pdfMergeTool, "pdfMergeTool");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.invoiceDAO = invoiceDAO;
        this.reservationDAO = reservationDAO;
        this.transactionDAO = transactionDAO;
        this.transactionHistDAO = transactionHistDAO;
        this.reservationHistDAO = reservationHistDAO;
        this.invoiceNumberService = invoiceNumberService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.resellerDefaultsService = resellerDefaultsService;
        this.pdfMergeTool = pdfMergeTool;
        this.emailTemplateSender = emailTemplateSender;
        this.applicationConfig = applicationConfig;
    }

    @Override
    public Invoice getInvoice(int invoiceId) throws InvoiceNotFoundException {
        Invoice invoice = invoiceDAO.get(invoiceId);
        if (invoice == null) {
            throw new InvoiceNotFoundException(null, invoiceId);
        }
        return invoice;
    }

    @Override
    public List<DomainInfo> getInvoiceInfo(String invoiceNumber) {
        return invoiceDAO.getInvoiceInfo(invoiceNumber);
    }

    @Override
    public int createInvoiceAndAssociateWithTransactions(String nicHandleId, List<Transaction> transactions)
            throws NicHandleNotFoundException, TransactionNotFoundException {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalNetAmount = BigDecimal.ZERO;
        BigDecimal totalVatAmount = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            totalCost = MoneyUtils.add(totalCost, transaction.getTotalCost());
            totalNetAmount = MoneyUtils.add(totalNetAmount, transaction.getTotalNetAmount());
            totalVatAmount = MoneyUtils.add(totalVatAmount, transaction.getTotalVatAmount());
        }
        int newInvoiceId = createInvoice(nicHandleId, VersionInfo.getRevision(), totalCost, totalNetAmount,
                totalVatAmount);
        associateTransactionsWithInvoice(newInvoiceId, transactions);
        moveToHistory(transactions);
        return newInvoiceId;
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        invoiceDAO.update(invoice);
    }

    @Override
    public LimitedSearchResult<PlainInvoice> findInvoices(PlainInvoiceSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        return invoiceDAO.findPlain(criteria, offset, limit, sortBy);
    }

    @Override
    public InputStream viewXmlInvoice(String invoiceNumber) throws InvoiceNotFoundException {
        return getInvoiceStream(invoiceNumber, NameFormatter.NamePostfix.xml);
    }

    @Override
    public InputStream viewPdfInvoice(String invoiceNumber) throws InvoiceNotFoundException {
        return getInvoiceStream(invoiceNumber, NameFormatter.NamePostfix.pdf);
    }

    @Override
    public InputStream viewMergedInvoices(List<String> invoiceNumbers)
            throws InvoiceNotFoundException, PdfMergeToolException {
        List<File> invoiceFiles = new ArrayList<>();
        for (String invoiceNumber : invoiceNumbers) {
            TargetFileInfo invoiceFileInfo = getInvoiceFileInfo(invoiceNumber, NameFormatter.NamePostfix.pdf);
            invoiceFiles.add(invoiceFileInfo.getTargetFile(false));
        }
        File output = null;
        InputStream result = null;
        try {
            output = File.createTempFile("mergedInvoices", ".pdf");
            pdfMergeTool.mergePdfFiles(invoiceFiles, output);
            result = new ByteArrayInputStream(FileUtils.readFileToByteArray(output));
        } catch (IOException e) {
            throw new PdfMergeToolException(e);
        } finally {
            if (output != null) {
                output.delete();
            }
        }
        return result;
    }

    @Override
    public void sendEmailWithInvoices(String invoiceNumber, AuthenticatedUser user) throws InvoiceEmailException {
        sendEmailWithInvoices(EmailTemplateNamesEnum.SEND_INVOICE.getId(), invoiceNumber, user, false);
    }

    @Override
    public void sendInvoicingSummaryEmail(String invoiceNumber, AuthenticatedUser user) {
        try {
            Invoice invoice = getInvoiceByNumber(invoiceNumber);
            NicHandle billingNH = nicHandleSearchService.getNicHandle(invoice.getBillingNicHandle());
            InvoiceEmailParameters parameters = new InvoiceEmailParameters(billingNH, invoice.getInvoiceDate(),
                    user.getUsername());
            List<File> attachments = getAttachments(invoice, billingNH, true);
            PaymentMethod paymentMethod = invoice.getPaymentMethod();
            switch (paymentMethod) {
                case ADP:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_SUMMARY_ADP.getId(), parameters,
                            attachments);
                    break;
                case CC:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_SUMMARY_CC.getId(), parameters,
                            attachments);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
            }
        } catch (Exception e) {
            LOG.error("Problem with sending invoice summary email for invoice number: " + invoiceNumber, e);
        }
    }

    private InputStream getInvoiceStream(String invoiceNumber, NameFormatter.NamePostfix postfix)
            throws InvoiceNotFoundException {
        TargetFileInfo invoiceFileInfo = getInvoiceFileInfo(invoiceNumber, postfix);
        try {
            InputStream stream = FileUtils.openInputStream(invoiceFileInfo.getTargetFile(false));
            return stream;
        } catch (IOException e) {
            LOG.error("Error when trying to read invoice file : " + invoiceFileInfo.toString(), e);
            throw new InvoiceNotFoundException("Problem wile reading invoice file : " + invoiceFileInfo.toString());
        }
    }

    private TargetFileInfo getInvoiceFileInfo(String invoiceNumber, NameFormatter.NamePostfix postfix)
            throws InvoiceNotFoundException {
        Invoice invoice = getInvoiceByNumber(invoiceNumber);
        InvoiceExportConfiguration exportConfiguration = getExportConfiguration(postfix);
        String formattedName = NameFormatter.getFormattedName(invoice.getInvoiceNumber(), postfix);
        return exportConfiguration.archiveFileConfig(formattedName, invoice.getInvoiceDate());
    }

    private Invoice getInvoiceByNumber(String invoiceNumber) throws InvoiceNotFoundException {
        Invoice invoice = invoiceDAO.getByNumber(invoiceNumber);
        if (invoice == null) {
            throw new InvoiceNotFoundException(null, invoiceNumber);
        }
        return invoice;
    }

    private InvoiceExportConfiguration getExportConfiguration(NameFormatter.NamePostfix postfix) {
        switch (postfix) {
            case xml:
                return (InvoiceExportConfiguration) applicationConfig.getXmlInvoiceExportConfig();
            case pdf:
                return (InvoiceExportConfiguration) applicationConfig.getPdfInvoiceExportConfig();
            default:
                throw new IllegalArgumentException("Invalid file type : " + postfix);
        }
    }

    private void sendEmailWithInvoices(int templateId, String invoiceNumber, AuthenticatedUser user,
            boolean useLocalStorage) throws InvoiceEmailException {
        try {
            Invoice invoice = getInvoiceByNumber(invoiceNumber);
            NicHandle billingNH = nicHandleSearchService.getNicHandle(invoice.getBillingNicHandle());
            List<File> attachments = getAttachments(invoice, billingNH, useLocalStorage);
            InvoiceEmailParameters parameters = new InvoiceEmailParameters(billingNH, invoice.getInvoiceDate(),
                    user.getUsername());
            emailTemplateSender.sendEmail(templateId, parameters, attachments);
        } catch (Exception e) {
            throw new InvoiceEmailException(e);
        }
    }

    private List<File> getAttachments(Invoice invoice, NicHandle billingNH, boolean useLocal)
            throws FileNotFoundException {
        boolean includePdf;
        boolean includeXml;
        if (nicHandleSearchService.isNicHandleDirect(billingNH)) {
            includePdf = true;
            includeXml = false;
        } else {//is registrar
            ResellerDefaults defaults;
            try {
                defaults = resellerDefaultsService.get(billingNH.getNicHandleId());
                switch (defaults.getEmailInvoiceFormat()) {
                    case XML:
                        includePdf = false;
                        includeXml = true;
                        break;
                    case PDF:
                        includePdf = true;
                        includeXml = false;
                        break;
                    case BOTH:
                        includePdf = true;
                        includeXml = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid email invoice format: "
                                + defaults.getEmailInvoiceFormat());
                }
            } catch (DefaultsNotFoundException e) {
                LOG.info("Defaults not found for registrar: " + billingNH.getNicHandleId());
                includePdf = true;
                includeXml = false;
            }
        }
        List<File> attachments = new ArrayList<>();
        if (includePdf) {
            File pdfFile = getAttachment(invoice, NameFormatter.NamePostfix.pdf, useLocal);
            attachments.add(pdfFile);
        }
        if (includeXml) {
            File xmlFile = getAttachment(invoice, NameFormatter.NamePostfix.xml, useLocal);
            attachments.add(xmlFile);
        }
        return attachments;
    }

    private File getAttachment(Invoice invoice, NameFormatter.NamePostfix postfix, boolean useLocal)
            throws FileNotFoundException {
        InvoiceExportConfiguration exportConfiguration = getExportConfiguration(postfix);
        String formattedName = NameFormatter.getFormattedName(invoice.getInvoiceNumber(), postfix);
        TargetFileInfo config;
        if (useLocal) {
            config = exportConfiguration.fileConfig(formattedName, invoice.getInvoiceDate());
        } else {
            config = exportConfiguration.archiveFileConfig(formattedName, invoice.getInvoiceDate());
        }
        File file = config.getTargetFile(false);
        if (!file.isFile()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        return file;
    }

    private int createInvoice(String nicHandleId, String CRSRevision, BigDecimal totalCost, BigDecimal totalNetAmount,
            BigDecimal totalVatAmount) throws NicHandleNotFoundException {
        Date currentDate = new Date();
        int invoiceNumber = invoiceNumberService.getNextInvoiceNumber(currentDate);
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        Invoice newInvoice = Invoice
                .newInstance(invoiceNumber, nicHandle.getAccount().getName(), nicHandle.getAccount().getId(),
                        nicHandle.getAddress(), null, null, nicHandleId, nicHandle.getCountry(),
                        nicHandle.getCounty(), CRSRevision, currentDate, "TODO: MD5", totalCost,
                        totalNetAmount, totalVatAmount);
        LOG.info("Creating new invoice, number=" + invoiceNumber);
        int id = invoiceDAO.createInvoice(newInvoice);
        LOG.info("Invoice created, number=" + invoiceNumber + ", id=" + id);
        return id;
    }

    private void associateTransactionsWithInvoice(int invoiceId, List<Transaction> transactions)
            throws TransactionNotFoundException {
        for (Transaction transaction : transactions) {
            associateTransactionWithInvoice(transaction.getId(), invoiceId);
        }
    }

    private void associateTransactionWithInvoice(long transactionId, int invoiceId)
            throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            if (transaction.getInvoiceId() != null) {
                throw new IllegalStateException("Transaction already associated with invoice! transactionId="
                        + transaction.getId());
            }
            transaction.setInvoiceId(invoiceId);
            transactionDAO.update(transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private void moveToHistory(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            if (transactionDAO.lock(t.getId())) {
                Transaction transaction = transactionDAO.get(t.getId());
                transactionHistDAO.create(transaction);
                for (Reservation r : transaction.getReservations()) {
                    if (reservationDAO.lock(r.getId())) {
                        Reservation reservation = reservationDAO.get(r.getId());
                        reservationHistDAO.create(reservation);
                        reservationDAO.deleteById(reservation.getId());
                    }
                }
                transactionDAO.deleteById(transaction.getId());
            }
        }
    }

}
