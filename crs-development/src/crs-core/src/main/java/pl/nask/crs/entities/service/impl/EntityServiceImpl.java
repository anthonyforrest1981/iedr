package pl.nask.crs.entities.service.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.entities.dao.EntitySubcategoryDAO;
import pl.nask.crs.entities.dao.OwnerTypeDAO;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.entities.service.EntityService;

public class EntityServiceImpl implements EntityService {

    EntityClassDAO classDAO;
    EntityCategoryDAO categoryDAO;
    EntitySubcategoryDAO subcategoryDAO;
    OwnerTypeDAO ownerTypeDAO;

    public EntityServiceImpl(EntityClassDAO entityClassDAO, EntityCategoryDAO entityCategoryDAO,
            EntitySubcategoryDAO subcategoryDAO, OwnerTypeDAO ownerTypeDAO) {
        this.classDAO = entityClassDAO;
        this.categoryDAO = entityCategoryDAO;
        this.subcategoryDAO = subcategoryDAO;
        this.ownerTypeDAO = ownerTypeDAO;
    }

    public List<EntityClass> getClasses() {
        return classDAO.getAll();
    }

    public EntityClass getClass(Long id) throws HolderClassNotExistException {
        EntityClass clazz = classDAO.get(id);
        if (clazz == null) {
            throw new HolderClassNotExistException(id);
        }
        return clazz;
    }

    public EntityClass getClassByName(String name) throws HolderClassNotExistException {
        EntityClass clazz = classDAO.getClassByName(name);
        if (clazz == null) {
            throw new HolderClassNotExistException(name);
        }
        return clazz;
    }

    public List<EntityCategory> getCategories() {
        return categoryDAO.getAll();
    }

    public EntityCategory getCategory(Long id) throws HolderCategoryNotExistException {
        EntityCategory category = categoryDAO.get(id);
        if (category == null) {
            throw new HolderCategoryNotExistException(id);
        }
        return category;
    }

    public EntityCategory getCategoryByName(String name) throws HolderCategoryNotExistException {
        EntityCategory category = categoryDAO.getCategoryByName(name);
        if (category == null) {
            throw new HolderCategoryNotExistException(name);
        }
        return category;
    }

    @Override
    public EntitySubcategory getSubcategory(Long id) throws HolderSubcategoryNotExistException {
        EntitySubcategory subcategory = subcategoryDAO.get(id);
        if (subcategory == null) {
            throw new HolderSubcategoryNotExistException(id);
        }
        return subcategory;
    }

    @Override
    public void validateHolderEntities(EntityClass clazz, EntityCategory category)
            throws ClassDoesNotMatchCategoryException {
        Validator.assertNotNull(clazz, "holder class");
        Validator.assertNotNull(category, "holder category");
        if (!classDAO.isClassMatchCategory(clazz.getId(), category.getId())) {
            throw new ClassDoesNotMatchCategoryException(clazz.getName(), category.getName());
        }
    }

    @Override
    public void validateHolderEntities(EntityClass clazz, EntityCategory category,
            /*>>>@Nullable*/ EntitySubcategory subcategory)
            throws ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        validateHolderEntities(clazz, category);
        if (subcategory != null && !categoryDAO.isCategoryMatchSubcategory(category.getId(), subcategory.getId())) {
            throw new CategoryDoesNotMatchSubcategoryException(category.getName(), subcategory.getName());
        }
    }

    @Override
    public OwnerType getOwnerType(Long id) throws OwnerTypeNotExistException {
        OwnerType ownerType = ownerTypeDAO.get(id);
        if (ownerType == null) {
            throw new OwnerTypeNotExistException(id);
        }
        return ownerType;
    }

    @Override
    public OwnerType getOwnerTypeByName(String name) throws OwnerTypeNotExistException {
        OwnerType ownerType = ownerTypeDAO.getByName(name);
        if (ownerType == null) {
            throw new OwnerTypeNotExistException(name);
        }
        return ownerType;
    }

    @Override
    public List<OwnerType> getAllOwnerTypes() {
        return ownerTypeDAO.getAllOwnerTypes();
    }

}
