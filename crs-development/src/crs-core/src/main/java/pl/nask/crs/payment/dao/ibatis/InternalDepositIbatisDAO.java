package pl.nask.crs.payment.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalDeposit;

public class InternalDepositIbatisDAO extends GenericIBatisDAO<InternalDeposit, String> {

    private static final Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("nicHandleId", "nicHandleId");
        sortingMap.put("nicHandleName", "nicHandleName");
        sortingMap.put("transactionDate", "transactionDate");
        sortingMap.put("openBal", "openBal");
        sortingMap.put("closeBal", "closeBal");
        sortingMap.put("orderId", "orderId");
        sortingMap.put("transactionAmount", "transactionAmount");
        sortingMap.put("transactionType", "transactionType");
        sortingMap.put("correctorNH", "correctorNH");
    }

    public InternalDepositIbatisDAO() {
        setLockQueryId("deposit.getLockedDepositByNicHandleId");
        setCreateQueryId("deposit.insertDeposit");
        setGetQueryId("deposit.getDepositByNicHandleId");
        setFindQueryId("deposit.findDeposits");
        setCountFindQueryId("deposit.countFindDeposits");
        setUpdateUsingHistoryQueryId("deposit.updateUsingHistory");

        setSortMapping(sortingMap);
    }

}
