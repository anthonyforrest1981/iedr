package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.pages.console.EditNicHandlePage;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC062 extends SeleniumTest {

    public UC062(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc062_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc062_data.sql";
    }

    @Test
    public void test_uc062_sc01() throws SQLException, JSchException, IOException {
        User user = new User("UC062A-IEDR", "Passw0rd!", false, "uc062a@iedr.ie");
        String domainName = "uc062-sc01.ie";
        String contactNh = user.login;
        String modifiedName = "Modified name";
        String modifiedEmail = "xdd274_modified@iedr.ie";
        String modifiedAddress = "1 The Road, Some Street Modified";
        int accountId = db().getLastAccountId();
        NicHandleDetails details = new NicHandleDetails(modifiedName, modifiedEmail);
        details.setAddress(modifiedAddress);
        modifyNicHandle(user, details, domainName);
        user.email = modifiedEmail;
        checkForm(contactNh, modifiedEmail);
        checkDb(contactNh, modifiedName, modifiedEmail, modifiedAddress);
        OutputFiles outputFiles = new OutputFiles(ssh().crsws);
        outputFiles.checkAccountXml(accountId, user.login);
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc062_nosc01() {
        // UC#062: Modify Nic Details - Vat number displayed for Bill C
        User user = this.registrar;
        console().login(user);
        console().viewMyAccount();
        console().clickElement(By.linkText("Edit Account"));
        console().assertElementPresent(By.id("Nichandle_Details_vatNo"));
    }

    @Test
    public void test_uc062_nosc02() {
        // UC#062: Modify Nic Details - Vat number not displayed for Admin C /
        // Tech C
        User user = this.adminContact;
        console().login(user);
        console().viewMyAccount();
        console().clickElement(By.linkText("Edit Account"));
        console().assertElementNotPresent(By.id("Nichandle_Details_vatNo"));
    }

    @Test
    public void test_uc062_nosc03() throws SQLException {
        // UC#062: Modify Nic Details - Direct with no Mod file created
        User user = new User("UC062B-IEDR", "Passw0rd!", false, "uc062b@iedr.ie");
        String domainName = "uc062-nosc03.ie";
        String modifiedName = "Modified name";
        String modifiedEmail = "xdd274_modified@iedr.ie";
        int accountId = db().getLastAccountId();
        NicHandleDetails details = new NicHandleDetails(modifiedName, modifiedEmail);
        modifyNicHandle(user, details, domainName);
        assertEquals(db().getLastAccountId(), accountId);
    }

    private void modifyNicHandle(User user, NicHandleDetails details, String domainName) {
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.editTechContact();
        EditNicHandlePage enhp = new EditNicHandlePage();
        enhp.fillNicHandleForm(details);
        enhp.submit();
    }

    private void checkForm(String contactNh, String modifiedEmail) {
        WebElement contactInput = wd().findElement(By.id("ViewDomainModel_domain_techContacts_nicHandle"));
        assertEquals(contactNh, contactInput.getAttribute("value"));
        WebElement emailInput = wd().findElement(By.id("ViewDomainModel_domain_techContacts_email"));
        assertEquals(modifiedEmail, emailInput.getAttribute("value"));
    }

    private void checkDb(String contactNh, String modifiedName, String modifiedEmail, String modifiedAddress)
            throws SQLException {
        assertEquals(modifiedName, db().getNicHandleName(contactNh));
        assertEquals(modifiedEmail, db().getNicHandleEmail(contactNh));
        assertEquals(modifiedAddress, db().getNicHandleAddress(contactNh));
    }

}
