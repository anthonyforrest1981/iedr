package pl.nask.crs.payment.service;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.DepositBelowLimitException;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.DepositOverLimitException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareDeposit;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareNewDeposit;

public class DepositTest extends AbstractContextAwareTest {

    @Resource
    DepositService depositService;

    @Resource
    PaymentService paymentService;

    @Resource
    NicHandleSearchService nicHandleSearchService;

    @Resource
    DomainDAO domainDAO;

    @Resource
    HistoricalDomainDAO historicalDomainDAO;

    @Test
    public void viewDepositTest() throws Exception {
        ExtendedDeposit deposit = depositService.viewDeposit("APITEST-IEDR");
        AssertJUnit.assertEquals("APITEST-IEDR", deposit.getNicHandleId());
        AssertJUnit.assertEquals("open balance", MoneyUtils.getRoundedAndScaledValue(7000), deposit.getOpenBal());
        AssertJUnit.assertEquals("close balance", MoneyUtils.getRoundedAndScaledValue(2594.0), deposit.getCloseBal());
        AssertJUnit.assertEquals("reserved funds", MoneyUtils.getRoundedAndScaledValue(206.38),
                deposit.getReservedFunds());
        AssertJUnit.assertEquals("close balance including reservations", MoneyUtils.getRoundedAndScaledValue(2387.62),
                deposit.getCloseBalMinusReservations());
    }

    @Test
    public void initDepositOKTest() throws Exception {
        String depositId = "APIT4-IEDR";
        Deposit newDeposit = depositService.initDeposit(depositId);
        Deposit fromDB = depositService.viewDeposit(depositId);
        compareDeposit(newDeposit, fromDB);
        compareDeposit(fromDB, getHistoricalDeposit(depositId));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void initDepositExistingTest() {
        depositService.initDeposit("APITEST-IEDR");
    }

    @Test
    public void makeDepositCorrectionUp() throws Exception {
        String depositId = "APIT5-IEDR";
        String correctorNh = "APITEST-IEDR";
        String remark = "payment by a cheque";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(10.51);
        ExtendedDeposit deposit = depositService.viewDeposit(depositId);
        Deposit expectedDeposit = Deposit.newInstance(deposit.getNicHandleId(), new Date(), deposit.getCloseBal(),
                deposit.getCloseBal().add(amount), amount, DepositTransactionType.MANUAL, null, correctorNh, remark);
        Deposit newDeposit = depositService.correctDeposit(new TestOpInfo(correctorNh), depositId, amount, remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test
    public void makeDepositCorrectionDown() throws Exception {
        String depositId = "APIT5-IEDR";
        String correctorNh = "APITEST-IEDR";
        String remark = "payment by a cheque";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(-13.89);
        ExtendedDeposit deposit = depositService.viewDeposit(depositId);
        Deposit expectedDeposit = Deposit.newInstance(deposit.getNicHandleId(), new Date(), deposit.getOpenBal(),
                deposit.getCloseBal().add(amount), amount, DepositTransactionType.MANUAL, null, correctorNh, remark);
        Deposit newDeposit = depositService.correctDeposit(new TestOpInfo(correctorNh), depositId, amount, remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void makeDepositCorrectionDownInsufficientFunds() throws Exception {
        String depositId = "APIT5-IEDR";
        String correctorNh = "APITEST-IEDR";
        ExtendedDeposit deposit = depositService.viewDeposit(depositId);
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(deposit.getCloseBal().add(BigDecimal.ONE).negate());
        depositService.correctDeposit(new TestOpInfo(correctorNh), depositId, amount, "payment by a cheque");
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void makeDepositCorrectionDownInsufficientFundsDueToUnsettledReservations() throws Exception {
        String nicHandleId = "APITEST-IEDR";
        String correctorNh = "APITEST-IEDR";
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        ExtendedDeposit deposit = depositService.viewDeposit(nicHandleId);
        BigDecimal amountWithReservations = deposit.getCloseBalMinusReservations();
        // make a reservation to make sure the available funds are lower than the closing balance
        Map<String, Period> domainsWithPeriods = new HashMap<String, Period>();
        String dname = "payDomain.ie";
        domainsWithPeriods.put(dname, Period.fromYears(1));
        Domain domain = domainDAO.get(dname);
        OpInfo opInfo = new OpInfo(nicHandle);
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, 17, new Date(), nicHandleId));
        paymentService.authorizePaymentForRenewal(null, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, true);
        Deposit newDeposit = depositService.correctDeposit(new TestOpInfo(correctorNh), nicHandleId,
                amountWithReservations.negate(), "payment by a cheque");
        AssertJUnit.fail("NotEnoughDepositFundsException expected to be thrown, instead got " + newDeposit);
    }

    @Test
    public void makeDepositCorrectionUpNewDeposit() throws Exception {
        String depositId = "KCB1-IEDR";
        String correctorNh = "APITEST-IEDR";
        String remark = "payment by a cheque";
        try {
            depositService.viewDeposit(depositId);
            AssertJUnit.fail("There supposed to be no deposit with id=" + depositId);
        } catch (DepositNotFoundException e) {
            // this was expected!
        }
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(10.59);
        Deposit expectedDeposit = Deposit.newInstance(depositId, new Date(), amount, amount, amount,
                DepositTransactionType.MANUAL, null, correctorNh, remark);
        Deposit newDeposit = depositService.correctDeposit(new TestOpInfo(correctorNh), depositId, amount, remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test(expectedExceptions = DepositOverLimitException.class)
    public void depositFundsOverLimitTest() throws Exception {
        String correctorNh = "APITEST-IEDR";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(100005.98);
        CreditCard card = new CreditCard("1234567899", "05/2020", "Visa", "Test", "123", 1);
        depositService.depositFunds(null, new TestOpInfo(correctorNh), "APIT5-IEDR", amount, card);
    }

    @Test(expectedExceptions = DepositBelowLimitException.class)
    public void depositFundsBelowLimitTest() throws Exception {
        String correctorNh = "APITEST-IEDR";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(105.98);
        CreditCard card = new CreditCard("1234567899", "05/2020", "Visa", "Test", "123", 1);
        depositService.depositFunds(null, new TestOpInfo(correctorNh), "APIT5-IEDR", amount, card);
    }

    @Test
    public void depositFundsOfflineTest() throws Exception {
        String depositId = "APIT5-IEDR";
        String correctorNh = "APITEST-IEDR";
        String remark = "payment by a cheque";
        ExtendedDeposit deposit = depositService.viewDeposit(depositId);
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(1005.99);
        Deposit expectedDeposit = Deposit.newInstance(depositId, new Date(), deposit.getCloseBal(),
                deposit.getCloseBal().add(amount), amount, DepositTransactionType.TOPUP, null, correctorNh, remark);
        Deposit newDeposit = depositService.depositFundsOffline(null, new TestOpInfo(correctorNh), depositId, amount,
                remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test
    public void depositFundsOfflineUpNewDepositTest() throws Exception {
        String depositId = "KCB1-IEDR";
        String correctorNh = "APITEST-IEDR";
        String remark = "payment by a cheque";
        try {
            depositService.viewDeposit(depositId);
            AssertJUnit.fail("There supposed to be no deposit with id=" + depositId);
        } catch (DepositNotFoundException e) {
            // this was expected!
        }
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(1006.99);
        Deposit expectedDeposit = Deposit.newInstance(depositId, new Date(), amount, amount, amount,
                DepositTransactionType.TOPUP, null, correctorNh, remark);
        Deposit newDeposit = depositService.depositFundsOffline(null, new TestOpInfo(correctorNh), depositId, amount,
                remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test(expectedExceptions = DepositOverLimitException.class)
    public void depositFundsOfflineOverLimitTest() throws Exception {
        String correctorNh = "APITEST-IEDR";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(100005.99);
        depositService.depositFundsOffline(null, new TestOpInfo(correctorNh), "APIT5-IEDR", amount, "remark");
    }

    @Test(expectedExceptions = DepositBelowLimitException.class)
    public void depositFundsOfflineBelowLimitTest() throws Exception {
        String correctorNh = "APITEST-IEDR";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(100.99);
        depositService.depositFundsOffline(null, new TestOpInfo(correctorNh), "APIT5-IEDR", amount, "remark");
    }

    @Test
    public void reduceDepositTest() throws Exception {
        String depositId = "APIT5-IEDR";
        BigDecimal amount = MoneyUtils.getRoundedAndScaledValue(1001.95);
        String orderId = "orderId";
        String correctorNh = "APITEST-IEDR";
        String remark = "Correction";
        ExtendedDeposit deposit = depositService.viewDeposit(depositId);
        Deposit expectedDeposit = Deposit.newInstance(depositId, new Date(), deposit.getOpenBal(),
                deposit.getCloseBal().subtract(amount), amount.negate(), DepositTransactionType.MANUAL, orderId, correctorNh, remark);
        Deposit newDeposit = depositService.reduceDeposit(depositId, amount, orderId, DepositTransactionType.MANUAL,
                correctorNh, remark);
        compareDepositsAfterOperation(newDeposit, expectedDeposit);
    }

    @Test
    public void getTopUpHistoryTest() {
        LimitedSearchResult<DepositTopUp> depositTopUps = depositService.getTopUpHistory("X-IEDR", new Date(106, 4, 1),
                new Date(), 0, 10);
        AssertJUnit.assertEquals(5, depositTopUps.getTotalResults());

        depositTopUps = depositService.getTopUpHistory("X-IEDR", new Date(106, 4, 1), new Date(), 1, 1);
        AssertJUnit.assertEquals(5, depositTopUps.getTotalResults());
        DepositTopUp depositTopUp = depositTopUps.getResults().get(0);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(600), depositTopUp.getTopUpAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(3100), depositTopUp.getClosingBalance());
    }

    @Test
    public void findHistoryWithNoLimit() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("IDL2-IEDR");
        LimitedSearchResult<Deposit> searchResult = depositService.findHistoricalDeposits(criteria, 0, 0, null);
        AssertJUnit.assertEquals(420, searchResult.getTotalResults());
        AssertJUnit.assertEquals(420, searchResult.getResults().size());
    }

    private void compareDepositsAfterOperation(Deposit depositFromService, Deposit expectedDeposit) throws Exception {
        String depositId = depositFromService.getNicHandleId();
        Deposit dbDeposit = depositService.viewDeposit(depositId);
        Deposit histDeposit = getHistoricalDeposit(depositId);
        compareNewDeposit(expectedDeposit, depositFromService);
        compareDeposit(depositFromService, dbDeposit);
        compareDeposit(dbDeposit, histDeposit);
    }

    private Deposit getHistoricalDeposit(String depositId) {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId(depositId);
        List<Deposit> history = depositService.findHistoricalDeposits(criteria, 0, 1,
                Arrays.asList(new SortCriterion("histChangeDate", false))).getResults();
        AssertJUnit.assertEquals(String.format("No history found for deposit %s", depositId), 1, history.size());
        return history.get(0);
    }

}
