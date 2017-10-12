package pl.nask.crs.user.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.*;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;

import static org.testng.Assert.*;

@ContextConfiguration(locations = {"/users-config.xml", "/users-config-test.xml", "/test-config.xml",
        "/commons-base-config.xml"})
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    HistoricalUserDAO historicalUserDAO;

    @Resource
    HashAlgorithm hashAlg;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationConfig applicationConfig;

    @Test
    public void testChangePassword() {
        String nicHandleId = "IH4-IEDR";
        String oldPassword = "Passw0rd!";
        String newPassword = "Passw0rd!modified";

        try {
            userService.changePassword(nicHandleId, newPassword, TestOpInfo.DEFAULT);
        } catch (PasswordAlreadyExistsException e1) {
            Assert.fail("First password change should not fail");
        }

        User user = userDAO.get(nicHandleId);
        AssertJUnit.assertEquals(hashAlg.hashString(newPassword, user.getSalt()), user.getPassword());

        try {
            userService.changePassword(nicHandleId, newPassword, TestOpInfo.DEFAULT);
            Assert.fail("Second password change should fail");
        } catch (PasswordAlreadyExistsException e) {
            // exception expected
        }

        try {
            userService.changePassword(nicHandleId, newPassword, oldPassword, TestOpInfo.DEFAULT);
        } catch (PasswordAlreadyExistsException | InvalidOldPasswordException e) {
            Assert.fail("Password change with old pass should pass");
        }

        user = userDAO.get(nicHandleId);
        AssertJUnit.assertEquals(hashAlg.hashString(oldPassword, user.getSalt()), user.getPassword());

        try {
            userService.changePassword(nicHandleId, "wrong pass", "1234", TestOpInfo.DEFAULT);
        } catch (PasswordAlreadyExistsException e) {
            Assert.fail("Password change with old pass should pass");
        } catch (InvalidOldPasswordException e) {
            //expected exception
        }
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testChangePasswordDate() throws Exception {
        Date startDate = DateUtils.truncate(new Date(), Calendar.SECOND);
        String nicHandleId = "AAI538-IEDR";
        User user = userDAO.get(nicHandleId);
        assertNull(user);
        userService.changePassword(nicHandleId, "Passw0rd!", TestOpInfo.DEFAULT);
        user = userDAO.get(nicHandleId);
        assertNotNull(user);
        assertFalse(user.getPasswordChangeDate().before(startDate));
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testChangePermissionGroups() {
        String testedUserNh = "AAB069-IEDR";
        User user = userDAO.get(testedUserNh);
        HashSet<Permission> fullAccessSet = new HashSet<>();
        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        fullAccessSet.add(fullAccess);
        Group customerService = new Group("Batch", fullAccessSet);
        AssertJUnit.assertEquals(user.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user.getPermissionGroups().contains(customerService));

        HashSet<Group> newPermissionGroup = new HashSet<>();
        HashSet<Permission> emptySet = new HashSet<>();
        Group reseller = new Group("Registrar", "Reseller", emptySet);
        newPermissionGroup.add(reseller);

        userService.changePermissionGroups(user.getUsername(), newPermissionGroup, new TestOpInfo("TEST-IEDR"));

        User user2 = userDAO.get(testedUserNh);
        AssertJUnit.assertEquals(user2.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user2.getPermissionGroups().contains(reseller));
        compareWithHistory(testedUserNh);
    }

    @Test
    public void testChangePermissionGroupsUserWithNoEntry() {
        String nicHandleId = "A007-NASK";
        assertNull(userDAO.get(nicHandleId));
        HashSet<Group> permissionGroups = new HashSet<>();
        permissionGroups.add(new Group("Registrar", new HashSet<Permission>()));
        userService.changePermissionGroups(nicHandleId, permissionGroups, TestOpInfo.DEFAULT);
        User user = userDAO.get(nicHandleId);
        assertNotNull(user);
        assertEquals(user.getPermissionGroups(), permissionGroups);
        compareWithHistory(nicHandleId);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullUser() {
        userService.changePermissionGroups(null, null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullGroup() {
        User user = userDAO.get("AAA442-IEDR");
        userService.changePermissionGroups(user.getUsername(), null, new TestOpInfo("TEST-IEDR"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullOpInfo() {
        User user = userDAO.get("AAA442-IEDR");
        HashSet<Group> groups = new HashSet<>();
        userService.changePermissionGroups(user.getUsername(), groups, null);
    }

    @Test
    public void testChangePermissionGroupsEmptyGroups() {
        User user = userDAO.get("AAA442-IEDR");
        HashSet<Group> groups = new HashSet<>();
        userService.changePermissionGroups(user.getUsername(), groups, new TestOpInfo("TEST-IEDR"));
    }

    @Test
    public void testChangeUseTfa() {
        String nic = "AAA442-IEDR";
        User user = userDAO.get(nic); // Default in DB at the moment is NOT to use two factor
        boolean originalTfaFlag = user.isUseTwoFactorAuthentication();

        String secret = userService.changeTfa(nic, !originalTfaFlag, TestOpInfo.DEFAULT);
        AssertJUnit.assertEquals(!originalTfaFlag, userDAO.get(nic).isUseTwoFactorAuthentication());
        Assert.assertNotNull(secret);

        secret = userService.changeTfa(nic, originalTfaFlag, TestOpInfo.DEFAULT);
        AssertJUnit.assertEquals(originalTfaFlag, userDAO.get(nic).isUseTwoFactorAuthentication());
        assertNull(secret);
        compareWithHistory(nic);
    }

    @Test
    public void testChangeUseTfaUserWithNoEntry() {
        String nicHandleId = "A007-NASK";
        assertNull(userDAO.get(nicHandleId));
        userService.changeTfa(nicHandleId, true, TestOpInfo.DEFAULT);
        User user = userDAO.get(nicHandleId);
        assertNotNull(user);
        assertTrue(user.isUseTwoFactorAuthentication());
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testAddUserPermission() {
        String nicHandleId = "IH4-IEDR";
        String permissionName = "fullAccess";
        User user = userDAO.get(nicHandleId);
        assertEquals(user.getPermissions().size(), 0);
        userService.addUserPermission(nicHandleId, permissionName);
        user = userDAO.get(nicHandleId);
        assertEquals(user.getPermissions().size(), 1);
        user.getPermissions().containsKey(permissionName);
        Permission permission = user.getPermissions().get(permissionName);
        assertEquals(permission.getName(), permissionName);
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testRemoveUserPermission() {
        String nicHandleId = "IH4-IEDR";
        String permissionName = "fullAccess";
        userService.addUserPermission(nicHandleId, permissionName);
        User user = userDAO.get(nicHandleId);
        assertEquals(user.getPermissions().size(), 1);
        user.getPermissions().containsKey(permissionName);

        userService.removeUserPermission(nicHandleId, permissionName);
        user = userDAO.get(nicHandleId);
        assertEquals(user.getPermissions().size(), 0);
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testResetPassword() {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String ip = "ip";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        Date expiration = DateUtils.addMinutes(new Date(), applicationConfig.getPasswordResetTokenExpiry());
        userService.resetPassword(nicHandleId, tokenString, ip, TestOpInfo.DEFAULT);
        PasswordResetToken token = userDAO.getToken(hashedToken);
        assertNotNull(token);
        assertEquals(token.getNicHandle(), nicHandleId);
        assertEquals(token.getRemoteIp(), ip);
        assertTrue(token.getExpires().getTime() - expiration.getTime() < 1000);
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testGetUserFromToken() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userService.resetPassword(nicHandleId, tokenString, "ip", TestOpInfo.DEFAULT);
        assertNotNull(userDAO.getToken(hashedToken));
        User tokenUser = userService.getUserFromToken(tokenString, nicHandleId);
        assertEquals(tokenUser.getUsername(), nicHandleId);
        compareWithHistory(nicHandleId);
    }

    @Test(expectedExceptions = PasswordResetTokenExpiredException.class)
    public void testGetUserFromTokenInvalidNicHandle() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userService.resetPassword(nicHandleId, tokenString, "ip", TestOpInfo.DEFAULT);
        assertNotNull(userDAO.getToken(hashedToken));
        userService.getUserFromToken(tokenString, "OTHER-IEDR");
    }

    @Test
    public void testDecreaseTokenAttempts() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userService.resetPassword(nicHandleId, tokenString, "ip", TestOpInfo.DEFAULT);
        int attempts = applicationConfig.getPasswordResetTokenAttempts();
        for (int i = 0; i < attempts; i++) {
            assertTrue(userDAO.getToken(hashedToken).isValid(nicHandleId));
            userService.decreaseTokenAttempts(tokenString);
        }
        assertFalse(userDAO.getToken(hashedToken).isValid(nicHandleId));
    }

    @Test
    public void testInvalidateToken() {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userService.resetPassword(nicHandleId, tokenString, "ip", TestOpInfo.DEFAULT);
        assertNotNull(userDAO.getToken(hashedToken));
        userService.invalidateToken(tokenString);
        assertNull(userDAO.getToken(hashedToken));
    }

    @Test(expectedExceptions = PasswordResetTokenExpiredException.class)
    public void testUseExpiredToken() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        Date date = DateUtils.addDays(new Date(), -2);
        userDAO.addPasswordReset(nicHandleId, hashedToken, date, 3, "ip");
        assertNotNull(userDAO.getToken(hashedToken));
        userService.getUserFromToken(tokenString, nicHandleId);
        compareWithHistory(nicHandleId);
    }

    @Test(expectedExceptions = PasswordResetTokenExpiredException.class)
    public void testUseInvalidToken() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userDAO.addPasswordReset(nicHandleId, hashedToken, new Date(), 3, "ip");
        assertNotNull(userDAO.getToken(hashedToken));
        userDAO.invalidateToken(hashedToken);
        userService.getUserFromToken(tokenString, nicHandleId);
        compareWithHistory(nicHandleId);
    }

    @Test(expectedExceptions = PasswordResetTokenExpiredException.class)
    public void testUseTokenOfAnotherNicHandle() throws Exception {
        String nicHandleId = "IH4-IEDR";
        String tokenString = "token";
        String hashedToken = hashAlg.hashString(tokenString, "jSe9VrjJRosMn/hKdT6MPu");
        userDAO.addPasswordReset(nicHandleId, hashedToken, new Date(), 3, "ip");
        assertNotNull(userDAO.getToken(hashedToken));
        userService.getUserFromToken(tokenString, nicHandleId);
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testGetUserLevel() {
        String nicHandleId = "IH4-IEDR";
        assertEquals(userService.getUserLevel(nicHandleId), NRCLevel.REGISTRAR);
    }

    @Test
    public void testAddUserToGroup() {
        String nicHandleId = "AAA22-IEDR";
        User user = userDAO.get(nicHandleId);
        assertTrue(user.hasGroup(Level.Registrar));
        assertFalse(user.hasGroup(Level.Direct));
        userService.addUserToGroup(nicHandleId, Level.Direct, TestOpInfo.DEFAULT);
        user = userDAO.get(nicHandleId);
        assertTrue(user.hasGroup(Level.Registrar));
        assertTrue(user.hasGroup(Level.Direct));
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testSetUserGroup() {
        String nicHandleId = "AAA22-IEDR";
        User user = userDAO.get(nicHandleId);
        assertTrue(user.hasGroup(Level.Registrar));
        assertFalse(user.hasGroup(Level.Direct));
        userService.setUserGroup(nicHandleId, Level.Direct, TestOpInfo.DEFAULT);
        user = userDAO.get(nicHandleId);
        assertFalse(user.hasGroup(Level.Registrar));
        assertTrue(user.hasGroup(Level.Direct));
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testGenerateSecret() {
        String nicHandleId = "IH4-IEDR";
        User user = userDAO.get(nicHandleId);
        assertNull(user.getSecret());
        String secret = userService.generateSecret(nicHandleId, TestOpInfo.DEFAULT);
        user = userDAO.get(nicHandleId);
        assertNotNull(secret);
        assertEquals(user.getSecret(), secret);
        compareWithHistory(nicHandleId);
    }

    @Test
    public void testGenerateSecretUserWithNoEntry() {
        String nicHandleId = "A007-NASK";
        assertNull(userDAO.get(nicHandleId));
        String secret = userService.generateSecret(nicHandleId, TestOpInfo.DEFAULT);
        User user = userDAO.get(nicHandleId);
        assertNotNull(user);
        assertNotNull(secret);
        assertEquals(user.getSecret(), secret);
        compareWithHistory(nicHandleId);
    }

    private void compareWithHistory(String nicHandleId) {
        User user = userDAO.get(nicHandleId);
        HistoricalUserSearchCriteria criteria = new HistoricalUserSearchCriteria(nicHandleId);
        LimitedSearchResult<HistoricalObject<User>> results =
                historicalUserDAO.find(criteria, 0, 1, Arrays.asList(new SortCriterion("changeDate", false)));
        User histUser = results.getResults().get(0).getObject();
        UserHelper.compareHistoricalUsers(user, histUser);
    }

}
