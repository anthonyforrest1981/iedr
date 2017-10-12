package pl.nask.crs.commons.email.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailDisablerIbatisDAO extends
        GenericIBatisDAO<HistoricalObject<EmailDisabler>, HistoricalEmailDisablerKey> implements
        HistoricalEmailDisablerDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("emailId", "emailId");
        sortMap.put("nicHandle", "nicHandle");
        sortMap.put("disabled", "disabled");
        sortMap.put("changeDate", "changeDate");
        sortMap.put("histChangedBy", "histChangedBy");
        sortMap.put("histChangeDate", "histChangeDate");
        sortMap.put("changeId", "changeId");
    }

    HistoricalEmailDisablerIbatisDAO() {
        setGetQueryId("histemaildisabler.get");
        setCreateQueryId("histemaildisabler.create");
        setFindQueryId("histemaildisabler.find");
        setCountFindQueryId("histemaildisabler.count");
        setSortMapping(sortMap);
    }

    @Override
    public HistoricalEmailDisablerKey create(EmailDisabler emailDisabler, Date changeDate, String changedBy) {
        HistoricalObject<EmailDisabler> dto = new HistoricalObject<>(emailDisabler, changeDate, changedBy);
        this.create(dto);
        return new HistoricalEmailDisablerKey(emailDisabler.getEmailTemplate().getId(), emailDisabler.getNicHandle(),
                dto.getChangeId());
    }
}
