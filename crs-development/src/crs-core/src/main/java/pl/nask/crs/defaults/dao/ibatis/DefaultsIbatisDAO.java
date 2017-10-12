package pl.nask.crs.defaults.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.dao.DefaultsDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DefaultsIbatisDAO extends GenericIBatisDAO<ResellerDefaults, String> implements DefaultsDAO {

    public DefaultsIbatisDAO() {
        setGetQueryId("defaults.getResellerDefaults");
        setCreateQueryId("defaults.create");
        setUpdateQueryId("defaults.update");
    }

    @Override
    public List<ResellerDefaults> getAll() {
        return performQueryForList("defaults.getAll");
    }

    @Override
    public void create(ResellerDefaults resellerDefaults) {
        super.create(resellerDefaults);
        updateNameservers(resellerDefaults);
    }

    @Override
    public void update(ResellerDefaults resellerDefaults) {
        super.update(resellerDefaults);
        updateNameservers(resellerDefaults);
    }

    private void updateNameservers(ResellerDefaults resellerDefaults) {
        final String nicHandleId = resellerDefaults.getNicHandleId();
        performDelete("defaults.deleteNameservers", nicHandleId);
        int order = 0;
        final Date date = new Date();
        for (String newNs : resellerDefaults.getNameservers()) {
            performInsert("defaults.createTicketNameserver", createDNSParams(nicHandleId, newNs, order++, date));
        }
    }

    private Map<String, Object> createDNSParams(String nicHandle, String nameserver, int order, Date creationDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nicHandle", nicHandle);
        params.put("nameserver", nameserver);
        params.put("order", order);
        params.put("date", creationDate);
        return params;
    }
}
