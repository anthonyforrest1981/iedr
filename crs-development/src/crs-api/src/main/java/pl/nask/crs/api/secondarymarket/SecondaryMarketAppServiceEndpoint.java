package pl.nask.crs.api.secondarymarket;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.BuyRequestSearchCriteriaVO;
import pl.nask.crs.api.vo.search.SellRequestSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.email.service.EmailException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
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
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;

public class SecondaryMarketAppServiceEndpoint extends WsSessionAware implements CRSSecondaryMarketAppService {

    private SecondaryMarketAppService service;

    public void setService(SecondaryMarketAppService service) {
        this.service = service;
    }

    @Override
    public boolean canDomainBePurchased(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return service.canDomainBePurchased(completeUser, domainName);
    }

    @Override
    public boolean canDomainBeSold(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return service.canDomainBeSold(completeUser, domainName);
    }

    @Override
    public BuyRequestVO getBuyRequest(AuthenticatedUserVO user, long buyRequestId)
            throws BuyRequestNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new BuyRequestVO(service.getBuyRequest(completeUser, buyRequestId));
    }

    @Override
    public BuyRequestSearchResultVO findOwnBuyRequests(AuthenticatedUserVO user, BuyRequestSearchCriteriaVO criteriaVO,
            long offset, long limit, List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        LimitedSearchResult<BuyRequest> result = service.findOwnBuyRequests(completeUser, criteriaVO.toSearchCriteria(),
                offset, limit, sortCriteria);
        return new BuyRequestSearchResultVO(result.getResults(), result.getTotalResults());
    }

    @Override
    public SellRequestSearchResultVO findOwnSellRequests(AuthenticatedUserVO user,
            SellRequestSearchCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        LimitedSearchResult<SellRequest> result = service.findOwnSellRequests(completeUser, criteriaVO.toSearchCriteria(),
                offset, limit, sortCriteria);
        return new SellRequestSearchResultVO(result.getResults(), result.getTotalResults());
    }

    @Override
    public long registerBuyRequest(AuthenticatedUserVO user, String domainName, String domainHolder,
            long domainOwnerTypeId, NicHandleEditVO nicHandleDetails, PaymentMethod paymentMethod,
            CreditCardVO creditCardVO, String remark)
            throws AuthenticationException, InvalidCountryException, HolderClassNotExistException, CardPaymentException,
            ClassDoesNotMatchCategoryException, TransactionInvalidStateForSettlement, EmptyRemarkException,
            DomainHolderMandatoryException, HolderCategoryNotExistException, DomainNotFoundException,
            InvalidEmailException, AccountNotActiveException, NicHandleNotFoundException,
            NotEnoughDepositFundsException, InvalidCountyException, AccountNotFoundException,
            DomainHolderTooLongException, DomainIllegalStateException, TransactionNotFoundException,
            GenericValidationException, SessionExpiredException, DomainNotAvailableForUserException, ExportException,
            OwnerTypeNotExistException, CharityOwnerTypeNotAllowed {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        if (creditCardVO != null) {
            ValidationHelper.validate(creditCardVO);
        }
        CreditCard creditCard = creditCardVO == null ? null : creditCardVO.toCreditCard();
        return service.registerBuyRequest(completeUser, domainName, domainHolder, domainOwnerTypeId,
                nicHandleDetails.toNewNicHandle(), paymentMethod, creditCard, remark);
    }

    @Override
    public long registerSellRequest(AuthenticatedUserVO user, String domainName, String authCode,
            PaymentMethod paymentMethod, CreditCardVO creditCardVO)
            throws AuthenticationException, SessionExpiredException, DomainLockedException,
            DomainTransferPendingException, DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            NotEnoughDepositFundsException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            InvalidAuthCodeException, DomainNotAvailableForUserException, NicHandleException, EmptyRemarkException,
            TooManyTicketsException, SellRequestExistsException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        CreditCard creditCard = creditCardVO == null ? null : creditCardVO.toCreditCard();
        return service.registerSellRequest(completeUser, domainName, authCode, paymentMethod, creditCard);
    }

    @Override
    public void modifyBuyRequest(AuthenticatedUserVO user, long buyRequestId, String domainHolder,
            NicHandleEditVO nicHandleDetails, String remark)
            throws AuthenticationException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            DomainHolderMandatoryException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        service.modifyBuyRequest(completeUser, buyRequestId, domainHolder, nicHandleDetails.toNewNicHandle(), remark);
    }

    @Override
    public void cancelBuyRequest(AuthenticatedUserVO user, long buyRequestId)
            throws AuthenticationException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestCheckedOutException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        service.cancelBuyRequest(completeUser, buyRequestId);
    }

    @Override
    public boolean verifyAuthCode(AuthenticatedUserVO user, String domainName, String authCode)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return service.verifyAuthCode(completeUser, domainName, authCode);
    }

}
