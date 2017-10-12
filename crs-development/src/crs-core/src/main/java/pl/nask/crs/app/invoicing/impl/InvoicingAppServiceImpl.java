package pl.nask.crs.app.invoicing.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.payment.service.InvoicingService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class InvoicingAppServiceImpl implements InvoicingAppService {

    private final static Logger LOG = Logger.getLogger(InvoicingAppServiceImpl.class);
    private final InvoicingService invoicingService;
    private final PaymentService paymentService;
    private InvoiceExporter exportersChain;

    public InvoicingAppServiceImpl(InvoicingService invoicingService, PaymentService paymentService,
            InvoiceExporter exportersChain) {
        this.invoicingService = invoicingService;
        this.paymentService = paymentService;
        this.exportersChain = exportersChain;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setTransactionStartedSettlement(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException {
        paymentService.setTransactionStartedSettlement(transactionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId)
            throws TransactionNotFoundException, TransactionInvalidStateForSettlement, DomainNotFoundException,
            DomainIllegalStateException {
        paymentService.settleTransaction(user, opInfo, transactionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateInvoice(AuthenticatedUser user, String nicHandleId, List<Transaction> transactions)
            throws NicHandleNotFoundException, TransactionNotFoundException, InvoiceNotFoundException, ExportException {
        LOG.info("Create db invoice for NicHandle=" + nicHandleId);
        int invoiceId = invoicingService.createInvoiceAndAssociateWithTransactions(nicHandleId, transactions);
        LOG.info("db invoice created for NicHandle=" + nicHandleId + ", invoiceId=" + invoiceId);
        Invoice invoice = invoicingService.getInvoice(invoiceId);
        String invoiceNumber = invoice.getInvoiceNumber();
        LOG.info("Exporting invoice, number=" + invoiceNumber + " ,id=" + invoiceId);
        exportersChain.export(invoice);
        LOG.info("Exporting invoice, number=" + invoiceNumber + " ,id=" + invoiceId + " completed.");
        LOG.info("Updating export info in the db invoice, number=" + invoiceNumber + ", id=" + invoiceId);
        invoicingService.updateInvoice(invoice);
        LOG.info("Sending invoice summary email");
        invoicingService.sendInvoicingSummaryEmail(invoiceNumber, user);
        LOG.info("Finished generating invoice number=" + invoiceNumber + ", id=" + invoiceId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException, NotAdmissiblePeriodException, CardPaymentException,
            NicHandleNotFoundException {
        return paymentService.invalidateTransactionsIfNeeded(user, transactionId);
    }
}
