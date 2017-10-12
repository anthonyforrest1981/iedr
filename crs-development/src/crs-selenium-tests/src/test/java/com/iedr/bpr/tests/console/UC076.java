package com.iedr.bpr.tests.console;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertFalse;

public class UC076 extends SeleniumTest {

    public UC076(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return null;
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    public void test_uc076_sc01() {
        User user = this.registrar;
        console().login(user);
        assertFalse(wd().getCurrentUrl().equals(console().url.login));
    }

    @Test
    public void test_uc076_sc03() {
        User user = this.direct;
        console().login(user);
        assertFalse(wd().getCurrentUrl().equals(console().url.login));
    }

}
