package pl.nask.crs.payment.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalHistoricalDeposit;

public class InternalHistoricalDepositIbatisDAO extends GenericIBatisDAO<InternalHistoricalDeposit, Long> {

    private static final Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("id", "id");
        sortingMap.put("nicHandleId", "nicHandleId");
        sortingMap.put("nicHandleName", "nicHandleName");
        sortingMap.put("transactionDate", "transactionDate");
        sortingMap.put("openBal", "openBal");
        sortingMap.put("closeBal", "closeBal");
        sortingMap.put("orderId", "orderId");
        sortingMap.put("transactionAmount", "transactionAmount");
        sortingMap.put("transactionType", "transactionType");
        sortingMap.put("correctorNH", "correctorNH");
        sortingMap.put("histChangeDate", "histChangeDate");
    }

    public InternalHistoricalDepositIbatisDAO() {
        setCreateQueryId("historical-deposit.createHistoricalDeposit");
        setFindQueryId("historical-deposit.findHistoricalDeposits");
        setCountFindQueryId("historical-deposit.countFindHistoricalDeposits");

        setSortMapping(sortingMap);
    }

}
