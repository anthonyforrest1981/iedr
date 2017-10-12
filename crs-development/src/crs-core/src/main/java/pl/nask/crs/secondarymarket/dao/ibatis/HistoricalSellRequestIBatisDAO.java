package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.dao.HistoricalSellRequestDAO;

public class HistoricalSellRequestIBatisDAO extends GenericIBatisDAO<HistoricalObject<SellRequest>, Long> implements
        HistoricalSellRequestDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("sellHistChangeId", "sellHistChangeId");
        sortMap.put("sellHistChangedBy", "sellHistChangedBy");
        sortMap.put("sellHistChangeDate", "sellHistChangeDate");
        sortMap.put("sellId", "sellId");
        sortMap.put("domainName", "domainName");
        sortMap.put("domainHolder", "domainHolder");
        sortMap.put("accountId", "accountId");
        sortMap.put("accountName", "accountName");
        sortMap.put("sellStatus", "sellStatus");
    }

    public HistoricalSellRequestIBatisDAO() {
        setGetQueryId("secondary-market-historical-sell-request.getHistoricalSellRequest");
        setCreateQueryId("secondary-market-historical-sell-request.createHistoricalSellRequest");
        setFindQueryId("secondary-market-historical-sell-request.findHistoricalSellRequests");
        setCountFindQueryId("secondary-market-historical-sell-request.countHistoricalSellRequests");

        setSortMapping(sortMap);
    }

    @Override
    public Long create(SellRequest request, Date changeDate, String changedBy) {
        HistoricalObject<SellRequest> dto = new HistoricalObject<>(request, changeDate, changedBy);
        this.create(dto);
        return dto.getChangeId();
    }
}
