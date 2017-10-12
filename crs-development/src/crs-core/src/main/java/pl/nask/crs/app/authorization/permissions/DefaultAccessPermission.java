package pl.nask.crs.app.authorization.permissions;

import java.util.*;

import pl.nask.crs.app.authorization.queries.*;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;
import pl.nask.crs.secondarymarket.dao.SellRequestDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.user.permissions.*;

public class DefaultAccessPermission extends ContextualPermission {
    private DomainDAO domainDAO;
    private InvoiceDAO invoiceDAO;
    private BuyRequestDAO buyRequestDAO;
    private SellRequestDAO sellRequestDAO;
    private NicHandleSearchService nicHandleSearchService;
    private TicketSearchService ticketSearchService;
    private Permission methodPermission;
    private Set<String> contactType = new HashSet<String>();
    {
        contactType.add("T");
        contactType.add("A");
        contactType.add("B");
    }

    public DefaultAccessPermission(String id, String name, NicHandleSearchService nicHandleSearchService) {
        this(id, new NamedPermission(name, name), nicHandleSearchService);
    }

    public DefaultAccessPermission(String id, Permission namedPermission,
            NicHandleSearchService nicHandleSearchService) {
        super(id, namedPermission.getName());
        this.nicHandleSearchService = nicHandleSearchService;
        this.methodPermission = namedPermission;
    }


    @Override
    protected boolean verifyContext(PermissionQuery query) throws PermissionDeniedException {
        if (query instanceof DomainSearchQuery) {
            return verify((DomainSearchQuery) query);
        } else if (query instanceof DomainPermissionQuery) {
            return verify((DomainPermissionQuery) query);
        } else if (query instanceof AccountPermissionQuery) {
            return verify((AccountPermissionQuery) query);
        } else if (query instanceof NicHandleQuery) {
            return verify((NicHandleQuery) query);
        } else if (query instanceof NicHandleSearchPermissionQuery) {
            return verify((NicHandleSearchPermissionQuery) query);
        } else if (query instanceof TicketPermissionQuery) {
            return verify((TicketPermissionQuery) query);
        } else if (query instanceof TransactionPermissionQuery) {
            return verify((TransactionPermissionQuery) query);
        } else if (query instanceof InvoicePermissionQuery) {
            return verify((InvoicePermissionQuery) query);
        } else if (query instanceof BuyRequestPermissionQuery) {
            return verify((BuyRequestPermissionQuery) query);
        } else if (query instanceof SellRequestPermissionQuery) {
            return verify((SellRequestPermissionQuery) query);
        } else if (query instanceof NamedPermissionQuery) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verify(BuyRequestPermissionQuery query) throws PermissionDeniedException {
        BuyRequest buyRequest = buyRequestDAO.get(query.getBuyRequestId());
        NicHandle nh = getNicHandle(query.getUser().getUsername());
        if (buyRequest == null) {
            return true;
        }
        return verifyAccessToBuyRequest(buyRequest, nh);
    }

    protected boolean verifyAccessToBuyRequest(BuyRequest buyRequest, NicHandle nh) throws PermissionDeniedException {
        return false;
    }

    private boolean verify(SellRequestPermissionQuery query) throws PermissionDeniedException {
        SellRequest sellRequest = sellRequestDAO.get(query.getSellRequestId());
        NicHandle nh = getNicHandle(query.getUser().getUsername());
        if (sellRequest == null) {
            return true;
        }
        return verifyAccessToSellRequest(sellRequest, nh);
    }

    protected boolean verifyAccessToSellRequest(SellRequest sellRequest, NicHandle nh)
            throws PermissionDeniedException {
        return false;
    }

    private boolean verify(DomainSearchQuery permission) throws PermissionDeniedException {
        String username = permission.getUser().getUsername();
        return username.equals(permission.getBillingNH()) || verifyAccessToAccount(permission.getAccountId(), username);
    }

    private boolean verify(TicketPermissionQuery permission) throws PermissionDeniedException {
        try {
            Ticket ticket = getTicketForQuery(permission);
            return verifyAccessToTicket(permission.getUser(), ticket);
        } catch (TicketNotFoundException e) {
            return true;
        } catch (TooManyTicketsException e) {
            return false;
        }
    }

    private Ticket getTicketForQuery(TicketPermissionQuery permission)
            throws TicketNotFoundException, TooManyTicketsException {
        if (permission.getTicketId() != null) {
            return ticketSearchService.getTicket(permission.getTicketId());
        } else if (permission.getDomainName() != null) {
            return ticketSearchService.getTicketForDomain(permission.getDomainName());
        } else {
            return null;
        }
    }

    protected boolean verifyAccessToTicket(AuthenticatedUser user, Ticket ticket) throws PermissionDeniedException {
        if (ticket == null) {
            return true;
        }
        return Validator.isEqual(user.getUsername(), ticket.getCreator().getNicHandle());
    }

    protected NicHandle getNicHandle(String nicHandleId) throws PermissionDeniedException {
        try {
            return nicHandleSearchService.getNicHandle(nicHandleId);
        } catch (NicHandleNotFoundException e) {
            throw new PermissionDeniedException(String.format("NicHandle %s not found", nicHandleId), e);
        }
    }

    private boolean verify(AccountPermissionQuery query) throws PermissionDeniedException {
        long accountId = query.getAccountId();
        String nicHandleId = query.getNicHandleId();
        return verifyAccessToAccount(accountId, nicHandleId);
    }

    protected boolean verifyAccessToAccount(long accountId, String nicHandleId) throws PermissionDeniedException {
        return false;
    }

    private boolean verify(NicHandleQuery query) throws PermissionDeniedException {
        if (Validator.isEqual(query.getUser().getUsername(), query.getNicHandleId())) {
            return true;
        }
        if (query.getNicHandle() == null) {
            try {
                NicHandle nicHandle = nicHandleSearchService.getNicHandle(query.getNicHandleId());
                return verifyAccessToNicHandle(query.getUser(), nicHandle);
            } catch (NicHandleNotFoundException e) {
                return true;
            }
        } else {
            return verifyAccessToNicHandle(query.getUser(), query.getNicHandle());
        }
    }

    protected boolean verifyAccessToNicHandle(AuthenticatedUser user, NicHandle nicHandle)
            throws PermissionDeniedException {
        if (user.getUsername().equalsIgnoreCase(nicHandle.getCreator())) {
            return true;
        } else {
            return nhIsAContactTheSameDomain(user.getUsername(), nicHandle.getNicHandleId());
        }
    }

    private boolean verify(NicHandleSearchPermissionQuery permission) throws PermissionDeniedException {
        return verifyAccessToSearchForNicHandle(permission.getUser(), permission.getAccountNumber(),
                permission.getCreator());
    }

    protected boolean verifyAccessToSearchForNicHandle(AuthenticatedUser user, Long accountNumber, String creator)
            throws PermissionDeniedException {
        return validateAccountNumber(user, accountNumber);
    }

    protected boolean validateAccountNumber(AuthenticatedUser user, Long accountNumber)
            throws PermissionDeniedException {
        return false;
    }

    private List<Domain> getDomains(QueriedDomains domains) {
        if (!Validator.isEmpty(domains.getDomains())) {
            return domains.getDomains();
        } else {
            return getDomains(domains.getDomainNames());
        }
    }

    private List<Domain> getDomains(List<String> domainsNames) {
        List<Domain> ret = new ArrayList<>();
        for (String name : domainsNames) {
            Domain d = domainDAO.get(name);
            ret.add(d);
        }
        return ret;
    }

    private boolean verify(DomainPermissionQuery permission) throws PermissionDeniedException {
        AuthenticatedUser user = permission.getUser();
        List<Domain> domains = getDomains(permission.getDomains());
        return verifyAccessToDomains(user, domains);
    }

    protected boolean verifyAccessToDomains(AuthenticatedUser user, List<Domain> domains)
            throws PermissionDeniedException {
        if (user == null || domains == null || domains.size() == 0) {
            return false;
        }
        NicHandle nh = getNicHandle(user.getUsername());

        for (Domain d : domains) {
            if (!verifyAccessToDomain(nh, d)) {
                return false;
            }
        }
        return true;
    }

    protected boolean verifyAccessToDomain(NicHandle nicHandle, Domain d) throws PermissionDeniedException {
        if (d == null) {
            return true;
        }
        String nicHandleId = nicHandle.getNicHandleId();
        return (contactType.contains("B") && isOnContactList(nicHandleId, d.getBillingContacts()))
                || (contactType.contains("A") && isOnContactList(nicHandleId, d.getAdminContacts()))
                || (contactType.contains("T") && isOnContactList(nicHandleId, d.getTechContacts()));
    }

    private boolean verify(InvoicePermissionQuery query) {
        AuthenticatedUser user = query.getUser();
        String invoiceNumber = query.getInvoiceNumber();
        Invoice invoice = invoiceDAO.getByNumber(invoiceNumber);
        return verifyAccessToInvoice(user, invoice);
    }

    protected boolean verifyAccessToInvoice(AuthenticatedUser user, Invoice invoice) {
        return false;
    }

    private boolean verify(TransactionPermissionQuery query) {
        AuthenticatedUser user = query.getUser();
        Transaction transaction = query.getTransaction();
        return verifyAccessToTransaction(user, transaction);
    }

    protected boolean verifyAccessToTransaction(AuthenticatedUser user, Transaction transaction) {
        if (transaction == null) {
            return true;
        }
        return transaction.getBillNicHandleId().equalsIgnoreCase(user.getUsername());
    }

    private boolean isOnContactList(String nh, List<Contact> contacts) {
        for (Contact c : contacts) {
            if (Validator.isEqual(nh, c.getNicHandle()))
                return true;
        }
        return false;
    }

    @Override
    public boolean isImplied(PermissionQuery query) {
        try {
            return methodPermission.implies(query);
        } catch (PermissionDeniedException e) {
            return false;
        }
    }

    public void setTicketSearchService(TicketSearchService ticketSearchService) {
        this.ticketSearchService = ticketSearchService;
    }

    public void setDomainDAO(DomainDAO domainDAO) {
        this.domainDAO = domainDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void setBuyRequestDAO(BuyRequestDAO buyRequestDAO) {
        this.buyRequestDAO = buyRequestDAO;
    }

    public void setSellRequestDAO(SellRequestDAO sellRequestDAO) {
        this.sellRequestDAO = sellRequestDAO;
    }

    public DomainDAO getDomainDAO() {
        return domainDAO;
    }

    public NicHandleSearchService getNicHandleSearchService() {
        return nicHandleSearchService;
    }

    public Permission getMethodPermission() {
        return methodPermission;
    }

    public void setContactType(String contactType) {
        this.contactType.clear();
        this.contactType.add(contactType);
    }

    private boolean nhIsAContactTheSameDomain(String firstNh, String secondNh) {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setNicHandle(firstNh);
        crit.setContactType(Arrays.asList(ContactType.ADMIN));
        crit.setSecondContact(secondNh);
        return domainDAO.exists(crit);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id: " + getId() + ", methodPermission: " + methodPermission + "]";
    }

    @Override
    public String getDescription() {
        if (getClass() != DefaultAccessPermission.class)
            return null;
        return "Contextual, allows to access " + getProtectedObjects() + " in combination with "
                + methodPermission.getId() + " (" + methodPermission.getDescription() + ")";
    }

    private String getProtectedObjects() {
        StringBuilder sb = new StringBuilder();
        append(sb, nicHandleSearchService != null,
                "NicHandles which are created by the user or are contacts in the same domains");
        append(sb, ticketSearchService != null, "Tickets created by the user");
        append(sb, domainDAO != null, "Domains the user is a contact (" + CollectionUtils.toString(contactType, ", ")
                + ") in");
        return sb.toString();
    }

    private void append(StringBuilder sb, boolean append, String text) {
        if (append) {
            if (sb.length() > 0)
                sb.append(", ");
            sb.append(text);
        }
    }

}
