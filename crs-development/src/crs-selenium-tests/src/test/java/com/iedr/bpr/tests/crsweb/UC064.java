package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.pages.crsweb.AccountPage;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static org.junit.Assert.assertEquals;

public class UC064 extends SeleniumTest {

    float initialDepositAmount;
    String doaTransactionXmlFilePath;
    int lastDoaId;

    public UC064(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc064_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc064_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initialDepositAmount = db().getDepositAmount("XBC189-IEDR");
        lastDoaId = db().getLastDoaId();
    }

    @Override
    public void tearDown() throws Exception {
        // Remove created XML file.
        try {
            ssh().crsweb.execute("rm -f " + doaTransactionXmlFilePath, true);
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc064_sc01() throws SQLException, JSchException, IOException {
        User user = this.registrar;
        emails.add(emailSummaryGenerator.getDepositTopUpEmail(user));
        topUpAccount(user, 1000, "Top up", false);
        checkDoaTransactionFile(user.login, 1000);
        checkDb(user.login, 1000, "Top up");
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc064_sc02() throws SQLException, JSchException, IOException {
        User user = this.registrar;
        emails.add(emailSummaryGenerator.getDepositCorrectionEmail(user));
        correctAccount(user, -120, "Correction");
        crsweb().waitForElementPresent(By.id("deposit-view-input_deposit-correct-input"));
        checkDoaTransactionFile(user.login, -120);
        checkDb(user.login, -120, "Correction");
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc064_sc02alt() throws SQLException {
        // UC#064: Make Deposit Account Correction - UC064-SC02: Deposit Account
        // Correction - Debit - reservation exists
        User user = this.registrar;
        String domainName = "uc064-sc02alt.ie";
        payForDomain(user, domainName, domainName, 1, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        correctAccount(user, -initialDepositAmount, "Correction");
        crsweb().waitForTextPresentOnPage("The user does not have enough funds to deduct this amount.");
    }

    @Test
    public void test_uc064_nosc01() throws SQLException, JSchException, IOException {
        // UC#064: Make Deposit Account Correction - Deposit Top-up
        User user = this.registrar;
        emails.add(emailSummaryGenerator.getDepositTopUpEmail(user));
        topUpAccount(user, 1000, "Top up", true);
        checkDoaTransactionFile(user.login, 1000);
        checkDb(user.login, 1000, "Top up");
        checkAndResetEmails(emails);
    }

    private void topUpAccount(User user, float amount, String remark, boolean doubleClick) {
        crsweb().login(this.internal);
        AccountPage da = new AccountPage(user);
        da.topUpAccount(amount, remark, doubleClick);
    }

    private void correctAccount(User user, float amount, String remark) {
        crsweb().login(this.internal);
        AccountPage da = new AccountPage(user);
        da.correctAccount(amount, remark);
    }

    private void payForDomain(User user, String domainName, String domainPrefix, int paymentPeriod,
            PaymentDetails paymentDetails) throws SQLException {
        List<Domain> domains = Arrays.asList(new Domain(domainName, paymentPeriod));
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, false, 0);
        console().login(user);
        pfd.payForDomainsSuccess(user, domainPrefix, paymentDetails);
    }

    private void checkDb(String userName, float amount, String remark) throws SQLException {
        assertEquals(amount, db().getDepositTransactionAmount(userName), 1e-2);
        assertEquals(config.getProperty("crsweblogin"), db().getDepositCorrectorNh(userName));
        assertEquals(remark, db().getDepositRemark(userName));
        assertEquals(initialDepositAmount + amount, db().getDepositAmount(userName), 1e-2);
    }

    private void checkDoaTransactionFile(String userName, float amount) throws SQLException, JSchException, IOException {
        OutputFiles files = new OutputFiles(ssh().crsweb);
        files.checkDoaTransactionFile(lastDoaId, userName, amount);
    }

}
