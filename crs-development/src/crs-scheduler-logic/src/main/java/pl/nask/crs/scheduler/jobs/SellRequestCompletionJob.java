package pl.nask.crs.scheduler.jobs;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SellRequestCompletionJob extends AbstractJob {

    private static final Logger LOG = Logger.getLogger(SellRequestCompletionJob.class);

    private SecondaryMarketAppService secondaryMarketAppService;

    public SellRequestCompletionJob(SecondaryMarketAppService secondaryMarketAppService) {
        this.secondaryMarketAppService = secondaryMarketAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws Exception {
        List<SellRequest> sellRequests = secondaryMarketAppService.findSellRequestsToComplete(user);
        for (SellRequest request : sellRequests) {
            try {
                secondaryMarketAppService.completeSellRequest(user, request.getId());
            } catch (Exception e) {
                LOG.warn(String.format("Cannot complete sell request: %s for the domain: %s", request.getId(),
                        request.getDomainName()), e);
            }
        }
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
