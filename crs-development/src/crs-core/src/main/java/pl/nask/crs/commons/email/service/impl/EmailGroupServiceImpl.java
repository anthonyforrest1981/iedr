package pl.nask.crs.commons.email.service.impl;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailGroupDAO;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.commons.email.service.EmailDisablerService;
import pl.nask.crs.commons.email.service.EmailGroupService;
import pl.nask.crs.commons.email.service.EmailTemplateService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

public class EmailGroupServiceImpl implements EmailGroupService {

    private EmailGroupDAO dao;
    private HistoricalEmailGroupDAO histDao;
    private EmailTemplateService emailService;
    private EmailDisablerService disablerService;

    public EmailGroupServiceImpl(EmailGroupDAO dao, HistoricalEmailGroupDAO histDao, EmailTemplateService emailService,
            EmailDisablerService disablerService) {
        this.dao = dao;
        this.histDao = histDao;
        this.emailService = emailService;
        this.disablerService = disablerService;
    }

    @Override
    public LimitedSearchResult<EmailGroup> findEmailGroups(SearchCriteria<EmailGroup> criteria, long offset,
            long limit, List<SortCriterion> orderBy) {
        return dao.find(criteria, offset, limit, orderBy);
    }

    @Override
    public SearchResult<EmailGroup> findEmailGroups(SearchCriteria<EmailGroup> criteria, List<SortCriterion> orderBy) {
        return dao.find(criteria, orderBy);
    }

    @Override
    public EmailGroup get(long id) {
        return dao.get(id);
    }

    @Override
    public void create(EmailGroup group, OpInfo op) {
        Date changeDate = new Date();
        group.setChangeDate(changeDate);
        dao.create(group);
        HistoricalEmailGroupKey historicalEmailGroupKey = histDao.create(group, changeDate, op.getActorName());
        dao.updateUsingHistory(historicalEmailGroupKey.getChangeId());
    }

    @Override
    public void update(EmailGroup group, OpInfo op) {
        if (!group.getVisible()) {
            EmailTemplateSearchCriteria criteria = new EmailTemplateSearchCriteria();
            criteria.setGroupId(group.getId());
            List<EmailTemplate> affected = emailService.findEmailTemplate(criteria).getResults();
            for (EmailTemplate template : affected) {
                disablerService.removeDisablingsOfTemplate(template.getId(), op);
            }
        }
        Date changeDate = new Date();
        group.setChangeDate(changeDate);
        HistoricalEmailGroupKey histKey = histDao.create(group, changeDate, op.getActorName());
        dao.updateUsingHistory(histKey.getChangeId());
    }

    @Override
    public void delete(EmailGroup group, OpInfo op) {
        emailService.onGroupDeleted(group, op);
        histDao.create(group, new Date(), op.getActorName());
        dao.delete(group);
    }

}
