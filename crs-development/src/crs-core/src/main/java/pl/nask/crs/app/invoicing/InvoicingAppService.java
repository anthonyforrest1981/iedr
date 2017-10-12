package pl.nask.crs.app.invoicing;

import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoicingAppService {

    void setTransactionStartedSettlement(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException, AccessDeniedException;

    void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId)
            throws TransactionNotFoundException, TransactionInvalidStateForSettlement, DomainNotFoundException,
            DomainIllegalStateException, AccessDeniedException;

    void generateInvoice(AuthenticatedUser user, String nicHandleId, List<Transaction> transactions)
            throws NicHandleNotFoundException, TransactionNotFoundException, InvoiceNotFoundException, ExportException,
            AccessDeniedException;

    boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException, NotAdmissiblePeriodException, CardPaymentException,
            NicHandleNotFoundException, AccessDeniedException;
}
