package pl.nask.crs.commons.email.dao.ibatis;

import java.util.*;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.NicHandleDetails;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;

public class EmailDisablerIbatisDAO extends GenericIBatisDAO<EmailDisabler, EmailDisablerKey> implements
        EmailDisablerDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("emailId", "emailId");
        sortMap.put("nicHandle", "nicHandle");
        sortMap.put("disabled", "disabled");
        sortMap.put("changeDate", "changeDate");
    }

    public EmailDisablerIbatisDAO() {
        setGetQueryId("emaildisabler.get");
        setUpdateUsingHistoryQueryId("emaildisabler.updateUsingHistory");
        setDeleteQueryId("emaildisabler.delete");
        setFindQueryId("emaildisabler.find");
        setCountFindQueryId("emaildisabler.count");
        setSortMapping(sortMap);
    }

    @Override
    public boolean isNhAdminOrTechForDomain(final String nicHandle, final String domain) {
        Map<String, Object> params = new HashMap<>();
        params.put("nicHandle", nicHandle);
        params.put("domainName", domain);
        int count = this.<Integer>performQueryForObject("emaildisabler.isNhAdminOrTechForDomain", params);
        return count > 0;
    }

    @Override
    public List<EmailDisabler> findWithEmailTempAndEmailGroup(final String nicHandle) {
        return performQueryForList("emaildisabler.findWithEmailTempAndEmailGroup",
                Collections.singletonMap("nicHandle", nicHandle));
    }

    @Override
    public SearchResult<EmailDisabler> findAndLockForUpdate(EmailDisablerSearchCriteria criteria) {
        FindParameters params = new FindParameters(criteria);
        List<EmailDisabler> list = performQueryForList("emaildisabler.findAndLockForUpdate", params);
        return new SearchResult<>(criteria, list);
    }

    @Override
    public List<EmailDisabler> findAllDisablingsOfTemplatesBelongingToGroup(long groupId) {
        return performQueryForList("emaildisabler.findAllOfTemplatesBelongingToGroup", groupId);
    }

    @Override
    public List<EmailDisabler> findAllDisablingsOfTemplate(int templateId) {
        return performQueryForList("emaildisabler.findAllOfTemplate", templateId);
    }

    @Override
    public NicHandleDetails getUserDetailsByNicHandle(String nicHandle) {
        return performQueryForObject("emaildisabler.getUserDetailsByNicHandle",
                Collections.singletonMap("nicHandle", nicHandle));
    }

}
