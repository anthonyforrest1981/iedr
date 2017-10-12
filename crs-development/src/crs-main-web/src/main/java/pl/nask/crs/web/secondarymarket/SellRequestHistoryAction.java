package pl.nask.crs.web.secondarymarket;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.SellRequestStatus;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class SellRequestHistoryAction extends
        GenericSearchAction<HistoricalObject<SellRequest>, HistoricalSellRequestSearchCriteria> {

    private final AccountSearchService accountSearchService;

    private final List<SellRequestStatus> allowedStatuses = Arrays.asList(SellRequestStatus.CANCELLED,
            SellRequestStatus.COMPLETED);

    public SellRequestHistoryAction(final SecondaryMarketAppService secondaryMarketAppService,
            AccountSearchService accountSearchService) {
        super(new AppSearchService<HistoricalObject<SellRequest>, HistoricalSellRequestSearchCriteria>() {
            public LimitedSearchResult<HistoricalObject<SellRequest>> search(AuthenticatedUser user,
                    HistoricalSellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                if (orderBy == null) {
                    orderBy = Arrays.asList(new SortCriterion("sellHistChangeDate", true), new SortCriterion(
                            "sellHistChangeId", true));
                }
                return secondaryMarketAppService.findHistoricalSellRequests(user, criteria, offset, limit, orderBy);
            }
        });
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected HistoricalSellRequestSearchCriteria createSearchCriteria() {
        HistoricalSellRequestSearchCriteria criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setStatuses(allowedStatuses);
        return criteria;
    }

    @Override
    protected void updateSearchCriteria(HistoricalSellRequestSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getStatuses())) {
            searchCriteria.setStatuses(allowedStatuses);
        }
        searchCriteria.removeEmptyStrings();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<SellRequestStatus> getStatuses() {
        return allowedStatuses;
    }

}
