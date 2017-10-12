package pl.nask.crs.price.dao;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;

public interface DomainPricingDAO extends GenericDAO<DomainPrice, Integer> {

    List<DomainPrice> getDomainPriceList(Date forDate);

    DomainPrice getDomainPriceByCode(String productCode, Date forDate);

    DomainPrice getDomainPrice(int durationYears, Date forDate, Boolean isRegistration, Boolean isRenewal,
            boolean isDirect);

    List<DomainPrice> getAll();

    LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy);

}
