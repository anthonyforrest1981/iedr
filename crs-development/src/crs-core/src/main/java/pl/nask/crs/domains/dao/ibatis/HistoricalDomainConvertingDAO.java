package pl.nask.crs.domains.dao.ibatis;

import java.util.Date;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmStateStub;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalDomainConvertingDAO extends
        ConvertingGenericDAO<InternalHistoricalDomain, HistoricalObject<Domain>, Long> implements HistoricalDomainDAO {

    public HistoricalDomainConvertingDAO(InternalHistoricalDomainIbatisDAO internalDao,
            Converter<InternalHistoricalDomain, HistoricalObject<Domain>> internalConverter) {
        super(internalDao, internalConverter);
    }

    @Override
    public Long create(Domain domain, Date changeDate, String changedBy) {
        HistoricalObject<Domain> historicalDomain = new HistoricalObject<>(domain, changeDate, changedBy);
        InternalHistoricalDomain ihd = getInternalConverter().from(historicalDomain);
        getInternalDao().create(ihd);
        return ihd.getChangeId();
    }

    @Override
    public Long create(Domain domain, int targetState, Date changeDate, String changedBy) {
        HistoricalObject<Domain> historicalDomain = new HistoricalObject<>(domain, changeDate, changedBy);
        InternalHistoricalDomain ihd = getInternalConverter().from(historicalDomain);
        ihd.setDsmState(new DsmStateStub(targetState));
        getInternalDao().create(ihd);
        return ihd.getChangeId();
    }

}
