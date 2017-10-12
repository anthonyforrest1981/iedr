package com.iedr.bpr.tests;

import org.openqa.selenium.WebDriver;

import com.iedr.bpr.tests.gui.ConsoleGui;
import com.iedr.bpr.tests.gui.CrsWebGui;
import com.iedr.bpr.tests.utils.CrsDb;
import com.iedr.bpr.tests.utils.SchedulerMonitor;
import com.iedr.bpr.tests.utils.ssh.SshConnectionBank;

public class TestEnvironment {

    private static WebDriver wd = null;
    private static ConsoleGui console = null;
    private static CrsWebGui crsweb = null;
    private static SchedulerMonitor scheduler = null;
    private static CrsDb db = null;
    private static SshConnectionBank ssh = null;
    private static SeleniumTest.Browser browser = null;

    public static WebDriver wd() {
        return wd;
    }

    public static void setWd(WebDriver wd) {
        TestEnvironment.wd = wd;
    }

    public static ConsoleGui console() {
        return console;
    }

    public static void setConsole(ConsoleGui console) {
        TestEnvironment.console = console;
    }

    public static CrsWebGui crsweb() {
        return crsweb;
    }

    public static void setCrsweb(CrsWebGui crsweb) {
        TestEnvironment.crsweb = crsweb;
    }

    public static SchedulerMonitor scheduler() {
        return scheduler;
    }

    public static void setScheduler(SchedulerMonitor scheduler) {
        TestEnvironment.scheduler = scheduler;
    }

    public static CrsDb db() {
        return db;
    }

    public static void setDb(CrsDb db) {
        TestEnvironment.db = db;
    }

    public static SshConnectionBank ssh() {
        return ssh;
    }

    public static void setSsh(SshConnectionBank ssh) {
        TestEnvironment.ssh = ssh;
    }

    public static SeleniumTest.Browser browser() {
        return browser;
    }

    public static void setBrowser(SeleniumTest.Browser browser) {
        TestEnvironment.browser = browser;
    }

}
