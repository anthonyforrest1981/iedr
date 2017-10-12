package pl.nask.crs.web.secondarymarket;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.time.DateUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class SellRequestsSearchAction extends GenericSearchAction<SellRequest, SellRequestSearchCriteria> {

    private final AccountSearchService accountSearchService;
    private final ApplicationConfig applicationConfig;

    public SellRequestsSearchAction(final SecondaryMarketAppService secondaryMarketAppService,
            AccountSearchService accountSearchService, ApplicationConfig applicationConfig) {
        super(new AppSearchService<SellRequest, SellRequestSearchCriteria>() {
            public LimitedSearchResult<SellRequest> search(AuthenticatedUser user, SellRequestSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                if (orderBy == null) {
                    orderBy = Arrays.asList(new SortCriterion("sellId", true));
                }
                return secondaryMarketAppService.findSellRequests(user, criteria, offset, limit, orderBy);
            }
        });
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    protected SellRequestSearchCriteria createSearchCriteria() {
        return new SellRequestSearchCriteria();
    }

    @Override
    protected void updateSearchCriteria(SellRequestSearchCriteria searchCriteria) {
        searchCriteria.removeEmptyStrings();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public Date calculateCompletionDate(Date date) {
        return DateUtils.addDays(date, applicationConfig.getSecondaryMarketCountdownPeriod());
    }

}
