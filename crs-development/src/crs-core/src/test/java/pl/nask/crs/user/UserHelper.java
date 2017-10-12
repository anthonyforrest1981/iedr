package pl.nask.crs.user;

import java.util.Calendar;

import pl.nask.crs.commons.DateTestHelper;

import static org.testng.Assert.assertEquals;

public class UserHelper {

    public static void compareUsers(User actual, User expected) {
        compareUsers(actual, expected, false);
    }

    public static void compareHistoricalUsers(User actual, User expected) {
        compareUsers(actual, expected, true);
    }

    public static void compareUsers(User actual, User expected, boolean isHistorical) {
        assertEquals(actual.getUsername(), expected.getUsername());
        assertEquals(actual.getPassword(), expected.getPassword());
        DateTestHelper.assertEqualNullableDates(actual.getPasswordChangeDate(), expected.getPasswordChangeDate(),
                Calendar.SECOND);
        assertEquals(actual.getSalt(), expected.getSalt());
        assertEquals(actual.getSecret(), expected.getSecret());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPermissionGroups(), expected.getPermissionGroups());
        if (!isHistorical) {
            assertEquals(actual.getPermissions(), expected.getPermissions());
        }
    }

}
