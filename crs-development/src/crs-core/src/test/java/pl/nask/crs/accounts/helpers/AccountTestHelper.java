package pl.nask.crs.accounts.helpers;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.history.HistoricalObject;

import static org.testng.Assert.assertEquals;

public class AccountTestHelper {

    public static void compareAccounts(Account actual, Account expected) {
        compareAccounts(actual, expected, true);
    }

    public static void compareAccounts(HistoricalObject<Account> actual, Account expected) {
        // Historical accounts don't have contact info.
        compareAccounts(actual.getObject(), expected, false);
    }

    private static void compareAccounts(Account actual, Account expected, boolean compareContactData) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getBillingContact().getNicHandle(), expected.getBillingContact().getNicHandle());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getRemark(), expected.getRemark());
        assertEquals(actual.getStatus(), expected.getStatus());
        assertEquals(actual.getWebAddress(), expected.getWebAddress());
        assertEquals(actual.isAgreementSigned(), expected.isAgreementSigned());
        assertEquals(actual.isTicketEdit(), expected.isTicketEdit());
        assertEquals(actual.getSegmentId(), expected.getSegmentId());

        Date expectedCreationDate = expected.getCreationDate() == null ? null
                : DateUtils.truncate(expected.getCreationDate(), Calendar.SECOND);
        Date expectedStatusChangeDate = expected.getStatusChangeDate() == null ? null
                : DateUtils.truncate(expected.getStatusChangeDate(), Calendar.SECOND);
        Date expectedChangeDate = expected.getChangeDate() == null ? null
                : DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND);
        assertEquals(actual.getCreationDate(), expectedCreationDate);
        assertEquals(actual.getStatusChangeDate(), expectedStatusChangeDate);
        assertEquals(actual.getChangeDate(), expectedChangeDate);
        if (compareContactData) {
            assertEquals(actual.getBillingContact().getName(), expected.getBillingContact().getName());
            assertEquals(actual.getBillingContact().getEmail(), expected.getBillingContact().getEmail());
        }
    }

    public static Account getExpectedGuestAccount() {
        Contact contact = new Contact("IH4-IEDR", "IEDR Hostmaster", "NHEmail000903@server.xxx");
        return new Account(1L, "GUEST ACCOUNT", contact, AccountStatus.Active, "www.www000007.xxx",
                "Default Guest Account", new Date(1021413600000L), new Date(1023400800000L), new Date(1394701826000L),
                true, true, null);
    }

}
