package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.actions.InternalDsmAction;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

public class DsmSpecIntegrityTest extends AbstractContextAwareTest {
    @Resource
    DsmTestDAO dao;

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

    DsmActionFactory dsmActionFactory = new DsmActionFactory(applicationConfig, emailTemplateSender, contactDAO,
            nicHandleDao);

    List<String> errors = new ArrayList<>();

    private String errorMsg;

    @BeforeMethod
    public void clearErrors() {
        errors.clear();
    }

    @AfterMethod
    public void failIfErrorInTest() {
        if (!errors.isEmpty()) {
            AssertJUnit.fail(errorMsg + " : " + errors.toString());
        }
    }

    @Test
    public void testActions() {
        List<String> actions = dao.getActions();
        for (String a : actions) {
            checkAction(a);
        }
    }

    @Test
    public void testEventNames() {
        List<String> events = dao.getEvents();
        for (String e : events) {
            checkEvent(e);
        }
        for (DsmEventName eventName : DsmEventName.values()) {
            if (!events.contains(eventName.name())) {
                AssertJUnit.fail("DsmEventName contains event that cannot be found in db: " + eventName);
            }
        }

    }

    private void checkEvent(String e) {
        try {
            DsmEventName.valueOf(e);
        } catch (IllegalArgumentException ex) {
            logError("Missing events (DsmEventName)", e);
        }
    }

    @Test
    public void testNRPStatuses() {
        List<String> statuses = dao.getNrpStatuses();
        for (String s : statuses) {
            checkStatus(s);
        }
    }

    @Test
    public void testRenewalModes() {
        List<String> modes = dao.getRenewalModes();
        for (String s : modes) {
            checkRenewalMode(s);
        }
    }

    @Test
    public void testCustTypes() {
        List<String> modes = dao.getAllCustTypes();
        for (String s : modes) {
            checkCustType(s);
        }
    }

    private void checkCustType(String s) {
        if (s == null)
            return;
        CustomerType status = CustomerType.forCode(s);
        if (status == null || !status.getCode().equals(s)) {
            logError("CustType missing", s);
        }

    }

    @Test
    public void testHolderTypes() {
        List<String> modes = dao.getAllHolderTypes();
        for (String s : modes) {
            checkHolderType(s);
        }
    }

    private void checkHolderType(String s) {
        if (s == null)
            return;
        try {
            DomainHolderType.forCode(s);
        } catch (Exception ex) {
            logError("DomainHolderType missing", s);
        }
    }

    private void checkRenewalMode(String s) {
        if (s == null)
            return;
        RenewalMode status = RenewalMode.forCode(s);
        if (status == null || !status.getCode().equals(s)) {
            logError("RenewalMode missing", s);
        }
    }

    private void checkStatus(String s) {
        if (s == null)
            return;
        try {
            NRPStatus.forCode(s);
        } catch (Exception ex) {
            logError("Status missing (NRPStatus)", s);
        }
    }

    private void checkAction(String actionName) {
        try {
            InternalDsmAction internalAction = getInternalActionForName(actionName);
            DsmAction action = dsmActionFactory.actionFor(internalAction);
            if (action == null) {
                logError("Missing Actions (DsmActionFactory)", actionName);
            }
        } catch (Exception e) {
            logError("Error getting actions (DsmActionFactory)", actionName);
        }

    }

    private InternalDsmAction getInternalActionForName(String actionName) {
        InternalDsmAction internalAction = new InternalDsmAction();
        internalAction.setActionName(actionName);
        internalAction.setExecuteAfterDsmChange(false);
        switch (actionName) {
            case "Email":
            case "SetRenewalDate":
                internalAction.setActionParam("1");
                break;
            case "EmailCond":
                internalAction.setActionParam("PAY_METHOD,CC,25");
                break;
            default:
                internalAction.setActionParam(null);
        }
        return internalAction;
    }

    private void logError(String errorType, String message) {
        errorMsg = errorType;
        errors.add(message);
    }
}
