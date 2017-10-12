package pl.nask.crs.entities.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.EntityCategory;

public interface EntityCategoryDAO extends GenericDAO<EntityCategory, Long> {

    EntityCategory getCategoryByName(String categoryName);

    List<EntityCategory> getAll();

    List<EntityCategory> getForClass(long classId);

    boolean isCategoryMatchSubcategory(long categoryId, long subcategoryId);

}
