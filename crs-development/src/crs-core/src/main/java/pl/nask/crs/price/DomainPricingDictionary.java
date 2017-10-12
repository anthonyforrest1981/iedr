package pl.nask.crs.price;

import java.util.*;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.price.dao.DomainPricingDAO;

/**
 * Represents domain pricing in a particular moment of time.
 * The instance of this class will retrieve all prices at the creation time and keep them so no changes in the domain pricing made in the mean time will be visible.
 * A new instance must be created in order to get the current prices.
 *
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPricingDictionary implements Dictionary<Integer, DomainPrice> {

    /**
     * The date from which (inclusively) the product price applies
     */
    private Date forDate;
    private List<DomainPrice> priceList;
    private Map<Integer, DomainPrice> priceMap;

    public DomainPricingDictionary(DomainPricingDAO domainPricingDAO) {
        this(domainPricingDAO, new Date());
    }

    public DomainPricingDictionary(DomainPricingDAO domainPricingDAO, Date forDate) {
        Validator.assertNotNull(domainPricingDAO, "domain pricing DAO");
        Validator.assertNotNull(forDate, "forDate");
        this.priceList = domainPricingDAO.getDomainPriceList(forDate);
        this.priceMap = buildMap(priceList);
        this.forDate = forDate;
    }

    private Map<Integer, DomainPrice> buildMap(List<DomainPrice> priceList) {
        Map<Integer, DomainPrice> res = new HashMap<>();
        for (DomainPrice p : priceList) {
            res.put(p.getId(), p);
        }
        return res;
    }

    @Override
    public DomainPrice getEntry(Integer productId) {
        Validator.assertNotNull(productId, "category param");
        return priceMap.get(productId);
    }

    @Override
    public List<DomainPrice> getEntries() {
        return new ArrayList<>(priceList);
    }

    public Date getDate() {
        return forDate;
    }
}
