package pl.nask.crs.entities.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.dao.EntitySubcategoryDAO;

public class EntitySubcategoryIBatisDAO extends GenericIBatisDAO<EntitySubcategory, Long>
        implements EntitySubcategoryDAO {

    public EntitySubcategoryIBatisDAO() {
        setGetQueryId("entities.getSubcategory");
    }

    public List<EntitySubcategory> getAll() {
        return performQueryForList("entities.getAllSubcategories");
    }

    public List<EntitySubcategory> getForCategory(long categoryId) {
        return performQueryForList("entities.getSubcategoryList", categoryId);
    }

}
