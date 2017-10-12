package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC074 extends SeleniumTest {

    public UC074(Browser browser) {
        super(browser);
    }

    String initialInvoiceFormat;

    @Override
    protected String getResetDataScript() {
        return null;
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initialInvoiceFormat = db().getEmailInvoiceFormat("XBC189-IEDR");
        db().setEmailInvoiceFormat("XBC189-IEDR", "pdf");
    }

    @Override
    public void tearDown() throws Exception {
        try {
            db().setEmailInvoiceFormat("XBC189-IEDR", initialInvoiceFormat);
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc074_sc01() throws SQLException {
        User user = this.registrar;
        console().login(user);
        console().viewProfile(user.login);
        console().clickElement(By.xpath("//a[normalize-space()='Edit Registrar Default Values']"));
        console().assertElementPresent(By.xpath("//input[@value='PDF' and @checked='checked']"));
        console().selectElement(By.xpath("//input[@value='BOTH']"));
        console().clickElement(By.name("yt8"));
        String message = "Modify data is successful. " + "These changes will take effect after relogin.";
        console().waitForTextPresentOnPage(message);
        assertEquals("both", db().getEmailInvoiceFormat(user.login));
    }

}
