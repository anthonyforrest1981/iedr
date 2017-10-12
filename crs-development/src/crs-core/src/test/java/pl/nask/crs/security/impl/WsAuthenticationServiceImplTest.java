package pl.nask.crs.security.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.authentication.impl.WsAuthenticationServiceImpl;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.LoginPermission;
import pl.nask.crs.user.permissions.Permission;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

public class WsAuthenticationServiceImplTest {

    private WsAuthenticationService authService;
    private UserDAO mockDAO;
    private HashAlgorithm mockAlgorithm;
    private ApplicationConfig mockAppConfig;
    private LoginAttemptDAO loginAttemptDAO;

    @BeforeMethod
    public void init() {
        mockDAO = createMock(UserDAO.class);
        mockAlgorithm = createMock(HashAlgorithm.class);
        mockAppConfig = createMock(ApplicationConfig.class);
        loginAttemptDAO = createMock(LoginAttemptDAO.class);
        authService = new WsAuthenticationServiceImpl(mockDAO, Arrays.asList(mockAlgorithm), mockAppConfig,
                loginAttemptDAO);
        expect(mockAppConfig.getUserSessionTimeout()).andStubReturn(2);
        expect(mockAppConfig.getPasswordExpiryPeriod()).andStubReturn(10);
        expect(mockDAO.get("IH4-IEDR")).andStubReturn(createSuperUser());
        expect(mockDAO.get("IDL2-IEDR")).andStubReturn(createUserWithExpiredPassword());
        expect(mockDAO.get("G1-IEDR")).andStubReturn(createUser());
        expect(mockDAO.get("G2-IEDR")).andStubReturn(null);
        expect(mockDAO.get("G3-IEDR")).andStubReturn(createUserWithNoLoginPermissions());
        expect(mockDAO.get("USER-NOT-IN-DB")).andStubReturn(null);
        expect(mockAlgorithm.hashString("123456", "wMskkVE8gIIZ/0HFaVeBtu")).andStubReturn(
                "9uEqdZSZtPSqK0C07C5v48iOhUr3vtG");
        expect(loginAttemptDAO.createAttempt((LoginAttempt) anyObject())).andReturn(1L).times(1);
        expect(loginAttemptDAO.getLastAttemptByNic((String) anyObject())).andReturn(
                LoginAttempt.newSuccessInstance("IH4-IEDR", "1.1.1.1")).times(1);
        replay(mockDAO);
        replay(mockAlgorithm);
        replay(mockAppConfig);
        replay(loginAttemptDAO);
    }

    private User createSuperUser() {
        User u = new User();
        u.setUsername("IH4-IEDR");
        u.setPassword("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG");
        u.setSalt("wMskkVE8gIIZ/0HFaVeBtu");
        Permission perm = new FullAccessPermission("fullAccess", "fullAccess");
        Set<Permission> perms = new HashSet<>();
        perms.add(perm);
        Group gr = new Group("HostmasterLead", perms);
        Set<Group> permGrs = new HashSet<>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    private User createUser() {
        User u = new User();
        u.setUsername("G1-IEDR");
        u.setPassword("69bac2841e83eb64");
        Permission perm = new LoginPermission(LoginPermission.WS);
        Set<Permission> perms = new HashSet<>();
        perms.add(perm);
        Group gr = new Group("Direct", perms);
        Set<Group> permGrs = new HashSet<>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    private User createUserWithNoLoginPermissions() {
        User u = new User();
        u.setUsername("G1-IEDR");
        u.setPassword("69bac2841e83eb64");
        Set<Group> permGrs = new HashSet<>();
        u.setPermissionGroups(permGrs);
        return u;
    }

    private User createUserWithExpiredPassword() {
        User u = new User();
        u.setUsername("IDL2-IEDR");
        Set<Group> permissionGroups = new HashSet<>();
        Permission permission = new LoginPermission(LoginPermission.WS);
        Set<Permission> permissions = new HashSet<>();
        permissions.add(permission);
        permissionGroups.add(new Group("Direct", permissions));
        u.setPermissionGroups(permissionGroups);
        u.setPassword("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG");
        u.setSalt("wMskkVE8gIIZ/0HFaVeBtu");
        u.setPasswordChangeDate(DateUtils.addDays(new Date(), -11));
        return u;
    }

    @Test
    public void switchUserTest() throws Exception {
        AuthenticatedUser user;
        boolean isSwitched;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        AssertJUnit.assertEquals("IH4-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertFalse(isSwitched);

        user = authService.switchUser(user, "G1-IEDR");
        AssertJUnit.assertEquals("G1-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);

        user = authService.unswitch(user);
        AssertJUnit.assertEquals("IH4-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertFalse(isSwitched);
    }

    @Test(expectedExceptions = InvalidUsernameOrPasswordException.class)
    public void switchToInvalidUserTest() throws Exception {
        AuthenticatedUser user;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        authService.switchUser(user, "G2-IEDR");
    }

    @Test(expectedExceptions = UserNotSwitchedException.class)
    public void unswitchWhenNoSwitchTest() throws Exception {
        AuthenticatedUser user;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        authService.unswitch(user);
    }

    @Test
    public void authenticateAndSwitchUserTest() throws Exception {
        AuthenticatedUser user = authService.authenticateAndSwitchUser("IH4-IEDR", "G1-IEDR", "123456", false,
                "1.1.1.1", false);
        AssertJUnit.assertNotNull(user);
        boolean isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);
    }

    @Test(expectedExceptions = PasswordExpiredException.class)
    public void authenticateUserPasswordExpired() throws Exception {
        authService.authenticate("IDL2-IEDR", "123456", true, "1.1.1.1", false, null, true, "ws");
    }
}
