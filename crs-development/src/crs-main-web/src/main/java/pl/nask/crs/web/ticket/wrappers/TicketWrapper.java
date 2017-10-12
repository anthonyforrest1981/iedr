package pl.nask.crs.web.ticket.wrappers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class TicketWrapper {
    private Ticket ticket;
    private final AccountSearchService accountSearchService;
    private final EntityService entityService;

    // values inside the ticket (possibly not saved to the db, only in ticket object in session)
    private List<NameserverWrapper> currentNameserverWrappers;
    // values from the form
    private List<NameserverWrapper> newNameserverWrappers = new LinkedList<NameserverWrapper>();
    private StringDomainFieldChangeWrapper domainHolder;
    private StringDomainFieldChangeWrapper domainName;
    private ContactDomainFieldChangeWrapper adminContact1;
    private ContactDomainFieldChangeWrapper adminContact2;
    private ContactDomainFieldChangeWrapper billingContact;
    private ContactDomainFieldChangeWrapper techContact;
    private AccountDomainFieldChangeWrapper resellerAccount;
    private ClassFieldChangeWrapper domainHolderClass;
    private CategoryFieldChangeWrapper domainHolderCategory;
    private SubcategoryFieldChangeWrapper domainHolderSubcategory;

    public TicketWrapper(Ticket ticket, AccountSearchService accountSearchService,
            EntityService entityService) {
        this.ticket = ticket;
        this.accountSearchService = accountSearchService;
        this.entityService = entityService;
    }

    public StringDomainFieldChangeWrapper getDomainHolder() {
        if (domainHolder == null)
            domainHolder = new StringDomainFieldChangeWrapper(ticket.getOperation().getDomainHolderField(),
                    ticket.getOperation().getType());
        return domainHolder;
    }

    public StringDomainFieldChangeWrapper getDomainName() {
        if (domainName == null)
            domainName = new StringDomainFieldChangeWrapper(ticket.getOperation().getDomainNameField(),
                    ticket.getOperation().getType());
        return domainName;
    }

    public ContactDomainFieldChangeWrapper getAdminContact1() {
        if (adminContact1 == null)
            adminContact1 = new ContactDomainFieldChangeWrapper(ticket.getOperation().getAdminContactsField().get(0),
                    ticket.getOperation().getType());
        return adminContact1;
    }

    public ContactDomainFieldChangeWrapper getAdminContact2() {
        if (adminContact2 == null)
            adminContact2 = new ContactDomainFieldChangeWrapper(ticket.getOperation().getAdminContactsField().get(1),
                    ticket.getOperation().getType());
        return adminContact2;
    }

    public ContactDomainFieldChangeWrapper getTechContact() {
        if (techContact == null)
            techContact = new ContactDomainFieldChangeWrapper(ticket.getOperation().getTechContactsField().get(0),
                    ticket.getOperation().getType());
        return techContact;
    }

    public ContactDomainFieldChangeWrapper getBillingContact() {
        if (billingContact == null)
            billingContact = new ContactDomainFieldChangeWrapper(ticket.getOperation().getBillingContactsField().get(0),
                    ticket.getOperation().getType());
        return billingContact;
    }

    public AccountDomainFieldChangeWrapper getResellerAccount() {
        if (resellerAccount == null)
            resellerAccount = new AccountDomainFieldChangeWrapper(ticket.getOperation().getResellerAccountField(),
                    accountSearchService, ticket.getOperation().getType());
        return resellerAccount;
    }

    public ClassFieldChangeWrapper getDomainHolderClass() {
        if (domainHolderClass == null)
            domainHolderClass = new ClassFieldChangeWrapper(ticket.getOperation().getDomainHolderClassField(),
                    ticket.getOperation().getType(), entityService);
        return domainHolderClass;
    }

    public CategoryFieldChangeWrapper getDomainHolderCategory() {
        if (domainHolderCategory == null)
            domainHolderCategory = new CategoryFieldChangeWrapper(ticket.getOperation().getDomainHolderCategoryField(),
                    ticket.getOperation().getType(), entityService);
        return domainHolderCategory;
    }

    public SubcategoryFieldChangeWrapper getDomainHolderSubcategory() {
        if (domainHolderSubcategory == null)
            domainHolderSubcategory = new SubcategoryFieldChangeWrapper(
                    ticket.getOperation().getDomainHolderSubcategoryField(),
                    ticket.getOperation().getType(), entityService);
        return domainHolderSubcategory;
    }

    public List<NameserverWrapper> getCurrentNameserverWrappers() {
        if (currentNameserverWrappers == null) {
            List<NameserverChange> nameservers = ticket.getOperation().getNameserversField();
            currentNameserverWrappers = new ArrayList<>();
            for (NameserverChange ns : nameservers) {
                currentNameserverWrappers.add(new NameserverWrapper(ns));
            }
        }
        return currentNameserverWrappers;
    }

    public List<NameserverWrapper> getNewNameserverWrappers() {
        return newNameserverWrappers;
    }

    public void updateNameserversWithNewValues() {
        // try to match existing nameservers with new ones:
        CollectionUtils.filter(newNameserverWrappers, PredicateUtils.notNullPredicate());
        List<NameserverChange> newChangeList = new ArrayList<NameserverChange>(newNameserverWrappers.size());
        List<NameserverChange> currentChangeList = ticket.getOperation().getNameserversField();
        for (NameserverWrapper newWrapper : newNameserverWrappers) {
            NameserverChange change = (NameserverChange) CollectionUtils.find(currentChangeList,
                    lookFor(newWrapper.getName()));
            if (change != null) {
                change.getName().setNewValue(newWrapper.getName());
                change.getName().setFailureReason(newWrapper.getNameFr());
                change.getIpv4Address().setNewValue(newWrapper.getIpv4());
                change.getIpv4Address().setFailureReason(newWrapper.getIpv4Fr());
                change.getIpv6Address().setNewValue(newWrapper.getIpv6());
                change.getIpv6Address().setFailureReason(newWrapper.getIpv6Fr());
            } else {
                change = new NameserverChange(
                        new SimpleDomainFieldChange<>(null, newWrapper.getName(), newWrapper.getNameFr()),
                        new IpFieldChange(null, newWrapper.getIpv4(), newWrapper.getIpv4Fr()),
                        new IpFieldChange(null, newWrapper.getIpv6(), newWrapper.getIpv6Fr()));
            }
            newChangeList.add(change);
        }
        ticket.getOperation().setNameserversField(newChangeList);
        currentNameserverWrappers = null;
    }

    public void updateNameserversWithNewFailureReasons() {
        CollectionUtils.filter(newNameserverWrappers, PredicateUtils.notNullPredicate());
        List<NameserverChange> currentChangeList = ticket.getOperation().getNameserversField();

        for (int i = 0; i < Math.min(newNameserverWrappers.size(), currentChangeList.size()); i++) {
            final NameserverWrapper newWrapper = newNameserverWrappers.get(i);
            final NameserverChange change = currentChangeList.get(i);
            if (change != null) {
                change.getName().setFailureReason(newWrapper.getNameFr());
                change.getIpv4Address().setFailureReason(newWrapper.getIpv4Fr());
                change.getIpv6Address().setFailureReason(newWrapper.getIpv6Fr());
            }
        }
        currentNameserverWrappers = null;
    }

    private Predicate lookFor(final String domainName) {
        return new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((NameserverChange) o).getName().getNewValue().equalsIgnoreCase(domainName);
            }
        };
    }

    public boolean isRegistration() {
        return ticket.getOperation().getType() == DomainOperationType.REG;
    }

    public String getNumberOfYears() {
        String res = null;
        try {
            /*
             * return number of days if (requirement from Billy):
             *
             * T_Type = 'R' AND Admin_Status = 1 AND Tech_Status = 1 and
             * A_Number >= 100
             */
            if (ticket.getOperation().getType() == DomainOperationType.REG
                    && ticket.getAdminStatus() == AdminStatus.PASSED
                    && ticket.getTechStatus() == TechStatus.PASSED
                    && ticket.getOperation().getResellerAccountField().getNewValue().getId() > 100) {
                if (ticket.getOperation().getRenewalDate() == null)
                    res = "Unknown (renewal date is empty)";
                else {
                    /*
                     * count and return number of years (requirement from
                     * Billy):
                     *
                     * $Value_To_Display = YEAR(T_Ren_Dt) - YEAR(CURDATE())
                     *
                     * AND if T_Ren_Dt - CURDATE() < 365 days then value is 1
                     */

                    Calendar c = Calendar.getInstance();
                    int thisYear = c.get(Calendar.YEAR);
                    c.setTime(ticket.getOperation().getRenewalDate());
                    res = "" + (Math.max(1, c.get(Calendar.YEAR) - thisYear));
                }

            }
        } catch (NullPointerException e) {
            //something went wrong!!!
            res = "Unknown (error occurred)";
        }
        return res;
    }

    public String getDomainPeriodInYears() {
        if (ticket.getOperation().getType() == DomainOperationType.REG
                || ticket.getOperation().getType() == DomainOperationType.XFER) {
            return "" + ticket.getDomainPeriod().getYears();
        } else {
            return null;
        }
    }

    public String getCharityCode() {
        return ticket.getCharityCode();
    }
}
