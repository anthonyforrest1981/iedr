package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.Ignore;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC013 extends SeleniumTest {

    public UC013(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc013_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc013_data.sql";
    }

    @Test
    public void test_uc013_sc01() throws SQLException {
        User billC = this.registrar;
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 81);
        emails.add(emailSummaryGenerator.getConvertDomainToCharityEmail(billC, domainName));
        test_change_domain_holder_type(domainName, "Charity", 113);
    }

    @Test
    public void test_uc013_sc02() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 49);
        test_change_domain_holder_type(domainName, "IEDRUnpublished", 386);
    }

    @Test
    public void test_uc013_sc03_direct() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 113);
        test_change_domain_holder_type(domainName, "Billable", 17);
    }

    @Test
    public void test_uc013_sc03_registrar() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 121);
        test_change_domain_holder_type(domainName, "Billable", 25);
    }

    @Test
    public void test_uc013_sc04_direct() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 116);
        // DSM state should remain the same.
        test_change_domain_holder_type(domainName, "NonBillable", 116);
    }

    @Test
    public void test_uc013_sc04_registrar() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 124);
        // DSM state should remain the same.
        test_change_domain_holder_type(domainName, "NonBillable", 124);
    }

    @Test
    public void test_uc013_sc05_direct() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 309);
        // DSM state should remain the same.
        test_change_domain_holder_type(domainName, "Billable", 309);
    }

    @Test
    public void test_uc013_sc05_registrar() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 317);
        // DSM state should remain the same.
        test_change_domain_holder_type(domainName, "Billable", 317);
    }

    @Test
    public void test_uc013_sc06() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 386);
        test_change_domain_holder_type(domainName, "NonBillable", 386);
    }

    @Test
    public void test_uc013_sc08() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 82);
        test_change_domain_holder_type(domainName, "NonBillable", 305);
    }

    @Test
    public void test_uc013_sc09() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 65);
        test_change_domain_holder_type(domainName, "NonBillable", 65);
    }

    @Test
    public void test_uc013_sc10() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 402);
        test_change_domain_holder_type(domainName, "Billable", 390);
    }

    @Test
    @Ignore("Setting vNRP domain to charity is not allowed. Test can be removed after TestRail is updated.")
    public void test_uc013_sc11() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 486);
        test_change_domain_holder_type(domainName, "Charity", 498);
    }

    @Test
    public void test_uc013_sc12() throws SQLException {
        User billC = this.registrar;
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 438);
        emails.add(emailSummaryGenerator.getConvertDomainToCharityEmail(billC, domainName));
        test_change_domain_holder_type(domainName, "Charity", 402);
    }

    @Ignore
    @Test
    public void test_uc013_sc13() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 24);
        test_change_domain_holder_type(domainName, "Charity", 113);
    }

    @Test
    public void test_uc013_sc14() throws SQLException {
        String domainName = "uc013-cdht.ie";
        db().setDsmStateForDomain(domainName, 49);
        test_change_domain_holder_type(domainName, "IEDRPublished", 628);
    }

    private void test_change_domain_holder_type(String domainName, String type, int dsmAfter) throws SQLException {
        crsweb().login(this.internal);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.changeDomainHolderType(domainName, type);
        assertEquals(dsmAfter, db().getDsmStateForDomain(domainName));
        checkAndResetEmails(emails);
    }
}
