package pl.nask.crs.domains.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalExtendedDomain;

import static pl.nask.crs.domains.dao.ibatis.DomainSortMapHelper.getDomainSortMap;

public class InternalExtendedDomainIBatisDAO extends GenericIBatisDAO<InternalExtendedDomain, String> {

    public InternalExtendedDomainIBatisDAO() {
        setGetQueryId("extended-domain.getExtendedDomainByName");
        setFindQueryId("extended-domain.findExtendedDomain");
        setCountFindQueryId("extended-domain.countFindExtendedDomain");
        setSortMapping(getDomainSortMap());
    }

}
