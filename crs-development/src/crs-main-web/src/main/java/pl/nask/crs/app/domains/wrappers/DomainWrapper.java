package pl.nask.crs.app.domains.wrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.HolderSubcategoryNotExistException;
import pl.nask.crs.entities.service.EntityService;

public class DomainWrapper extends AbstractDomainWrapper {

    private final boolean documents;
    private final boolean tickets;

    private EntityService entityService;
    private String remark;
    private String domainHolder;
    private Collection<String> relatedDomainNames;
    private Collection<String> pendingDomainNames;

    @Override
    public Domain getDomain() {
        Domain d = super.getDomain();
        d.setHolder(domainHolder);
        return d;
    }

    /**
     * Use this constructor if you plan to use {get|set}holder{class|category}id
     */
    public DomainWrapper(ExtendedDomainInfo domainInfo, EntityService entityService) {
        super(domainInfo.getDomain());
        this.entityService = entityService;
        this.domainHolder = domainInfo.getDomain().getHolder();
        this.documents = domainInfo.isDocuments();
        this.tickets = domainInfo.isTickets();
        this.relatedDomainNames = domainInfo.getRelatedDomainNames();
        this.pendingDomainNames = domainInfo.getPendingDomainNames();
    }

    /**
     * Use this constructor if you DON'T plan to use {get|set}holder{class|category}id
     */
    public DomainWrapper(ExtendedDomainInfo domainInfo) {
        super(domainInfo.getDomain());
        this.domainHolder = domainInfo.getDomain().getHolder();
        this.documents = domainInfo.isDocuments();
        this.tickets = domainInfo.isTickets();
        this.relatedDomainNames = domainInfo.getRelatedDomainNames();
        this.pendingDomainNames = domainInfo.getPendingDomainNames();
    }

    // domain holder cannot be null in the Domain, so we have to keep it here
    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    @Override
    public String getDomainHolder() {
        return StringEscapeUtils.escapeHtml(this.domainHolder);
    }

    @Override
    public String getHolder() {
        return getDomainHolder();
    }

    public EntityClass getHolderClass() {
        return getWrappedDomain().getHolderClass();
    }

    public EntityCategory getHolderCategory() {
        return getWrappedDomain().getHolderCategory();
    }

    public EntitySubcategory getHolderSubcategory() {
        EntitySubcategory subcategory = getWrappedDomain().getHolderSubcategory();
        return subcategory == null ? new EntitySubcategory() : subcategory;
    }

    public Long getHolderClassId() {
        return getWrappedDomain().getHolderClass().getId();
    }

    public void setHolderClassId(Long holderClassId) throws HolderClassNotExistException {
        getWrappedDomain().setHolderClass(entityService.getClass(holderClassId));
    }

    public Long getHolderCategoryId() {
        return getWrappedDomain().getHolderCategory().getId();
    }

    public void setHolderCategoryId(Long holderCategoryId) throws HolderCategoryNotExistException {
        getWrappedDomain().setHolderCategory(entityService.getCategory(holderCategoryId));
    }

    public Long getHolderSubcategoryId() {
        EntitySubcategory subcategory = getWrappedDomain().getHolderSubcategory();
        return subcategory == null ? null : subcategory.getId();
    }

    public void setHolderSubcategoryId(Long holderSubcategoryId) throws HolderSubcategoryNotExistException {
        EntitySubcategory subcategory = null;
        if (holderSubcategoryId != null) {
            subcategory = entityService.getSubcategory(holderSubcategoryId);
        }
        getWrappedDomain().setHolderSubcategory(subcategory);
    }

    public String getAdminContact1() {
        return getContact(getWrappedDomain().getAdminContacts(), 0);
    }

    public String getAdminContact2() {
        return getContact(getWrappedDomain().getAdminContacts(), 1);
    }

    private String getContact(List<Contact> l, int index) {
        if (l == null)
            return null;
        Contact c = l.get(index);
        if (c == null)
            return null;
        return c.getNicHandle();
    }

    public void setAdminContact1(String adminContact) {
        setAdminContact(0, adminContact);
    }

    public void setAdminContact2(String adminContact) {
        setAdminContact(1, adminContact);
    }

    private void setAdminContact(int index, String adminContact) {
        List<Contact> contacts = getWrappedDomain().getAdminContacts();
        if (contacts == null) {
            contacts = new ArrayList<>();
            getWrappedDomain().setAdminContacts(contacts);
        }
        setContact(contacts, index, adminContact);
    }

    private void setContact(List<Contact> contacts, int index, String nichandle) {
        Contact contact = new Contact(nichandle);
        if (contacts.size() > index) {
            contacts.set(index, contact);
        } else {
            contacts.add(contact);
        }
    }

    public String getTechContact() {
        return getContact(getWrappedDomain().getTechContacts(), 0);
    }

    public void setTechContact(String techContact) {
        List<Contact> contacts = getWrappedDomain().getTechContacts();
        if (contacts == null) {
            contacts = new ArrayList<>();
            getWrappedDomain().setTechContacts(contacts);
        }
        setContact(contacts, 0, techContact);
    }

    public String getBillContact() {
        return getContact(getWrappedDomain().getBillingContacts(), 0);
    }

    public void setBillContact(String billContact) {
        List<Contact> contacts = getWrappedDomain().getBillingContacts();
        if (contacts == null) {
            contacts = new ArrayList<>();
            getWrappedDomain().setBillingContacts(contacts);
        }
        setContact(contacts, 0, billContact);
    }

    public Long getAccountNo() {
        Domain d = getWrappedDomain();
        if (d != null && d.getResellerAccount() != null)
            return d.getResellerAccount().getId();
        else
            return null;
    }

    public void setAccountNo(Long accountNo) {
        getWrappedDomain().setResellerAccount(new Account(accountNo));
    }

    public String getNewRemark() {
        return remark;
    }

    public void setNewRemark(String remark) {
        this.remark = remark;
        setRemark(remark);
    }

    public String getRemark() {
        return StringEscapeUtils.escapeHtml(getWrappedDomain().getRemark());
    }

    public void setRemark(String remark) {
        getWrappedDomain().setRemark(remark);
    }

    public String getNoDotsDomainName() {
        return getWrappedDomain().getName().replaceAll("\\.", "");
    }

    public boolean isDocuments() {
        return documents;
    }

    public boolean isTickets() {
        return tickets;
    }

    public Collection<String> getRelatedDomainNames() {
        return this.relatedDomainNames;
    }

    public Collection<String> getPendingDomainNames() {
        return this.pendingDomainNames;
    }

}
