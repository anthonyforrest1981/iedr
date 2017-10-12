package pl.nask.crs.commons.email.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerSearchCriteria;

public class EmailDisablerServiceTest extends AbstractTest {
    @Resource
    EmailDisablerService disablerService;
    @Resource
    EmailDisablerCheckService disablerCheckService;

    @Resource
    EmailDisablerDAO emailDisablerDao;
    @Resource
    HistoricalEmailDisablerDAO historicalEmailDisablerDao;
    @Resource
    EmailTemplateService emailTemplateService;
    @Resource
    EmailGroupService emailGroupService;

    private class LocalEmailParameters implements EmailParameters {
        private String domainName;
        private String owner;
        private String gaining;
        private String user;

        public LocalEmailParameters(String domainName, String owner, String gaining, String user) {
            this.domainName = domainName;
            this.owner = owner;
            this.gaining = gaining;
            this.user = user;
        }

        @Override
        public List<? extends ParameterName> getParameterNames() {
            return null;
        }

        @Override
        public String getParameterValue(String name, boolean html) {
            return null;
        }

        @Override
        public String getLoggedInNicHandle() {
            return user;
        }

        @Override
        public String getAccountRelatedNicHandle(boolean gaining) {
            return gaining ? this.gaining : owner;
        }

        @Override
        public String getDomainName() {
            return domainName;
        }
    }

    private boolean shouldSendToExternal(int templateId, String domainName, String owner, String user) {
        return shouldSendToExternal(templateId, domainName, owner, owner, user);
    }

    private boolean shouldSendToExternal(int templateId, String domainName, String owner, String gaining, String user) {
        EmailTemplate template = emailTemplateService.getEmailTemplate(templateId);
        EmailParameters params = new LocalEmailParameters(domainName, owner, gaining, user);
        return disablerCheckService.shouldSendToExternal(template, params);
    }

    @Test
    public void simpleServiceTest() throws Exception {
        disablerService.isTemplateDisabledForNH(44, "pizaonline.ie");

        // Should not send
        Assert.assertFalse(shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - nonsuppressible
        Assert.assertTrue(shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - nonsuppressible but disabled
        Assert.assertTrue(shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - suppressible but not disabled
        Assert.assertTrue(shouldSendToExternal(902, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - suppressible, disabled but user is tech or admin
        Assert.assertTrue(shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "AGZ082-IEDR"));
        // Should not send - blocked by a losing
        Assert.assertFalse(shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "AGZ082-IEDR", "IDL2-IEDR"));
        // Should send - gaining not affected
        Assert.assertTrue(shouldSendToExternal(900, "pizzaonline.ie", "AGZ082-IEDR", "IDL2-IEDR", "IDL2-IEDR"));
        // Should not send - blocked by a gaining
        Assert.assertFalse(shouldSendToExternal(903, "pizzaonline.ie", "AGZ082-IEDR", "IDL2-IEDR", "IDL2-IEDR"));
        // Should not send - losing not affected
        Assert.assertTrue(shouldSendToExternal(903, "pizzaonline.ie", "IDL2-IEDR", "AGZ082-IEDR", "IDL2-IEDR"));
        // Should send - unknown owner
        Assert.assertTrue(shouldSendToExternal(42, "pizzaonline.ie", "N/A", "IDL2-IEDR"));
        // And some null testing:
        Assert.assertFalse(shouldSendToExternal(900, null, "IDL2-IEDR", null));
    }

    @Test
    public void updateEmailStatusTest() throws Exception {

        Assert.assertFalse(shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        Assert.assertTrue(shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));

        List<EmailDisablerSuppressInfo> emaildisablerInfo = new LinkedList<EmailDisablerSuppressInfo>();
        emaildisablerInfo.add(new EmailDisablerSuppressInfo(900, "IDL2-IEDR", false));
        emaildisablerInfo.add(new EmailDisablerSuppressInfo(901, "IDL2-IEDR", true));

        List<EmailDisablerSuppressInfo> persistedEmailDisablerInfo = this.disablerService.updateEmailStatus(
                emaildisablerInfo, new TestOpInfo("IDL2-IEDR"));
        Assert.assertTrue(persistedEmailDisablerInfo.size() == 1);
        Assert.assertTrue(persistedEmailDisablerInfo.get(0).getEmailId() == 900);

        Assert.assertTrue(shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        Assert.assertTrue(shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
    }

    @Test
    public void testShouldntBlockTemplateInInvisibleGroup() {
        EmailTemplate tpl = emailTemplateService.getEmailTemplate(1);
        EmailGroup grp = new EmailGroup("Test group");
        grp.setVisible(false);
        TestOpInfo opInfo = new TestOpInfo("NH");
        emailGroupService.create(grp, opInfo);
        tpl.setGroup(grp);
        emailTemplateService.save(tpl, opInfo);

        EmailDisabler dis = emailDisablerDao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNull(dis, "Template is not yet disabled");
    }

    @Test
    public void testRemoveDisablingsOfTemplate() {
        List<EmailDisablerSuppressInfo> infos = new ArrayList<>();
        infos.add(new EmailDisablerSuppressInfo(1, "NTG1-IEDR", true));
        infos.add(new EmailDisablerSuppressInfo(1, "ABC718-IEDR", true));
        infos.add(new EmailDisablerSuppressInfo(1, "APIT5-IEDR", false));
        disablerService.updateEmailStatus(infos, new TestOpInfo("NH"));

        Assert.assertTrue(disablerService.isTemplateDisabledForNH(1, "NTG1-IEDR"));
        Assert.assertTrue(disablerService.isTemplateDisabledForNH(1, "ABC718-IEDR"));
        Assert.assertFalse(disablerService.isTemplateDisabledForNH(1, "APIT5-IEDR"));
        HistoricalEmailDisablerSearchCriteria find_for_u1 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u1.setNicHandle("NTG1-IEDR");
        find_for_u1.setEmailId(1l);
        int hist_u1_count = historicalEmailDisablerDao.find(find_for_u1).getResults().size();
        HistoricalEmailDisablerSearchCriteria find_for_u2 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u2.setNicHandle("ABC718-IEDR");
        find_for_u2.setEmailId(1l);
        int hist_u2_count = historicalEmailDisablerDao.find(find_for_u2).getResults().size();
        HistoricalEmailDisablerSearchCriteria find_for_u3 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u3.setNicHandle("APIT5-IEDR");
        find_for_u3.setEmailId(1l);
        int hist_u3_count = historicalEmailDisablerDao.find(find_for_u3).getResults().size();

        disablerService.removeDisablingsOfTemplate(1, new TestOpInfo("actor"));

        EmailDisabler ed1 = emailDisablerDao.get(new EmailDisablerKey(1, "NTG1-IEDR"));
        EmailDisabler ed2 = emailDisablerDao.get(new EmailDisablerKey(1, "ABC718-IEDR"));
        EmailDisabler ed3 = emailDisablerDao.get(new EmailDisablerKey(1, "APIT5-IEDR"));
        Assert.assertFalse(ed1.isDisabled(), "u1 should have email enabled again");
        Assert.assertFalse(ed2.isDisabled(), "u2 should have email enabled again");
        Assert.assertFalse(ed3.isDisabled(), "u3 should have email enabled still");

        int u1_count = historicalEmailDisablerDao.find(find_for_u1).getResults().size();
        int u2_count = historicalEmailDisablerDao.find(find_for_u2).getResults().size();
        int u3_count = historicalEmailDisablerDao.find(find_for_u3).getResults().size();
        Assert.assertEquals(hist_u1_count + 1, u1_count, "First user should have new history entry");
        Assert.assertEquals(hist_u2_count + 1, u2_count, "Second user should have new history entry");
        Assert.assertEquals(hist_u3_count, u3_count, "Third user was already enabled, no history for him");
    }

    @Test
    public void testShouldSendToExternal() {
        String domain = "autocreated.ie";
        String owner = "APIT5-IEDR";
        String tech = "APIT2-IEDR";
        String admin = "APIT1-IEDR";
        String randomNH = "random";
        int templateId = 11;

        // without disabling should sent to all
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, tech),
                "Missing disabling, should send to tech");
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, admin),
                "Missing disabling, should send to admin");
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, randomNH),
                "Missing disabling, should send to random");

        List<EmailDisablerSuppressInfo> infos = new ArrayList<>();
        infos.add(new EmailDisablerSuppressInfo(templateId, owner, false));
        TestOpInfo opInfo = new TestOpInfo("NH");
        disablerService.updateEmailStatus(infos, opInfo);

        //disabling exists but passes, should sent to everyone
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, tech), "Enabled, should send to tech");
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, admin), "Enabled, should send to admin");
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, randomNH), "Enabled, should send to random");

        infos.get(0).setDisabled(true);
        disablerService.updateEmailStatus(infos, opInfo);
        // disabled, should send to tech and admin and not to random
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, tech), "Disabled, should send to tech");
        Assert.assertTrue(shouldSendToExternal(templateId, domain, owner, admin), "Disabled, should send to tech");
        Assert.assertFalse(shouldSendToExternal(templateId, domain, owner, randomNH),
                "Disabled, should not send to random");
    }

}
