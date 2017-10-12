package pl.nask.crs.secondarymarket.dao;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.secondarymarket.BuyRequest;

public interface BuyRequestDAO extends GenericDAO<BuyRequest, Long> {

    BuyRequest getByAuthcode(final String authcode);

    List<Long> findCompletedSales(Date dateTo);
}
