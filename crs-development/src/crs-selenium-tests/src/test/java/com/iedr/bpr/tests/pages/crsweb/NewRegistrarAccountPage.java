package com.iedr.bpr.tests.pages.crsweb;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class NewRegistrarAccountPage {

    public void createNewAccount(String nh, String name) {
        crsweb().view(SiteId.AccountsCreate);
        crsweb().fillInput(By.name("accountCreateWrapper.name"), name);
        crsweb().fillInput(By.name("accountCreateWrapper.webAddress"), "www.somedomain.ie");
        crsweb().fillInput(By.name("accountCreateWrapper.billingContactNicHandle"), nh);
        crsweb().clickElement(By.id("account-create-input_account-create_create"));
        crsweb().waitForTextPresentOnPage("Account View");
    }

}
