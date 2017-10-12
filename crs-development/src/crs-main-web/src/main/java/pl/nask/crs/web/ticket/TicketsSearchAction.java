package pl.nask.crs.web.ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.UserSearchCriteria;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.web.GenericSearchAction;

/**
 * Action for tickets SEARCH. Action keep it's state (search criteria, result
 * table state) in the session.
 * <p/>
 * Action methods:
 * <ul>
 * <li>{@link #input()}</li>
 * <li>{@link #search()}</li>
 * </ul>
 *
 * @author Patrycja Wegrzynowicz, Artur Gniadzik
 */
public class TicketsSearchAction extends GenericSearchAction<Ticket, TicketSearchCriteria> {

    private List<Account> accounts = new ArrayList<>();

    private List<User> hostmasters = new ArrayList<>();

    private List<EntityCategory> categoryList = new ArrayList<>();

    private List<EntityClass> classList = new ArrayList<>();

    private Ticket processedTicket;
    private final TicketSearchService ticketSearchService;
    private final UserSearchService userSearchService;

    public TicketsSearchAction(TicketAppService ticketAppService, TicketSearchService ticketSearchService,
            AccountSearchService accountSearchService, UserSearchService userSearchService,
            EntityService entityService) {
        super(ticketAppService);
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        Validator.assertNotNull(userSearchService, "userSearchService");
        Validator.assertNotNull(entityService, "entityService");
        this.ticketSearchService = ticketSearchService;
        this.userSearchService = userSearchService;
        initializeDictionaries(accountSearchService, entityService);
    }

    @Override
    protected TicketSearchCriteria createSearchCriteria() {
        return new TicketSearchCriteria();
    }

    private void initializeDictionaries(AccountSearchService accountSearchService, EntityService entityService) {
        try {
            hostmasters = findHostmasters();
            accounts = accountSearchService.getAccountsWithExclusion();
            categoryList = entityService.getCategories();
            classList = entityService.getClasses();

        } catch (Exception e) {
            log.error("Error initializing dictionaries", e);
            addActionError("Error initializing dictionaries");
        }
    }

    private List<User> findHostmasters() {
        UserSearchCriteria criteria = new UserSearchCriteria();
        int nhLevelMask = Level.Hostmaster.getLevel() + Level.HostmasterLead.getLevel() +
                Level.TechnicalLead.getLevel();
        criteria.setNhLevelMask(nhLevelMask);
        SearchResult<User> result = userSearchService.find(criteria);
        return result.getResults();
    }

    @Override
    protected void updateSearchCriteria(TicketSearchCriteria searchCriteria) {

        final Long lall = -1l;

        if (lall.equals(searchCriteria.getTicketId())) {
            searchCriteria.setTicketId(null);
        }
        if ("".equals(searchCriteria.getNicHandle())) {
            searchCriteria.setNicHandle(null);
        }
        if ("".equals(searchCriteria.getDomainHolder())) {
            searchCriteria.setDomainHolder(null);
        }
        if ("".equals(searchCriteria.getDomainName())) {
            searchCriteria.setDomainName(null);
        }
        if ("".equals(searchCriteria.getCategoryName())) {
            searchCriteria.setCategoryName(null);
        }
        if ("".equals(searchCriteria.getClassName())) {
            searchCriteria.setClassName(null);
        }
        if (lall.equals(searchCriteria.getAccountId())) {
            searchCriteria.setAccountId(null);
        }

        searchCriteria.setCustomerStatus(CustomerStatus.NEW);

        super.updateSearchCriteria(searchCriteria);
    }

    /**
     * @return true, if the ticket may be checked in, false if not
     */
    public boolean allowCheckOut(Ticket t) {
        return t != null && !t.isCheckedOut();
    }

    public boolean allowCheckIn(Ticket t) {
        return t != null && t.isCheckedOut() && owns(t);
    }

    /**
     * checks, if current (logged-in) hostmaster owns the ticket given as an
     * argument
     *
     * @param t
     * @return
     */
    private boolean owns(Ticket t) {
        Contact c = t.getCheckedOutTo();
        String username = getUsername();
        if (c == null)
            return false;
        if (username == null)
            return false;

        return username.equals(c.getNicHandle());
    }

    public boolean allowReassign(Ticket t) {
        return t != null && t.isCheckedOut();
    }

    /**
     * returns ticket, which id is stored in the session and removes this id
     * from the session.
     *
     * @return ticket with id stored under TICKET_ID or null, if no ticket with
     *         such id was found
     */
    public Ticket getProcessedTicket() {
        return processedTicket;
    }

    /**
     * tries to fetch previously edited ticket
     *
     * @param tid
     */
    public void setPreviousTicketId(long tid) {
        try {
            processedTicket = ticketSearchService.getTicket(tid);
        } catch (TicketNotFoundException e) {
            addActionError(e.getMessage());
        }
    }

    public List<Ticket> getProcessedTicketsList() {
        List<Ticket> l = new ArrayList<Ticket>();
        Ticket t = getProcessedTicket();
        if (t != null)
            l.add(t);
        return l;
    }

    /**
     * @return true, if the ticket may be revised, false if not
     */
    public boolean allowRevise(Ticket t) {
        return t != null && owns(t);
    }

    public boolean allowAlterStatus(Ticket t) {
        return t != null && owns(t);
    }

    public List<AdminStatus> getAdminStatuses() {
        return Arrays.asList(AdminStatus.values());
    }

    public List<TechStatus> getTechStatuses() {
        return Arrays.asList(TechStatus.values());
    }

    public List<FinancialStatus> getFinancialStatuses() {
        return Arrays.asList(FinancialStatus.values());
    }

    public List<User> getHostmasters() {
        return hostmasters;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<EntityCategory> getCategoryList() {
        return categoryList;
    }

    public List<EntityClass> getClassList() {
        return classList;
    }

}
