package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.PasswordUtils;

public class UC079 extends SeleniumTest {

    public UC079(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc079_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc079_data.sql";
    }

    @Test
    public void test_uc079_sc01() throws SQLException {
        User user = new User("UC079AA-IEDR", "Passw0rd!", true, "uc079_aa@iedr.ie");
        emails.add(emailSummaryGenerator.getNhPasswordResetEmail(user));
        PasswordUtils.requestPasswordReset(user);
        checkAndResetEmails(emails);
    }

}
