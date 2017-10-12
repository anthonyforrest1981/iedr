package pl.nask.crs.security;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.security.dao.LoginAttemptDAO;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class LoginAttemptDAOTest extends AbstractTest {

    @Resource
    LoginAttemptDAO dao;

    @Test
    public void testCreate() throws Exception {
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        LoginAttempt loginAttempt1 = LoginAttempt.newSuccessInstance("nic1", "1.1.1.1");
        LoginAttempt loginAttempt2 = LoginAttempt.newFailedInstance("nic2", "2.2.2.2", Cause.INVALID_PASSWORD, 10);
        loginAttempt1.setDate(aDate);

        long attemptId1 = dao.createAttempt(loginAttempt1);
        long attemptId2 = dao.createAttempt(loginAttempt2);

        LoginAttempt fromDB1 = dao.get(attemptId1);
        LoginAttempt fromDB2 = dao.get(attemptId2);

        assertEquals(loginAttempt1, fromDB1);
        assertEquals(loginAttempt2, fromDB2);

        AssertJUnit.assertEquals(0, fromDB1.getFailureCount());
        AssertJUnit.assertTrue(fromDB2.getFailureCount() > 0);
    }

    private void assertEquals(LoginAttempt expected, LoginAttempt actual) {
        AssertJUnit.assertNotNull(expected);
        AssertJUnit.assertNotNull(actual);
        AssertJUnit.assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        Assert.assertEquals(DateUtils.truncate(expected.getDate(), Calendar.SECOND), actual.getDate());
        AssertJUnit.assertEquals(expected.getClientIP(), actual.getClientIP());
        AssertJUnit.assertEquals(expected.getCause(), actual.getCause());
        AssertJUnit.assertEquals(expected.isSuccessful(), actual.isSuccessful());
    }

    @Test
    public void testGetLastByIP() throws Exception {
        LoginAttempt attempt = dao.getLastAttemptByClientIP("6.6.6.6");
        AssertJUnit.assertNotNull(attempt);
        AssertJUnit.assertEquals(2, attempt.getId());
        AssertJUnit.assertEquals("wrong", attempt.getNicHandleId());
        Assert.assertNotNull(attempt.getDate());
        AssertJUnit.assertEquals("6.6.6.6", attempt.getClientIP());
        AssertJUnit.assertEquals(Cause.INVALID_NIC, attempt.getCause());
        AssertJUnit.assertEquals(3, attempt.getFailureCount());
    }

    @Test
    public void testGetLastByNic() throws Exception {
        LoginAttempt attempt = dao.getLastAttemptByNic("wrong");
        AssertJUnit.assertNotNull(attempt);
        AssertJUnit.assertEquals(2, attempt.getId());
        AssertJUnit.assertEquals("wrong", attempt.getNicHandleId());
        Assert.assertNotNull(attempt.getDate());
        AssertJUnit.assertEquals("6.6.6.6", attempt.getClientIP());
        AssertJUnit.assertEquals(Cause.INVALID_NIC, attempt.getCause());
        AssertJUnit.assertEquals(3, attempt.getFailureCount());
    }

    @Test
    public void testUnnormalizedUtf8() {
        Date now = new Date();
        LoginAttempt attempt1 = LoginAttempt.newSuccessInstance("IDE\u03082-IEDR", "IPA\u0308ddress.1");
        LoginAttempt attempt2 = LoginAttempt.newSuccessInstance("IDE\u03082-IEDR", "IPA\u0308ddress.2");
        attempt1.setDate(DateUtils.addDays(now, -2));
        attempt2.setDate(DateUtils.addDays(now, -1));
        long id1 = dao.createAttempt(attempt1);
        long id2 = dao.createAttempt(attempt2);
        LoginAttempt dbAttempt1 = dao.get(id1);
        LoginAttempt dbAttempt2 = dao.get(id2);

        AssertJUnit.assertEquals(dbAttempt1.getNicHandleId(), "IDË2-IEDR");
        AssertJUnit.assertEquals(dbAttempt1.getClientIP(), "IPÄddress.1");
        AssertJUnit.assertEquals(dbAttempt2.getNicHandleId(), "IDË2-IEDR");
        AssertJUnit.assertEquals(dbAttempt2.getClientIP(), "IPÄddress.2");

        LoginAttempt lastByNicHandle = dao.getLastAttemptByNic("IDE\u03082-IEDR");
        AssertJUnit.assertEquals(lastByNicHandle.getNicHandleId(), "IDË2-IEDR");
        AssertJUnit.assertEquals(lastByNicHandle.getClientIP(), "IPÄddress.2");

        LoginAttempt lastByIp = dao.getLastAttemptByClientIP("IPA\u0308ddress.2");
        AssertJUnit.assertEquals(lastByIp.getNicHandleId(), "IDË2-IEDR");
        AssertJUnit.assertEquals(lastByIp.getClientIP(), "IPÄddress.2");
    }

    @Test
    public void testPreparedUnnormalizedUtf8() {
        LoginAttempt unnormalizedDb = dao.getLastAttemptByNic("IDE\u03082-IEDR-LAU");
        Assert.assertNotNull(unnormalizedDb);
        Assert.assertEquals(unnormalizedDb.getNicHandleId(), "IDË2-IEDR-LAU");
        Assert.assertEquals(unnormalizedDb.getClientIP(), "unnörmalized_ip");
        unnormalizedDb = dao.getLastAttemptByClientIP("unno\u0308rmalized_ip");
        Assert.assertNotNull(unnormalizedDb);
        Assert.assertEquals(unnormalizedDb.getNicHandleId(), "IDË2-IEDR-LAU");
        Assert.assertEquals(unnormalizedDb.getClientIP(), "unnörmalized_ip");

        LoginAttempt normalizedDb = dao.getLastAttemptByNic("IDE\u03082-IEDR-LAN");
        Assert.assertNotNull(normalizedDb);
        Assert.assertEquals(normalizedDb.getNicHandleId(), "IDË2-IEDR-LAN");
        Assert.assertEquals(normalizedDb.getClientIP(), "nörmalized_ip");
        normalizedDb = dao.getLastAttemptByClientIP("no\u0308rmalized_ip");
        Assert.assertNotNull(normalizedDb);
        Assert.assertEquals(normalizedDb.getNicHandleId(), "IDË2-IEDR-LAN");
        Assert.assertEquals(normalizedDb.getClientIP(), "nörmalized_ip");
    }

    @Test
    public void testCleanupLoginAttempts() {
        LoginAttempt attempt = dao.getLastAttemptByNic("wrong");
        assertNotNull(attempt);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, Calendar.JANUARY, 10, 16, 52, 0);
        dao.cleanupAttempts(calendar.getTime());
        attempt = dao.getLastAttemptByNic("wrong");
        assertNull(attempt);
    }
}
