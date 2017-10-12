package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.util.*;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.junit.Test;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.BulkTransferPage;
import com.iedr.bpr.tests.pages.crsweb.ExportAuthcodesPage;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.EmailUtils;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailVerifier;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailsVerifier;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UC084 extends SeleniumTest {

    public UC084(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc084_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc084_data.sql";
    }

    @Test
    public void test_uc084_sc04_present() throws SQLException {
        String domainName = "uc084-sc04-a.ie";
        User user = this.registrar;
        String currentAuthCode = db().getAuthcodeForDomain(domainName);
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(this.internal);
        vdp.sendAuthCode(domainName);
        String authCode = extractAuthCodeFromReceivedEmails(user, user, domainName);
        assertEquals(currentAuthCode, db().getAuthcodeForDomain(domainName));
        assertEquals(currentAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc04_empty() throws SQLException {
        String domainName = "uc084-sc04-b.ie";
        User user = this.registrar;
        assertEquals(null, db().getAuthcodeForDomain(domainName));
        ViewDomainPage vdp = new ViewDomainPage();
        crsweb().login(this.internal);
        vdp.sendAuthCode(domainName);
        String authCode = extractAuthCodeFromReceivedEmails(user, user, domainName);
        String dbAuthCode = db().getAuthcodeForDomain(domainName);
        assertNotNull(dbAuthCode);
        assertEquals(dbAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc06() throws SQLException {
        Set<String> domainNames = new HashSet<String>(Arrays.asList("uc084-sc06-a.ie", "uc084-sc06-b.ie",
                "uc084-sc06-c.ie", "uc084-sc06-d.ie", "uc084-sc06-e.ie"));
        Map<String, String> expectedAuthCodes = getAuthCodesForDomains(domainNames);
        String domainPrefix = "uc084-sc06";
        crsweb().login(this.internal);
        exportAuthCodes(domainNames, domainPrefix);
        Map<MultiKey, Set<String>> domainsMap = mapReceiversToDomains(domainNames);
        Map<MultiKey, ExpectedEmailSummary> emailsMap = getExpectedBulkExportEmails(domainsMap);
        emails.addAll(emailsMap.values());
        Set<ActualEmailSummary> actualEmails = checkAndResetEmails(emails);
        verifySentAuthCodes(actualEmails, emailsMap, domainsMap, expectedAuthCodes);
    }

    @Test
    public void test_uc084_sc07() {
        List<String> domains = Arrays.asList("uc084-sc07-a.ie", "uc084-sc07-b.ie");
        crsweb().login(this.internal);
        BulkTransferPage btp = new BulkTransferPage();
        btp.createBulkTransfer(1113, 1111);
        btp.completeBulkTransfer(domains);
    }

    private void exportAuthCodes(Set<String> domainNames, String domainPrefix) {
        ExportAuthcodesPage eap = new ExportAuthcodesPage();
        eap.exportDomains(domainNames, domainPrefix);
        crsweb().waitForTextPresentOnPage("Emails sent successfully");
    }

    private Map<MultiKey, Set<String>> mapReceiversToDomains(Set<String> domainNames) throws SQLException {
        Map<MultiKey, Set<String>> map = new HashMap<MultiKey, Set<String>>();
        for (String domainName : domainNames) {
            String adminCNh = db().getContactForDomain(domainName, "Admin");
            String billCNh = db().getContactForDomain(domainName, "Billing");
            MultiKey key = new MultiKey(adminCNh, billCNh);
            if (!map.containsKey(key)) {
                map.put(key, new HashSet<String>());
            }
            map.get(key).add(domainName);
        }
        return map;
    }

    private Map<MultiKey, ExpectedEmailSummary> getExpectedBulkExportEmails(Map<MultiKey, Set<String>> domainsMap)
            throws SQLException {
        Map<MultiKey, ExpectedEmailSummary> emails = new HashMap<MultiKey, ExpectedEmailSummary>();
        for (MultiKey receivers : domainsMap.keySet()) {
            String adminCNh = (String) receivers.getKey(0);
            String billCNh = (String) receivers.getKey(1);
            User adminC = new User(adminCNh, null, true, db().getNicHandleEmail(adminCNh));
            User billC = new User(billCNh, null, true, db().getNicHandleEmail(billCNh));
            ExpectedEmailSummary email = new ToAndCcMatches(emailSummaryGenerator.getAuthCodeBulkExport(adminC, billC),
                    billC.email, adminC.email);
            emails.put(receivers, email);
        }
        return emails;
    }

    private void verifySentAuthCodes(Set<ActualEmailSummary> actualEmails,
            Map<MultiKey, ExpectedEmailSummary> emailsMap, Map<MultiKey, Set<String>> domainsMap,
            Map<String, String> expectedAuthCodes) throws SQLException {
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(emails, actualEmails);
        for (MultiKey receivers : emailsMap.keySet()) {
            ExpectedEmailSummary expected = emailsMap.get(receivers);
            ActualEmailSummary actual = rev.getActualEmail(expected);
            Set<String> expectedDomains = domainsMap.get(receivers);
            ReceivedEmailVerifier ev = new ReceivedEmailVerifier(expected, actual);
            Map<String, String> parameters = ev.getPopulatedParameters();
            Set<String> actualDomains = EmailUtils.getValueAsSet(parameters.get("DOMAIN_LIST"));
            assertEquals(expectedDomains, actualDomains);
            Map<String, String> actualAuthCodes = getAuthCodesFromBulkEmailParameters(parameters
                    .get("DOMAIN_TABLE_WITH_AUTHCODES"));
            assertEquals(expectedDomains.size(), actualAuthCodes.size());
            for (Map.Entry<String, String> entry : actualAuthCodes.entrySet()) {
                String domainName = entry.getKey();
                String expectedAuthCode = expectedAuthCodes.get(domainName);
                if (expectedAuthCode == null) {
                    assertNotNull(entry.getValue());
                } else {
                    assertEquals(expectedAuthCode, entry.getValue());
                }
            }
        }
    }

    private Map<String, String> getAuthCodesFromBulkEmailParameters(String parameter) {
        Map<String, String> authCodes = new HashMap<String, String>();
        List<String> lines = Arrays.asList(parameter.split("\n"));
        lines = lines.subList(3, lines.size() - 1);
        for (String line : lines) {
            List<String> values = Arrays.asList(line.split("\\|"));
            values = values.subList(1, values.size());
            authCodes.put(values.get(0).trim(), values.get(1).trim());
        }
        return authCodes;
    }

    private class ToAndCcMatches extends DetailedExpectedEmailSummary {

        private Set<String> to;
        private Set<String> cc;

        public ToAndCcMatches(ExpectedEmailSummary base, String to, String cc) {
            super(base);
            this.to = EmailUtils.getValueAsSet(to);
            this.cc = EmailUtils.getValueAsSet(cc);
            this.cc.removeAll(this.to);
        }

        @Override
        public boolean matches(ActualEmailSummary actualEmail) {
            return to.equals(actualEmail.to) && cc.equals(actualEmail.cc);
        }

    }

    private Map<String, String> getAuthCodesForDomains(Set<String> domainNames) throws SQLException {
        Map<String, String> authCodes = new HashMap<String, String>();
        for (String domainName : domainNames) {
            authCodes.put(domainName, db().getAuthcodeForDomain(domainName));
        }
        return authCodes;
    }

}
