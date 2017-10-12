package pl.nask.crs.app.domains.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.PushQSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class PushQSupportServiceImpl implements PushQSupportService {

    private DomainAppService domainAppService;
    private DomainSearchService domainSearchService;

    private final static Logger LOG = Logger.getLogger(PushQSupportService.class);

    public PushQSupportServiceImpl(DomainAppService domainAppService, DomainSearchService domainSearchService) {
        this.domainAppService = domainAppService;
        this.domainSearchService = domainSearchService;
    }

    @Override
    public void pushQ(AuthenticatedUser user, OpInfo opInfo) throws AccessDeniedException {
        validateLoggedIn(user);
        Date now = new Date();
        deleteDatePushQ(user, opInfo, now);
        suspensionDatePushQ(user, opInfo, now);
        renewalDatePushQ(user, opInfo);
        deleteDomainPushQ(user, opInfo);
    }

    private void deleteDomainPushQ(AuthenticatedUser user, OpInfo opInfo) throws AccessDeniedException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Deleted);
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            domainAppService.runDeleteProcess(user, opInfo, domain.getName());
        }
    }

    private void renewalDatePushQ(AuthenticatedUser user, OpInfo opInfo) throws AccessDeniedException {
        Date yesterday = DateUtils.getCurrDate(-1);
        Date yesterdayYesterday = DateUtils.getCurrDate(-2);
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        criteria.setRenewalTo(yesterday);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended);
        List<ExtendedDomain> domains = domainSearchService.findExtended(criteria, null);
        for (ExtendedDomain domain : domains) {
            boolean domainTrasferredRecently = domain.getTransferDate() != null
                    && domain.getTransferDate().after(yesterday);
            boolean domainRenewedRecently = domain.hasPendingReservations()
                    && domain.getRenewalDate().after(yesterdayYesterday);
            if (domainTrasferredRecently || domainRenewedRecently) {
                LOG.info("Domain " + domain.getName() + " has unsettled reservation, skipping");
            } else {
                domainAppService.runRenewalDatePasses(user, opInfo, domain.getName());
            }
        }
    }

    private void suspensionDatePushQ(AuthenticatedUser user, OpInfo opInfo, Date now) throws AccessDeniedException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspensionTo(now);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed);
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            domainAppService.runSuspensionDatePasses(user, opInfo, domain.getName());
        }
    }

    private void deleteDatePushQ(AuthenticatedUser user, OpInfo opInfo, Date now) throws AccessDeniedException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDeletionTo(now);
        criteria.setNrpStatuses(NRPStatus.InvoluntarySuspended, NRPStatus.VoluntarySuspended);
        List<Domain> domains = domainSearchService.findAll(criteria, null);
        for (Domain domain : domains) {
            domainAppService.runDeletionDatePasses(user, opInfo, domain.getName());
        }
    }

}
