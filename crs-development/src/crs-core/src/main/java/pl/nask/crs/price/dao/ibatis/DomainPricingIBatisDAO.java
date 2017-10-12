package pl.nask.crs.price.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.dao.DomainPricingDAO;

public class DomainPricingIBatisDAO extends GenericIBatisDAO<DomainPrice, Integer> implements DomainPricingDAO {

    private static final Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("id", "id");
        sortingMap.put("code", "code");
        sortingMap.put("description", "description");
        sortingMap.put("price", "price");
        sortingMap.put("duration", "duration");
        sortingMap.put("defaultPrice", "defaultPrice");
        sortingMap.put("validFrom", "validFrom");
        sortingMap.put("validTo", "validTo");
        sortingMap.put("forRegistration", "forRegistration");
        sortingMap.put("forRenewal", "forRenewal");
        sortingMap.put("direct", "direct");
    }

    public DomainPricingIBatisDAO() {
        setCreateQueryId("domain-price.create");
        setGetQueryId("domain-price.getById");
        setUpdateQueryId("domain-price.update");

        setSortMapping(sortingMap);
    }

    @Override
    public List<DomainPrice> getDomainPriceList(Date forDate) {
        return performQueryForList("domain-price.getDomainPriceList", forDate);
    }

    @Override
    public DomainPrice getDomainPriceByCode(String productCode, Date forDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("productCode", productCode);
        params.put("forDate", forDate);
        return performQueryForObject("domain-price.getDomainPriceByCode", params);
    }

    @Override
    public DomainPrice getDomainPrice(int durationYears, Date forDate, Boolean isRegistration, Boolean isRenewal,
            boolean isDirect) {
        Map<String, Object> params = new HashMap<>();
        params.put("durationYears", durationYears);
        params.put("forDate", forDate);
        params.put("forRegistration", isRegistration);
        params.put("forRenewal", isRenewal);
        params.put("isDirect", isDirect);
        return performQueryForObject("domain-price.getDomainPrice", params);
    }

    @Override
    public List<DomainPrice> getAll() {
        return performQueryForList("domain-price.getAll");
    }

    @Override
    public LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy) {
        return performFind("domain-price.findAll", "domain-price.countFindAll", new SearchCriteria<DomainPrice>() {},
                offset, limit, sortBy);
    }
}
