package pl.nask.crs.secondarymarket;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static pl.nask.crs.commons.DateTestHelper.assertEqualDates;
import static pl.nask.crs.commons.DateTestHelper.assertEqualNullableDates;

public final class SecondaryMarketHelper {
    private SecondaryMarketHelper() {}

    public static void assertEqualBuyRequests(BuyRequest actual, BuyRequest expected) {
        assertEquals(actual.getDomainName(), expected.getDomainName());
        assertEquals(actual.getCreatorNH(), expected.getCreatorNH());
        assertEquals(actual.getAccount().getId(), expected.getAccount().getId());
        assertEquals(actual.getAccount().getName(), expected.getAccount().getName());
        assertEquals(actual.getAccount().getBillingNH(), expected.getAccount().getBillingNH());
        assertEquals(actual.getDomainHolder(), expected.getDomainHolder());
        assertEquals(actual.getHolderClass(), expected.getHolderClass());
        assertEquals(actual.getHolderCategory(), expected.getHolderCategory());
        assertEquals(actual.getRemark(), expected.getRemark());
        assertEquals(actual.getHostmasterRemark(), expected.getHostmasterRemark());
        assertEquals(actual.getAdminEmail(), expected.getAdminEmail());
        assertEquals(actual.getAdminCompanyName(), expected.getAdminCompanyName());
        assertEquals(actual.getAdminAddress(), expected.getAdminAddress());
        assertEquals(actual.getAdminCountry(), expected.getAdminCountry());
        assertEquals(actual.getAdminCounty(), expected.getAdminCounty());
        assertEquals(actual.getStatus(), expected.getStatus());
        assertEqualDates(actual.getCreationDate(), expected.getCreationDate(), Calendar.SECOND);
        assertEquals(actual.getAuthcode(), expected.getAuthcode());
        assertEqualNullableDates(actual.getAuthcodeCreationDate(), expected.getAuthcodeCreationDate(), Calendar.SECOND);
        assertEquals(actual.getCheckedOutTo(), expected.getCheckedOutTo());
        assertEqualPhoneLists(actual.getPhones(), expected.getPhones());
        assertEqualPhoneLists(actual.getFaxes(), expected.getFaxes());
        assertEqualNullableDates(actual.getChangeDate(), expected.getChangeDate(), Calendar.SECOND);
        assertEquals(actual.getDomainNameFR(), expected.getDomainNameFR(), "domainNameFR");
        assertEquals(actual.getDomainHolderFR(), expected.getDomainHolderFR(), "domainHolderFR");
        assertEquals(actual.getHolderClassFR(), expected.getHolderClassFR(), "holderClassFR");
        assertEquals(actual.getHolderCategoryFR(), expected.getHolderCategoryFR(), "holderCategoryFR");
        assertEquals(actual.getAdminNameFR(), expected.getAdminNameFR(), "adminNameFR");
        assertEquals(actual.getAdminEmailFR(), expected.getAdminEmailFR(), "adminEmailFR");
        assertEquals(actual.getAdminAddressFR(), expected.getAdminAddressFR(), "adminAddressFR");
        assertEquals(actual.getAdminCompanyNameFR(), expected.getAdminCompanyNameFR(), "adminCompanyNameFR");
        assertEquals(actual.getAdminCountryFR(), expected.getAdminCountryFR(), "adminCountryFR");
        assertEquals(actual.getAdminCountyFR(), expected.getAdminCountyFR(), "adminCountyFR");
        assertEquals(actual.getPhonesFR(), expected.getPhonesFR(), "telecomsFR");
        assertEquals(actual.getFaxesFR(), expected.getFaxesFR(), "telecomsFR");
    }

    public static void assertEqualPhoneLists(List<String> actual, List<String> expected) {
        if (Validator.isEmpty(expected)) {
            assertTrue(Validator.isEmpty(actual));
        } else {
            assertEquals(actual.size(), expected.size());
            Iterator<String> actualPhones = actual.iterator();
            Iterator<String> expectedPhones = expected.iterator();
            while (actualPhones.hasNext()) {
                assertEquals(actualPhones.next(), expectedPhones.next());
            }
        }
    }

    public static void assertEqualSellRequests(SellRequest actual, SellRequest expected) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getCreatorNH(), expected.getCreatorNH());
        assertEqualDates(actual.getCreationDate(), expected.getCreationDate(), Calendar.SECOND);
        assertEquals(actual.getStatus(), expected.getStatus());
        assertEqualBuyRequests(actual.getBuyRequest(), expected.getBuyRequest());
    }

    public static void assertEqualHistoricalBuyRequests(HistoricalObject<BuyRequest> actual,
            HistoricalObject<BuyRequest> expected) {
        assertEquals(actual.getChangeId(), expected.getChangeId());
        assertEqualDates(actual.getChangeDate(), expected.getChangeDate(), Calendar.SECOND);
        assertEquals(actual.getChangedBy(), expected.getChangedBy());
        assertEqualBuyRequests(actual.getObject(), expected.getObject());
    }

    public static void assertEqualHistoricalSellRequests(HistoricalObject<SellRequest> actual,
            HistoricalObject<SellRequest> expected) {
        assertEquals(actual.getChangeId(), expected.getChangeId());
        assertEqualDates(actual.getChangeDate(), expected.getChangeDate(), Calendar.SECOND);
        assertEquals(actual.getChangedBy(), expected.getChangedBy());
        assertEqualSellRequests(actual.getObject(), expected.getObject());
    }
}
