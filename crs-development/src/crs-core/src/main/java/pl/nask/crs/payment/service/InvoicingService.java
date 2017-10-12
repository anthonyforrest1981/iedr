package pl.nask.crs.payment.service;

import java.io.InputStream;
import java.util.List;

import pl.nask.crs.commons.pdfmerge.PdfMergeToolException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.InvoiceEmailException;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface InvoicingService {

    Invoice getInvoice(int invoiceId) throws InvoiceNotFoundException;

    List<DomainInfo> getInvoiceInfo(String invoiceNumber);

    int createInvoiceAndAssociateWithTransactions(String nicHandleId, List<Transaction> transactions)
    throws NicHandleNotFoundException, TransactionNotFoundException;

    void updateInvoice(Invoice invoice);

    LimitedSearchResult<PlainInvoice> findInvoices(PlainInvoiceSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    InputStream viewXmlInvoice(String invoiceNumber) throws InvoiceNotFoundException;

    InputStream viewPdfInvoice(String invoiceNumber) throws InvoiceNotFoundException;

    InputStream viewMergedInvoices(List<String> invoiceNumbers) throws InvoiceNotFoundException, PdfMergeToolException;

    void sendEmailWithInvoices(String invoiceNumber, AuthenticatedUser user) throws InvoiceEmailException;

    void sendInvoicingSummaryEmail(String invoiceNumber, AuthenticatedUser user);

}
