package pl.nask.crs.api.payment;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.HistoricalDepositDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class UC005AutorenewTest extends AbstractTransactionalTestNGSpringContextTests {
    @Resource
    DomainDAO domainDAO;

    @Resource
    HistoricalDomainDAO historicalDomainDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    PaymentAppService paymentAppService;

    @Resource
    DepositDAO depositDao;

    @Resource
    HistoricalDepositDAO historicalDepositDao;

    @Resource
    CRSAuthenticationService authService;

    static DsmState autorenewState = dsmState(81, RenewalMode.Autorenew, NRPStatus.Active, false);
    static DsmState renewOnceState = dsmState(49, RenewalMode.RenewOnce, NRPStatus.Active, false);
    static DsmState lockedState = dsmState(1, RenewalMode.NoAutorenew, NRPStatus.Active, true);;
    static DsmState autorenewInvMailedState = dsmState(82, RenewalMode.Autorenew, NRPStatus.InvoluntaryMailed, false);;
    static DsmState autorenewInvSuspendedState = dsmState(83, RenewalMode.Autorenew, NRPStatus.InvoluntarySuspended,
            false);;

    Domain domain;
    final String domainName = "suka.ie";
    final String billingNh = "HIA1-IEDR";
    AuthenticatedUserVO user;

    @BeforeMethod
    public void prepare() throws Exception {
        user = authService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        topUpDeposit();
    }

    @Test
    public void sc01renewOnceHP() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(renewOnceState);
        paymentAppService.autorenewAll(user);
        checkReservation(true);
    }

    @Test
    public void sc02autorenewHP() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(autorenewState);
        paymentAppService.autorenewAll(user);
        checkReservation(true);
    }

    @Test
    public void sc04autorenewInvMailed() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(autorenewInvMailedState);
        paymentAppService.autorenewAll(user);
        checkReservation(true);
    }

    @Test
    public void sc05autorenewInvSusp() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(autorenewInvSuspendedState);
        paymentAppService.autorenewAll(user);
        checkReservation(true);
    }

    @Test
    public void sc06locked() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(lockedState);
        paymentAppService.autorenewAll(user);
        checkReservation(false);
    }

    @Test
    public void sc09paymentFails() throws AccessDeniedException, IncorrectUtf8FormatException {
        initDomain(renewOnceState);
        clearDeposit();
        paymentAppService.autorenewAll(user);
        checkReservation(false);
    }

    private void clearDeposit() {
        Deposit cleared = Deposit.newInstance(billingNh, new Date(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                DepositTransactionType.MANUAL, "2", "nh", "clear");
        depositDao.updateUsingHistory(historicalDepositDao.create(cleared, cleared.getTransactionDate(),
                cleared.getCorrectorNH()));
    }

    private void initDomain(DsmState state) {
        domain = domainDAO.get(domainName);
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, state.getId(), new Date(),
                TestOpInfo.DEFAULT.getActorName()));
        domain = domainDAO.get(domainName);
        AssertJUnit.assertEquals("renewal mode", state.getRenewalMode(), domain.getDsmState().getRenewalMode());
        AssertJUnit.assertEquals("locked", state.getLocked(), domain.getDsmState().getLocked());
        AssertJUnit.assertEquals("nrp status", state.getNrpStatus(), domain.getDsmState().getNrpStatus());
    }

    private void checkReservation(boolean exists) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH(billingNh);
        criteria.setDomainName(domainName);
        AssertJUnit.assertEquals(exists, reservationDAO.exists(criteria));
    }

    private void topUpDeposit() {
        depositDao.create(Deposit.newInstance(billingNh, new Date(), BigDecimal.ZERO,
                MoneyUtils.getRoundedAndScaledValue(10000), MoneyUtils.getRoundedAndScaledValue(10000),
                DepositTransactionType.TOPUP, "a", null, null));
    }

    static DsmState dsmState(int id, RenewalMode mode, NRPStatus nrpStatus, boolean locked) {
        return new DsmTestState(id, mode, nrpStatus, locked);
    }

    static class DsmTestState extends DsmState {

        public DsmTestState(int id, RenewalMode mode, NRPStatus nrpStatus, boolean locked) {
            super(id);
            setRenewalMode(mode);
            setNrpStatus(nrpStatus);
            setLocked(locked);
        }
    }
}
