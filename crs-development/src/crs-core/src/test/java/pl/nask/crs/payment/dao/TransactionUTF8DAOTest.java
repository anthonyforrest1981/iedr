package pl.nask.crs.payment.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;

public class TransactionUTF8DAOTest extends AbstractContextAwareTest {

    @Resource
    TransactionDAO transactionDAO;

    @Test
    public void testGetUnnormalizedTransaction() {
        // Check if internalTransaction resultMap has string handlers (used in selectTransactionById,
        // selectTransactionLockedById, getTransactions, findTransactions selects).
        Transaction transaction = transactionDAO.get(998L);
        Assert.assertEquals(transaction.getBillNicHandleId(), "ÄIDL2-IEDR");
        Assert.assertEquals(transaction.getInvoiceNumber(), "ÄTEST998");
        Assert.assertEquals(transaction.getOrderId(), "ÄorderId998");
        Assert.assertEquals(transaction.getRealexAuthCode(), "ÄRealexAuthcode");
        Assert.assertEquals(transaction.getRealexPasRef(), "ÄRealexPasref");
    }

    @Test
    public void testInsertUnnormalizedTransaction() {
        // Check if insertTransaction insert has string handlers.
        Transaction transaction = Transaction.newInstance(MoneyUtils.getRoundedAndScaledValue(50),
                MoneyUtils.getRoundedAndScaledValue(40), MoneyUtils.getRoundedAndScaledValue(10),
                "A\u0308orderId996", "A\u0308RealexAuthcode", "A\u0308RealexPasref", false);
        long transactionId = transactionDAO.createTransaction(transaction);
        Transaction dbTransaction = transactionDAO.get(transactionId);
        Assert.assertEquals(dbTransaction.getOrderId(), "ÄorderId996");
        Assert.assertEquals(dbTransaction.getRealexAuthCode(), "ÄRealexAuthcode");
        Assert.assertEquals(dbTransaction.getRealexPasRef(), "ÄRealexPasref");
    }

    @Test
    public void testUpdateTransactionWithUnnormalizedData() {
        // Check if updateTransaction update has string handlers.
        long transactionId = 999L;
        Transaction transaction = new Transaction(transactionId, null, null, null, null, false, false,
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), false, null, null, null, false, null, 666L, null, null, null,
                "A\u0308UpdatedRealexAuthcode", "A\u0308UpdatedRealexPasref");
        transactionDAO.update(transaction);
        Transaction dbTransaction = transactionDAO.get(transactionId);
        Assert.assertEquals(dbTransaction.getRealexAuthCode(), "ÄUpdatedRealexAuthcode");
        Assert.assertEquals(dbTransaction.getRealexPasRef(), "ÄUpdatedRealexPasref");
    }

    @Test
    public void testFindTransactionByUnnormalizedCriteria() {
        // Check if transactionCriteria sql frag has string handlers.
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("U\u0308IDL2-IEDR");
        List<Transaction> transactions = transactionDAO.findAllTransactions(criteria, null);
        Assert.assertEquals(transactions.size(), 1);
        Transaction transaction = transactions.get(0);
        Assert.assertEquals(transaction.getId(), 999);
    }

}
