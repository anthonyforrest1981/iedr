package pl.nask.crs.user.dao.ibatis;

import java.util.Date;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;

public class ConvertingHistoricalUserDAO extends
        ConvertingGenericDAO<InternalHistoricalUser, HistoricalObject<User>, Long> implements HistoricalUserDAO {

    public ConvertingHistoricalUserDAO(InternalHistoricalUserIBatisDAO internalDao,
            Converter<InternalHistoricalUser, HistoricalObject<User>> internalConverter) {
        super(internalDao, internalConverter);
    }

    @Override
    public Long create(User user, Date changeDate, String changedBy) {
        HistoricalObject<User> histUser = new HistoricalObject<>(user, changeDate, changedBy);
        histUser.setChangeDate(changeDate);
        histUser.setChangedBy(changedBy);
        InternalHistoricalUser ihu = getInternalConverter().from(histUser);
        getInternalDao().create(ihu);
        return ihu.getChangeId();
    }
}
