package com.iedr.bpr.tests.console;

import java.io.IOException;
import java.util.*;

import org.junit.Test;
import org.openqa.selenium.By;

import com.google.common.base.Joiner;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.DomainRegistrationDetailsPage;
import com.iedr.bpr.tests.pages.console.DomainRegistrationPage;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.ssh;

public class UC047 extends SeleniumTest {

    public UC047(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc047_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc047_data.sql";
    }

    @Test
    public void test_uc047_nosc01() throws JSchException, IOException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check for domain with
        // 7 nameservers - Console
        String domainName = "uc047-nosc01.ie";
        DomainNameServer dns = new DomainNameServer("ns1.dns.ie", null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, dns.name);
        String initialCkDnsScript = ssh().crsws.spoofCkDnsScript(map);
        try {
            modifyAndCheckDns(domainName, dns);
        } finally {
            ssh().crsws.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    @Test
    public void test_uc047_nosc02() throws JSchException, IOException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check - Console - IPv6 support
        String domainName = "uc047-nosc02.ie";
        DomainNameServer dns = new DomainNameServer(String.format("ns1.%s", domainName), null, "::ffff:10.10.1.3");
        Map<String, String> map = new HashMap<String, String>();
        map.put(domainName, dns.ipv6);
        String initialCkDnsScript = ssh().crsws.spoofCkDnsScript(map);
        try {
            modifyAndCheckDns(domainName, dns);
        } finally {
            ssh().crsws.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    @Test
    public void test_uc047_nosc03() throws JSchException, IOException {
        // UC#047: Perform DNS Check - UC#047: Perform DNS Check - Console - Not responding DNS
        // 3 domains registered, each with 7 nameservers
        List<String> domainList = Arrays.asList("uc047-nosc03a.ie", "uc047-nosc03b.ie", "uc047-nosc03c.ie");
        List<DomainNameServer> dnsList = new ArrayList<DomainNameServer>();
        for (int i = 0; i < 7; i++) {
            String dnsName = String.format("ns%s.dns.ie", i);
            DomainNameServer dns = new DomainNameServer(dnsName);
            dnsList.add(dns);
        }
        // Script that always waits 20 seconds
        String initialCkDnsScript = ssh().crsws.spoofCkDnsScriptToSleep(20);
        try {
            User user = this.registrar;
            String domainNames = Joiner.on(",").join(domainList);
            console().login(user);
            DomainRegistrationPage drp = new DomainRegistrationPage();
            drp.startRegistration(domainNames);
            DomainRegistrationDetails details = new DomainRegistrationDetails(user, dnsList,
                    PredefinedPayments.DEPOSIT_PAYMENT_DETAILS, 1, false);
            DomainRegistrationDetailsPage drdp = new DomainRegistrationDetailsPage();
            drdp.fillDomainRegistrationDetailsAndValidate(details);
            runDnsCheck();
            // The time of execution is independent from number of domains and nameservers, so the whole operation
            // should be done within 20 seconds + the time required to handle the request. 30s timeout should be enough
            // for that and is sufficiently small to make sure, that dns check is not done sequentially.
            console().waitForTextPresentOnPage("DNS Check passed", 30);
        } finally {
            ssh().crsws.restoreCkdnsScript(initialCkDnsScript);
        }
    }

    private void modifyAndCheckDns(String domainName, DomainNameServer dns) {
        User user = this.registrar;
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        runDnsCheck();
        console().waitForTextPresentOnPage("DNS Check passed");
        vdp.nameserverForm.fillRow(0, dns);
        runDnsCheck();
        console().waitForTextPresentOnPage("DNS Check failed");
    }

    private void runDnsCheck() {
        console().clickElement(By.id("ns_ver_buttton"));
    }

}
