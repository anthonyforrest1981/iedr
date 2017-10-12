package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class TransactionInvalidationJob extends AbstractJob {
    private final InvoicingSupportService invoicingSupportService;

    public TransactionInvalidationJob(InvoicingSupportService invoicingSupportService) {
        this.invoicingSupportService = invoicingSupportService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        invoicingSupportService.runTransactionInvalidation(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
