package pl.nask.crs.ticket.operation;

/*>>>import org.checkerframework.checker.initialization.qual.UnderInitialization;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class TicketEdit {

    private SimpleDomainFieldChange<String> domainNameField;
    private SimpleDomainFieldChange<String> domainHolderField;
    private SimpleDomainFieldChange<Long> domainHolderClassField;
    private SimpleDomainFieldChange<Long> domainHolderCategoryField;
    private SimpleDomainFieldChange<Long> domainHolderSubcategoryField;
    private SimpleDomainFieldChange<Long> resellerAccountField;
    private List<SimpleDomainFieldChange<String>> adminContactField;
    private List<SimpleDomainFieldChange<String>> techContactField;
    private List<SimpleDomainFieldChange<String>> billingContactField;
    private List<NameserverChange> nameserversField;

    public TicketEdit(SimpleDomainFieldChange<String> domainNameField,
            SimpleDomainFieldChange<String> domainHolderField, SimpleDomainFieldChange<Long> domainHolderClassField,
            SimpleDomainFieldChange<Long> domainHolderCategoryField,
            SimpleDomainFieldChange<Long> domainHolderSubcategoryField,
            SimpleDomainFieldChange<Long> resellerAccountField,
            List<SimpleDomainFieldChange<String>> adminContactField,
            List<SimpleDomainFieldChange<String>> techContactField,
            List<SimpleDomainFieldChange<String>> billingContactField, List<NameserverChange> nameserversField) {
        this.domainNameField = domainNameField;
        this.domainHolderField = domainHolderField;
        this.domainHolderClassField = domainHolderClassField;
        this.domainHolderCategoryField = domainHolderCategoryField;
        this.domainHolderSubcategoryField = domainHolderSubcategoryField;
        this.resellerAccountField = resellerAccountField;
        this.adminContactField = adminContactField;
        this.techContactField = techContactField;
        this.billingContactField = billingContactField;
        this.nameserversField = nameserversField;
    }

    public TicketEdit(DomainOperation domainOperation) {
        this.domainNameField = new SimpleDomainFieldChange<>(null, domainOperation.getDomainNameField().getNewValue());
        this.domainHolderField = new SimpleDomainFieldChange<>(null, domainOperation.getDomainHolderField()
                .getNewValue());
        EntityClass domainClass = domainOperation.getDomainHolderClassField().getNewValue();
        this.domainHolderClassField = new SimpleDomainFieldChange<>(null, domainClass == null ? null :
            domainClass.getId());
        EntityCategory domainCategory = domainOperation.getDomainHolderCategoryField().getNewValue();
        this.domainHolderCategoryField = new SimpleDomainFieldChange<>(null, domainCategory == null ? null :
            domainCategory.getId());
        EntitySubcategory subcategory = domainOperation.getDomainHolderSubcategoryField().getNewValue();
        this.domainHolderSubcategoryField =
                new SimpleDomainFieldChange<>(null, subcategory == null ? null : subcategory.getId());
        Account account = domainOperation.getResellerAccountField().getNewValue();
        this.resellerAccountField = new SimpleDomainFieldChange<>(null, account == null ? null : account.getId());
        this.adminContactField = transformContacts(domainOperation.getAdminContactsField());
        this.techContactField = transformContacts(domainOperation.getTechContactsField());
        this.billingContactField = transformContacts(domainOperation.getBillingContactsField());
        this.nameserversField = domainOperation.getNameserversField();
    }

    private static List<SimpleDomainFieldChange<String>> transformContacts(
            List<SimpleDomainFieldChange<Contact>> contacts) {
        List<SimpleDomainFieldChange<String>> result = new ArrayList<>();
        for (SimpleDomainFieldChange<Contact> contactField : contacts) {
            SimpleDomainFieldChange<String> contact = new SimpleDomainFieldChange<>(null,
                    contactField.getNewValue() == null ? null : contactField.getNewValue().getNicHandle());
            result.add(contact);
        }
        return result;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<String> getDomainNameField() {
        return domainNameField;
    }

    public void setDomainNameField(SimpleDomainFieldChange<String> domainNameField) {
        this.domainNameField = domainNameField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<String> getDomainHolderField() {
        return domainHolderField;
    }

    public void setDomainHolderField(SimpleDomainFieldChange<String> domainHolderField) {
        this.domainHolderField = domainHolderField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<Long> getDomainHolderClassField() {
        return domainHolderClassField;
    }

    public void setDomainHolderClassField(SimpleDomainFieldChange<Long> domainHolderClassField) {
        this.domainHolderClassField = domainHolderClassField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<Long> getDomainHolderCategoryField() {
        return domainHolderCategoryField;
    }

    public void setDomainHolderCategoryField(SimpleDomainFieldChange<Long> domainHolderCategoryField) {
        this.domainHolderCategoryField = domainHolderCategoryField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<Long> getDomainHolderSubcategoryField() {
        return domainHolderSubcategoryField;
    }

    public void setDomainHolderSubcategoryField(SimpleDomainFieldChange<Long> domainHolderSubcategoryField) {
        this.domainHolderSubcategoryField = domainHolderSubcategoryField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<Long> getResellerAccountField() {
        return resellerAccountField;
    }

    public void setResellerAccountField(SimpleDomainFieldChange<Long> resellerAccountField) {
        this.resellerAccountField = resellerAccountField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<String>> getAdminContactField() {
        return adminContactField;
    }

    public void setAdminContactField(List<SimpleDomainFieldChange<String>> adminContactField) {
        this.adminContactField = adminContactField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<String>> getTechContactField() {
        return techContactField;
    }

    public void setTechContactField(List<SimpleDomainFieldChange<String>> techContactField) {
        this.techContactField = techContactField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<String>> getBillingContactField() {
        return billingContactField;
    }

    public void setBillingContactField(List<SimpleDomainFieldChange<String>> billingContactField) {
        this.billingContactField = billingContactField;
    }

    /*>>>@Pure*/
    public List<NameserverChange> getNameserversField() {
        return nameserversField;
    }

    public void setNameserversField(List<NameserverChange> newNameservers) {
        this.nameserversField = newNameservers;
    }

    public boolean containsNameserver(String nameserverChange) {
        for (NameserverChange chng : nameserversField) {
            final String chngNameserverName = chng.getName().getNewValue();
            if (chngNameserverName != null && chngNameserverName.equalsIgnoreCase(nameserverChange))
                return true;
        }
        return false;
    }

    public void addNameserverChange(NameserverChange newNameserver) {
        nameserversField.add(newNameserver);
    }

    public void removeNameserverChange(String nameserverToRemove) {
        for (int i = 0; i < nameserversField.size(); ++i) {
            NameserverChange chng = nameserversField.get(i);
            final String chngName = chng.getName().getNewValue();
            if (chngName != null && chngName.equalsIgnoreCase(nameserverToRemove)) {
                nameserversField.remove(i);
                break;
            }
        }
    }

}
