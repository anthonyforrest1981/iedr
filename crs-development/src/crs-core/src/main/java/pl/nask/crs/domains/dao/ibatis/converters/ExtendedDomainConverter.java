package pl.nask.crs.domains.dao.ibatis.converters;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalExtendedDomain;

public class ExtendedDomainConverter extends AbstractDomainConverter<InternalExtendedDomain, ExtendedDomain> {

    protected ExtendedDomain createDomainObject(InternalExtendedDomain src, Contact creator,
            List<Contact> adminContacts, List<Contact> techContacts, List<Contact> billingContacts) {
        Account account = createAccount(src.getResellerAccountId(), src.getResellerAccountName(),
                src.getResellerAccountBillingContact());
        return new ExtendedDomain(src.getName(), src.getHolder(), src.getHolderClass(), src.getHolderCategory(),
                src.getHolderSubcategory(), creator, account, src.getRegistrationDate(), src.getRenewalDate(),
                src.getRemark(), src.getChangeDate(), src.isClikPaid(), techContacts, billingContacts, adminContacts,
                src.getNameservers(), src.getDsmState(), src.isZonePublished(), src.getLockingDate(),
                src.getLockingRenewalDate(), src.getPendingReservationPaymentMethod());
    }

    protected InternalExtendedDomain createInternalDomainObject() {
        return new InternalExtendedDomain();
    }

    protected InternalExtendedDomain _from(ExtendedDomain domain) {
        InternalExtendedDomain internalDomain = super._from(domain);
        internalDomain.setPendingReservationPaymentMethod(domain.getPendingReservationPaymentMethod());
        return internalDomain;
    }

}
