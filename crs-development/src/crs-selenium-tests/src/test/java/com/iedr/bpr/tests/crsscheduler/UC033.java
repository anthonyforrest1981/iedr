package com.iedr.bpr.tests.crsscheduler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.DsmPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;

public class UC033 extends SeleniumTest {

    public UC033(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc033_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc033_data.sql";
    }

    @Test
    public void uc033_sc01() throws SQLException {
        String domainName = "uc033-sc01.ie";
        crsweb().login(this.internal);
        DsmPage dp = new DsmPage();
        dp.forceEventAndWaitForSuccess(domainName, "DeletionDatePasses");
        scheduler().runJob(SchedulerJob.PUSH_Q);
        Map<String, String> exactCriteria = new HashMap<String, String>();
        exactCriteria.put("D_Name", domainName);
        exactCriteria.put("DSM_State", "385");
        assertEquals(1, db().getHistDomainsCount(exactCriteria, true));
        exactCriteria.put("DSM_State", "387");
        assertEquals(1, db().getHistDomainsCount(exactCriteria, true));
        assertEquals(0, db().getDnsListForDomain(domainName).size());
        assertEquals(0, db().getContactMapForDomain(domainName).size());

    }

}
