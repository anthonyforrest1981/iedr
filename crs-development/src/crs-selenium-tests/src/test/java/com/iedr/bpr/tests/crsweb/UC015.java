package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;

public class UC015 extends SeleniumTest {

    boolean accountCreated = false;
    int lastAccountId;

    public UC015(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc015_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        lastAccountId = db().getLastAccountId();
    }

    @Override
    public void tearDown() throws Exception {
        // Remove created XML file.
        try {
            if (accountCreated) {
                OutputFiles outputFiles = new OutputFiles(ssh().crsweb);
                outputFiles.clearAccountXmls();
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc015_sc01() throws SQLException {
        String nh = createNicHandle("UC015 test user", "uc015@iedr.ie", "Passw0rd!");
        checkDbValues(nh, 1, 1, 1, 1, 1, 3, 0);
        deleteNicHandle(nh);
        checkDbValues(nh, 1, 2, 1, 2, 1, 3, 0);
        scheduler().runJob(SchedulerJob.NIC_HANDLE_CLEANUP);
        checkDbValues(nh, 0, 2, 0, 2, 0, 3, 0);
    }

    private String createNicHandle(String nhName, String email, String password) throws SQLException {
        String nh = NicHandleUtils.createNewAccount(nhName, email, password);
        accountCreated = true;
        return nh;
    }

    private void deleteNicHandle(String nh) {
        crsweb().login(this.internal);
        crsweb().viewNicHandle(nh);
        crsweb().clickElement(By.id("openAlterStatusDialog"));
        crsweb().selectOptionByValue(By.id("alterStatus_status"), "Deleted");
        crsweb().fillInput(By.id("alterStatus_hostmastersRemark"), "Remark");
        String dialogId = String.format("alterStatusDialog%s", nh);
        crsweb().clickElement(By.cssSelector(String.format("#%s input[type='Submit']", dialogId)));
        new WebDriverWait(wd(), 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id(dialogId)));
    }

    private void checkDbValues(String nh, int nicHandleCount, int nicHandleHistCount, int telecomCount,
            int telecomHistCount, int accessCount, int accessHistCount, int contactCount) throws SQLException {
        assertEquals(nicHandleCount, db().countNicHandleRows(nh, "NicHandle"));
        assertEquals(nicHandleHistCount, db().countNicHandleRows(nh, "NicHandleHist"));
        assertEquals(telecomCount, db().countNicHandleRows(nh, "Telecom"));
        assertEquals(telecomHistCount, db().countNicHandleRows(nh, "TelecomHist"));
        assertEquals(accessCount, db().countNicHandleRows(nh, "Access"));
        assertEquals(accessHistCount, db().countNicHandleRows(nh, "AccessHist A left join NicHandleHist N on A.Nic_Handle_Chng_Id = N.Chng_Id"));
        assertEquals(contactCount, db().countNicHandleRows(nh, "Contact", "Contact_NH"));
    }

}
