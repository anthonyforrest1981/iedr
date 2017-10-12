package pl.nask.crs.payment.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalTransactionHistIbatisDAO extends GenericIBatisDAO<InternalTransaction, Long> {

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

    public InternalTransactionHistIbatisDAO() {
        setCreateQueryId("transaction-hist.createHistTransaction");
        setGetQueryId("transaction-hist.selectHistTransactionById");
        setCountFindQueryId("transaction-hist.countFindHistTransactions");
        setFindQueryId("transaction-hist.findHistTransactions");

        setSortMapping(sortingMap);
    }

    public List<DomainInfo> getTransactionInfo(long transactionId) {
        return performQueryForList("transaction-hist.getTransactionInfo", transactionId);
    }

    public List<DomainInfo> getTransactionInfo(String orderId) {
        return performQueryForList("transaction-hist.getTransactionInfoByOrderId", orderId);
    }

    public InternalTransaction getByOrderId(String orderId) {
        return performQueryForObject("transaction-hist.selectHistTransactionByOrderId", orderId);
    }
}
