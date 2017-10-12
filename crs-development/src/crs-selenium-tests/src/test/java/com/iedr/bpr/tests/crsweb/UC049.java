package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.NewRegistrarAccountPage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static org.junit.Assert.assertEquals;

public class UC049 extends SeleniumTest {

    String accountXmlFilePath = null;
    boolean accountCreated = false;
    int lastAccountId;

    public UC049(Browser browser) {
        super(browser);
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
                ssh().crsws.execute("rm -f " + accountXmlFilePath, true);
            }
        } finally {
            super.tearDown();
        }
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc049_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    public void test_uc049_sc01() throws SQLException {
        String email = "uc049@iedr.ie";
        test_create_new_account("UC049 test user", "Passw0rd!", email);
    }

    private void test_create_new_account(String nhName, String password, String email) throws SQLException {
        String nh = createNewAccountViaConsole(nhName, password, email);
        User user = new User(nh, password, false, email);
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(user));
        emails.add(emailSummaryGenerator.getNhDetailsUpdatedEmail(user));
        emails.add(emailSummaryGenerator.getNewRegistrarAccountEmail(user));
        convertToRegistrar(nh, nhName);
        checkAndResetEmails(emails);
    }

    private String createNewAccountViaConsole(String nhName, String password, String email) throws SQLException {
        String nh = NicHandleUtils.createNewAccount(nhName, email, password);
        accountCreated = true;
        assertEquals(2048, db().getNicHandleLevel(nh));
        return nh;
    }

    private void convertToRegistrar(String nh, String name) throws SQLException {
        crsweb().login(this.internal);
        NewRegistrarAccountPage nrap = new NewRegistrarAccountPage();
        nrap.createNewAccount(nh, name);
        assertEquals(2, db().getNicHandleLevel(nh));
    }
}
