package pl.nask.crs.payment.service;

import java.util.Map;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.payment.service.impl.TransactionCancelStrategy;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

public interface PaymentService {

    Reservation lockForUpdate(long id) throws ReservationNotFoundException;

    void updateReservation(Reservation reservation);

    long authorizeADPPaymentForTicket(String nicHandleId, String domainName, Period period, OperationType operationType,
            Long ticketId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException;

    void authorizeCCPaymentForTicket(AuthenticatedUser user, String nicHandleId, String domainName, String domainHolder,
            Period period, OperationType operationType, CreditCard creditCard, Long ticketId)
            throws NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException;

    PaymentSummary authorizePaymentForRenewal(AuthenticatedUser user, OpInfo opInfo,
            Map<String, Period> domainsWithPeriods, PaymentMethod paymentMethod, CreditCard creditCard,
            boolean allowRenewalReservations)
            throws DomainNotFoundException, NicHandleNotFoundException, DomainIncorrectStateForPaymentException,
            DuplicatedDomainException, NotAdmissiblePeriodException, CardPaymentException, DomainIllegalStateException,
            ReservationPendingException, NotEnoughDepositFundsException;

    PaymentSummary autorenew(AuthenticatedUser user, String domainName)
            throws NicHandleNotFoundException, DomainNotFoundException, DomainIncorrectStateForPaymentException,
            NotAdmissiblePeriodException, NotEnoughDepositFundsException, DomainIllegalStateException,
            ReservationPendingException;

    void executePaymentForSecondaryMarketRequest(AuthenticatedUser user, String domainName,
            PaymentMethod paymentMethod, CreditCard creditCard, OperationType operationType, long requestId,
            OpInfo opInfo)
            throws CardPaymentException, NicHandleNotFoundException, NotEnoughDepositFundsException,
            DomainNotFoundException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            DomainIllegalStateException;

    void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId)
            throws TransactionNotFoundException, TransactionInvalidStateForSettlement, DomainNotFoundException,
            DomainIllegalStateException;

    boolean invalidateTransactionsIfNeeded(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException, NotAdmissiblePeriodException, CardPaymentException,
            NicHandleNotFoundException;

    void setTransactionFinanciallyPassed(long transactionId) throws TransactionNotFoundException;

    void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException;

    PaymentSummary reauthoriseTransaction(Transaction transaction, Ticket ticket, CreditCard creditCard)
    throws DomainNotFoundException, NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException;

    void cancelTransaction(AuthenticatedUser user, long transactionId, TransactionCancelStrategy cancelStrategy)
            throws CardPaymentException, TransactionNotFoundException;

}
