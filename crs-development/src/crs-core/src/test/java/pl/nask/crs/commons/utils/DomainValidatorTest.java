package pl.nask.crs.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.commons.config.IBatisDaoBasedConfig;
import pl.nask.crs.commons.config.ImmutableConfigEntry;
import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;
import pl.nask.crs.domains.AbstractContextAwareTest;

public class DomainValidatorTest extends AbstractContextAwareTest {

    @Autowired
    IBatisDaoBasedConfig configDao;

    @Test
    public void domainValidatorTest() throws InvalidDomainNameException {
        DomainNameValidator.validateName("q.qq.valid.uk");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void nullDomainTest() throws InvalidDomainNameException {
        DomainNameValidator.validateName(null);
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void blankDomainTest() throws InvalidDomainNameException {
        DomainNameValidator.validateName("");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void singleDotDomainTest() throws InvalidDomainNameException {
        DomainNameValidator.validateName(".");
    }

    @Test
    public void domainWithHyphens() throws InvalidDomainNameException {
        DomainNameValidator.validateName("ns1.d-n-a.net");
    }

    @Test
    public void validNonAsciiDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateName("ąęćśń.pl");
    }

    @Test
    public void validNonAsciiTopLevelDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateName("domain-name.unnoȓmaližed");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void invalidCharacter() throws InvalidDomainNameException {
        DomainNameValidator.validateName("Passw0rd!.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void domainBeginningWithDot() throws InvalidDomainNameException {
        DomainNameValidator.validateName(".qq.invalid.uk");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void emptySegment() throws InvalidDomainNameException {
        DomainNameValidator.validateName("qq..invalid.uk");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void singleSegment() throws InvalidDomainNameException {
        DomainNameValidator.validateName("domain-name");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void topLevelDomainBeyondList() throws InvalidDomainNameException {
        DomainNameValidator.validateName("domain-name.yy");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void hypenAtTheBeginning() throws InvalidDomainNameException {
        DomainNameValidator.validateName("-domain-name.xxx");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void hypenAtTheEnd() throws InvalidDomainNameException {
        DomainNameValidator.validateName("domain-name-.xxx");
    }

    // Unicode < 63, Punycode > 63
    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void punycodeTooLong() throws InvalidDomainNameException {
        DomainNameValidator.validateName("āăąćĉċčďđēĕėęěĝğġģĥħĩīĭįıĳĵķĺļľŀ.xxx");
    }

    @Test
    public void ieDomainValidatorTest() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("valid.ie");
        DomainNameValidator.validateIedrName("1a.ie");
    }

    @Test
    public void ieDomainWithHyphens() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("d-n-a.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void iePunycodeDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("áéíóú.ie");
    }

    @Test
    public void iePunycodeDomainWithIdnAllowed() throws InvalidDomainNameException {
        enable("allow_idn_domains");
        DomainNameValidator.validateIedrName("áéíóú.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void oneLetterDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("q.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void twoLetterDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("qq.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void ieDomainWithIncorectPostfix() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("domain.uk");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void uppercaseLetterDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("Domain.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void idnDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("xn--domain.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void nonIrishPunycodeDomain() throws InvalidDomainNameException {
        DomainNameValidator.validateIedrName("łukasz.ie");
    }

    @Test
    public void domainMaxCorrectSegmentLength() throws InvalidDomainNameException {
        DomainNameValidator.validateName("63-characters-is-the-longest-possible-domain-name-for-a-website.com");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void domainSegmentLength() throws InvalidDomainNameException {
        DomainNameValidator.validateName("63-characters-is-the-longest-possible-domain-name-for-a-website.coma");
    }

    @Test
    public void domainMaxCorrectDomainLength() throws InvalidDomainNameException {
        DomainNameValidator.validateName("63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-we.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void domainMaxDomainLength() throws InvalidDomainNameException {
        DomainNameValidator.validateName("63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie");
    }

    @Test
    public void oneLetterDomainAllowed() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        DomainNameValidator.validateIedrName("a.ie");
    }

    @Test
    public void twoLetterDomainAllowed() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        DomainNameValidator.validateIedrName("aa.ie");
    }

    @Test
    public void oneLetterNonAsciiDomainIDNEnabled() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        enable("allow_idn_domains");
        DomainNameValidator.validateIedrName("á.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void oneLetterNonAsciiDomainIDNDisabled() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        disable("allow_idn_domains");
        DomainNameValidator.validateIedrName("á.ie");
    }

    @Test
    public void twoLetterNonAsciiDomainIDNEnabled() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        enable("allow_idn_domains");
        DomainNameValidator.validateIedrName("aá.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void twoLetterNonAsciiDomainIDNDisabled() throws InvalidDomainNameException {
        enable("allow_one_or_two_letter_domains");
        disable("allow_idn_domains");
        DomainNameValidator.validateIedrName("aá.ie");
    }

    private void enable(String key) {
        modifyAppConfig(key, "1");
    }

    private void disable(String key) {
        modifyAppConfig(key, "0");
    }

    @Autowired
    ApplicationConfig config;

    @Autowired
    DomainNameValidator validator;

    private void modifyAppConfig(String key, String value) {
        validator.setApplicationConfig(config);
        ImmutableConfigEntry twoLetterAllowedConfig = new ImmutableConfigEntry(key, value,
                ConfigEntry.ConfigValueType.BOOLEAN);
        configDao.updateEntry(twoLetterAllowedConfig);
    }

}
