package pl.nask.crs.app.invoicing;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoicingSupportService {

    void runInvoicing(AuthenticatedUser user) throws AccessDeniedException;

    void runTransactionInvalidation(AuthenticatedUser user) throws AccessDeniedException;
}
