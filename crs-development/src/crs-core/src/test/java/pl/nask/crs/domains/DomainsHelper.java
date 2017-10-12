package pl.nask.crs.domains;

import java.util.Calendar;
import java.util.List;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static pl.nask.crs.commons.DateTestHelper.assertEqualDates;
import static pl.nask.crs.commons.DateTestHelper.assertEqualNullableDates;

public class DomainsHelper {

    public static void compareNewDomains(Domain actual, Domain expected) {
        compareBasics(actual, expected);
        compareDates(actual, expected, true);
        compareContacts(actual, expected);
        compareNameservers(actual, expected);
    }

    public static void compareDomains(Domain actual, Domain expected) {
        compareBasics(actual, expected);
        compareDates(actual, expected, false);
        compareContacts(actual, expected);
        compareNameservers(actual, expected);
    }

    public static void compareBasics(Domain actual, Domain expected) {
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getDsmState().getId(), actual.getDsmState().getId());
        assertEquals(actual.getHolder(), expected.getHolder());
        assertEquals(actual.getHolderClass(), expected.getHolderClass());
        assertEquals(actual.getHolderCategory(), expected.getHolderCategory());
        assertEquals(actual.getHolderSubcategory(), expected.getHolderSubcategory());
        assertEquals(actual.getRemark(), expected.getRemark());
        assertEquals(actual.getResellerAccount(), expected.getResellerAccount());
        assertEquals(actual.getAuthCode(), expected.getAuthCode());
        assertEquals(actual.getAuthCodePortalCount(), expected.getAuthCodePortalCount());
        assertEquals(actual.isClikPaid(), expected.isClikPaid());
        assertEquals(actual.getDateRoll(), expected.getDateRoll());
    }

    public static void compareDates(Domain actual, Domain expected, boolean compareNewDomainDates) {
        if (compareNewDomainDates) {
            assertNotNull(actual.getChangeDate());
            assertEquals(actual.getRegistrationDate(), actual.getChangeDate());
            assertEqualDates(actual.getRenewalDate(), actual.getChangeDate(), Calendar.DATE);
        } else {
            assertEqualDates(actual.getChangeDate(), expected.getChangeDate(), Calendar.SECOND);
            assertEqualDates(actual.getRegistrationDate(), expected.getRegistrationDate(), Calendar.SECOND);
            assertEqualDates(actual.getRenewalDate(), expected.getRenewalDate(), Calendar.DATE);
        }
        assertEqualNullableDates(actual.getTransferDate(), expected.getTransferDate(), Calendar.SECOND);
        assertEqualNullableDates(actual.getDeletionDate(), expected.getDeletionDate(), Calendar.DATE);
        assertEqualNullableDates(actual.getSuspensionDate(), expected.getSuspensionDate(), Calendar.DATE);
        assertEqualNullableDates(actual.getAuthCodeExpirationDate(), expected.getAuthCodeExpirationDate(), Calendar.DATE);
        assertEqualNullableDates(actual.getLockingDate(), expected.getLockingDate(), Calendar.SECOND);
        assertEqualNullableDates(actual.getLockingRenewalDate(), expected.getLockingRenewalDate(), Calendar.DATE);
    }

    public static void compareContacts(Domain actual, Domain expected) {
        compareContactLists(actual.getAdminContacts(), expected.getAdminContacts());
        compareContactLists(actual.getTechContacts(), expected.getTechContacts());
        compareContactLists(actual.getBillingContacts(), expected.getBillingContacts());
        compareContacts(actual.getCreator(), expected.getCreator());
    }

    public static void compareNameservers(Domain actual, Domain expected) {
        compareNameserverLists(actual.getNameservers(), expected.getNameservers());
    }

    public static void compareContacts(Contact actual, Contact expected) {
        assertEquals(actual.getNicHandle(), expected.getNicHandle());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getCompanyName(), expected.getCompanyName());
        assertEquals(actual.getCountryName(), expected.getCountryName());
        assertEquals(actual.getCountyName(), expected.getCountyName());
    }

    public static void compareContactLists(List<Contact> actual, List<Contact> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            compareContacts(actual.get(i), expected.get(i));
        }
    }

    public static void compareNameservers(Nameserver actual, Nameserver expected) {
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getIpv4Address(), expected.getIpv4Address());
        assertEquals(actual.getIpv6Address(), expected.getIpv6Address());
    }

    public static void compareNameserverLists(List<Nameserver> actual, List<Nameserver> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            compareNameservers(actual.get(i), expected.get(i));
        }
    }
}
