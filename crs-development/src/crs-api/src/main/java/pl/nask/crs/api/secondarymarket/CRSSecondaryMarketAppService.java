package pl.nask.crs.api.secondarymarket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.BuyRequestSearchCriteriaVO;
import pl.nask.crs.api.vo.search.SellRequestSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.email.service.EmailException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.security.authentication.AuthenticationException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSSecondaryMarketAppService {

    @WebMethod
    boolean canDomainBePurchased(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    boolean canDomainBeSold(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    BuyRequestVO getBuyRequest(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "buyRequestId") long buyRequestId)
            throws BuyRequestNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    BuyRequestSearchResultVO findOwnBuyRequests(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") BuyRequestSearchCriteriaVO criteriaVO,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException;

    @WebMethod
    SellRequestSearchResultVO findOwnSellRequests(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") SellRequestSearchCriteriaVO criteriaVO,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException;

    @WebMethod
    long registerBuyRequest(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName, @WebParam(name = "domainHolder") String domainHolder,
            @WebParam(name = "domainOwnerTypeId") long domainOwnerTypeId,
            @WebParam(name = "newNicHandle") NicHandleEditVO newNicHandleVO,
            @WebParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @WebParam(name = "creditCard") CreditCardVO creditCardVO, @WebParam(name = "remark") String remark)
            throws InvalidCountryException, HolderClassNotExistException, CardPaymentException,
            ClassDoesNotMatchCategoryException, TransactionInvalidStateForSettlement, EmptyRemarkException,
            DomainHolderMandatoryException, HolderCategoryNotExistException, DomainNotFoundException,
            InvalidEmailException, AccountNotActiveException, NicHandleNotFoundException,
            NotEnoughDepositFundsException, InvalidCountyException, AccountNotFoundException,
            DomainHolderTooLongException, DomainIllegalStateException, TransactionNotFoundException,
            AuthenticationException, GenericValidationException, SessionExpiredException,
            DomainNotAvailableForUserException, ExportException, OwnerTypeNotExistException, CharityOwnerTypeNotAllowed;

    @WebMethod
    long registerSellRequest(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName, @WebParam(name = "authCode") String authCode,
            @WebParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws AuthenticationException, SessionExpiredException, DomainLockedException,
            DomainTransferPendingException, DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            NotEnoughDepositFundsException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            InvalidAuthCodeException, DomainNotAvailableForUserException, NicHandleException, EmptyRemarkException,
            TooManyTicketsException, SellRequestExistsException;

    @WebMethod
    void modifyBuyRequest(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "buyRequestId") long buyRequestId, @WebParam(name = "domainHolder") String domainHolder,
            @WebParam(name = "newNicHandle") NicHandleEditVO newNicHandleVO, @WebParam(name = "remark") String remark)
            throws AuthenticationException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
             DomainHolderMandatoryException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed, SessionExpiredException;

    @WebMethod
    void cancelBuyRequest(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "buyRequestId") long buyRequestId)
            throws AuthenticationException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestCheckedOutException, SessionExpiredException;

    @WebMethod
    boolean verifyAuthCode(@WebParam(name = "user")AuthenticatedUserVO user,
            @WebParam(name = "domainName")String domainName, @WebParam(name = "authCode") String authCode)
            throws AuthenticationException, SessionExpiredException;

}
