package pl.nask.crs.price;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.price.dao.DomainPricingDAO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class DomainPricingDAOTest extends AbstractTest {

    private static final String INACTIVE_PRICE_CODE = "Std1YearInactive";

    @Resource
    private DomainPricingDAO domainPricingDAO;

    @Test
    public void getByIdTest() throws Exception {
        DomainPrice price = domainPricingDAO.get(5);
        Date validFrom = DateUtils.addYears(new Date(), -2);
        Date validTo = DateUtils.addYears(new Date(), 2);
        DomainPrice expected = DomainPrice.newInstance("RM2Yr", "Registration for 2 Year",
                MoneyUtils.getRoundedAndScaledValue(40), 2, validFrom, validTo, true, true, false);
        expected.setId(5);
        assertNotNull(price);
        assertPricesEqual(price, expected);
    }

    @Test
    public void createTest() {
        Date validFromDate = pl.nask.crs.commons.utils.DateUtils.endOfYear(new Date());
        validFromDate = DateUtils.addYears(validFromDate, -1);
        Date validToDate = DateUtils.addYears(validFromDate, 5);
        DomainPrice price = DomainPrice.newInstance("newPrice", "description", MoneyUtils.getRoundedAndScaledValue(66),
                2, validFromDate, validToDate, true, true, true);
        domainPricingDAO.create(price);
        DomainPrice fromDB = domainPricingDAO.getDomainPriceByCode(price.getCode(), new Date());
        price.setId(fromDB.getId());
        assertPricesEqual(fromDB, price);
    }

    @Test
    public void getAllTest() {
        List<DomainPrice> domainPrices = domainPricingDAO.getAll();
        assertEquals(domainPrices.size(), 23);
    }

    @Test
    public void updateTest() throws ParseException {
        int id = 5;
        DomainPrice price = domainPricingDAO.get(id);

        price.setDescription("new description");
        price.setPrice(MoneyUtils.getRoundedAndScaledValue(55));
        price.setDuration(5);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date validFrom = dateFormat.parse("1975-05-05");
        Date validTo = dateFormat.parse("1975-06-05");
        price.setValidFrom(validFrom);
        price.setValidTo(validTo);
        price.setForRegistration(false);
        price.setForRenewal(false);
        price.setDirect(true);

        domainPricingDAO.update(price);

        DomainPrice updated = domainPricingDAO.get(id);
        assertPricesEqual(updated, price);
    }

    @Test
    public void findAllTest() {
        LimitedSearchResult<DomainPrice> result = domainPricingDAO.findAll(0, 10, null);
        assertEquals(result.getTotalResults(), 23);
        assertEquals(result.getResults().size(), 10);
    }

    @Test
    public void getDomainPriceListForSpecificDate() throws Exception {
        Date current = DateUtils.truncate(new Date(), Calendar.DATE);
        List<DomainPrice> prices = domainPricingDAO.getDomainPriceList(DateUtils.addYears(current, -6));
        assertEquals(prices.size(), 12);
        prices = domainPricingDAO.getDomainPriceList(DateUtils.addYears(current, 6));
        assertEquals(prices.size(), 12);
        prices = domainPricingDAO.getDomainPriceList(DateUtils.addYears(new Date(), 6));
        assertEquals(prices.size(), 10);
    }

    @Test
    public void getDomainPriceListCheckInactive() throws Exception {
        Date currentDate = new Date();
        List<DomainPrice> activePrices = domainPricingDAO.getDomainPriceList(currentDate);
        List<Integer> activeIds = new ArrayList<>();
        for (DomainPrice domainPrice : activePrices) {
            activeIds.add(domainPrice.getId());
        }
        assertEquals(activeIds.size(), 22);
        List<DomainPrice> allPrices = domainPricingDAO.getAll();
        assertEquals(allPrices.size(), 23);

        DomainPrice inactivePrice = null;
        for (DomainPrice domainPrice : allPrices) {
            if (!activeIds.contains(domainPrice.getId())) {
                inactivePrice = domainPrice;
                break;
            }
        }
        assertNotNull(inactivePrice);
        assertEquals(inactivePrice.getCode(), INACTIVE_PRICE_CODE);
        // Verify that the product is valid in regard to its date.
        assertNotEquals(currentDate.compareTo(inactivePrice.getValidFrom()), -1);
        assertNotEquals(currentDate.compareTo(inactivePrice.getValidTo()), 1);
    }

    @Test
    public void getDomainPriceByCodeTest() throws Exception {
        String code = "Std2Year";
        Date validFrom = DateUtils.addYears(new Date(), -2);
        Date validTo = DateUtils.addYears(new Date(), 2);
        DomainPrice expected = DomainPrice.newInstance(code, "Registration of your domain for 2 years",
                MoneyUtils.getRoundedAndScaledValue(184), 2, validFrom, validTo, true, true, true);
        expected.setId(14);
        DomainPrice price = domainPricingDAO.getDomainPriceByCode(code, new Date());
        assertNotNull(price);
        assertPricesEqual(price, expected);
    }

    @Test
    public void getDomainPriceTest() throws Exception {
        List<DomainPrice> domainPrices = domainPricingDAO.getAll();
        assertFalse(domainPrices.isEmpty());
        for (DomainPrice domainPrice : domainPrices) {
            if (INACTIVE_PRICE_CODE.equals(domainPrice.getCode())) {
                continue;
            }
            if (domainPrice.isForRegistration() && domainPrice.isForRenewal()) {
                DomainPrice regPrice = domainPricingDAO.getDomainPrice(domainPrice.getDuration(),
                        domainPrice.getValidFrom(), true, null, domainPrice.isDirect());
                assertPricesEqual(regPrice, domainPrice);
                DomainPrice renPrice = domainPricingDAO.getDomainPrice(domainPrice.getDuration(),
                        domainPrice.getValidFrom(), null, true, domainPrice.isDirect());
                assertPricesEqual(renPrice, domainPrice);
            } else {
                DomainPrice singlePrice = domainPricingDAO.getDomainPrice(domainPrice.getDuration(),
                        domainPrice.getValidFrom(), domainPrice.isForRegistration(), domainPrice.isForRenewal(),
                        domainPrice.isDirect());
                assertPricesEqual(singlePrice, domainPrice);
            }
        }
    }

    private void assertPricesEqual(DomainPrice actual, DomainPrice expected) {
        assertNotNull(actual, expected.toString());
        assertNotNull(expected, actual.toString());
        assertEquals(actual.getCode(), expected.getCode());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getDuration(), expected.getDuration());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getPrice(), expected.getPrice());
        assertEquals(actual.getValidFrom(), DateUtils.truncate(expected.getValidFrom(), Calendar.DATE));
        assertEquals(actual.getValidTo(), DateUtils.truncate(expected.getValidTo(), Calendar.DATE));
        assertEquals(actual.isDefaultPrice(), expected.isDefaultPrice());
        assertEquals(actual.isDirect(), expected.isDirect());
        assertEquals(actual.isForRegistration(), expected.isForRegistration());
        assertEquals(actual.isForRenewal(), expected.isForRenewal());

    }

}
