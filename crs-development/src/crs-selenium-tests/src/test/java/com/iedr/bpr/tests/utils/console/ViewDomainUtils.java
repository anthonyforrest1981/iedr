package com.iedr.bpr.tests.utils.console;

import com.iedr.bpr.tests.pages.console.AllDomainsPage;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class ViewDomainUtils {

    public static void viewDomain(String domainName, ContactType contactType) {
        AllDomainsPage adp = new AllDomainsPage();
        adp.viewDomain(domainName, contactType);
    }

    public static void enterDomainToNrp(String domainName, ContactType contactType) {
        ViewDomainUtils.viewDomain(domainName, contactType);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.enterToNrp();
        console().waitForTextPresentOnPage("Domain " + domainName + " added to Voluntary NRP");
    }

    public static void removeDomainFromNrp(String domainName, ContactType contactType) {
        ViewDomainUtils.viewDomain(domainName, contactType);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.removeFromNrp();
        console().waitForTextPresentOnPage("Domain " + domainName + " has been successfully removed from NRP.");
    }
}
