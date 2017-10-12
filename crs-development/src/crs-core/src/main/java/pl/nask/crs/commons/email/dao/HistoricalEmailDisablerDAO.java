package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.HistoricalDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;

public interface HistoricalEmailDisablerDAO extends HistoricalDAO<EmailDisabler, HistoricalEmailDisablerKey> {
}
