package pl.nask.crs.price;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.price.dao.DomainPricingDAO;

public class DomainPricingUTF8DAOTest extends AbstractTest {

    @Resource
    DomainPricingDAO pricingDAO;

    @Test
    public void testGetUnnormalizedPrice() {
        // Check if domainPriceResult resultMap has string handlers (used in getDomainPriceByCode, getDomainPriceList,
        // getById, getAll, findAll selects).
        String productCode = "ÄStd11Year";
        List<DomainPrice> prices = pricingDAO.getDomainPriceList(new Date());
        DomainPrice price = findDomainPriceByCode(productCode, prices);
        Assert.assertNotNull(price, "Price with normalized code was not on the list");
        Assert.assertEquals(price.getCode(), productCode);
        Assert.assertEquals(price.getDescription(), "ÄRegistration of your domain for 11 years");
    }

    private DomainPrice findDomainPriceByCode(String productCode, List<DomainPrice> prices) {
        for (DomainPrice p : prices) {
            if (productCode.equals(p.getCode())) {
                return p;
            }
        }
        return null;
    }

    @Test
    public void testGetPriceByUnnormalizedCode() {
        // Check if getDomainPriceByCode select has string handlers.
        String productCode = "A\u0308Std11Year";
        DomainPrice price = pricingDAO.getDomainPriceByCode(productCode, new Date());
        Assert.assertNotNull(price);
        Assert.assertEquals(price.getCode(), "ÄStd11Year");
    }

    @Test
    public void testInsertUnnormalizedPrice() {
        // Check if create insert has string handlers.
        Date validFromDate = DateUtils.addDays(new Date(), -1);
        Date validToDate = DateUtils.addDays(new Date(), 1);
        DomainPrice price = DomainPrice.newInstance("A\u0308RM3Yr", "A\u0308Registration for 3 Years", BigDecimal
                .valueOf(66).setScale(2), 3, validFromDate, validToDate, true, true, true);
        pricingDAO.create(price);
        DomainPrice dbPrice = pricingDAO.getDomainPriceByCode("ÄRM3Yr", new Date());
        Assert.assertEquals(dbPrice.getCode(), "ÄRM3Yr");
        Assert.assertEquals(dbPrice.getDescription(), "ÄRegistration for 3 Years");
    }

    @Test
    public void testUpdatePriceWithUnnormalizedData() {
        // Check if update has string handlers.
        DomainPrice price =  pricingDAO.getDomainPriceByCode("ÄStd11Year", new Date());
        price.setDescription("A\u0308Registration for 11 Years updated");
        pricingDAO.update(price);
        price = pricingDAO.getDomainPriceByCode("ÄStd11Year", new Date());
        Assert.assertEquals(price.getCode(), "ÄStd11Year");
        Assert.assertEquals(price.getDescription(), "ÄRegistration for 11 Years updated");
    }

}
