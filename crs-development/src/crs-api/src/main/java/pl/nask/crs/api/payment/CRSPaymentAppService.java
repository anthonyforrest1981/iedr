package pl.nask.crs.api.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.*;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidSessionTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

/**
 * @author: Marcin Tkaczyk
 */

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSPaymentAppService {
    /**
     * Returns user deposit data.
     *
     * @param user authentication token, required
     * @return <code>DepositVO</code> object that contans deposit data : <code>nicHandleId</code>, <code>trnasactionDate</code>, <code>openBal</code>, <code>closeBal</code>.
     * @throws AccessDeniedException
     * @throws DepositNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    DepositVO viewDeposit(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, DepositNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    /**
     * Returns historical deposits for given criteria. For all deposits search set offset=0 limit=0.
     *
     * @param user
     * @param criteria
     * @param offset
     * @param limit
     * @param sortBy
     * @return
     */
    @WebMethod
    DepositSearchResultVO findUserHistoricalDeposits(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") DepositSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortBy") List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    DepositVO depositFunds(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "amount") BigDecimal amount,
            @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws AccessDeniedException, CardPaymentException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, AuthenticationException,
            GenericValidationException, IncorrectUtf8FormatException, DepositBelowLimitException,
            DepositOverLimitException;

    /**
     * Returns system vat rate.
     *
     * @param user authentication token, required
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    BigDecimal getVatRate(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    DepositTopUpSearchResultVO getTopUpHistory(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "fromDate") Date fromDate, @WebParam(name = "toDate") Date toDate,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit)
            throws AccessDeniedException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    PaymentSummaryVO payForDomainRenewal(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domains") List<DomainWithPeriodVO> domains,
            @WebParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws AccessDeniedException, DomainNotFoundException, DuplicatedDomainException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughDepositFundsException, CardPaymentException, AuthenticationException, GenericValidationException,
            SessionExpiredException, DomainIllegalStateException, ReservationPendingException,
            IncorrectUtf8FormatException;

    @WebMethod
    ReservationSearchResultVO findHistoricalReservations(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") ReservationSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    /**
     * Returns not settled reservations for given user.
     *
     * @param user
     * @param criteria
     * @param offset
     * @param limit
     * @param sortBy
     * @return
     */
    @WebMethod
    ReservationSearchResultVO getNotSettledReservations(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") ReservationSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws AuthenticationException, GenericValidationException, IncorrectUtf8FormatException;

    @WebMethod
    ReservationTotalsVO getNotSettledReservationsTotals(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "isADP") boolean adp) throws AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    InvoiceSearchResultVO findInvoices(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") PlainInvoiceSearchCriteriaVO criteria, @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit, @WebParam(name = "sortBy") List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    List<DomainInfoVO> getInvoiceInfo(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "invoiceId") String invoiceNumber)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    List<DomainInfoVO> getTransactionInfo(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "transactionId") long transactionId)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    ReauthoriseTransactionSearchResultVO getTransactionToReauthorise(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit,
            @WebParam(name = "sortBy") List<SortCriterion> sortBy)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException, AuthenticationException,
            SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    void sendEmailWithInvoices(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "invoiceNumber") String invoiceNumber)
            throws InvoiceEmailException, AuthenticationException, SessionExpiredException,
            IncorrectUtf8FormatException;

    @WebMethod
    TransactionSearchResultVO findHistoricalTransactions(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") TransactionSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortBy") List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    List<DomainPriceVO> getDomainPricing(@WebParam(name = "user") AuthenticatedUserVO user)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    PaymentVO getRequestPrice(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(name = "operationType")
            OperationType operationType)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException,
            IncorrectUtf8FormatException;
}
