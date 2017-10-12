package pl.nask.crs.payment.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

public class InternalTransactionIbatisDAO extends GenericIBatisDAO<InternalTransaction, Long> {
    private SequentialNumberGenerator idGenerator;

    private static Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("id", "id");
        sortingMap.put("nicHandleId", "nicHandleId");
        sortingMap.put("invoiceId", "invoiceId");
        sortingMap.put("orderId", "orderId");
        sortingMap.put("settlementStarted", "settlementStarted");
        sortingMap.put("settlementEnded", "settlementEnded");
        sortingMap.put("totalCost", "totalCost");
        sortingMap.put("totalNetAmount", "totalNetAmount");
        sortingMap.put("totalVatAmount", "totalVatAmount");
        sortingMap.put("cancelled", "cancelled");
        sortingMap.put("cancelledDate", "cancelledDate");
        sortingMap.put("financiallyPassedDate", "financiallyPassedDate");
        sortingMap.put("invalidated", "invalidated");
        sortingMap.put("invalidatedDate", "invalidatedDate");
        sortingMap.put("settlementDate", "settlementDate");
        sortingMap.put("paymentMethod", "paymentMethod");
        sortingMap.put("operationType", "operationType");
        sortingMap.put("invoiceNumber", "invoiceNumber");
    }

    public InternalTransactionIbatisDAO() {
        setGetQueryId("transaction.selectTransactionById");
        setLockQueryId("transaction.selectTransactionLockedById");
        setUpdateQueryId("transaction.updateTransaction");
        setDeleteQueryId("transaction.deleteTransactionById");
        setCountFindQueryId("transaction.countFindTransactions");
        setFindQueryId("transaction.findTransactions");

        setSortMapping(sortingMap);
    }

    public long createTransaction(InternalTransaction internalTransaction) {
        long id = idGenerator.getNextId();
        internalTransaction.setId(id);
        performInsert("transaction.insertTransaction", internalTransaction);
        return id;
    }

    public List<InternalTransaction> getAll() {
        return performQueryForList("transaction.selectAll");
    }

    public List<InternalTransaction> getTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        FindParameters findParameters = new FindParameters(criteria).setOrderBy(sortBy);
        return performQueryForList("transaction.getTransactions", findParameters);
    }

    public void setIdGenerator(SequentialNumberGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
}
