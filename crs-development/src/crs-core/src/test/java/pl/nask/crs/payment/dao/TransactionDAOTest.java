package pl.nask.crs.payment.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareTransactions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

public class TransactionDAOTest extends AbstractContextAwareTest {

    @Resource
    TransactionDAO transactionDAO;

    @Test
    public void getTest() {
        Transaction transaction = transactionDAO.get(6L);
        AssertJUnit.assertEquals(6, transaction.getId());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(78.91), transaction.getTotalCost());
        AssertJUnit.assertEquals(1, transaction.getReservations().size());
        AssertJUnit.assertEquals(1, transaction.getReservations().get(0).getId());
        AssertJUnit.assertEquals(PaymentMethod.ADP, transaction.getPaymentMethod());
    }

    @Test
    public void createTest() {
        List<Transaction> transactions = transactionDAO.getAll();
        AssertJUnit.assertEquals(14, transactions.size());
        Transaction transaction = Transaction.newInstance(5, "5", true, false, MoneyUtils.getRoundedAndScaledValue(50),
                MoneyUtils.getRoundedAndScaledValue(40), MoneyUtils.getRoundedAndScaledValue(10), "order id", 666L);
        long transactionId = transactionDAO.createTransaction(transaction);
        transactions = transactionDAO.getAll();
        AssertJUnit.assertEquals(15, transactions.size());
        Transaction fromDB = transactionDAO.get(transactionId);
        compareTransactions(transaction, fromDB);
    }

    @Test
    public void updateTest() {
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Transaction transaction = transactionDAO.get(1L);
        AssertJUnit.assertFalse(transaction.isSettlementStarted());
        AssertJUnit.assertFalse(transaction.isSettlementEnded());
        AssertJUnit.assertFalse(transaction.isCancelled());
        Assert.assertNull(transaction.getCancelledDate());
        Assert.assertNull(transaction.getReauthorisedId());

        transaction.markCancelled();
        transaction.setSettlementStarted(true);
        transaction.setSettlementEnded(true);
        transaction.setInvoiceId(5);
        transaction.markInvalidated();
        transaction.setReauthorisedId(66L);
        transaction.getCancelledDate().setTime(aDate.getTime());
        transaction.getInvalidatedDate().setTime(aDate.getTime());
        transactionDAO.update(transaction);
        Transaction updateTransaction = transactionDAO.get(1L);

        compareTransactions(transaction, updateTransaction);
    }

    @Test
    public void getTransactionsTest() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setReadyForSettlement(true);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", false));
        List<Transaction> transactions = transactionDAO.findAllTransactions(criteria, sortBy);
        AssertJUnit.assertEquals(5, transactions.size());
        //FIX ME add more sort criterions
        AssertJUnit.assertEquals(103, transactions.get(0).getId());
        AssertJUnit.assertEquals(21, transactions.get(1).getId());

        sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", true));
        transactions = transactionDAO.findAllTransactions(criteria, sortBy);
        AssertJUnit.assertEquals(5, transactions.size());
        AssertJUnit.assertEquals(1, transactions.get(0).getId());
        AssertJUnit.assertEquals(2, transactions.get(1).getId());

        criteria = new TransactionSearchCriteria();
        criteria.setReadyForSettlement(false);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(9, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setCancelled(true);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(14, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setSettlementStarted(true);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(1, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(false);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(14, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setInvoiceAssociated(false);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(12, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setInvoiceAssociated(true);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(2, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setPaymentMethod(PaymentMethod.CC);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(6, transactions.size());

        criteria = new TransactionSearchCriteria();
        criteria.setReauthorised(true);
        transactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, transactions.size());

    }

    @Test
    public void deleteTest() {
        Transaction transaction = Transaction.newInstance(5, "5", true, false, MoneyUtils.getRoundedAndScaledValue(50),
                MoneyUtils.getRoundedAndScaledValue(40), MoneyUtils.getRoundedAndScaledValue(10), "order id", 666L);
        long transactionId = transactionDAO.createTransaction(transaction);

        List<Transaction> result = transactionDAO.getAll();
        AssertJUnit.assertEquals(15, result.size());

        transaction = transactionDAO.get(transactionId);
        AssertJUnit.assertNotNull(transaction);

        transactionDAO.deleteById(transactionId);

        result = transactionDAO.getAll();
        AssertJUnit.assertEquals(14, result.size());

        transaction = transactionDAO.get(transactionId);
        AssertJUnit.assertNull(transaction);
    }

    @Test
    public void findTest() throws Exception {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setReadyForSettlement(true);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", false));
        LimitedSearchResult<Transaction> result = transactionDAO.find(criteria, 0, 2, sortBy);
        AssertJUnit.assertEquals(5, result.getTotalResults());

        List<Transaction> transactions = result.getResults();
        AssertJUnit.assertEquals(2, transactions.size());
        //FIX ME add more sort criterions
        AssertJUnit.assertEquals(103, transactions.get(0).getId());
        AssertJUnit.assertEquals(21, transactions.get(1).getId());

        sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", true));
        transactions = transactionDAO.findAllTransactions(criteria, sortBy);
        AssertJUnit.assertEquals(5, transactions.size());
        AssertJUnit.assertEquals(1, transactions.get(0).getId());
        AssertJUnit.assertEquals(2, transactions.get(1).getId());
    }

    @Test
    public void countTest() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setReadyForSettlement(true);
        AssertJUnit.assertEquals(5, transactionDAO.count(criteria));
    }

    @Test
    public void existsTest() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setReadyForSettlement(true);
        AssertJUnit.assertTrue(transactionDAO.exists(criteria));
    }

    @Test
    public void notExistsTest() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH("APIT1-IEDR");
        criteria.setReadyForSettlement(false);
        AssertJUnit.assertFalse(transactionDAO.exists(criteria));
    }
}
