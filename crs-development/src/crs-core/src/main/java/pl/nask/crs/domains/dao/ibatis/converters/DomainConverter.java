package pl.nask.crs.domains.dao.ibatis.converters;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;

public class DomainConverter extends AbstractDomainConverter<InternalDomain, Domain> {

    protected Domain createDomainObject(InternalDomain src, Contact creator, List<Contact> adminContacts,
            List<Contact> techContacts, List<Contact> billingContacts) {
        Account account = createAccount(src.getResellerAccountId(), src.getResellerAccountName(),
                src.getResellerAccountBillingContact());
        return new Domain(src.getName(), src.getHolder(), src.getHolderClass(), src.getHolderCategory(),
                src.getHolderSubcategory(), creator, account, src.getRegistrationDate(), src.getRenewalDate(),
                src.getRemark(), src.getChangeDate(), src.isClikPaid(), techContacts, billingContacts, adminContacts,
                src.getNameservers(), src.getDsmState(), src.isZonePublished(), src.getLockingDate(),
                src.getLockingRenewalDate());
    }

    protected InternalDomain createInternalDomainObject() {
        return new InternalDomain();
    }

}
