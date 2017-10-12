package pl.nask.crs.web.reports;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClassReportAction extends GenericSearchAction<DomainsPerClass, DomainsPerClassSearchCriteria> {

    private final int PAGE_SIZE = Integer.MAX_VALUE;
    private EntityService entityService;
    private AccountSearchService accountSearchService;

    public DomainsPerClassReportAction(final ReportsAppService reportsAppService,
            EntityService entityService, AccountSearchService accountSearchService) {
        super(new AppSearchService<DomainsPerClass, DomainsPerClassSearchCriteria>() {
            public LimitedSearchResult<DomainsPerClass> search(AuthenticatedUser user,
                    DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                return reportsAppService.findTotalDomainsPerClass(user, criteria, offset, limit, orderBy);
            }
        });
        this.entityService = entityService;
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected DomainsPerClassSearchCriteria createSearchCriteria() {
        return new DomainsPerClassSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(DomainsPerClassSearchCriteria searchCriteria) {
        Long lall = -1L;
        if (Validator.isEmpty(searchCriteria.getBillingNH()))
            searchCriteria.setBillingNH(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    @Override
    protected int getPageSize() {
        return PAGE_SIZE;
    }

    public List<EntityClass> getClassList() {
        return entityService.getClasses();
    }

    public List<EntityCategory> getCategoryList() {
        return entityService.getCategories();
    }

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(null,
                Arrays.asList(new SortCriterion("billingContactId", true)));
    }

}
