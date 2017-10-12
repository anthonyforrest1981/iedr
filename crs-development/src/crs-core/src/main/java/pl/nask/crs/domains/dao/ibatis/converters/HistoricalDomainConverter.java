package pl.nask.crs.domains.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalDomainConverter extends AbstractConverter<InternalHistoricalDomain, HistoricalObject<Domain>> {

    private static ContactConverter adminConverter = new ContactConverter(ContactType.ADMIN);
    private static ContactConverter techConverter = new ContactConverter(ContactType.TECH);
    private static ContactConverter billingConverter = new ContactConverter(ContactType.BILLING);
    private static ContactConverter creatorConverter = new ContactConverter(ContactType.CREATOR);

    private DomainConverter domainConverter = new DomainConverter();
    private static final Logger logger = Logger.getLogger(HistoricalDomainConverter.class);

    protected HistoricalObject<Domain> _to(InternalHistoricalDomain src) {
        try {
            return new HistoricalObject<Domain>(src.getChangeId(), domainConverter.to(src), src.getHistChangeDate(),
                    src.getChangedByNicHandle());
        } catch (Exception e) {
            logger.error("Cannot convert Ticket History record with id=" + src.getName() + ". Error message was: "
                    + e.getMessage());
            return null;
        }
    }

    protected InternalHistoricalDomain _from(HistoricalObject<Domain> histDomain) {
        Domain domain = histDomain.getObject();
        InternalHistoricalDomain ret = new InternalHistoricalDomain();
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

        ret.setChangedByNicHandle(histDomain.getChangedBy());
        ret.setHistChangeDate(histDomain.getChangeDate());
        return ret;
    }

}
