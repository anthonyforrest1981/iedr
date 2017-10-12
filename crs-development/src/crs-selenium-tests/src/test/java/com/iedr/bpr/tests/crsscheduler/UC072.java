package com.iedr.bpr.tests.crsscheduler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC072 extends SeleniumTest {

    public UC072(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc072_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc072_data.sql";
    }

    @Test
    public void uc072_sc01() throws SQLException {
        List<String> periods = Arrays.asList(db().getAppConfigValue("renewal_notification_periods").split(","));
        for (String period : periods) {
            testNotificationPeriod(period);
        }
    }

    @Test
    public void uc072_sc02() throws SQLException {
        List<String> periods = Arrays.asList(db().getAppConfigValue("renewal_notification_periods").split(","));
        for (String period : periods) {
            testMissedNotificationPeriod(period);
        }
    }

    @Test
    public void uc072_sc03() throws SQLException {
        User user = this.direct;
        String domainName = "uc072-sc01.ie";
        db().setRenewalDateForDomain(domainName, new Date());
        ExpectedEmailSummary notice = emailSummaryGenerator.getPendingRenewalEmail(user, domainName);
        ExpectedEmailSummary expired = emailSummaryGenerator.getDomainExpiredEmail(user, domainName);
        Map<Integer, Integer> counts = countEmailsFromJob(SchedulerJob.RENEWAL_NOTIFICATION,
                Arrays.asList(notice, expired));
        assertEquals(0, counts.get(notice.id).intValue());
        assertEquals(1, counts.get(expired.id).intValue());
    }

    @Test
    public void uc072_nosc01() throws SQLException {
        // UC#072: Send Renewal Notifications - Domains in NRP
        List<String> periods = Arrays.asList(db().getAppConfigValue("renewal_notification_periods").split(","));
        for (String period : periods) {
            testNotificationPeriodForNRP(period);
        }
    }

    private void testNotificationPeriod(String periodStr) throws SQLException {
        int period = Integer.valueOf(periodStr);
        User user = this.direct;
        String domainName = "uc072-sc01.ie";
        ExpectedEmailSummary notice = emailSummaryGenerator.getPendingRenewalEmail(user, domainName);
        checkNotification(domainName, -1, notice, 0);
        checkNotification(domainName, period + 1, notice, 0);
        checkNotification(domainName, period, notice, 1);
        checkNotification(domainName, period - 1, notice, 0);
    }

    private void testMissedNotificationPeriod(String periodStr) throws SQLException {
        int period = Integer.valueOf(periodStr);
        User user = this.direct;
        String domainName = "uc072-sc01.ie";
        ExpectedEmailSummary notice = emailSummaryGenerator.getPendingRenewalEmail(user, domainName);
        checkNotification(domainName, period - 1, notice, 1);
        checkNotification(domainName, period - 2, notice, 0);
    }

    private void testNotificationPeriodForNRP(String periodStr) throws SQLException {
        int period = Integer.valueOf(periodStr);
        User user = this.direct;
        String domainName = "uc072-nosc01.ie";
        ExpectedEmailSummary notice = emailSummaryGenerator.getPendingRenewalEmail(user, domainName);
        db().setDsmStateForDomain(domainName, 18);
        checkNotification(domainName, period, notice, 0);
        db().setDsmStateForDomain(domainName, 20);
        checkNotification(domainName, period, notice, 0);
    }

    private void checkNotification(String domainName, int days, ExpectedEmailSummary notice, int count)
            throws SQLException {
        db().setRenewalDateForDomain(domainName, DateUtils.addDays(new Date(), days));
        Map<Integer, Integer> counts = countEmailsFromJob(SchedulerJob.RENEWAL_NOTIFICATION, Arrays.asList(notice));
        assertEquals(count, counts.get(notice.id).intValue());
    }

}
