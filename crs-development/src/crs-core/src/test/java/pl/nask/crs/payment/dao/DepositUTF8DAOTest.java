package pl.nask.crs.payment.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.commons.MoneyUtils;

public class DepositUTF8DAOTest extends AbstractContextAwareTest {

    @Resource
    DepositDAO depositDAO;

    @Resource
    HistoricalDepositDAO historicalDepositDAO;

    @Test
    public void testGetUnnormalizedDepositByNicHandle() {
        // Check if internalDeposit resultMap has string handlers (all except nicHandleId).
        final String nicHandleId = "ÜIDL2-IEDR";
        Assert.assertTrue(depositDAO.lock(nicHandleId));
        Deposit dbDeposit = depositDAO.get(nicHandleId);
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getNicHandleId(), nicHandleId);
        Assert.assertEquals(dbDeposit.getNicHandleName(), "ÜIRISH DOMAINS LTD");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ü20101202171646-D-102");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), nicHandleId);
        Assert.assertEquals(dbDeposit.getRemark(), "ÜNormalized NicHandle");
    }

    @Test
    public void testGetUnnormalizedDepositByCorrectorNicHandle() {
        // Check if nicHandleId in internalDeposit resultMap has string handler.
        String nicHandleId = "ÄIDL2-IEDR";
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setCorrectorNH(nicHandleId);
        List<Deposit> results = depositDAO.find(criteria, 0, 5, null).getResults();
        Assert.assertEquals(results.size(), 1);
        Deposit dbDeposit = results.get(0);
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getNicHandleId(), nicHandleId);
        Assert.assertEquals(dbDeposit.getNicHandleName(), "ÄIRISH DOMAINS LTD");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ä20101202171646-D-102");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), nicHandleId);
        Assert.assertEquals(dbDeposit.getRemark(), "ÄUnnormalized NicHandle");
    }

    @Test
    public void testGetDepositByUnnormalizedNicHandle() {
        // Check if getDepositByNicHandleId select has string handlers.
        String nicHandleId = "E\u0308IDL2-IEDR";
        Assert.assertTrue(depositDAO.lock(nicHandleId));
        Deposit dbDeposit = depositDAO.get(nicHandleId);
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getRemark(), "ËNormalized Deposit");
    }

    @Test
    public void testFindDepositByUnnormalizedCriteria() {
        // Check if depositCriteria sql frag has string handlers (used in findDeposits, countFindDeposits,
        // findDepositWithHistory, countFindDepositWithHistory selects).
        String nicHandleId = "E\u0308IDL2-IEDR";
        String nicHandleIdNfc = "ËIDL2-IEDR";
        String remark = "E\u0308Normalized Deposit";
        String remarkNfc = "ËNormalized Deposit";
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setCorrectorNH(nicHandleId);
        criteria.setAccountBillNH(nicHandleId);
        criteria.setNicHandleId(nicHandleId);
        List<Deposit> results = depositDAO.find(criteria, 0, 5, null).getResults();
        Assert.assertEquals(results.size(), 1);
        Deposit dbDeposit = results.get(0);
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getNicHandleId(), nicHandleIdNfc);
        Assert.assertEquals(dbDeposit.getCorrectorNH(), nicHandleIdNfc);
        Assert.assertEquals(dbDeposit.getRemark(), remarkNfc);
    }

    @Test
    public void testFindHistDepositByUnnormalizedCriteria() {
        // Check if depositHistCriteria sql frag has string handlers (used in findHistory, countFindHistory selects).
        String nicHandleId = "ËIDL2-IEDR";
        String remark = "ËNormalized Deposit";
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setCorrectorNH(nicHandleId);
        criteria.setAccountBillNH(nicHandleId);
        criteria.setNicHandleId(nicHandleId);
        List<Deposit> results = depositDAO.find(criteria, 0, 5, null).getResults();
        Assert.assertEquals(results.size(), 1);
        Deposit dbDeposit = results.get(0);
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getNicHandleId(), nicHandleId);
        Assert.assertEquals(dbDeposit.getCorrectorNH(), nicHandleId);
        Assert.assertEquals(dbDeposit.getRemark(), remark);
    }

    @Test
    public void testInsertUnnormalizedDeposit() {
        // Check if insertDeposit insert has string handlers.
        Deposit deposit = Deposit.newInstance("I\u0308IDL2-IEDR", new Date(), MoneyUtils.getRoundedAndScaledValue(500),
                MoneyUtils.getRoundedAndScaledValue(500), MoneyUtils.getRoundedAndScaledValue(500),
                DepositTransactionType.INIT, "I\u030820101202171646-D-102", "I\u0308IDL2-IEDR",
                "I\u0308New Normalized Deposit");
        depositDAO.create(deposit);
        Deposit dbDeposit = depositDAO.get("ÏIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getNicHandleId(), "ÏIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), "ÏIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ï20101202171646-D-102");
        Assert.assertEquals(dbDeposit.getRemark(), "ÏNew Normalized Deposit");
    }

    @Test
    public void testUpdateDepositWithUnnormalizedData() {
        // Check if updateDeposit update has string handlers.
        Deposit deposit = Deposit.newInstance("E\u0308IDL2-IEDR", new Date(), MoneyUtils.getRoundedAndScaledValue(500),
                MoneyUtils.getRoundedAndScaledValue(500), MoneyUtils.getRoundedAndScaledValue(500),
                DepositTransactionType.INIT, "E\u030820101202171646-D-999", "E\u0308AAAA-IEDR",
                "E\u0308Updated Deposit");
        depositDAO.updateUsingHistory(historicalDepositDAO.create(deposit, deposit.getTransactionDate(),
                deposit.getCorrectorNH()));
        Deposit dbDeposit = depositDAO.get("ËIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getNicHandleId(), "ËIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), "ËAAAA-IEDR");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ë20101202171646-D-999");
        Assert.assertEquals(dbDeposit.getRemark(), "ËUpdated Deposit");
    }

}
