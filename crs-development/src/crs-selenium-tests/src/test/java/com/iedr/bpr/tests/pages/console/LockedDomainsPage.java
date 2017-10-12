package com.iedr.bpr.tests.pages.console;

import org.openqa.selenium.By;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class LockedDomainsPage extends DomainGridPage {

    public void view() {
        console().view(console().url.lockedDomains);
    }

    public void lockedStatus(String domainName, String Locked) {
        view();
        assertEquals((wd().findElement(By.xpath("//*[.=\"" + Locked + "\"]")).getText().contains(Locked)), true);
        assertEquals((wd().findElement(By.xpath("//*[.=\"" + domainName + "\"]")).getText().contains(domainName)),
                true);
    }
}
