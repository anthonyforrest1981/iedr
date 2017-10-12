package pl.nask.crs.country.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.country.County;
import pl.nask.crs.country.dao.CountyDAO;

public class CountyIBatisDAO extends GenericIBatisDAO<County, Integer> implements CountyDAO {
    public CountyIBatisDAO() {
        setFindQueryId("country.findCounties");
        setCountFindQueryId("country.countFindCounties");
        setGetQueryId("country.getCounty");
    }
}
