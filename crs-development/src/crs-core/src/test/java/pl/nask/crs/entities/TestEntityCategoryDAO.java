package pl.nask.crs.entities;

import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.entities.dao.EntityCategoryDAO;

public class TestEntityCategoryDAO extends AbstractTest {

    @Resource
    EntityCategoryDAO entityCategoryDAO;

    private static final long ENTITY_CLASS_ID = 1L;
    private static final long ENTITY_CATEGORY_ID = 1L;

    @Test
    public void testGetForClass() {
        List<EntityCategory> result = entityCategoryDAO.getForClass(ENTITY_CLASS_ID);
        AssertJUnit.assertNotNull("No results found 1", result);
        AssertJUnit.assertTrue("No results found 3", result.size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", result.get(0).getName());
    }

    @Test
    public void testGetAll() {
        List<EntityCategory> result = entityCategoryDAO.getAll();
        AssertJUnit.assertNotNull("No results found 1", result);
        AssertJUnit.assertTrue("No results found 3", result.size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", result.get(0).getName());
    }

    @Test
    public void testGet() {
        EntityCategory r = entityCategoryDAO.get(ENTITY_CATEGORY_ID);
        AssertJUnit.assertNotNull("No results found", r);
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
    }

    @Test
    public void testGetAllAndCheckUtf8() {
        List<EntityCategory> result = entityCategoryDAO.getAll();
        AssertJUnit.assertEquals(13, result.size());
        EntityCategory normalized = result.get(11);
        AssertJUnit.assertEquals("Normalized Cateǵōry", normalized.getName());
        EntityCategory unnormalized = result.get(12);
        AssertJUnit.assertEquals("Unnormalized Cateǵōry", unnormalized.getName());
    }
}
