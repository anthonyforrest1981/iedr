package pl.nask.crs.payment.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareHistTransactions;

public class TransactionHistDAOTest extends AbstractContextAwareTest {

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Test
    public void findTest() {
        String billNh = "APITEST-IEDR";
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(billNh);
        LimitedSearchResult<Transaction> result = transactionHistDAO.find(criteria, 0, 10, null);

        // should get only transactions with the right BillingNH
        for (Transaction t : result.getResults()) {
            AssertJUnit.assertEquals(billNh, t.getBillNicHandleId());
        }

        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(5, result.getResults().size());
    }

    @Test
    public void createTest() {
        int resultOld = transactionHistDAO.count(null);

        Transaction transaction = transactionDAO.get(20L);
        transactionHistDAO.create(transaction);

        Transaction historicalTransaction = transactionHistDAO.get(20L);
        compareHistTransactions(transaction, historicalTransaction);

        int resultNew = transactionHistDAO.count(null);
        AssertJUnit.assertEquals(resultOld + 1, resultNew);
    }

    @Test
    public void testGetTransactionInfo() throws Exception {
        List<DomainInfo> domainInfoList = transactionHistDAO.getTransactionInfo(64);
        AssertJUnit.assertEquals(3, domainInfoList.size());
    }

    @Test
    public void testGetTransactionInfoByOrderId() throws Exception {
        List<DomainInfo> domainInfoList = transactionHistDAO.getTransactionInfo("20120621142531-D-1161975");
        AssertJUnit.assertEquals(3, domainInfoList.size());

        domainInfoList = transactionHistDAO.getTransactionInfo("20120621142533-D-8730425");
        AssertJUnit.assertEquals(1, domainInfoList.size());

        domainInfoList = transactionHistDAO.getTransactionInfo("not existing orderId");
        AssertJUnit.assertEquals(0, domainInfoList.size());
    }

    @Test
    public void testGetTransactionByOrderId() throws Exception {
        SearchResult<Transaction> res = transactionHistDAO.find(null);
        for (Transaction t : res.getResults()) {
            if (t.getId() != 997L && t.getId() != 998L) {
                // Transactions 997 and 998 have unnormalized orderId in db and will not be found.
                Transaction transByOrderId = transactionHistDAO.getByOrderId(t.getOrderId());
                AssertJUnit.assertEquals("TransactionId should be the same", t.getId(), transByOrderId.getId());
            }
        }
    }

    @Test
    public void testGetUnnormalizedHistTransaction() {
        // Check if internalTransaction resultMap has string handlers (used in selectHistTransactionById,
        // selectHistTransactionByOrderId, findHistTransactions selects).
        Transaction transaction = transactionHistDAO.get(998L);
        AssertJUnit.assertEquals(transaction.getBillNicHandleId(), "ÄIDL2-IEDR");
        AssertJUnit.assertEquals(transaction.getInvoiceNumber(), "ÄTEST998");
        AssertJUnit.assertEquals(transaction.getOrderId(), "ÄorderId998");
    }

    @Test
    public void testGetUnnormalizedHistTransactionInfo() {
        // Check if domainInfo resultMap has string handlers (used in getTransactionInfo, getTransactionInfoByOrderId
        // selects).
        List<DomainInfo> transactionInfo = transactionHistDAO.getTransactionInfo(998L);
        Assert.assertEquals(transactionInfo.size(), 1);
        DomainInfo domainInfo = transactionInfo.get(0);
        Assert.assertEquals(domainInfo.getDomainName(), "äpaymentutf8daotest.ie");
        Assert.assertEquals(domainInfo.getOrderId(), "ÄorderId998");
    }

    @Test
    public void testGetHistTransactionInfoByUnnormalizedOrderId() {
        // Check if getTransactionInfoByOrderId select has string handlers.
        String orderId = "U\u0308orderId999";
        List<DomainInfo> transactionInfo = transactionHistDAO.getTransactionInfo(orderId);
        Assert.assertEquals(transactionInfo.size(), 1);
    }

    @Test
    public void testGetHistTransactionByUnnormalizedOrderId() {
        // Check if selectHistTransactionByOrderId select has string handlers.
        String orderId = "U\u0308orderId999";
        Transaction transaction = transactionHistDAO.getByOrderId(orderId);
        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getId(), 999L);
    }

    @Test
    public void testFindHistTransactionByUnnormalizedCriteria() {
        // Check if transactionHistCriteria sql frag has string handlers (used in countFindHistTransactions,
        // findHistTransactions selects).
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("U\u0308IDL2-IEDR");
        LimitedSearchResult<Transaction> limitedResult = transactionHistDAO.find(criteria, 0, 5, null);
        List<Transaction> results = limitedResult.getResults();
        Assert.assertEquals(limitedResult.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
        Transaction transaction = results.get(0);
        Assert.assertEquals(transaction.getId(), 999L);
    }

    @Test
    public void testLooseUtf8Validation() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("IDL3-IEDR\01");
        LimitedSearchResult<Transaction> limitedResult = transactionHistDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(limitedResult.getTotalResults(), 1);
        final Transaction transaction = limitedResult.getResults().get(0);
        Assert.assertEquals(transaction.getOrderId(), "BadUtf8Order\01");
    }

}
