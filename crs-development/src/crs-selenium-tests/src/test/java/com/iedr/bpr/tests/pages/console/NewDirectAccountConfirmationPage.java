package com.iedr.bpr.tests.pages.console;

import org.openqa.selenium.By;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertTrue;

public class NewDirectAccountConfirmationPage {

    public void submit() {
        console().clickElement(By.cssSelector("input[name='action'][value='Confirm']"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        String successMessage = wd().findElement(By.className("flash-success")).getText();
        assertTrue(successMessage, successMessage.contains("Your account has been successfully created. "
                + "You are now logged into your IEDR Console account."));
    }
}
