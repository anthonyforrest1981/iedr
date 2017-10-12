package pl.nask.crs.app.payment;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.exceptions.DomainPriceDatesOutOfBoundException;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.pdfmerge.PdfMergeToolException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.PriceNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

public interface PaymentAppService {

    ExtendedDeposit viewUserDeposit(AuthenticatedUser user) throws AccessDeniedException, DepositNotFoundException;

    ExtendedDeposit viewDeposit(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, DepositNotFoundException;

    LimitedSearchResult<ExtendedDeposit> findDeposits(AuthenticatedUser user, DepositSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<Deposit> findUserHistoricalDeposits(AuthenticatedUser user, DepositSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<Deposit> findDepositWithHistory(AuthenticatedUser user, DepositSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    ExtendedDeposit depositFunds(AuthenticatedUser user, BigDecimal amountInStandardCurrencyUnit, CreditCard card)
            throws AccessDeniedException, CardPaymentException, ExportException, DepositBelowLimitException,
            DepositOverLimitException;

    BigDecimal getVatRate(AuthenticatedUser user) throws AccessDeniedException;

    List<DomainPrice> getDomainPricing(AuthenticatedUser user) throws NicHandleNotFoundException, AccessDeniedException;

    Payment getRequestPrice(AuthenticatedUser user, OperationType operationType)
            throws NicHandleNotFoundException, AccessDeniedException;

    LimitedSearchResult<DepositTopUp> getTopUpHistory(AuthenticatedUser user, Date fromDate, Date toDate, long offset,
            long limit) throws AccessDeniedException;

    PaymentSummary payForDomainRenewal(AuthenticatedUser user, Map<String, Period> domainsWithPeriods,
            PaymentMethod paymentMethod, CreditCard creditCard)
            throws AccessDeniedException, DuplicatedDomainException, DomainNotFoundException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughDepositFundsException, CardPaymentException, DomainIllegalStateException,
            ReservationPendingException;

    void autorenewAll(AuthenticatedUser user) throws AccessDeniedException;

    LimitedSearchResult<Reservation> findOwnReservations(AuthenticatedUser user,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    LimitedSearchResult<Reservation> findDomainReservations(AuthenticatedUser user, String domainName,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    ReservationTotals getNotSettledReservationsTotals(AuthenticatedUser user, boolean adp) throws AccessDeniedException;

    LimitedSearchResult<Reservation> findHistoricalReservations(AuthenticatedUser user,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    List<Transaction> findAllTransactions(AuthenticatedUser user, TransactionSearchCriteria criteria,
            List<SortCriterion> sortBy) throws AccessDeniedException;

    List<Transaction> getSettledTransactionHistory(AuthenticatedUser user, PaymentMethod paymentMethod)
            throws AccessDeniedException;

    LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(AuthenticatedUser user, long offset,
            long limit, List<SortCriterion> sortBy)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException, AccessDeniedException;

    List<TransactionInfo> getReadyADPTransactionsReport(AuthenticatedUser user, String billingNH)
            throws DepositNotFoundException, AccessDeniedException;

    LimitedSearchResult<Transaction> findHistoricalTransactions(AuthenticatedUser user,
            TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    int addVatRate(AuthenticatedUser user, String category, Date from, BigDecimal vatRate)
            throws AccessDeniedException, VatFromDuplicationException, ThirdDecimalPlaceException,
            DomainPriceDatesOutOfBoundException;

    void invalidate(AuthenticatedUser user, int vatId)
            throws AccessDeniedException, VatNotFoundException, NextValidVatNotFoundException;

    List<Vat> getValid(AuthenticatedUser user) throws AccessDeniedException;

    List<String> getVatCategories(AuthenticatedUser user) throws AccessDeniedException;

    LimitedSearchResult<DomainPrice> findAllPrices(AuthenticatedUser user, long offset, long limit,
            List<SortCriterion> sortBy) throws AccessDeniedException;

    DomainPrice getPrice(AuthenticatedUser user, int id) throws AccessDeniedException, PriceNotFoundException;

    void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException, AccessDeniedException;

    void modifyPrice(AuthenticatedUser user, int id, String decription, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws PriceNotFoundException, ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException,
            AccessDeniedException;

    LimitedSearchResult<PlainInvoice> findInvoices(AuthenticatedUser user, PlainInvoiceSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<PlainInvoice> findUserInvoices(AuthenticatedUser user, PlainInvoiceSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<ExtendedReservation> findExtendedReservations(AuthenticatedUser user,
            ExtendedReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    List<DomainInfo> getInvoiceInfo(AuthenticatedUser user, String invoiceNumber) throws AccessDeniedException;

    List<DomainInfo> getTransactionInfo(AuthenticatedUser user, long transactionId) throws AccessDeniedException;

    List<DomainInfo> getTransactionInfo(AuthenticatedUser user, String orderId) throws AccessDeniedException;

    Deposit correctDeposit(AuthenticatedUser user, String nicHandle, BigDecimal amountInStandardCurrencyUnit,
            String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException, AccessDeniedException;

    Deposit depositFundsOffline(AuthenticatedUser user, String nicHandle, BigDecimal amountInStandardCurrencyUnit,
            String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException, AccessDeniedException,
            DepositBelowLimitException, DepositOverLimitException;

    InputStream viewXmlInvoice(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceNotFoundException, AccessDeniedException;

    InputStream viewPdfInvoice(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceNotFoundException, AccessDeniedException;

    InputStream viewMergedInvoices(AuthenticatedUser user, List<String> invoiceNumbers)
            throws InvoiceNotFoundException, PdfMergeToolException, AccessDeniedException;

    void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber)
            throws InvoiceEmailException, AccessDeniedException;

    List<Transaction> findTransactionsToCleanup(AuthenticatedUser user) throws AccessDeniedException;

    void cleanupTransaction(AuthenticatedUser user, Transaction transaction)
            throws TransactionNotFoundException, AccessDeniedException, CardPaymentException;
}
