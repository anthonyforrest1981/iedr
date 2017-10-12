package pl.nask.crs.domains.dsm.actions;

import java.util.Arrays;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class ApplySecondaryMarketChanges extends AbstractDsmAction {

    private ContactDAO contactDAO;

    public ApplySecondaryMarketChanges(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws Exception {
        String nicHandle = event.getStringParameter(DsmEvent.NEW_ADMIN_C);
        Contact contact = contactDAO.get(nicHandle);
        setContactsToDomain(domain, contact);
        SellRequest sellRequest = (SellRequest) event.getParameter(DsmEvent.SELL_REQUEST);
        domain.setHolder(sellRequest.getBuyRequest().getDomainHolder());
        domain.setHolderClass(sellRequest.getBuyRequest().getHolderClass());
        domain.setHolderCategory(sellRequest.getBuyRequest().getHolderCategory());
        domain.setHolderSubcategory(null);
    }

    protected void setContactsToDomain(Domain domain, Contact contact) {
        domain.setAdminContacts(Arrays.asList(contact));
    }

}
