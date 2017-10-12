package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC050 extends SeleniumTest {

    Map<String, String> initialAppConfig = new HashMap<String, String>();
    final static List<String> keysToStore = Arrays.asList("nrp_mailed_period", "ticket_expiration_period");

    public UC050(Browser browser) {
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

    @Override
    public void setUp() throws Exception {
        super.setUp();
        for (String key : keysToStore) {
            initialAppConfig.put(key, db().getAppConfigValue(key));
        }
    }

    @Override
    public void tearDown() throws Exception {
        try {
            for (String key : keysToStore) {
                if (initialAppConfig.containsKey(key)) {
                    db().setAppConfigStringValue(key, initialAppConfig.get(key));
                }
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc050_sc01() throws SQLException {
        test_change_app_config_value("nrp_mailed_period");
    }

    @Test
    public void test_uc050_sc02() throws SQLException {
        test_change_app_config_value("ticket_expiration_period");
    }

    private void test_change_app_config_value(String key) throws SQLException {
        List<Integer> initialValues = parseValues(key);
        String newValue = generateNewValue(initialValues);
        setNewAppConfigValue(key, newValue);
        assertEquals(newValue, db().getAppConfigValue(key));
    }

    private List<Integer> parseValues(String key) {
        List<Integer> values = new ArrayList<Integer>();
        for (String strValue : Arrays.asList(initialAppConfig.get(key).split(","))) {
            values.add(Integer.parseInt(strValue));
        }
        return values;
    }

    private String generateNewValue(List<Integer> initialValues) {
        List<String> newValues = new ArrayList<String>();
        for (int value : initialValues) {
            newValues.add(String.valueOf(value + 1));
        }
        return StringUtils.join(newValues, ",");
    }

    private void setNewAppConfigValue(String key, String value) {
        crsweb().login(this.internal);
        crsweb().view(SiteId.CrsConfigurationView);
        crsweb().clickElement(By.xpath("//td[starts-with(text(), '" + key + "')]/..//img[contains(@title, 'Edit')]"));
        crsweb().fillInput("configEdit-input_newValue", value);
        crsweb().clickElement(By.id("configEdit-input_configEdit-save"));
        crsweb().waitForTextPresentOnPage("Config view");
    }

}
