package pl.nask.crs.app.invoicing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.nask.crs.app.invoicing.email.InvalidatedInvoiceEmailParams;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.dao.InvoiceDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

import mockit.Mocked;
import mockit.NonStrictExpectations;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
public class InvoicingSupportServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    InvoicingSupportService invoicingSupportService;

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    AuthenticationService authenticationService;

    @Mocked
    EmailTemplateSenderImpl emailTemplateSender;

    private AuthenticatedUser user;

    @BeforeClass
    public void setUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @Test
    public void invoicingTest() throws AccessDeniedException {
        preAssert();

        invoicingSupportService.runInvoicing(user);

        postAssert();
    }

    private void preAssert() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        List<Transaction> settledTransactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(0, settledTransactions.size());
        AssertJUnit.assertEquals(12, transactionHistDAO.count(null));

        List<Invoice> invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(10, invoices.size());
    }

    private void postAssert() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        List<Transaction> settledTransactions = transactionDAO.findAllTransactions(criteria, null);
        AssertJUnit.assertEquals(1, settledTransactions.size());
        AssertJUnit.assertEquals(17, transactionHistDAO.count(null));

        List<Invoice> invoices = invoiceDAO.getAll();
        AssertJUnit.assertEquals(12, invoices.size());

        Invoice invoice = invoices.get(10);
        assertInvoiceInProperState(invoice);
    }

    private void assertInvoiceInProperState(Invoice invoice) {
        int invoiceId = invoice.getId();
        AssertJUnit.assertEquals("INV0000001", invoice.getInvoiceNumber());
        AssertJUnit.assertEquals(666, invoice.getAccountNumber());
        AssertJUnit.assertTrue(invoice.isCompleted());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(206.38), invoice.getTotalCost());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(170), invoice.getTotalNetAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(36.38), invoice.getTotalVatAmount());
        AssertJUnit.assertEquals(3, invoice.getTransactions().size());

        for (Transaction transaction : invoice.getTransactions()) {
            AssertJUnit.assertTrue(transaction.isFinanciallyPassed());
            AssertJUnit.assertTrue(transaction.isSettlementStarted());
            AssertJUnit.assertTrue(transaction.isSettlementEnded());
            AssertJUnit.assertFalse(transaction.isCancelled());
            AssertJUnit.assertEquals(invoiceId, (int) transaction.getInvoiceId());

            long transactionId = transaction.getId();
            for (Reservation reservation : transaction.getReservations()) {
                AssertJUnit.assertTrue(reservation.isReadyForSettlement());
                AssertJUnit.assertTrue(reservation.isSettled());
                AssertJUnit.assertEquals(transactionId, (long) reservation.getTransactionId());
            }
        }
    }

    @Test
    public void invalidateTransactionsTest() throws Exception {
        List<Transaction> transactionsToInvalidate =
                transactionDAO.findAllTransactions(getTransactionToInvalidateCriteria(), null);
        List<Transaction> invalidatedTransactions =
                transactionDAO.findAllTransactions(getInvalidatedTransactionCriteria(), null);
        AssertJUnit.assertEquals(0, invalidatedTransactions.size());

        final Set<String> nicHandles = new HashSet<>();
        for (Transaction t : transactionsToInvalidate) {
            nicHandles.add(t.getBillNicHandleId());
        }

        new NonStrictExpectations() {
            {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVALIDATED_TRANSACTION.getId(),
                        withInstanceOf(InvalidatedInvoiceEmailParams.class));
                minTimes = nicHandles.size();
                maxTimes = nicHandles.size();
            }
        };

        invoicingSupportService.runTransactionInvalidation(user);

        invalidatedTransactions = transactionDAO.findAllTransactions(getInvalidatedTransactionCriteria(), null);
        AssertJUnit.assertEquals(transactionsToInvalidate.size(), invalidatedTransactions.size());

        transactionsToInvalidate = transactionDAO.findAllTransactions(getTransactionToInvalidateCriteria(), null);
        AssertJUnit.assertEquals(0, transactionsToInvalidate.size());

    }

    private TransactionSearchCriteria getInvalidatedTransactionCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setInvalidated(true);
        return criteria;
    }

    private TransactionSearchCriteria getTransactionToInvalidateCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(false);
        criteria.setSettlementEnded(false);
        criteria.setReadyForSettlement(false);
        return criteria;
    }
}
