package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.dao.HistoricalBuyRequestDAO;

public class HistoricalBuyRequestIBatisDAO extends GenericIBatisDAO<HistoricalObject<BuyRequest>, Long>
        implements HistoricalBuyRequestDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("buyHistChangeId", "buyHistChangeId");
        sortMap.put("buyHistChangedBy", "buyHistChangedBy");
        sortMap.put("buyHistChangeDate", "buyHistChangeDate");
        sortMap.put("buyId", "buyId");
        sortMap.put("domainName", "domainName");
        sortMap.put("domainHolder", "domainHolder");
        sortMap.put("accountId", "accountId");
        sortMap.put("accountName", "accountName");
    }

    public HistoricalBuyRequestIBatisDAO() {
        setGetQueryId("secondary-market-historical-buy-request.get");
        setCreateQueryId("secondary-market-historical-buy-request.create");
        setFindQueryId("secondary-market-historical-buy-request.findHistBuyRequests");
        setCountFindQueryId("secondary-market-historical-buy-request.countHistBuyRequests");

        setSortMapping(sortMap);
    }

    @Override
    public void create(HistoricalObject<BuyRequest> buyRequestHistoricalObject) {
        super.create(buyRequestHistoricalObject);
        if (!buyRequestHistoricalObject.getObject().getTelecoms().isEmpty()) {
            performInsert("secondary-market-historical-buy-request.createTelecoms", buyRequestHistoricalObject);
        }
    }

    @Override
    public Long create(BuyRequest buyRequest, Date changeDate, String changedBy) {
        final HistoricalObject<BuyRequest> histEntry = new HistoricalObject<>(buyRequest, changeDate, changedBy);
        create(histEntry);
        return histEntry.getChangeId();
    }
}
