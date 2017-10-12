package pl.nask.crs.entities;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.entities.service.EntityService;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class EntityServiceTest extends AbstractTest {

    @Resource
    private EntityService entitiesService;

    @Test
    public void checkHolderEntitiesByNamesTest() throws Exception {
        EntityClass clazz = entitiesService.getClassByName("Natural Person");
        EntityCategory category = entitiesService.getCategoryByName("Personal Name");
        entitiesService.validateHolderEntities(clazz, category);
    }

    @Test
    public void getHolderClassByNameTest() throws Exception {
        EntityClass clazz = entitiesService.getClassByName("Natural Person");
        assertEquals(clazz.getId(), 1L);
        assertEquals(clazz.getCategories().size(), 4);
    }

    @Test
    public void getHolderCategoryByNameTest() throws Exception {
        EntityCategory category = entitiesService.getCategoryByName("Discretionary Name");
        assertEquals(category.getId(), 11L);
        assertEquals(category.getSubcategories().size(), 10);
    }

    @Test(expectedExceptions = HolderClassNotExistException.class)
    public void holderClassNotExistByNameTest() throws Exception {
        entitiesService.getClassByName("Not existing class");
    }

    @Test(expectedExceptions = HolderCategoryNotExistException.class)
    public void holderCategoryNotExistsByNameTest() throws Exception {
        entitiesService.getCategoryByName("Not existing category");
    }

    @Test
    public void getHolderClassByIdTest() throws Exception {
        EntityClass clazz = entitiesService.getClass(1L);
        assertEquals(clazz.getName(), "Natural Person");
    }

    @Test
    public void getHolderCategoryByIdTest() throws Exception {
        EntityCategory category = entitiesService.getCategory(1L);
        assertEquals(category.getName(), "Personal Name");
    }

    @Test
    public void getHolderSubcategoryByIdTest() throws Exception {
        EntitySubcategory subcategory = entitiesService.getSubcategory(2L);
        assertEquals(subcategory.getName(), "Blog / Informative website");
    }

    @Test(expectedExceptions = HolderClassNotExistException.class)
    public void holderClassNotExistByIdTest() throws Exception {
        entitiesService.getClass(-1L);
    }

    @Test(expectedExceptions = HolderCategoryNotExistException.class)
    public void holderCategoryNotExistsByIdTest() throws Exception {
        entitiesService.getCategory(-1L);
    }

    @Test(expectedExceptions = ClassDoesNotMatchCategoryException.class)
    public void holderClassDoesNotMatchCategoryTest() throws Exception {
        EntityClass clazz = entitiesService.getClassByName("Natural Person");
        EntityCategory category = entitiesService.getCategoryByName("Personal Trading Name");
        entitiesService.validateHolderEntities(clazz, category);
    }

    @Test(expectedExceptions = CategoryDoesNotMatchSubcategoryException.class)
    public void holderCategoryDoesNotMatchSubcategoryTest() throws Exception {
        EntityClass clazz = entitiesService.getClassByName("Natural Person");
        EntityCategory category = entitiesService.getCategoryByName("Personal Name");
        EntitySubcategory subcategory = entitiesService.getSubcategory(1L);
        entitiesService.validateHolderEntities(clazz, category, subcategory);
    }

    @Test
    public void getClassesTest() {
        List<EntityClass> entities = entitiesService.getClasses();
        assertEquals(entities.size(), 10);
    }

    @Test
    public void getCategoriesTest() {
        List<EntityCategory> entities = entitiesService.getCategories();
        assertEquals(entities.size(), 13);
    }

    @Test
    public void getOwnerType() throws Exception {
        OwnerType ownerType = entitiesService.getOwnerType(1L);
        assertEquals(ownerType.getId(), 1L);
        assertEquals(ownerType.getName(), "Company");
        assertEquals(ownerType.getClassId(), 2L);
        assertEquals(ownerType.getCategoryId(), 11L);
    }

    @Test(expectedExceptions = OwnerTypeNotExistException.class)
    public void getNonexistentOwnerType() throws Exception {
        entitiesService.getOwnerType(100L);
    }

    @Test
    public void getOwnerTypeByName() throws Exception {
        OwnerType ownerType = entitiesService.getOwnerTypeByName("Company");
        assertEquals(ownerType.getId(), 1L);
        assertEquals(ownerType.getName(), "Company");
        assertEquals(ownerType.getClassId(), 2L);
        assertEquals(ownerType.getCategoryId(), 11L);
    }

    @Test(expectedExceptions = OwnerTypeNotExistException.class)
    public void getOwnerTypeByNonexistentName() throws Exception {
        entitiesService.getOwnerTypeByName("Nonexistent type");
    }

    @Test
    public void getAllOwnerTypes() throws Exception {
        List<OwnerType> ownerTypes = entitiesService.getAllOwnerTypes();
        assertNotNull(ownerTypes);
        assertFalse(ownerTypes.isEmpty());
    }

}
