package pl.nask.crs.commons;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;

public class DateTestHelper {

    public static void assertEqualDates(Date actual, Date expected, int granularity) {
        Assert.assertEquals(actual, DateUtils.truncate(expected, granularity));
    }

    public static void assertEqualNullableDates(Date actual, Date expected, int granularity) {
        if (actual == null || expected == null) {
            Assert.assertEquals(actual, expected);
        } else {
            assertEqualDates(actual, expected, granularity);
        }
    }

    public static Date makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }

    public static Date setHour(Date date, int hour, int minute, int second, int millisec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisec);
        return cal.getTime();
    }
}
