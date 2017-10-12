package pl.nask.crs.contacts.services;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface ContactSearchService {

    Contact getContact(String nicHandle) throws NicHandleNotFoundException;

    LimitedSearchResult<Contact> findContacts(ContactSearchCriteria criteria, long offset, long limit);

    boolean contactExists(ContactSearchCriteria criteria);

    String getDefaultTechContact(String billingContact);

}
