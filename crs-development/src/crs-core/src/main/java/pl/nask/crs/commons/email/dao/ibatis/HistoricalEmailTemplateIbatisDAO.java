package pl.nask.crs.commons.email.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailTemplateIbatisDAO extends
        GenericIBatisDAO<HistoricalObject<EmailTemplate>, HistoricalEmailTemplateKey> implements
        HistoricalEmailTemplateDAO {


    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "E_ID");
        sortMap.put("subject", "E_Subject");
        sortMap.put("group_id", "e.EG_ID");
        sortMap.put("sendReason", "E_Send_Reason");
        sortMap.put("suppressible", "E_Suppressible");
    }

    HistoricalEmailTemplateIbatisDAO() {
        setGetQueryId("histemailtemplate.get");
        setFindQueryId("histemailtemplate.find");
        setCountFindQueryId("histemailtemplate.count");
        setCreateQueryId("histemailtemplate.create");
        setSortMapping(sortMap);
    }

    @Override
    public HistoricalEmailTemplateKey create(EmailTemplate emailTemplate, Date changeDate, String changedBy) {
        HistoricalObject<EmailTemplate> dto = new HistoricalObject<>(emailTemplate, changeDate, changedBy);
        this.create(dto);
        return new HistoricalEmailTemplateKey(emailTemplate.getId(), dto.getChangeId());
    }
}
