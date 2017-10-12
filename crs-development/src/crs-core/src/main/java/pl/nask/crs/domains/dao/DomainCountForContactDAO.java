package pl.nask.crs.domains.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.DomainCountForContact;

public interface DomainCountForContactDAO extends GenericDAO<DomainCountForContact, String> {

    public List<DomainCountForContact> findDomainCountForContact(SearchCriteria<DomainCountForContact> criteria);
}
