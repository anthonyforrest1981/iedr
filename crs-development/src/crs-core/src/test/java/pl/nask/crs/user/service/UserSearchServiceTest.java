package pl.nask.crs.user.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.ConvertingUserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.search.UserSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(locations = {"/users-config.xml", "/users-config-test.xml", "/test-config.xml",
        "/commons-base-config.xml"})
public class UserSearchServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    ConvertingUserDAO userDAO;

    @Autowired
    UserSearchService userSearchService;

    @Test
    public void findAll() {
        SearchResult<User> result = userSearchService.find(new UserSearchCriteria());
        AssertJUnit.assertEquals(46, result.getResults().size());
    }

    @Test
    public void findLimited() {
        LimitedSearchResult<User> result = userSearchService.find(new UserSearchCriteria(), 2, 3);
        AssertJUnit.assertEquals(result.getResults().size(), 3);
    }

    @Test
    public void get() {
        User user = userSearchService.get("AAB069-IEDR");
        AssertJUnit.assertEquals("AAB069-IEDR", user.getUsername());
        AssertJUnit.assertEquals("host Ireland Accounts Payable", user.getName());
        AssertJUnit.assertEquals(1, user.getPermissionGroups().size());

        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        HashSet<Permission> perms = new HashSet<>();
        perms.add(fullAccess);
        Group customerService = new Group("Batch", "Batch", perms);

        AssertJUnit.assertTrue(user.getPermissionGroups().contains(customerService));
    }

    @Test
    public void testFindInternalUsers() {
        List<String> internalUsers = userSearchService.findInternalUsers();
        assertEquals(internalUsers.size(), 1);
        assertEquals(internalUsers.get(0), "GEORGE-IEDR");
    }

    @Test
    public void testFindByNhMask() throws Exception {
        UserSearchCriteria criteria = new UserSearchCriteria();
        criteria.setNhLevelMask(Level.Hostmaster.getLevel() + Level.HostmasterLead.getLevel() +
                Level.TechnicalLead.getLevel());
        List<User> users = userSearchService.find(criteria).getResults();
        assertEquals(users.size(), 7);
        for (User user : users) {
            assertTrue(user.hasGroup(Level.Hostmaster) || user.hasGroup(Level.HostmasterLead)
                    || user.hasGroup(Level.TechnicalLead));
        }
        User user = users.get(0);
        AssertJUnit.assertEquals(user.getUsername(), "AAA906-IEDR");
        AssertJUnit.assertEquals(user.getName(), "ELive Technical");
        AssertJUnit.assertEquals(user.getPermissionGroups().size(), 1);
        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        HashSet<Permission> perms = new HashSet<>();
        perms.add(fullAccess);
        Group hostmaster = new Group("Hostmaster", perms);
        AssertJUnit.assertTrue(user.getPermissionGroups().contains(hostmaster));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getNull() {
        userSearchService.get(null);
    }

    @Test
    public void getNotExists() {
        AssertJUnit.assertNull(userSearchService.get("NOT-ESISTS"));
    }
}
