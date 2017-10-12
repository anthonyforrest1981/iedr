package pl.nask.crs.app.payment.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.exceptions.DomainPriceDatesOutOfBoundException;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.pdfmerge.PdfMergeToolException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.payment.service.InvoicingService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.payment.service.impl.TransactionCancelStrategy;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.PriceNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class PaymentAppServiceImpl implements PaymentAppService {

    private ServicesRegistry services;
    private PaymentService paymentService;
    private PaymentSearchService paymentSearchService;
    private InvoicingService invoicingService;

    protected final static Logger log = Logger.getLogger(PaymentAppServiceImpl.class);

    public PaymentAppServiceImpl(ServicesRegistry services) {
        Validator.assertNotNull(services, "services registry");
        this.services = services;
        this.paymentService = services.getPaymentService();
        this.paymentSearchService = services.getPaymentSearchService();
        this.invoicingService = services.getInvoicingService();
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedDeposit viewUserDeposit(AuthenticatedUser user) throws AccessDeniedException, DepositNotFoundException {
        validateLoggedIn(user);
        return services.getDepositService().viewDeposit(user.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public ExtendedDeposit viewDeposit(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, DepositNotFoundException {
        validateLoggedIn(user);
        return services.getDepositService().viewDeposit(nicHandleId);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<ExtendedDeposit> findDeposits(AuthenticatedUser user, DepositSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().findDeposits(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Deposit> findUserHistoricalDeposits(AuthenticatedUser user,
            DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setNicHandleId(user.getUsername());
        return services.getDepositService().findHistoricalDeposits(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Deposit> findDepositWithHistory(AuthenticatedUser user, DepositSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().findHistoricalDeposits(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExtendedDeposit depositFunds(AuthenticatedUser user, BigDecimal amountInStandardCurrencyUnit, CreditCard card)
            throws AccessDeniedException, CardPaymentException, ExportException, DepositBelowLimitException,
            DepositOverLimitException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        return services.getDepositService().depositFunds(user, opInfo, user.getUsername(), amountInStandardCurrencyUnit,
                card);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Deposit correctDeposit(AuthenticatedUser user, String nicHandle, BigDecimal amountInStandardCurrencyUnit,
            String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException,
            AccessDeniedException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(remark, "remark");
        return services.getDepositService().correctDeposit(new OpInfo(user), nicHandle, amountInStandardCurrencyUnit,
                remark);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, BigDecimal amountInStandardCurrencyUnit,
            String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException,
            AccessDeniedException, DepositBelowLimitException, DepositOverLimitException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(remark, "remark");
        return services.getDepositService().depositFundsOffline(user, new OpInfo(user), nicHandle,
                amountInStandardCurrencyUnit, remark);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DomainPrice> getDomainPricing(AuthenticatedUser user)
            throws NicHandleNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getDomainPricing(user.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public Payment getRequestPrice(AuthenticatedUser user, OperationType operationType)
            throws NicHandleNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        PriceWithVat price = paymentSearchService.getRequestPriceWithVat(operationType, user.getUsername());
        return new Payment(price.getNetAmount(), price.getVatAmount(), price.getTotal());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getVatRate(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getVatRate(user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<DepositTopUp> getTopUpHistory(AuthenticatedUser user, Date fromDate, Date toDate,
            long offset, long limit) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getDepositService().getTopUpHistory(user.getUsername(), fromDate, toDate, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentSummary payForDomainRenewal(AuthenticatedUser user, Map<String, Period> domainsWithPeriods,
            PaymentMethod paymentMethod, CreditCard creditCard)
            throws AccessDeniedException, DuplicatedDomainException, DomainNotFoundException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughDepositFundsException, CardPaymentException, DomainIllegalStateException,
            ReservationPendingException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        return paymentService
                .authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, paymentMethod, creditCard, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autorenewAll(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewalTo(new Date());
        criteria.setDomainRenewalModes(RenewalMode.Autorenew, RenewalMode.RenewOnce);
        int offset = 0;
        final int limit = 100;
        while (true) {
            List<String> domains = services.getDomainSearchService().findDomainNames(criteria, offset, limit);
            if (domains.isEmpty()) {
                break; // no more domains in autorenew state
            }
            offset += limit;
            for (String domainName : domains) {
                try {
                    log.debug("Trying to auto renew domain: " + domainName);
                    paymentService.autorenew(user, domainName);
                } catch (DomainNotFoundException e) {
                    logFailure(domainName, "Domain not found");
                } catch (DomainIncorrectStateForPaymentException e) {
                    logFailure(domainName, "Domain state dissalows payments");
                } catch (NotEnoughDepositFundsException e) {
                    logInfo(domainName, "Not enough funds to perform ADP payment");
                } catch (NicHandleNotFoundException e) {
                    logError(domainName, "No such nicHandle: " + e.getNicHandleId());
                } catch (NotAdmissiblePeriodException e) {
                    logError(domainName, "Not admissible period for the renewal operation: " + e.getPeriod());
                } catch (ReservationPendingException e) {
                    logError(domainName, "Reservation pending");
                } catch (Exception e) {
                    log.error("Domain renewal failed for " + domainName, e);
                }
            }
        }
    }

    private void logInfo(String domainName, String message) {
        log.info("Domain renewal failed for " + domainName + " : " + message);
    }

    private void logFailure(String domainName, String message) {
        log.warn("Domain renewal failed for " + domainName + " : " + message);
    }

    private void logError(String domainName, String message) {
        log.error("Domain renewal failed (this should never happen!) for " + domainName + " : " + message);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Reservation> findOwnReservations(AuthenticatedUser user,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setBillingNH(user.getUsername());
        return paymentSearchService.findReservations(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Reservation> findDomainReservations(AuthenticatedUser user, String domainName,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setDomainName(domainName);
        return paymentSearchService.findReservations(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationTotals getNotSettledReservationsTotals(AuthenticatedUser user, boolean adp)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getNotSettledReservationsTotals(user.getUsername(), adp);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Reservation> findHistoricalReservations(AuthenticatedUser user,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.findHistoricalReservations(user.getUsername(), criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findAllTransactions(AuthenticatedUser user, TransactionSearchCriteria criteria,
            List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.findAllTransactions(criteria, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getSettledTransactionHistory(AuthenticatedUser user, PaymentMethod paymentMethod)
            throws AccessDeniedException {
        validateLoggedIn(user);
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(user.getUsername());
        criteria.setSettlementEnded(true);
        criteria.setPaymentMethod(paymentMethod);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", false));
        return paymentSearchService.findAllTransactions(criteria, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(AuthenticatedUser user, long offset,
            long limit, List<SortCriterion> sortBy)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getTransactionToReauthorise(user.getUsername(), offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionInfo> getReadyADPTransactionsReport(AuthenticatedUser user, String billingNH)
            throws DepositNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getReadyADPTransactionsReport(billingNH);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Transaction> findHistoricalTransactions(AuthenticatedUser user,
            TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.findHistoricalTransactions(user.getUsername(), criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addVatRate(AuthenticatedUser user, String category, Date from, BigDecimal vatRate)
            throws AccessDeniedException, VatFromDuplicationException, ThirdDecimalPlaceException,
            DomainPriceDatesOutOfBoundException {
        validateLoggedIn(user);
        return services.getVatService().addVatRate(category, from, vatRate, user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidate(AuthenticatedUser user, int vatId)
            throws AccessDeniedException, VatNotFoundException, NextValidVatNotFoundException {
        validateLoggedIn(user);
        services.getVatService().invalidate(vatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vat> getValid(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getVatService().getValid();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getVatCategories(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getVatService().getCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<DomainPrice> findAllPrices(AuthenticatedUser user, long offset, long limit,
            List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return services.getPriceService().findAll(offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainPrice getPrice(AuthenticatedUser user, int id)
            throws AccessDeniedException, PriceNotFoundException {
        validateLoggedIn(user);
        return services.getPriceService().get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException, AccessDeniedException {
        validateLoggedIn(user);
        services.getPriceService().addPrice(user, code, decription, price, duration, validFrom, validTo,
                forRegistration, forRenewal, direct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPrice(AuthenticatedUser user, int id, String decription, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws PriceNotFoundException, ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException,
            AccessDeniedException {
        validateLoggedIn(user);
        DomainPrice domainPrice = services.getPriceService().get(id);
        domainPrice.setDescription(decription);
        domainPrice.setPrice(price);
        domainPrice.setDuration(duration);
        domainPrice.setValidFrom(validFrom);
        domainPrice.setValidTo(validTo);
        domainPrice.setForRegistration(forRegistration);
        domainPrice.setForRenewal(forRenewal);
        domainPrice.setDirect(direct);
        services.getPriceService().save(domainPrice, user);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<PlainInvoice> findInvoices(AuthenticatedUser user, PlainInvoiceSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return invoicingService.findInvoices(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<PlainInvoice> findUserInvoices(AuthenticatedUser user, PlainInvoiceSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setBillingNH(user.getUsername());
        return invoicingService.findInvoices(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<ExtendedReservation> findExtendedReservations(AuthenticatedUser user,
            ExtendedReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.findExtendedReservations(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getInvoiceInfo(AuthenticatedUser user, String invoiceNumber)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return invoicingService.getInvoiceInfo(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getTransactionInfo(AuthenticatedUser user, long transactionId)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getTransactionInfo(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomainInfo> getTransactionInfo(AuthenticatedUser user, String orderId)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return paymentSearchService.getTransactionInfo(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream viewXmlInvoice(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return invoicingService.viewXmlInvoice(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream viewPdfInvoice(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return invoicingService.viewPdfInvoice(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream viewMergedInvoices(AuthenticatedUser user, List<String> invoiceNumbers)
            throws InvoiceNotFoundException, PdfMergeToolException, AccessDeniedException {
        validateLoggedIn(user);
        return invoicingService.viewMergedInvoices(invoiceNumbers);
    }

    @Override
    @Transactional(readOnly = true)
    public void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceEmailException, AccessDeniedException {
        validateLoggedIn(user);
        invoicingService.sendEmailWithInvoices(invoiceNumber, user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findTransactionsToCleanup(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        final int reservationExpirationPeriod = services.getApplicationConfig().getReservationExpirationPeriod();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), -reservationExpirationPeriod));
        return paymentSearchService.findAllTransactions(criteria, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanupTransaction(AuthenticatedUser user, Transaction transaction)
            throws TransactionNotFoundException, CardPaymentException {
        TransactionCancelStrategy cancelStrategy = new TransactionCancelStrategy() {
            @Override
            public void handleRealexError(Transaction transaction, CardPaymentException e) {
                log.info(String.format("Failed to cancel an expired transaction %s, probably was already removed by"
                        + " realex", transaction.getOrderId()));
            }

            @Override
            public boolean shouldPurgeTransaction() {
                return true;
            }
        };
        paymentService.cancelTransaction(user, transaction.getId(), cancelStrategy);
    }
}
