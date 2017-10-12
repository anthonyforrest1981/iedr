package pl.nask.crs.payment.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.HistoricalDepositSearchCriteria;

public class HistoricalDepositUTF8DAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalDepositDAO historicalDepositDAO;

    @Test
    public void testGetUnnormalizedDepositByCorrectorNicHandle() {
        // Check if nicHandleId in histInternalDeposit resultMap has string handler.
        String nicHandleId = "ÄIDL2-IEDR";
        HistoricalDepositSearchCriteria criteria = new HistoricalDepositSearchCriteria();
        criteria.setCorrectorNH(nicHandleId);
        List<HistoricalObject<Deposit>> results = historicalDepositDAO.find(criteria, 0, 5, null).getResults();
        Assert.assertEquals(results.size(), 1);
        Deposit dbDeposit = results.get(0).getObject();
        Assert.assertNotNull(dbDeposit);
        Assert.assertEquals(dbDeposit.getNicHandleId(), nicHandleId);
        Assert.assertEquals(dbDeposit.getNicHandleName(), "ÄIRISH DOMAINS LTD");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ä20101202171646-D-102");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), nicHandleId);
        Assert.assertEquals(dbDeposit.getRemark(), "ÄUnnormalized NicHandle");
    }

    @Test
    public void testUpdateDepositWithUnnormalizedData() {
        BigDecimal decimal = BigDecimal.valueOf(500);
        Deposit deposit = Deposit.newInstance("E\u0308IDL2-IEDR", new Date(), decimal, decimal, decimal,
                DepositTransactionType.INIT, "E\u030820101202171646-D-999", "E\u0308AAAA-IEDR",
                "E\u0308Updated Deposit");
        long changeId = historicalDepositDAO.create(deposit, deposit.getTransactionDate(), deposit.getCorrectorNH());
        HistoricalDepositSearchCriteria criteria = new HistoricalDepositSearchCriteria();
        criteria.setNicHandleId("ËIDL2-IEDR");
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("histChangeDate", false));
        HistoricalObject<Deposit> histDeposit = historicalDepositDAO.find(criteria, 0, 1, sortBy).getResults().get(0);
        Assert.assertEquals(histDeposit.getChangeId(), changeId);
        Deposit dbDeposit = histDeposit.getObject();
        Assert.assertEquals(dbDeposit.getNicHandleId(), "ËIDL2-IEDR");
        Assert.assertEquals(dbDeposit.getCorrectorNH(), "ËAAAA-IEDR");
        Assert.assertEquals(dbDeposit.getOrderId(), "Ë20101202171646-D-999");
        Assert.assertEquals(dbDeposit.getRemark(), "ËUpdated Deposit");
    }

}
