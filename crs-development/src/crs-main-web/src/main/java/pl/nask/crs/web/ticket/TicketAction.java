package pl.nask.crs.web.ticket;

import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketInfo;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.app.utils.ValidationHelper;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.IpFailureReason;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

/**
 * Base class for Ticket actions.
 *
 * @author Artur Gniadzik
 * @author Patrycja Wegrzynowicz
 */
public abstract class TicketAction extends AuthenticatedUserAwareAction {

    private Logger log = Logger.getLogger(this.getClass());

    private final ApplicationConfig config;

    // action results
    public static final String SEARCH = "search";
    public static final String REVISE = "revise";
    public static final String ALTER_STATUS = "alterstatus";
    public static final String ACCEPT = "accept";
    public static final String REJECT = "reject";
    public static final String EDIT = "edit";
    public static final String EMAIL_ERROR = "emailerror";

    public TicketAction(ApplicationConfig config) {
        this.config = config;
    }

    // ticket id
    protected Long id;

    // factory for getting list of possible domain holder classes
    private EntityService entityService;

    private AccountSearchService accountSearchService;

    private DnsCheckService dnsCheckService;

    // service allowing to look up for the ticket history
    private TicketAppService ticketAppService;

    protected TicketModel ticketModel;

    private TicketWrapper ticketWrapper;

    protected ValidationHelper validationHelper = new ValidationHelper(this);

    public List<EntityClass> getTicketClasses() {
        return entityService.getClasses();
    }

    public List<EntityCategory> getTicketCategories() {
        return entityService.getCategories();
    }

    public void setDnsCheckService(DnsCheckService dnsCheckService) {
        this.dnsCheckService = dnsCheckService;
    }

    public Ticket getTicket() {
        return ticketModel.getTicket();
    }

    public TicketInfo getInfo() {
        return ticketModel.getAdditionalInfo();
    }

    public List<HistoricalObject<Ticket>> getTicketHistory() {
        return ticketModel.getHistory();
    }

    public TicketWrapper getTicketWrapper() {
        if (ticketWrapper == null)
            ticketWrapper = new TicketWrapper(getTicket(), accountSearchService, entityService);
        return ticketWrapper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    * returns id of the edited ticket (needed when chaining to the
    * TicketSearchAction)
    */
    public long getPreviousTicketId() {
        return id;
    }

    public EntityService getEntityService() {
        return entityService;
    }

    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    public void setAccountSearchService(AccountSearchService accountSearchService) {
        this.accountSearchService = accountSearchService;
    }

    public TicketAppService getTicketAppService() {
        return ticketAppService;
    }

    public void setTicketAppService(TicketAppService ticketAppService) {
        this.ticketAppService = ticketAppService;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }

    public boolean isModification() {
        final DomainOperation.DomainOperationType type = getTicket().getOperation().getType();
        return type == DomainOperation.DomainOperationType.XFER || type == DomainOperation.DomainOperationType.MOD;
    }

    protected void refresh() throws Exception {
        fetchTicket();
    }

    public String input() throws Exception {
        fetchTicket();
        return super.input();
    }

    abstract protected TicketModel getTicketModel(long id) throws AccessDeniedException, TicketNotFoundException;

    protected void fetchTicket() throws Exception {
        log.debug("Fetching ticket with id==" + id);
        if (id == null) {// fatal error!!!
            log.error("Ticket id is missing!!!");
            ticketModel = null;
            throw new IllegalStateException("Ticket id is missing");
        }
        ticketModel = getTicketModel(id);
        ticketWrapper = null;
    }

    protected void log() {
        log.debug("Ticket id: " + id);
        log.debug("Ticket: " + ticketModel.getTicket());
        log.debug("Name servers: " + getTicketWrapper().getCurrentNameserverWrappers());
    }

    /**
     * forwards to the REVISE view.
     *
     * @return always REVISE.
     */
    public String revise() {
        log();
        return REVISE;
    }

    /**
     * forwards to the EDIT view
     *
     * @return always EDIT
     */
    public String edit() {
        log();
        return EDIT;
    }

    /**
     * forwards to the SEARCH view
     *
     * @return always SEARCH
     */
    public String search() {
        log();
        return SEARCH;
    }

    /*
    * JSP helpers
    */

    public List<FailureReason> getNameserverNameFailureReasons() {
        return Arrays.asList(FailureReason.values());
    }

    public List<IpFailureReason> getNameserverIpFailureReasons() {
        return Arrays.asList(IpFailureReason.values());
    }

    public boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public String makeContactInfo(Contact contact) {
        return ContactHelper.makeContactInfo(contact);
    }

    public Date getTicketDeletionDate() {
        Date ticketCrDate = getTicketModel().getTicket().getCreationDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ticketCrDate);
        calendar.add(Calendar.DATE, config.getTicketExpirationPeriod());
        return calendar.getTime();
    }

    @SkipValidation
    public String dnsCheck() {
        try {
            final String domainName = getTicket().getOperation().getDomainNameField().getNewValue();
            List<Nameserver> namservers = new ArrayList<>();
            for (NameserverWrapper ns : getTicketWrapper().getCurrentNameserverWrappers()) {
                final String name = ns.getName();
                if (name != null && !name.isEmpty()) {
                    namservers.add(new Nameserver(name, ns.getIpv4(), ns.getIpv6()));
                }
            }
            dnsCheckService.check(Arrays.asList(domainName), namservers, getUser().getUsername(), true);
            addActionMessage("DNS Check completed with no errors");
            return SUCCESS;
        } catch (DnsCheckProcessingException e) {
            addActionError("Dns check ended with an error: " + StringEscapeUtils.escapeHtml(e.getMessage()));
            LOG.info("DNS check error", e);
            return ERROR;
        } catch (HostNotConfiguredException e) {
            addActionError("DNS Check failed: <pre>" + StringEscapeUtils.escapeHtml(e.getFullOutputMessage())
                    + "</pre>");
            return ERROR;
        }
    }
}
