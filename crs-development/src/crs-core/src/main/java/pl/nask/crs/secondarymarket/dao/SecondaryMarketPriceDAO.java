package pl.nask.crs.secondarymarket.dao;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.secondarymarket.SecondaryMarketPrice;

public interface SecondaryMarketPriceDAO {

    public SecondaryMarketPrice getPriceByTypes(OperationType requestType, CustomerType customerType);

}
