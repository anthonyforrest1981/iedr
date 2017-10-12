package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.dao.SellRequestDAO;

public class SellRequestIBatisDAO extends GenericIBatisDAO<SellRequest, Long> implements SellRequestDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("sellId", "sellId");
        sortMap.put("domainName", "domainName");
        sortMap.put("sellCreatorNH", "sellCreatorNH");
        sortMap.put("sellCreationDate", "sellCreationDate");
        sortMap.put("domainHolder", "domainHolder");
        sortMap.put("accountId", "accountId");
        sortMap.put("accountName", "accountName");
    }

    public SellRequestIBatisDAO() {
        setGetQueryId("secondary-market-sell-request.getRequest");
        setLockQueryId("secondary-market-sell-request.lock");
        setUpdateUsingHistoryQueryId("secondary-market-sell-request.updateUsingHistory");
        setCreateQueryId("secondary-market-sell-request.createRequest");
        setFindQueryId("secondary-market-sell-request.findRequests");
        setCountFindQueryId("secondary-market-sell-request.countRequests");
        setDeleteQueryId("secondary-market-sell-request.delete");

        setSortMapping(sortMap);
    }

}
