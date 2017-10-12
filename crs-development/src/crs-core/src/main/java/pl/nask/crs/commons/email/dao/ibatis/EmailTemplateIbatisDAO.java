package pl.nask.crs.commons.email.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;

public class EmailTemplateIbatisDAO extends GenericIBatisDAO<EmailTemplate, Integer> implements EmailTemplateDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "E_ID");
        sortMap.put("subject", "E_Subject");
        sortMap.put("group_id", "e.EG_ID");
        sortMap.put("sendReason", "E_Send_Reason");
        sortMap.put("suppressible", "E_Suppressible");
    }

    public EmailTemplateIbatisDAO() {
        setGetQueryId("emailtemplate.getTemplateById");
        setFindQueryId("emailtemplate.find");
        setLimitedFindQueryId("emailtemplate.find");
        setCountFindQueryId("emailtemplate.findCount");
        setLockQueryId("emailtemplate.lock");
        setUpdateUsingHistoryQueryId("emailtemplate.updateUsingHistory");
        setSortMapping(sortMap);
    }

    @Override
    public SearchResult<EmailTemplate> findAndLockInShareMode(EmailTemplateSearchCriteria criteria) {
        FindParameters params = new FindParameters(criteria);
        List<EmailTemplate> list = performQueryForList("emailtemplate.findAndLockInShareMode", params);
        return new SearchResult<>(criteria, list);
    }

    @Override
    public SearchResult<EmailTemplate> findAndLockForUpdate(EmailTemplateSearchCriteria criteria) {
        FindParameters params = new FindParameters(criteria);
        List<EmailTemplate> list = performQueryForList("emailtemplate.findAndLockForUpdate", params);
        return new SearchResult<>(criteria, list);
    }

}
