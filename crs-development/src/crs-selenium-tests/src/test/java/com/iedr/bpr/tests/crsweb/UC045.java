package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.BulkTransferPage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UC045 extends SeleniumTest {

    Map<String, String> initialAppConfig = new HashMap<String, String>();

    public UC045(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc045_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc045_data.sql";
    }

    @Test
    public void test_uc045_sc01() throws SQLException {
        User losing = new User("UC045R1-IEDR", "Passw0rd!", true, "uc045r1@iedr.ie");
        User gaining = new User("UC045R3-IEDR", "Passw0rd!", true, "uc045r3@iedr.ie");
        User adminC = new User("UC045C1-IEDR", "Passw0rd!", true, "uc045c1@iedr.ie");
        List<String> domains = db().getNicHandleDomains(losing.login);
        for (String domain : domains) {
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator
                    .getBulkTransferCompletedForDomainEmail(gaining, losing, adminC, domain), domain));
        }
        emails.add(new DetailedExpectedEmailSummary.MatchEverything(emailSummaryGenerator
                .getNhDetailsUpdatedTacEmail(gaining, this.internal, gaining, adminC)));
        emails.add(emailSummaryGenerator.getBulkTransferCompletedEmail(gaining));
        crsweb().login(this.internal);
        BulkTransferPage btp = new BulkTransferPage();
        btp.createBulkTransfer(1111, 1113);
        btp.completeBulkTransfer(domains);
        checkDbBulkSuccessful(domains, 1113, adminC.login);
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc045_sc02() throws SQLException {
        List<String> domains = db().getNicHandleDomains("UC045R2-IEDR");
        domains.add("uc045-sc02d2.ie");
        crsweb().login(this.internal);
        BulkTransferPage btp = new BulkTransferPage();
        btp.createBulkTransfer(1112, 1113);
        btp.completeBulkTransferNoConfirmation(domains);
        checkErrors(new HashSet<>(Arrays.asList(
                "uc045-sc02a.ie: has a different billing contact (UC045C2-IEDR) than a losing account (UC045R2-IEDR)",
                "uc045-sc02b1.ie: admin contact (UC045C2-IEDR) is associated with domains, which are not in transfer : [uc045-sc02b2.ie]",
                "uc045-sc02c.ie: admin contact UC045C5-IEDR associated with a different account",
                "uc045-sc02d1.ie: admin contact (UC045C3-IEDR) is associated with domains, which are associated with a different account",
                "uc045-sc02e.ie: has unsettled reservations",
                "uc045-sc02f3.ie: has an illegal NRP Status: TransferPendingActive",
                "uc045-sc02f4.ie: has an illegal NRP Status: TransferPendingInvNRP",
                "uc045-sc02f5.ie: has an illegal NRP Status: TransferPendingVolNRP",
                "uc045-sc02g.ie: is locked")));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc045_nosc01() throws SQLException {
        // UC#045: Perform Bulk Domain Billing Transfer - Bulk Transfer for NRP
        // domains
        User adminC = new User("UC045C6-IEDR", "Passw0rd!", false, "uc045c6@iedr.ie");
        List<String> domains = Arrays.asList("uc045-nosc01a.ie", "uc045-nosc01b.ie");
        Map<String, Date> suspDates = new HashMap<String, Date>();
        Map<String, Date> delDates = new HashMap<String, Date>();
        for (String domain : domains) {
            suspDates.put(domain, db().getSuspensionDateForDomain(domain));
            delDates.put(domain, db().getDeletionDateForDomain(domain));
        }
        crsweb().login(this.internal);
        BulkTransferPage btp = new BulkTransferPage();
        btp.createBulkTransfer(1113, 1111);
        btp.completeBulkTransfer(domains);
        checkDbBulkSuccessful(domains, 1111, adminC.login);
        checkDbDates(domains, suspDates, delDates);
    }

    private void checkDbBulkSuccessful(List<String> domains, int gainingAccount, String oldAdminC) throws SQLException {
        String gainingNh = db().getBillingNicHandleForAccount(gainingAccount);
        for (String domain : domains) {
            assertEquals(gainingAccount, db().getAccountForDomain(domain));
            assertEquals(gainingNh, db().getContactForDomain(domain, "Billing"));
            // AdminC should stay the same, but his account number should
            // change.
            String adminC = db().getContactForDomain(domain, "Admin");
            assertEquals(oldAdminC, adminC);
            assertEquals(gainingAccount, db().getNicHandleAccountNumber(adminC));
            String defaultTechC = db().getResellerDefaults(gainingNh, "Tech_C");
            assertEquals(defaultTechC, db().getContactForDomain(domain, "Tech"));
        }
    }

    private void checkErrors(Set<String> expectedErrors) {
        Set<String> actualErrors = getErrors();
        expectedErrors.removeAll(actualErrors);
        if (!expectedErrors.isEmpty()) {
            fail("Missing errors: " + Arrays.toString(expectedErrors.toArray()) + "\nActual errors: "
                    + Arrays.toString(actualErrors.toArray()));
        }
    }

    private Set<String> getErrors() {
        String errorMessage = wd().findElement(By.className("errorMessage")).getText();
        errorMessage = errorMessage.substring(1, errorMessage.length() - 1);
        Pattern errorStartPattern = Pattern.compile("((uc045-sc0\\d\\w\\d{0,1}\\.ie)\\s(:\\s)?)");
        Matcher m = errorStartPattern.matcher(errorMessage);
        String parsedMessage = errorMessage;
        while (m.find()) {
            parsedMessage = parsedMessage.replaceFirst(m.group(1), "\n" + m.group(2) + ": ");
        }
        parsedMessage = parsedMessage.replaceAll(", \n", "\n");
        List<String> errors = new LinkedList<String>(Arrays.asList(parsedMessage.split("\n")));
        errors.remove(0); // remove first - empty line
        return new HashSet<String>(errors);
    }

    private void checkDbDates(List<String> domains, Map<String, Date> suspDates, Map<String, Date> delDates)
            throws SQLException {
        for (String domain : domains) {
            assertEquals(18, db().getDsmStateForDomain(domain));
            Date delDate = db().getDeletionDateForDomain(domain);
            Date suspDate = db().getSuspensionDateForDomain(domain);
            assertEquals(delDates.get(domain), delDate);
            assertEquals(suspDates.get(domain), suspDate);
        }
    }
}
