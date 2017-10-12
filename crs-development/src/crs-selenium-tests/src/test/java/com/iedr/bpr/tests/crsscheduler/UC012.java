package com.iedr.bpr.tests.crsscheduler;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;

public class UC012 extends SeleniumTest {

    private int nrpSuspendedPeriod;
    private int nrpMailedPeriod;

    public UC012(Browser browser) {
        super(browser);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        nrpSuspendedPeriod = Integer.valueOf(db().getAppConfigValue("nrp_suspended_period"));
        nrpMailedPeriod = Integer.valueOf(db().getAppConfigValue("nrp_mailed_period"));
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc012_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc012_data.sql";
    }

    @Test
    public void uc012_sc01() throws SQLException {
        User user = this.registrar;
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 17, 18, false);
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(getINrpEmail(user, user, domain), domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_sc02() throws SQLException {
        User user = this.registrar;
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 81, 82, false);
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(getINrpEmail(user, user, domain), domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_sc03() throws SQLException {
        String domain = "uc012-susp-r.ie";
        testPushQBillable(domain, 18, 19, false);
    }

    @Test
    public void uc012_sc04() throws SQLException {
        String domain = "uc012-del-r.ie";
        testPushQDeleted(domain, 19);
    }

    @Test
    public void uc012_sc06() throws SQLException {
        User user = this.registrar;
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 49, 18, false);
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(getINrpEmail(user, user, domain), domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_sc07() throws SQLException {
        String domain = "uc012-ren-r.ie";
        testPushQRenewed(domain, 113);
    }

    @Test
    public void uc012_sc08() throws SQLException {
        String domain = "uc012-ren-r.ie";
        testPushQRenewed(domain, 305);
    }

    @Test
    public void uc012_sc09() throws SQLException {
        String domain = "uc012-susp-r.ie";
        testPushQBillable(domain, 20, 21, false);
    }

    @Test
    public void uc012_sc10() throws SQLException {
        String domain = "uc012-del-r.ie";
        testPushQDeleted(domain, 21);
    }

    @Test
    public void uc012_sc16() throws SQLException {
        User user = this.registrar;
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 20, 18, true);
        emails.add(emailSummaryGenerator.getRenewalDatePassedEmail(user, domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_sc17() throws SQLException {
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 21, 19, true);
    }

    @Test
    public void uc012_sc18() throws SQLException {
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 390, 390, true);
    }

    @Test
    public void uc012_sc19() throws SQLException {
        String domain = "uc012-ren-r.ie";
        testPushQBillable(domain, 486, 486, true);
    }

    @Test
    public void uc012_sc20() throws SQLException {
        String domain = "uc012-susp-r.ie";
        testPushQBillable(domain, 534, 534, true);
    }

    @Test
    public void uc012_sc21() throws SQLException {
        String domain = "uc012-del-r.ie";
        testPushQBillable(domain, 534, 534, true);
    }

    @Test
    public void uc012_sc01_alt() throws SQLException {
        User user = this.direct;
        String domain = "uc012-ren-d-b.ie";
        testPushQBillable(domain, 25, 26, false);
        emails.add(getINrpEmail(user, this.registrar, domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_sc03_alt() throws SQLException {
        String domain = "uc012-susp-d.ie";
        testPushQBillable(domain, 26, 27, false);
    }

    @Test
    public void uc012_sc02_alt() throws SQLException {
        User user = this.direct;
        String domain = "uc012-ren-d-a.ie";
        testPushQBillable(domain, 25, 26, false);
        emails.add(getINrpEmail(user, user, domain));
        partiallyCheckAndResetEmails(emails);
    }

    @Test
    public void uc012_nosc01() throws SQLException {
        // UC#012: Update Domain NRP Status - Renewal Date Passes - Domain with two Admin Contacts
        User user = this.registrar;
        User admin = this.adminContact;
        String domainName = "uc012-ren-r-2.ie";
        testPushQBillable(domainName, 17, 18, false);
        ExpectedEmailSummary email = emailSummaryGenerator
                .getNrpNotificationEmail(user, user, admin, domainName, false);
        emails.add(new DetailedExpectedEmailSummary.SubjectContains(email, domainName));
        partiallyCheckAndResetEmails(emails);
    }

    private void testPushQBillable(String domain, int dsmBefore, int dsmAfter, boolean dtUnmodified)
            throws SQLException {
        db().setDsmStateForDomain(domain, dsmBefore);
        Date renDt = db().getRenewalDateForDomain(domain);
        Date suspDt = db().getSuspensionDateForDomain(domain);
        Date delDt = db().getDeletionDateForDomain(domain);
        scheduler().runJob(SchedulerJob.PUSH_Q);
        checkDomainState(domain, renDt, getSuspDt(renDt, suspDt, dtUnmodified),
                getDelDt(renDt, suspDt, delDt, dtUnmodified), dsmAfter);
    }

    private void testPushQDeleted(String domain, int dsmBefore) throws SQLException {
        db().setDsmStateForDomain(domain, dsmBefore);
        scheduler().runJob(SchedulerJob.PUSH_Q);
        Map<String, String> exactCriteria = new HashMap<String, String>();
        exactCriteria.put("D_Name", domain);
        int count = db().getDomainsCount(exactCriteria, new HashMap<String, List<Date>>(), true);
        assertEquals(0, count);
    }

    private void testPushQRenewed(String domain, int dsm) throws SQLException {
        db().setDsmStateForDomain(domain, dsm);
        Date renDt = db().getRenewalDateForDomain(domain);
        scheduler().runJob(SchedulerJob.PUSH_Q);
        checkDomainState(domain, getRenDt(renDt), null, null, dsm);
    }

    private Date getRenDt(Date prevRenDt) {
        return DateUtils.addYears(prevRenDt, 1);
    }

    private Date getSuspDt(Date prevRenDt, Date prevSuspDt, boolean dtUnmodified) {
        Date now = new LocalDate().toDate();
        if (dtUnmodified) {
            return prevSuspDt;
        } else if (prevRenDt.before(now) && prevSuspDt == null) {
            return DateUtils.addDays(now, nrpMailedPeriod + 1);
        } else {
            return null;
        }
    }

    private Date getDelDt(Date prevRenDt, Date prevSuspDt, Date prevDelDt, boolean dtUnmodified) {
        Date now = new LocalDate().toDate();
        if (!dtUnmodified && prevRenDt.before(now) && prevSuspDt == null) {
            return DateUtils.addDays(getSuspDt(prevRenDt, prevSuspDt, dtUnmodified), nrpSuspendedPeriod);
        } else {
            return prevDelDt;
        }
    }

    private void checkDomainState(String domain, Date renDt, Date suspDt, Date delDt, int dsmState) throws SQLException {
        assertEquals(dsmState, db().getDsmStateForDomain(domain));
        assertEquals(renDt, db().getRenewalDateForDomain(domain));
        assertEquals(suspDt, db().getSuspensionDateForDomain(domain));
        assertEquals(delDt, db().getDeletionDateForDomain(domain));
    }

    private ExpectedEmailSummary getINrpEmail(User billC, User adminC, String domain) throws SQLException {
        return emailSummaryGenerator.getNrpNotificationEmail(billC, adminC, domain, false);
    }

}
