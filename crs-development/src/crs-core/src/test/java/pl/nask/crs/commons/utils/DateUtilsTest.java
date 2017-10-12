package pl.nask.crs.commons.utils;

import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.Pair;

import static pl.nask.crs.commons.DateTestHelper.makeDate;
import static pl.nask.crs.commons.DateTestHelper.setHour;

public class DateUtilsTest {

    @Test
    public void testEndOfDay() {
        Date anyDate = makeDateMidDay(2012, 10, 10);

        Date endOfDay = makeDate(2012, 10, 10);
        endOfDay = setHour(endOfDay, 23, 59, 59, 999);

        Assert.assertEquals(endOfDay, DateUtils.endOfDay(anyDate));
    }

    @Test
    public void testStartOfDay() {
        Date anyDate = makeDateMidDay(2012, 10, 10);

        Date startOfDay = makeDate(2012, 10, 10);
        startOfDay = setHour(anyDate, 0, 0, 0, 0);
        Assert.assertEquals(startOfDay, DateUtils.startOfDay(anyDate));
    }

    @Test
    public void testStartOfMonth() {
        Date anyDate = makeDateMidDay(2012, 10, 10);
        Date startOfMonth = makeDate(2012, 10, 1);
        startOfMonth = setHour(startOfMonth, 0, 0, 0, 0);

        Assert.assertEquals(startOfMonth, DateUtils.startOfMonth(anyDate));
    }

    @Test
    public void testEndOfMonth31() {
        Date anyDate = makeDateMidDay(2012, 10, 10);
        Date endOfMonth = makeDate(2012, 10, 31);
        endOfMonth = setHour(endOfMonth, 23, 59, 59, 999);

        Assert.assertEquals(endOfMonth, DateUtils.endOfMonth(anyDate));
    }

    @Test
    public void testEndOfMonth30() {
        Date anyDate = makeDateMidDay(2012, 9, 10);
        Date endOfMonth = makeDate(2012, 9, 30);
        endOfMonth = setHour(endOfMonth, 23, 59, 59, 999);

        Assert.assertEquals(endOfMonth, DateUtils.endOfMonth(anyDate));
    }

    @Test
    public void testEndOfMonth28() {
        Date anyDate = makeDateMidDay(2011, 2, 10);
        Date endOfMonth = makeDate(2011, 2, 28);
        endOfMonth = setHour(endOfMonth, 23, 59, 59, 999);

        Assert.assertEquals(endOfMonth, DateUtils.endOfMonth(anyDate));
    }

    @Test
    public void testEndOfMonth29() {
        Date anyDate = makeDateMidDay(2012, 2, 10);
        Date endOfMonth = makeDate(2012, 2, 29);
        endOfMonth = setHour(endOfMonth, 23, 59, 59, 999);

        Assert.assertEquals(endOfMonth, DateUtils.endOfMonth(anyDate));
    }

    @Test
    public void testEndOfYear() {
        Date anyDate = makeDateMidDay(2012, 2, 10);
        Date endOfYear = makeDate(2012, 12, 31);
        endOfYear = setHour(endOfYear, 23, 59, 59, 999);

        Assert.assertEquals(endOfYear, DateUtils.endOfYear(anyDate));
    }

    @Test
    public void testGetCardExpDateAsStringOneDigit() {
        Date anyDate = makeDateMidDay(2012, 9, 10);
        String cardExpDate = "0912";

        Assert.assertEquals(cardExpDate, DateUtils.getCardExpDateAsString(anyDate));
    }

    @Test
    public void testGetCardExpDateAsStringTwoDigits() {
        Date anyDate = makeDateMidDay(2012, 11, 10);
        String cardExpDate = "1112";

        Assert.assertEquals(cardExpDate, DateUtils.getCardExpDateAsString(anyDate));
    }

    @Test
    public void testGetFirstLastDayOfMonthDatePair() {
        String anyDate = "2012-11";

        Date startOfMonth = makeDate(2012, 11, 1);
        startOfMonth = setHour(startOfMonth, 0, 0, 0, 0);

        Date endOfMonth = makeDate(2012, 11, 30);
        endOfMonth = setHour(endOfMonth, 23, 59, 59, 999);

        Pair<Date, Date> range = DateUtils.getFirstLastDayOfMonthDatePair(anyDate);
        Assert.assertEquals(startOfMonth, range.getLeft());
        Assert.assertEquals(endOfMonth, range.getRight());
    }

    @Test
    public void testStartOfYear() throws Exception {
        Date anyDate = makeDateMidDay(2012, 10, 10);
        Date startOfYear = makeDate(2012, 1, 1);
        startOfYear = setHour(startOfYear, 0, 0, 0, 0);

        Assert.assertEquals(startOfYear, DateUtils.startOfYear(anyDate));
    }

    @Test
    public void testDayBeetweenDates() {
        Date now = new Date();
        Date past = DateUtils.getCurrDate(-1);
        Date future = DateUtils.getCurrDate(+1);

        // check in range:

        Assert.assertTrue(DateUtils.isDateBetween(now, now, now, true, true), "Date d in range <d,d>");
        Assert.assertTrue(DateUtils.isDateBetween(now, past, now, false, true), "Date d in range (d-1,d>");
        Assert.assertTrue(DateUtils.isDateBetween(now, past, future, false, false), "Date d in range (d-1,d+1)");
        Assert.assertTrue(DateUtils.isDateBetween(now, now, future, true, false), "Date d in range <d,d+1)");

        // left/right open
        Assert.assertTrue(DateUtils.isDateBetween(now, now, null, true, false), "Date d in range <d,null)");
        Assert.assertTrue(DateUtils.isDateBetween(now, null, now, false, true), "Date d in range (null,d>");
        Assert.assertTrue(DateUtils.isDateBetween(now, null, null, false, false), "Date d in range (null,null)");

        // check out of range
        Assert.assertFalse(DateUtils.isDateBetween(now, now, now, false, false), "Date d in range (d,d)");
        Assert.assertFalse(DateUtils.isDateBetween(now, now, null, false, false), "Date d in range (d,null)");
        Assert.assertFalse(DateUtils.isDateBetween(past, now, null, true, false), "Date d in range <d-1,null)");
        Assert.assertFalse(DateUtils.isDateBetween(now, null, now, false, false), "Date d in range (null,d)");
        Assert.assertFalse(DateUtils.isDateBetween(past, null, future, false, true), "Date d in range (null,d+1>");
    }

    @Test
    public void diffBetweenSameDaysIsZero() {
        Date someDate = new Date();
        Date d1 = DateUtils.endOfDay(someDate);
        Date d2 = DateUtils.startOfDay(someDate);
        Assert.assertEquals(0, DateUtils.diffInDays(d1, d2), "Diff between " + d1 + ", " + d2);
    }

    @Test
    public void diffBetweenDifferentDays() {
        Date d1 = makeDateMidDay(2000, 12, 31);
        Date d2 = makeDate(2001, 01, 01);
        Assert.assertEquals(1, DateUtils.diffInDays(d1, d2), "Diff between " + d1 + ", " + d2);
    }

    @Test
    public void diffBetweenDifferentDaysIsSymetric() {
        Date d1 = makeDate(2000, 12, 31);
        Date d2 = makeDate(2001, 01, 01);
        Assert.assertEquals(DateUtils.diffInDays(d2, d1), DateUtils.diffInDays(d1, d2), "Diff between " + d1 + ", "
                + d2);
    }

    private Date makeDateMidDay(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 12, 12, 12);
        cal.set(Calendar.MILLISECOND, 99);
        return cal.getTime();
    }

}
