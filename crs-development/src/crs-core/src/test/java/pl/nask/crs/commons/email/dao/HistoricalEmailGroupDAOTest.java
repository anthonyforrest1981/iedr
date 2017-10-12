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
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailGroupDAOTest extends AbstractTest {
    @Resource
    EmailGroupDAO emailGroupDAO;

    @Resource
    HistoricalEmailGroupDAO historicalEmailGroupDAO;

    private void compareHEG(HistoricalObject<EmailGroup> actual, HistoricalObject<EmailGroup> expected) {
        Assert.assertEquals(actual.getObject().getId(), expected.getObject().getId());
        Assert.assertEquals(actual.getObject().getName(), expected.getObject().getName());
        Assert.assertEquals(actual.getObject().getChangeDate(), expected.getObject().getChangeDate());
        Assert.assertEquals(actual.getChangedBy(), expected.getChangedBy());
        Assert.assertEquals(actual.getChangeDate(), DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND));
    }

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailGroupDAO.count(null);

        // A group...
        EmailGroup g1 = new EmailGroup("group 1");
        emailGroupDAO.create(g1);
        g1 = emailGroupDAO.get(g1.getId());
        // becomes history...
        // because of type conversion, we need a Date that ends on a full second
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        HistoricalObject<EmailGroup> histGrp1 = new HistoricalObject<EmailGroup>(g1, aDate, "somebody");
        historicalEmailGroupDAO.create(histGrp1);
        Assert.assertEquals(historicalEmailGroupDAO.find(null, 0, 1).getTotalResults(), origCount + 1);
        HistoricalObject<EmailGroup> histGrp2 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(),
                histGrp1.getChangeId()));

        // It's weird, but if the order of parameters in the line below is
        // reversed, the test does not pass. It's because change date in one
        // of the objects is a Date and is a Timestamp in the other.
        // Apparently Timestamp.equals can deal with Dates but not the other
        // way around.
        compareHEG(histGrp2, histGrp1);

        HistoricalEmailGroupSearchCriteria crit = new HistoricalEmailGroupSearchCriteria();
        crit.setId(g1.getId());
        LimitedSearchResult<HistoricalObject<EmailGroup>> findRes = historicalEmailGroupDAO.find(crit, 0, 1);

        Assert.assertEquals(findRes.getTotalResults(), 1);
        compareHEG(findRes.getResults().get(0), histGrp2);
    }

    @Test
    public void sortingTest() {
        EmailGroup g1 = new EmailGroup("group 1");
        emailGroupDAO.create(g1);
        g1 = emailGroupDAO.get(g1.getId());
        EmailGroup g2 = new EmailGroup("group 2");
        emailGroupDAO.create(g2);
        g2 = emailGroupDAO.get(g2.getId());
        EmailGroup g3 = new EmailGroup("group 3");
        emailGroupDAO.create(g3);
        g3 = emailGroupDAO.get(g3.getId());

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);

        HistoricalObject<EmailGroup> histGrp1 = new HistoricalObject<EmailGroup>(g1, aDate, "somebody");
        historicalEmailGroupDAO.create(histGrp1);
        histGrp1 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(), histGrp1.getChangeId()));
        HistoricalObject<EmailGroup> histGrp2 = new HistoricalObject<EmailGroup>(g1, aDate2, "somebody");
        historicalEmailGroupDAO.create(histGrp2);
        histGrp2 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(), histGrp2.getChangeId()));
        HistoricalObject<EmailGroup> histGrp3 = new HistoricalObject<EmailGroup>(g2, aDate, "someone");
        historicalEmailGroupDAO.create(histGrp3);
        histGrp3 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g2.getId(), histGrp3.getChangeId()));
        HistoricalObject<EmailGroup> histGrp4 = new HistoricalObject<EmailGroup>(g3, aDate2, "someone");
        historicalEmailGroupDAO.create(histGrp4);
        histGrp4 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g3.getId(), histGrp4.getChangeId()));

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("histChangedBy", false));
        sortCrit.add(new SortCriterion("name", true));
        sortCrit.add(new SortCriterion("id", false));
        sortCrit.add(new SortCriterion("histChangeDate", true));
        sortCrit.add(new SortCriterion("changeDate", true));

        SearchResult<HistoricalObject<EmailGroup>> findRes = historicalEmailGroupDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().size(), 17);
        compareHEG(findRes.getResults().get(0), histGrp3);
        compareHEG(findRes.getResults().get(1), histGrp4);
        compareHEG(findRes.getResults().get(2), histGrp1);
        compareHEG(findRes.getResults().get(3), histGrp2);
    }

    @Test
    public void getUnnormalizedUtf8() {
        HistoricalObject<EmailGroup> heg = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(100, 1));
        Assert.assertNotNull(heg);
        Assert.assertEquals(heg.getChangedBy(), "IDL2-IËDP");
    }

    @Test
    public void createUnnormalizedUtf8() {
        EmailGroup group = emailGroupDAO.get(101l);
        HistoricalObject<EmailGroup> histGroup = new HistoricalObject<>(group, new Date(), "IDE\u03083-IEDR");
        historicalEmailGroupDAO.create(histGroup);

        HistoricalEmailGroupSearchCriteria criteria = new HistoricalEmailGroupSearchCriteria();
        criteria.setId(101l);
        SearchResult<HistoricalObject<EmailGroup>> result = historicalEmailGroupDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 2);
        Assert.assertEquals(result.getResults().get(0).getChangedBy(), "IDË2-IEDR");
        Assert.assertEquals(result.getResults().get(1).getChangedBy(), "IDË3-IEDR");
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalEmailGroupKey key = new HistoricalEmailGroupKey(100, 3);
        HistoricalObject<EmailGroup> histGroup = historicalEmailGroupDAO.get(key);
        Assert.assertNotNull(histGroup);
        Assert.assertEquals(histGroup.getObject().getName(), "Email group\01");
    }
}
