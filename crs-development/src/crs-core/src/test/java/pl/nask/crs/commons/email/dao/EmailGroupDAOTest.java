package pl.nask.crs.commons.email.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

public class EmailGroupDAOTest extends AbstractTest {
    @Resource
    EmailGroupDAO emailGroupDAO;
    @Resource
    HistoricalEmailGroupDAO historicalEmailGroupDAO;

    @Test
    public void basicDAOTests() {
        long origCount = emailGroupDAO.find(null, 0, 1).getTotalResults();

        // Create a group...
        EmailGroup g1 = new EmailGroup("group 1");
        Assert.assertEquals(g1.getId(), -1L);
        Assert.assertTrue(g1.getVisible());
        emailGroupDAO.create(g1);
        Assert.assertNotEquals(g1.getId(), -1L);
        Assert.assertEquals(emailGroupDAO.find(null, 0, 1).getTotalResults(), origCount + 1);

        // Read it back...
        EmailGroup g2 = emailGroupDAO.get(g1.getId());
        Assert.assertEquals(g2.getName(), g1.getName());
        Assert.assertEquals(g2.getVisible(), g1.getVisible());
        Assert.assertNotNull(g2.getChangeDate());

        // Find it...
        LimitedSearchResult<EmailGroup> findRes = emailGroupDAO.find(
                new EmailGroupSearchCriteria(g1.getId(), g1.getName()), 0, 1);
        Assert.assertEquals(findRes.getTotalResults(), 1L);
        Assert.assertEquals(findRes.getResults().get(0).getId(), g2.getId());
        Assert.assertEquals(findRes.getResults().get(0).getName(), g2.getName());
        Assert.assertEquals(findRes.getResults().get(0).getChangeDate(), g2.getChangeDate());

        // Update it...
        String newName = "updated group 1";
        g1.setName(newName);
        g1.setVisible(false);
        g1.setChangeDate(new Date());
        HistoricalEmailGroupKey key = historicalEmailGroupDAO.create(g1, new Date(), "NH");
        emailGroupDAO.updateUsingHistory(key.getChangeId());
        // Check that id stayed the same
        Assert.assertEquals(g1.getId(), g2.getId());
        // Re-read and verify
        g2 = emailGroupDAO.get(g1.getId());
        Assert.assertEquals(g1.getId(), g2.getId());
        Assert.assertEquals(g1.getName(), g2.getName());
        Assert.assertEquals(g1.getVisible(), g2.getVisible());

        // Remove it...
        emailGroupDAO.delete(g1);
        findRes = emailGroupDAO.find(new EmailGroupSearchCriteria(g1.getId(), g1.getName()), 0, 1);
        Assert.assertEquals(findRes.getTotalResults(), 0L);
        findRes = emailGroupDAO.find(null, 0, 1);
        Assert.assertEquals(findRes.getTotalResults(), origCount);
    }

    private void clearEmailGroups() {
        for (EmailGroup g : emailGroupDAO.find(null).getResults()) {
            emailGroupDAO.delete(g);
        }
    }

    @Test
    public void selectionTest() {
        clearEmailGroups();

        EmailGroup g1 = new EmailGroup("a group");
        emailGroupDAO.create(g1);
        EmailGroup g2 = new EmailGroup("b group");
        emailGroupDAO.create(g2);
        EmailGroup g3 = new EmailGroup("0 group");
        emailGroupDAO.create(g3);

        List<SortCriterion> sort = new LinkedList<SortCriterion>();
        sort.add(new SortCriterion("id", true));
        SearchResult<EmailGroup> findRes = emailGroupDAO.find(null, sort);
        Assert.assertTrue(findRes.getResults().get(0).getId() < findRes.getResults().get(1).getId()
                && findRes.getResults().get(1).getId() < findRes.getResults().get(2).getId());

        sort.clear();
        sort.add(new SortCriterion("name", false));
        sort.add(new SortCriterion("id", true));
        findRes = emailGroupDAO.find(null, sort);
        Assert.assertTrue(findRes.getResults().get(0).getName().compareTo(findRes.getResults().get(1).getName()) > 0
                && findRes.getResults().get(1).getName().compareTo(findRes.getResults().get(2).getName()) > 0);

        EmailGroupSearchCriteria crit = new EmailGroupSearchCriteria(null, "0 group");

        findRes = emailGroupDAO.find(crit);
        Assert.assertEquals(findRes.getResults().size(), 1);
    }

    @Test
    public void substringName() throws Exception {
        clearEmailGroups();

        EmailGroup g1 = new EmailGroup("a group");
        emailGroupDAO.create(g1);
        EmailGroup g2 = new EmailGroup("b group");
        emailGroupDAO.create(g2);

        SearchResult<EmailGroup> findRes = emailGroupDAO.find(new EmailGroupSearchCriteria(null, "gro"));
        Assert.assertEquals(findRes.getResults().size(), 2);
        findRes = emailGroupDAO.find(new EmailGroupSearchCriteria(null, "a"));
        Assert.assertEquals(findRes.getResults().size(), 1);
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        EmailGroup group = emailGroupDAO.get(100l);
        Assert.assertEquals(group.getName(), "Unnörmalized group");
    }

    @Test
    public void testFindUnnormalizedUtf8() {
        EmailGroupSearchCriteria criteria = new EmailGroupSearchCriteria();
        criteria.setName("No\u0308rmalized group");
        SearchResult<EmailGroup> result = emailGroupDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void testCreateUnnormalizedUtf8() {
        EmailGroup newGroup = new EmailGroup();
        newGroup.setName("New unno\u0308rmalized name");
        newGroup.setChangeDate(new Date());
        emailGroupDAO.create(newGroup);
        EmailGroupSearchCriteria criteria = new EmailGroupSearchCriteria();
        criteria.setName("New unno\u0308rmalized name");
        SearchResult<EmailGroup> result = emailGroupDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
    }
}
