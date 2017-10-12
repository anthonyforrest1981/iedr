package pl.nask.crs.web.reports;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.ExtendedDeposit;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositActualBalanceAction extends GenericSearchAction<ExtendedDeposit, DepositSearchCriteria> {

    private final static List<Long> excludedAccountsIds = Arrays.asList(1L, 2L, 104L, 105L, 108L, 115L, 122L, 125L,
            138L, 143L, 151L, 226L, 242L, 243L, 246L, 251L, 303L, 312L, 318L, 999L);

    private final AccountSearchService accountSearchService;

    private boolean showAll;

    public DepositActualBalanceAction(final PaymentAppService paymentAppService,
            AccountSearchService accountSearchService) {
        super(new AppSearchService<ExtendedDeposit, DepositSearchCriteria>() {
            public LimitedSearchResult<ExtendedDeposit> search(AuthenticatedUser user, DepositSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findDeposits(user, criteria, offset, limit, orderBy);
            }
        });
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected DepositSearchCriteria createSearchCriteria() {
        return new DepositSearchCriteria();
    }

    protected void updateSearchCriteria(DepositSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getNicHandleId())) {
            searchCriteria.setNicHandleId(null);
        }
        if (Validator.isEmpty(searchCriteria.getAccountBillNH()))
            searchCriteria.setAccountBillNH(null);
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

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion(excludedAccountsIds,
                Arrays.asList(new SortCriterion("name", true)));
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(excludedAccountsIds,
                Arrays.asList(new SortCriterion("billingContactId", true)));
    }

}
