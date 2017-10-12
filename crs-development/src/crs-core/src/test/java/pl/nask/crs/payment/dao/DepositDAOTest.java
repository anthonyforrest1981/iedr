package pl.nask.crs.payment.dao;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareDeposit;

public class DepositDAOTest extends AbstractContextAwareTest {

    @Resource
    DepositDAO depositDAO;

    @Resource
    HistoricalDepositDAO historicalDepositDAO;

    @Test
    public void getDepositTest() {
        final String nicHandleId = "IDL2-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L),
                MoneyUtils.getRoundedAndScaledValue(7000), MoneyUtils.getRoundedAndScaledValue(2594),
                MoneyUtils.getRoundedAndScaledValue(-100), DepositTransactionType.SETTLEMENT, "20101202171646-D-102",
                null, null);
        AssertJUnit.assertTrue(depositDAO.lock(nicHandleId));
        Deposit dbDeposit = depositDAO.get(nicHandleId);

        compareDeposit(deposit, dbDeposit);
        AssertJUnit.assertEquals("IRISH DOMAINS LTD", dbDeposit.getNicHandleName());
    }

    @Test
    public void updateDepositUsingHistoryTest() {
        final String nicHandleId = "IDL2-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L),
                MoneyUtils.getRoundedAndScaledValue(1000), MoneyUtils.getRoundedAndScaledValue(594),
                MoneyUtils.getRoundedAndScaledValue(500), DepositTransactionType.TOPUP, "20101202171646-D-202",
                null, null);
        depositDAO.updateUsingHistory(historicalDepositDAO.create(deposit, deposit.getTransactionDate(),
                deposit.getCorrectorNH()));
        Deposit updated = depositDAO.get(nicHandleId);

        compareDeposit(deposit, updated);

        deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L), MoneyUtils.getRoundedAndScaledValue(1000),
                MoneyUtils.getRoundedAndScaledValue(594), MoneyUtils.getRoundedAndScaledValue(500),
                DepositTransactionType.MANUAL, "20101202171646-D-202", "correctorNH", "corrector remark");
        depositDAO.updateUsingHistory(historicalDepositDAO.create(deposit, deposit.getTransactionDate(),
                deposit.getCorrectorNH()));
        updated = depositDAO.get(nicHandleId);

        compareDeposit(deposit, updated);
    }

    @Test
    public void createDepositTest() {
        final String nicHandleId = "DTEST-IEDR";
        Deposit deposit = Deposit.newInstance(nicHandleId, new Date(1215502925999L),
                MoneyUtils.getRoundedAndScaledValue(500), MoneyUtils.getRoundedAndScaledValue(500),
                MoneyUtils.getRoundedAndScaledValue(500), DepositTransactionType.INIT, "20101202171646-D-203",
                null, "init remark");
        depositDAO.create(deposit);
        Deposit dbDeposit = depositDAO.get(nicHandleId);

        compareDeposit(deposit, dbDeposit);
    }

    @Test
    public void findTest() {
        LimitedSearchResult<Deposit> searchResult = depositDAO.find(null, 0, 5,
                Arrays.asList(new SortCriterion("nicHandleId", true)));
        AssertJUnit.assertEquals(11, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());
        AssertJUnit.assertEquals("AAE553-IEDR", searchResult.getResults().get(0).getNicHandleId());

        searchResult = depositDAO.find(null, 0, 5, Arrays.asList(new SortCriterion("nicHandleId", false)));
        AssertJUnit.assertEquals("X-IEDR", searchResult.getResults().get(0).getNicHandleId());

        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("SWD2-IEDR");
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
        AssertJUnit.assertEquals("SWD2-IEDR", searchResult.getResults().get(0).getNicHandleId());

        criteria = new DepositSearchCriteria();
        criteria.setTransactionDateFrom(new Date(108, 0, 1));
        criteria.setTransactionDateTo(new Date(108, 7, 1));
        searchResult = depositDAO.find(criteria, 0, 10, null);
        AssertJUnit.assertEquals(8, searchResult.getTotalResults());
        AssertJUnit.assertEquals(8, searchResult.getResults().size());

        criteria = new DepositSearchCriteria();
        criteria.setAccountBillNH("APITEST-IEDR");
        searchResult = depositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
    }

    @Test
    public void countTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("AAE553-IEDR");
        AssertJUnit.assertEquals(1, depositDAO.count(criteria));
    }

    @Test
    public void existsTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("AAE553-IEDR");
        AssertJUnit.assertTrue(depositDAO.exists(criteria));
    }

    @Test
    public void notExistsTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("AHL863-IEDR");
        AssertJUnit.assertFalse(depositDAO.exists(criteria));
    }

}
