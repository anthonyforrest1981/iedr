package pl.nask.crs.web.ticket;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketHistorySearchService;
import pl.nask.crs.web.GenericSearchAction;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketsHistorySearchAction extends
        GenericSearchAction<HistoricalObject<Ticket>, HistoricTicketSearchCriteria> {

    private final EntityService entityService;

    public TicketsHistorySearchAction(AccountSearchService accountSearchService,
            final TicketHistorySearchService ticketHistorySearchService, EntityService entityService) {
        super(new AppSearchService<HistoricalObject<Ticket>, HistoricTicketSearchCriteria>() {
            public LimitedSearchResult<HistoricalObject<Ticket>> search(AuthenticatedUser user,
                    HistoricTicketSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                validateLoggedIn(user);
                return ticketHistorySearchService.findTicketHistory(criteria, offset, limit, orderBy);
            }
        });

        Validator.assertNotNull(entityService, "class service");
        this.entityService = entityService;

        Validator.assertNotNull(accountSearchService, "accountSearchService");
        this.accountSearchService = accountSearchService;
    }

    private AccountSearchService accountSearchService;

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    @Override
    protected void updateSearchCriteria(HistoricTicketSearchCriteria searchCriteria) {
        Long lall = -1l;
        if ("".equals(searchCriteria.getDomainHolder()))
            searchCriteria.setDomainHolder(null);
        if ("".equals(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
    }

    @Override
    protected HistoricTicketSearchCriteria createSearchCriteria() {
        return new HistoricTicketSearchCriteria();
    }

    public List<EntityClass> getClassList() {
        return entityService.getClasses();
    }

    public List<EntityCategory> getCategoryList() {
        return entityService.getCategories();
    }
}
