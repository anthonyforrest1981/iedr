package pl.nask.crs.entities.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;

public class EntityCategoryIBatisDAO extends GenericIBatisDAO<EntityCategory, Long> implements EntityCategoryDAO {

    public EntityCategoryIBatisDAO() {
        setGetQueryId("entities.getCategory");
    }

    public EntityCategory getCategoryByName(String categoryName) {
        return performQueryForObject("entities.getCategoryByName", categoryName);
    }

    public List<EntityCategory> getAll() {
        return performQueryForList("entities.getAllCategories");
    }

    public List<EntityCategory> getForClass(long classId) {
        return performQueryForList("entities.getCategoryList", classId);
    }

    @Override
    public boolean isCategoryMatchSubcategory(long categoryId, long subcategoryId) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("ctId", categoryId);
        parameterMap.put("sctId", subcategoryId);
        return (Integer) performQueryForObject("entities.isCategoryMatchSubcategory", parameterMap) > 0;
    }
}
