package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.console.*;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.pages.console.DomainRegistrationDetailsPage;
import com.iedr.bpr.tests.pages.console.DomainRegistrationPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.NameserverUtils;
import com.iedr.bpr.tests.utils.OutputFiles;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
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

public class UC001 extends SeleniumTest {

    public UC001(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc001_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc001_data.sql";
    }

    public ExpectedEmailSummary getRegistrationEmail(User user, String domainName) throws SQLException {
        return emailSummaryGenerator.getRegistrationReceivedEmail(user, user, domainName);
    }

    @Test
    public void test_uc001_sc01() throws SQLException {
        User user = this.registrar;
        String domainName = "uc001-sc01.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check ticket.
                db().getTicketId(domainName);
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_sc02() throws SQLException {
        User user = new User("UC001AA-IEDR", "Passw0rd!", false, "uc001_aa@iedr.ie");
        String domainName = "uc001-sc02.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check CC transaction, reservation and ticket.
                int reservationId = db().getReservationId(domainName);
                assertEquals(12, db().getReservationDuration(reservationId));
                assertFalse(db().reservationReadyForSettlement(reservationId));
                int ticketId = db().getTicketId(domainName);
                assertEquals(ticketId, db().getReservationTicketId(reservationId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_sc03() throws SQLException {
        User user = this.registrar;
        String domainName = "uc001-sc03.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check CC transaction, reservation and ticket.
                int reservationId = db().getReservationId(domainName);
                assertEquals(12, db().getReservationDuration(reservationId));
                assertFalse(db().reservationReadyForSettlement(reservationId));
                int ticketId = db().getTicketId(domainName);
                assertEquals(ticketId, db().getReservationTicketId(reservationId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_sc07() throws SQLException {
        User user = this.direct;
        String domainName = "uc001-sc07.ie";
        final CharityPaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check ticket.
                int ticketId = db().getTicketId(domainName);
                assertEquals(paymentDetails.getAuthCode(), db().getTicketChyCode(ticketId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_qa1121() throws SQLException {
        User user = this.registrar;
        String domainName = "uc001-qa1121.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check ticket.
                db().getTicketId(domainName);
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_qa1122() throws SQLException {
        User user = this.registrarNonVat;
        String domainName = "uc001-qa1122.ie";
        final PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check ticket.
                db().getTicketId(domainName);
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_qa1123() throws SQLException {
        User user = this.registrar;
        String domainName = "uc001-qa1123.ie";
        final CharityPaymentDetails paymentDetails = new CharityPaymentDetails("123");
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check ticket.
                int ticketId = db().getTicketId(domainName);
                assertEquals(paymentDetails.getAuthCode(), db().getTicketChyCode(ticketId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_qa1124_direct() throws SQLException {
        User user = this.direct;
        _test_uc001_qa1124(user, "uc001-qa1124direct.ie");
    }

    @Test
    public void test_uc001_qa1124_registrar() throws SQLException {
        User user = this.registrar;
        _test_uc001_qa1124(user, "uc001-qa1124registrar.ie");
    }

    private void _test_uc001_qa1124(User user, String domainName) throws SQLException {
        console().login(user);

        NameserverFormDetails dnsDetails = new NameserverFormDetails(domainName);
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsDetails,
                PredefinedPayments.INVALID_CREDIT_CARD, 1, false);
        DomainRegistrationUtils.registerDomainNoConfirmation(domainName, user, details);

        // Wait for page to load.
        console().waitForTextPresentOnPage("Card declined.");
        // Go back to form.
        console().clickElement(By.linkText("Click here to try again/another card."));
        verifyForm("Test Holder 0001", "Company", true, "Test remark", user.login, user.login, dnsDetails.getDnsList());
        // Check that ticket is not created.
        assertEquals(0, db().getTicketsCountForDomain(domainName));
    }

    @Test
    @IgnoredBrowsers({Browser.Edge})
    public void test_uc001_nosc01() throws NumberFormatException, SQLException {
        // Registrar registers domain with 7 nameservers
        User user = this.registrar;
        String domainName = "uc001-nosc01.ie";
        console().login(user);
        DomainRegistrationPage drp = new DomainRegistrationPage();
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName);
        details.setPaymentDetails(PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        drp.startRegistration(domainName);
        drdp.fillDomainRegistrationDetailsAndValidate(details);
        NameserverUtils.testNameserversCount(true, drdp.nameserverForm, console());
        drdp.submitAndWaitForSuccess(domainName);
    }

    @Test
    public void test_uc001_nosc02() throws SQLException {
        // Multiple domain registration
        User user = this.registrar;
        String domainNameA = "uc001-nosc02-a.ie";
        String domainNameB = "uc001-nosc02-b.ie";
        console().login(user);
        DomainNameServer dns1 = new DomainNameServer(String.format("ns.%s", domainNameA), "10.10.1.1", null);
        DomainNameServer dns2 = new DomainNameServer("ns.dns.ie", null, null);
        List<DomainNameServer> dnsList = Arrays.asList(dns1, dns2);
        String domainNames = String.format("%s,%s", domainNameA, domainNameB);
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList,
                PredefinedPayments.DEPOSIT_PAYMENT_DETAILS, 1, false);
        DomainRegistrationUtils.registerDomainNoConfirmation(domainNames, user, details);
        console().waitForTextPresentOnPage(console().getNewTicketMessage(domainNameA));
        console().waitForTextPresentOnPage(console().getNewTicketMessage(domainNameB));
        int ticketA = db().getTicketId(domainNameA);
        int ticketB = db().getTicketId(domainNameB);
        List<DomainNameServer> ticketADns = db().getDnsListForTicket(ticketA);
        List<DomainNameServer> ticketBDns = db().getDnsListForTicket(ticketB);
        assertEquals(dns1, ticketADns.get(0));
        assertEquals(dns2, ticketADns.get(1));
        assertEquals(new DomainNameServer(dns1.name, null, null), ticketBDns.get(0));
        assertEquals(dns2, ticketBDns.get(1));
    }

    @Test
    public void test_uc001_nosc03() throws SQLException {
        // Registrar credit-card registration with badly formatted credit card number
        User user = this.registrar;
        String domainName = "uc001-nosc03.ie";
        final CardPaymentDetails validCreditCard = PredefinedPayments.VALID_CREDIT_CARD;
        // insert string at the end and in the middle of the card
        String cardNumber = validCreditCard.getCardNumber() + " ";
        cardNumber = cardNumber.substring(0, 4) + " " + cardNumber.substring(4);
        final PaymentDetails paymentDetails = new CardPaymentDetails(PaymentMethod.CARD,
                validCreditCard.getCardHolder(), cardNumber, validCreditCard.getExpirationDate(),
                validCreditCard.getAuthCode());
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check CC transaction, reservation and ticket.
                int reservationId = db().getReservationId(domainName);
                assertEquals(12, db().getReservationDuration(reservationId));
                assertFalse(db().reservationReadyForSettlement(reservationId));
                int ticketId = db().getTicketId(domainName);
                assertEquals(ticketId, db().getReservationTicketId(reservationId));
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_nosc04() throws SQLException {
        // UC#001: Request Domain Registration - Domain fields validation
        User user = this.registrar;
        String domainName = "uc001-nosc04.ie";
        DomainRegistrationPage drp = new DomainRegistrationPage();
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        console().login(user);
        drp.startRegistration(domainName);
        drdp.fillDomainRegistrationDetailsAndValidate(new DomainRegistrationDetails(user, domainName));
        DomainContactsUtils.verifyAll(user, "UC001SU-IEDR", drdp.contactsForm, drdp, console());
        DomainRegistrationUtils.verifyEmptyHolder(user, domainName);
    }

    @Test
    public void test_uc001_nosc05() throws SQLException {
        // UC#001: Request Domain Registration - DNS validation
        User user = this.registrar;
        String domainName = "uc001-nosc05.ie";

        DomainRegistrationPage drp = new DomainRegistrationPage();
        DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName);
        details.setPaymentDetails(PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);

        console().login(user);
        drp.startRegistration(domainName);
        drdp.fillDomainRegistrationDetailsAndValidate(details);
        NameserverUtils.verifyAll(domainName, drdp.nameserverForm, drdp, console());
        NameserverUtils.verifyCkdns(drdp.nameserverForm, console());
    }

    @Test
    public void test_uc001_nosc06() throws SQLException {
        // UC#001: Request Domain Registration - Don't log CC details
        User user = this.registrar;
        String domainName = "uc001-nosc06.ie";
        final CardPaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        DomainRegistration registration = new DomainRegistration() {
            public void after(String domainName) throws SQLException {
                // Check CC details are not logged
                try {
                    verifyCardDataInLogs(paymentDetails);
                } catch (Exception e) {
                    fail();
                }
            }
        };
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        test_domain_registration_success(user, domainName, paymentDetails, paymentPeriod, registration);
    }

    @Test
    public void test_uc001_nosc07() throws SQLException {
        // UC#001: Request Domain Registration - IPv6 support
        User user = this.registrar;
        String domainName = "uc001-nosc07.ie";
        List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName), null,
                "::ffff:10.10.1.1"),
                new DomainNameServer(String.format("ns2.%s", domainName), null, "::ffff:10.10.1.2"));
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList, paymentDetails,
                paymentPeriod, true);
        emails.add(getRegistrationEmail(user, domainName));

        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
        int ticketId = db().getTicketId(domainName);
        assertEquals(dnsList, db().getDnsListForTicket(ticketId));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc001_nosc08() throws SQLException {
        // UC#001: Request Domain Registration - Domain with two Admin Contacts
        User user = this.registrar;
        String domainName = "uc001-nosc08.ie";
        User adminC1 = new User("UC001AB-IEDR", "Passw0rd!", true, "uc001_ab@iedr.ie");
        User adminC2 = new User("UC001AC-IEDR", "Passw0rd!", true, "uc001_ac@iedr.ie");
        console().login(user);

        DomainContactFormDetails contactDetails = new DomainContactFormDetails(adminC1.login, adminC2.login, user.login);
        DomainRegistrationDetails details = new DomainRegistrationDetails(contactDetails,
                new NameserverFormDetails(domainName), PredefinedPayments.DEPOSIT_PAYMENT_DETAILS, 1, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
        emails.add(emailSummaryGenerator.getRegistrationReceivedEmail(user, adminC1, adminC2, domainName));
        checkAndResetEmails(emails);
    }

    private class DomainRegistrationWithModFile implements DomainRegistration {
        private String nicHandle;
        private int initialModFileId;
        private boolean modFile;

        public DomainRegistrationWithModFile(String nicHandle, int initialModFileId, boolean modFile) {
            this.nicHandle = nicHandle;
            this.initialModFileId = initialModFileId;
            this.modFile = modFile;
        }

        @Override
        public void after(String domainName) throws SQLException {
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

    @Test
    public void test_uc001_nosc09() throws SQLException {
        // UC#001: Request Domain Registration - Creating a Mod file
        String emailAddress = "uc001-nosc09@iedr.ie";
        String domainName = "uc001-nosc09.ie";
        int initialModFileId = db().getLastAccountId();
        String nh = NicHandleUtils.createNewAccount("uc001 Direct User", emailAddress, "Passw0rd!");
        int modFileId = db().getLastAccountId();
        assertEquals(modFileId, initialModFileId);
        User newUser = new User(nh, "Passw0rd!", false, emailAddress);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        DomainRegistrationWithModFile registration = new DomainRegistrationWithModFile(nh, initialModFileId, true);
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(newUser));
        emails.add(getRegistrationEmail(newUser, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(newUser, paymentDetails.getMethod(), domainName));
        console().login(newUser);
        test_domain_registration_success(newUser, domainName, paymentDetails, 1, registration);
    }

    @Test
    public void test_uc001_nosc10() throws SQLException {
        // UC#001: Request Domain Registration - Creating a Mod file by a Direct with an account number different than 1
        String emailAddress = "uc001-nosc10@iedr.ie";
        String domainName = "uc001-nosc10.ie";
        int initialModFileId = db().getLastAccountId();
        String nh = NicHandleUtils.createNewAccount("uc001 Direct User", emailAddress, "Passw0rd!");
        db().setNicHandleAccountNumber(nh, 600);
        int modFileId = db().getLastAccountId();
        assertEquals(modFileId, initialModFileId);
        User newUser = new User(nh, "Passw0rd!", true, emailAddress);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        DomainRegistrationWithModFile registration = new DomainRegistrationWithModFile(nh, initialModFileId, true);
        emails.add(emailSummaryGenerator.getNhRegistrationEmail(newUser));
        emails.add(getRegistrationEmail(newUser, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(newUser, paymentDetails.getMethod(), domainName));
        console().login(newUser);
        test_domain_registration_success(newUser, domainName, paymentDetails, 1, registration);
    }

    @Test
    public void test_uc001_nosc11() throws SQLException {
        //UC#001: Request Domain Registration - Direct with Mod file already created
        User user = direct;
        String domainName = "uc001-nosc10.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        DomainRegistrationWithModFile registration = new DomainRegistrationWithModFile(user.login, db()
                .getLastAccountId(), false);
        emails.add(getRegistrationEmail(user, domainName));
        emails.add(emailSummaryGenerator.getRegistrationPaymentEmail(user, paymentDetails.getMethod(), domainName));
        test_domain_registration_success(user, domainName, paymentDetails, 1, registration);
    }

    @Test
    public void test_uc001_nosc12() throws SQLException {
        // UC#001: Request Domain Registration - DNS Length checks
        User user = this.registrar;
        String domainName = "uc001-nosc12.ie";
        List<DomainNameServer> dnsList = Arrays.asList(
                new DomainNameServer("63-character-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie", null, null),
                new DomainNameServer("63-kharacter-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie.", null, null));
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        int paymentPeriod = 1;
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList, paymentDetails,
                paymentPeriod, true);
        emails.add(getRegistrationEmail(user, domainName));

        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
        int ticketId = db().getTicketId(domainName);
        List<DomainNameServer> dbDnsListForTicket = db().getDnsListForTicket(ticketId);
        assertEquals(dnsList, dbDnsListForTicket);
        checkAndResetEmails(emails);
    }

    private void verifyForm(String domainHolder, String ownerType, boolean isBasedInIreland, String remarks,
            String adminNh, String techNh, List<DomainNameServer> dnsList) {
        verifyInput("Domains_Creation_Details_holder", domainHolder);
        verifySelectText("Domains_Creation_Details_ownerType", ownerType);
        verifyCheckbox("Domains_Creation_Details_isOwnerFromIreland", isBasedInIreland);
        verifyInput("Domains_Creation_Details_remarks", remarks);
        verifyInput("Domains_Creation_Details_admin_contact_nic_1", adminNh);
        verifyInput("Domains_Creation_Details_tech_contact", techNh);
        for (int i = 0; i < dnsList.size(); i++) {
            DomainNameServer dns = dnsList.get(i);
            verifyInput(String.format("ns_%s", i), dns.name);
            verifyInput(String.format("ipv4_%s", i), dns.ipv4);
            verifyInput(String.format("ipv6_%s", i), dns.ipv6);
        }
        assertTrue(wd().findElement(By.id("Domains_Creation_Details_accept_tnc")).isSelected());
    }

    private void verifyInput(String inputId, String value) {
        String inputValue = wd().findElement(By.id(inputId)).getAttribute("value");
        inputValue = "".equals(inputValue) ? null : inputValue;
        assertEquals(value, inputValue);
    }

    private void verifySelectText(String selectId, String value) {
        WebElement option = wd().findElement(
                By.xpath(String.format("//select[@id='%s']/option[.='%s']", selectId, value)));
        assertTrue(option.isSelected());
    }

    private void verifyCheckbox(String checkboxId, boolean checked) {
        WebElement checkbox = wd().findElement(By.id(checkboxId));
        if (checked) {
            assertTrue(checkbox.isSelected());
        } else {
            assertFalse(checkbox.isSelected());
        }
    }

    @Test
    public void test_uc001_qa1126_to_11210() throws SQLException {
        User user = this.direct;
        _test_uc001_qa1126_to_11210(user);
    }

    public void _test_uc001_qa1126_to_11210(User user) throws SQLException {
        console().login(user);

        final PaymentDetails paymentDetails = new CharityPaymentDetails("123");
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, "uc001-qa11210b.ie", paymentDetails, 1,
                true);
        DomainRegistrationUtils.registerDomain("uc001-qa11210b.ie", user, details);

        DomainRegistrationPage drp = new DomainRegistrationPage();
        List<String> existingNames = new ArrayList<String>();
        existingNames.add("uc001-qa11210b.ie"); // Ticket pending
        existingNames.add("uc001-qa11210a.ie"); // Already exists
        Map<String, String> invalidNames = new HashMap<String, String>();
        invalidNames.put("some^domain.ie", "some^domain.ie is not a valid domain address"); // Incorrect char
        invalidNames.put("somedomain-ie", "somedomain-ie is not a valid domain address"); // No dot
        invalidNames.put("some.domain.ie", "some.domain.ie is not a valid IEDR domain address"); // More than one dot
        invalidNames.put("somedomain.ir", "somedomain.ir is not a valid IEDR domain address"); // Suffix different than ".ie"
        invalidNames.put("aa.ie", "Two-letter domain addresses like aa.ie are not allowed"); // Too short name
        invalidNames.put("-somedomain.ie", "-somedomain.ie is not a valid domain address"); // Hyphen at the beginning
        invalidNames.put("somedomain-.ie", "somedomain-.ie is not a valid domain address"); // Hyphen at the end
        // Domain can have 66 characters including ".ie"
        char[] charArray = new char[64]; // This should fail (64+3>66)
        Arrays.fill(charArray, 'a');
        final String tooLongDomainName = new String(charArray) + ".ie";
        invalidNames.put(tooLongDomainName, tooLongDomainName + " is not a valid domain address");
        for (String domainName : existingNames) {
            drp.checkNewDomainName(domainName);
            String message = domainName + " is already registered or has a pending ticket";
            try {
                console().waitForTextPresentOnPage(message);
            } catch (TimeoutException e) {
                fail("Domain \"" + domainName + "\" should have been rejected.");
            }
        }
        for (String domainName : invalidNames.keySet()) {
            final String errorMessage = invalidNames.get(domainName);
            drp.checkNewDomainName(domainName);
            try {
                console().waitForTextPresentOnPage(errorMessage);
            } catch (TimeoutException e) {
                fail("Domain with name \"" + domainName + "\" shouldn't have passed.");
            }
        }
    }

    private interface DomainRegistration {
        public void after(String domainName) throws SQLException;
    }

    private void test_domain_registration_success(User user, String domainName, PaymentDetails paymentDetails,
            int paymentPeriod, DomainRegistration registration) throws SQLException {
        console().login(user);

        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails,
                paymentPeriod, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);

        // Check conditions after the registration.
        registration.after(domainName);
        checkAndResetEmails(emails);
    }

}
