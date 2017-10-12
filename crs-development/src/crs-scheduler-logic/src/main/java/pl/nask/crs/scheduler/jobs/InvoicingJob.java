package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class InvoicingJob extends AbstractJob {

    private final InvoicingSupportService invoicingSupportService;

    public InvoicingJob(InvoicingSupportService invoicingSupportService) {
        super();
        this.invoicingSupportService = invoicingSupportService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        invoicingSupportService.runInvoicing(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
