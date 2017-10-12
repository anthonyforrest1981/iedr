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
import pl.nask.crs.commons.email.dao.*;
import pl.nask.crs.commons.email.search.*;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;

public class EmailGroupServiceTest extends AbstractTest {
    @Resource
    EmailGroupDAO groupDao;
    @Resource
    HistoricalEmailGroupDAO histGroupDao;
    @Resource
    EmailTemplateDAO emailDao;
    @Resource
    HistoricalEmailTemplateDAO histEmailDao;
    @Resource
    EmailDisablerDAO disablerDao;
    @Resource
    EmailGroupService groupService;
    @Resource
    EmailTemplateService emailService;
    @Resource
    EmailDisablerService disablerService;

    @Test
    public void testFind() {
        EmailGroupSearchCriteria emptyCriteria = new EmailGroupSearchCriteria();

        SearchResult<EmailGroup> search_res = groupService.findEmailGroups(emptyCriteria, null);
        Assert.assertEquals(12, search_res.getResults().size(), "Find should return whole list of groups");

        LimitedSearchResult<EmailGroup> l_search_res = groupService.findEmailGroups(emptyCriteria, 0, 1, null);
        Assert.assertEquals(1, l_search_res.getResults().size(), "Limited Find should return only limit");
        Assert.assertEquals(12, l_search_res.getTotalResults(), "Limited Find should return total count");
    }

    @Test
    public void testGet() {
        EmailGroup grp = new EmailGroup("grp1");
        groupService.create(grp, new TestOpInfo("u1"));

        EmailGroup sgrp = groupService.get(grp.getId());
        Assert.assertNotNull(sgrp, "Service should be able to get group");
        Assert.assertEquals(grp.getName(), sgrp.getName(), "Group should have created group's name");
    }

    @Test
    public void testCreate() {
        EmailGroup grp = new EmailGroup("grp1");

        OpInfo actor = new TestOpInfo("u1");
        groupService.create(grp, actor);
        EmailGroup sgrp = groupService.get(grp.getId());
        Assert.assertNotNull(sgrp, "Service should be able to get group");
        Assert.assertEquals(grp.getName(), sgrp.getName(), "Group should have created group's name");

        HistoricalEmailGroupSearchCriteria groupCriteria = new HistoricalEmailGroupSearchCriteria();
        groupCriteria.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> histGroups = histGroupDao.find(groupCriteria);
        Assert.assertEquals(1, histGroups.getResults().size(),
                "There should be one historical entry for freshly created group");
        HistoricalObject<EmailGroup> histGroup = histGroups.getResults().get(0);
        Assert.assertEquals(actor.getActorName(), histGroup.getChangedBy(), "Changed by should be set");
        Assert.assertEquals(grp.getId(), histGroup.getObject().getId(), "Objects id should be the one just created");
    }

    @Test
    public void testUpdate() {
        OpInfo actor = new TestOpInfo("u1");
        EmailGroup grp = new EmailGroup("grp1");
        groupService.create(grp, actor);

        EmailTemplate tpl = emailDao.get(1);
        tpl.setGroup(grp);
        emailService.save(tpl, actor);
        EmailDisablerSuppressInfo disable = new EmailDisablerSuppressInfo(tpl.getId(), "IDL2-IEDR", true);
        disablerService.updateEmailStatus(Collections.singletonList(disable), actor);

        EmailDisabler disabler = disablerDao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNotNull(disabler, "Disabling should exist");
        Assert.assertTrue(disabler.isDisabled(),
                "Disablings for template should be gone as it's group is invisible now");

        grp.setName("grp1_changed");
        grp.setVisible(false);
        groupService.update(grp, actor);
        EmailGroup sgrp = groupService.get(grp.getId());
        Assert.assertNotNull(sgrp, "Service should be able to get group");
        Assert.assertEquals(grp.getName(), sgrp.getName(), "Group should have created group's name");
        Assert.assertFalse(sgrp.getVisible(), "Group should be marked as invisible");

        HistoricalEmailGroupSearchCriteria groupCriteria = new HistoricalEmailGroupSearchCriteria();
        groupCriteria.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> histGroups = histGroupDao.find(groupCriteria);
        Assert.assertEquals(histGroups.getResults().size(), 2, "There should be two historical entries for updated group");
        HistoricalObject<EmailGroup> histGroup = histGroups.getResults().get(1);
        Assert.assertEquals(actor.getActorName(), histGroup.getChangedBy(), "Changed by should be set");
        Assert.assertEquals(grp.getId(), histGroup.getObject().getId(), "Objects id should be the one just created");
        Assert.assertEquals(grp.getName(), histGroup.getObject().getName(), "History should have current name");
        Assert.assertFalse(histGroup.getObject().getVisible(), "History should have current visibility");

        // email should no longer be blocked since it's group is no longer visible
        disabler = disablerDao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNotNull(disabler, "Disabling still exists for this template");
        Assert.assertFalse(disabler.isDisabled(), "But it's now enabled as it's group is invisible");
    }

    @Test
    public void testDelete() {
        EmailGroup grp = new EmailGroup("grp1");
        TestOpInfo opInfo = new TestOpInfo("NH");
        groupService.create(grp, opInfo);

        final int TEMPLATE_ID = 1;
        EmailTemplate e = emailDao.get(TEMPLATE_ID);
        e.setGroup(grp);
        emailService.save(e, opInfo);

        OpInfo actor = new TestOpInfo("u1");
        groupService.delete(grp, actor);
        EmailGroup sgrp = groupService.get(grp.getId());
        Assert.assertNull(sgrp, "Service should not be able to get group");

        HistoricalEmailGroupSearchCriteria groupCriteria = new HistoricalEmailGroupSearchCriteria();
        groupCriteria.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> histGroups = histGroupDao.find(groupCriteria);
        Assert.assertEquals(histGroups.getResults().size(), 2, "There should be two historical entries for the group (create + delete)");
        HistoricalObject<EmailGroup> histGroup = histGroups.getResults().get(1);
        Assert.assertEquals(histGroup.getChangedBy(), actor.getActorName(), "Changed by should be set");
        Assert.assertEquals(histGroup.getObject().getId(), grp.getId(), "Objects id should be the one just deleted");
        Assert.assertEquals(histGroup.getObject().getName(), "grp1", "History should have last name");

        e = emailDao.get(TEMPLATE_ID);
        Assert.assertNull(e.getGroup(), "Template's group should be null after it was deleted");
        HistoricalEmailTemplateSearchCriteria templateCriteria = new HistoricalEmailTemplateSearchCriteria();
        templateCriteria.setId(e.getId());
        SearchResult<HistoricalObject<EmailTemplate>> histTemplates = histEmailDao.find(templateCriteria);
        Assert.assertEquals(histTemplates.getResults().size(), 4,
                "History of template should show new entry for deleted group");
        HistoricalObject<EmailTemplate> histTemplate = histTemplates.getResults().get(3);
        Assert.assertEquals(actor.getActorName(), histTemplate.getChangedBy(), "Changed by should be set");
        Assert.assertNull(histTemplate.getObject().getGroup(), "History of tpl should show null group");
    }
}
