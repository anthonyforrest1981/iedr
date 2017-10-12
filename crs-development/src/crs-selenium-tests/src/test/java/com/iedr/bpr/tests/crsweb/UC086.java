package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.LockedDomainsPage;
import com.iedr.bpr.tests.pages.crsweb.*;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static com.iedr.bpr.tests.pages.crsweb.ProlongLockingServicePage.ProlongType.BY_1_YEAR;
import static com.iedr.bpr.tests.pages.crsweb.ProlongLockingServicePage.ProlongType.SYNC_WITH_RENEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC086 extends SeleniumTest {

    public UC086(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc086_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc086_data.sql";
    }

    @Test
    // Lock and unlock domains and roll forword the locking dates.
    public void test_uc086_sc01() throws SQLException {
        User user = this.registrar;
        User userInt = this.internal;
        String domainName = "uc086-aa.ie";
        db().setDsmStateForDomain(domainName, 17);

        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.getDomainEntersLockingEmail(user, domainName), domainName));
        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        lockingRun(domainName, vdp, user);

        emails.add(new DetailedExpectedEmailSummary.BodyContains(emailSummaryGenerator.getLockingServiceRolled(userInt),
                domainName));
        ProlongLockingServicePage dlsp = new ProlongLockingServicePage();
        dlsp.viewAndProlong(domainName, BY_1_YEAR);
        checkAndResetEmails(emails);

        lockingRun(domainName, vdp, user);
        emails.add(new DetailedExpectedEmailSummary.BodyContains(emailSummaryGenerator.getLockingServiceRolled(userInt),
                domainName));
        dlsp.viewAndProlong(domainName, SYNC_WITH_RENEW);
        checkAndResetEmails(emails);

        lockingRun(domainName, vdp, user);
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.getDomainIsLockedEmail(user, domainName), domainName));
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.domainRemovedFromLocking(user, domainName), domainName));
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.getDomainIsUnlockedEmail(user, domainName), domainName));
        vdp.setLockingStatus(domainName, true);
        assertEquals(1, db().getDsmStateForDomain(domainName));
        vdp.removeFromLocking(domainName);
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc086_sc03() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ak.ie";
        String domainSearch = "lockingDomains-search_searchCriteria_domainName";
        String holderSearch = "lockingDomains-search_searchCriteria_domainHolder";
        String contactSearch = "lockingDomains-search_searchCriteria_nicHandle";
        String lockingDate = "jscal_input_searchCriteria_lockFrom";
        String lockingRenewalDate = "jscal_input_searchCriteria_lockRenewalFrom";

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        crsweb().login(userInt);

        LockingServicePage lsp = new LockingServicePage();
        lsp.view();

        assertFalse(searchCheck(domainName, domainName, domainSearch));
        assertFalse(searchCheck(domainName, db().getHolderForDomain(domainName), holderSearch));
        assertFalse(searchCheck(domainName, db().getContactForDomain(domainName, "BillC"), contactSearch));
        assertFalse(searchCheck(domainName, dateFormat.format(date), lockingDate));
        assertFalse(searchCheck(domainName, dateFormat.format(date), lockingRenewalDate));

        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);

        lsp.view();

        assertTrue(searchCheck(domainName, domainName, domainSearch));
        assertTrue(searchCheck(domainName, db().getHolderForDomain(domainName), holderSearch));
        assertTrue(searchCheck(domainName, db().getContactForDomain(domainName, "BillC"), contactSearch));
        assertTrue(searchCheck(domainName, dateFormat.format(date), lockingDate));
        assertTrue(searchCheck(domainName, dateFormat.format(date), lockingRenewalDate));
    }

    @Test
    public void test_uc086_sc04() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-af.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);
        vdp.changeDomainHolderType(domainName, "Charity");

        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        assertTrue(errorMessage, errorMessage.contains("Invalid domain state: SetCharity is not a valid event for a " +
                "domain uc086-af.ie in the current state (1). Valid events are: [PaymentInitiated, PaymentSettled, " +
                "PaymentCancelled, Unlock, RenewalDatePasses, RegisterBuyRequest, CancelLastBuyRequest]"));
    }

    @Test
    public void test_uc086_sc05() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ag.ie";
        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);

        assertEquals(wd().findElements(By.id("domainview_domainedit_input")).size(), 0);
    }

    @Test
    public void test_uc086_sc06() throws SQLException {
        User user = this.registrar;
        User userInt = this.internal;
        String domainName = "uc086-ac.ie";
        console().login(user);
        com.iedr.bpr.tests.pages.console.ViewDomainPage cvdp = new com.iedr.bpr.tests.pages.console.ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        cvdp.initModification();
        cvdp.holderField.fill("test");
        cvdp.remarksField.fill("test");

        cvdp.submit();

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);
        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        assertTrue(errorMessage, errorMessage
                .contains("Please resolve outstanding modification tickets for this domain before locking"));

        assertEquals(17, db().getDsmStateForDomain(domainName));
    }

    @Test
    public void test_uc086_sc07() throws SQLException {
        User gaining = this.registrarNonVat;
        User userInt = this.internal;
        String domainName = "uc086-ad.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;

        console().login(gaining);
        DomainTransferUtils.transferDomain(domainName, gaining, paymentDetails);

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);
        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        assertTrue(errorMessage,
                errorMessage.contains("Please resolve outstanding transfer tickets for this domain before locking"));

        assertEquals(390, db().getDsmStateForDomain(domainName));
    }

    @Test
    public void test_uc086_sc08() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ab.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);

        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        String expected = "Internal error occurred. See logs for details. "
                + "Exception message is: Lock is not a valid event for a domain "
                + domainName + " in the current state (20). Valid events are: "
                + "[RenewalDatePasses, SuspensionDatePasses,"
                + " RemoveFromVoluntaryNRP, PaymentInitiated, PaymentSettled, "
                + "PaymentCancelled, TransferRequest, BulkTransfer, CancelLastBuyRequest]";
        assertEquals(expected, errorMessage);

    }

    @Test
    public void test_uc086_sc10() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-aj.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);

        ExportAuthcodesPage eap = new ExportAuthcodesPage();
        eap.exportAuthcode(domainName);

        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        assertTrue(errorMessage,
                errorMessage.contains(domainName + ": Cannot generate authcode for the domain: " + domainName));
    }

    @Test
    public void test_uc086_sc12() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ah.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.sendAuthCode(domainName);
        assertTrue(db().getAuthcodeForDomain(domainName) != null);
        vdp.setLockingStatus(domainName, true);
        assertTrue(db().getAuthcodeForDomain(domainName) == null);

    }

    @Test
    public void test_uc086_sc15() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ai.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);
        DsmPage dp = new DsmPage();
        dp.forceEvent(domainName, "EnterVoluntaryNRP");

        By errorMessageBy = By.xpath("//ul[contains(@class, 'errorMessage')]");
        crsweb().assertElementPresent(errorMessageBy);
        String errorMessage = wd().findElement(errorMessageBy).getText();
        assertTrue(errorMessage, errorMessage.contains("DSM event cannot proceed: EnterVoluntaryNRP" +
                " is not a valid event for a domain " + domainName + " " +
                "in the current state (1). Valid events are: " +
                "[PaymentInitiated, PaymentSettled, PaymentCancelled, " +
                "Unlock, RenewalDatePasses, RegisterBuyRequest, CancelLastBuyRequest]"));
    }

    @Test
    public void test_uc086_sc16() throws SQLException {
        User userInt = this.internal;
        String domainName = "uc086-ae.ie";

        crsweb().login(userInt);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.setLockingStatus(domainName, true);
        vdp.alterStatus();

        crsweb().waitForTextPresentOnPage("Altering the status of the domain uc086-ae.ie");
        crsweb().waitForTextPresentOnPage("Status cannot be changed.");

    }

    private void lockingRun(String domainName, ViewDomainPage vdp, User user) throws SQLException {
        LockedDomainsPage ldp = new LockedDomainsPage();

        // Lock a domain, check console report
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.getDomainIsLockedEmail(user, domainName), domainName));
        vdp.setLockingStatus(domainName, true);
        assertEquals(1, db().getDsmStateForDomain(domainName));
        console().login(user);
        ldp.lockedStatus(domainName, "yes");
        checkAndResetEmails(emails);

        // Unlock a domain, check console report
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(
                emailSummaryGenerator.getDomainIsUnlockedEmail(user, domainName), domainName));
        vdp.setLockingStatus(domainName, false);
        assertEquals(17, db().getDsmStateForDomain(domainName));
        console().login(user);
        ldp.lockedStatus(domainName, "no");
        checkAndResetEmails(emails);
    }


    private boolean searchCheck(String domainName, String searchValue, String searchCriteria) {
        String trXPath = "//table[@id='domainRow']//td[text()='" + domainName + "']/..";
        crsweb().fillInput(searchCriteria, searchValue);
        wd().findElement(By.id(searchCriteria)).sendKeys(Keys.RETURN);
        try {
            return wd().findElement(By.xpath(trXPath)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
