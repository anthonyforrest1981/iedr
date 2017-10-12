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
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class BuyRequestHistoryAction extends
        GenericSearchAction<HistoricalObject<BuyRequest>, HistoricalBuyRequestSearchCriteria> {

    private final AccountSearchService accountSearchService;

    public BuyRequestHistoryAction(final SecondaryMarketAppService secondaryMarketAppService,
            AccountSearchService accountSearchService) {
        super(new AppSearchService<HistoricalObject<BuyRequest>, HistoricalBuyRequestSearchCriteria>() {
            public LimitedSearchResult<HistoricalObject<BuyRequest>> search(AuthenticatedUser user,
                    HistoricalBuyRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                if (orderBy == null) {
                    orderBy = Arrays.asList(new SortCriterion("buyHistChangeDate", true), new SortCriterion(
                            "buyHistChangeId", true));
                }
                return secondaryMarketAppService.findHistoricalBuyRequests(user, criteria, offset, limit, orderBy);
            }
        });
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
    }

    @Override
    protected HistoricalBuyRequestSearchCriteria createSearchCriteria() {
        return new HistoricalBuyRequestSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(HistoricalBuyRequestSearchCriteria searchCriteria) {
        searchCriteria.removeEmptyStrings();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

}
