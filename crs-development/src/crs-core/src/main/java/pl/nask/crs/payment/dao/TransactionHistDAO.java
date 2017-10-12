package pl.nask.crs.payment.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;

public interface TransactionHistDAO extends GenericDAO<Transaction, Long> {

    List<DomainInfo> getTransactionInfo(long transactionId);

    List<DomainInfo> getTransactionInfo(String orderId);

    Transaction getByOrderId(String orderId);
}
