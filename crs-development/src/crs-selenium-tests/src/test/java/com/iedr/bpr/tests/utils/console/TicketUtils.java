package com.iedr.bpr.tests.utils.console;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.pages.console.ViewTicketPage;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class TicketUtils {

    public static void viewTicket(String domainName) {
        console().view(console().url.tickets);
        console().waitForElementPresent(By.cssSelector(".jqgrow"));
        By by = By.xpath(String.format("//td[@title='%s']/a", domainName));
        if (!console().isElementPresentInstantaneously(by)) {
            console().fillInput(By.id("gs_B"), domainName);
        }
        console().clickElement(by);
    }

    public static void initEditTicket(String domainName) {
        viewTicket(domainName);
        ViewTicketPage vtp = new ViewTicketPage();
        vtp.initEditTicket();
    }

    public static void cancelTicket(String domainName) {
        viewTicket(domainName);
        ViewTicketPage vtp = new ViewTicketPage();
        vtp.cancelTicket(domainName);
    }

}
