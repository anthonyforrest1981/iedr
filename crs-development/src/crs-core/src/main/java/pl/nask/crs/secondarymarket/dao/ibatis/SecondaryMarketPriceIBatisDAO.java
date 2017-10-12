package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.secondarymarket.SecondaryMarketPrice;
import pl.nask.crs.secondarymarket.dao.SecondaryMarketPriceDAO;

public class SecondaryMarketPriceIBatisDAO extends GenericIBatisDAO<SecondaryMarketPrice, Long>
    implements SecondaryMarketPriceDAO {

    @Override
    public SecondaryMarketPrice getPriceByTypes(OperationType requestType, CustomerType customerType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("requestType", requestType);
        params.put("customerType", customerType);
        return performQueryForObject("secondary-market-price.getPriceByTypes", params);
    }

}
