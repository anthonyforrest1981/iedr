package pl.nask.crs.entities.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.dao.OwnerTypeDAO;

public class OwnerTypeIBatisDAO extends GenericIBatisDAO<OwnerType, Long>
        implements OwnerTypeDAO {

    public OwnerTypeIBatisDAO() {
        setGetQueryId("entities.getOwnerType");
    }

    @Override
    public OwnerType getByName(String name) {
        return performQueryForObject("entities.getOwnerTypeByName", name);
    }

    @Override
    public List<OwnerType> getAllOwnerTypes() {
        return performQueryForList("entities.getAllOwnerTypes");
    }

}
