package pl.nask.crs.api.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

@WebService(
    serviceName = "CRSPaymentAppService",
    endpointInterface="pl.nask.crs.api.payment.CRSPaymentAppService",
    portName = "CRSPaymentAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSPaymentAppServiceProxy implements CRSPaymentAppService {
    private CRSPaymentAppService service;

    public void setService(CRSPaymentAppService service) {
        this.service = service;
    }

    public DepositSearchResultVO findUserHistoricalDeposits(AuthenticatedUserVO user, DepositSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.findUserHistoricalDeposits(user, criteria, offset, limit, sortBy);
    }

    public DepositVO depositFunds(AuthenticatedUserVO user, BigDecimal amount, CreditCardVO creditCard)
            throws AccessDeniedException, CardPaymentException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, AuthenticationException,
            GenericValidationException, IncorrectUtf8FormatException, DepositBelowLimitException,
            DepositOverLimitException {
        return service.depositFunds(user, amount, creditCard);
    }

    public DepositVO viewDeposit(AuthenticatedUserVO user)
            throws AccessDeniedException, DepositNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.viewDeposit(user);
    }

    public BigDecimal getVatRate(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.getVatRate(user);
    }

    public DepositTopUpSearchResultVO getTopUpHistory(AuthenticatedUserVO user, Date fromDate, Date toDate,
            long offset, long limit)
            throws AccessDeniedException, AuthenticationException, IncorrectUtf8FormatException {
        return service.getTopUpHistory(user, fromDate, toDate, offset, limit);
    }

    public PaymentSummaryVO payForDomainRenewal(AuthenticatedUserVO user, List<DomainWithPeriodVO> domains,
            PaymentMethod paymentMethod, CreditCardVO creditCard)
            throws AccessDeniedException, DomainNotFoundException, DuplicatedDomainException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughDepositFundsException, CardPaymentException, AuthenticationException, GenericValidationException,
            SessionExpiredException, DomainIllegalStateException, ReservationPendingException,
            IncorrectUtf8FormatException {
        return service.payForDomainRenewal(user, domains, paymentMethod, creditCard);
    }

    public ReservationSearchResultVO findHistoricalReservations(AuthenticatedUserVO user,
            ReservationSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.findHistoricalReservations(user, criteria, offset, limit, sortBy);
    }

    public ReservationSearchResultVO getNotSettledReservations(AuthenticatedUserVO user,
            ReservationSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, GenericValidationException, IncorrectUtf8FormatException {
        return service.getNotSettledReservations(user, criteria, offset, limit, sortBy);
    }

    public ReservationTotalsVO getNotSettledReservationsTotals(AuthenticatedUserVO user, boolean adp)
            throws AuthenticationException, IncorrectUtf8FormatException {
        return service.getNotSettledReservationsTotals(user, adp);
    }

    public InvoiceSearchResultVO findInvoices(AuthenticatedUserVO user, PlainInvoiceSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.findInvoices(user, criteria, offset, limit, sortBy);
    }

    public List<DomainInfoVO> getInvoiceInfo(AuthenticatedUserVO user, String invoiceNumber)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.getInvoiceInfo(user, invoiceNumber);
    }

    public List<DomainInfoVO> getTransactionInfo(AuthenticatedUserVO user, long transactionId)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.getTransactionInfo(user, transactionId);
    }

    public ReauthoriseTransactionSearchResultVO getTransactionToReauthorise(AuthenticatedUserVO user, long offset,
            long limit, List<SortCriterion> sortBy)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException, AuthenticationException,
            SessionExpiredException, IncorrectUtf8FormatException {
        return service.getTransactionToReauthorise(user, offset, limit, sortBy);
    }

    public void sendEmailWithInvoices(AuthenticatedUserVO user, String invoiceNumber)
            throws InvoiceEmailException, AuthenticationException, SessionExpiredException,
            IncorrectUtf8FormatException {
        service.sendEmailWithInvoices(user, invoiceNumber);
    }

    public TransactionSearchResultVO findHistoricalTransactions(AuthenticatedUserVO user,
            TransactionSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.findHistoricalTransactions(user, criteria, offset, limit, sortBy);
    }

    public List<DomainPriceVO> getDomainPricing(AuthenticatedUserVO user)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.getDomainPricing(user);
    }

    public PaymentVO getRequestPrice(AuthenticatedUserVO user, OperationType operationType)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.getRequestPrice(user, operationType);
    }
}
