package com.iedr.bpr.tests.console;

import java.io.IOException;

import com.iedr.bpr.tests.IgnoredBrowsers;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.components.console.DocumentsUploadComponent;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@IgnoredBrowsers({SeleniumTest.Browser.Edge})
public class UC085 extends SeleniumTest {

    public UC085(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc085_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc085_data.sql";
    }

    @Test
    public void test_uc085_sc06() throws IOException {
        User user = this.registrar;
        String domainName = "uc085-with-ticket.ie";
        editTicket(user, domainName);
        DocumentsUploadComponent duc = new DocumentsUploadComponent();
        duc.uploadResourceFile();
        console().clickElement(By.name("yt9"));
        console().waitForTextPresentOnPage("Ticket modified successfully.");
        console().waitForTextPresentOnPage("Documents uploaded successfully");
        console().assertElementNotPresent(By.className("flash-error"));
    }

    @Test
    @IgnoredBrowsers({Browser.IE})
    public void test_uc085_sc07() throws IOException {
        User user = this.registrar;
        String domainName = "uc085-with-ticket.ie";
        editTicket(user, domainName);
        DocumentsUploadComponent duc = new DocumentsUploadComponent();
        duc.uploadRandomAccessFile(1024 * 1024 * 5 + 1, "png");
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        assertEquals(alert.getText(), "File size exceeds maximum allowed " + "size of 5.00 Mb");
        alert.accept();
        wd().switchTo().defaultContent();
    }

    @Test
    @IgnoredBrowsers({Browser.IE})
    public void test_uc085_sc08() throws IOException {
        User user = this.registrar;
        String domainName = "uc085-with-ticket.ie";
        editTicket(user, domainName);
        DocumentsUploadComponent duc = new DocumentsUploadComponent();
        duc.uploadRandomAccessFile(1024, "csv");
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        assertEquals(alert.getText(), "Unsupported file type csv");
        alert.accept();
        wd().switchTo().defaultContent();
    }

    @Test
    public void test_uc085_sc09() throws IOException {
        User user = this.registrar;
        String domainName = "uc085-with-ticket.ie";
        editTicket(user, domainName);
        DocumentsUploadComponent duc = new DocumentsUploadComponent();
        duc.uploadResourceFile();
        duc.uploadResourceFile();
        duc.uploadResourceFile();
        duc.uploadResourceFile();
        duc.uploadResourceFile();
        console().assertElementNotPresent(duc.getFileDialogButton());
    }

    private void editTicket(User user, String domainName) {
        console().login(user);
        console().view(console().url.tickets);
        String xpath = String.format("//td[@title='%s']/a", domainName);
        console().clickElement(By.xpath(xpath));
        console().clickElement(By.linkText("Edit Ticket"));
    }

}
