package pl.nask.crs.web.domains;

import java.util.Collections;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class DomainsLockingServiceReportAction extends GenericSearchAction<Domain, DomainSearchCriteria> {

    private AccountSearchService accountSearchService;

    private boolean showAll;

    public DomainsLockingServiceReportAction(final DomainAppService domainAppService,
            AccountSearchService accountSearchService) {
        super(new AppSearchService<Domain, DomainSearchCriteria>() {
            @Override
            public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return domainAppService.searchFullWithLockingActive(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
        this.setDefaultSortBy(Collections.singletonList(new SortCriterion("lockingRenewalDate", true)));
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        return new DomainSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(DomainSearchCriteria searchCriteria) {
        Long lall = -1l;
        if (Validator.isEmpty(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if (Validator.isEmpty(searchCriteria.getNicHandle()))
            searchCriteria.setNicHandle(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
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

}
