package pl.nask.crs.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class DomainAutorenewTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    ServicesRegistry services;

    @Resource
    PaymentService paymentService;

    @Resource
    AuthenticationService authenticationService;

    @Resource
    DomainDAO domainDAO;

    @Resource
    HistoricalDomainDAO historicalDomainDAO;

    @Resource
    DepositDAO depositDAO;

    @Resource
    ReservationDAO reservationDAO;

    int renewOnceState = 49;
    int autorenewState = 81;
    int noAutoRenew = 17;

    final String domainName = "suka.ie";
    final String billingNh = "HIA1-IEDR";

    AuthenticatedUser user;
    AuthenticatedUser techUser;
    Domain domain;

    @BeforeMethod
    public void clear() throws Exception {
        user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true,
                "ws");
        techUser = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true,
                "ws");
        domain = null;
    }

    @Test
    public void renewOnceTest() throws Exception {
        initDomain(renewOnceState);
        assertRenewalMode(RenewalMode.RenewOnce);
        assertReservation(domainName, false);
        topUpDeposit();
        checkRenewSuccessful();
    }

    @Test
    public void renewAutoTest() throws Exception {
        initDomain(autorenewState);
        assertRenewalMode(RenewalMode.Autorenew);
        assertReservation(domainName, false);
        topUpDeposit();
        checkRenewSuccessful();
    }

    private void topUpDeposit() {
        depositDAO.create(Deposit.newInstance(billingNh, new Date(), BigDecimal.ZERO,
                MoneyUtils.getRoundedAndScaledValue(10000), MoneyUtils.getRoundedAndScaledValue(10000),
                DepositTransactionType.INIT, "a", null, null));
    }

    @Test(expectedExceptions = DomainIncorrectStateForPaymentException.class)
    public void renewNoAuto() throws Exception {
        initDomain(noAutoRenew);
        assertRenewalMode(RenewalMode.NoAutorenew);
        assertReservation(domainName, false);
        checkRenewUnSuccessful();
    }

    // tests #12080
    @Test
    public void renewDomainWithHyphensInName() throws Exception {
        // having a domain with '-' in its name
        String newDomainName = "trans-o-flex.ie";
        createDomain(newDomainName);
        topUpDeposit();
        // the autorenewal for this domain should end with success
        checkRenewSuccessful(newDomainName);
    }

    private void createDomain(String newDomainName) {
        Domain existingDomain = domainDAO.get(domainName);
        existingDomain.setName(newDomainName);
        existingDomain.setRenewalDate(new Date());
        domainDAO.create(existingDomain);
        // the domain needs to be active with Autorenew
        domainDAO.updateUsingHistory(historicalDomainDAO.create(existingDomain, autorenewState, new Date(),
                TestOpInfo.DEFAULT.getActorName()));
        domain = domainDAO.get(newDomainName);
    }

    @Test
    public void domainWithHypensInNameIsTakenForAutorenewal() throws Exception {
        String newDomainName = "trans-o-flex.ie";
        createDomain(newDomainName);
        // when searching for the domain
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewalTo(new Date());
        criteria.setDomainRenewalModes(RenewalMode.Autorenew, RenewalMode.RenewOnce);
        int offset = 0;
        final int limit = 100;
        List<String> domains = services.getDomainSearchService().findDomainNames(criteria, offset, limit);
        // the domain should be on the list!
        Assert.assertFalse(domains.isEmpty());
        for (String domain : domains) {
            if (newDomainName.equalsIgnoreCase(domain)) {
                // success, end test
                return;
            }
        }
        Assert.fail("Domain " + newDomainName + " not found in the results: " + domains);
    }

    private void checkRenewUnSuccessful() throws Exception {
        paymentService.autorenew(techUser, domainName);
        AssertJUnit.fail("autorenew should not succeed");
    }

    private void checkRenewSuccessful() throws Exception {
        checkRenewSuccessful(domainName);
    }

    private void checkRenewSuccessful(String domainName) throws Exception {
        PaymentSummary res = paymentService.autorenew(techUser, domainName);
        assertReservation(domainName, true);
        AssertJUnit.assertNotNull(res);
    }

    private void assertReservation(String domainName, boolean exists) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH(billingNh);
        criteria.setDomainName(domainName);
        AssertJUnit.assertEquals(exists, reservationDAO.exists(criteria));
    }

    private void assertRenewalMode(RenewalMode mode) {
        AssertJUnit.assertEquals("renewal mode", mode, domain.getDsmState().getRenewalMode());
    }

    private void initDomain(int state) {
        domain = domainDAO.get(domainName);
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, state, new Date(),
                TestOpInfo.DEFAULT.getActorName()));
        domain = domainDAO.get(domainName);
    }

}
