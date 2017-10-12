package pl.nask.crs.entities.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.OwnerType;

public interface OwnerTypeDAO extends GenericDAO<OwnerType, Long> {

    OwnerType getByName(String name);

    List<OwnerType> getAllOwnerTypes();

}
