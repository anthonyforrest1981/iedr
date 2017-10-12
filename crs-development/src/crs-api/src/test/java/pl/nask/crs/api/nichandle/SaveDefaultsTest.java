package pl.nask.crs.api.nichandle;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class SaveDefaultsTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSNicHandleAppService crsNicHandleAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Test
    public void saveDefaultsTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "APIT2-IEDR", Arrays.asList("ns1.example.ie", "ns2.example.ie"), 10,
                EmailInvoiceFormat.PDF);
    }

    @Test(expectedExceptions = NicHandleNotFoundException.class)
    public void saveDefaultsTestIncorrectTechContact() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "NOT-EXISTS-IEDR", Arrays.asList("ns1.example.ie", "ns2.example.ie"),
                10, EmailInvoiceFormat.PDF);
    }

    @Test(expectedExceptions = NicHandleIncorrectForAccountException.class)
    public void saveDefaultsTestWrongAccount() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "AA11-IEDR", Arrays.asList("ns1.example.ie", "ns2.example.ie"), 10,
                EmailInvoiceFormat.PDF);
    }

    @Test
    // Number of nameservers is not validated for Reseller Defaults
    public void saveDefaultsTestTooFewNameservers() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "APIT2-IEDR", Arrays.asList("ns1.example.ie"), 10,
                EmailInvoiceFormat.PDF);
    }

    @Test
    public void saveDefaultsTestEmptyNameservers() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "APIT2-IEDR", null, 10, EmailInvoiceFormat.PDF);
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void saveDefaultsTestIncorrectNameserver() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsNicHandleAppService.saveDefaults(user, "APIT2-IEDR", Arrays.asList("ns1.ex@mple.ie"), 10,
                EmailInvoiceFormat.PDF);
    }

}
