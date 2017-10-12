package pl.nask.crs.scheduler.jobs;

import org.apache.log4j.Logger;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class AuthCodeCleanupJob extends AbstractJob {

    private static final Logger LOG = Logger.getLogger(SellRequestCompletionJob.class);

    private final DomainAppService domainAppService;
    private final SecondaryMarketAppService secondaryMarketAppService;

    public AuthCodeCleanupJob(DomainAppService domainAppService, SecondaryMarketAppService secondaryMarketAppService) {
        this.domainAppService = domainAppService;
        this.secondaryMarketAppService = secondaryMarketAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws Exception {
        for (Domain domain : domainAppService.findDomainsToAuthCodeCleanup(user)) {
            try {
                domainAppService.authCodeCleanup(user, domain.getName());
            } catch (Exception e) {
                LOG.warn(String.format("Cannot clean authcode for the domain: %s", domain.getName()), e);
            }
        }
        for (Domain domain : domainAppService.findDomainsToAuthCodePortalCleanup(user)) {
            try {
                domainAppService.authCodePortalCleanup(user, domain.getName());
            } catch (Exception e) {
                LOG.warn(String.format("Cannot clean authcode for the domain: %s", domain.getName()), e);
            }
        }
        for (Long id : secondaryMarketAppService.findBuyRequestsWithCompletedSales(user)) {
            try {
                secondaryMarketAppService.deleteBuyRequestWithCompletedSale(user, id);
            } catch (Exception e) {
                LOG.warn(String.format("Cannot delete buy request: %s", id), e);
            }
        }
        for (BuyRequest buyRequest : secondaryMarketAppService.findBuyRequestsWithExpiredAuthCode(user)) {
            try {
                secondaryMarketAppService.deleteBuyRequestWithExpiredAuthCode(user, buyRequest.getId());
            } catch (Exception e) {
                LOG.warn(String.format("Cannot delete buy request: %s", buyRequest.getId()), e);
            }
        }
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
