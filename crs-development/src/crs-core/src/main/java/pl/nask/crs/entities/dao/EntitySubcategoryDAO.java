package pl.nask.crs.entities.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.EntitySubcategory;

public interface EntitySubcategoryDAO extends GenericDAO<EntitySubcategory, Long> {

    List<EntitySubcategory> getAll();

    List<EntitySubcategory> getForCategory(long categoryId);

}
