package pl.nask.crs.secondarymarket.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualSellRequests;

public class SellRequestDAOTest extends AbstractContextAwareTest {

    @Resource
    SellRequestDAO sellRequestDAO;

    @Resource
    HistoricalSellRequestDAO historicalSellRequestDAO;

    @Resource
    BuyRequestDAO buyRequestDAO;

    @Test
    public void createAndGetTest() {
        BuyRequest buyRequest = buyRequestDAO.get(4L);
        SellRequest sellRequest = new SellRequest("IDL2-IEDR", new Date(), buyRequest);
        sellRequestDAO.create(sellRequest);
        assertFalse(buyRequest.getId() == -1);
        SellRequest dbRequest = sellRequestDAO.get(sellRequest.getId());
        assertNotNull(dbRequest);
        assertEqualSellRequests(dbRequest, sellRequest);
    }

    @Test
    public void updateUsingHistoryTest() {
        SellRequest sellRequest = sellRequestDAO.get(3L);
        sellRequest.setCreationDate(new Date());
        sellRequest.setCreatorNH("IDL2-IEDR");
        sellRequest.setBuyRequest(buyRequestDAO.get(4L));
        long chngId = historicalSellRequestDAO.create(sellRequest, new Date(), "IDL2-IEDR");
        sellRequestDAO.updateUsingHistory(chngId);
        SellRequest dbRequest = sellRequestDAO.get(sellRequest.getId());
        assertEqualSellRequests(dbRequest, sellRequest);
    }

    @Test
    public void findTest() {
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        LimitedSearchResult<SellRequest> limitedResult = sellRequestDAO.find(criteria, 0, 10, null);
        assertEquals(limitedResult.getResults().size(), 9);
        assertEquals(limitedResult.getTotalResults(), 9);

        limitedResult = sellRequestDAO.find(criteria, 0, 1, null);
        assertEquals(limitedResult.getResults().size(), 1);
        assertEquals(limitedResult.getTotalResults(), 9);

        criteria = new SellRequestSearchCriteria();
        criteria.setSellRequestId(2L);
        checkFindResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-2.ie");
        checkFindResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedFrom(DateUtils.addDays(new Date(), -1));
        checkFindResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), -11));
        checkFindResult(criteria, 19L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatorNH("IOA3-IEDR");
        checkFindResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setDomainHolder("Second");
        checkFindResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setAccountId(262L);
        checkFindResult(criteria, 13L);

        criteria = new SellRequestSearchCriteria();
        criteria.setBuyRequestId(2L);
        checkFindResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setContactNH("AHK693-IEDR");
        checkFindResult(criteria, 19L);
    }

    private void checkFindResult(SellRequestSearchCriteria criteria, long expectedId) {
        List<SellRequest> requests = sellRequestDAO.find(criteria).getResults();
        assertEquals(requests.size(), 1);
        SellRequest expectedRequest = sellRequestDAO.get(expectedId);
        assertEqualSellRequests(requests.get(0), expectedRequest);
    }

}
