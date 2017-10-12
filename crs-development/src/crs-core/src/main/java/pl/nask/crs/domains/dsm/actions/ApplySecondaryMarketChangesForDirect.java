package pl.nask.crs.domains.dsm.actions;

import java.util.Arrays;
import java.util.Date;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class ApplySecondaryMarketChangesForDirect extends ApplySecondaryMarketChanges {

    public ApplySecondaryMarketChangesForDirect(ContactDAO contactDAO) {
        super(contactDAO);
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws Exception {
        super.invokeAction(user, domain, event);
        Date completionDate = event.getDateParameter(DsmEvent.EVENT_DATE);
        domain.setTransferDate(completionDate);
        domain.setAuthCode(null);
        domain.setAuthCodeExpirationDate(null);
    }

    @Override
    protected void setContactsToDomain(Domain domain, Contact contact) {
        domain.setAdminContacts(Arrays.asList(contact));
        domain.setBillingContacts(Arrays.asList(contact));
        domain.setTechContacts(Arrays.asList(contact));
    }

}
