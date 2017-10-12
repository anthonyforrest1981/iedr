package pl.nask.crs.commons.dns;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverValidatorTest extends AbstractContextAwareTest {

    @Autowired
    private ServicesRegistry registry;

    @Test
    public void correctNameservers() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void correctNameserversWithGlue() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.domain-name.ie", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.domain-name.ie", null, "2001:0000:0:fd:123:ffff:0:0");
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void correctNameserversWithGlueAndHangingDot() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.domain-name.ie.", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.domain-name.ie", null, "2001:0000:0:fd:123:ffff:0:0");
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void correctIdnAsciiNameservers() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.xn--ire-9la.ie", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.xn--ire-9la.ie", null, "2001:0000:0:fd:123:ffff:0:0");
        NameserverValidator.checkNameservers("éire.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void correctIdnUnicodeNameservers() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.éire.ie", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.éire.ie", null, "2001:0000:0:fd:123:ffff:0:0");
        NameserverValidator.checkNameservers("xn--ire-9la.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = TooFewNameserversException.class)
    public void tooFewNameservers() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1), registry);
    }

    @Test(expectedExceptions = TooManyNameserversException.class)
    public void tooManyNameservers() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        Nameserver ns3 = new Nameserver("ns3.example.ie", null, null);
        Nameserver ns4 = new Nameserver("ns4.example.ie", null, null);
        Nameserver ns5 = new Nameserver("ns5.example.ie", null, null);
        Nameserver ns6 = new Nameserver("ns6.example.ie", null, null);
        Nameserver ns7 = new Nameserver("ns7.example.ie", null, null);
        Nameserver ns8 = new Nameserver("ns8.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2, ns3, ns4, ns5, ns6, ns7, ns8),
                registry);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void nameserverDuplicate() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", null, null);
        Nameserver ns2 = new Nameserver("ns1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void nameserverDuplicateUppercase() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", null, null);
        Nameserver ns2 = new Nameserver("NS1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void nameserverDuplicateHangingDot() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie.", null, null);
        Nameserver ns2 = new Nameserver("ns1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void nameserverDuplicatePunycode() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.éire.ie", null, null);
        Nameserver ns2 = new Nameserver("ns1.xn--ire-9la.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void incorrectNameserverSyntax() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example..ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = IpSyntaxException.class)
    public void incorrectIPv4Syntax() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.domain-name.ie", "10.10.1..1", null);
        Nameserver ns2 = new Nameserver("ns2.domain-name.ie", null, "2001:0000:0:fd:123:ffff:0:0");
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = IpSyntaxException.class)
    public void incorrectIPv6Syntax() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.domain-name.ie", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.domain-name.ie", null, "2001::0000:0:fd:123:ffff::0:0");
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = GlueNotAllowedException.class)
    public void glueNotAllowed() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.example.ie", "10.10.1.1", null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = GlueRequiredException.class)
    public void glueRequired() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.domain-name.ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = GlueRequiredException.class)
    public void glueRequiredUppercase() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.DOMAIN-NAME.ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = GlueRequiredException.class)
    public void glueRequiredPunycode() throws Exception {
        Nameserver ns1 = new Nameserver("ns1.xn--ire-9la.ie", null, null);
        Nameserver ns2 = new Nameserver("ns2.example.ie", null, null);
        NameserverValidator.checkNameservers("éire.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void correctNameserverNames() throws Exception {
        NameserverValidator.checkNameserverNames(Arrays.asList("ns1.example.ie", "ns2.example.ie"), registry);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void nameserverDNSNameSegmentSize() throws Exception {
        Nameserver ns1 = new Nameserver("63-characters-is-the-longest-possible-domain-name-for-a-website.com/", null, null);
        Nameserver ns2 = new Nameserver("NS1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void nameserverDNSNameSize() throws Exception {
        // 254 characters
        Nameserver ns1 = new Nameserver("63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie", null, null);
        Nameserver ns2 = new Nameserver("NS1.example.ie", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }

    @Test
    public void nameserverDNSNameSizeCorrect() throws Exception {
        // 253 characters
        Nameserver ns1 = new Nameserver("63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-we.ie", null, null);
        // 254 characters, ends with a dot
        Nameserver ns2 = new Nameserver("63-kharacters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-we.ie.", null, null);
        NameserverValidator.checkNameservers("domain-name.ie", Arrays.asList(ns1, ns2), registry);
    }
    @Test
    public void emptyList() throws Exception {
        NameserverValidator.checkNameserverNames(new ArrayList<String>(), registry);
    }

    @Test(expectedExceptions = TooManyNameserversException.class)
    public void tooManyNameserverNames() throws Exception {
        NameserverValidator.checkNameserverNames(Arrays.asList("ns1.example.ie", "ns2.example.ie", "ns3.example.ie",
                "ns4.example.ie", "ns5.example.ie", "ns6.example.ie", "ns7.example.ie", "ns8.example.ie"), registry);
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void nameserverNameDuplicate() throws Exception {
        NameserverValidator.checkNameserverNames(Arrays.asList("ns1.example.ie", "ns1.example.ie"), registry);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void incorrectNameserverNameSyntax() throws Exception {
        NameserverValidator.checkNameserverNames(Arrays.asList("ns1.example..ie", "ns2.example.ie"), registry);
    }
}
