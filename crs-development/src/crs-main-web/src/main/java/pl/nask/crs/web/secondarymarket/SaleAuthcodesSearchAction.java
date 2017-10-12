package pl.nask.crs.web.secondarymarket;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class SaleAuthcodesSearchAction extends GenericSearchAction<BuyRequest, BuyRequestSearchCriteria> {
    private final ApplicationConfig config;
    private final AccountSearchService accountSearchService;
    private final SecondaryMarketAppService secondaryMarketAppService;

    private long id;

    public SaleAuthcodesSearchAction(final SecondaryMarketAppService secondaryMarketAppService,
            AccountSearchService accountSearchService, ApplicationConfig config) {
        super(new AppSearchService<BuyRequest, BuyRequestSearchCriteria>() {
            public LimitedSearchResult<BuyRequest> search(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                if (orderBy == null) {
                    orderBy = Collections.singletonList(new SortCriterion("buyId", true));
                }
                return secondaryMarketAppService.findBuyRequests(user, criteria, offset, limit, orderBy);
            }
        });
        Validator.assertNotNull(secondaryMarketAppService, "secondaryMarketAppService");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.config = config;
        this.secondaryMarketAppService = secondaryMarketAppService;
        this.accountSearchService = accountSearchService;
    }

    public String resend() throws Exception {
        secondaryMarketAppService.regenerateAndResendAuthCode(getUser(), id);
        addActionMessage("Authcode regenerated and sent successfully");
        return search();
    }

    public String invalidate() throws Exception {
        secondaryMarketAppService.invalidateBuyRequest(getUser(), id);
        addActionMessage("Request deleted");
        return search();
    }

    @Override
    protected BuyRequestSearchCriteria createSearchCriteria() {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setStatus(BuyRequestStatus.PASSED);
        return criteria;
    }

    @Override
    protected void updateSearchCriteria(BuyRequestSearchCriteria criteria) {
        criteria.removeEmptyStrings();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public boolean allowInvalidate(BuyRequest r) throws Exception {
        return !secondaryMarketAppService.isBuyRequestUsedInSale(getUser(), r.getId());
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date authcodeExpirationDate(Date d) {
        return DateUtils.addDays(d, config.getSecondaryMarketAuthcodeExpirationPeriod());
    }
}
