package pl.nask.crs.entities.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.dao.EntityClassDAO;

public class EntityClassIBatisDAO extends GenericIBatisDAO<EntityClass, Long> implements EntityClassDAO {

    public EntityClassIBatisDAO() {
        setGetQueryId("entities.getClass");
    }

    public EntityClass getClassByName(String className) {
        return performQueryForObject("entities.getClassByName", className);
    }

    public boolean isClassMatchCategory(Long classId, Long categoryId) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("cId", classId);
        parameterMap.put("ctId", categoryId);
        return (Integer) performQueryForObject("entities.isClassMatchCategory", parameterMap) > 0;
    }

    @Override
    public List<EntityClass> getAll() {
        return performQueryForList("entities.getClassList");
    }
}
