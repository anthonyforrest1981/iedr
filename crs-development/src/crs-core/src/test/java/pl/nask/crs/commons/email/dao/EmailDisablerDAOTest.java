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
import pl.nask.crs.commons.email.NicHandleDetails;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

public class EmailDisablerDAOTest extends AbstractTest {

    @Resource
    EmailDisablerDAO emailDisablerDAO;

    @Resource
    HistoricalEmailDisablerDAO historicalEmailDisablerDAO;

    @Test
    public void basicDAOTests() {
        long origCount = emailDisablerDAO.count(null);

        String nicHandle = "AA11-IEDR";
        int emailId = 42;
        int emailId2 = 43;
        Date aDate = DateUtils.truncate(new Date(), Calendar.SECOND);

        EmailDisablerSearchCriteria crit = new EmailDisablerSearchCriteria(null, nicHandle);

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(emailId), nicHandle, true, aDate);
        HistoricalEmailDisablerKey key = historicalEmailDisablerDAO.create(ed1, new Date(), nicHandle);
        emailDisablerDAO.updateUsingHistory(key.getChangeId());

        ed1 = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));

        Assert.assertEquals(ed1.getEmailTemplate().getId(), emailId);
        Assert.assertEquals(ed1.getNicHandle(), nicHandle);
        Assert.assertEquals(ed1.isDisabled(), true);
        Assert.assertFalse(aDate.after(ed1.getChangeDate()));

        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(emailId2), nicHandle, false, aDate);
        key = historicalEmailDisablerDAO.create(ed2, new Date(), nicHandle);
        emailDisablerDAO.updateUsingHistory(key.getChangeId());

        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId2, nicHandle));
        Assert.assertEquals(ed2.getEmailTemplate().getId(), emailId2);
        Assert.assertEquals(ed2.getNicHandle(), nicHandle);
        Assert.assertEquals(ed2.isDisabled(), false);
        Assert.assertFalse(aDate.after(ed2.getChangeDate()));

        Assert.assertEquals(emailDisablerDAO.find(null, 0, 1).getTotalResults(), origCount + 2);

        ed1.setDisabled(true);
        key = historicalEmailDisablerDAO.create(ed1, new Date(), nicHandle);
        emailDisablerDAO.updateUsingHistory(key.getChangeId());
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));
        Assert.assertNotNull(ed2);
        Assert.assertEquals(ed1.getEmailTemplate().getId(), ed2.getEmailTemplate().getId());
        Assert.assertEquals(ed1.getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(ed1.isDisabled(), ed2.isDisabled());
        Assert.assertFalse(ed1.getChangeDate().before(ed2.getChangeDate()));

        emailDisablerDAO.delete(ed1);
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));
        Assert.assertNull(ed2);
    }

    private EmailTemplate newEmailTemplateWithId(int emailId) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(emailId);
        return emailTemplate;
    }

    private void clearEmailDisabler() {
        List<EmailDisabler> forDeletion = emailDisablerDAO.find(null).getResults();
        for (EmailDisabler ed : forDeletion) {
            emailDisablerDAO.delete(ed);
        }
    }

    @Test
    public void sortingTest() throws Exception {
        clearEmailDisabler();
        Date changeDate = new Date();
        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(42), "AAA442-IEDR", true, changeDate);
        HistoricalEmailDisablerKey key = historicalEmailDisablerDAO.create(ed1, changeDate, "NH");
        emailDisablerDAO.updateUsingHistory(key.getChangeId());
        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(43), "AAA906-IEDR", true, changeDate);
        key = historicalEmailDisablerDAO.create(ed2, changeDate, "NH");
        emailDisablerDAO.updateUsingHistory(key.getChangeId());
        EmailDisabler ed3 = new EmailDisabler(newEmailTemplateWithId(42), "AAA967-IEDR", false, changeDate);
        key = historicalEmailDisablerDAO.create(ed3, changeDate, "NH");
        emailDisablerDAO.updateUsingHistory(key.getChangeId());

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("nicHandle", true));
        SearchResult<EmailDisabler> findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getEmailTemplate().getId(), ed1.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed1.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getEmailTemplate().getId(), ed2.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getEmailTemplate().getId(), ed3.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed3.getNicHandle());

        sortCrit.clear();
        sortCrit.add(new SortCriterion("emailId", false));
        sortCrit.add(new SortCriterion("nicHandle", true));
        findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed1.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed3.getNicHandle());

        sortCrit.clear();
        sortCrit.add(new SortCriterion("disabled", false));
        sortCrit.add(new SortCriterion("nicHandle", false));
        findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed3.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed1.getNicHandle());
    }

    @Test
    public void contactCountTest() throws Exception {
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("NTG1-IEDR", "theweb.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("ABC718-IEDR", "theweb.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT5-IEDR", "thedomain-msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT1-IEDR", "thedomain-msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "thedomain-msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT4-IEDR", "thedomain-totransfer2.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "thedomain-totransfer2.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "obrienpr.ie"));
    }

    @Test
    public void returnsNullWhenEmailDisablerIsNotPresentInDatabase() {
        String nicHandle = "AA11-IEDR";
        int emailId = 1;

        EmailDisabler result = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));

        Assert.assertTrue(result == null);
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        EmailDisabler ed = emailDisablerDAO.get(new EmailDisablerKey(951, "IDE\u03082-IEDR"));
        Assert.assertNotNull(ed);
    }

    @Test
    public void testFindUnnormalizedUtf8() {
        EmailDisablerSearchCriteria criteria = new EmailDisablerSearchCriteria();
        criteria.setNicHandle("IDE\u03082-IEDR");
        SearchResult<EmailDisabler> result = emailDisablerDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void testFindWithEmailTempAndEmailGroupUnnormalizedUtf8() {
        List<EmailDisabler> result = emailDisablerDAO.findWithEmailTempAndEmailGroup("IDE\u03082-IEDR");
        Assert.assertEquals(result.size(), 99);
        for (EmailDisabler ed : result) {
            if (ed.getEmailTemplate().getId() == 951)
                Assert.assertTrue(ed.isDisabled(), "Email template 950 should be disabled");
            else
                Assert.assertFalse(ed.isDisabled(), "Email template " + ed.getEmailTemplate().getId()
                        + " should be enabled");
        }
    }

    @Test
    public void testGetNicHandleDetailsUnnormalizedUtf8() {
        NicHandleDetails details = emailDisablerDAO.getUserDetailsByNicHandle("IDE\u03082-IEDR");
        Assert.assertNotNull(details);
        Assert.assertEquals(details.getName(), "NicHandlë name");
        Assert.assertEquals(details.getEmail(), "NicHandlë@email.ie");
    }

}
