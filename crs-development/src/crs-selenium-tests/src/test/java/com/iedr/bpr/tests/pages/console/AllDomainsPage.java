package com.iedr.bpr.tests.pages.console;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.utils.ContactType;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class AllDomainsPage extends DomainGridPage {

    public void view() {
        console().view(console().url.allDomains);
    }

    public void viewDomain(String domainName, ContactType contactType) {
        view();
        if (contactType != null) {
            switchDomainContactType(contactType);
        }
        if (!getVisibleDomainNames().contains(domainName)) {
            filterDomainsByName(domainName);
        }
        console().clickAndWaitForPageToLoad(By.linkText(domainName));
    }

    public void switchDomainContactType(ContactType type) {
        boolean rightTypeSelected = wd().findElement(By.tagName("html")).getText()
                .contains(String.format("Current contact type: %s", type));
        if (rightTypeSelected) {
            return;
        }
        console().selectOptionByText(By.id("AllDomainsModel_contact_type"), type.toString());
        console().clickAndWaitForPageToLoad(By.cssSelector("#AllDomains input[type='submit']"));
    }
}
