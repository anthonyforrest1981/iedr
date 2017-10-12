package com.iedr.bpr.tests.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.gui.Gui;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class NameserverUtils {

    public static void testNameserversCount(boolean verifyMin, NameserverForm nameserverForm, Gui gui)
            throws NumberFormatException, SQLException {
        int rowsMin = Integer.valueOf(db().getAppConfigValue("nameserver_min_count"));
        int rowsMax = Integer.valueOf(db().getAppConfigValue("nameserver_max_count"));
        assertEquals(rowsMin, nameserverForm.countVisibleRows());
        for (int i = rowsMin; i < rowsMax; i++) {
            nameserverForm.addRow();
        }
        assertEquals(rowsMax, nameserverForm.countVisibleRows());
        nameserverForm.addRow();
        assertTrue(gui.isAlertPresent());
        wd().switchTo().alert().accept();
        for (int i = rowsMax; i > rowsMin; i--) {
            nameserverForm.removeLastRow();
        }
        if (verifyMin) {
            nameserverForm.removeLastRow();
            assertTrue(gui.isAlertPresent());
            wd().switchTo().alert().accept();
        }
        List<DomainNameServer> dnsList = new ArrayList<>();
        for (int i = 0; i < rowsMax; i++) {
            dnsList.add(new DomainNameServer(String.format("ns%s.dns.ie", i)));
        }
        nameserverForm.fillNameserverDetails(new NameserverFormDetails(dnsList));
    }

    public static void verifyNoIpScenarios(NameserverForm nameserverForm, SubmittableForm containingForm, Gui gui) {
        NameserverForm.ErrorMessages errorMessages = nameserverForm.getErrorMessages();
        DnsErrorVerifier verifier = new DnsErrorVerifier(nameserverForm, containingForm, gui);
        // duplicate Nameservers
        verifier.verify("ns0.dns.ie", errorMessages.duplicateMessage, "Duplicate nameservers shouldn't have passed.");
        // invalid Namserver - incorrect signs included
        verifier.verify("ns1^dns.ie", errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
        // invalid Namserver - no dots
        verifier.verify("ns1dnsie", errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
        // invalid Namserver - two successive dots
        verifier.verify("ns1..dns.ie", errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
        // invalid Namserver - incorrect top-level domain
        verifier.verify("ns1.dns.ii", errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
        // invalid Namserver - segment too long
        char[] charArray = new char[64];
        Arrays.fill(charArray, 'a');
        verifier.verify(new String(charArray) + ".ie", errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
        // invalid Nameserver - name too long
        String serverName = "63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie";
        verifier.verify(serverName, errorMessages.invalidDnsMessage, "Invalid namserver shouldn't have passed.");
    }

    public static void verifyIpScenarios(String domainName, NameserverForm nameserverForm,
            SubmittableForm containingForm, Gui gui) {
        NameserverForm.ErrorMessages errorMessages = nameserverForm.getErrorMessages();
        DnsErrorVerifier verifier = new DnsErrorVerifier(nameserverForm, containingForm, gui);
        // invalid IP number
        verifier.verify("ns1." + domainName, "10.10.1.256", null, errorMessages.invalidIpv4Message,
                "Invalid IP number shouldn't have passed.");
        // IP number entered when not allowed
        verifier.verify("ns1.dns.ie", "10.10.1.1", null, errorMessages.notAllowedGlueMessage,
                "IP number entered when not allowed shouldn't have passed.");
        // IP number empty when required
        verifier.verify("ns1." + domainName, null, null, errorMessages.requiredGlueMessage,
                "Empty IP when required shouldn't have passed.");
    }

    public static void verifyAll(String domainName, NameserverForm nameserverForm, SubmittableForm containingForm,
            Gui gui) {
        verifyNoIpScenarios(nameserverForm, containingForm, gui);
        verifyIpScenarios(domainName, nameserverForm, containingForm, gui);
    }

    public static void verifyCkdns(NameserverForm nameserverForm, Gui gui) {
        List<DomainNameServer> dnsList = Arrays.asList(
                new DomainNameServer("ns0.dns.ie", null, null),
                new DomainNameServer(" ; echo Hello ; ", null, null));
        nameserverForm.fillNameserverDetails(new NameserverFormDetails(dnsList));
        nameserverForm.verifyDns();
        try {
            gui.waitForTextPresentOnPage(nameserverForm.getErrorMessages().ckdnsValidationMessage);
        } catch (TimeoutException e) {
            fail("ckdns should not be run if nameservers are invalid");
        }
    }

    private static class DnsErrorVerifier {

        private NameserverForm nameserverForm;
        private SubmittableForm containingForm;
        private Gui gui;

        public DnsErrorVerifier(NameserverForm nameserverForm, SubmittableForm containingForm, Gui gui) {
            this.nameserverForm = nameserverForm;
            this.containingForm = containingForm;
            this.gui = gui;
        }

        public void verify(String dns, String message, String failureMessage) {
            verify(dns, null, null, message, failureMessage);
        }

        public void verify(String dns, String ipv4, String ipv6, String message, String failureMessage) {
            List<DomainNameServer> dnsList = Arrays.asList(new DomainNameServer("ns0.dns.ie", null, null),
                    new DomainNameServer(dns, ipv4, ipv6));
            nameserverForm.fillNameserverDetails(new NameserverFormDetails(dnsList));
            containingForm.submit();
            try {
                gui.waitForTextPresentOnPage(message);
            } catch (TimeoutException e) {
                fail(failureMessage);
            }
        }

    }

}
