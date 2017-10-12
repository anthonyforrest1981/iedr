package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.CardPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.PaymentUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC024 extends SeleniumTest {

    public UC024(Browser browser) {
        super(browser);
    }

    String doaTransactionXmlFilePath = null;
    int lastDoaId;

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc024_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc024_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        lastDoaId = db().getLastDoaId();
    }

    @Override
    public void tearDown() throws Exception {
        // Remove created XML file.
        try {
            ssh().crsws.execute("rm -f " + doaTransactionXmlFilePath, true);
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc024_sc01() throws SQLException, JSchException, IOException {
        User user = new User("UC024AA-IEDR", "Passw0rd!", true, "uc024@iedr.ie");
        emails.add(getDepositTopUpEmail(user));
        test_top_up(user, "1000");
    }

    @Test
    public void test_uc024_doa3() throws SQLException, JSchException, IOException {
        User user = new User("UC024AA-IEDR", "Passw0rd!", true, "uc024@iedr.ie");
        emails.add(getDepositTopUpEmail(user));
        test_top_up(user, "1000.00");
    }

    @Test
    public void test_uc024_doa4() throws SQLException, JSchException, IOException {
        User user = new User("UC024AA-IEDR", "Passw0rd!", true, "uc024@iedr.ie");
        emails.add(getDepositTopUpEmail(user));
        test_top_up(user, "1000.99");
    }

    @Test
    public void test_uc024_nosc02() throws SQLException, JSchException, IOException {
        // UC#024: Top-up Deposit Account - View Deposit Top-ups
        User user = new User("UC024AA-IEDR", "Passw0rd!", true, "uc024@iedr.ie");
        emails.add(getDepositTopUpEmail(user));
        test_top_up(user, "1000");
        checkDepositTopUps(user, "1,000.00");
    }

    @Test
    public void test_uc024_nosc03() throws SQLException, JSchException, IOException {
        // UC#024: Top-up Deposit Account - Don't log CC details
        User user = new User("UC024AA-IEDR", "Passw0rd!", true, "uc024@iedr.ie");
        CardPaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        PaymentUtils.topUpAccount(user, "1000", paymentDetails);
        verifyCardDataInLogs(paymentDetails);
    }

    public void test_top_up(User user, String topUpAmount) throws SQLException, JSchException, IOException {
        float initialAmount = db().getDepositAmount(user.login);
        String orderId = PaymentUtils.topUpAccount(user, topUpAmount);
        checkDb(user.login, orderId, initialAmount, topUpAmount);
        OutputFiles files = new OutputFiles(ssh().crsws);
        files.checkDoaTransactionFile(lastDoaId, user.login, new Float(topUpAmount));
        checkDepositBalanceUpdated(initialAmount, topUpAmount);
        checkAndResetEmails(emails);
    }

    private void checkDb(String userName, String orderId, float initialAmount, String topUpAmount) throws SQLException {
        assertEquals(orderId, db().getDepositOrderId(userName));
        assertEquals(initialAmount + Float.parseFloat(topUpAmount), db().getDepositAmount(userName), 1e-2);
    }

    private void checkDepositBalanceUpdated(float initialAmount, String topUpAmount) {
        console().view(console().url.depositBalance);
        String content = wd().findElement(By.id("content")).getText();
        assertEquals(initialAmount + Float.parseFloat(topUpAmount), extractDepositBalance(content), 1e-2);
    }

    private float extractDepositBalance(String content) {
        // (char) 8364 = euro symbol
        return extractValue(content, "Deposit Balance: " + (char) 8364 + " ");
    }

    private float extractValue(String content, String linePrefix) {
        String value = null;
        List<String> lines = Arrays.asList(content.split("\n"));
        for (String line : lines) {
            if (line.startsWith(linePrefix)) {
                value = line.replace(linePrefix, "");
            }
        }
        return Float.parseFloat(value);
    }

    private void checkDepositTopUps(User user, String topUpAmount) throws SQLException {
        console().view(console().url.depositTopUps);
        String orderId = db().getDepositOrderId(user.login);
        WebElement topUpRow = wd().findElement(By.xpath("//td[@title='" + orderId + "']/.."));
        String topUpAmountTitle = String.format("%s %s", (char) 8364, topUpAmount);
        topUpRow.findElement(By.cssSelector("td[title='" + topUpAmountTitle + "']"));
    }

    private ExpectedEmailSummary getDepositTopUpEmail(User billC) throws SQLException {
        return emailSummaryGenerator.getDepositTopUpEmail(billC);
    }
}
