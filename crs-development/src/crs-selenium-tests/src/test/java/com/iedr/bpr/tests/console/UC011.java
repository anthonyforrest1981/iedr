package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC011 extends SeleniumTest {

    public UC011(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc011_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc011_data.sql";
    }

    @Test
    public void test_uc011_sc02() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-sc02.ie";
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_sc04() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-sc04.ie";
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_sc04alt() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-sc04alt.ie";
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcnp17() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 20);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcnp18() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 21);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrpdcnp20() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-nrpdc.ie";
        db().setDsmStateForDomain(domainName, 29);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcchy21() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 116);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcchy22() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 117);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcchy23() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 308);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrprcchy24() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 309);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrpdcchy24() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-nrpdc.ie";
        db().setDsmStateForDomain(domainName, 316);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Test
    public void test_uc011_nrpdcchy25() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-nrpdc.ie";
        db().setDsmStateForDomain(domainName, 317);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc011_nrpwrcnp58() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 4);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc011_nrpwrcnp59() throws SQLException {
        User user = this.registrar;
        String domainName = "uc011-nrprc.ie";
        db().setDsmStateForDomain(domainName, 5);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc011_nrpwdcnp60() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-nrpdc.ie";
        db().setDsmStateForDomain(domainName, 12);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc011_nrpwdcnp61() throws SQLException {
        User user = this.direct;
        String domainName = "uc011-nrpdc.ie";
        db().setDsmStateForDomain(domainName, 13);
        emails.add(getNrpReactivationEmail(user, domainName));
        test_remove_from_nrp(user, domainName);
    }

    private void test_remove_from_nrp(User user, String domainName) throws SQLException {
        console().login(user);
        ViewDomainUtils.removeDomainFromNrp(domainName, ContactType.BILL);

        // Check suspension date and NRP status.
        assertEquals("A", db().getNrpStatusForDomain(domainName));
        assertEquals(null, db().getSuspensionDateForDomain(domainName));
        checkAndResetEmails(emails);
    }

    private ExpectedEmailSummary getNrpReactivationEmail(User billC, String domainName) throws SQLException {
        User adminC = this.adminContact;
        return emailSummaryGenerator.getNrpVoluntaryReactivationEmail(billC, adminC, domainName);
    }

}
