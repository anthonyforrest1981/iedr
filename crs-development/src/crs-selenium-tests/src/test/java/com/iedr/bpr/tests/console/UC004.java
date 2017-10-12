package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.CardPaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC004 extends SeleniumTest {

    public UC004(Browser browser) {
        super(browser);
    }

    List<Domain> domains;

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc004_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc004_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        User user = this.registrar;
        db().setDepositAmount(user.login, 10000);
        domains = new ArrayList<Domain>();
    }

    @Override
    public void tearDown() throws Exception {
        try {
            domains.clear();
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void test_uc004_sc01alt1() throws SQLException {
        // UC004-SC01 (Alt): Registrar Happy Path
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01alt1a.ie", 1));
        domains.add(new Domain("uc004-sc01alt1b.ie", 2));
        domains.add(new Domain("uc004-sc01alt1c.ie", 3));
        domains.add(new Domain("uc004-sc01alt1d.ie", 4));
        domains.add(new Domain("uc004-sc01alt1e.ie", 5));
        domains.add(new Domain("uc004-sc01alt1f.ie", 6));
        domains.add(new Domain("uc004-sc01alt1g.ie", 7));
        domains.add(new Domain("uc004-sc01alt1h.ie", 8));
        domains.add(new Domain("uc004-sc01alt1i.ie", 9));
        domains.add(new Domain("uc004-sc01alt1j.ie", 10));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt1", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc01alt2() throws SQLException {
        // UC004-SC01 (Alt): Registrar Happy Path (Future Renewals)
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 1;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01alt2a.ie", 1));
        domains.add(new Domain("uc004-sc01alt2b.ie", 2));
        domains.add(new Domain("uc004-sc01alt2c.ie", 3));
        domains.add(new Domain("uc004-sc01alt2d.ie", 4));
        domains.add(new Domain("uc004-sc01alt2e.ie", 5));
        domains.add(new Domain("uc004-sc01alt2f.ie", 6));
        domains.add(new Domain("uc004-sc01alt2g.ie", 7));
        domains.add(new Domain("uc004-sc01alt2h.ie", 8));
        domains.add(new Domain("uc004-sc01alt2i.ie", 9));
        domains.add(new Domain("uc004-sc01alt2j.ie", 10));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt2", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc01alt3() throws SQLException {
        // UC004-SC01 (Alt): Registrar Happy Path (Future Renewals II)
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 11;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01alt3a.ie", 1));
        domains.add(new Domain("uc004-sc01alt3b.ie", 2));
        domains.add(new Domain("uc004-sc01alt3c.ie", 3));
        domains.add(new Domain("uc004-sc01alt3d.ie", 4));
        domains.add(new Domain("uc004-sc01alt3e.ie", 5));
        domains.add(new Domain("uc004-sc01alt3f.ie", 6));
        domains.add(new Domain("uc004-sc01alt3g.ie", 7));
        domains.add(new Domain("uc004-sc01alt3h.ie", 8));
        domains.add(new Domain("uc004-sc01alt3i.ie", 9));
        domains.add(new Domain("uc004-sc01alt3j.ie", 10));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt3", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc03() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc03a.ie", 1));
        domains.add(new Domain("uc004-sc03b.ie", 10));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc03", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Ignore
    @Test
    public void test_uc004_sc04() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc04a.ie", 5));
        domains.add(new Domain("uc004-sc04b.ie", 5));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc04alt1() throws SQLException, IOException {
        // UC004-SC04 (Alt): Direct using Debit Card
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        for (int i = 1; i < 11; i++) {
            db().reloadData("sql_data/console/uc004_reset_data.sql", "sql_data/console/uc004_data.sql");
            domains.clear();
            emails.clear();
            domains.add(new Domain("uc004-sc04alt.ie", i));
            emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
            PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
            test_pay_for_domains(user, "uc004-sc04alt", monthsToRenewal, paymentDetails, nrp, outcome);
            smtpServer.restart();
        }
    }

    @Test
    public void test_uc004_sc04alt2() throws SQLException {
        // UC004-SC04 (Alt): Direct using Debit Card (NRP)
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        domains.add(new Domain("uc004-sc04alta.ie", 4));
        domains.add(new Domain("uc004-sc04altb.ie", 8));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        for (Domain domain : domains) {
            emails.add(new DetailedExpectedEmailSummary.SubjectContains(emailSummaryGenerator
                    .getNrpPaidReactivationEmail(user, domain.name, true, paymentDetails.getMethod()), domain.name));
        }
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04alt", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc07() throws SQLException {
        final User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc07a.ie", 1));
        domains.add(new Domain("uc004-sc07b.ie", 1));
        PaymentOutcome outcome = new PaymentOutcome() {
            public void before() throws SQLException {
                db().setDepositAmount(user.login, 0);
            }

            public void after() {
                String message = "You do not have enough founds in your account to complete this action.";
                console().waitForTextPresentOnPage(message);
                message = "Please top up your deposit account and try again.";
                console().waitForTextPresentOnPage(message);
            }
        };
        test_pay_for_domains(user, "uc004-sc07", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc11() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.INVALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc11.ie", 1));
        PaymentOutcome outcome = new PaymentCardDeclined();
        test_pay_for_domains(user, "uc004-sc11", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_sc17() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc17.ie", 2));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc17", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_ren2rcc5() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 12;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01alt1a.ie", 1));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt", monthsToRenewal1, paymentDetails, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt", monthsToRenewal2, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_ren2rcd6() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 12;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01a.ie", 1));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01", monthsToRenewal1, paymentDetails, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01", monthsToRenewal2, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_ren2dcc7() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 12;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc03a.ie", 1));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc03", monthsToRenewal1, paymentDetails, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc03", monthsToRenewal2, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_ren2dcd8() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 24;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc04a.ie", 2));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04", monthsToRenewal1, paymentDetails, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04", monthsToRenewal2, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_ren3cctodep9() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails1 = PredefinedPayments.VALID_CREDIT_CARD;
        PaymentDetails paymentDetails2 = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 12;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01alt1a.ie", 1));
        emails.add(getRenewalsReservationEmail(user, paymentDetails1.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails1.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt1", monthsToRenewal1, paymentDetails1, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.clear();
        emails.add(getRenewalsReservationEmail(user, paymentDetails2.getMethod()));
        outcome = new PaymentSuccess(paymentDetails2.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01alt1", monthsToRenewal2, paymentDetails2, nrp, outcome);
    }

    @Test
    public void test_uc004_ren3deptocc10() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails1 = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        PaymentDetails paymentDetails2 = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 24;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc01a.ie", 2));
        emails.add(getRenewalsReservationEmail(user, paymentDetails1.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails1.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01", monthsToRenewal1, paymentDetails1, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.clear();
        emails.add(getRenewalsReservationEmail(user, paymentDetails2.getMethod()));
        outcome = new PaymentSuccess(paymentDetails2.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc01", monthsToRenewal2, paymentDetails2, nrp, outcome);
    }

    @Test
    public void test_uc004_ren3debittocc11() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails1 = PredefinedPayments.VALID_DEBIT_CARD;
        PaymentDetails paymentDetails2 = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 24;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc04a.ie", 2));
        emails.add(getRenewalsReservationEmail(user, paymentDetails1.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails1.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04", monthsToRenewal1, paymentDetails1, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.clear();
        emails.add(getRenewalsReservationEmail(user, paymentDetails2.getMethod()));
        outcome = new PaymentSuccess(paymentDetails2.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc04", monthsToRenewal2, paymentDetails2, nrp, outcome);
    }

    @Test
    public void test_uc004_ren3cctodebit12() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails1 = PredefinedPayments.VALID_CREDIT_CARD;
        PaymentDetails paymentDetails2 = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal1 = 0;
        int monthsToRenewal2 = 24;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc03a.ie", 2));
        emails.add(getRenewalsReservationEmail(user, paymentDetails1.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails1.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc03", monthsToRenewal1, paymentDetails1, nrp, outcome);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        emails.clear();
        emails.add(getRenewalsReservationEmail(user, paymentDetails2.getMethod()));
        outcome = new PaymentSuccess(paymentDetails2.getMethod(), emails);
        test_pay_for_domains(user, "uc004-sc03", monthsToRenewal2, paymentDetails2, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcd1() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 18);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcd2() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 19);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcc5() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 18);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcc6() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 19);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcc7() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 20);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrprcc8() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 21);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcc9() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 26);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcc10() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 27);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcl13() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 26);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcl14() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 27);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcl15() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 28);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpdcl16() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 29);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcd42() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 2);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcd43() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 3);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcd44() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 20);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcd45() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 21);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcc46() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 2);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcc47() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 3);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcc48() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 20);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwrcc49() throws SQLException {
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-rcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-rcatnrp.ie", 21);
        test_pay_for_domains(user, "uc004-rcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcc50() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 10);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcc51() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 11);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcc52() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 28);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcc53() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 29);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcl54() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 10);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcl55() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, true,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 11);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcl56() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 28);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nrpwdcl57() throws SQLException {
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_DEBIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-dcatnrp.ie", 1);
        domains.add(domain);
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domain.name, false,
                paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        db().setDsmStateForDomain("uc004-dcatnrp.ie", 29);
        test_pay_for_domains(user, "uc004-dcatnrp", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nosc01() throws SQLException {
        // UC#004: Make a Payment for Existing Domain - Check for duplicate
        // payment entry
        User user = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-sc03a.ie", 1));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains_double_click(user, "uc004-sc03", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nosc02() throws SQLException {
        // UC#004: Make a Payment for Existing Domain - Period selection on
        // renewal over mutli-page grids
        User user = this.registrar;
        PaymentMethod method = PaymentMethod.ADP;
        int monthsToRenewal = 0;
        int paymentPeriod = 10;
        console().login(user);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, false, monthsToRenewal);
        pfd.view();
        final String domainNameA = selectFirstDomain(paymentPeriod, user);
        console().clickElement(By.id("next_thisJqGridPager"));
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver wd) {
                return !console().isElementPresentInstantaneously(By.id(domainNameA));
            }
        });
        String domainNameB = selectFirstDomain(paymentPeriod, user);
        pfd.selectPaymentMethod(method, monthsToRenewal);
        checkPaymentPeriodSelected(domainNameA, user, 10);
        checkPaymentPeriodSelected(domainNameB, user, 10);
    }

    @Test
    public void test_uc004_nosc03() throws SQLException {
        // UC#004: Make a Payment for Existing Domain - Declined Message should
        // be returned - Current Renewals
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.INVALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-nosc03.ie", 1));
        PaymentOutcome outcome = new PaymentCardDeclined();
        test_pay_for_domains(user, "uc004-nosc03", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nosc04() throws SQLException {
        // UC#004: Make a Payment for Existing Domain - Declined Message should
        // be returned - Current NRP Statuses
        User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.INVALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        domains.add(new Domain("uc004-nosc04.ie", 1));
        PaymentOutcome outcome = new PaymentCardDeclined();
        test_pay_for_domains(user, "uc004-nosc04", monthsToRenewal, paymentDetails, nrp, outcome);
    }

    @Test
    public void test_uc004_nosc05() throws SQLException {
        // UC#004: Make a Payment for Existing Domain - Laser Card option not
        // available
        User user = this.registrar;
        PaymentMethod method = PaymentMethod.CARD;
        int monthsToRenewal = 0;
        boolean nrp = true;
        Domain domain = new Domain("uc004-nosc05.ie", 1);
        domains.add(domain);

        console().login(user);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, nrp, monthsToRenewal);
        pfd.openPaymentScreen(user, domain.name, method);

        List<WebElement> paymentOptions = wd().findElements(By.xpath("//img[contains(@onclick, 'setcardtype')]"));
        Set<String> cardTypes = new HashSet<String>();
        for (WebElement option : paymentOptions) {
            String type = option.getAttribute("onclick").replace("setcardtype('", "").replace("')", "");
            cardTypes.add(type);
        }
        assertEquals(new HashSet<String>(Arrays.asList("MC", "VISA")), cardTypes);
    }

    @Test
    public void test_uc004_nosc06() throws SQLException, JSchException, IOException {
        // UC#004: Make Payment for Existing Domain - Don't log CC details
        User user = this.direct;
        CardPaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int monthsToRenewal = 0;
        boolean nrp = false;
        domains.add(new Domain("uc004-nosc06a.ie", 1));
        domains.add(new Domain("uc004-nosc06b.ie", 10));
        emails.add(getRenewalsReservationEmail(user, paymentDetails.getMethod()));
        PaymentOutcome outcome = new PaymentSuccess(paymentDetails.getMethod(), emails);
        test_pay_for_domains(user, "uc004-nosc06", monthsToRenewal, paymentDetails, nrp, outcome);
        verifyCardDataInLogs(paymentDetails);
    }

    private interface PaymentOutcome {
        public void before() throws SQLException;

        public void after() throws SQLException;
    }

    private class PaymentSuccess implements PaymentOutcome {

        PaymentMethod method;
        Set<ExpectedEmailSummary> emails;

        public PaymentSuccess(PaymentMethod method, Set<ExpectedEmailSummary> emails) {
            super();
            this.method = method;
            this.emails = emails;
        }

        public void before() {

        };

        public void after() throws SQLException {
            // Wait for page to load.
            new WebDriverWait(wd(), 60).until(ExpectedConditions.presenceOfElementLocated(By
                    .xpath("//input[contains(@value, 'Print This Page')]")));
            checkDomainPaymentSuccess(method);
            checkDbState();
            checkAndResetEmails(emails);
        }
    }

    private class PaymentCardDeclined implements PaymentOutcome {
        public void before() throws SQLException {}

        public void after() {
            String message = "Card declined";
            console().waitForTextPresentOnPage(message);
            console().assertElementPresent(By.linkText("Click here to try again/another card."));
        }
    };

    private void test_pay_for_domains(User user, String domainPrefix, int monthsToRenewal,
            PaymentDetails paymentDetails, boolean nrp, PaymentOutcome outcome) throws SQLException {
        test_pay_for_domains(user, domainPrefix, monthsToRenewal, paymentDetails, nrp, outcome, false);
    }

    private void test_pay_for_domains_double_click(User user, String domainPrefix, int monthsToRenewal,
            PaymentDetails paymentDetails, boolean nrp, PaymentOutcome outcome) throws SQLException {
        test_pay_for_domains(user, domainPrefix, monthsToRenewal, paymentDetails, nrp, outcome, true);
    }

    private void test_pay_for_domains(User user, String domainPrefix, int monthsToRenewal,
            PaymentDetails paymentDetails, boolean nrp, PaymentOutcome outcome, boolean doubleClick)
            throws SQLException {
        outcome.before();
        console().login(user);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, nrp, monthsToRenewal);
        pfd.payForDomains(user, domainPrefix, paymentDetails, doubleClick);
        outcome.after();
    }

    private void checkDomainPaymentSuccess(PaymentMethod method) {
        String message = null;
        if (method == PaymentMethod.ADP) {
            message = "by payment from deposit";
        } else if (method == PaymentMethod.CARD) {
            message = "by payment online";
        }
        console().waitForTextPresentOnPage(message);
        console().waitForTextPresentOnPage("Transaction ID");
    }

    private String selectFirstDomain(int paymentPeriod, User user) throws SQLException {
        int productId = db().getProductId(paymentPeriod, false, user.isRegistrar);
        WebElement firstRow = wd().findElement(By.cssSelector("#thisJqGrid tbody tr.jqgrow"));
        String domainName = firstRow.getAttribute("id");
        console().clickElement(firstRow.findElement(By.id(String.format("jqg_thisJqGrid_%s", domainName))));
        console().selectOptionByValue(By.id("period_" + domainName), Integer.toString(productId));
        return domainName;
    }

    private void checkPaymentPeriodSelected(String domainName, User user, int paymentPeriod) throws SQLException {
        WebElement select = wd().findElement(By.id(String.format("CurrentRenewalsSelectionModel_list_%s", domainName)));
        WebElement option = (new Select(select)).getFirstSelectedOption();
        int expected = db().getProductId(paymentPeriod, false, user.isRegistrar);
        assertEquals(Integer.toString(expected), option.getAttribute("value"));
    }

    private void checkDbState() throws SQLException {
        int transactionId = -1;
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            assertEquals(1, db().getReservationsCount(domain.name));
            int reservationId = db().getReservationId(domain.name);
            if (i == 0) {
                transactionId = db().getReservationTransactionId(reservationId);
            }
            assertEquals(transactionId, db().getReservationTransactionId(reservationId));
            assertEquals(0, db().getTicketsCountForDomain(domain.name));
            assertTrue(db().reservationReadyForSettlement(reservationId));
            assertFalse(db().reservationSettled(reservationId));
            assertTrue(db().transactionFinanciallyPassed(transactionId));
        }
    }

    private ExpectedEmailSummary getRenewalsReservationEmail(User user, PaymentMethod method) throws SQLException {
        return emailSummaryGenerator.getRenewalsPaymentEmail(user, method);
    }

}
