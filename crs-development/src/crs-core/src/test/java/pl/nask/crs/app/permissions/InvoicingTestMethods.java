package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface InvoicingTestMethods {

    void generateInvoice() throws AccessDeniedException;

    void invalidateTransactionIfNeeded() throws AccessDeniedException;

    void settleTransaction() throws AccessDeniedException;

    void setTransactionStartedSettlement() throws AccessDeniedException;

}
