package pl.nask.crs.api.nichandle;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.api.Helper;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.WsAuthenticationService;

import static pl.nask.crs.api.Helper.createBasicCreditCard;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class NicHandleExportTest extends AbstractTransactionalTestNGSpringContextTests {
    @Resource
    NicHandleSearchService nicHandleSearchService;
    @Resource
    NicHandleService nicHandleService;
    @Resource
    DomainService domainService;
    @Resource
    AccountSearchService accountSearchService;
    @Resource
    CommonAppService commonAppService;
    @Resource
    DepositService depositService;
    @Resource
    WsAuthenticationService authService;

    @Test
    public void exportForRegistration() throws Exception {
        String username = createDirectAccount();
        verifyExported(username, false);
        registerDomain(username);
        verifyExported(username, true);
    }

    @Test
    public void exportForRegistrationWithDifferentAccountNumber() throws Exception {
        String username = createDirectAccount();
        verifyExported(username, false);
        nicHandleService.changeNicHandleAccount(username, 114L, new TestOpInfo(username, "Set account"));
        verifyExported(username, false);
        registerDomain(username);
        verifyExported(username, true);
    }

    @Test
    public void exportForTransfer() throws Exception {
        String username = createDirectAccount();
        verifyExported(username, false);
        transferDomain(username);
        verifyExported(username, true);
    }

    @Test
    public void exportForTopUp() throws Exception {
        String username = createDirectAccount();
        verifyExported(username, false);
        topUp(username);
        verifyExported(username, true);
    }

    private String createDirectAccount() throws Exception {
        NewAccount newAcc = nicHandleService.createDirectAccount("Test User", "A Company", "email@email.xxx",
                "1 Main Street", 121, 14, Arrays.asList("11111111"), Arrays.asList("11111111"), null,
                new TestOpInfo("test", "remark"), "Passw0rd!", false, false);
        return newAcc.getNicHandleId();
    }

    private void verifyExported(String username, boolean exported) throws Exception {
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(username);
        AssertJUnit.assertEquals(exported, nicHandle.isExported());
    }

    private void registerDomain(String username) throws Exception {
        AuthenticatedUser user = authService.authenticate(username, "Passw0rd!", false, "1.1.1.1", false, null, true,
                "ws");
        RegistrationRequestVO request = Helper.createBasicCreateRequest("new-domain.ie", username);
        request.setPeriod(2);
        CreditCardVO creditCard = createBasicCreditCard();
        commonAppService.registerDomain(user, request, creditCard.toCreditCard());
    }

    private void transferDomain(String username) throws Exception {
        AuthenticatedUser user = authService.authenticate(username, "Passw0rd!", false, "1.1.1.1", false, null, true,
                "ws");
        String domainName = "transferDomainUC006SC01.ie";
        AuthCode authcode = domainService.getOrCreateAuthCode(domainName, TestOpInfo.DEFAULT);
        TransferRequestVO request = Helper.createBasicTransferRequest(domainName, username, false, 2,
                authcode.getAuthCode());
        CreditCardVO creditCard = createBasicCreditCard();
        commonAppService.transfer(user, request, creditCard.toCreditCard());
    }

    private void topUp(String username) throws Exception {
        AuthenticatedUser user = authService.authenticate(username, "Passw0rd!", false, "1.1.1.1", false, null, true,
                "ws");
        CreditCardVO creditCard = createBasicCreditCard();
        depositService.depositFunds(user, TestOpInfo.DEFAULT, username, BigDecimal.valueOf(1000),
                creditCard.toCreditCard());
    }

}
