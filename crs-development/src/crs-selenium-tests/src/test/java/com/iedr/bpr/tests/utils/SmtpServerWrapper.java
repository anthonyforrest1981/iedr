package com.iedr.bpr.tests.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.InvalidBrowserException;
import com.iedr.bpr.tests.WebDriverManager;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;

public class SmtpServerWrapper {
    private SmtpServer server;
    private WebDriver wd;
    private List<String> emailSendingMachines;
    private File emailLog;

    public SmtpServerWrapper(Properties config, String logDirectory) throws InvalidBrowserException,
            MalformedURLException {
        server = SmtpServer.getServer();
        this.wd = WebDriverManager.getManager().getEmailWorkerDriver();
        emailSendingMachines = Arrays.asList(config.getProperty("crsweburl"), config.getProperty("crswsurl"),
                config.getProperty("schedulermonitorurl"));
        emailLog = new File(logDirectory, "emails.log");
    }

    public void start() {
        waitForServerToStart();
    }

    public void stop() {
        // Wait for all emails to be sent.
        for (String url : emailSendingMachines) {
            String emailWorkerMonitorUrl = getEmailWorkerMonitorUrl(url);
            waitForEmptyEmailQueue(wd, emailWorkerMonitorUrl);
        }
        waitForServerToStop();
        logEmails();
    }

    public void restart() {
        stop();
        start();
    }

    public Set<ActualEmailSummary> getEmailSummaries() {
        return server.getEmailSummaries();
    }

    private static String getEmailWorkerMonitorUrl(String url) {
        // Email worker monitor is a servlet running on each of the machines
        // that generate emails.
        return url.replaceAll("/[\\w]*$", "/emailWorkerMonitor");
    }

    private void waitForServerToStart() {
        new WebDriverWait(wd, 10, 1000).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                try {
                    server.start();
                } catch (RuntimeException e) {
                    return false;
                }
                return true;
            }
        });
    }

    private void waitForServerToStop() {
        new WebDriverWait(wd, 10, 1000).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                if (!server.isStopped()) {
                    server.stop();
                }
                return server.isStopped();
            }
        });
    }

    private String getEmailAsLog(ActualEmailSummary email) {
        return String.valueOf(email.id);
    }

    private String getEmailsAsLog() {
        List<String> emailsAsLog = new ArrayList<String>();
        Set<ActualEmailSummary> emails = server.getEmailSummaries();
        for (ActualEmailSummary email : emails) {
            emailsAsLog.add(getEmailAsLog(email));
        }
        return StringUtils.join(emailsAsLog, System.getProperty("line.separator"));
    }

    private void logEmails() {
        String emailsAsLog = getEmailsAsLog();
        if ("".equals(emailsAsLog)) {
            return;
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(emailLog, true)));
            out.println(emailsAsLog);
        } catch (IOException e) {
            System.out.println("Failed to log emails");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void waitForEmptyEmailQueue(WebDriver wd, final String emailWorkerMonitorUrl) {
        final String message = "Email sender thread is running with 0 emails in the queue";
        new WebDriverWait(wd, 360, 500).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                driver.get(emailWorkerMonitorUrl + "?action=start");
                return driver.findElement(By.tagName("html")).getText().contains(message);
            }
        });
    }
}
