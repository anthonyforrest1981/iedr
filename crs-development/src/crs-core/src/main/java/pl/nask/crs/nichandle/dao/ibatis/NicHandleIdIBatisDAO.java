package pl.nask.crs.nichandle.dao.ibatis;

import java.util.Collections;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIdIBatisDAO extends SqlMapClientLoggingDaoSupport implements NicHandleIdDAO {

    public Long get() {
        return performQueryForObject("nicHandleId.getNicHandleId", Collections.emptyMap());
    }

    public void update(Long newValue) {
        performUpdate("nicHandleId.updateNicHandleId", newValue);
    }

}
