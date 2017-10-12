package pl.nask.crs.commons.email.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailDisablerDAOTest extends AbstractTest {
    @Resource
    EmailDisablerDAO emailDisablerDAO;

    @Resource
    HistoricalEmailDisablerDAO historicalEmailDisablerDAO;

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailDisablerDAO.count(null);

        String nicHandle = "AA11-IEDR";
        long emailId = 42;
        EmailTemplate emailTemplate = newEmailTemplateWithId(42);
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);
        EmailDisabler ed1 = new EmailDisabler(emailTemplate, nicHandle, true, aDate);

        final HistoricalObject<EmailDisabler> histEd1 = new HistoricalObject<>(ed1, aDate2, "somebody");
        historicalEmailDisablerDAO.create(histEd1);
        Assert.assertEquals(historicalEmailDisablerDAO.find(null, 0, 1).getTotalResults(), origCount + 1);

        HistoricalObject<EmailDisabler> histDis1 = historicalEmailDisablerDAO.get(
                new HistoricalEmailDisablerKey(emailId, nicHandle, histEd1.getChangeId()));
        Assert.assertNotNull(histDis1);
        Assert.assertEquals(histDis1.getChangeDate(), DateUtils.truncate(aDate2, Calendar.SECOND));

        HistoricalEmailDisablerSearchCriteria criteria = new HistoricalEmailDisablerSearchCriteria();
        criteria.setEmailId(emailId);
        criteria.setNicHandle(nicHandle);
        LimitedSearchResult<HistoricalObject<EmailDisabler>> findRes = historicalEmailDisablerDAO.find(criteria, 0, 1);

        Assert.assertEquals(findRes.getTotalResults(), 1);
        HistoricalObject<EmailDisabler> emailDisablerHistoricalObject = findRes.getResults().get(0);
        EmailDisabler emailDisabler = emailDisablerHistoricalObject.getObject();
        Assert.assertEquals(emailDisabler.getEmailTemplate().getId(), histDis1.getObject().getEmailTemplate().getId());
        Assert.assertEquals(emailDisabler.getNicHandle(), histDis1.getObject().getNicHandle());
        Assert.assertEquals(emailDisabler.isDisabled(), histDis1.getObject().isDisabled());
        Assert.assertEquals(emailDisabler.getChangeDate(), histDis1.getObject().getChangeDate());
        Assert.assertEquals(emailDisablerHistoricalObject.getChangeDate(), histDis1.getChangeDate());
        Assert.assertEquals(emailDisablerHistoricalObject.getChangedBy(), histDis1.getChangedBy());
    }

    private void compareHED(HistoricalObject<EmailDisabler> actual, HistoricalObject<EmailDisabler> expected) {
        Assert.assertEquals(actual.getObject().getEmailTemplate().getId(), expected.getObject().getEmailTemplate()
                .getId());
        Assert.assertEquals(actual.getObject().getNicHandle(), expected.getObject().getNicHandle());
        Assert.assertEquals(actual.getChangeDate(), DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND));
    }

    @Test
    public void sortingTest() throws Exception {
        int origCount = historicalEmailDisablerDAO.count(null);

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(42), "AAA442-IEDR", true, aDate);
        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(43), "AAA906-IEDR", true, aDate);
        EmailDisabler ed3 = new EmailDisabler(newEmailTemplateWithId(42), "AAA967-IEDR", false, aDate);

        HistoricalObject<EmailDisabler> hed1 = new HistoricalObject<>(ed1, aDate, "author 1");
        historicalEmailDisablerDAO.create(hed1);
        HistoricalObject<EmailDisabler> hed2 = new HistoricalObject<>(ed1, aDate2, "author 2");
        historicalEmailDisablerDAO.create(hed2);
        HistoricalObject<EmailDisabler> hed3 = new HistoricalObject<>(ed2, aDate, "author 1");
        historicalEmailDisablerDAO.create(hed3);
        HistoricalObject<EmailDisabler> hed4 = new HistoricalObject<>(ed3, aDate, "author 2");
        historicalEmailDisablerDAO.create(hed4);

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("emailId", true));
        sortCrit.add(new SortCriterion("histChangedBy", false));
        sortCrit.add(new SortCriterion("nicHandle", false));
        sortCrit.add(new SortCriterion("changeDate", false));
        sortCrit.add(new SortCriterion("disabled", false));
        sortCrit.add(new SortCriterion("histChangeDate", false));

        SearchResult<HistoricalObject<EmailDisabler>> findRes = historicalEmailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().size(), origCount + 4);
        compareHED(findRes.getResults().get(0), hed4);
        compareHED(findRes.getResults().get(1), hed2);
        compareHED(findRes.getResults().get(2), hed1);
        compareHED(findRes.getResults().get(3), hed3);
    }

    private EmailTemplate newEmailTemplateWithId(int emailId) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(emailId);
        return emailTemplate;
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        HistoricalObject<EmailDisabler> hed = historicalEmailDisablerDAO.get(new HistoricalEmailDisablerKey(951,
                "IDE\u03082-IEDR", 2));
        Assert.assertNotNull(hed);
        Assert.assertEquals(hed.getChangedBy(), "IDË2-IEDR");
    }

    @Test
    public void testCreateUnnormalizedUtf8() {
        EmailDisabler ed = emailDisablerDAO.get(new EmailDisablerKey(951, "IDE\u03082-IEDR"));
        HistoricalObject<EmailDisabler> hed = new HistoricalObject<>(ed, new Date(), "IDE\u03082-IEDR");
        historicalEmailDisablerDAO.create(hed);
        HistoricalObject<EmailDisabler> dbhed = historicalEmailDisablerDAO.get(new HistoricalEmailDisablerKey(951,
                "IDE\u03082-IEDR", hed.getChangeId()));
        Assert.assertNotNull(dbhed);
        Assert.assertEquals(dbhed.getChangedBy(), "IDË2-IEDR");
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalEmailDisablerKey key = new HistoricalEmailDisablerKey(951, "IDL2-IEDR\01", 100);
        HistoricalObject<EmailDisabler> histDisabler = historicalEmailDisablerDAO.get(key);
        Assert.assertNotNull(histDisabler);
        Assert.assertEquals(histDisabler.getChangedBy(), "IDL2-IEDR\01");
    }
}
