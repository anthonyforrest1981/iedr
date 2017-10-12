package pl.nask.crs.web.secondarymarket;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.UserSearchCriteria;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.web.GenericSearchAction;

public class BuyRequestsSearchAction extends GenericSearchAction<BuyRequest, BuyRequestSearchCriteria> {

    private final AccountSearchService accountSearchService;
    private final UserSearchService userSearchService;
    private List<User> hostmasters;

    public BuyRequestsSearchAction(final SecondaryMarketAppService secondaryMarketAppService,
            AccountSearchService accountSearchService, UserSearchService userSearchService) {
        super(new AppSearchService<BuyRequest, BuyRequestSearchCriteria>() {
            public LimitedSearchResult<BuyRequest> search(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                if (orderBy == null) {
                    orderBy = Arrays.asList(new SortCriterion("buyId", true));
                }
                return secondaryMarketAppService.findBuyRequests(user, criteria, offset, limit, orderBy);
            }
        });
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
        this.userSearchService = userSearchService;
    }

    @Override
    protected BuyRequestSearchCriteria createSearchCriteria() {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setStatuses(getStatuses());
        return criteria;
    }

    @Override
    protected void updateSearchCriteria(BuyRequestSearchCriteria criteria) {
        if (criteria.getStatuses() == null) {
            criteria.setStatuses(getStatuses());
        }
        criteria.removeEmptyStrings();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<BuyRequestStatus> getStatuses() {
        return BuyRequestStatus.valuesExcept(BuyRequestStatus.PASSED, BuyRequestStatus.CANCELLED);
    }

    public List<User> getHostmasters() {
        if (this.hostmasters == null) {
            UserSearchCriteria criteria = new UserSearchCriteria();
            int nhLevelMask =
                    Level.Hostmaster.getLevel() + Level.HostmasterLead.getLevel() + Level.TechnicalLead.getLevel();
            criteria.setNhLevelMask(nhLevelMask);
            SearchResult<User> result = userSearchService.find(criteria);
            this.hostmasters = result.getResults();
        }
        return this.hostmasters;
    }

    public boolean allowCheckout(BuyRequest r) {
        return r != null && r.getAuthcode() == null && !r.isCheckedOut();
    }

    public boolean allowCheckin(BuyRequest r) {
        return r != null && r.getAuthcode() == null && r.isCheckedOut() && owns(r);
    }

    public boolean allowReassign(BuyRequest r) {
        return r != null && r.getAuthcode() == null && r.isCheckedOut();
    }

    public boolean allowRevise(BuyRequest r) {
        return r != null && r.getAuthcode() == null && r.isCheckedOut() && owns(r);
    }

    private boolean owns(BuyRequest t) {
        String checkedOutTo = t.getCheckedOutTo();
        String username = getUsername();

        return username.equals(checkedOutTo);
    }
}
