package pl.nask.crs.payment.service;

import java.math.BigDecimal;
import java.util.List;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.ProductPriceWithVat;

public interface PaymentSearchService {

    Reservation getReservationForTicket(long ticketId);

    LimitedSearchResult<Reservation> findReservations(ReservationSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<Reservation> findHistoricalReservations(String billingNH, ReservationSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy);

    ReservationTotals getNotSettledReservationsTotals(String username, boolean adp);

    LimitedSearchResult<ExtendedReservation> findExtendedReservations(ExtendedReservationSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy);

    Transaction getTransaction(long transactionId) throws TransactionNotFoundException;

    List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Transaction> findHistoricalTransactions(String billingNH, TransactionSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy);

    List<TransactionInfo> getReadyADPTransactionsReport(String billingNH) throws DepositNotFoundException;

    List<DomainInfo> getTransactionInfo(long transactionId);

    List<DomainInfo> getTransactionInfo(String orderId);

    LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(String billingNH, long offset, long limit,
            List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    BigDecimal getVatRate(String nicHandleId);

    List<DomainPrice> getDomainPricing(String nicHandleId) throws NicHandleNotFoundException;

    DomainPrice getProductPrice(Period period, OperationType type, String nicHandleId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    ProductPriceWithVat getProductPriceWithVat(Period period, OperationType operationType, String nicHandleId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    PriceWithVat getRequestPriceWithVat(OperationType operationType, String nicHandleId)
            throws NicHandleNotFoundException;

}
