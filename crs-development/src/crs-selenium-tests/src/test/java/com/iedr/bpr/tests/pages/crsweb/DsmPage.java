package com.iedr.bpr.tests.pages.crsweb;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DsmPage {

    public void forceEventAndWaitForSuccess(String domainName, String dsmEvent) {
        forceEvent(domainName, dsmEvent);
        crsweb().waitForTextPresentOnPage("Force DSM event success");
    }

    public void forceEvent(String domainName, String dsmEvent) {
        crsweb().view(SiteId.DsmForceEvent);
        crsweb().fillInput("forceDSMevent-input_wrapper_domainNames", domainName);
        crsweb().fillInput("forceDSMevent-input_wrapper_remark", "Forcing DSM");
        crsweb().selectOptionByValue(By.id("forceDSMevent-input_wrapper_eventName"), dsmEvent);
        crsweb().clickElement(By.id("forceDSMevent-input_forceDSMevent-force"));
    }

}
