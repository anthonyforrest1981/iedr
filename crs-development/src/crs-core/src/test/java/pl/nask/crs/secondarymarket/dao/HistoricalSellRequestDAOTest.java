package pl.nask.crs.secondarymarket.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.SellRequestStatus;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualHistoricalSellRequests;
import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualSellRequests;

public class HistoricalSellRequestDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalSellRequestDAO historicalSellRequestDAO;

    @Resource
    HistoricalBuyRequestDAO historicalBuyRequestDAO;

    @Test
    public void createAndGetTest() {
        SellRequest sellRequest = new SellRequest("IDL2-IEDR", new Date(), historicalBuyRequestDAO.get(3L).getObject());
        sellRequest.setId(99L);
        long chngId = historicalSellRequestDAO.create(sellRequest, new Date(), "IDL2-IEDR");
        HistoricalObject<SellRequest> histSellRequest = historicalSellRequestDAO.get(chngId);
        assertNotNull(histSellRequest);
        assertEqualSellRequests(histSellRequest.getObject(), sellRequest);
    }

    @Test
    public void findTest() {
        HistoricalSellRequestSearchCriteria criteria = new HistoricalSellRequestSearchCriteria();
        List<HistoricalObject<SellRequest>> results = historicalSellRequestDAO.find(criteria).getResults();
        assertEquals(results.size(), 17);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setSellRequestId(2L);
        checkFindResult(criteria, 1L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-1.ie");
        checkFindResult(criteria, 1L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setDomainHolder("Another");
        checkFindResult(criteria, 13L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setAccountId(102L);
        checkFindResult(criteria, 2L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setStatus(SellRequestStatus.CANCELLED);
        checkFindResult(criteria, 4L);

    }

    private void checkFindResult(HistoricalSellRequestSearchCriteria criteria, long expectedChangeId) {
        List<HistoricalObject<SellRequest>> requests = historicalSellRequestDAO.find(criteria).getResults();
        assertEquals(requests.size(), 1);
        HistoricalObject<SellRequest> expected = historicalSellRequestDAO.get(expectedChangeId);
        assertEqualHistoricalSellRequests(requests.get(0), expected);
    }

}
