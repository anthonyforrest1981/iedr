package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC010 extends SeleniumTest {

    public UC010(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc010_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc010_data.sql";
    }

    @Test
    public void test_uc010_sc02() throws SQLException {
        User user = this.direct;
        emails.add(getNrpNotificationEmail(user, this.adminContact, "uc010-sc02.ie"));
        test_place_domain_in_volnrp(user, "uc010-sc02.ie", "VM", true);
    }

    @Test
    public void test_uc010_sc03() throws SQLException {
        User user = this.registrar;
        emails.add(getNrpNotificationEmail(user, this.adminContact, "uc010-sc03.ie"));
        test_place_domain_in_volnrp(user, "uc010-sc03.ie", "VM", true);
    }

    @Test
    public void test_uc010_sc04() throws SQLException {
        User user = this.direct;
        emails.add(getNrpNotificationEmail(user, user, "uc010-sc04.ie"));
        test_place_domain_in_volnrp(user, "uc010-sc04.ie", "VM", true);
    }

    @Test
    public void test_uc010_sc06() {
        User user = this.direct;
        test_nrp_buttons(user, "uc010-sc06.ie", false, false);
    }

    @Test
    public void test_uc010_sc09() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc09.ie", true, false);
    }

    @Test
    public void test_uc010_sc10() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc10.ie", true, false);
    }

    @Test
    public void test_uc010_sc11() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc11.ie", false, false);
    }

    @Test
    public void test_uc010_sc12() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc12.ie", false, false);
    }

    @Test
    @Ignore("Transfer pending domains can no longer be put into vNRP. Test can be removed after TestRail is updated.")
    public void test_uc010_sc13() throws SQLException {
        User user = this.registrar;
        emails.add(getNrpNotificationEmail(user, this.adminContact, "uc010-sc13.ie"));
        test_place_domain_in_volnrp(user, "uc010-sc13.ie", "XPV", false);
    }

    @Test
    public void test_uc010_sc14() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc14.ie", false, false);
    }

    @Test
    public void test_uc010_sc15() {
        User user = this.registrar;
        test_nrp_buttons(user, "uc010-sc15.ie", true, false);
    }

    @Test
    public void test_uc010_nosc01() throws SQLException {
        // UC#010: Place Domain in Voluntary NRP - Direct - Admin-C = Bill-C
        User user = this.direct;
        emails.add(getNrpNotificationEmail(user, user, "uc010-nosc01.ie"));
        test_place_domain_in_volnrp(user, "uc010-nosc01.ie", "VM", true);
    }

    @Test
    public void test_uc010_nosc02() throws SQLException {
        // UC#010: Place Domain in Voluntary NRP - Registrar - Admin-C = Bill-C
        User user = this.registrar;
        emails.add(getNrpNotificationEmail(user, user, "uc010-nosc02.ie"));
        test_place_domain_in_volnrp(user, "uc010-nosc02.ie", "VM", true);
    }

    private void test_place_domain_in_volnrp(User user, String domainName, String expectedStatus, boolean checkDate)
            throws SQLException {
        console().login(user);
        ViewDomainUtils.enterDomainToNrp(domainName, ContactType.BILL);

        // Check suspension date and NRP status.
        assertEquals(expectedStatus, db().getNrpStatusForDomain(domainName));
        if (checkDate) {
            LocalDate now = new LocalDate();
            LocalDate suspension = new LocalDate(db().getSuspensionDateForDomain(domainName));
            LocalDate deletion = new LocalDate(db().getDeletionDateForDomain(domainName));
            assertEquals(1 + Integer.valueOf(db().getAppConfigValue("nrp_mailed_period")),
                    Days.daysBetween(now, suspension).getDays());
            assertEquals(0 + Integer.valueOf(db().getAppConfigValue("nrp_suspended_period")),
                    Days.daysBetween(suspension, deletion).getDays());
        }
        checkAndResetEmails(emails);
    }

    private void test_nrp_buttons(User user, String domainName, boolean removeButton, boolean enterButton) {
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);

        if (removeButton) {
            console().assertElementPresent(ViewDomainPage.removeFromNrpButton);
        } else {
            console().assertElementNotPresent(ViewDomainPage.removeFromNrpButton);
        }

        if (enterButton) {
            console().assertElementPresent(ViewDomainPage.enterToNrpButton);
        } else {
            console().assertElementNotPresent(ViewDomainPage.enterToNrpButton);
        }
    }

    private ExpectedEmailSummary getNrpNotificationEmail(User billC, User adminC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getNrpNotificationEmail(billC, adminC, domainName, true);
    }
}
