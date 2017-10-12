package pl.nask.crs.security.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.*;
import pl.nask.crs.security.Cause;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.LoginLockedException;
import pl.nask.crs.security.authentication.impl.AuthenticationServiceImpl;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.testng.AssertJUnit.assertTrue;

public class AuthenticationLockTest {

    private AuthenticationService authService;
    private UserDAO mockDAO;
    private HashAlgorithm mockAlgorithm;
    private ApplicationConfig mockedAppConfig;
    private LoginAttemptDAO loginAttemptDAO;

    @Test
    public void authenticateUser() throws Exception {
        initSuccess(false);
        AuthenticatedUser u = authService
                .authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "crs");
        assertTrue("IH4-IEDR".equals(u.getUsername()));
    }

    @Test(expectedExceptions = LoginLockedException.class)
    public void authenticateUserLockedOn() throws Exception {
        initFailed(false);
        authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", true, null, true, "crs");
    }

    @Test
    public void authenticateUserLockedOff() throws Exception {
        initFailed(false);
        AuthenticatedUser u = authService
                .authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "crs");
        assertTrue("IH4-IEDR".equals(u.getUsername()));
    }

    @Test(expectedExceptions = LoginLockedException.class)
    public void authenticateUserWithTfa() throws Exception {
        initFailed(true);
        authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", true, "123123", true, "crs");
    }

    private void init(boolean useTfa) {
        mockDAO = createMock(UserDAO.class);
        mockAlgorithm = createMock(HashAlgorithm.class);
        mockedAppConfig = createMock(ApplicationConfig.class);
        loginAttemptDAO = createMock(LoginAttemptDAO.class);
        authService = new AuthenticationServiceImpl(mockDAO, Collections.singletonList(mockAlgorithm), mockedAppConfig,
                loginAttemptDAO);
        expect(mockDAO.get("IH4-IEDR")).andStubReturn(createUser(useTfa));
        expect(mockDAO.get("ih4-iEdR")).andStubReturn(createUser(useTfa));
        expect(mockDAO.get("USER-NOT-IN-DB")).andStubReturn(null);
        expect(mockAlgorithm.hashString("123456", "fQMUoHUvZ4giN5rq9y4mEe")).andReturn(
                "p.KIBZJgwp1zaOriBKTX9dxvVis5.Pa");
        expect(mockAlgorithm.hashString("password not in db", "fQMUoHUvZ4giN5rq9y4mEe")).andReturn(
                "hashed password not in db");
        expect(mockedAppConfig.getGoogleAuthenticationConfig()).andReturn(createGAConfig());
    }

    private User createUser(boolean useTfa) {
        User u = new User();
        u.setUsername("IH4-IEDR");
        u.setPassword("p.KIBZJgwp1zaOriBKTX9dxvVis5.Pa");
        u.setSalt("fQMUoHUvZ4giN5rq9y4mEe");
        u.setUseTwoFactorAuthentication(useTfa);
        if (useTfa) {
            u.setSecret("BVSQGHOCX3DP6THY");
        }
        Permission perm = new FullAccessPermission("fullAccess", "fullAccess");
        Set<Permission> perms = new HashSet<>();
        perms.add(perm);
        Group gr = new Group("HostmasterLead", perms);
        Set<Group> permGrs = new HashSet<>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    private GAConfig createGAConfig() {
        GenericConfig genericConfig = new HardcodedGenericConfig();
        genericConfig.putEntry(new ImmutableConfigEntry("ga_pastIntervals", "3", ConfigEntry.ConfigValueType.INT));
        genericConfig.putEntry(new ImmutableConfigEntry("ga_futureIntervals", "3", ConfigEntry.ConfigValueType.INT));
        return new GAConfig(genericConfig);
    }

    private void initSuccess(boolean useTfa) {
        init(useTfa);
        expect(loginAttemptDAO.getLastAttemptByNic((String) anyObject())).andReturn(
                LoginAttempt.newSuccessInstance("IH4-IEDR", "1.1.1.1"));
        expect(loginAttemptDAO.createAttempt((LoginAttempt) anyObject())).andReturn(1L);
        replay(loginAttemptDAO);
        replay(mockDAO);
        replay(mockAlgorithm);
    }

    private void initFailed(boolean useTfa) {
        init(useTfa);
        expect(loginAttemptDAO.getLastAttemptByNic((String) anyObject())).andReturn(
                LoginAttempt.newFailedInstance("IH4-IEDR", "1.1.1.1", Cause.INVALID_PASSWORD, 5));
        expect(mockedAppConfig.getLoginLockoutConfig()).andReturn(new LoginLockoutConfig(5, 3, 60));
        expect(loginAttemptDAO.createAttempt((LoginAttempt) anyObject())).andReturn(1L);
        replay(mockedAppConfig);
        replay(loginAttemptDAO);
        replay(mockDAO);
        replay(mockAlgorithm);
    }

}
