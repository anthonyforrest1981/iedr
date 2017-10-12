package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.crsweb.DsmPage;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC035 extends SeleniumTest {

    public UC035(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc035_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc035_data.sql";
    }

    @Test
    public void test_uc035_sc01() throws SQLException {
        test_force_dsm_state(Arrays.asList("uc035-fdsm1.ie", "uc035-fdsm2.ie", "uc035-fdsm3.ie"), 81);
    }

    @Test
    public void test_uc035_sc02() throws SQLException {
        String domainName = "uc035-fdsm1.ie";
        emails.add(emailSummaryGenerator.getDsmEventForcedEmail(this.internal, domainName));
        emails.add(emailSummaryGenerator.getNrpNotificationEmail(registrar, adminContact, domainName, true));
        test_force_dsm_event(domainName, "EnterVoluntaryNRP", 20);
    }

    private void test_force_dsm_state(List<String> domains, int newDsm) throws SQLException {
        crsweb().login(this.internal);
        crsweb().view(SiteId.DsmForceState);
        crsweb().fillInput("forceDSMstate-input_wrapper_domainNames", StringUtils.join(domains, ','));
        crsweb().fillInput("forceDSMstate-input_wrapper_remark", "Forcing DSM");
        crsweb().selectOptionByValue(By.id("forceDSMstate-input_wrapper_state"), String.valueOf(newDsm));
        crsweb().clickElement(By.id("forceDSMstate-input_forceDSMstate-force"));
        crsweb().waitForTextPresentOnPage("Force DSM state success");
        for (String domainName : domains) {
            assertEquals(newDsm, db().getDsmStateForDomain(domainName));
        }
        checkAndResetEmails(emails);
    }

    private void test_force_dsm_event(String domainName, String dsmEvent, int newDsm) throws SQLException {
        crsweb().login(this.internal);
        DsmPage dp = new DsmPage();
        dp.forceEventAndWaitForSuccess(domainName, dsmEvent);
        assertEquals(newDsm, db().getDsmStateForDomain(domainName));
        checkAndResetEmails(emails);
    }

}
