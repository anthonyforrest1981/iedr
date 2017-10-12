package pl.nask.crs.payment.dao.ibatis;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.dao.HistoricalDepositDAO;
import pl.nask.crs.payment.dao.ibatis.converters.HistoricalDepositConverter;
import pl.nask.crs.payment.dao.ibatis.objects.InternalHistoricalDeposit;

public class ConvertingHistoricalDepositDAO
        extends ConvertingGenericDAO<InternalHistoricalDeposit, HistoricalObject<Deposit>, Long>
        implements HistoricalDepositDAO {

    public ConvertingHistoricalDepositDAO(InternalHistoricalDepositIbatisDAO internalDao,
            HistoricalDepositConverter internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
    }

    @Override
    public Long create(Deposit deposit, Date changeDate, String changedBy) {
        HistoricalObject<Deposit> histDeposit = new HistoricalObject<>();
        histDeposit.setObject(deposit);
        // DepositHist table has no Chng_NH column.
        histDeposit.setChangedBy(null);
        histDeposit.setChangeDate(changeDate);
        InternalHistoricalDeposit ihd = getInternalConverter().from(histDeposit);
        getInternalDao().create(ihd);
        return ihd.getChangeId();
    }
}
