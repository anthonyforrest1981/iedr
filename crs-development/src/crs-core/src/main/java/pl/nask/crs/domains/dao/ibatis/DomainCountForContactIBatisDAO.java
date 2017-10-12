package pl.nask.crs.domains.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.DomainCountForContact;
import pl.nask.crs.domains.dao.DomainCountForContactDAO;

public class DomainCountForContactIBatisDAO extends GenericIBatisDAO<DomainCountForContact, String> implements
        DomainCountForContactDAO {

    @Override
    public List<DomainCountForContact> findDomainCountForContact(SearchCriteria<DomainCountForContact> criteria) {
        FindParameters params = new FindParameters(criteria);
        List<DomainCountForContact> res = performQueryForList("domain.getDomainCountForContact", params);
        return res;
    }
}
