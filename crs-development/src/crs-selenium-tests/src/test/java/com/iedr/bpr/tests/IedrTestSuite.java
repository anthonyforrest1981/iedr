package com.iedr.bpr.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.extensions.cpsuite.ClasspathSuite.BeforeSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.IncludeJars;
import org.junit.runner.RunWith;

import com.iedr.bpr.tests.ExtendedClasspathSuite.AfterSuite;
import com.iedr.bpr.tests.utils.SmtpServerWrapper;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;

@IncludeJars(true)
@RunWith(ExtendedClasspathSuite.class)
@ClassnameFilters({".*(console|crsweb|crsscheduler).*"})
public class IedrTestSuite {

    private static boolean setUpRequired = true;

    public static void setUpSuiteOnce(SmtpServerWrapper smtpServer) throws SQLException, IOException {
        if (setUpRequired) {
            db().reloadData("sql_data/clean_data.sql", "sql_data/prepare_database.sql");
            generateAndDiscardEmails(smtpServer);
            setUpRequired = false;
        }
    }

    @BeforeSuite
    public static void setUpSuite() {
        // By default browser will be closed after each TestCase (so that we can
        // run them separately). Changing this setting will prevent this
        // behavior, we will close all browsers in tearDownSuite method.
        WebDriverManager.getManager().shouldCloseBrowsersAfterTestCase = false;
    }

    @AfterSuite
    public static void tearDownSuite() {
        WebDriverManager.getManager().closeBrowsers();
    }

    private static void generateAndDiscardEmails(SmtpServerWrapper smtpServer) throws SQLException {
        // Run all jobs to generate emails for the already existing data, then
        // wait for those emails to be sent and restart SMTP server. This is to
        // make sure that no additional emails will be sent when running
        // scheduler jobs during test suite run.
        smtpServer.start();
        scheduler().runAllJobs();
        smtpServer.stop();
    }

}
