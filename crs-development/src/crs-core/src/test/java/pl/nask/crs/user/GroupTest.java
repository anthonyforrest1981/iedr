package pl.nask.crs.user;

import java.util.Collections;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.user.permissions.*;

public class GroupTest {

    private Level anyLevel = Level.Batch;

    private Permission denyPermission = new NamedPermission("denyPermission", "denyPermission") {
        @Override
        public boolean implies(PermissionQuery query) throws PermissionDeniedException {
            return false;
        }
    };

    private Permission throwExPermission = new NamedPermission("throwExPermission", "throwExPermission") {
        @Override
        public boolean implies(PermissionQuery query) throws PermissionDeniedException {
            throw new PermissionDeniedException();
        }
    };

    private Permission acceptsPermission = new NamedPermission("acceptsPermission", "acceptsPermission") {
        @Override
        public boolean implies(PermissionQuery query) throws PermissionDeniedException {
            return true;
        }
    };

    private PermissionQuery anyQuery = new NamedPermissionQuery("anyQuery");

    @Test
    public void testConstructors() {
        Group g = new Group(anyLevel, "Batch", Collections.EMPTY_SET);
        Group g2 = new Group(anyLevel.getName(), "Batch", Collections.EMPTY_SET);

        AssertJUnit.assertEquals(g, g2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorIllegalName() {
        new Group("nieistniejacaGrupa", Collections.EMPTY_SET);
    }

    @Test
    public void groupNameShouldBeSameAsLevelName() {
        Group g = new Group(anyLevel, "label", Collections.EMPTY_SET);
        AssertJUnit.assertEquals(anyLevel.getName(), g.getName());
    }

    @Test
    public void groupIdShouldBeSameAsLevelName() {
        Group g = new Group(anyLevel, "label", Collections.EMPTY_SET);
        AssertJUnit.assertEquals(anyLevel.getName(), g.getName());
    }

    @Test
    public void hasPermissionShouldReturnTrueIfOneOfWrapperPermissionsAcceptsTheQuery() throws Exception {
        Group g = new Group(anyLevel, "label", CollectionUtils.arrayAsSet(denyPermission, throwExPermission,
                acceptsPermission));
        AssertJUnit.assertTrue(g.hasPermission(anyQuery));
    }

    @Test
    public void hasPermissionShouldReturnFalseIfNoneOfWrapperPermissionsAcceptsTheQuery() throws Exception {
        Group g = new Group(anyLevel, "label", CollectionUtils.arrayAsSet(denyPermission));
        AssertJUnit.assertFalse(g.hasPermission(anyQuery));
    }

    @Test(expectedExceptions = PermissionDeniedException.class)
    public void hasPermissionShouldThrowExceptionIfPermissionIsNotGrantedAndExceptionIsThrown() throws Exception {
        Group g = new Group(anyLevel, "label", CollectionUtils.arrayAsSet(denyPermission, throwExPermission));
        g.hasPermission(anyQuery);
    }
}
