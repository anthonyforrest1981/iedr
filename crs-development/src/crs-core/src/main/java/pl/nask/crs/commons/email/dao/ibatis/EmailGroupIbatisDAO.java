package pl.nask.crs.commons.email.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;

public class EmailGroupIbatisDAO extends GenericIBatisDAO<EmailGroup, Long> implements EmailGroupDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("id", "EG_ID");
        sortMap.put("name", "EG_Name");
        sortMap.put("changeDate", "EG_Change_TS");
    }

    EmailGroupIbatisDAO() {
        setGetQueryId("emailgroup.get");
        setCreateQueryId("emailgroup.create");
        setUpdateUsingHistoryQueryId("emailgroup.updateUsingHistory");
        setDeleteQueryId("emailgroup.delete");
        setFindQueryId("emailgroup.find");
        setCountFindQueryId("emailgroup.count");
        setSortMapping(sortMap);
    }
}
