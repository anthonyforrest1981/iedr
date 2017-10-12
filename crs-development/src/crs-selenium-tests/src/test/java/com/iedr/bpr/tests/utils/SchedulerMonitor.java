package com.iedr.bpr.tests.utils;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.CaseFormat;
import com.google.common.base.Predicate;
import com.iedr.bpr.tests.gui.Gui;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class SchedulerMonitor extends Gui {

    private String base;
    private String userNh;
    private String userPass;

    public SchedulerMonitor(String base, String userNh, String userPass) {
        this.base = base;
        this.userNh = userNh;
        this.userPass = userPass;
    }

    public void runJob(SchedulerJob job) throws SQLException {
        tryToLogin();
        tryToStart();
        rawRun(job);
        tryToLogout();
    }

    private void tryToLogin() {
        wd().get(base + "schedulerMonitor");
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return wd().getCurrentUrl().startsWith(base);
            }
        });
        if (wd().getCurrentUrl().endsWith("schedulerMonitorAuth")) {
            fillInput(By.name("nichandle"), userNh);
            fillInput(By.name("password"), userPass);
            clickElement(By.tagName("button"));
        }
    }

    private void tryToStart() {
        if (!isSchedulerRunning()) {
            clickElement(By.cssSelector("button[formaction$=start]"));
        }
    }

    private boolean isSchedulerRunning() {
        return "Running".equals(wd().findElement(By.cssSelector(".panel-heading small")).getText());
    }

    private void tryToLogout() {
        clickElement(By.cssSelector("form[action$=logout] button"));
    }

    public void runAllJobs() throws SQLException {
        tryToLogin();
        tryToStart();
        for (SchedulerJob job : SchedulerJob.values()) {
            rawRun(job);
        }
        tryToLogout();
    }

    private void rawRun(SchedulerJob job) throws SQLException {
        // assumption - wd has already loaded schedulerMonitor
        String jobName = getJobName(job);
        // We're subtracting 1 second from the date returned by the database, because if the scheduler machine has a
        // slightly earlier system time, then the difference between the trimmed dates might get up to 1 second.
        Date startDate = DateUtils.addSeconds(db().getCurrentDate(), -1);
        By button = By.cssSelector("form[action$=" + jobName + "] button");
        try {
            clickElement(button);
        } catch (StaleElementReferenceException e) {
            // If the previous job finished instantly, the page will still be refreshed after 2s. If this refresh
            // happens between finding the button and clicking it (very rare), we'll get StaleElementReferenceException.
            // We could wait until the page is refreshed, but it's faster not to wait and retry in those rare
            // situations.
            clickElement(button);
        }
        waitForJobStarted(jobName, startDate);
        waitForJobFinished(jobName, startDate);
    }

    private void waitForJobStarted(final String jobName, final Date startDate) {
        // This method is for making sure that the job has started. To run the
        // job properly we only need to wait till it's finished, but in case of
        // a timeout we get information whether the error occurred during the
        // job or the job hasn't even started.
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                try {
                    return db().isSchedulerJobStarted(jobName, startDate);
                } catch (SQLException e) {
                    return false;
                }
            }
        });
    }

    private void waitForJobFinished(final String jobName, final Date startDate) {
        waitForJobFinishedInDb(jobName, startDate);
        waitForJobFinishedInBrowser(jobName);
    }

    private void waitForJobFinishedInDb(final String jobName, final Date startDate) {
        try {
            new WebDriverWait(wd(), 300).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    try {
                        return db().isSchedulerJobFinished(jobName, startDate);
                    } catch (SQLException e) {
                        return false;
                    }
                }
            });
        } catch (TimeoutException e) {
            throw new TimeoutException(String.format("Timeout in %s job (start_TS: %s)", jobName, startDate), e);
        }
    }

    private String getJobStatusFromBrowser(String jobName) {
        String xpath = String.format("(//div[contains(@class, 'panel-info')]//td[.='%s']/../td)[3]", jobName);
        return wd().findElement(By.xpath(xpath)).getText();
    }

    private void waitForJobFinishedInBrowser(final String jobName) {
        try {
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    try {
                        return "Finished".equals(getJobStatusFromBrowser(jobName));
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        } catch (TimeoutException e) {
            String jobStatus = getJobStatusFromBrowser(jobName);
            if (!"Finished".equals(getJobStatusFromBrowser(jobName))) {
                throw new TimeoutException(String.format("Timeout in %s job (status: %s)", jobName, jobStatus), e);
            }
        }
    }

    public enum SchedulerJob {
        AUTORENEWAL,
        DNS_CHECK_FAILURE_NOTIFICATION,
        TICKET_AND_TRANSACTION_CLEANUP,
        EXPIRING_TICKET_EMAIL,
        INVOICING,
        NIC_HANDLE_CLEANUP,
        PUSH_Q,
        RENEWAL_NOTIFICATION,
        TRANSACTION_INVALIDATION,
        TRIPLE_PASS
    }

    private String getJobName(SchedulerJob job) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, job.name());
    }

}
