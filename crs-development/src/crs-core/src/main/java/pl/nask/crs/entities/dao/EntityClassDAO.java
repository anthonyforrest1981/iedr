package pl.nask.crs.entities.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.EntityClass;

public interface EntityClassDAO extends GenericDAO<EntityClass, Long> {

    EntityClass getClassByName(String className);

    boolean isClassMatchCategory(Long classId, Long categoryId);

    List<EntityClass> getAll();
}
