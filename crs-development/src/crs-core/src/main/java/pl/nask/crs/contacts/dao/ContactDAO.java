package pl.nask.crs.contacts.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.contacts.Contact;

public interface ContactDAO extends GenericDAO<Contact, String> {

    String getDefaultTechContact(String billingContact);

}
