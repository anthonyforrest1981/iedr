package com.iedr.bpr.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;

import com.iedr.bpr.tests.SeleniumTest.Browser;

public class WebDriverManager {

    private static final WebDriverManager INSTANCE = new WebDriverManager();

    private Map<Browser, WebDriver> drivers;
    private Properties config;
    private WebDriver emailWorkerDriver;
    public boolean shouldCloseBrowsersAfterTestCase = true;

    private WebDriverManager() {
        drivers = new HashMap<>();
        try {
            config = TestConfig.getConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebDriverManager getManager() {
        return INSTANCE;
    }

    public WebDriver getDriver(Browser browser) throws InvalidBrowserException, MalformedURLException {
        WebDriver driver;
        if (drivers.containsKey(browser)) {
            driver = drivers.get(browser);
        } else {
            driver = createDriver(browser);
            drivers.put(browser, driver);
        }
        return driver;
    }

    public WebDriver getEmailWorkerDriver() throws InvalidBrowserException, MalformedURLException {
        // Driver instance used for checking email worker monitor. We don't want
        // to use the current window for that, because it involves change of
        // url, which might require changing the test's logic to navigate back
        // to the old one.
        if (emailWorkerDriver == null) {
            emailWorkerDriver = createDriver(Browser.Firefox);
        }
        return emailWorkerDriver;
    }

    public void closeBrowsersAfterTestCase() {
        if (shouldCloseBrowsersAfterTestCase) {
            closeBrowsers();
        }
    }

    public void closeBrowsers() {
        Iterator<Browser> it = drivers.keySet().iterator();
        while (it.hasNext()) {
            WebDriver wd = drivers.get(it.next());
            closeBrowser(wd);
            it.remove();
        }
        closeBrowser(emailWorkerDriver);
    }

    private WebDriver createDriver(Browser browser) throws InvalidBrowserException, MalformedURLException {
        boolean useRemote = "true".equals(System.getenv("SELENIUM_USE_REMOTE"));
        DesiredCapabilities dc = getDesiredCapabilities(browser, useRemote);
        dc.setCapability("unexpectedAlertBehaviour", "ignore");
        if (useRemote) {
            URL url = new URL(System.getenv("SELENIUM_REMOTE_URL"));
            return new RemoteWebDriver(url, dc);
        } else {
            return createLocalDriver(browser, dc);
        }
    }

    private WebDriver createLocalDriver(Browser browser, DesiredCapabilities dc) throws InvalidBrowserException {
        switch (browser) {
            case Firefox:
                return new FirefoxDriver(dc);
            case IE:
                return new InternetExplorerDriver(dc);
            case Edge:
                return new EdgeDriver(dc);
            default:
                throw new InvalidBrowserException();
        }

    }

    private DesiredCapabilities getDesiredCapabilities(Browser browser, boolean useRemote)
            throws InvalidBrowserException {
        DesiredCapabilities dc;
        switch (browser) {
            case Firefox:
                dc = DesiredCapabilities.firefox();
                FirefoxProfile fp;
                String profileLocation = config.getProperty("firefoxprofilepath");
                if (profileLocation != null) {
                    File profileDir = new File(profileLocation);
                    fp = new FirefoxProfile(profileDir);
                } else {
                    fp = new FirefoxProfile();
                }
                fp.setPreference("intl.accept_languages", "en-US");
                dc.setCapability(FirefoxDriver.PROFILE, fp);
                break;
            case IE:
                dc = DesiredCapabilities.internetExplorer();
                if (!useRemote) {
                    String ieDriverLocation = config.getProperty("iedriverpath");
                    System.setProperty("webdriver.ie.driver", ieDriverLocation);
                }
                break;
            case Edge:
                dc = DesiredCapabilities.edge();
                if (!useRemote) {
                    String edgeDriverLocation = config.getProperty("edgedriverpath");
                    System.setProperty("webdriver.edge.driver", edgeDriverLocation);
                }
                break;
            default:
                throw new InvalidBrowserException();
        }
        return dc;
    }

    private void closeBrowser(WebDriver wd) {
        if (wd != null) {
            wd.quit();
        }
    }

}
