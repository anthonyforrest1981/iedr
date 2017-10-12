package pl.nask.crs.user.dao;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.UserHelper;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.search.UserSearchCriteria;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

@ContextConfiguration(locations = {"/users-config.xml", "/users-config-test.xml", "/test-config.xml",
        "/commons-base-config.xml"})
public class UserDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    private static UserSearchCriteria criteria = null;

    @Autowired
    UserDAO userDAO;

    @Autowired
    HistoricalUserDAO historicalUserDAO;

    @Resource
    HashAlgorithm saltedHashAlg;

    @Test
    public void getUser() {
        String nicHandleId = "IH4-IEDR";
        User user = userDAO.get(nicHandleId);
        UserHelper.compareUsers(user, getExpectedIh4());
    }

    @Test
    public void getUserBadUsername() {
        User u = userDAO.get("bad username");
        assertNull(u);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getUserNullUsername() {
        User u = userDAO.get(null);
        assertNull(u);
    }

    @Test
    public void getUserCaseInsensitive() {
        User u = userDAO.get("ih4-iEDr");
        assertEquals("IH4-IEDR", u.getUsername());
        assertEquals(6, u.getPermissionGroups().size());
    }

    @Test
    public void findAll() {
        SearchResult<User> result = userDAO.find(criteria);
        assertEquals(46, result.getResults().size());
        User user = result.getResults().get(0);
        UserHelper.compareUsers(user, userDAO.get("AA11-IEDR"));
    }

    @Test
    public void findLimited() {
        LimitedSearchResult<User> result = userDAO.find(criteria, 1, 2);
        assertEquals(2, result.getResults().size());
        User user = result.getResults().get(0);
        UserHelper.compareUsers(user, userDAO.get("AAA22-IEDR"));
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("A007-NASK");
        String salt = saltedHashAlg.getSalt();
        user.setPassword(saltedHashAlg.hashString("password123", salt));
        user.setSalt(salt);
        Group gr = new Group("Hostmaster", new HashSet<Permission>());
        Set<Group> groups = new HashSet<>();
        groups.add(gr);
        user.setPermissionGroups(groups);
        userDAO.create(user);
        user.setName("TEST");
        User dbUser = userDAO.get("A007-NASK");
        UserHelper.compareUsers(dbUser, user);
    }

    @Test
    public void updateUsingHistory() {
        String nicHandleId = "IH4-IEDR";
        UserHelper.compareUsers(userDAO.get(nicHandleId), getExpectedIh4());
        User modifiedUser = getModifiedIh4();
        long changeId = historicalUserDAO.create(modifiedUser, new Date(), TestOpInfo.DEFAULT.getActorName());
        userDAO.updateUsingHistory(changeId);
        UserHelper.compareUsers(userDAO.get(nicHandleId), modifiedUser);
    }

    @Test
    public void testPassResetToken() {
        String nicHandleId = "IH4-IEDR";
        Date aDate = DateUtils.addDays(new Date(), 1);
        userDAO.addPasswordReset(nicHandleId, "hash", aDate, 3, "0.0.0.0");
        PasswordResetToken token = userDAO.getToken("hash");
        assertTrue(token.isValid(nicHandleId));
        assertEquals(nicHandleId, token.getNicHandle());
        assertEquals("0.0.0.0", token.getRemoteIp());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), token.getExpires());
    }

    @Test
    public void testExpired() {
        String nicHandleId = "IH4-IEDR";
        Date aDate = DateUtils.addMilliseconds(new Date(), -1);
        userDAO.addPasswordReset(nicHandleId, "hash", aDate, 0, "0.0.0.0");
        assertFalse(userDAO.getToken("hash").isValid(nicHandleId));
    }

    @Test
    public void testNoAttemptsLeft() {
        String nicHandleId = "IH4-IEDR";
        Date aDate = DateUtils.addDays(new Date(), 1);
        userDAO.addPasswordReset(nicHandleId, "hash", aDate, 0, "0.0.0.0");
        assertFalse(userDAO.getToken("hash").isValid(nicHandleId));
    }

    private static User getExpectedIh4() {
        String nicHandleId = "IH4-IEDR";
        Set<Group> expectedGroups = new HashSet<>();
        expectedGroups.add(new Group("Registrar", new HashSet<Permission>()));
        expectedGroups.add(new Group("Finance", new HashSet<Permission>()));
        expectedGroups.add(new Group("Hostmaster", new HashSet<Permission>()));
        expectedGroups.add(new Group("HostmasterLead", new HashSet<Permission>()));
        expectedGroups.add(new Group("Batch", new HashSet<Permission>()));
        expectedGroups.add(new Group("TechnicalLead", new HashSet<Permission>()));
        Date passwordChangeDate = DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DATE), -12);
        Map<String, Permission> userPermissions = new HashMap<>();
        return new User(nicHandleId, "t1X7oZ2/bFDr0cyVoH442gyNoNSib/a", expectedGroups, false, ".Z805gDk7mXJJfbcIwZJde",
                passwordChangeDate, false, false, null, "IEDR Hostmaster", userPermissions);
    }

    private static User getModifiedIh4() {
        String nicHandleId = "IH4-IEDR";
        Set<Group> expectedGroups = new HashSet<>();
        expectedGroups.add(new Group("Direct", new HashSet<Permission>()));
        Date passwordChangeDate = DateUtils.truncate(new Date(), Calendar.DATE);
        Map<String, Permission> userPermissions = new HashMap<>();
        return new User(nicHandleId, "password", expectedGroups, false, "salt", passwordChangeDate, false, false, null,
                "IEDR Hostmaster", userPermissions);
    }

    @Test
    public void testCleanupResetPassword() {
        PasswordResetToken token = userDAO.getToken("20080408141236-341011");
        assertNotNull(token);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2008, Calendar.APRIL, 8, 14, 14, 0);
        userDAO.cleanupResetPassword(calendar.getTime());
        token = userDAO.getToken("20080408141236-341011");
        assertNull(token);
    }

}
