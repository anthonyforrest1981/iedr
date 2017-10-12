package pl.nask.crs.domains.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;
import pl.nask.crs.domains.dao.TopLevelDomainDAO;

public class TopLevelDomainIBatisDAO extends SqlMapClientLoggingDaoSupport implements TopLevelDomainDAO {

    public List<String> getAll() {
        return performQueryForList("top-level-domain.findTopLevelDomains");
    }

}
