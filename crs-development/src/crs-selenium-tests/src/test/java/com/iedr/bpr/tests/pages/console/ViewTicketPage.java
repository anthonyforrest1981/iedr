package com.iedr.bpr.tests.pages.console;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewTicketPage {

    public void initEditTicket() {
        console().clickAndWaitForPageToLoad(By.linkText("Edit Ticket"));
    }

    public void checkFormValue(String fieldLabel, String expectedValue) {
        // Some form fields don't have id nor name, so we have to find them using their html labels.
        By fieldBy = By.xpath(String.format("//label[.='%s']/", fieldLabel)
                + "../following-sibling::td//*[self::input or self::textarea]");
        String actualValue = wd().findElement(fieldBy).getAttribute("value");
        actualValue = "".equals(actualValue) ? null : actualValue;
        assertEquals(fieldLabel, expectedValue, actualValue);
    }

    public void cancelTicket(String domainName) {
        console().clickElement(By.linkText("Cancel Ticket"));
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        alert.accept();
        String message = "Success: Ticket for domain " + domainName + " cancelled";
        console().waitForTextPresentOnPage(message);
    }

}
