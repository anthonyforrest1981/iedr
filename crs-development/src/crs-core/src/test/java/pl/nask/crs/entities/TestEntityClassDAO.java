package pl.nask.crs.entities;

import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.entities.dao.EntityClassDAO;

/**
 * @author Artur Gniadzik
 * @author Kasia Fulara
 */
public class TestEntityClassDAO extends AbstractTest {

    @Resource
    private EntityClassDAO entityClassDAO;

    private static final long ENTITY_CLASS_ID = 1;

    @Test
    public void testGetAll() {
        List<EntityClass> result = entityClassDAO.getAll();
        AssertJUnit.assertNotNull("No results found", result);
        AssertJUnit.assertTrue("No results found", result.size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", result.get(0).getName());
    }

    @Test
    public void testGet() {
        EntityClass r = entityClassDAO.get(ENTITY_CLASS_ID);
        AssertJUnit.assertNotNull("No results found", r);
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
    }

    @Test
    public void testFindAllAndCheckUtf8() {
        List<EntityClass> result = entityClassDAO.getAll();
        AssertJUnit.assertEquals(10, result.size());
        EntityClass normalized = result.get(8);
        AssertJUnit.assertEquals("Normalized Clãss", normalized.getName());
        EntityClass unnormalized = result.get(9);
        AssertJUnit.assertEquals("Unnormalized Clãss", unnormalized.getName());
    }
}
