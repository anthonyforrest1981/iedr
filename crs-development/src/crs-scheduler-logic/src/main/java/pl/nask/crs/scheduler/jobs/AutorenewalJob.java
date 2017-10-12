package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class AutorenewalJob extends AbstractJob {

    private PaymentAppService paymentAppService;

    public AutorenewalJob(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        paymentAppService.autorenewAll(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
