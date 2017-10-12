package pl.nask.crs.payment.dao.ibatis.converters;

import java.util.Collections;
import java.util.List;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ibatis.objects.InternalInvoice;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceConverter extends AbstractConverter<InternalInvoice, Invoice> {

    private TransactionConverter transactionConverter;

    public InvoiceConverter(TransactionConverter transactionConverter) {
        this.transactionConverter = transactionConverter;
    }

    @Override
    protected Invoice _to(InternalInvoice internalInvoice) {
        final List<InternalTransaction> internalTransactions = internalInvoice.getTransactions();
        List<Transaction> transactions;
        if (internalTransactions != null) {
            transactions = transactionConverter.to(internalTransactions);
        } else {
            transactions = Collections.emptyList();
        }
        return new Invoice(internalInvoice.getId(), internalInvoice.getInvoiceNumber(),
                internalInvoice.getAccountName(), internalInvoice.getAccountNumber(), internalInvoice.getAddress1(),
                internalInvoice.getAddress2(), internalInvoice.getAddress3(), internalInvoice.getBillingNicHandle(),
                internalInvoice.getBillingNicHandleName(), internalInvoice.getCountry(), internalInvoice.getCounty(),
                internalInvoice.getCrsVersion(), internalInvoice.getInvoiceDate(), internalInvoice.getMD5(),
                internalInvoice.isCompleted(), transactions, internalInvoice.getTotalCost(),
                internalInvoice.getTotalNetAmount(), internalInvoice.getTotalVatAmount(),
                internalInvoice.getSettlementDate(), internalInvoice.getPaymentMethod());
    }

    @Override
    protected InternalInvoice _from(Invoice invoice) {
        List<InternalTransaction> transactions = transactionConverter.from(invoice.getTransactions());
        return new InternalInvoice(invoice.getId(), invoice.getInvoiceNumber(), invoice.getAccountName(),
                invoice.getAccountNumber(), invoice.getAddress1(), invoice.getAddress2(), invoice.getAddress3(),
                invoice.getBillingNicHandle(), invoice.getBillingNicHandleName(), invoice.isCompleted(),
                invoice.getCountry(), invoice.getCounty(), invoice.getCrsVersion(), invoice.getInvoiceDate(),
                invoice.getMD5(), transactions, invoice.getTotalCost(), invoice.getTotalNetAmount(),
                invoice.getTotalVatAmount());
    }
}
