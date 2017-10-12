package pl.nask.crs.ticket.dao.ibatis.converters;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketNameserver;
import pl.nask.crs.ticket.operation.*;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

public class TicketConverter extends AbstractConverter<InternalTicket, Ticket> {
    private GenericDAO<InternalDomain, String> domainDao;

    private boolean evaluateModification;

    public TicketConverter(GenericDAO<InternalDomain, String> domainDao) {
        Validator.assertNotNull(domainDao, "domain dao");
        this.domainDao = domainDao;
    }

    public void setEvaluateModification(boolean evaluateModification) {
        this.evaluateModification = evaluateModification;
    }

    protected Ticket _to(InternalTicket src) {
        final Contact creator = createContact(src.getCreatorNicHandle(), src.getCreatorName(), src.getCreatorEmail(),
                src.getCreatorCompanyName(), src.getCreatorCountry());
        assert creator != null : "@AssumeAssertion(nullness)";
        Ticket t = new Ticket(src.getId(), createDomainChangeOperation(src), src.getAdminStatus(),
                src.getAdminStatusChangeDate(), src.getTechStatus(), src.getTechStatusChangeDate(), src.getRequestersRemark(),
                src.getHostmastersRemark(), creator, src.getCreationDate(), src.getChangeDate(), createContact(
                        src.getCheckedOutToNicHandle(), src.getCheckedOutToName()), src.isClikPaid(),
                src.getDocumentsCount() > 0, src.getDomainPeriod() == null ? null : Period.fromYears(src
                        .getDomainPeriod()), src.getCharityCode(), src.isAutorenewMode(), src.getFinancialStatus(),
                src.getFinancialStatusChangeDate(), src.getCustomerStatus(), src.getCustomerStatusChangeDate());

        return t;
    }

    private DomainOperation createDomainChangeOperation(InternalTicket src) {
        InternalDomain cur = new InternalDomain();
        if (evaluateModification
                && (src.getType() == DomainOperationType.MOD || src.getType() == DomainOperationType.XFER)) {
            cur = domainDao.get(src.getDomainName());
        }

        if (cur == null) {
            cur = new InternalDomain();
            String comment = "Domain name does not exist";
            cur.setName(comment);
            cur.setRemark(comment);
            src.setHostmastersRemark(comment + ", " + src.getHostmastersRemark());
        }

        return new DomainOperation(src.getType(), src.getRenewalDate(), createDomainNameChangeField(src, cur),
                createDomainHolderChangeField(src, cur), createDomainHolderClassChangeField(src, cur),
                createDomainHolderCategoryChangeField(src, cur), createDomainHolderSubcategoryChangeField(src, cur),
                createResellerAccountChangeField(src, cur), createAdminContactsChangeField(src, cur),
                createTechContactsChangeField(src, cur), createBillingContactsChangeField(src, cur),
                createNameserversChangeField(src, cur));
    }

    private SimpleDomainFieldChange<String> createDomainNameChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<>(cur.getName(), src.getDomainName(), src.getDomainNameFailureReason());
    }

    private SimpleDomainFieldChange<String> createDomainHolderChangeField(InternalTicket src, InternalDomain cur) {
        return new SimpleDomainFieldChange<>(cur.getHolder(), src.getDomainHolder(), src.getDomainHolderFailureReason());
    }

    private SimpleDomainFieldChange<EntityClass> createDomainHolderClassChangeField(InternalTicket src,
            InternalDomain cur) {
        return new SimpleDomainFieldChange<>(cur.getHolderClass(), src.getDomainHolderClass(),
                src.getDomainHolderClassFailureReason());
    }

    private SimpleDomainFieldChange<EntityCategory> createDomainHolderCategoryChangeField(InternalTicket src,
            InternalDomain cur) {
        return new SimpleDomainFieldChange<>(cur.getHolderCategory(), src.getDomainHolderCategory(),
                src.getDomainHolderCategoryFailureReason());
    }

    private SimpleDomainFieldChange<EntitySubcategory> createDomainHolderSubcategoryChangeField(InternalTicket src,
            InternalDomain cur) {
        return new SimpleDomainFieldChange<>(cur.getHolderSubcategory(), src.getDomainHolderSubcategory());
    }

    private SimpleDomainFieldChange<Account> createResellerAccountChangeField(InternalTicket src, InternalDomain cur) {
        Account newAccount = new Account(src.getResellerAccountId(), src.getResellerAccountName(),
                src.getResellerAccountBillingContact());
        newAccount.setAgreementSigned(src.isResellerAccountAgreementSigned());
        newAccount.setTicketEdit(src.isResellerAccountTicketEdit());
        return new SimpleDomainFieldChange<>(cur.getResellerAccount(), newAccount,
                src.getResellerAccountFailureReason());
    }

    private List<SimpleDomainFieldChange<Contact>> createAdminContactsChangeField(InternalTicket src, InternalDomain cur) {
        List<InternalContact> contacts = cur.getAdminContacts();
        // todo: match the contacts by name?
        ContactConverter converter = new ContactConverter();
        Contact curContact1 = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        Contact curContact2 = converter.to(contacts.size() > 1 ? contacts.get(1) : null);
        SimpleDomainFieldChange<Contact> adminContact1 = new SimpleDomainFieldChange<>(curContact1, createContact(
                src.getAdminContact1NicHandle(), src.getAdminContact1Name(), src.getAdminContact1Email(),
                src.getAdminContact1CompanyName(), src.getAdminContact1Country()),
                src.getAdminContact1FailureReason());
        SimpleDomainFieldChange<Contact> adminContact2 = new SimpleDomainFieldChange<>(curContact2, createContact(
                src.getAdminContact2NicHandle(), src.getAdminContact2Name(), src.getAdminContact2Email(),
                src.getAdminContact2CompanyName(), src.getAdminContact2Country()),
                src.getAdminContact2FailureReason());
        return Arrays.asList(adminContact1, adminContact2);
    }

    private List<SimpleDomainFieldChange<Contact>> createTechContactsChangeField(InternalTicket src, InternalDomain cur) {
        List<InternalContact> contacts = cur.getTechContacts();
        ContactConverter converter = new ContactConverter();
        Contact curContact = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        SimpleDomainFieldChange<Contact> techContact = new SimpleDomainFieldChange<>(curContact, createContact(
                src.getTechContactNicHandle(), src.getTechContactName(), src.getTechContactEmail(),
                src.getTechContactCompanyName(), src.getTechContactCountry()),
                src.getTechContactFailureReason());
        return Arrays.asList(techContact);
    }

    private List<SimpleDomainFieldChange<Contact>> createBillingContactsChangeField(InternalTicket src,
            InternalDomain cur) {
        List<InternalContact> contacts = cur.getBillingContacts();
        ContactConverter converter = new ContactConverter();
        Contact curContact = converter.to(contacts.size() > 0 ? contacts.get(0) : null);
        SimpleDomainFieldChange<Contact> billingContact = new SimpleDomainFieldChange<>(curContact, createContact(
                src.getBillingContactNicHandle(), src.getBillingContactName(), src.getBillingContactEmail(),
                src.getBillingContactCompanyName(), src.getBillingContactCountry()),
                src.getBillingContactFailureReason());
        return Arrays.asList(billingContact);
    }

    private List<NameserverChange> createNameserversChangeField(InternalTicket src, InternalDomain cur) {
        // cur cannot be null here.
        if (src.getDataSetType() == InternalTicket.DataSet.SIMPLE) {
            return Collections.emptyList();
        }

        List<Nameserver> dlist = new ArrayList<Nameserver>(cur.getNameservers());
        List<NameserverChange> changeList = new ArrayList<NameserverChange>();
        for (InternalTicketNameserver internal_ticket_ns : src.getNameservers()) {
            changeList.add(makeNameserverChange(internal_ticket_ns, cur, dlist));
        }

        /*
         *  the changelist is not complete: deletion changes are not filled yet.
         *  every nameserver left on the dlist should be marked as deleted, so:
         *  1. every empty change (new value is null) should get a proper old value from the dlist
         *  2. for other nameservers left in the dlist a new nameserver change should be created.
         */

        //first check NameserverChange with not empty newValue
        for (int i = 0; i < changeList.size(); i++) {
            NameserverChange nsc = changeList.get(i);
            if (!Validator.isEmpty(nsc.getName().getNewValue()) && Validator.isEmpty(nsc.getName().getCurrentValue())) {
                if (dlist.size() > 0) {
                    // use the first from the list
                    Nameserver ns = dlist.remove(0);
                    nsc = makeNameServerChange(ns, nsc);
                    changeList.set(i, nsc);
                } else {
                    // no more nameservers left in dlist
                    break;
                }
            }
        }
        //then empty change
        for (int i = 0; i < changeList.size(); i++) {
            NameserverChange nsc = changeList.get(i);
            if (Validator.isEmpty(nsc.getName().getNewValue())) {
                if (dlist.size() > 0) {
                    // use the first from the list
                    Nameserver ns = dlist.remove(0);
                    nsc = makeNameServerChange(ns, nsc);
                    changeList.set(i, nsc);
                } else {
                    // no more nameservers left in dlist
                    break;
                }
            }
        }

        return Collections.unmodifiableList(changeList);
    }

    private NameserverChange makeNameServerChange(Nameserver ns, NameserverChange nsc) {
        return new NameserverChange(
            new SimpleDomainFieldChange<>(ns.getName(), nsc.getName().getNewValue(), nsc.getName().getFailureReason()),
            new IpFieldChange(ns.getIpv4Address(), nsc.getIpv4Address().getNewValue(), nsc.getIpv4Address().getFailureReason()),
            new IpFieldChange(ns.getIpv6Address(), nsc.getIpv6Address().getNewValue(), nsc.getIpv6Address().getFailureReason())
        );
    }

    private NameserverChange makeNameserverChange(InternalTicketNameserver ticketNs, InternalDomain cur,
            List<Nameserver> dlist) {
        String domainNsName = null;
        String domainNsIPv4 = null;
        String domainNsIPv6 = null;

        if (!Validator.isEmpty(ticketNs.getName())) {
            Nameserver ns = cur.getNameserver(ticketNs.getName());
            if (ns != null) {
                // ticket modifies entry
                domainNsName = ns.getName();
                domainNsIPv4 = ns.getIpv4Address();
                domainNsIPv6 = ns.getIpv6Address();
                // remove handled nameserver from the list so it would be possible to handle deleted entries
                dlist.remove(ns);
            }
            /*
             * else: ticket adds a new entry - this must be handled outside of this method
             */
        }
        /*
         * else: ticket deletes dns entry - this must be handled outside of this method
         */
        return new NameserverChange(
            new SimpleDomainFieldChange<>(domainNsName, ticketNs.getName(), ticketNs.getNameFailureReason()),
            new IpFieldChange(domainNsIPv4, ticketNs.getIpv4(), ticketNs.getIpv4FailureReason()),
            new IpFieldChange(domainNsIPv6, ticketNs.getIpv6(), ticketNs.getIpv6FailureReason()));
    }

    private /*>>>@Nullable*/ Contact createContact(/*>>>@Nullable*/ String nicHandle, /*>>>@Nullable*/ String name) {
        if (nicHandle == null && name != null) {
            Logger.getLogger(getClass()).warn("nic handle is null but name not null - " + name);
        }
        return nicHandle == null ? null : new Contact(nicHandle, name);
    }

    private /*>>>@Nullable*/ Contact createContact(/*>>>@Nullable*/ String nicHandle, /*>>>@Nullable*/ String name, /*>>>@Nullable*/
            String email, /*>>>@Nullable*/ String companyName, /*>>>@Nullable*/ String country) {
        if (nicHandle == null && name != null) {
            Logger.getLogger(getClass()).warn("nic handle is null but name not null - " + name);
        }
        return nicHandle == null ? null : new Contact(nicHandle, name, email, companyName, country, null);
    }

    protected InternalTicket _from(Ticket ticket) {
        InternalTicket ret = new InternalTicket();
        copy(ticket, ret);
        return ret;
    }

    public static void copy(Ticket ticket, InternalTicket internalTicket) {
        internalTicket.setId(ticket.getId());
        internalTicket.setType(ticket.getOperation().getType());
        internalTicket.setHostmastersRemark(ticket.getHostmastersRemark());
        internalTicket.setCheckedOut(ticket.isCheckedOut());
        if (ticket.isCheckedOut()) {
            internalTicket.setCheckedOutToNicHandle(ticket.getCheckedOutTo().getNicHandle());
        } else {
            internalTicket.setCheckedOutToNicHandle(null);
        }
        internalTicket.setAdminStatus(ticket.getAdminStatus());
        internalTicket.setAdminStatusChangeDate(ticket.getAdminStatusChangeDate());
        internalTicket.setTechStatus(ticket.getTechStatus());
        internalTicket.setTechStatusChangeDate(ticket.getTechStatusChangeDate());
        internalTicket.setChangeDate(ticket.getChangeDate());
        // domain name
        SimpleDomainFieldChange<String> domainName = ticket.getOperation().getDomainNameField();
        assert domainName.getNewValue() != null : "@AssumeAssertion(nullness)";
        internalTicket.setDomainName(domainName.getNewValue());
        internalTicket.setDomainNameFailureReason(domainName.getFailureReason());
        // domain holder
        SimpleDomainFieldChange<String> domainHolder = ticket.getOperation().getDomainHolderField();
        internalTicket.setDomainHolderFailureReason(domainHolder.getFailureReason());
        assert domainHolder.getNewValue() != null : "@AssumeAssertion(nullness)";
        internalTicket.setDomainHolder(domainHolder.getNewValue());
        // domain holder class
        SimpleDomainFieldChange<EntityClass> domainHolderClass = ticket.getOperation().getDomainHolderClassField();
        internalTicket.setDomainHolderClassFailureReason(domainHolderClass.getFailureReason());
        assert domainHolderClass.getNewValue() != null : "@AssumeAssertion(nullness)";
        internalTicket.setDomainHolderClass(domainHolderClass.getNewValue());
        // domain holder category
        SimpleDomainFieldChange<EntityCategory> domainHolderCategory = ticket.getOperation().getDomainHolderCategoryField();
        internalTicket.setDomainHolderCategoryFailureReason(domainHolderCategory.getFailureReason());
        assert domainHolderCategory.getNewValue() != null : "@AssumeAssertion(nullness)";
        internalTicket.setDomainHolderCategory(domainHolderCategory.getNewValue());
        // domain holder subcategory
        SimpleDomainFieldChange<EntitySubcategory> domainHolderSubcategory =
                ticket.getOperation().getDomainHolderSubcategoryField();
        internalTicket.setDomainHolderSubcategory(domainHolderSubcategory.getNewValue());
        // reseller account
        SimpleDomainFieldChange<Account> resellerAccount = ticket.getOperation().getResellerAccountField();
        internalTicket.setResellerAccountFailureReason(resellerAccount.getFailureReason());
        assert resellerAccount.getNewValue() != null : "@AssumeAssertion(nullness)";
        internalTicket.setResellerAccountId(resellerAccount.getNewValue().getId());
        // admin contacts
        List<SimpleDomainFieldChange<Contact>> adminContacts = ticket.getOperation().getAdminContactsField();
        SimpleDomainFieldChange<Contact> ac1 = getElement(adminContacts, 0);
        SimpleDomainFieldChange<Contact> ac2 = getElement(adminContacts, 1);
        if (ac1 != null) {
            internalTicket.setAdminContact1NicHandle(getNicHandle(ac1));
            internalTicket.setAdminContact1FailureReason(ac1.getFailureReason());
        } else {
            internalTicket.setAdminContact1NicHandle(null);
            internalTicket.setAdminContact1FailureReason(null);
        }
        if (ac2 != null) {
            internalTicket.setAdminContact2NicHandle(getNicHandle(ac2));
            internalTicket.setAdminContact2FailureReason(ac2.getFailureReason());
        } else {
            internalTicket.setAdminContact2NicHandle(null);
            internalTicket.setAdminContact2FailureReason(null);
        }
        // tech contact
        List<SimpleDomainFieldChange<Contact>> techContacts = ticket.getOperation().getTechContactsField();
        SimpleDomainFieldChange<Contact> tc = getElement(techContacts, 0);
        if (tc != null) {
            internalTicket.setTechContactNicHandle(getNicHandle(tc));
            internalTicket.setTechContactFailureReason(tc.getFailureReason());
        } else {
            internalTicket.setTechContactNicHandle(null);
            internalTicket.setTechContactFailureReason(null);
        }
        // billing contact
        List<SimpleDomainFieldChange<Contact>> billingContacts = ticket.getOperation().getBillingContactsField();
        SimpleDomainFieldChange<Contact> bc = getElement(billingContacts, 0);
        if (bc != null) {
            internalTicket.setBillingContactNicHandle(getNicHandle(bc));
            internalTicket.setBillingContactFailureReason(bc.getFailureReason());
        } else {
            internalTicket.setBillingContactNicHandle(null);
            internalTicket.setBillingContactFailureReason(null);
        }
        //creator contact
        internalTicket.setCreatorNicHandle(ticket.getCreator().getNicHandle());
        //ns
        List<NameserverChange> nameservers = ticket.getOperation().getNameserversField();
        List<InternalTicketNameserver> internalNameservers = new ArrayList<>();
        for (NameserverChange ns : nameservers) {
            final SimpleDomainFieldChange<String> name = ns.getName();
            final FailureReason nameFailureReason = name.getFailureReason();
            final IpFieldChange ipv4Address = ns.getIpv4Address();
            final IpFailureReason ipv4FailureReason = ipv4Address.getFailureReason();
            final IpFieldChange ipv6Address = ns.getIpv6Address();
            final IpFailureReason ipv6FailureReason = ipv6Address.getFailureReason();
            final String nameValue = name.getNewValue();
            if (nameValue != null && !nameValue.isEmpty()) {
                internalNameservers
                        .add(new InternalTicketNameserver(nameValue, nameFailureReason, ipv4Address.getNewValue(),
                                ipv4FailureReason, ipv6Address.getNewValue(), ipv6FailureReason));
            }
        }
        internalTicket.setNameservers(internalNameservers);

        internalTicket.setClikPaid(ticket.isClikPaid());
        internalTicket.setCreationDate(ticket.getCreationDate());
        internalTicket.setRenewalDate(ticket.getOperation().getRenewalDate());
        internalTicket.setRequestersRemark(ticket.getRequestersRemark());
        internalTicket.setHostmastersRemark(ticket.getHostmastersRemark());
        internalTicket.setDomainPeriod(ticket.getDomainPeriod() == null ? null : ticket.getDomainPeriod().getYears());
        internalTicket.setCharityCode(ticket.getCharityCode());
        internalTicket.setAutorenewMode(ticket.isAutorenewMode());
        internalTicket.setFinancialStatus(ticket.getFinancialStatus());
        internalTicket.setFinancialStatusChangeDate(ticket.getFinancialStatusChangeDate());
        internalTicket.setCustomerStatus(ticket.getCustomerStatus());
        internalTicket.setCustomerStatusChangeDate(ticket.getCustomerStatusChangeDate());
    }

    private static /*>>>@Nullable*/ <T> T getElement(List<T> list, int index) {
        return list.size() > index ? list.get(index) : null;
    }

    private static /*>>>@Nullable*/ String getNicHandle(SimpleDomainFieldChange<Contact> change) {
        if (change.getNewValue() == null || Validator.isEmpty(change.getNewValue().getNicHandle())) {
            return null;
        } else {
            return change.getNewValue().getNicHandle();
        }
    }

}
