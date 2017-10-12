package com.iedr.bpr.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TestConfig {

    private static List<String> NULLABLE_SETTINGS = Arrays.asList("iedriverpath", "edgedriverpath");
    private static Map<SeleniumTest.Browser, String> BROWSERS_SETTINGS = new HashMap<>();
    static {
        BROWSERS_SETTINGS.put(SeleniumTest.Browser.IE, "iedriverpath");
        BROWSERS_SETTINGS.put(SeleniumTest.Browser.Edge, "edgedriverpath");
    }

    public static Properties getConfig() throws IOException {
        Properties prop = new Properties();
        String externalConfig = System.getenv("SELENIUM_EXTERNAL_CONFIG");
        if (externalConfig == null) {
            try {
                prop.load(TestConfig.class.getResourceAsStream("/testconfig.properties"));
            } catch (NullPointerException e) {
                String msg = "Couldn't find testconfig.properties resource. "
                        + "Use testconfig.template to create one and put it " + "in the same directory";
                throw new RuntimeException(msg, e);
            }
        } else {
            InputStream in = new FileInputStream(new File(externalConfig));
            prop.load(in);
        }
        checkConfig(prop);
        return prop;
    }

    private static void checkConfig(Properties config) throws IOException {
        Properties template = new Properties();
        template.load(TestConfig.class.getResourceAsStream("/testconfig.template"));
        for (Object keyObj : template.keySet()) {
            String key = (String) keyObj;
            if (!config.containsKey(key)) {
                throw new RuntimeException(String.format("%s variable not found in testconfig. "
                        + "Compare your config with " + "testconfig.template", key));
            }
            String configValue = config.getProperty(key);
            if (isConfigValueEmpty(configValue) && !NULLABLE_SETTINGS.contains(key)) {
                throw new RuntimeException(String.format("%s variable populated as empty in testconfig", key));
            }
        }
        for (Map.Entry<SeleniumTest.Browser, String> entry : BROWSERS_SETTINGS.entrySet()) {
            SeleniumTest.Browser browser = entry.getKey();
            String setting = entry.getValue();
            String configValue = config.getProperty(setting);
            if (config.getProperty("browsers").contains(browser.toString()) && isConfigValueEmpty(configValue)) {
                throw new RuntimeException(String.format("To test %s browser, you have to populate %s variable in " +
                        "testconfig", browser, configValue));
            }
        }
    }

    private static boolean isConfigValueEmpty(String value) {
        return value == null || "".equals(value.trim());
    }

}
