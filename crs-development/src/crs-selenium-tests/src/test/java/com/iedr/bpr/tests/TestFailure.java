package com.iedr.bpr.tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.iedr.bpr.tests.utils.ssh.CrsSshConnection;

import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class TestFailure extends TestWatcher {

    private static String logDirectory = null;

    public void takeScreenShot(Description description) {
        String fileName = generateScreenShotName(description);
        takeScreenShot(fileName);
    }

    public void takeScreenShot(String fileName) {
        if (SeleniumTest.handleAlerts()) {
            System.out.println(String.format("There were alerts present while taking screenshot (%s)", fileName));
        }
        File ss = ((TakesScreenshot) wd()).getScreenshotAs(OutputType.FILE);
        try {
            File writeTo = new File(logDirectory, fileName);
            FileUtils.copyFile(ss, writeTo);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println(String.format("Failed to write screenshot to %s", logDirectory));
        }
    }

    public String generateScreenShotName(String className, String methodName) {
        return generateFileName(className, methodName, "png");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        if (getOrCreateLogDirectory() == null) {
            return;
        }
        try {
            takeScreenShot(description);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            saveHtml(description);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            saveLogs(description);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public String getOrCreateLogDirectory() {
        if (logDirectory == null) {
            try {
                logDirectory = createLogDirectory();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to create log directory");
                return null;
            }
        }
        return logDirectory;
    }

    private String createLogDirectory() throws IOException {
        Properties config = TestConfig.getConfig();
        String outerLogDirectory = config.getProperty("logdirectory");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String prefix = String.format("testRun-%s-", sdf.format(new Date()));
        File logDir = File.createTempFile(prefix, "-log", new File(outerLogDirectory));
        if (!logDir.delete()) {
            throw new IOException();
        }
        if (!logDir.mkdir()) {
            throw new IOException();
        }
        return logDir.getAbsolutePath();
    }

    private void saveHtml(Description description) {
        String html = wd().getPageSource();
        String fileName = generateFileName(description.getClassName(), description.getMethodName(), "html.log");
        saveTextToFile(html, fileName);
    }

    private void saveLogs(Description description) {
        saveLogsFromTomcatMachine(description, "crsws", ssh().crsws);
        saveLogsFromTomcatMachine(description, "crsweb", ssh().crsweb);
        saveLogsFromTomcatMachine(description, "crsscheduler", ssh().scheduler);
        saveLogsFromConsole(description, "console", ssh().console);
    }

    private void saveLogsFromTomcatMachine(Description description, String machineName, CrsSshConnection conn) {
        String logTail;
        try {
            logTail = conn.getTomcatLogTail(400);
        } catch (Exception e) {
            System.out.println(String.format("Failed to get log tail from %s", machineName));
            e.printStackTrace();
            return;
        }
        saveLogs(description, machineName, logTail);
    }

    private void saveLogsFromConsole(Description description, String machineName, CrsSshConnection conn) {
        String logTail;
        try {
            logTail = conn.getConsoleLogTail(400);
        } catch (Exception e) {
            System.out.println(String.format("Failed to get log tail from %s", machineName));
            e.printStackTrace();
            return;
        }
        saveLogs(description, machineName, logTail);
    }

    private void saveLogs(Description description, String machineName, String logTail) {
        String suffix = String.format("%s.log", machineName);
        String fileName = generateFileName(description.getClassName(), description.getMethodName(), suffix);
        saveTextToFile(logTail, fileName);
    }

    private void saveTextToFile(String text, String fileName) {
        File logFile = new File(logDirectory, fileName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(logFile);
            writer.println(text);
        } catch (Exception e) {
            System.out.println(String.format("Failed to write log tail to %s", logFile.getAbsolutePath()));
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String generateScreenShotName(Description description) {
        return generateScreenShotName(description.getClassName(), description.getMethodName());
    }

    private String generateFileName(String className, String methodName, String extension) {
        String fileName = String.format("%s.%s.%s", className, methodName, extension);
        return fileName.replace(" ", "").replace(":", "");
    }

}
