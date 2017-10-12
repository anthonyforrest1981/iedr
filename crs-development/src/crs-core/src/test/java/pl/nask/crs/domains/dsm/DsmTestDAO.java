package pl.nask.crs.domains.dsm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pl.nask.crs.domains.DsmState;

public class DsmTestDAO extends SqlMapClientDaoSupport {

    public List<String> getActions() {
        return query("dsm-test.getAllActions");
    }

    public List<String> getEvents() {
        return query("dsm-test.getAllEvents");
    }

    public List<String> getNrpStatuses() {
        return query("dsm-test.getAllNRPStatuses");
    }

    public List<String> getRenewalModes() {
        return query("dsm-test.getAllRenewalModes");
    }

    public List<String> getAllHolderTypes() {
        return query("dsm-test.getAllHolderTypes");
    }

    public List<String> getAllCustTypes() {
        return query("dsm-test.getAllCustTypes");
    }

    public List<DsmState> findDsmStates(DsmStateSearchCriteria criteria) {
        Map<String, Object> map = new HashMap<>();
        map.put("criteria", criteria);
        return getSqlMapClientTemplate().queryForList("dsm-test.findDsmStates", map);
    }

    private List<String> query(String query) {
        return getSqlMapClientTemplate().queryForList(query);
    }

}
