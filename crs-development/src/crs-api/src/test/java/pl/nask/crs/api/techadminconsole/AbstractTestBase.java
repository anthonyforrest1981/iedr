package pl.nask.crs.api.techadminconsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.common.CRSCommonAppService;
import pl.nask.crs.api.domain.CRSDomainAppService;
import pl.nask.crs.api.nichandle.CRSNicHandleAppService;
import pl.nask.crs.api.payment.CRSPaymentAppService;
import pl.nask.crs.api.ticket.CRSTicketAppService;
import pl.nask.crs.api.users.CRSPermissionsAppService;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.DomainSearchCriteriaVO;
import pl.nask.crs.api.vo.search.TicketSearchCriteriaVO;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.impl.EmailQueue;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.InternalUserIBatisDAO;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 *
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 * TAC stands for Tech or Admin Contact
 *
 * There are some operations which may be performed by both contacts: tech or admin.
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public abstract class AbstractTestBase extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    protected CRSAuthenticationService authService;

    @Autowired
    protected CRSNicHandleAppService crsNicHandleService;

    @Autowired
    protected CRSDomainAppService crsDomainAppService;

    @Autowired
    protected CRSTicketAppService crsTicketAppService;

    @Autowired
    protected CRSCommonAppService crsCommonAppService;

    @Autowired
    protected CRSPaymentAppService crsPaymentAppService;

    @Autowired
    protected CRSPermissionsAppService crsPermissionsAppService;

    // internal
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private DomainSearchService domainSearch;

    @Autowired
    private NicHandleService nhService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private TicketSearchService ticketSearchService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EmailQueue emailQueue;

    @Autowired
    private InternalUserIBatisDAO internalUserIBatisDAO;

    protected static final String REMOTE_ADDRESS = "127.0.0.1";
    protected static final String PASSWORD = "Passw0rd!";
    protected static final String NEW_PASSWORD = "Passw0rd!1";
    protected static final String OTHER_DOMAIN_NAME = "suka.ie";
    protected static final String OTHER_NIC = "IDL2-IEDR";

    protected abstract String getNic();

    protected abstract String getOwnDomainName();

    protected abstract String getNewDomainName();

    protected Domain getOwnDomain() throws Exception {
        return domainSearch.getDomain(getOwnDomainName());
    }

    protected AuthenticatedUserVO getAuthenticatedUser() throws Exception {
        try {
            setPassword();
        } catch(PasswordAlreadyExistsException e) {
            // expected
        }
        return authService.authenticate(getNic(), PASSWORD, REMOTE_ADDRESS, null);
    }

    protected abstract long getTicketId();

    private void setPassword() throws PasswordAlreadyExistsException {
        userService.changePassword(getNic(), PASSWORD, TestOpInfo.DEFAULT);
    }

    @BeforeMethod
    public void initTest() throws Exception {
        cleanEmailQueue();
    }

    private void cleanEmailQueue() throws Exception {
        while (emailQueue.size() > 0)
            emailQueue.next();
    }

    private Email getEmail() throws IllegalStateException {
        if (emailQueue.size() == 0) {
            throw new IllegalStateException("No email in the queue");
        } else {
            try {
                return emailQueue.next().getEmail();
            } catch (InterruptedException e) {
                throw new IllegalStateException("Failed to get the message", e);
            }
        }
    }

    // there should be NO error when resetting own password
    @Test
    public void shouldBeAbleToResetPassword() throws Exception {
        // trigger password reset
        resetPassword();
    }

    private void resetPassword() throws Exception {
        cleanEmailQueue();
        crsNicHandleService.requestPasswordReset(getNic(), REMOTE_ADDRESS);
        Email email = getEmail();
        Pattern p = Pattern.compile("&token=([^\"]+)");
        Matcher m = p.matcher(email.getText());
        AssertJUnit.assertTrue(
                String.format("Failed to find the token in the email using pattern: \"%s\" in an email", p.pattern()),
                m.find());
        String token = m.group(1);
        crsNicHandleService.resetPassword(getNic(), token, null, NEW_PASSWORD);
    }

    @Test
    public void shouldBeAbleToLogin() throws Exception {
        setPassword();
        authService.authenticate(getNic(), PASSWORD, REMOTE_ADDRESS, null);
    }

    @Test
    public void shouldBeAbleToLogout() throws Exception {
        // must assume, that the user CAN log-in
        AuthenticatedUserVO user = getAuthenticatedUser();
        authService.logout(user);
    }

    @Test
    public void shouldSeeOwnDomains() throws Exception {
        DomainSearchCriteriaVO criteria = new DomainSearchCriteriaVO();
        DomainSearchResultVO res = crsDomainAppService.findDomains(getAuthenticatedUser(), criteria, 0, 10, null);
        AssertJUnit.assertTrue("Some domains should be found", res.getTotalResults() > 0);
        validateDomains(res);
    }

    private void validateDomains(DomainSearchResultVO res) throws Exception {
        for (DomainVO dv : res.getResults()) {
            System.err.println(dv.getName());
            Domain d = domainSearch.getDomain(dv.getName());
            List<Contact> tacList = new ArrayList<Contact>(d.getAdminContacts());
            tacList.addAll(d.getTechContacts());
            assertInContacts(tacList);
            assertNotInContacts(d.getBillingContacts());
        }
    }

    private void assertNotInContacts(List<Contact> contacts) {
        for (Contact c : contacts) {
            if (getNic().equalsIgnoreCase(c.getNicHandle()))
                AssertJUnit.fail("Contact found on a list");
        }
    }

    private void assertInContacts(List<Contact> contacts) {
        for (Contact c : contacts) {
            if (getNic().equalsIgnoreCase(c.getNicHandle()))
                return;
        }
        AssertJUnit.fail("Couldn't find contact in a contact list");
    }

    @Test
    public void shouldSeeOwnTickets() throws Exception {
        TicketSearchCriteriaVO searchCriteria = new TicketSearchCriteriaVO();

        TicketSearchResultVO res = crsTicketAppService.find(getAuthenticatedUser(), searchCriteria, 0, 10, null);
        AssertJUnit.assertTrue("A ticket should be found", res.getTotalResults() > 0);
        AssertJUnit.assertEquals("Ticket id should be the same as the newly created ticket", getTicketId(), res.getResults()
                .get(0).getId());
        validateTickets(res);
    }

    @Test
    public void shouldViewOwnTicket() throws Exception {
        TicketVO res = crsTicketAppService.view(getAuthenticatedUser(), getTicketId());
        AssertJUnit.assertNotNull("Ticket should not be null", res);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotViewOtherTicket() throws Exception {
        crsTicketAppService.view(getAuthenticatedUser(), 256754L);
    }

    private void validateTickets(TicketSearchResultVO res) {
        for (TicketVO t : res.getResults()) {
            if (!getNic().equalsIgnoreCase(t.getCreator())) {
                AssertJUnit.fail("User is not a creator of the ticket");
            }
        }
    }

    @Test
    public void shouldSeeOwnNichandle() throws Exception {
        NicHandleVO nh = crsNicHandleService.get(getAuthenticatedUser(), getNic());
        AssertJUnit.assertNotNull(nh);
    }

    @Test
    public void shouldSeeNichandleCreatedByHimself() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        NicHandleEditVO nicHandleCreateWrapper = new NicHandleEditVO(crsNicHandleService.get(user, getNic()));
        String newNic = crsNicHandleService.create(user, nicHandleCreateWrapper, "test");
        NicHandleVO nh = crsNicHandleService.get(getAuthenticatedUser(), newNic);
        AssertJUnit.assertNotNull(nh);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotViewOtherNichandles() throws Exception {
        crsNicHandleService.get(getAuthenticatedUser(), OTHER_NIC);
    }

    @Test
    public void shouldEditOwnNichandle() throws Exception {
        editNicHandle(getNic());
    }

    private void editNicHandle(String nic) throws Exception {
        AuthenticatedUserVO u = getAuthenticatedUser();
        NicHandleVO nh = crsNicHandleService.get(u, nic);
        NicHandleEditVO nicHandleData = new NicHandleEditVO(nh);
        nicHandleData.setName("new name");
        cleanEmailQueue();
        crsNicHandleService.save(u, getNic(), nicHandleData, "test");

        // check, if a notification email was send
        assertEmailSent("Nic-Handle details amended");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotEditOtherNichandles() throws Exception {
        editNicHandle(OTHER_NIC);
    }

    @Test
    public void shouldViewOwnDomain() throws Exception {
        ExtendedDomainInfoVO domain = crsDomainAppService.view(getAuthenticatedUser(), getOwnDomainName());
        AssertJUnit.assertNotNull(domain);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotViewOthersDomain() throws Exception {
        crsDomainAppService.view(getAuthenticatedUser(), OTHER_DOMAIN_NAME);
    }

    @Test
    public void shouldNotBeDirectNorRegistrar() throws Exception {
        setPassword();
        User u = userSearchService.get(getNic());
        AssertJUnit.assertFalse("User is a Direct", u.hasGroup(Level.Direct));
        AssertJUnit.assertFalse("User is a Registrar", u.hasGroup(Level.Registrar));
        AssertJUnit.assertFalse("User is a SuperRegistrar", u.hasGroup(Level.SuperRegistrar));
    }

    private List<String> getNicHandleList(List<Contact> contacts) {
        Function<Contact, String> getNicHandle = new Function<Contact, String>() {
            public String apply(Contact contact) {
                return contact.getNicHandle();
            }
        };
        return Lists.transform(contacts, getNicHandle);
    }

    private List<String> getDefaultAdminContacts(Domain domain) {
        return getNicHandleList(domain.getAdminContacts());
    }

    private List<String> getDefaultTechContacts(Domain domain) {
        return getNicHandleList(domain.getTechContacts());
    }

    private List<NameserverVO> getDefaultNameservers(Domain domain) throws Exception {
        Function<Nameserver, NameserverVO> toVo = new Function<Nameserver, NameserverVO>() {
            public NameserverVO apply(Nameserver ns) {
                return new NameserverVO(ns);
            }
        };
        return Lists.transform(domain.getNameservers(), toVo);
    }

    @Test
    public void shouldModifyOwnDomainHolder() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        cleanEmailQueue();
        Domain domain = getOwnDomain();
        crsCommonAppService.modifyDomain(user, domain.getName(), "New domain holder", getDefaultAdminContacts(domain),
                getDefaultTechContacts(domain), getDefaultNameservers(domain), domain.getDsmState().getRenewalMode(),
                "test");
    }

    @Test
    public void shouldModifyOwnDomainAdminContacts() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        NicHandleEditVO nicHandleCreateWrapper = new NicHandleEditVO(crsNicHandleService.get(user, getNic()));
        String newNic = crsNicHandleService.create(user, nicHandleCreateWrapper, "test");
        List<String> newAdminC = Arrays.asList(newNic);
        Domain domain = getOwnDomain();
        crsCommonAppService.modifyDomain(user, domain.getName(), domain.getHolder(), newAdminC,
                getDefaultTechContacts(domain), getDefaultNameservers(domain), domain.getDsmState().getRenewalMode(),
                "test");
    }

    @Test
    public void shouldModifyOwnDomainTechContacts() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        NicHandleEditVO nicHandleCreateWrapper = new NicHandleEditVO(crsNicHandleService.get(user, getNic()));
        String newNic = crsNicHandleService.create(user, nicHandleCreateWrapper, "test");
        List<String> newList = Arrays.asList(newNic);
        Domain domain = getOwnDomain();
        crsCommonAppService.modifyDomain(user, domain.getName(), domain.getHolder(), getDefaultAdminContacts(domain),
                newList, getDefaultNameservers(domain), domain.getDsmState().getRenewalMode(), "test");
    }

    @Test
    public void shouldModifyOwnDomainNameservers() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        Domain domain = getOwnDomain();
        crsCommonAppService.modifyDomain(user, domain.getName(), domain.getHolder(), getDefaultAdminContacts(domain),
                getDefaultTechContacts(domain), newNameservers(), domain.getDsmState().getRenewalMode(), "test");
    }

    private List<NameserverVO> newNameservers() {
        List<NameserverVO> l = new ArrayList<NameserverVO>();
        l.add(new NameserverVO("nask.pl", null, null));
        l.add(new NameserverVO("waw.nask.pl", null, null));
        return l;
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldModifyOwnDomainRenewalMode() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        Domain domain = getOwnDomain();
        crsCommonAppService.modifyDomain(user, domain.getName(), domain.getHolder(), getDefaultAdminContacts(domain),
                getDefaultTechContacts(domain), getDefaultNameservers(domain),
                domain.getDsmState().getRenewalMode() == RenewalMode.Autorenew ? RenewalMode.NoAutorenew
                        : RenewalMode.Autorenew, "test");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldModifyOwnDomainRenewalMode2way() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        ExtendedDomainInfoVO domain = crsDomainAppService.view(user, getOwnDomainName());
        crsDomainAppService.modifyRenewalMode(user, Arrays.asList(getOwnDomainName()), domain.getDomain().getDsmState()
                .getRenewalMode() == RenewalMode.Autorenew ? RenewalMode.NoAutorenew : RenewalMode.Autorenew);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotModifyOthersDomainData() throws Exception {
        AuthenticatedUserVO user = getAuthenticatedUser();
        NicHandleEditVO nicHandleCreateWrapper = new NicHandleEditVO(crsNicHandleService.get(user, getNic()));
        String newNic = crsNicHandleService.create(user, nicHandleCreateWrapper, "test");
        List<String> newList = Arrays.asList(newNic);
        crsCommonAppService.modifyDomain(user, OTHER_DOMAIN_NAME, "New domain holder", newList, newList,
                newNameservers(), RenewalMode.Autorenew, "test");
    }

    @Test
    public void shouldEnterVnrpOwnDomain() throws Exception {
        crsDomainAppService.enterVoluntaryNRP(getAuthenticatedUser(), getOwnDomainName());
    }

    @Test
    public void shouldLeaveVnrpOwnDomain() throws Exception {
        crsDomainAppService.removeFromVoluntaryNRP(getAuthenticatedUser(), getOwnDomainName());
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotCHangeOthersDomainVNRP() throws Exception {
        crsDomainAppService.enterVoluntaryNRP(getAuthenticatedUser(), OTHER_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotRegisterNewDomain() throws Exception {
        crsCommonAppService.registerDomain(getAuthenticatedUser(), new RegistrationRequestVO(), null);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotRequestDomainTransfer() throws Exception {
        crsCommonAppService.transferDomain(getAuthenticatedUser(), new TransferRequestVO(), null);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotMakePayments() throws Exception {
        DomainWithPeriodVO d = new DomainWithPeriodVO(getOwnDomainName(), 1);
        CreditCardVO card = new CreditCardVO();
        card.setCardNumber("123456");
        card.setCardHolderName("any name");
        card.setCardExpDate("2021-01");
        card.setCardType("VISA");
        card.setCvnNumber("111");
        card.setCvnPresenceIndicator(1);
        crsPaymentAppService.payForDomainRenewal(getAuthenticatedUser(), Arrays.asList(d), PaymentMethod.CC, card);
    }

    @Test
    public void shouldModifyOwnDNS() throws Exception {
        cleanEmailQueue();
        crsCommonAppService.modifyNameservers(getAuthenticatedUser(), Arrays.asList(getOwnDomainName()),
                newNameservers(), "test");

        assertEmailSent("DNS Modification");
    }

    private void assertEmailSent(String subject) {
        Email e = getEmail();
        AssertJUnit.assertNotNull(e);
        boolean subjectOk = e.getSubject().startsWith(subject);
        AssertJUnit.assertTrue(
                "Email's subject should start with: '" + subject + "' but the subject is: '" + e.getSubject() + "'",
                subjectOk);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void shouldNotModifyOtherDNS() throws Exception {
        crsCommonAppService.modifyNameservers(getAuthenticatedUser(),
                Arrays.asList(OTHER_DOMAIN_NAME, getOwnDomainName()), newNameservers(), "test");
    }

    @Test
    public void mustHavePermissionToCheckOwnUserLevel() throws Exception {
        int level = crsPermissionsAppService.getUserLevel(getAuthenticatedUser());
        AssertJUnit.assertEquals("Tech/Admin user level", 4, level);
    }

    @Test
    public void mustHavePermissionToChangeOwnTfaFlag() throws Exception {
        crsNicHandleService.changeTfaFlag(getAuthenticatedUser(), false);
    }

    @Test
    public void mustHavePermissionToChangeOwnPassword() throws Exception {
        crsNicHandleService.changePassword(getAuthenticatedUser(), getNic(), PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void mustHavePermissionToChangeOthersPassword() throws Exception {
        crsNicHandleService.changePassword(getAuthenticatedUser(), OTHER_NIC, PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
    }

    @Test
    public void mustHavePermissionToGetVatRate() throws Exception {
        try {
            crsPaymentAppService.getVatRate(getAuthenticatedUser());
        } catch (AccessDeniedException e) {
            AssertJUnit.fail("Should have a valid permission but got access denied: " + e);
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("Vat category not defined")) {
                // skip - nothing wrong with that in this case
            } else {
                // rethrow
                throw e;
            }
        }
    }

}
