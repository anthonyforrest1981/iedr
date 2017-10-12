package pl.nask.crs.app.commons;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.app.tickets.exceptions.DomainNameSyntaxException;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.commons.config.IBatisDaoBasedConfig;
import pl.nask.crs.commons.config.ImmutableConfigEntry;
import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.exceptions.GlueNotAllowedException;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.SecondaryMarketStatus;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class CommonAppServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CommonAppService commonAppService;

    @Resource
    IBatisDaoBasedConfig configDao;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    DomainService domainService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    AuthenticationService authenticationService;

    @DataProvider
    public static Object[][] oneLetterDomainAllowedStatuses() {
        return new Object[][] { {"0"}, {"1"}};
    }

    private AuthenticatedUser user;
    private AuthenticatedUser owner;

    @BeforeMethod
    public void setUp() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        owner = authenticationService
                .authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @Test
    public void testRegisterDomainWithOneLettersAllowed() throws Exception {
        setupOneLetterConfig("1");

        TicketRequest request = createBasicCreateRequest("a.ie", "AHM692-IEDR", null);
        long newTicketId = commonAppService.registerDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        assertNotNull(ticket);
        assertEquals(ticket.getOperation().getDomainNameField().getNewValue(), "a.ie");
    }

    @Test(expectedExceptions = DomainNameSyntaxException.class)
    public void testRegisterDomainWithOneLettersForbidden() throws Exception {
        setupOneLetterConfig("0");

        TicketRequest request = createBasicCreateRequest("a.ie", "AHM692-IEDR", null);
        commonAppService.registerDomain(user, request, null);
    }

    @Test(expectedExceptions = DepositNotFoundException.class)
    public void testRegisterDomainAsDirectWithAdpPayment() throws Exception {
        String directNh = "AAU809-IEDR";
        AuthenticatedUser direct =
                authenticationService.authenticate(directNh, "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        TicketRequest request = createBasicCreateRequest("domainfordirect.ie", directNh, null);
        commonAppService.registerDomain(direct, request, null);
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testTransfer(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        String domainName = "z.ie";

        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        Domain domain = domainSearchService.getDomain(domainName);
        assertEquals(domain.getDsmState().getId(), 17);

        TicketRequest request = createBasicCreateRequest(domainName, "AHM692-IEDR", authcode.getAuthCode());
        long newTicketId = commonAppService.transfer(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        assertEquals(ticket.getOperation().getType(), DomainOperation.DomainOperationType.XFER);

        domain = domainSearchService.getDomain(domainName);
        assertEquals(domain.getDsmState().getId(), 390);
    }

    @Test(expectedExceptions = DepositNotFoundException.class)
    public void testTransferDomainAsDirectWithAdpPayment() throws Exception {
        String directNh = "AAU809-IEDR";
        String domainName = "futuredomena.ie";
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        AuthenticatedUser direct =
                authenticationService.authenticate(directNh, "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        TicketRequest request = createBasicCreateRequest(domainName, directNh, authcode.getAuthCode());
        commonAppService.transfer(direct, request, null);
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testIsTransferPossible(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        assertTrue(commonAppService.isTransferPossible(user, "z.ie"));
    }

    @Test
    public void testTransferOfLockedDomainNotPossible() throws Exception {
        String domainName = "payDomainLocked.ie";
        assertTrue(domainSearchService.getDomain(domainName).getDsmState().getLocked());
        assertFalse(commonAppService.isTransferPossible(user, domainName));
        domainService.unlock(user, domainName, new TestOpInfo("", "remark"), false);
        assertFalse(domainSearchService.getDomain(domainName).getDsmState().getLocked());
        assertTrue(commonAppService.isTransferPossible(user, domainName));
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testModifyDomain(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        Long modificationTicketId = commonAppService.modifyDomain(owner, "z.ie", "New domain holder",
                Collections.singletonList("APIT2-IEDR"), Collections.singletonList("APIT2-IEDR"),
                Arrays.asList(new Nameserver("newns1.z.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                RenewalMode.NoAutorenew, "Test remark");
        Domain domain = domainSearchService.getDomain("z.ie");
        Ticket ticket = ticketSearchService.getTicket(modificationTicketId);
        assertEquals(domain.getDsmState().getRenewalMode(), RenewalMode.NoAutorenew);
        assertEquals(ticket.getOperation().getAdminContactsField().get(0).getNewValue().getNicHandle(), "APIT2-IEDR");
        assertEquals(domain.getNameservers().get(0).getName(), "newns1.z.ie");
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses", expectedExceptions = GlueNotAllowedException.class)
    public void testModifyDomainIncorrectNameservers(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        commonAppService.modifyDomain(owner, "z.ie", "New domain holder", Collections.singletonList("APIT2-IEDR"),
                Collections.singletonList("APIT2-IEDR"),
                Arrays.asList(new Nameserver("newns1.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                RenewalMode.NoAutorenew, "Test remark");
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testModifyNameservers(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        commonAppService.modifyNameservers(user, Collections.singletonList("z.ie"),
                Arrays.asList(new Nameserver("newns1.z.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                "Hostmaster remark");
        Domain domain = domainSearchService.getDomain("z.ie");
        assertEquals(domain.getNameservers().get(0).getName(), "newns1.z.ie");
        assertEquals(domain.getNameservers().get(1).getName(), "newns2.ie");
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses", expectedExceptions = GlueNotAllowedException.class)
    public void testModifyNameserversFailure(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        commonAppService.modifyNameservers(user, Collections.singletonList("z.ie"),
                Arrays.asList(new Nameserver("newns1.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                "Hostmaster remark");
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testValidateNameservers(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        commonAppService.validateNameservers(Collections.singletonList("z.ie"),
                Arrays.asList(new Nameserver("newns1.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                user.getUsername(), false);
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testVerifyAuthCode(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        commonAppService.verifyAuthCode(user, "z.ie", "authcode", 0);
    }

    @Test(dataProvider = "oneLetterDomainAllowedStatuses")
    public void testGenerateOrProlongAuthCode(String oneLetterAllowed) throws Exception {
        setupOneLetterConfig(oneLetterAllowed);
        AuthCode authcode = commonAppService.generateOrProlongAuthCode(owner, "z.ie");
        assertNotNull(authcode);
    }

    @Test(expectedExceptions = DomainLockedException.class)
    public void testModifyLockedDomain() throws Exception {
        Domain domain = domainSearchService.getDomain("paydomainlocked.ie");
        assertTrue(domain.getDsmState().getLocked());
        commonAppService.modifyDomain(owner, "paydomainlocked.ie", "New domain holder",
                Collections.singletonList("APIT2-IEDR"), Collections.singletonList("APIT2-IEDR"),
                Arrays.asList(new Nameserver("ns1.dns.ie", null, null), new Nameserver("ns2.dns.ie", null, null)),
                RenewalMode.NoAutorenew, "Test remark");
    }

    @Test(expectedExceptions = SellRequestExistsException.class)
    public void testModifyDomainWithSellRequest() throws Exception {
        Domain domain = domainSearchService.getDomain("sec-mrkt-domain-1.ie");
        assertEquals(domain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.SellRequestRegistered);
        commonAppService.modifyDomain(user, "sec-mrkt-domain-1.ie", "New domain holder",
                Collections.singletonList("AHK716-IEDR"), Collections.singletonList("AHK716-IEDR"),
                Arrays.asList(new Nameserver("ns1.dns.ie", null, null), new Nameserver("ns2.dns.ie", null, null)),
                RenewalMode.NoAutorenew, "Test remark");
    }

    @Test
    public void testDefaultRegistrationHostmastersRemark() throws Exception {
        TicketRequest request = createBasicCreateRequest("domain.ie", "AHM692-IEDR", null);
        long newTicketId = commonAppService.registerDomain(user, request, null);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        assertEquals(ticket.getHostmastersRemark(), "Ticket created via Console");
    }

    @Test
    public void testDefaultTransferHostmastersRemark() throws Exception {
        String domainName = "futuredomena.ie";
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        TicketRequest request = createBasicCreateRequest(domainName, "AHM692-IEDR", authcode.getAuthCode());
        long newTicketId = commonAppService.transfer(user, request, null);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        assertNull(ticket.getHostmastersRemark());
    }

    @Test
    public void testDefaultModificationHostmastersRemark() throws Exception {
        Long modificationTicketId = commonAppService.modifyDomain(owner, "z.ie", "New domain holder",
                Collections.singletonList("APIT2-IEDR"), Collections.singletonList("APIT2-IEDR"),
                Arrays.asList(new Nameserver("newns1.z.ie", "1.1.1.1", null), new Nameserver("newns2.ie", null, null)),
                RenewalMode.NoAutorenew, "Test remark");
        Ticket ticket = ticketSearchService.getTicket(modificationTicketId);
        assertNull(ticket.getHostmastersRemark());
    }

    private static TicketRequest createBasicCreateRequest(final String domainName, final String contact,
            final String authCode) {
        return new TicketRequest() {
            @Override
            public String getDomainName() {
                return domainName;
            }

            @Override
            public String getDomainHolder() {
                return "John Doe";
            }

            @Override
            public Long getDomainOwnerTypeId() {
                return 1L;
            }

            @Override
            public List<String> getAdminContactNicHandles() {
                return Arrays.asList(contact, null);
            }

            @Override
            public String getTechContactNicHandle() {
                return contact;
            }

            @Override
            public List<Nameserver> getNameservers() {
                return Arrays.asList(new Nameserver("ns1." + domainName, "1.1.1.1", null), new Nameserver(
                        "ns1.example-nameserver.ie", null, null));
            }

            @Override
            public String getRequestersRemark() {
                return "requester remark";
            }

            @Override
            public String getCharityCode() {
                return null;
            }

            @Override
            public boolean getAutorenewMode() {
                return false;
            }

            @Override
            public Period getRegPeriod() {
                return Period.fromMonths(12);
            }

            @Override
            public String getAuthCode() {
                return authCode;
            }

            @Override
            public boolean isCharity() {
                return false;
            }

            @Override
            public TicketSource getTicketSource() {
                return TicketSource.CONSOLE;
            }

            @Override
            public AdminStatus getDefaultAdminStatus() {
                return AdminStatus.NEW;
            }

            @Override
            public void setDomainHolder(String domainHolder) {
            }

            @Override
            public void setDomainOwnerTypeId(Long domainOwnerTypeId) {
            }

            @Override
            public void setAdminContactNicHandles(List</*>>>? extends @Nullable*/ String> adminContactNicHandles) {
            }

            @Override
            public void setNameservers(List<Nameserver> nameservers) {
            }
        };
    }

    @Autowired
    ApplicationConfig config;

    @Autowired
    DomainNameValidator validator;

    private void setupOneLetterConfig(final String allowOneLetter) {
        validator.setApplicationConfig(config);
        ImmutableConfigEntry twoLetterAllowedConfig = new ImmutableConfigEntry("allow_one_or_two_letter_domains",
                allowOneLetter, ConfigEntry.ConfigValueType.BOOLEAN);
        configDao.updateEntry(twoLetterAllowedConfig);

    }
}
