package pl.nask.crs.regression;

import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * tests isse #12140 https://drotest4.nask.net.pl:3000/issues/12140
 *
 * Email - $LOSING_BILL-C_NAME$ in E_ID = 39 is incorrectly populated with gaining Bill C
 *
 * @author Artur Gniadzik
 *
 */
public class TransferEmailTest extends AbstractEmailsTest {
    @Autowired
    private CommonAppService commonAppService;
    @Autowired
    private TriplePassSupportService triplePassSupportService;
    @Autowired
    private DomainSearchService domainSearchService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private TicketAppService ticketAppService;
    @Autowired
    private NicHandleDAO nicHandleDAO;
    @Autowired
    private HistoricalNicHandleDAO historicalNicHandleDAO;

    private String domainForTransfer = "thedomain-totransfer.ie";
    private String losingRegistrar = "APIT2-IEDR"; // account 668
    private String gainingRegistrar = "APITEST-IEDR"; // 666
    private long gainingAccountNumber = 666;

    @Test
    public void email39shouldBeSentWhenTransferIsCompleted() throws Exception {
        // given
        String losingName = "losing";
        String gainingName = "gaining";
        setName(gainingRegistrar, gainingName);
        setName(losingRegistrar, losingName);

        TicketRequest req = prepareTransferRequest();
        Domain beforeTransfer = getDomain();

        AssertJUnit.assertEquals(losingRegistrar, beforeTransfer.getBillingContact().getNicHandle());

        // when
        createExpectations(39, populatedValues(losingName, gainingName));
        performDomainTransfer(req);

        // then
        // the expectations are met
        // the domain is transferred to a new account

    }

    private void setName(String nh, String newName) {
        NicHandle nicHandle = nicHandleDAO.get(nh);
        nicHandle.setName(newName);
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR"));
    }

    private Map<ParameterNameEnum, String> populatedValues(String losingName, String gainingName) {
        Map<ParameterNameEnum, String> map = new HashMap<ParameterNameEnum, String>();
        map.put(ParameterNameEnum.LOSING_BILL_C_NAME, losingName);
        map.put(ParameterNameEnum.GAINING_BILL_C_NAME, gainingName);
        return map;
    }

    private TicketRequest prepareTransferRequest() throws Exception {
        final Domain d = getDomain();
        AuthenticatedUser user = getAuthenticatedUser(losingRegistrar);
        String authCode = domainService.getOrCreateAuthCode(d.getName(), new OpInfo(user)).getAuthCode();
        TransferRequestVO req = new TransferRequestVO();
        req.resetToNameservers(d.getNameservers());
        req.setAdminContactNicHandles(Arrays.asList(d.getFirstAdminContactNic(), d.getSecondAdminContactNic()));
        req.setTechContactNicHandle(d.getTechContactNic());
        req.setDomainHolder(d.getHolder());
        req.setDomainName(domainForTransfer);
        req.setAuthCode(authCode);
        req.setPeriod(1);
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

    private Domain getDomain() {
        try {
            return domainSearchService.getDomain(domainForTransfer);
        } catch (DomainNotFoundException e) {
            throw new IllegalStateException("Domain not found: " + domainForTransfer, e);
        }
    }

    private void performDomainTransfer(TicketRequest req) {
        try {
            long ticketId = commonAppService.transfer(getAuthenticatedUser(gainingRegistrar), req, null);
            AssertJUnit.assertTrue("ticket.id != 0", ticketId != 0);
            AuthenticatedUser user = getAuthenticatedUser("IDL2-IEDR");

            triplePassSupportService.triplePass(user, ticketId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error when creating transfer ticket", e);
        }
    }
}
