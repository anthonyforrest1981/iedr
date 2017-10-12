package pl.nask.crs.secondarymarket.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.SecondaryMarketPrice;

import static org.testng.AssertJUnit.assertEquals;

public class SecondaryMarketPriceDAOTest extends AbstractContextAwareTest {

    @Resource
    SecondaryMarketPriceDAO secondaryMarketPriceDAO;

    @Test
    public void testGetPriceByTypes() {
        SecondaryMarketPrice price = secondaryMarketPriceDAO.getPriceByTypes(OperationType.BUY_REQUEST,
                CustomerType.Registrar);
        assertEquals(MoneyUtils.getRoundedAndScaledValue(14), price.getAmount());
    }

}
