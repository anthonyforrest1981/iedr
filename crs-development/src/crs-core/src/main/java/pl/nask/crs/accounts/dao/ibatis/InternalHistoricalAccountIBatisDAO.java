package pl.nask.crs.accounts.dao.ibatis;

import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;

public class InternalHistoricalAccountIBatisDAO extends GenericIBatisDAO<InternalHistoricalAccount, Long> {

    public InternalHistoricalAccountIBatisDAO() {
        setFindQueryId("historicalAccount.findHistoricalAccount");
        setCreateQueryId("historicalAccount.createHistoricalAccount");
    }

}
