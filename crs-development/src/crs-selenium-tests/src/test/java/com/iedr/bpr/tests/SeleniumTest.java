package com.iedr.bpr.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.iedr.bpr.tests.formdetails.console.CardPaymentDetails;
import com.iedr.bpr.tests.gui.ConsoleGui;
import com.iedr.bpr.tests.gui.ConsoleUrlProvider;
import com.iedr.bpr.tests.gui.CrsWebGui;
import com.iedr.bpr.tests.utils.CrsDb;
import com.iedr.bpr.tests.utils.SchedulerMonitor;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.SmtpServerWrapper;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.EmailSummaryGenerator;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailVerifier;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailsVerifier;
import com.iedr.bpr.tests.utils.ssh.SshConnectionBank;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(IdeCompatibleParameterized.class)
public abstract class SeleniumTest {

    public enum Browser {
        Edge, Firefox, IE
    }

    public static int defaultImplicitlyWaitTimeout = 10;
    public static int defaultAsyncScriptTimeout = 10;

    // TODO: Those can also be moved to TestEnvironment at some point.
    protected Properties config;
    protected SmtpServerWrapper smtpServer;
    // TODO: All EmailSummaryGenerator methods should be made static.
    protected EmailSummaryGenerator emailSummaryGenerator;

    protected Set<ExpectedEmailSummary> emails;

    protected User registrar = new User("XBC189-IEDR", "Passw0rd!", true, "xbc189@iedr.ie");
    protected User registrarNonVat = new User("XNV498-IEDR", "Passw0rd!", true, "xnv498@iedr.ie");
    protected User direct = new User("XDD274-IEDR", "Passw0rd!", false, "xdd274@iedr.ie");
    protected User adminContact = new User("XOE550-IEDR", "Passw0rd!", true, "xoe550@iedr.ie");
    protected User internal;

    public SeleniumTest(Browser browser) {
        TestEnvironment.setBrowser(browser);
        if (this.getClass().toString().contains("com.iedr.bpr.tests.crsweb")
                || this.getClass().toString().contains("com.iedr.bpr.tests.crsscheduler")) {
            assumeTrue(browser == Browser.Firefox);
        }
    }

    @Rule
    public IgnoredBrowsersTestWatcher ignoredBrowsersTestWatcher = new IgnoredBrowsersTestWatcher();

    @Rule
    public TestFailure failure = new TestFailure() {
        @Override
        protected void finished(Description description) {
            try {
                tearDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Parameters(name = "{0}")
    public static List<Object[]> browsers() throws IOException {
        List<Browser> browsers = getBrowsers();
        List<Object[]> wrappedBrowsers = new ArrayList<Object[]>();
        for (Browser browser : browsers) {
            wrappedBrowsers.add(new Object[] {browser});
        }
        return wrappedBrowsers;
    }

    public static List<Browser> getBrowsers() throws IOException {
        Properties tmpCfg = TestConfig.getConfig();
        List<String> browserNames = Arrays.asList(tmpCfg.getProperty("browsers").split(","));
        List<Browser> browsers = new ArrayList<Browser>();
        for (String name : browserNames) {
            browsers.add(Browser.valueOf(name.trim()));
        }
        return browsers;
    }

    @Before
    public void setUp() throws Exception {
        assumeFalse(String.format("Test ignored in %s browser", browser()), shouldIgnore(browser()));

        config = TestConfig.getConfig();
        smtpServer = new SmtpServerWrapper(config, failure.getOrCreateLogDirectory());

        WebDriver wd = getWebDriver();
        TestEnvironment.setWd(wd);
        CrsDb db = new CrsDb();
        TestEnvironment.setDb(db);
        ConsoleGui console = new ConsoleGui(new ConsoleUrlProvider(config.getProperty("consoleurl")));
        TestEnvironment.setConsole(console);
        CrsWebGui crsweb = new CrsWebGui(config.getProperty("crsweburl"));
        TestEnvironment.setCrsweb(crsweb);
        SchedulerMonitor scheduler = new SchedulerMonitor(config.getProperty("schedulermonitorurl"),
                config.getProperty("schedulerusernh"), config.getProperty("scheduleruserpass"));
        TestEnvironment.setScheduler(scheduler);
        SshConnectionBank ssh = new SshConnectionBank();
        TestEnvironment.setSsh(ssh);

        emailSummaryGenerator = new EmailSummaryGenerator();
        internal = new User(config.getProperty("crsweblogin"), config.getProperty("crswebpassword"));
        internal.email = db.getNicHandleEmail(internal.login);

        fixMousePosition();

        IedrTestSuite.setUpSuiteOnce(smtpServer);
        smtpServer.start();
        emails = new HashSet<>();
        reloadData(true);
    }

    private boolean shouldIgnore(Browser browser) {
        List<Browser> ignoredBrowsers = new ArrayList<>();
        ignoredBrowsers.addAll(ignoredBrowsersTestWatcher.getIgnoredBrowsers());
        IgnoredBrowsers ignoredBrowsersAnnotation = getClass().getAnnotation(IgnoredBrowsers.class);
        if (ignoredBrowsersAnnotation != null) {
            ignoredBrowsers.addAll(Arrays.asList(ignoredBrowsersAnnotation.value()));
        }
        return ignoredBrowsers.contains(browser);
    }

    private void fixMousePosition() throws AWTException {
        if (Browser.IE.equals(browser())) {
            // IE is unstable if mouse pointer is within the boundaries of the IE window.
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
        }
    }

    private WebDriver getWebDriver() throws InvalidBrowserException, MalformedURLException {
        WebDriver wd = WebDriverManager.getManager().getDriver(browser());
        wd.manage().timeouts().implicitlyWait(defaultImplicitlyWaitTimeout, TimeUnit.SECONDS);
        wd.manage().timeouts().setScriptTimeout(defaultAsyncScriptTimeout, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        return wd;
    }

    public void tearDown() throws Exception {
        if (shouldIgnore(browser())) {
            return;
        }

        tearDownBrowser();
        try {
            if (ssh() != null) {
                ssh().close();
            }
        } catch (Exception e) {
            System.out.println("Failed to close ssh sessions");
            e.printStackTrace();
        }
        try {
            if (emails != null) {
                emails.clear();
            }
            if (smtpServer != null) {
                smtpServer.stop();
            }
        } catch (Exception e) {
            System.out.println("Failed to stop SMTP server");
            e.printStackTrace();
        }
        try {
            reloadData(false);
        } catch (Exception e) {
            System.out.println("Failed to reload data");
            e.printStackTrace();
        }
    }

    private void tearDownBrowser() {
        try {
            wd().getTitle();
        } catch (UnhandledAlertException e1) {
            try {
                handleAlerts();
            } catch (Exception e2) {
                System.out.println("Failed to handle alerts");
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            System.out.println("Failed to tear browser down");
            e3.printStackTrace();
        }
        try {
            wd().switchTo().defaultContent();
            if (console() != null) {
                console().forceLogout();
            }
        } catch (Exception e) {
            System.out.println("Failed to switch to default content");
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        WebDriverManager.getManager().closeBrowsersAfterTestCase();
    }

    protected void reloadData(boolean populate) throws IOException, SQLException {
        if (populate) {
            db().reloadData("sql_data/clean_data.sql", "sql_data/prepare_database.sql");
        }
        if (this.getResetDataScript() != null) {
            String loadScript = populate ? this.getLoadDataScript() : null;
            db().reloadData(this.getResetDataScript(), loadScript);
        }
        if (!populate) {
            db().reloadData("sql_data/clean_data.sql", null);
        }
    }

    protected abstract String getResetDataScript();

    protected abstract String getLoadDataScript();

    public Set<ActualEmailSummary> checkAndResetEmails(Set<ExpectedEmailSummary> expectedEmails) {
        return checkAndResetEmails(expectedEmails, true);
    }

    public Set<ActualEmailSummary> partiallyCheckAndResetEmails(Set<ExpectedEmailSummary> expectedEmails) {
        return checkAndResetEmails(expectedEmails, false);
    }

    public void waitForEmailReceived() {
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                Set<ActualEmailSummary> actualEmails = smtpServer.getEmailSummaries();
                return !actualEmails.isEmpty();
            }
        });
    }

    private Set<ActualEmailSummary> checkAndResetEmails(Set<ExpectedEmailSummary> expectedEmails,
            boolean checkUnexpected) {
        smtpServer.stop();
        Set<ActualEmailSummary> actualEmails = smtpServer.getEmailSummaries();
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(expectedEmails, actualEmails);
        try {
            rev.verify(checkUnexpected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        expectedEmails.clear();
        smtpServer.start();
        return actualEmails;
    }

    public Set<ActualEmailSummary> resetEmails() {
        smtpServer.stop();
        Set<ActualEmailSummary> emails = smtpServer.getEmailSummaries();
        smtpServer.start();
        return emails;
    }

    public Map<Integer, Integer> countEmailsFromJob(SchedulerJob job, List<ExpectedEmailSummary> expectedEmails)
            throws SQLException {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        resetEmails();
        scheduler().runJob(job);
        Set<ActualEmailSummary> actualEmails = resetEmails();
        for (ExpectedEmailSummary expected : expectedEmails) {
            int id = expected.id;
            counts.put(id, 0);
            for (ActualEmailSummary actual : actualEmails) {
                if (expected.id == actual.id) {
                    int count = counts.get(id);
                    counts.put(id, count + 1);
                }
            }
        }
        return counts;
    }

    public String extractAuthCodeFromReceivedEmails(User adminC, User billC, String domainName) throws SQLException {
        Set<ActualEmailSummary> actualEmails = resetEmails();
        return extractAuthCodeFromReceivedEmails(adminC, billC, domainName, actualEmails);
    }

    public String extractAuthCodeFromReceivedEmails(User adminC, User billC, String domainName,
            Set<ActualEmailSummary> actualEmails) throws SQLException {
        ExpectedEmailSummary base = emailSummaryGenerator.getAuthCodeOnDemandEmail(adminC, billC, domainName);
        ExpectedEmailSummary expectedAuthCodeEmail = new DetailedExpectedEmailSummary.SubjectContains(base, domainName);
        emails.add(expectedAuthCodeEmail);
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(emails, actualEmails);
        ActualEmailSummary actualAuthCodeEmail = rev.getActualEmail(expectedAuthCodeEmail);
        return extractAuthCodeFromEmail(expectedAuthCodeEmail, actualAuthCodeEmail);
    }

    public String extractAuthCodeFromEmail(ExpectedEmailSummary expected, ActualEmailSummary actual)
            throws SQLException {
        ReceivedEmailVerifier rev = new ReceivedEmailVerifier(expected, actual);
        Map<String, String> parameters = rev.getPopulatedParameters();
        return parameters.get("AUTHCODE");
    }

    public static boolean handleAlerts() {
        // Accept all alerts. This is in case the page is asking for
        // confirmation before leaving. If one test ends with such page, all
        // subsequent tests will fail on first attempt to change the page.
        // This implementation is much faster then trying to switch to alert and
        // catching exception if there's none.
        boolean alertPresent = true;
        boolean handledAnyAlerts = false;
        while (alertPresent) {
            alertPresent = false;
            try {
                wd().getTitle();
            } catch (WebDriverException e) {
                // Unexpected alerts are set to be ignored by WebDriver, so
                // after catching an exception, we can handle this alert.
                alertPresent = true;
                Alert alert = wd().switchTo().alert();
                alert.accept();
                handledAnyAlerts = true;
            }
        }
        return handledAnyAlerts;
    }

    protected void verifyCardDataInLogs(CardPaymentDetails details) throws JSchException, IOException {
        verifyCardDataInCrsAppLog(details);
        verifyCardDataInConsoleAppLog(details);
        verifyCardDataInSoapLog(details);
    }

    private void verifyCardDataInCrsAppLog(CardPaymentDetails details) throws JSchException, IOException {
        String logTail = ssh().crsws.getTomcatLogTail(150);
        assertFalse(logTail, logTail.contains(details.getCardNumber()));
        assertFalse(logTail, logTail.contains(details.getCardHolder()));
        assertTrue(logTail, logTail.contains("<card><number>######</number>"));
        assertTrue(logTail, logTail.contains("<expdate>######</expdate>"));
        assertTrue(logTail, logTail.contains("<chname>######</chname>"));
        assertTrue(logTail, logTail.contains("<type>######</type>"));
        assertTrue(logTail, logTail.contains("<cvn><number>######</number>"));
    }

    private void verifyCardDataInConsoleAppLog(CardPaymentDetails details) throws JSchException, IOException {
        String logTail = ssh().console.getConsoleLogTail(5000);
        List<String> lines = Splitter.on('\n').splitToList(logTail);
        for (String line : lines) {
            if (!line.contains(" [debug] ")) {
                assertFalse(line, line.contains(details.getCardNumber()));
                assertFalse(line, line.contains(details.getCardHolder()));
            }
        }
        assertTrue(logTail, logTail.contains("[cardholder] => ######"));
        assertTrue(logTail, logTail.contains("[creditcardno] => ######"));
        assertTrue(logTail, logTail.contains("[cardtype] => ######"));
        assertTrue(logTail, logTail.contains("[exp_month] => ######"));
        assertTrue(logTail, logTail.contains("[exp_year] => ######"));
        assertTrue(logTail, logTail.contains("[cvn] => ######"));
    }

    private void verifyCardDataInSoapLog(CardPaymentDetails details) throws JSchException, IOException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String location = config.getProperty("soaploglprefix") + "_" + df.format(new Date()) + ".log";
        String logTail = ssh().console.getLogTail(location, 5000);
        assertFalse(logTail, logTail.contains(details.getCardNumber()));
        assertFalse(logTail, logTail.contains(details.getCardHolder()));
        assertTrue(logTail, logTail.contains("<cardNumber>######</cardNumber>"));
        assertTrue(logTail, logTail.contains("<cardExpDate>######</cardExpDate>"));
        assertTrue(logTail, logTail.contains("<cardType>######</cardType>"));
        assertTrue(logTail, logTail.contains("<cardHolderName>######</cardHolderName>"));
        assertTrue(logTail, logTail.contains("<cvnNumber>######</cvnNumber>"));
    }
}
