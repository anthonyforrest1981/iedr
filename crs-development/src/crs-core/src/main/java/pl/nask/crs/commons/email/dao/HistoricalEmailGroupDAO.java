package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.HistoricalDAO;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;

public interface HistoricalEmailGroupDAO extends HistoricalDAO<EmailGroup, HistoricalEmailGroupKey> {
}
