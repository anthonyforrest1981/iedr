package pl.nask.crs.app.invoicing.impl;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.app.invoicing.email.InvalidatedInvoiceEmailParams;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoicingSupportServiceImpl implements InvoicingSupportService {

    private final static Logger LOG = Logger.getLogger(InvoicingSupportServiceImpl.class);

    private final ServicesRegistry servicesRegistry;

    private final AppServicesRegistry appServicesRegistry;

    private final InvoicingAppService invoicingAppService;

    public InvoicingSupportServiceImpl(ServicesRegistry servicesRegistry, AppServicesRegistry appServicesRegistry,
            InvoicingAppService invoicingAppService) {
        this.servicesRegistry = servicesRegistry;
        this.appServicesRegistry = appServicesRegistry;
        this.invoicingAppService = invoicingAppService;
    }

    @Override
    public void runInvoicing(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        markTransactionsStartedSettlement(user);
        settleTransactions(user);
        generateInvoices(user);
    }

    private List<Transaction> getTransactionToMarkSettlementStarted(AuthenticatedUser user)
            throws AccessDeniedException {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(false);
        criteria.setSettlementEnded(false);
        criteria.setReadyForSettlement(true);
        return appServicesRegistry.getPaymentAppService().findAllTransactions(user, criteria, null);
    }

    private void markTransactionsStartedSettlement(AuthenticatedUser user) throws AccessDeniedException {
        List<Transaction> transactions = getTransactionToMarkSettlementStarted(user);
        for (Transaction transaction : transactions) {
            setTransactionStartedSettlement(user, transaction.getId());
        }
    }

    private void setTransactionStartedSettlement(AuthenticatedUser user, long transactionId) {
        try {
            invoicingAppService.setTransactionStartedSettlement(user, transactionId);
        } catch (Exception e) {
            LOG.error("Exception during starting transaction settlement, transactionId=" + transactionId, e);
        }
    }

    private void settleTransactions(AuthenticatedUser user) throws AccessDeniedException {
        List<Transaction> transactions = getSortedTransactions(user, getTransactionToSettleCriteria());
        for (Transaction transaction : transactions) {
            settleTransaction(user, new OpInfo(user), transaction.getId());
        }
    }

    private void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) {
        try {
            invoicingAppService.settleTransaction(user, opInfo, transactionId);
        } catch (Exception e) {
            LOG.error("Exception during transaction settlement occured, transactionId=" + transactionId, e);
        }
    }

    private List<Transaction> getSortedTransactions(AuthenticatedUser user, TransactionSearchCriteria criteria)
            throws AccessDeniedException {
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", true));
        return appServicesRegistry.getPaymentAppService().findAllTransactions(user, criteria, sortBy);
    }

    private TransactionSearchCriteria getTransactionToSettleCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(true);
        criteria.setSettlementEnded(false);
        criteria.setReadyForSettlement(true);
        return criteria;
    }

    private void generateInvoices(AuthenticatedUser user) throws AccessDeniedException {
        List<Transaction> transactions = getSortedTransactions(user, getTransactionsPendingInvoicingCriteria());
        Map<String, List<Transaction>> nhToADPTransactions = new HashMap<String, List<Transaction>>();
        Map<String, List<Transaction>> nhToCCTransactions = new HashMap<String, List<Transaction>>();
        prepareNHToTransactionMaps(transactions, nhToADPTransactions, nhToCCTransactions);
        generateInvoicesForNH(user, nhToADPTransactions);
        generateInvoicesForNH(user, nhToCCTransactions);
    }

    private void generateInvoicesForNH(AuthenticatedUser user, Map<String, List<Transaction>> nhToTransactions) {
        for (Map.Entry<String, List<Transaction>> entry : nhToTransactions.entrySet()) {
            createInvoice(user, entry.getKey(), entry.getValue());
        }
    }

    private TransactionSearchCriteria getTransactionsPendingInvoicingCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        criteria.setInvoiceAssociated(false);
        return criteria;
    }

    private void prepareNHToTransactionMaps(List<Transaction> transactions,
            final Map<String, List<Transaction>> ADPMap, final Map<String, List<Transaction>> CCMap) {
        for (Transaction transaction : transactions) {
            List<Transaction> nhTransactions = null;
            if (Validator.isEmpty(transaction.getReservations())) {
                LOG.warn("Transaction without reservations (skipping), transactionId=" + transaction.getId());
                if (transaction.isSettlementEnded()) {
                    throw new IllegalArgumentException(
                            "Transaction is settled but cannot add it to the invoice (no reservations)!, transactionId="
                                    + transaction.getId());
                }
            }

            if (transaction.getPaymentMethod() == null) {
                throw new NullPointerException("PaymentMethod is null in transaction (transactionId="
                        + transaction.getId());
            } else {
                switch (transaction.getPaymentMethod()) {
                    case ADP:
                        nhTransactions = ADPMap.get(transaction.getBillNicHandleId());
                        if (nhTransactions == null) {
                            nhTransactions = new ArrayList<Transaction>();
                            nhTransactions.add(transaction);
                            ADPMap.put(transaction.getBillNicHandleId(), nhTransactions);
                        } else {
                            nhTransactions.add(transaction);
                        }
                        break;
                    case CC:
                        nhTransactions = CCMap.get(transaction.getBillNicHandleId());
                        if (nhTransactions == null) {
                            nhTransactions = new ArrayList<Transaction>();
                            nhTransactions.add(transaction);
                            CCMap.put(transaction.getBillNicHandleId(), nhTransactions);
                        } else {
                            nhTransactions.add(transaction);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid payment method: " + transaction.getPaymentMethod()
                                + ", transactionId=" + transaction.getId());
                }
            }
        }
    }

    private void createInvoice(AuthenticatedUser user, String nicHandleId, List<Transaction> transactions) {
        try {
            invoicingAppService.generateInvoice(user, nicHandleId, transactions);
        } catch (Exception e) {
            LOG.error("Exception during invoice creating occured, nicHandleId=" + nicHandleId, e);
        }
    }

    @Override
    public void runTransactionInvalidation(AuthenticatedUser user) throws AccessDeniedException {
        List<Transaction> regXferTransactions = appServicesRegistry.getPaymentAppService().findAllTransactions(
                user, getTransactionToInvalidateCriteria(), null);
        Map<String, List<String>> nhToNotifyWithDomains = new HashMap<String, List<String>>();
        for (Transaction transaction : regXferTransactions) {
            if (invalidateTransactionIfNeeded(user, transaction.getId())) {
                prepareNHToNotifyWithDomainsMap(transaction, nhToNotifyWithDomains);
            }
        }
        sendNotificationEmails(nhToNotifyWithDomains, user);
    }

    private void prepareNHToNotifyWithDomainsMap(Transaction transaction, final Map<String, List<String>> map) {
        List<String> nhDomains = map.get(transaction.getBillNicHandleId());
        if (nhDomains == null) {
            nhDomains = new ArrayList<>();
            for (Reservation reservation : transaction.getReservations()) {
                nhDomains.add(reservation.getDomainName());
            }
            map.put(transaction.getBillNicHandleId(), nhDomains);
        } else {
            for (Reservation reservation : transaction.getReservations()) {
                nhDomains.add(reservation.getDomainName());
            }
        }
    }

    private boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId) {
        try {
            return invoicingAppService.invalidateTransactionIfNeeded(user, transactionId);
        } catch (Exception e) {
            LOG.error("Exception during transaction invalidation, transactionId=" + transactionId, e);
        }
        return false;
    }

    private TransactionSearchCriteria getTransactionToInvalidateCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(false);
        criteria.setSettlementEnded(false);
        // imply that only registration,transfer transaction are selected (renewal transaction are never notReadyForSettlement)
        criteria.setReadyForSettlement(false);
        return criteria;
    }

    private void sendNotificationEmails(Map<String, List<String>> nhToNotifyWithDomains, AuthenticatedUser user) {
        try {
            for (Map.Entry<String, List<String>> entry : nhToNotifyWithDomains.entrySet()) {
                NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(entry.getKey());
                InvalidatedInvoiceEmailParams params = new InvalidatedInvoiceEmailParams(nicHandle, entry.getValue(),
                        user.getUsername());
                servicesRegistry.getEmailTemplateSender().sendEmail(
                        EmailTemplateNamesEnum.INVALIDATED_TRANSACTION.getId(), params);
            }
        } catch (Exception e) {
            LOG.warn("Problem with notification email occured.", e);
        }
    }
}
