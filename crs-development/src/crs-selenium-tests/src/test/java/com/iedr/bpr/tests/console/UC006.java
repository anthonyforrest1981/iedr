package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.console.*;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.gui.ConsoleGui.CardTypeXPath;
import com.iedr.bpr.tests.pages.console.DomainTransferDetailsPage;
import com.iedr.bpr.tests.pages.console.DomainTransferPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.NameserverUtils;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.NicHandleUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UC006 extends SeleniumTest {

    User losingDirect = new User("UC006AA-IEDR", "Passw0rd!", false, "uc006_aa@iedr.ie");

    public UC006(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc006_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc006_data.sql";
    }

    /**
     * UC006-SC01: (Alt) Registrar - Registrar Transfer Basic - CC
     */
    @Test
    public void test_uc006_sc01alt1() throws SQLException {
        User user = this.registrar;
        User losingUser = this.registrarNonVat;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc01alt1.ie", paymentDetails, paymentPeriod, outcome);
    }

    /**
     * UC006-SC01: (Alt) Registrar Charity - Registrar Transfer Basic
     */
    @Test
    public void test_uc006_sc01alt2() throws SQLException {
        User user = this.registrar;
        User losingUser = this.registrarNonVat;
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc01alt2.ie", paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_sc02() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc02.ie", paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_sc06() throws SQLException {
        User user = this.direct;
        User losingUser = this.losingDirect;
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc06.ie", paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_sc08() throws SQLException {
        final User user = this.registrar;
        PaymentDetails paymentDetails = PredefinedPayments.INVALID_CREDIT_CARD;
        int paymentPeriod = 1;
        TransferOutcome outcome = new TransferOutcome() {
            public void after(String domainName) throws SQLException {
                String message = "Card declined";
                console().waitForTextPresentOnPage(message, 60);
                // Go back to form.
                console().clickElement(By.linkText("Click here to try again/another card."));
                verifyForm(user.login, user.login, "ns1.uc006-sc08.ie", "ns2.uc006-sc08.ie", "10.10.1.1", "10.10.1.2");
                assertEquals("A", db().getNrpStatusForDomain(domainName));
            }
        };
        test_domain_transfer(user, "uc006-sc08.ie", paymentDetails, paymentPeriod, outcome);
    }

    private void verifyForm(String adminNh, String techNh, String dns1, String dns2, String dns1Ip, String dns2Ip) {
        verifyInput("DomainsTransferDetails_admin_contact_nic_1", adminNh);
        verifyInput("DomainsTransferDetails_tech_contact", techNh);
        verifyInput("ns_0", dns1);
        verifyInput("ns_1", dns2);
        verifyInput("ipv4_0", dns1Ip);
        verifyInput("ipv4_1", dns2Ip);
        assertTrue(wd().findElement(By.id("DomainsTransferDetails_accept_tnc")).isSelected());
    }

    private void verifyInput(String inputId, String value) {
        assertEquals(wd().findElement(By.id(inputId)).getAttribute("value"), value);
    }

    @Test
    public void test_uc006_sc09() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc09.ie", paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_sc10() throws SQLException {
        User user = this.direct;
        User losingUser = this.losingDirect;
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-sc10.ie", paymentDetails, paymentPeriod, outcome);
    }

    @Ignore
    @Test
    public void test_uc006_dcfail4() {
        User user = this.direct;
        test_domain_transfer_debit_card_failure(user, "uc006-dcfail4.ie");
    }

    @Test
    public void test_uc006_xnrpimrcc26() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 26);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpisrcc27() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 27);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvmrcc28() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 28);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvsrcc29() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 29);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpimrcd30() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 26);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpisrcd31() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 27);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvmrcd32() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 28);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvsrcd33() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 29);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpimdcc34() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 18);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpisdcc35() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 19);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPI");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvmdcc36() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 20);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpvsdcc36() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        db().setDsmStateForDomain(domainName, 21);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Ignore
    @Test
    public void test_uc006_xnrpimdcdfail37() throws SQLException {
        User user = this.direct;
        String domainName = "uc006-xnrpdc.ie";
        db().setDsmStateForDomain(domainName, 18);
        test_domain_transfer_debit_card_failure(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc006_xnrpisdcdfail37() throws SQLException {
        User user = this.direct;
        String domainName = "uc006-xnrpdc.ie";
        db().setDsmStateForDomain(domainName, 19);
        test_domain_transfer_debit_card_failure(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc006_xnrpvmdcdfail37() throws SQLException {
        User user = this.direct;
        String domainName = "uc006-xnrpdc.ie";
        db().setDsmStateForDomain(domainName, 20);
        test_domain_transfer_debit_card_failure(user, domainName);
    }

    @Ignore
    @Test
    public void test_uc006_xnrpvsdcdfail37() throws SQLException {
        User user = this.direct;
        String domainName = "uc006-xnrpdc.ie";
        db().setDsmStateForDomain(domainName, 21);
        test_domain_transfer_debit_card_failure(user, domainName);
    }

    @Test
    public void test_uc006_xnrprcchy38() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 124);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrprcchy39() throws SQLException {
        User user = this.registrar;
        User losingUser = this.direct;
        String domainName = "uc006-xnrprc.ie";
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 125);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpdcchy40() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 116);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_xnrpdcchy41() throws SQLException {
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-xnrpdc.ie";
        PaymentDetails method = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getNrpTransferRequestEmail(user, losingUser));
        db().setDsmStateForDomain(domainName, 117);
        TransferOutcome outcome = new TransferSuccess(method.getMethod(), paymentPeriod, "XPV");
        test_domain_transfer(user, domainName, method, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_nosc01() throws SQLException {
        // UC#006: Request Domain Billing Transfer - Domain fields validation
        User user = this.registrar;
        String domainName = "uc006-nosc01.ie";
        DomainTransferPage dtp = new DomainTransferPage();
        DomainTransferDetailsPage dtdp = new DomainTransferDetailsPage(false);
        console().login(user);
        dtp.startTransfer(domainName);
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode,
                PredefinedPayments.VALID_CREDIT_CARD, 1);
        dtdp.fillTransferDomainDetails(details);
        DomainTransferUtils.validatePrice(user, details);
        DomainContactsUtils.verifyAll(user, "UC006SU-IEDR", dtdp.contactsForm, dtdp, console());
    }

    @Test
    public void test_uc006_nosc02() throws SQLException {
        // UC#006: Request Domain Billing Transfer - DNS validation
        User user = this.registrar;
        String domainName = "uc006-nosc02.ie";
        DomainTransferPage dtp = new DomainTransferPage();
        DomainTransferDetailsPage dtdp = new DomainTransferDetailsPage(false);
        console().login(user);
        dtp.startTransfer(domainName);
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode,
                PredefinedPayments.VALID_CREDIT_CARD, 1);
        dtdp.fillTransferDomainDetails(details);
        DomainTransferUtils.validatePrice(user, details);
        NameserverUtils.verifyAll(domainName, dtdp.nameserverForm, dtdp, console());
        NameserverUtils.verifyCkdns(dtdp.nameserverForm, console());
    }

    @Test
    public void test_uc006_nosc03() throws SQLException {
        // Request Domain Billing Transfer - Request invalid domain transfer
        User user = this.registrar;
        DomainTransferPage dtp = new DomainTransferPage();
        console().login(user);
        List<String> invalidDomains = new ArrayList<String>();
        invalidDomains.add("uc006-nosc03nexs.ie"); // Non-existent
        invalidDomains.add("uc006-nosc03ones.ie"); // Owned by oneself
        invalidDomains.add("uc006-nosc03pend.ie"); // With pending transfer ticket
        for (String domainName : invalidDomains) {
            dtp.checkTransferDomain(domainName);
            try {
                console()
                        .waitForTextPresentOnPage(
                                domainName
                                        + " is invalid, already registered, or has a pending ticket, or has incorrect state");
            } catch (TimeoutException e) {
                fail("Invalid domain name shouldn't have passed");
            }
        }
    }

    @Test
    public void test_uc006_nosc04() throws SQLException {
        // Request Domain Billing Transfer - Domain with pending modification ticket
        User user = this.registrar;
        User losingUser = this.registrarNonVat;
        String domainName = "uc006-nosc04.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        int ticketId = db().getTicketId(domainName);
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
        assertFalse(db().isTicketExists(ticketId));
    }

    @Test
    public void test_uc006_nosc05() throws SQLException, JSchException, IOException {
        // UC#006: Request Domain Billing Transfer - Don't log CC details
        User user = this.direct;
        User losingUser = this.registrar;
        CardPaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA");
        test_domain_transfer(user, "uc006-nosc05.ie", paymentDetails, paymentPeriod, outcome);
        verifyCardDataInLogs(paymentDetails);
    }

    @Test
    public void test_uc006_nosc06() throws SQLException {
        // UC#006: Request Domain Billing Transfer - IPv6 support
        User user = this.registrar;
        User losingUser = this.registrarNonVat;
        String domainName = "uc006-nosc06.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        String authcode = db().getAuthcodeForDomain(domainName);
        final List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName),
                null, "::ffff:10.10.1.1"), new DomainNameServer(String.format("ns2.%s", domainName), null,
                "::ffff:10.10.1.2"));
        DomainTransferDetails details = new DomainTransferDetails(authcode, new DomainContactFormDetails(user),
                new NameserverFormDetails(dnsList), paymentDetails, paymentPeriod);
        TransferOutcome outcome = new TransferSuccess(paymentDetails.getMethod(), paymentPeriod, "XPA") {
            @Override
            public void after(String domainName) throws SQLException {
                super.after(domainName);
                int ticketId = db().getTicketId(domainName);
                assertEquals(dnsList, db().getDnsListForTicket(ticketId));
            }
        };
        emails.add(getBillingTransferRequestEmail(user, losingUser));

        test_domain_transfer(user, domainName, details, outcome);
    }

    @Test
    public void test_uc006_nosc07() throws SQLException {
        // UC#006: Request Domain Billing Transfer - Creating a Mod file
        String emailAddress = "uc006-nosc07@iedr.ie";
        String domainName = "uc006-nosc07.ie";
        User losingUser = this.registrar;
        int initialModFileId = db().getLastAccountId();
        String nh = NicHandleUtils.createNewAccount("uc006 Direct User", emailAddress, "Passw0rd!");
        int modFileId = db().getLastAccountId();
        assertEquals(modFileId, initialModFileId);
        User newUser = new User(nh, "Passw0rd!", false, emailAddress);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(newUser));
        emails.add(getBillingTransferRequestEmail(newUser, losingUser));
        emails.add(getXferEmail(newUser, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccessWithModFile(paymentDetails.getMethod(), paymentPeriod, "XPA", nh,
                modFileId, true);
        test_domain_transfer(newUser, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_nosc08() throws SQLException {
        // UC#006: Request Domain Billing Transfer - Creating a Mod file
        String emailAddress = "uc006-nosc08@iedr.ie";
        String domainName = "uc006-nosc08.ie";
        User losingUser = this.registrar;
        int initialModFileId = db().getLastAccountId();
        String nh = NicHandleUtils.createNewAccount("uc006 Direct User", emailAddress, "Passw0rd!");
        db().setNicHandleAccountNumber(nh, 600);
        int modFileId = db().getLastAccountId();
        assertEquals(modFileId, initialModFileId);
        User newUser = new User(nh, "Passw0rd!", true, emailAddress);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(newUser));
        emails.add(getBillingTransferRequestEmail(newUser, losingUser));
        emails.add(getXferEmail(newUser, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccessWithModFile(paymentDetails.getMethod(), paymentPeriod, "XPA", nh,
                modFileId, true);
        test_domain_transfer(newUser, domainName, paymentDetails, paymentPeriod, outcome);
    }

    @Test
    public void test_uc006_nosc09() throws SQLException {
        // UC#006: Request Domain Billing Transfer - Direct with Mod file already created
        User user = this.direct;
        User losingUser = this.registrar;
        String domainName = "uc006-nosc09.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        emails.add(getBillingTransferRequestEmail(user, losingUser));
        emails.add(getXferEmail(user, paymentDetails.getMethod()));
        TransferOutcome outcome = new TransferSuccessWithModFile(paymentDetails.getMethod(), paymentPeriod, "XPA",
                user.login, db().getLastAccountId(), false);
        test_domain_transfer(user, domainName, paymentDetails, paymentPeriod, outcome);
    }

    private void test_domain_transfer(User user, String domainName, PaymentDetails method, int paymentPeriod,
            TransferOutcome outcome) throws SQLException {
        String authcode = db().getAuthcodeForDomain(domainName);
        DomainTransferDetails details = new DomainTransferDetails(domainName, user, authcode, method, paymentPeriod);
        test_domain_transfer(user, domainName, details, outcome);
    }

    private void test_domain_transfer(User user, String domainName, DomainTransferDetails details,
            TransferOutcome outcome) throws SQLException {
        console().login(user);
        DomainTransferUtils.transferDomainNoConfirmation(domainName, user, details);
        outcome.after(domainName);
        checkAndResetEmails(emails);
    }

    private interface TransferOutcome {
        public void after(String domainName) throws SQLException;
    }

    private class TransferSuccess implements TransferOutcome {

        PaymentMethod method;
        int paymentPeriod;
        String nrpStatus;

        public TransferSuccess(PaymentMethod method, int paymentPeriod, String nrpStatus) {
            super();
            this.method = method;
            this.paymentPeriod = paymentPeriod;
            this.nrpStatus = nrpStatus;
        }

        public void after(String domainName) throws SQLException {
            String message = console().getNewTicketMessage(domainName);
            console().waitForTextPresentOnPage(message, 60);
            int ticketId = db().getTicketId(domainName);
            if (method == PaymentMethod.CARD) {
                int reservationId = db().getReservationId(domainName);
                assertEquals(12 * paymentPeriod, db().getReservationDuration(reservationId));
                assertFalse(db().reservationReadyForSettlement(reservationId));
                assertEquals(ticketId, db().getReservationTicketId(reservationId));
            }
            // Check domain's NRP status.
            assertEquals(nrpStatus, db().getNrpStatusForDomain(domainName));
        }
    }

    private class TransferSuccessWithModFile extends TransferSuccess {
        private String nicHandle;
        private int initialModFileId;
        private boolean modFile;

        public TransferSuccessWithModFile(PaymentMethod method, int paymentPeriod, String nrpStatus, String nicHandle,
                int initialModFileId, boolean modFile) {
            super(method, paymentPeriod, nrpStatus);
            this.nicHandle = nicHandle;
            this.initialModFileId = initialModFileId;
            this.modFile = modFile;
        }

        @Override
        public void after(String domainName) throws SQLException {
            super.after(domainName);
            // Check Mod file has been created
            OutputFiles files = new OutputFiles(ssh().crsws);
            try {
                if (modFile) {
                    // Verifying that Xml file has been created
                    files.checkAccountXml(initialModFileId, nicHandle);
                } else {
                    // Verifying that Xml file has not been created
                    assertEquals(initialModFileId, db().getLastAccountId());
                }
            } catch (JSchException e) {
                fail();
            } catch (IOException e) {
                fail();
            }
        }
    }

    private void test_domain_transfer_debit_card_failure(User user, String domainName) {
        console().login(user);
        DomainTransferPage dtp = new DomainTransferPage();
        DomainTransferDetailsPage dtdp = new DomainTransferDetailsPage(false);
        dtp.startTransfer(domainName);
        dtdp.paymentForm.selectPaymentType(PaymentMethod.CARD);
        // Verify that debit card method cannot be selected.
        console().assertElementNotPresent(By.xpath(CardTypeXPath.DEBIT_VISA));
        console().assertElementPresent(By.xpath(CardTypeXPath.VISA));
    }

    private ExpectedEmailSummary getBillingTransferRequestEmail(User gainingUser, User losingUser) throws SQLException {
        User adminC = this.adminContact;
        return emailSummaryGenerator.getBillingTransferRequestEmail(gainingUser, losingUser, adminC);
    }

    private ExpectedEmailSummary getNrpTransferRequestEmail(User gainingUser, User losingUser) throws SQLException {
        User adminC = this.adminContact;
        return emailSummaryGenerator.getNrpTransferRequestEmail(gainingUser, losingUser, adminC);
    }

    private ExpectedEmailSummary getXferEmail(User gainingUser, PaymentMethod method) throws SQLException {
        return emailSummaryGenerator.getBillingTransferPaymentEmail(gainingUser, method);
    }
}
