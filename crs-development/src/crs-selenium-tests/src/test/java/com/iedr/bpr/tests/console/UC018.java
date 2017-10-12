package com.iedr.bpr.tests.console;

import java.sql.SQLException;

import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.console.DomainDnsModificationPage;
import com.iedr.bpr.tests.pages.console.DomainDnsPage;
import com.iedr.bpr.tests.pages.console.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.NameserverUtils;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class UC018 extends SeleniumTest {

    public UC018(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc018_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc018_data.sql";
    }

    @Test
    public void test_uc018_nosc01_autorenew() {
        // UC#018: Modify Domain - UC018: Modify Domain in Involuntary NRP
        test_uc018_nosc01("Autorenew", true);
    }

    @Test
    public void test_uc018_nosc01_renewonce() {
        // UC#018: Modify Domain - UC018: Modify Domain in Involuntary NRP
        test_uc018_nosc01("RenewOnce", false);
    }

    private void test_uc018_nosc01(String renewalMode, boolean changeAllowed) {
        User user = this.registrar;
        String domainName = "uc018-nosc01.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.renewalModeField.fill(renewalMode);
        if (changeAllowed) {
            vdp.submitAndWaitForSuccess(domainName, false);
        } else {
            vdp.submit();
            console().waitForTextPresentOnPage("Change domain details failed. Error. Current domain state prohibits " +
                    "renewal mode modification for a domain: uc018-nosc01.ie");
        }
    }

    @Test
    public void test_uc018_nosc02() {
        // UC#018: Modify Domain - UC018: Modify Domain - Edit DNS
        User user = this.registrar;
        String domainName = "uc018-nosc02.ie";
        DomainNameServer dns = new DomainNameServer("ns1.dns.ie", null, null);
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillRow(0, dns);
        vdp.submitAndWaitForSuccess(domainName, false);
    }

    @Test
    public void test_uc018_nosc03() {
        // UC#018: Modify Domain - UC018: Modify Domain via Console - DNS validation
        User user = this.registrar;
        String domainName = "uc018-nosc03.ie";
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        NameserverUtils.verifyAll(domainName, vdp.nameserverForm, vdp, console());
        NameserverUtils.verifyCkdns(vdp.nameserverForm, console());
    }

    @Test
    public void test_uc018_nosc04() {
        // UC#018: Modify Domain - UC018: Modify Domain via Console - Edit DNS - DNS validation
        User user = this.registrar;
        String domainName = "uc018-nosc04.ie";
        console().login(user);
        DomainDnsPage ddp = new DomainDnsPage();
        ddp.initDnsModification(domainName);
        DomainDnsModificationPage ddmp = new DomainDnsModificationPage();
        NameserverUtils.verifyNoIpScenarios(ddmp.nameserverForm, ddmp, console());
    }

    @Test
    public void test_uc018_nosc05() throws SQLException {
        // UC#018: Modify Domain - UC018: Modify Domain via Console - IPv6 support
        User user = this.registrar;
        String domainName = "uc018-nosc05.ie";
        DomainNameServer dns = new DomainNameServer(String.format("ns1.%s", domainName), null, "::ffff:10.10.1.1");
        console().login(user);
        ViewDomainPage vdp = new ViewDomainPage();
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        vdp.initModification();
        vdp.nameserverForm.fillRow(0, dns);
        vdp.submitAndWaitForSuccess(domainName, false);
        assertEquals(dns, db().getDnsListForDomain(domainName).get(0));
    }

}
