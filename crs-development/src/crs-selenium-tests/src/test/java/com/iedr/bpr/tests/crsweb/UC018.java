package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.formdetails.crsweb.DomainModificationDetails;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.NameserverUtils;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainContactsUtils;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class UC018 extends SeleniumTest {

    public UC018(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc018_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc018_data.sql";
    }

    @Test
    public void test_uc018_nosc01() {
        // UC#018: Modify Domain - UC018: Modify Domain
        User user = this.internal;
        String domainName = "uc018-nosc01.ie";
        String newHolder = "New Holder";
        String newClass = "Sole Trader";
        String newCategory = "Discretionary Name";
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(user);
        vdp.edit(domainName);
        DomainModificationDetails details = new DomainModificationDetails(this.adminContact, domainName, newHolder,
                newClass, newCategory, "Remark");
        vdp.fillModificationDetails(details);
        checkFieldValue("Domain Holder", newHolder);
        checkFieldValue("Class", newClass);
        checkFieldValue("Category", newCategory);
        checkFieldValue("Admin Contact 1", this.adminContact.login);
        checkFieldValue("Tech Contact", this.adminContact.login);
    }

    @Test
    public void test_uc018_nosc02() {
        // UC#018: Modify Domain - UC018: Modify Domain via CRS-WEB - Domain fields validation
        User user = this.internal;
        String domainName = "uc018-nosc02.ie";
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(user);
        vdp.edit(domainName);
        DomainContactsUtils.verifyAll(this.adminContact, "UC018SU-IEDR", vdp.contactsForm, vdp, crsweb());
    }

    @Test
    public void test_uc018_nosc03() {
        // UC#018: Modify Domain - UC018: Modify Domain via CRS-WEB - DNS validation
        User user = this.internal;
        String domainName = "uc018-nosc03.ie";
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(user);
        vdp.edit(domainName);
        NameserverUtils.verifyAll(domainName, vdp.nameserverForm, vdp, crsweb());
    }

    @Test
    public void test_uc018_nosc04() throws SQLException {
        // UC#018: Modify Domain - UC018: Modify Domain via CRS-WEB - IPv6 support
        User user = this.internal;
        String domainName = "uc018-nosc04.ie";
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(user);
        vdp.edit(domainName);
        List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName), null,
                "::ffff:10.10.1.1"),
                new DomainNameServer(String.format("ns2.%s", domainName), null, "::ffff:10.10.1.2"));
        DomainModificationDetails details = new DomainModificationDetails("Remark");
        details.setDnsDetails(new NameserverFormDetails(dnsList));
        vdp.fillModificationDetails(details);
        assertEquals(dnsList, db().getDnsListForDomain(domainName).subList(0, 2));
    }

    private void checkFieldValue(String fieldText, String expectedValue) {
        String xpath = String.format("//div[normalize-space(.)='%s' and "
                + "@class='ctrl-label-inner']/../following-sibling::div", fieldText);
        By fieldBy = By.xpath(xpath);
        assertEquals(expectedValue, wd().findElement(fieldBy).getText());
    }
}
