package pl.nask.crs.web.reports;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class DomainAutorenewReportAction extends GenericSearchAction<Domain, DomainSearchCriteria> {

    private final static List<RenewalMode> renewalModes = Arrays.asList(RenewalMode.Autorenew, RenewalMode.RenewOnce);
    private boolean showAll;

    public DomainAutorenewReportAction(final DomainAppService domainAppService) {
        super(new AppSearchService<Domain, DomainSearchCriteria>() {
            @Override
            public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return domainAppService.findDomainAutorenewals(user, criteria, offset, limit, orderBy);
            }
        });
        setDefaultSortBy(Arrays.asList(new SortCriterion("billingNH", true), new SortCriterion("name", true)));
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        return new DomainSearchCriteria();
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

    public List<RenewalMode> getRenewalModes() {
        return renewalModes;
    }

}
