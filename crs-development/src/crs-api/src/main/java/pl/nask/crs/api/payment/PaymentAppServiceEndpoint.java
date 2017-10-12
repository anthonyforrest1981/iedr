package pl.nask.crs.api.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.converter.Converter;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.*;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.security.authentication.*;

public class PaymentAppServiceEndpoint extends WsSessionAware implements CRSPaymentAppService {
    private Logger log = Logger.getLogger(CRSPaymentAppService.class);

    private PaymentAppService service;

    public void setService(PaymentAppService service) {
        this.service = service;
    }

    @Override
    public DepositVO viewDeposit(AuthenticatedUserVO user)
            throws AccessDeniedException, DepositNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return new DepositVO(service.viewUserDeposit(completeUser));
    }

    @Override
    public DepositSearchResultVO findUserHistoricalDeposits(AuthenticatedUserVO user, DepositSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new DepositSearchResultVO(service.findUserHistoricalDeposits(user, criteria.toSearchCriteria(), offset,
                limit, sortBy));
    }

    @Override
    public DepositVO depositFunds(AuthenticatedUserVO user, BigDecimal amount, CreditCardVO creditCard)
            throws AccessDeniedException, CardPaymentException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, AuthenticationException,
            GenericValidationException, DepositBelowLimitException, DepositOverLimitException {
        ValidationHelper.validate(user);
        ValidationHelper.validate(creditCard);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new DepositVO(service.depositFunds(completeUser, amount, creditCard.toCreditCard()));
    }

    @Override
    public BigDecimal getVatRate(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return service.getVatRate(completeUser);
    }

    @Override
    public DepositTopUpSearchResultVO getTopUpHistory(AuthenticatedUserVO user, Date fromDate, Date toDate,
            long offset, long limit) throws AccessDeniedException, AuthenticationException {
        ValidationHelper.validate(user);
        Validator.assertNotNull(fromDate, "from name");
        Validator.assertNotNull(toDate, "to date");
        return new DepositTopUpSearchResultVO(service.getTopUpHistory(user, fromDate, toDate, offset, limit));
    }

    @Override
    public PaymentSummaryVO payForDomainRenewal(AuthenticatedUserVO user, List<DomainWithPeriodVO> domainsWithPeriods,
            PaymentMethod paymentMethod, CreditCardVO creditCard)
            throws AccessDeniedException, DomainNotFoundException, DuplicatedDomainException,
            DomainIncorrectStateForPaymentException, NicHandleNotFoundException, NotAdmissiblePeriodException,
            NotEnoughDepositFundsException, CardPaymentException, AuthenticationException, GenericValidationException,
            SessionExpiredException, DomainIllegalStateException, ReservationPendingException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(domainsWithPeriods, "domains names list");
        ValidationHelper.validate(paymentMethod, creditCard);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new PaymentSummaryVO(service.payForDomainRenewal(completeUser, Converter
                .convertDomainsWithPeriodToMap(domainsWithPeriods), paymentMethod, creditCard == null ? null
                : creditCard.toCreditCard()));
    }

    @Override
    public ReservationSearchResultVO getNotSettledReservations(AuthenticatedUserVO user,
            ReservationSearchCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, GenericValidationException {
        ValidationHelper.validate(user);
        ValidationHelper.validate(criteriaVO);
        ReservationSearchCriteria criteria = criteriaVO.toSearchCriteria();
        criteria.setSettled(false);
        criteria.setCancelled(false);
        LimitedSearchResult<Reservation> result = service.findOwnReservations(user, criteria, offset, limit, sortBy);
        return new ReservationSearchResultVO(result);
    }

    @Override
    public ReservationTotalsVO getNotSettledReservationsTotals(AuthenticatedUserVO user, boolean adp)
            throws AuthenticationException {
        ValidationHelper.validate(user);
        ReservationTotals totals = service.getNotSettledReservationsTotals(user, adp);
        return new ReservationTotalsVO(totals);
    }

    @Override
    public ReservationSearchResultVO findHistoricalReservations(AuthenticatedUserVO user,
            ReservationSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ReservationSearchResultVO(service.findHistoricalReservations(user, criteria.toSearchCriteria(),
                offset, limit, sortBy));
    }

    @Override
    public InvoiceSearchResultVO findInvoices(AuthenticatedUserVO user, PlainInvoiceSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new InvoiceSearchResultVO(service.findUserInvoices(user, criteria.toSearchCriteria(), offset, limit,
                sortBy));
    }

    @Override
    public List<DomainInfoVO> getInvoiceInfo(AuthenticatedUserVO user, String invoiceNumber)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainInfoVO(service.getInvoiceInfo(user, invoiceNumber));
    }

    @Override
    public List<DomainInfoVO> getTransactionInfo(AuthenticatedUserVO user, long transactionId)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainInfoVO(service.getTransactionInfo(user, transactionId));
    }

    private List<DomainInfoVO> toDomainInfoVO(List<DomainInfo> domainInfos) {
        if (Validator.isEmpty(domainInfos)) {
            return Collections.emptyList();
        } else {
            List<DomainInfoVO> ret = new ArrayList<DomainInfoVO>();
            for (DomainInfo info : domainInfos) {
                ret.add(new DomainInfoVO(info));
            }
            return ret;
        }
    }

    @Override
    public ReauthoriseTransactionSearchResultVO getTransactionToReauthorise(AuthenticatedUserVO user, long offset,
            long limit, List<SortCriterion> sortBy)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException, AuthenticationException,
            SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ReauthoriseTransactionSearchResultVO(
                service.getTransactionToReauthorise(user, offset, limit, sortBy));
    }

    @Override
    public void sendEmailWithInvoices(AuthenticatedUserVO user, String invoiceNumber)
            throws InvoiceEmailException, AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.sendEmailWithInvoices(user, invoiceNumber);
    }

    @Override
    public TransactionSearchResultVO findHistoricalTransactions(AuthenticatedUserVO user,
            TransactionSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new TransactionSearchResultVO(service.findHistoricalTransactions(user, criteria.toSearchCriteria(),
                offset, limit, sortBy));
    }

    @Override
    public List<DomainPriceVO> getDomainPricing(AuthenticatedUserVO user)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toDomainPricingVOList(service.getDomainPricing(user));
    }

    private List<DomainPriceVO> toDomainPricingVOList(List<DomainPrice> domainPriceList) {
        if (Validator.isEmpty(domainPriceList))
            return new ArrayList<>(0);
        List<DomainPriceVO> ret = new ArrayList<>();
        for (DomainPrice domainPrice : domainPriceList) {
            ret.add(new DomainPriceVO(domainPrice));
        }
        return ret;
    }

    @Override
    public PaymentVO getRequestPrice(AuthenticatedUserVO user, OperationType operationType)
            throws SessionExpiredException, NicHandleNotFoundException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PaymentVO(service.getRequestPrice(user, operationType));
    }

}
