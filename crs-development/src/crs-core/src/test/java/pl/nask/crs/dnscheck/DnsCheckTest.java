package pl.nask.crs.dnscheck;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.dnscheck.impl.DnsCheckError;
import pl.nask.crs.dnscheck.impl.DnsCheckIdType;
import pl.nask.crs.dnscheck.impl.DnsCheckServiceImpl;
import pl.nask.crs.domains.nameservers.Nameserver;

import mockit.Expectations;
import mockit.Mocked;

public class DnsCheckTest extends AbstractTest {

    private DnsCheckService dnsCheckService;

    @Mocked
    EmailTemplateSender emailTemplateSender;

    @BeforeMethod
    public void setUp() throws Exception {
        dnsCheckService = new DnsCheckServiceImpl("Passw0rd!", emailTemplateSender);
    }

    @Test(expectedExceptions = DnsCheckProcessingException.class)
    public void testCheck() throws Exception {

        new Expectations() {
            {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
            }
        };

        dnsCheckService.check(Arrays.asList("domain.ie"),
                Arrays.asList(new Nameserver("ns1.domain.ie", "10.10.1.1", "FE80::0202:B3FF:FE1E:8329")),
                "AA11-IEDR", true);
    }

    @Test(expectedExceptions = DnsCheckProcessingException.class)
    public void testCheckNoEmail() throws Exception {

        new Expectations() {
            {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };

        dnsCheckService.check(Arrays.asList("domain.ie"),
                Arrays.asList(new Nameserver("ns1.domain.ie", "10.10.1.1", "FE80::0202:B3FF:FE1E:8329")),
                "AA11-IEDR", false);
    }

    @Test
    public void glueNotAllowedTest() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ie"),
                Arrays.asList(new Nameserver("other-domain.ie", "10.10.1.1", "FE80::0202:B3FF:FE1E:8329")),
                "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Glue IPv4 for nameserver: other-domain.ie for" +
                    " domain: domain.ie\nFATAL Glue not allowed");
        }
    }

    @Test
    public void glueRequiredTest() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ie"),
                    Arrays.asList(new Nameserver("ns1.domain.ie", null, null)), "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Glue IPv4 for nameserver: ns1.domain.ie for" +
                    " domain: domain.ie\nFATAL Glue required");
        }
    }

    @Test
    public void validatesDomainsHostname() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ies"),
                    Arrays.asList(new Nameserver("ns1.domain.ie", null, null)), "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Nameserver hostname for nameserver:"
                    + " ns1.domain.ie for domain: domain.ies\nFATAL Invalid domain name");
        }
    }

    @Test
    public void validatesNameserversHostname() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ie"),
                    Arrays.asList(new Nameserver("ns1.domain.ies", null, null)), "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Nameserver hostname for nameserver:"
                    + " ns1.domain.ies for domain: domain.ie\nFATAL Invalid nameserver name");
        }
    }

    @Test
    public void validatesNameserversIpv4Address() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ie"),
                    Arrays.asList(new Nameserver("ns1.domain.ie", "bad.ipv4", null)), "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Glue IPv4 for nameserver: ns1.domain.ie"
                    + " for domain: domain.ie\nFATAL Invalid IPv4 address");
        }
    }

    @Test
    public void validatesNameserversIpv6Address() throws Exception {
        try {
            dnsCheckService.check(Arrays.asList("domain.ie"),
                    Arrays.asList(new Nameserver("ns1.domain.ie", null, "bad:ipv6")), "AA11-IEDR", false);
            Assert.fail("Should have thrown a HostNotConfiguredException");
        } catch (HostNotConfiguredException e) {
            Assert.assertEquals(e.getFatalMessage(), "While checking Glue IPv6 for nameserver: ns1.domain.ie"
                    + " for domain: domain.ie\nFATAL Invalid IPv6 address");
        }
    }

    @Test
    public void testHostNotInitializedException() {
        DnsCheckError dnsError1 = new DnsCheckError("domain.ie", "nameserver1.ie", DnsCheckIdType.HOSTNAME,
                "shortMessage", "OK Full Message\nFATAL first fatal line\nOK It's good\nFATAL second fatal line\n");
        DnsCheckError dnsError2 = new DnsCheckError("domain.ie", "nameserver2.ie", DnsCheckIdType.HOSTNAME,
                "shortMessage", "FATAL the only fatal line\n");
        HostNotConfiguredException ex = new HostNotConfiguredException(Arrays.asList(dnsError1, dnsError2));
        Assert.assertEquals(
                ex.getFullOutputMessage(),
                "While checking Nameserver hostname for nameserver: nameserver1.ie for domain: domain.ie\nOK Full Message\nFATAL first fatal line\nOK It's good\nFATAL second fatal line\n\nWhile checking Nameserver hostname for nameserver: nameserver2.ie for domain: domain.ie\nFATAL the only fatal line\n");
        Assert.assertEquals(
                ex.getFullOutputMessage(true),
                "While checking Nameserver hostname for nameserver: nameserver1.ie for domain: domain.ie\nFATAL first fatal line\nFATAL second fatal line\n\nWhile checking Nameserver hostname for nameserver: nameserver2.ie for domain: domain.ie\nFATAL the only fatal line\n");
        Assert.assertEquals(ex.getSingleOutputMessage("domain.ie", "nameserver1.ie", true),
                "FATAL first fatal line\nFATAL second fatal line\n");
    }
}
