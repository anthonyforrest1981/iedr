package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.*;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UC017 extends SeleniumTest {

    public UC017(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc017_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc017_data.sql";
    }

    @Test
    public void test_uc017_sc02alt1() throws SQLException {
        // UC017-SC02 (Alt): Registrar successfully changes nameserver
        // configuration from "View Domain"
        User user = this.registrar;
        String domainName = "uc017-sc02alt1.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillNameserverDetails(new NameserverFormDetails(Arrays.asList(new DomainNameServer(
                "ns1.dns.ie"), new DomainNameServer("ns2.dns.ie"), new DomainNameServer("ns3.dns.ie"))));
        vdp.remarksField.fill("dns change");
        vdp.submitAndWaitForSuccess(domainName, false);

        emails.add(emailSummaryGenerator.getDnsModificationEmail(user, user, user, user, false, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc017_sc02alt2() {
        // UC017-SC02 (Alt): Registrar successfully changes nameserver
        // configuration from "DNS" section
        User user = this.registrar;
        test_domains_dns_modification(user, "uc017-sc02alt2.ie");
    }

    @Test
    public void test_uc017_sc05() throws SQLException, JSchException, IOException {
        List<String> nonAuthoritativeDomains = Arrays.asList("uc017-sc05.ie");
        String initialCkDnsScript = ssh().crsws.spoofCkDnsScript(nonAuthoritativeDomains);
        try {
            _test_uc017_sc05();
        } finally {
            ssh().crsws.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    private void _test_uc017_sc05() {
        User user = this.direct;
        String domainName = "uc017-sc05.ie";
        // Valid DNS server but not configured for this domain.
        List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer("ns1.dns.ie"), new DomainNameServer(
                "ns2.dns.ie"), new DomainNameServer("ns3.dns.ie"));
        String dnsNames = StringUtils.join(Lists.transform(dnsList, new Function<DomainNameServer, String>() {
            public String apply(DomainNameServer dns) {
                return dns.name;
            }
        }), ", ");
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillNameserverDetails(new NameserverFormDetails(dnsList));
        vdp.remarksField.fill("dns change");
        vdp.submit();
        console().waitForTextPresentOnPage(
                String.format("Change domain details failed. Error. Hosts: %s not configured", dnsNames));
    }

    @Test
    public void test_uc017_sc06() throws SQLException {
        User user = this.direct;
        String domainName = "uc017-sc06.ie";

        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.contactsForm.admin1Field.fill("UC017AB-IEDR");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
    }

    @Test
    public void test_uc017_sc07() throws SQLException {
        User user = this.registrar;
        String domainName = "uc017-sc07.ie";

        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.renewalModeField.fill("NoAutorenew");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, false);

        assertEquals("N", db().getRenewalModeForDomain(domainName));
    }

    @Test
    public void test_uc017_sc09() throws SQLException {
        User user = this.registrar;
        String domainName = "uc017-sc09.ie";

        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.holderField.fill("Modified Holder");
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, true);
    }

    @Test
    public void test_uc017_q11722() throws SQLException {
        User user = this.registrar;
        String domainName = "uc017-q11722.ie";

        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.renewalModeField.fill("Autorenew");
        vdp.remarksField.fill("Remark");
        vdp.submit();
        console().waitForTextPresentOnPage(
                "Change domain details failed. Error. Domain is not billable: " + domainName + ", status is Charity");
    }

    @Test
    public void test_uc017_q11726() {
        User user = this.registrar;
        test_domain_modification_not_allowed(user, "uc017-q11726.ie");
    }

    @Test
    public void test_uc017_q11728() {
        User user = this.registrar;
        test_domain_modification_allowed(user, "uc017-q11728.ie");
    }

    @Test
    public void test_uc017_q117212() {
        User user = this.direct;
        test_domain_modification_not_allowed(user, "uc017-q117212.ie");
    }

    @Test
    public void test_uc017_q117214() {
        User user = this.direct;
        test_domains_dns_modification(user, "uc017-q117214.ie");
    }

    @Test
    public void test_uc017_q117214alt() throws SQLException {
        User user = this.direct;
        String domainName = "uc017-q117214alt.ie";

        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillNameserverDetails(new NameserverFormDetails(Arrays.asList(new DomainNameServer(
                "ns1.dns.ie"), new DomainNameServer("ns2.dns.ie"), new DomainNameServer("ns3.dns.ie"))));
        vdp.remarksField.fill("dns change");
        vdp.submitAndWaitForSuccess(domainName, false);

        emails.add(emailSummaryGenerator.getDnsModificationEmail(user, user, user, user, false, domainName));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc017_q117215() {
        User user = this.direct;
        test_domain_modification_not_allowed(user, "uc017-q117215.ie");
    }

    @Test
    public void test_uc017_uc001ns() throws SQLException {
        // UC001-NS: Submit Domain modification when domain in Pending Payment
        // state
        User user = this.direct;
        String domainName = "uc017-uc001ns.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;

        // According to CRS-102 user should be able to modify domain in iNRP.
        test_domain_modification_allowed(user, domainName);
        // Initiate a payment for domain.
        payForNrpDomain(user, domainName, domainName, paymentDetails, 1);
        // Domain state should move to IMPP (pending payment).
        assertEquals("IMPP", db().getNrpStatusForDomain(domainName));
        // User should be immediately able to modify data.
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.remarksField.fill("Remark");
        vdp.submitAndWaitForSuccess(domainName, false);

        emails.add(emailSummaryGenerator.getRenewalsPaymentEmail(user, paymentDetails.getMethod()));
        emails.add(emailSummaryGenerator.getNrpPaidReactivationEmail(user, domainName, true, paymentDetails.getMethod()));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc017_nosc01() {
        // UC#017: Request Domain Modification - Domain fields validation
        User user = this.registrar;
        String domainName = "uc017-nosc01.ie";
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        ViewDomainPage vdp = new ViewDomainPage();
        vdp.initModification();
        vdp.holderField.fill("");
        vdp.submit();
        try {
            console().waitForTextPresentOnPage("Holder cannot be blank.");
        } catch (TimeoutException e) {
            fail("Empty holder name shouldn't have passed.");
        }
        DomainContactsUtils.verifyAll(user, "UC017SU-IEDR", vdp.contactsForm, vdp, console());
    }

    private void payForNrpDomain(User user, String domainName, String domainPrefix, PaymentDetails paymentDetails,
            int paymentPeriod) throws SQLException {
        List<Domain> domains = Arrays.asList(new Domain(domainName, paymentPeriod));
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, true, 0);
        pfd.payForDomainsSuccess(user, domainPrefix, paymentDetails);
        new WebDriverWait(wd(), 10).until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//input[contains(@value, 'Print This Page')]")));
    }

    private void test_domains_dns_modification(User user, String domainName) {
        console().login(user);
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        ddmp.modifyDns(new NameserverFormDetails(Arrays.asList(new DomainNameServer("ns1.dns.ie"),
                new DomainNameServer("ns2.dns.ie"))));
    }

    private void test_domain_modification_not_allowed(User user, String domainName) {
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        console().assertElementPresent(By.id("ViewDomainForm"));
        console().assertElementNotPresent(By.xpath("//input[@id='mod']"));
    }

    private void test_domain_modification_allowed(User user, String domainName) {
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        console().assertElementPresent(By.id("ViewDomainForm"));
        console().assertElementPresent(By.xpath("//input[@id='mod']"));
    }

}
