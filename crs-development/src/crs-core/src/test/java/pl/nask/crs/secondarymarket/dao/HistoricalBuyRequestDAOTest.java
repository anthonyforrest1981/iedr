package pl.nask.crs.secondarymarket.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualBuyRequests;
import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualHistoricalBuyRequests;

public class HistoricalBuyRequestDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalBuyRequestDAO histBuyRequestDAO;

    @Resource
    EntityClassDAO classDAO;
    @Resource
    EntityCategoryDAO categoryDAO;
    @Resource
    CountryDAO countryDAO;
    @Resource
    AccountDAO accountDAO;

    @Test
    public void createAndGet() {
        EntityCategory entityCategory = categoryDAO.get(1l);
        EntityClass entityClass = classDAO.get(1l);
        Country country = countryDAO.get(119);
        Account account = accountDAO.get(101l);
        List<String> phones = Arrays.asList("123456", "234");
        Date creationDate = new Date();

        BuyRequest buyRequest =
                new BuyRequest("pizzaonline.ie", "IDL2-IEDR", account, "holder", entityClass, entityCategory, "remark",
                        "hostmasterRemark", "a@a.ie", "Test Inc.", "Test Street", phones, null, country,
                        country.getCounties().get(1), creationDate);
        buyRequest.setId(1l);
        buyRequest.setChangeDate(creationDate);
        assertEquals(buyRequest.getStatus(), BuyRequestStatus.NEW);
        Long chngId = histBuyRequestDAO.create(buyRequest, creationDate, "TEST-IEDR");
        assertNotNull(chngId);
        HistoricalObject<BuyRequest> dbRequest = histBuyRequestDAO.get(chngId);
        assertNotNull(dbRequest);
        assertEquals(dbRequest.getChangeDate(), DateUtils.truncate(creationDate, Calendar.SECOND));
        assertEquals(dbRequest.getChangedBy(), "TEST-IEDR");
        assertEqualBuyRequests(dbRequest.getObject(), buyRequest);
    }

    @Test
    public void findTest() {
        HistoricalBuyRequestSearchCriteria criteria = new HistoricalBuyRequestSearchCriteria();
        List<HistoricalObject<BuyRequest>> results = histBuyRequestDAO.find(criteria).getResults();
        assertEquals(results.size(), 30);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setBuyRequestId(2L);
        checkFindResult(criteria, 1L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-4.ie");
        checkFindResult(criteria, 4L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setDomainHolder("Another");
        checkFindResult(criteria, 13L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setAccountId(102L);
        checkFindResult(criteria, 2L);

    }

    private void checkFindResult(HistoricalBuyRequestSearchCriteria criteria, long expectedChangeId) {
        List<HistoricalObject<BuyRequest>> requests = histBuyRequestDAO.find(criteria).getResults();
        assertEquals(requests.size(), 1);
        HistoricalObject<BuyRequest> expected = histBuyRequestDAO.get(expectedChangeId);
        assertEqualHistoricalBuyRequests(requests.get(0), expected);
    }
}
