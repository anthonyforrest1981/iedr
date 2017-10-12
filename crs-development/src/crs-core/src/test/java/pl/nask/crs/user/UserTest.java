package pl.nask.crs.user;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class UserTest {

    @Test
    public void testHasGroupString() {
        Set<Group> groups = new HashSet<Group>();
        groups.add(new Group("Registrar", Collections.EMPTY_SET));
        groups.add(new Group("Batch", Collections.EMPTY_SET));
        User u = new User();
        u.setPermissionGroups(groups);
        AssertJUnit.assertTrue(u.hasGroup("Registrar"));
        AssertJUnit.assertFalse(u.hasGroup("Technical"));
    }

    @Test
    public void testHasGroupLevel() {
        Set<Group> groups = new HashSet<Group>();
        groups.add(new Group("Registrar", Collections.EMPTY_SET));
        groups.add(new Group("Batch", Collections.EMPTY_SET));
        User u = new User();
        u.setPermissionGroups(groups);
        AssertJUnit.assertTrue(u.hasGroup(Level.Registrar));
        AssertJUnit.assertFalse(u.hasGroup("Technical"));
    }
}
