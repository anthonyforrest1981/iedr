package pl.nask.crs.commons.email.service;

import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EmailTemplateServiceTest extends AbstractTest {
    @Resource
    EmailTemplateService service;

    @Resource
    EmailGroupDAO groupDAO;

    @Resource
    EmailGroupService groupService;

    @Resource
    HistoricalEmailTemplateDAO historicalTemplateDAO;

    @Resource
    EmailTemplateDAO templateDAO;

    @Resource
    EmailDisablerService disablerService;

    @Resource
    EmailDisablerDAO disablerDAO;

    @Test
    public void testSave() throws Exception {
        HistoricalEmailTemplateSearchCriteria criteria = new HistoricalEmailTemplateSearchCriteria();
        criteria.setId(1);
        SearchResult<HistoricalObject<EmailTemplate>> previousHistory = historicalTemplateDAO.find(criteria);
        assertEquals(previousHistory.getResults().size(), 2, "History for email template is prefilled with two entries");

        EmailGroup group = new EmailGroup("grp 1");
        groupService.create(group, new TestOpInfo("U1"));

        EmailTemplate e1 = templateDAO.get(1);
        e1.setGroup(group);
        service.save(e1, new TestOpInfo("U1"));

        SearchResult<HistoricalObject<EmailTemplate>> hist1 = historicalTemplateDAO.find(criteria);
        assertEquals(hist1.getResults().size(), 3, "History for email template is filled after save");
        assertEquals(hist1.getResults().get(2).getObject().getGroup().getId(), group.getId(),
                "In history we should have current object");

        e1.setGroup(null);
        service.save(e1, new TestOpInfo("U2"));

        SearchResult<HistoricalObject<EmailTemplate>> hist2 = historicalTemplateDAO.find(criteria);
        assertEquals(hist2.getResults().size(), 4, "History for email template is filled after save");
        Assert.assertNull(hist2.getResults().get(3).getObject().getGroup(), "In history we should have current object");
    }

    @Test
    public void testTemplateIsEnabledAfterAssignedToInvisibleGroup() {
        TestOpInfo opInfo = new TestOpInfo("U");
        EmailGroup group = new EmailGroup("Test invisible group");
        group.setVisible(false);
        groupService.create(group, opInfo);

        EmailTemplate template = templateDAO.get(1);
        EmailDisablerSuppressInfo suppressInfo = new EmailDisablerSuppressInfo(template.getId(), "IDL2-IEDR", true);
        disablerService.updateEmailStatus(Collections.singletonList(suppressInfo), opInfo);
        EmailDisabler disabler = disablerDAO.get(new EmailDisablerKey(template.getId(), "IDL2-IEDR"));

        assertNotNull(disabler, "Just created disabling should exist");
        assertTrue(disabler.isDisabled(), "For now template is blocked");

        template.setGroup(group);
        service.save(template, opInfo);

        disabler = disablerDAO.get(new EmailDisablerKey(template.getId(), "IDL2-IEDR"));

        assertNotNull(disabler, "Disabling still exists");
        Assert.assertFalse(disabler.isDisabled(), "Template assigned to invisible group should be enabled");
    }

    @Test
    public void testOnGroupDeleted() {
        EmailGroup group = new EmailGroup("grp1");
        groupService.create(group, new TestOpInfo("NH"));
        group = groupService.get(group.getId());

        templateDAO.lock(1);
        EmailTemplate template1 = templateDAO.get(1);
        template1.setGroup(group);
        templateDAO.updateUsingHistory(historicalTemplateDAO.create(template1, new Date(), "NH").getChangeId());
        templateDAO.lock(2);
        EmailTemplate template2 = templateDAO.get(2);
        template2.setGroup(group);
        templateDAO.updateUsingHistory(historicalTemplateDAO.create(template2, new Date(), "NH").getChangeId());

        template1 = templateDAO.get(1);
        template2 = templateDAO.get(2);
        assertNotNull(template1.getGroup(), "Email template 1 should have set group");
        assertEquals(template1.getGroup().getId(), group.getId(),
                "Email template 1 group should be the one just created");
        assertNotNull(template2.getGroup(), "Email template 2 should have set group");
        assertEquals(template2.getGroup().getId(), group.getId(),
                "Email template 2 group should be the one just created");

        HistoricalEmailTemplateSearchCriteria criteria1 = new HistoricalEmailTemplateSearchCriteria();
        criteria1.setId(1);
        HistoricalEmailTemplateSearchCriteria criteria2 = new HistoricalEmailTemplateSearchCriteria();
        criteria2.setId(2);

        SearchResult<HistoricalObject<EmailTemplate>> found1 = historicalTemplateDAO.find(criteria1);
        assertEquals(found1.getResults().size(), 3, "History for email template 1 should have 3 entries at start");
        SearchResult<HistoricalObject<EmailTemplate>> found2 = historicalTemplateDAO.find(criteria2);
        assertEquals(found2.getResults().size(), 3, "History for email template 2 should have 3 entries at start");

        service.onGroupDeleted(group, new TestOpInfo("a1"));

        template1 = templateDAO.get(1);
        template2 = templateDAO.get(2);
        assertTrue(template1.getGroup() == null, "Email template should be removed");
        assertTrue(template2.getGroup() == null, "Email template should be removed");

        found1 = historicalTemplateDAO.find(criteria1);
        assertEquals(found1.getResults().size(), 4, "History of 1st template should be filled after removing group");
        found2 = historicalTemplateDAO.find(criteria2);
        assertEquals(found2.getResults().size(), 4, "History of 2nd template should be filled after removing group");

    }

    @Test
    public void testEmptyGroupShouldBeNull() {
        EmailTemplate e = service.getEmailTemplate(170);
        Assert.assertNull(e.getGroup(), "Email template without assigned group should have it set to null");
    }

    @Test
    public void testAfterSettingNullGroupItShouldBeNull() {
        final int id = 170;
        final OpInfo op = new TestOpInfo("test user");
        EmailTemplate e = service.getEmailTemplate(id);
        EmailGroup group = new EmailGroup("Test group");
        groupService.create(group, op);
        e.setGroup(group);

        service.save(e, op);
        e = templateDAO.get(id);
        assertEquals(e.getGroup().getId(), group.getId(), "Should have this new group set");

        e.setGroup(null);
        service.save(e, op);
        e = templateDAO.get(id);
        Assert.assertNull(e.getGroup(), "Should have group set to null");
    }

    @Test
    public void testUpdateOnlyAllowedFields() {
        EmailTemplate tpl = service.getEmailTemplate(1);
        tpl.setSubject("Test subject");
        tpl.setText("Test text");
        tpl.setToList(Collections.singletonList("totest@test.ie"));
        tpl.setCcList(Collections.singletonList("cctest@test.ie"));
        tpl.setBccList(Collections.singletonList("bcctest@test.ie"));
        tpl.setInternalToList(Collections.singletonList("itotest@test.ie"));
        tpl.setInternalCcList(Collections.singletonList("icctest@test.ie"));
        tpl.setInternalBccList(Collections.singletonList("ibcctest@test.ie"));
        tpl.setActive(false);
        tpl.setHtml(true);
        tpl.setSendReason("Test send reason");
        tpl.setSuppressible(false);
        tpl.setNonSuppressibleReason("Test non suppressible reason");

        service.save(tpl, new TestOpInfo("NH"));

        EmailTemplate dbTemplate = service.getEmailTemplate(1);
        assertEquals(dbTemplate.getSubject(), "Deposit Top-up (DOA) - $ORDER_ID$", "Subject should not be changed");
        assertTrue(dbTemplate.getText().startsWith("Dear $BILL-C_NAME$"), "Text should not be changed");
        assertEquals(dbTemplate.getToList().size(), 1, "To list should still be size 1");
        assertEquals(dbTemplate.getToList().get(0), "$BILL-C_EMAIL$", "To list should not be changed");
        assertTrue(dbTemplate.getCcList().isEmpty(), "Cc list should not be changed");
        assertTrue(dbTemplate.getBccList().isEmpty(), "Bcc list should not be changed");
        assertTrue(dbTemplate.getInternalToList().isEmpty(), "Internal To list should not be changed");
        assertEquals(dbTemplate.getInternalCcList().size(), 1, "Internal Cc list should still be size 1");
        assertEquals(dbTemplate.getInternalCcList().get(0), "receipts@iedr.ie",
                "Internal Cc list should not be changed");
        assertEquals(dbTemplate.getInternalBccList().size(), 1, "Internal Bcc list should still be size 1");
        assertEquals(dbTemplate.getInternalBccList().get(0), "receipts@iedr.ie",
                "Internal Bcc list should not be changed");
        assertEquals(dbTemplate.isActive(), true, "Active should not change");
        assertEquals(dbTemplate.isHtml(), false, "Html should not change");
        assertEquals(dbTemplate.getSendReason(), "Test send reason", "Send reason should change");
        assertEquals(dbTemplate.isSuppressible(), false, "Suppressible should change");
        assertEquals(dbTemplate.getNonSuppressibleReason(), "Test non suppressible reason",
                "NonSuppressibleReason should change");
    }
}
