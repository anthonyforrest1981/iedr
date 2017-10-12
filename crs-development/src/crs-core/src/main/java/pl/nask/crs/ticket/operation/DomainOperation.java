package pl.nask.crs.ticket.operation;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class DomainOperation {

    public enum DomainOperationType {
        REG("Registration"), DEL("Deletion"), MOD("Modification"), XFER("Transfer");

        private String fullName;

        DomainOperationType(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public static DomainOperationType forFullname(final String fullname) {
            for (DomainOperationType t : DomainOperationType.values()) {
                if (t.getFullName().equals(fullname))
                    return t;
            }
            throw new IllegalArgumentException("Unknown domain operation type (ticket type) " + fullname);
        }
    }

    /**
     * T_Type not null
     */
    private DomainOperationType type;

    /**
     * D_Name not null, DN_Fail_Cd null
     * <p/>
     * todo: domain name normalized and validated - DomainName class?
     */
    private SimpleDomainFieldChange<String> domainNameField;

    /**
     * D_Holder not null, DH_Fail_Cd null
     */
    private SimpleDomainFieldChange<String> domainHolderField;

    /**
     * Class_Id not null, Class_Id_Fail_Cd null
     */
    private SimpleDomainFieldChange<EntityClass> domainHolderClassField;

    /**
     * Category_Id not null, Category_Id_Fail_Cd null
     */
    private SimpleDomainFieldChange<EntityCategory> domainHolderCategoryField;

    private SimpleDomainFieldChange<EntitySubcategory> domainHolderSubcategoryField;

    /**
     * A_Number not null, AC_Fail_Cd null
     */
    private SimpleDomainFieldChange<Account> resellerAccountField;

    /**
     * Admin_NH2 null, ANH2_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> adminContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    /**
     * Tech_NH not null, TNH_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> techContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    /**
     * Bill_NH not null, BNH_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> billingContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    private List<NameserverChange> nameserversField = new ArrayList<NameserverChange>();

    private /*>>>@Nullable*/ Date renewalDate;

    public DomainOperation(DomainOperationType type,
    /*>>>@Nullable*/ Date renewalDate, SimpleDomainFieldChange<String> domainNameField,
            SimpleDomainFieldChange<String> domainHolderField,
            SimpleDomainFieldChange<EntityClass> domainHolderClassFieldChange,
            SimpleDomainFieldChange<EntityCategory> domainHolderCategoryField,
            SimpleDomainFieldChange<EntitySubcategory> domainHolderSubcategoryField,
            SimpleDomainFieldChange<Account> resellerAccountField,
            List<SimpleDomainFieldChange<Contact>> adminContactFieldChange,
            List<SimpleDomainFieldChange<Contact>> techContactFieldChange,
            List<SimpleDomainFieldChange<Contact>> billingContactFieldChange, List<NameserverChange> nameserversField) {
        Validator.assertNotNull(type, "domain change type");
        Validator.assertNotNull(domainNameField, "domain name field");
        Validator.assertNotNull(domainHolderField, "domain holder field");
        Validator.assertNotNull(domainHolderClassFieldChange, "domain holder class field");
        Validator.assertNotNull(domainHolderCategoryField, "domain holder category field");
        Validator.assertNotNull(resellerAccountField, "reseller account field");
        Validator.assertNotNull(adminContactFieldChange, "admin contact field");
        Validator.assertNotNull(techContactFieldChange, "tech contact field");
        Validator.assertNotNull(billingContactFieldChange, "billing contact field");
        Validator.assertNotNull(nameserversField, "nameservers field");
        this.type = type;
        this.domainNameField = domainNameField;
        this.domainHolderField = domainHolderField;
        this.domainHolderClassField = domainHolderClassFieldChange;
        this.domainHolderCategoryField = domainHolderCategoryField;
        this.domainHolderSubcategoryField = domainHolderSubcategoryField;
        this.resellerAccountField = resellerAccountField;
        this.adminContactsField = adminContactFieldChange;
        this.techContactsField = techContactFieldChange;
        this.billingContactsField = billingContactFieldChange;
        this.nameserversField.addAll(nameserversField);
        this.renewalDate = renewalDate != null ? DateUtils.truncate(renewalDate, Calendar.SECOND) : null;
        /*
         * if (type == Type.REG) { Validator.assertNotNull(renewalDate,
         * "renewal date"); }
         */
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ Date getRenewalDate() {
        return renewalDate;
    }

    /**
     * Returns the type of the ticket.
     *
     * @return the type of the ticket; never null.
     */
    /*>>>@Pure*/
    public DomainOperationType getType() {
        return type;
    }

    /**
     * Returns the the domain name field of the ticket.
     *
     * @return the domain name field of the ticket; never null; modified always false
     */
    /*>>>@Pure*/
    public SimpleDomainFieldChange<String> getDomainNameField() {
        return domainNameField;
    }

    /**
     * Returns the domain holder field of the ticket.
     *
     * @return the domain holder field of the ticket; never null
     */
    /*>>>@Pure*/
    public SimpleDomainFieldChange<String> getDomainHolderField() {
        return domainHolderField;
    }

    /**
     * Returns the class field representing the class of the domain holder.
     *
     * @return the class field representing the class of the domain holder; nevern null
     */
    /*>>>@Pure*/
    public SimpleDomainFieldChange<EntityClass> getDomainHolderClassField() {
        return domainHolderClassField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<EntityCategory> getDomainHolderCategoryField() {
        return domainHolderCategoryField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<EntitySubcategory> getDomainHolderSubcategoryField() {
        return domainHolderSubcategoryField;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<Account> getResellerAccountField() {
        return resellerAccountField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<Contact>> getAdminContactsField() {
        // todo: return unmodifiable list?
        return adminContactsField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<Contact>> getTechContactsField() {
        // todo: return unmodifiable list?
        return techContactsField;
    }

    /*>>>@Pure*/
    public List<SimpleDomainFieldChange<Contact>> getBillingContactsField() {
        // todo: return unmodifiable list?
        return billingContactsField;
    }

    /*>>>@Pure*/
    public List<NameserverChange> getNameserversField() {
        // todo: return unmodifiable list?
        return nameserversField;
    }

    public void setNameserversField(List<NameserverChange> newNameservers) {
        this.nameserversField = newNameservers;
    }

    public void clearFailureReasons() {
        domainNameField.setFailureReason(null);
        domainHolderField.setFailureReason(null);
        domainHolderClassField.setFailureReason(null);
        domainHolderCategoryField.setFailureReason(null);
        resellerAccountField.setFailureReason(null);
        clearFailureReasons(adminContactsField);
        clearFailureReasons(techContactsField);
        clearFailureReasons(billingContactsField);
        for (int i = 0; i < nameserversField.size(); ++i) {
            NameserverChange ns1 = nameserversField.get(i);
            ns1.getName().setFailureReason(null);
            ns1.getIpv4Address().setFailureReason(null);
            ns1.getIpv6Address().setFailureReason(null);
        }
    }

    private void clearFailureReasons(List<SimpleDomainFieldChange<Contact>> contacts) {
        for (SimpleDomainFieldChange<Contact> contact : contacts) {
            contact.setFailureReason(null);
        }
    }

    public boolean hasFailureReasons() {
        return domainNameField.isFailed() || domainHolderField.isFailed() || domainHolderClassField.isFailed()
                || domainHolderCategoryField.isFailed() || resellerAccountField.isFailed()
                || isFailed(adminContactsField) || isFailed(techContactsField) || isFailed(billingContactsField)
                || isFailed(nameserversField);
    }

    private boolean isFailed(List<? extends DomainFieldChange> list) {
        for (DomainFieldChange change : list) {
            if (change.isFailed())
                return true;
        }
        return false;
    }

    public List<Contact> getAllContactList() {
        ArrayList<Contact> result = new ArrayList<Contact>();
        Contact contact;
        if ((contact = this.getAdminContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getAdminContactsField().get(1).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getTechContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getBillingContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        return result;
    }

}
