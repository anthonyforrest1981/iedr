package pl.nask.crs.api.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.PlainDomainDAO;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(locations = {"/crs-api-config.xml"})
public class DomainTransferTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private CommonAppService commonAppService;
    @Autowired
    private TriplePassSupportService triplePassSupportService;
    @Autowired
    private DomainSearchService domainSearchService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private NicHandleDAO nicHandleDAO;
    @Autowired
    private HistoricalNicHandleDAO historicalNicHandleDAO;
    @Autowired
    private PlainDomainDAO plainDomainDAO;

    private String domainForTransfer = "thedomain-totransfer.ie";
    private String losingRegistrar = "APIT2-IEDR"; // account 668
    private String gainingRegistrar = "APITEST-IEDR"; // 666
    private String gainingDirect = "AAU809-IEDR";
    private long gainingAccountNumber = 666;
    private long losingAccountNumber = 668;

    @Test
    public void theDomainShouldBeTransferredToTheNewAccount() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        performDomainTransfer(gainingRegistrar, req);

        Domain d = getDomain();
        AssertJUnit.assertEquals("Account number", gainingAccountNumber, d.getResellerAccount().getId());
        AssertJUnit.assertEquals("Biling contact", gainingRegistrar, d.getBillingContact().getNicHandle());
    }

    @Test
    public void recreatedAdminContactShouldHaveTelcomInfoCopied() throws Exception {
        TransferRequestVO req = (TransferRequestVO) prepareTransferRequest(gainingRegistrar);
        NicHandle origNicHandle = nicHandleDAO.get("APIT1-IEDR");
        origNicHandle.setFaxes(Arrays.asList("22 123456789", "22 345123697"));
        origNicHandle.setPhones(Arrays.asList("48 123456789", "48 345123697"));
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(origNicHandle, new Date(), "IDL2-IEDR"));
        req.setAdminContactNicHandles(Arrays.asList("", ""));
        performDomainTransfer(gainingRegistrar, req);

        Domain d = getDomain();
        NicHandle newNicHandle = nicHandleDAO.get(d.getFirstAdminContactNic());
        AssertJUnit.assertEquals(origNicHandle.getName(), newNicHandle.getName());
        AssertJUnit.assertEquals(origNicHandle.getPhones(), newNicHandle.getPhones());
        AssertJUnit.assertEquals(origNicHandle.getFaxes(), newNicHandle.getFaxes());
    }

    @Test
    public void email39shouldBeSentWhenTransferIsCompleted() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        performDomainTransfer(gainingRegistrar, req);

        Domain d = getDomain();
        AssertJUnit.assertEquals("Account number", gainingAccountNumber, d.getResellerAccount().getId());
        AssertJUnit.assertEquals("Biling contact", gainingRegistrar, d.getBillingContact().getNicHandle());
    }

    @Test(expectedExceptions = DepositNotFoundException.class)
    public void transferToDirectWithADPPaymentShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingDirect);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        commonAppService.transfer(getAuthenticatedUser(gainingDirect), req, null);
    }

    @Test
    public void transferWithAutorenewShouldMakeDomainAutorenewed() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        ((TransferRequestVO)req).setAutorenewMode(true);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        AuthenticatedUser authenticatedUser = getAuthenticatedUser(gainingRegistrar);
        long ticketId = commonAppService.transfer(authenticatedUser, req, null);
        triplePassSupportService.triplePass(getAuthenticatedUser("IDL2-IEDR"), ticketId);
        Domain afterTransfer = getDomain();
        AssertJUnit.assertEquals(RenewalMode.Autorenew, afterTransfer.getDsmState().getRenewalMode());
    }

    @Test
    public void transferRecordShouldBeStoredAndLinkToRightDomain() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        // Creating transfer ticket modifies the domain, so we have to do it before fetching "beforeTransfer" domain.
        long ticketId = createTransferTicket(gainingRegistrar, req);
        Domain beforeTransfer = getDomain();
        assertEquals(beforeTransfer.getResellerAccount().getId(), losingAccountNumber);
        assertEquals(findDomainsTransferredAway(losingRegistrar).size(), 0);
        triplePassTicket(ticketId);
        List<PlainDomain> domainsTransferredAway = findDomainsTransferredAway(losingRegistrar);
        assertEquals(domainsTransferredAway.size(), 1);
        PlainDomain transferredDomain = domainsTransferredAway.get(0);
        assertTrue(beforeTransfer.getChangeDate().compareTo(transferredDomain.getTransferDate()) <= 0);
        Domain currentDomain = getDomain();
        assertTrue(currentDomain.getChangeDate().compareTo(transferredDomain.getTransferDate()) >= 0);
        assertEquals(currentDomain.getTransferDate(), transferredDomain.getTransferDate());
        assertEquals(currentDomain.getResellerAccount().getId(), gainingAccountNumber);
    }

    @Test
    public void transferShouldSetTransferDate() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        Domain beforeTransfer = getDomain();
        assertNull(beforeTransfer.getTransferDate());
        Date now = new Date();
        performDomainTransfer(gainingRegistrar, req);
        Domain d = getDomain();
        assertTrue(d.getTransferDate().getTime() - now.getTime() < 5000,
                String.format("%s, %s", d.getTransferDate(), now));
    }

    @Test(expectedExceptions = InvalidAuthCodeException.class)
    public void transferWithNoAuthCodeShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar, null);
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
    }

    @Test(expectedExceptions = InvalidAuthCodeException.class)
    public void transferWithWrongAuthCodeShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar, "ABC123456789");
        Domain beforeTransfer = getDomain();
        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
    }

    @Test(expectedExceptions = InvalidAuthCodeException.class)
    public void transferOfLockedDomainShouldFail() throws Exception {
        TicketRequest req = prepareTransferRequest(gainingRegistrar);
        domainService.lock(getAuthenticatedUser(gainingRegistrar), domainForTransfer, new TestOpInfo(gainingRegistrar,
                "remark"));
        assertTrue(domainSearchService.getDomain(domainForTransfer).getDsmState().getLocked());
        commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
    }

    @Test
    public void transferShouldPreserveSubcategory() throws Exception {
        String gaining = "IDL2-IEDR";
        TicketRequest req = prepareTransferRequest(gaining);
        Domain beforeTransfer = domainSearchService.getDomain("domain-with-subcategory-to-transfer.ie");
        performDomainTransfer(gaining, req);
        Domain afterTransfer = domainSearchService.getDomain("domain-with-subcategory-to-transfer.ie");
        assertEquals(afterTransfer.getHolderSubcategory().getId(), beforeTransfer.getHolderSubcategory().getId());
    }

    private TicketRequest prepareTransferRequest(String nh) throws Exception {
        final Domain d = getDomain();
        String authCode = domainService.getOrCreateAuthCode(d.getName(), TestOpInfo.DEFAULT).getAuthCode();
        return prepareTransferRequest(nh, authCode);
    }

    private TicketRequest prepareTransferRequest(String nh, String authCode) throws Exception {
        final Domain d = getDomain();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContactNicHandles(Arrays.asList(nh, null));
        req.setTechContactNicHandle(nh);
        req.setDomainHolder(d.getHolder());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(2);
        req.setPeriodType(PeriodType.Y);
        return req;
    }

    private AuthenticatedUser getAuthenticatedUser(final String username) {
        return new AuthenticatedUser() {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getSuperUserName() {
                return null;
            }

            @Override
            public String getAuthenticationToken() {
                return "";
            }
        };
    }

    private Domain getDomain() throws Exception {
        return domainSearchService.getDomain(domainForTransfer);
    }

    private void performDomainTransfer(String gainingRegistrar, TicketRequest req) throws Exception {
        triplePassTicket(createTransferTicket(gainingRegistrar, req));
    }

    private long createTransferTicket(String gainingRegistrar, TicketRequest req) throws Exception {
        long ticketId = commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
        assertTrue(ticketId != 0, "ticket.id != 0");
        return ticketId;
    }

    private void triplePassTicket(long ticketId) throws Exception {
        AuthenticatedUser user = getAuthenticatedUser("IDL2-IEDR");
        triplePassSupportService.triplePass(user, ticketId);
    }

    private List<PlainDomain> findDomainsTransferredAway(String nicHandle) {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH(nicHandle);
        return plainDomainDAO.findTransferredAwayDomains(criteria, 0, 10, null).getResults();
    }
}
