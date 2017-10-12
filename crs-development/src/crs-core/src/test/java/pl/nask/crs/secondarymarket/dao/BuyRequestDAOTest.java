package pl.nask.crs.secondarymarket.dao;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
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
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.ticket.operation.FailureReason;

import static org.testng.Assert.*;
import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.assertEqualBuyRequests;

public class BuyRequestDAOTest extends AbstractContextAwareTest {

    @Resource
    BuyRequestDAO buyRequestDAO;
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

        BuyRequest buyRequest =
                new BuyRequest("pizzaonline.ie", "IDL2-IEDR", account, "holder", entityClass, entityCategory, "remark",
                        "hostmasterRemark", "a@a.ie", "Test Inc.", "Test Street", phones, null, country,
                        country.getCounties().get(1), new Date());
        assertEquals(buyRequest.getStatus(), BuyRequestStatus.NEW);
        buyRequestDAO.create(buyRequest);
        assertFalse(buyRequest.getId() == -1);
        BuyRequest dbRequest = buyRequestDAO.get(buyRequest.getId());
        assertNotNull(dbRequest);
        assertEqualBuyRequests(dbRequest, buyRequest);
    }

    @Test
    public void updateUsingHistory() {
        BuyRequest buyRequest = buyRequestDAO.get(2l);
        BuyRequest buyRequest2 = buyRequestDAO.get(3l);
        HistoricalObject<BuyRequest> histBuyRequest = histBuyRequestDAO.get(1l);

        // actual test
        buyRequestDAO.updateUsingHistory(histBuyRequest.getChangeId());
        BuyRequest dbRequest = buyRequestDAO.get(buyRequest.getId());
        BuyRequest dbRequest2 = buyRequestDAO.get(buyRequest2.getId());
        // first that it did update the request
        assertEqualBuyRequests(dbRequest, histBuyRequest.getObject());
        // and second: that only the one desired one got updated
        assertEqualBuyRequests(dbRequest2, buyRequest2);
    }

    @DataProvider
    public static Object[][] failureReasonFields() {
        return new Object[][] {{"domainNameFR"}, {"domainHolderFR"}, {"holderClassFR"}, {"holderCategoryFR"},
                {"adminNameFR"}, {"adminEmailFR"}, {"adminCompanyNameFR"}, {"adminAddressFR"}, {"adminCountryFR"},
                {"adminCountyFR"}, {"phonesFR"}, {"faxesFR"}};
    }
    @Test(dataProvider = "failureReasonFields")
    public void testFailureReason(final String fieldName) {
        try {
            BuyRequest origRequest = buyRequestDAO.get(2l);
            PropertyDescriptor property = new PropertyDescriptor(fieldName, BuyRequest.class);
            assertNull(property.getReadMethod().invoke(origRequest));
            property.getWriteMethod().invoke(origRequest, FailureReason.INCORRECT);
            long histId = histBuyRequestDAO.create(origRequest, new Date(), "IDL2-IEDR");
            buyRequestDAO.updateUsingHistory(histId);
            BuyRequest dbRequest = buyRequestDAO.get(2l);
            // we're not checking change date, have to copy it for the rest of test to work
            origRequest.setChangeDate(dbRequest.getChangeDate());
            assertEqualBuyRequests(dbRequest, origRequest);
        } catch (Exception e) {
            fail("Failed automating test for FailureReason " + fieldName, e);
        }
    }

    @Test
    public void find() {

        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        LimitedSearchResult<BuyRequest> result = buyRequestDAO.find(criteria, 0, 10);
        assertEquals(result.getTotalResults(), 25);

        criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-1");
        checkFindResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-2");
        checkFindResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyRequestId(2L);
        checkFindResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyerAccountId(262L);
        checkFindResult(criteria, 13L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setSellerAccountId(102L);
        checkFindResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setStatus(BuyRequestStatus.NEW);
        assertEquals(buyRequestDAO.find(criteria, 0, 100).getTotalResults(), 7);

        criteria = new BuyRequestSearchCriteria();
        criteria.setStatuses(Arrays.asList(BuyRequestStatus.NEW, BuyRequestStatus.HOLD_UPDATE));
        assertEquals(buyRequestDAO.find(criteria, 0, 100).getTotalResults(), 8);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyerName("Admin1");
        checkFindResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(DateUtils.addDays(new Date(), -21));
        criteria.setCreationDateTo(DateUtils.addDays(new Date(), -19));
        checkFindResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(DateUtils.addDays(new Date(), -12));
        checkFindResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCheckedOutTo("TDI2");
        checkFindResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateFrom(DateUtils.addDays(new Date(), 1));
        checkFindResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateTo(DateUtils.addDays(new Date(), -90));
        checkFindResult(criteria, 38L);
    }

    private void checkFindResult(BuyRequestSearchCriteria criteria, long expectedRequestId) {
        BuyRequest expectedRequest = buyRequestDAO.get(expectedRequestId);
        LimitedSearchResult<BuyRequest> limitedResult = buyRequestDAO.find(criteria, 0, 10);
        assertEquals(limitedResult.getTotalResults(), 1);
        checkFindResult(limitedResult.getResults(), expectedRequest);
        SearchResult<BuyRequest> unlimitedResult = buyRequestDAO.find(criteria);
        checkFindResult(unlimitedResult.getResults(), expectedRequest);
    }

    private void checkFindResult(List<BuyRequest> result, BuyRequest expectedRequest) {
        assertEquals(result.size(), 1);
        BuyRequest dbRequest = result.get(0);
        assertEqualBuyRequests(dbRequest, expectedRequest);
    }

    @Test
    public void getByAuthcode() {
        BuyRequest expected = buyRequestDAO.get(2l);
        assertNull(buyRequestDAO.getByAuthcode("AUTHCODE"));
        BuyRequest dbRequest = buyRequestDAO.getByAuthcode("TESTAUTHCODE");
        assertNotNull(dbRequest);
        assertEqualBuyRequests(dbRequest, expected);
    }

    @Test
    public void testDelete() {
        long buyRequestId = 21L;
        assertNotNull(buyRequestDAO.get(buyRequestId));
        buyRequestDAO.deleteById(buyRequestId);
        assertNull(buyRequestDAO.get(buyRequestId));
    }

}
