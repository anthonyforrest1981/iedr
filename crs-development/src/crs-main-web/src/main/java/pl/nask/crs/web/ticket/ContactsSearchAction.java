package pl.nask.crs.web.ticket;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

/**
 * @author Patrycja Wegrzynowicz
 */
public class ContactsSearchAction extends GenericSearchAction<Contact, ContactSearchCriteria> {
    private String nicHandleField;

    public String getNicHandleField() {
        return nicHandleField;
    }

    public void setNicHandleField(String nicHandleField) {
        this.nicHandleField = nicHandleField;
    }

    public ContactsSearchAction(final ContactSearchService searchService) {
        super(new AppSearchService<Contact, ContactSearchCriteria>() {
            @Override
            public LimitedSearchResult<Contact> search(AuthenticatedUser user, ContactSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return searchService.findContacts(criteria, offset, limit);
            }
        });
    }

    @Override
    protected int getPageSize() {
        return 15;
    }

    @Override
    protected ContactSearchCriteria createSearchCriteria() {
        return new ContactSearchCriteria();
    }

    @Override
    public String execute() throws Exception {
        return search();
    }

    @Override
    protected void resetSearch() {
        setHiddenSearchCriteria(getSearchCriteria());
    }

}
