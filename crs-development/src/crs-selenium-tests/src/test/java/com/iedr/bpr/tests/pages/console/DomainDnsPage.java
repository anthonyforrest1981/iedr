package com.iedr.bpr.tests.pages.console;

import org.openqa.selenium.By;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DomainDnsPage extends DomainGridPage {

    public void initDnsModification(String domainName) {
        console().view(console().url.dns);
        selectDomainFromList(domainName);
        console().clickElement(By.id("gridaction_dnsMod"));
    }

}
