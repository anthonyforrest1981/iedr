package pl.nask.crs.api.payment;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.PaymentSummaryVO;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.security.authentication.AuthenticationException;

import static pl.nask.crs.api.Helper.createBasicCreditCard;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class UC004MakePaymentForExistingDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSPaymentAppService crsPaymentAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    AuthenticatedUserVO user;

    @BeforeMethod
    public void authenticate() throws IllegalArgumentException, AuthenticationException, IncorrectUtf8FormatException {
        user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void SC03Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 2),
                new DomainWithPeriodVO("payDomain2.ie", 2));
        CreditCardVO creditCard = createBasicCreditCard();
        PaymentSummaryVO summaryVO =
                crsPaymentAppService.payForDomainRenewal(user, domains, PaymentMethod.CC, creditCard);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(80), summaryVO.getAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(16), summaryVO.getVat());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(96), summaryVO.getTotal());
        Assert.assertNotNull(summaryVO.getOrderId());
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void SC07Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomain3.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domains, PaymentMethod.ADP, null);
    }

    @Test
    public void SC17Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomainLocked.ie", 2));
        PaymentSummaryVO summaryVO = crsPaymentAppService.payForDomainRenewal(user, domains, PaymentMethod.ADP, null);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(40), summaryVO.getAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(8), summaryVO.getVat());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(48), summaryVO.getTotal());
        Assert.assertNotNull(summaryVO.getOrderId());
    }

    @Test
    public void SC18Test() throws Exception {
        List<DomainWithPeriodVO> domains = Arrays.asList(new DomainWithPeriodVO("payDomainLockedAutorenew.ie", 2));
        PaymentSummaryVO summaryVO = crsPaymentAppService.payForDomainRenewal(user, domains, PaymentMethod.ADP, null);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(40), summaryVO.getAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(8), summaryVO.getVat());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(48), summaryVO.getTotal());
        Assert.assertNotNull(summaryVO.getOrderId());
    }

}
