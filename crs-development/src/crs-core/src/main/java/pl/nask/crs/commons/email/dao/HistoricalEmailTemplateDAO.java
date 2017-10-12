package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.HistoricalDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;

public interface HistoricalEmailTemplateDAO extends HistoricalDAO<EmailTemplate, HistoricalEmailTemplateKey> {
}
