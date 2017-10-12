package pl.nask.crs.nichandle.dao.ibatis;

import java.util.Date;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalHistoricalNicHandle;

public class ConvertingHistoricalNicHandleDAO extends
        ConvertingGenericDAO<InternalHistoricalNicHandle, HistoricalObject<NicHandle>, Long>
        implements HistoricalNicHandleDAO {

    public ConvertingHistoricalNicHandleDAO(
            GenericDAO<InternalHistoricalNicHandle, Long> internalDao,
            Converter<InternalHistoricalNicHandle, HistoricalObject<NicHandle>> internalConverter) {
        super(internalDao, internalConverter);
    }

    @Override
    public Long create(NicHandle nicHandle, Date changeDate, String changedBy) {
        HistoricalObject<NicHandle> historicalNicHandle = new HistoricalObject<>(nicHandle, changeDate, changedBy);
        InternalHistoricalNicHandle nh = getInternalConverter().from(historicalNicHandle);
        getInternalDao().create(nh);
        return nh.getChangeId();
    }

}
