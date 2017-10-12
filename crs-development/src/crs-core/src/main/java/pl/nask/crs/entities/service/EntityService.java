package pl.nask.crs.entities.service;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.*;

public interface EntityService {

    List<EntityClass> getClasses();

    EntityClass getClass(Long id) throws HolderClassNotExistException;

    EntityClass getClassByName(String name) throws HolderClassNotExistException;

    List<EntityCategory> getCategories();

    EntityCategory getCategory(Long id) throws HolderCategoryNotExistException;

    EntityCategory getCategoryByName(String name) throws HolderCategoryNotExistException;

    EntitySubcategory getSubcategory(Long id) throws HolderSubcategoryNotExistException;

    void validateHolderEntities(EntityClass clazz, EntityCategory category) throws ClassDoesNotMatchCategoryException;

    void validateHolderEntities(EntityClass clazz, EntityCategory category,
            /*>>>@Nullable*/ EntitySubcategory subcategory)
            throws ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    OwnerType getOwnerType(Long id) throws OwnerTypeNotExistException;

    OwnerType getOwnerTypeByName(String name) throws OwnerTypeNotExistException;

    List<OwnerType> getAllOwnerTypes();

}
