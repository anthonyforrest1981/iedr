package pl.nask.crs.domains.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;

public abstract class AbstractDomainConverter<SRC extends InternalDomain, DST extends Domain> extends
        AbstractConverter<SRC, DST> {

    private static ContactConverter contactConverter = new ContactConverter();
    private static ContactConverter adminConverter = new ContactConverter(ContactType.ADMIN);
    private static ContactConverter techConverter = new ContactConverter(ContactType.TECH);
    private static ContactConverter billingConverter = new ContactConverter(ContactType.BILLING);
    private static ContactConverter creatorConverter = new ContactConverter(ContactType.CREATOR);

    protected DST _to(SRC src) {
        List<Contact> adminContacts = new ArrayList<>();
        List<Contact> techContacts = new ArrayList<>();
        List<Contact> billingContacts = new ArrayList<>();
        Contact creator = contactConverter.to(src.getCreator());

        splitContacts(src.getContacts(), adminContacts, techContacts, billingContacts);

        DST domain = createDomainObject(src, creator, adminContacts, techContacts, billingContacts);
        domain.setSuspensionDate(src.getSuspensionDate());
        domain.setDeletionDate(src.getDeletionDate());
        domain.setTransferDate(src.getTransferDate());
        domain.setAuthCode(src.getAuthCode());
        domain.setAuthCodeExpirationDate(src.getAuthCodeExpirationDate());
        domain.setAuthCodePortalCount(src.getAuthCodePortalCount());
        return domain;
    }

    abstract protected DST createDomainObject(SRC src, Contact creator, List<Contact> adminContacts,
            List<Contact> techContacts, List<Contact> billingContacts);

    private void splitContacts(List<InternalContact> src, List<Contact> adminContacts, List<Contact> techContacts,
            List<Contact> billingContacts) {
        for (InternalContact srcContact : src) {
            Contact contact = contactConverter.to(srcContact);
            if (srcContact.isAdmin())
                adminContacts.add(contact);
            else if (srcContact.isBilling())
                billingContacts.add(contact);
            else if (srcContact.isTech())
                techContacts.add(contact);
        }
    }

    protected SRC _from(DST domain) {
        SRC ret = createInternalDomainObject();
        ret.setName(domain.getName());
        ret.setHolder(domain.getHolder());
        ret.setHolderClass(domain.getHolderClass());
        ret.setHolderCategory(domain.getHolderCategory());
        ret.setHolderSubcategory(domain.getHolderSubcategory());
        ret.setResellerAccountId(domain.getResellerAccount().getId());
        ret.setResellerAccountName(domain.getResellerAccount().getName());
        ret.setRegistrationDate(domain.getRegistrationDate());
        ret.setRenewalDate(domain.getRenewalDate());
        ret.setRemark(domain.getRemark());
        ret.setAuthCode(domain.getAuthCode());
        ret.setAuthCodeExpirationDate(domain.getAuthCodeExpirationDate());
        ret.setAuthCodePortalCount(domain.getAuthCodePortalCount());
        ret.setChangeDate(domain.getChangeDate());
        ret.setClikPaid(domain.isClikPaid());
        ret.setDateRoll(domain.getDateRoll());
        ret.setDsmState(domain.getDsmState());
        ret.setLockingDate(domain.getLockingDate());
        ret.setLockingRenewalDate(domain.getLockingRenewalDate());
        List<InternalContact> contacts = new ArrayList<>();
        contacts.addAll(adminConverter.from(domain.getAdminContacts()));
        contacts.addAll(billingConverter.from(domain.getBillingContacts()));
        contacts.addAll(techConverter.from(domain.getTechContacts()));
        InternalContact creator = creatorConverter.from(domain.getCreator());
        if (creator != null) {
            creator.setType(ContactType.CREATOR);
            contacts.add(creator);
        }
        ret.setContacts(contacts);
        ret.setNameservers(new ArrayList<>(domain.getNameservers()));
        ret.setSuspensionDate(domain.getSuspensionDate());
        ret.setDeletionDate(domain.getDeletionDate());
        ret.setTransferDate(domain.getTransferDate());
        return ret;
    }

    abstract protected SRC createInternalDomainObject();

    protected Account createAccount(Long id, String name, String billingContact) {
        return id == null ? null : new Account(id, name, billingContact);
    }

}
