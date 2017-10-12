package com.iedr.bpr.tests.pages.crsweb;

import java.util.Collections;
import java.util.Set;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.CrsWebGui;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ExportAuthcodesPage {

    public void exportAuthcode(String domainName) {
        exportDomains(Collections.singleton(domainName), domainName);
    }

    public void exportDomains(Set<String> domainNames, String domainPrefix) {
        crsweb().view(CrsWebGui.SiteId.DomainsExportAuthcodes);
        crsweb().fillInput(By.id("domainExportAuthCodes-search_searchCriteria_domainName"), domainPrefix);
        crsweb().clickAndWaitForPageToLoad(By.id("domainExportAuthCodes-search_0"));
        for (String domainName : domainNames) {
            String xpath = String.format("//input[@class='export-button' and @bubble-list-value='%s']", domainName);
            crsweb().clickElement(By.xpath(xpath));
            crsweb().waitForElementPresent(By.xpath(String.format("//li[@bubble-list-value='%s']", domainName)));
        }
        crsweb().clickElement(By.id("do_export_authcodes"));
    }
}
