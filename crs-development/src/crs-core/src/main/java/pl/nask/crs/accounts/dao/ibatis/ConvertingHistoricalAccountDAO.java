package pl.nask.crs.accounts.dao.ibatis;

import java.util.Date;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.history.HistoricalObject;

public class ConvertingHistoricalAccountDAO extends
        ConvertingGenericDAO<InternalHistoricalAccount, HistoricalObject<Account>, Long> implements
        HistoricalAccountDAO {

    public ConvertingHistoricalAccountDAO(GenericDAO<InternalHistoricalAccount, Long> internalDao,
            Converter<InternalHistoricalAccount, HistoricalObject<Account>> internalConverter) {
        super(internalDao, internalConverter);
    }

    public Long create(Account account, Date changeDate, String changedBy) {
        HistoricalObject<Account> historicalAccount = new HistoricalObject<>(account, changeDate, changedBy);
        InternalHistoricalAccount iha = getInternalConverter().from(historicalAccount);
        getInternalDao().create(iha);
        return iha.getChangeId();
    }
}
