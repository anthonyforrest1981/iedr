package pl.nask.crs.secondarymarket.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;

public class BuyRequestIBatisDAO extends GenericIBatisDAO<BuyRequest, Long> implements BuyRequestDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("buyId", "buyId");
        sortMap.put("domainName", "domainName");
        sortMap.put("accountName", "accountName");
        sortMap.put("domainHolder", "domainHolder");
        sortMap.put("adminName", "adminName");
        sortMap.put("buyCreationDate", "buyCreationDate");
        sortMap.put("authcodeDate", "authcodeDate");
        sortMap.put("checkedOutTo", "checkedOutTo");
        sortMap.put("buyStatus", "buyStatus");
    }
    public BuyRequestIBatisDAO() {
        setGetQueryId("secondary-market-buy-request.get");
        setLockQueryId("secondary-market-buy-request.lock");
        setUpdateUsingHistoryQueryId("secondary-market-buy-request.updateUsingHistory");
        setCreateQueryId("secondary-market-buy-request.create");
        setFindQueryId("secondary-market-buy-request.find");
        setCountFindQueryId("secondary-market-buy-request.count");
        setDeleteQueryId("secondary-market-buy-request.delete");

        setSortMapping(sortMap);
    }

    @Override
    public void create(BuyRequest buyRequest) {
        super.create(buyRequest);
        if (!buyRequest.getTelecoms().isEmpty())
            performInsert("secondary-market-buy-request.createTelecoms", buyRequest);
    }

    @Override
    public void updateUsingHistory(long changeId) {
        super.updateUsingHistory(changeId);
        performDelete("secondary-market-buy-request.deleteTelecoms", changeId);
        performInsert("secondary-market-buy-request.createTelecomsUsingHistory", changeId);
    }

    @Override
    public BuyRequest getByAuthcode(String authcode) {
        return performQueryForObject("secondary-market-buy-request.getByAuthcode", authcode);
    }

    @Override
    public List<Long> findCompletedSales(Date dateTo) {
        return performQueryForList("secondary-market-buy-request.findCompletedSales", dateTo);
    }
}
