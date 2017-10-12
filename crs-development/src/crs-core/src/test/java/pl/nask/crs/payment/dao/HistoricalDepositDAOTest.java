package pl.nask.crs.payment.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositTransactionType;
import pl.nask.crs.payment.HistoricalDepositSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareDeposit;

public class HistoricalDepositDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalDepositDAO historicalDepositDAO;

    @Test
    public void createHistoricalDepositTest() {
        final String nicHandleId = "X-IEDR";
        Date date = new Date();
        BigDecimal decimal = BigDecimal.valueOf(500);
        Deposit deposit = Deposit.newInstance(nicHandleId, date, decimal, decimal, decimal,
                DepositTransactionType.INIT, "20101202171646-D-203", null, "init remark");
        long changeId = historicalDepositDAO.create(deposit, date, null);
        HistoricalDepositSearchCriteria criteria = new HistoricalDepositSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        HistoricalObject<Deposit> histDeposit = historicalDepositDAO.find(criteria,
                Arrays.asList(new SortCriterion("histChangeDate", false))).getResults().get(0);
        assertEquals(histDeposit.getChangeId(), changeId);
        assertEquals(histDeposit.getChangeDate(), DateUtils.truncate(date, Calendar.SECOND));
        assertNull(histDeposit.getChangedBy());

        compareDeposit(deposit, histDeposit.getObject());
    }

    @Test
    public void findHistoryTest() {
        HistoricalDepositSearchCriteria criteria = new HistoricalDepositSearchCriteria();
        criteria.setNicHandleId("IDL2-IEDR");
        LimitedSearchResult<HistoricalObject<Deposit>> searchResult = historicalDepositDAO.find(criteria, 0, 5, null);
        AssertJUnit.assertEquals(420, searchResult.getTotalResults());
        AssertJUnit.assertEquals(5, searchResult.getResults().size());

        criteria = new HistoricalDepositSearchCriteria();
        criteria.setNicHandleId("SWD2-IEDR");
        searchResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("id", true)));
        AssertJUnit.assertEquals(21, searchResult.getTotalResults());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(1800),
                searchResult.getResults().get(0).getObject().getCloseBal());
        searchResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("id", false)));
        AssertJUnit.assertEquals(21, searchResult.getTotalResults());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(46.2),
                searchResult.getResults().get(1).getObject().getCloseBal());

        criteria.setTransactionDateFrom(new Date(108, 7, 1));
        searchResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("id", true)));
        AssertJUnit.assertEquals(7, searchResult.getTotalResults());
    }

    @Test
    public void findHistorySortingTest() {
        HistoricalDepositSearchCriteria criteria = new HistoricalDepositSearchCriteria();
        criteria.setNicHandleId("IDL2-IEDR");
        // should ignore bogus sortBy
        LimitedSearchResult<HistoricalObject<Deposit>> ascResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", true)));
        LimitedSearchResult<HistoricalObject<Deposit>> descResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", false)));
        AssertJUnit.assertTrue(ascResult.getResults().get(0).getChangeId() == descResult.getResults().get(0).getChangeId());
        // should ignore unknown sort field
        ascResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", true)));
        descResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", false)));
        AssertJUnit.assertTrue(ascResult.getResults().get(0).getChangeId() == descResult.getResults().get(0).getChangeId());
        //
        ascResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", true)));
        descResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", false)));
        AssertJUnit.assertTrue(ascResult.getResults().get(0).getChangeId() == descResult.getResults().get(0).getChangeId());
        //
        ascResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", true)));
        descResult = historicalDepositDAO.find(criteria, 0, 5, Arrays.asList(new SortCriterion("DH.Chng_ID DESC; select * from DepositHist order by Chng_ID ", false)));
        AssertJUnit.assertTrue(ascResult.getResults().get(0).getChangeId() == descResult.getResults().get(0).getChangeId());

    }

}
