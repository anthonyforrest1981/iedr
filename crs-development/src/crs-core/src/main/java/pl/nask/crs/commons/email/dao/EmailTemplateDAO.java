package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface EmailTemplateDAO extends GenericDAO<EmailTemplate, Integer> {

    SearchResult<EmailTemplate> findAndLockInShareMode(EmailTemplateSearchCriteria criteria);

    SearchResult<EmailTemplate> findAndLockForUpdate(EmailTemplateSearchCriteria criteria);
}
