package pl.nask.crs.price;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPricingDictionaryTest extends AbstractTest {

    private Dictionary<Integer, DomainPrice> domainPricingDictionary;

    @Resource
    private DomainPricingDAO domainPricingDAO;

    @BeforeMethod
    public void prepareDomainPricingDictionary() {
        domainPricingDictionary = new DomainPricingDictionary(domainPricingDAO);
    }

    @Test
    public void getAllTest() {
        List<DomainPrice> prices = domainPricingDictionary.getEntries();
        AssertJUnit.assertEquals("total prices", 22, prices.size());
    }

    @Test
    public void getByCodeTest() {
        int productId = domainPricingDAO.getDomainPriceByCode("Std1Year", new Date()).getId();
        DomainPrice domainPrice = domainPricingDictionary.getEntry(productId);
        AssertJUnit.assertEquals("description", "Registration of your domain for 1 year", domainPrice.getDescription());
        AssertJUnit.assertEquals("price", MoneyUtils.getRoundedAndScaledValue(65), domainPrice.getPrice()
        );
    }

    @Test
    public void getAllWithDateTest() {
        Date forDate = DateUtils.addYears(new Date(), -3);
        Dictionary<Integer, DomainPrice> dictionary = new DomainPricingDictionary(domainPricingDAO, forDate);
        List<DomainPrice> prices = dictionary.getEntries();
        AssertJUnit.assertEquals("total prices", 18, prices.size());
    }

    @Test
    public void getByCodeWithDateTest() {
        Date forDate = DateUtils.addYears(new Date(), -6);
        Dictionary<Integer, DomainPrice> dictionary = new DomainPricingDictionary(domainPricingDAO, forDate);
        int productId = domainPricingDAO.getDomainPriceByCode("RM6Yr", forDate).getId();
        DomainPrice domainPrice = dictionary.getEntry(productId);
        AssertJUnit.assertEquals("code", "RM6Yr", domainPrice.getCode());
        AssertJUnit.assertEquals("description", "Registration for 6 Year", domainPrice.getDescription());
        AssertJUnit.assertEquals("price", MoneyUtils.getRoundedAndScaledValue(120), domainPrice.getPrice());
    }
}
