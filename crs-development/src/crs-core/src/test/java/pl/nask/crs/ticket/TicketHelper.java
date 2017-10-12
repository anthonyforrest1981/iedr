package pl.nask.crs.ticket;

import java.util.Calendar;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.helpers.AccountTestHelper;
import pl.nask.crs.commons.DateTestHelper;
import pl.nask.crs.commons.Period;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static pl.nask.crs.domains.DomainsHelper.compareContacts;
import static pl.nask.crs.commons.DateTestHelper.assertEqualDates;

@SuppressWarnings("nullness")
public class TicketHelper {

    public static void compareNewTickets(Ticket actual, Ticket expected, boolean compareNameservers) {
        compareTickets(actual, expected, true, false, compareNameservers);
    }

    public static void compareTickets(Ticket actual, Ticket expected) {
        compareTickets(actual, expected, false, false, true);
    }

    public static void compareHistoricalTickets(Ticket actual, Ticket expected) {
        compareTickets(actual, expected, false, true, true);
    }

    private static void compareTickets(Ticket actual, Ticket expected, boolean isNew, boolean isHistory,
            boolean compareNameservers) {
        if (!isNew) {
            assertEquals(actual.getId(), expected.getId());
        }
        compareOperations(actual.getOperation(), expected.getOperation(), compareNameservers, isHistory);
        compareContacts(actual.getCreator(), expected.getCreator());
        compareNullableContacts(actual.getCheckedOutTo(), expected.getCheckedOutTo());
        assertEquals(actual.getAdminStatus(), expected.getAdminStatus());
        assertEquals(actual.getTechStatus(), expected.getTechStatus());
        assertEquals(actual.getFinancialStatus(), expected.getFinancialStatus());
        assertEquals(actual.getCustomerStatus(), expected.getCustomerStatus());
        assertEqualDates(actual.getAdminStatusChangeDate(), expected.getAdminStatusChangeDate(), Calendar.SECOND);
        assertEqualDates(actual.getTechStatusChangeDate(), expected.getTechStatusChangeDate(), Calendar.SECOND);
        assertEqualDates(actual.getFinancialStatusChangeDate(), expected.getFinancialStatusChangeDate(), Calendar.SECOND);
        assertEqualDates(actual.getCustomerStatusChangeDate(), expected.getCustomerStatusChangeDate(), Calendar.SECOND);
        assertEqualDates(actual.getCreationDate(), expected.getCreationDate(), Calendar.SECOND);
        assertEqualDates(actual.getChangeDate(), expected.getChangeDate(), Calendar.SECOND);
        assertEquals(actual.getCharityCode(), expected.getCharityCode());
        assertEquals(actual.getHostmastersRemark(), expected.getHostmastersRemark());
        assertEquals(actual.getRequestersRemark(), expected.getRequestersRemark());
        comparePeriods(actual.getDomainPeriod(), expected.getDomainPeriod());
    }

    private static void compareOperations(DomainOperation actual, DomainOperation expected, boolean compareNameservers,
            boolean isHistory) {
        compareChanges(actual.getDomainNameField(), expected.getDomainNameField());
        assertEquals(actual.getType(), expected.getType());
        DateTestHelper.assertEqualNullableDates(actual.getRenewalDate(), expected.getRenewalDate(), Calendar.DATE);
        compareChanges(actual.getDomainHolderField(), expected.getDomainHolderField());
        compareChanges(actual.getDomainHolderClassField(), expected.getDomainHolderClassField());
        compareChanges(actual.getDomainHolderCategoryField(), expected.getDomainHolderCategoryField());
        compareChanges(actual.getDomainHolderSubcategoryField(), expected.getDomainHolderSubcategoryField());
        compareAccountChanges(actual.getResellerAccountField(), expected.getResellerAccountField(), isHistory);
        compareContactChanges(actual.getAdminContactsField(), expected.getAdminContactsField());
        compareContactChanges(actual.getBillingContactsField(), expected.getBillingContactsField());
        compareContactChanges(actual.getTechContactsField(), expected.getTechContactsField());
        if (compareNameservers) {
            compareNameserverChanges(actual.getNameserversField(), expected.getNameserversField());
        }
    }

    private static void compareContactChanges(List<SimpleDomainFieldChange<Contact>> actualList,
            List<SimpleDomainFieldChange<Contact>> expectedList) {
        // DB tickets will always have two admin contacts. If the second doesn't exist in the db, it will be null in the
        // object.
        if (actualList.size() > expectedList.size()) {
            assertEquals(actualList.size(), expectedList.size() + 1);
            SimpleDomainFieldChange<Contact> expected = new SimpleDomainFieldChange<>(null, null, null);
            compareContactChanges(actualList.get(actualList.size() - 1), expected);
        } else {
            assertEquals(actualList.size(), expectedList.size());
        }

        for (int i = 0; i < expectedList.size(); i++) {
            SimpleDomainFieldChange<Contact> actual = actualList.get(i);
            SimpleDomainFieldChange<Contact> expected = expectedList.get(i);
            compareContactChanges(actual, expected);
        }
    }

    private static void compareContactChanges(SimpleDomainFieldChange<Contact> actual,
            SimpleDomainFieldChange<Contact> expected) {
        compareNullableContacts(actual.getNewValue(), expected.getNewValue());
        compareNullableContacts(actual.getCurrentValue(), expected.getCurrentValue());
        assertEquals(actual.getFailureReason(), expected.getFailureReason());
    }

    private static void compareNullableContacts(Contact actual, Contact expected) {
        if (expected == null) {
            assertNull(actual);
        } else {
            compareContacts(actual, expected);
        }
    }

    public static void compareNameserverChanges(List<NameserverChange> actualList,
            List<NameserverChange> expectedList) {
        assertEquals(actualList.size(), expectedList.size());
        for (int i = 0; i < actualList.size(); i++) {
            NameserverChange actual = actualList.get(i);
            NameserverChange expected = expectedList.get(i);
            compareChanges(actual.getName(), expected.getName());
            compareIpChanges(actual.getIpv4Address(), expected.getIpv4Address());
            compareIpChanges(actual.getIpv6Address(), expected.getIpv6Address());
        }
    }

    private static <T> void compareChanges(SimpleDomainFieldChange<T> actual, SimpleDomainFieldChange<T> expected) {
        assertEquals(actual.getNewValue(), expected.getNewValue());
        assertEquals(actual.getCurrentValue(), expected.getCurrentValue());
        assertEquals(actual.getFailureReason(), expected.getFailureReason());
    }

    private static void compareIpChanges(IpFieldChange actual, IpFieldChange expected) {
        assertEquals(actual.getNewValue(), expected.getNewValue());
        assertEquals(actual.getOldValue(), expected.getOldValue());
        assertEquals(actual.getFailureReason(), expected.getFailureReason());
    }

    private static void comparePeriods(Period actual, Period expected) {
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(actual.getMonths(), expected.getMonths());
            assertEquals(actual.getYears(), expected.getYears());
        }
    }

    private static void compareAccountChanges(SimpleDomainFieldChange<Account> actual,
            SimpleDomainFieldChange<Account> expected, boolean isHistory) {
        compareAccounts(actual.getNewValue(), expected.getNewValue(), isHistory);
        if (expected.getCurrentValue() == null) {
            assertNull(actual.getCurrentValue());
        } else {
            compareAccounts(actual.getCurrentValue(), expected.getCurrentValue(), isHistory);
        }
        assertEquals(actual.getFailureReason(), expected.getFailureReason());
    }

    private static void compareAccounts(Account actual, Account expected, boolean isHistory) {
        if (isHistory) {
            // Historical tickets don't have billing contacts info.
            actual.setBillingContact(expected.getBillingContact());
        } else {
            AccountTestHelper.compareAccounts(actual, expected);
        }
    }

}
