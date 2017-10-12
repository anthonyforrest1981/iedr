package pl.nask.crs.user.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;

public class InternalHistoricalUserIBatisDAO extends GenericIBatisDAO<InternalHistoricalUser, Long> {

    public InternalHistoricalUserIBatisDAO() {
        setCountFindQueryId("historicalUser.countFind");
        setLimitedFindQueryId("historicalUser.find");
        setCreateQueryId("historicalUser.createHistoricalUser");
    }

}
