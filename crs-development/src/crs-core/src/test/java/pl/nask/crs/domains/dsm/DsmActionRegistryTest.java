package pl.nask.crs.domains.dsm;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.actions.InternalDsmAction;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

import javax.annotation.Resource;

public class DsmActionRegistryTest {

    @Resource
    ApplicationConfig applicationConfig;

    @Resource
    EmailTemplateSender emailTemplateSender;

    @Resource
    EmailTemplateDAO emailTemplateDao;

    @Resource
    DomainDAO domainDao;

    @Resource
    ContactDAO contactDAO;

    @Resource
    NicHandleDAO nicHandleDao;

    @Test
    public void testParamlessAction() {
        DsmActionFactory factory = new DsmActionFactory(applicationConfig, emailTemplateSender, contactDAO,
                nicHandleDao);
        InternalDsmAction internalAction = new InternalDsmAction();
        internalAction.setActionName("ClearDeletionDate");
        DsmAction action = factory.actionFor(internalAction);
        AssertJUnit.assertNotNull(action);
        Assert.assertNull(action.getActionParam());
    }

    @Test
    public void testActionWithParam() {
        DsmActionFactory factory = new DsmActionFactory(applicationConfig, emailTemplateSender, contactDAO,
                nicHandleDao);
        InternalDsmAction internalAction = new InternalDsmAction();
        internalAction.setActionName("SetRenewalDate");
        internalAction.setActionParam("0");
        DsmAction action = factory.actionFor(internalAction);
        AssertJUnit.assertNotNull(action);
        Assert.assertNotNull(action.getActionParam());
        AssertJUnit.assertEquals("0", action.getActionParam());
    }
}
