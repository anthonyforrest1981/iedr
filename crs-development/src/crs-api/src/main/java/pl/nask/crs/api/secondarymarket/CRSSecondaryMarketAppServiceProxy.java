package pl.nask.crs.api.secondarymarket;

import java.util.List;

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
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.security.authentication.AuthenticationException;

@WebService(
    serviceName = "CRSSecondaryMarketAppService",
    endpointInterface = "pl.nask.crs.api.secondarymarket.CRSSecondaryMarketAppService",
    portName = "CRSSecondaryMarketAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSSecondaryMarketAppServiceProxy implements CRSSecondaryMarketAppService {

    private CRSSecondaryMarketAppService service;

    public void setService(CRSSecondaryMarketAppService service) {
        this.service = service;
    }

    public boolean canDomainBePurchased(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.canDomainBePurchased(user, domainName);
    }

    @Override
    public boolean canDomainBeSold(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.canDomainBeSold(user, domainName);
    }

    @Override
    public BuyRequestVO getBuyRequest(AuthenticatedUserVO user, long buyRequestId)
            throws BuyRequestNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.getBuyRequest(user, buyRequestId);
    }

    @Override
    public BuyRequestSearchResultVO findOwnBuyRequests(AuthenticatedUserVO user, BuyRequestSearchCriteriaVO criteriaVO,
            long offset, long limit, List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException {
        return service.findOwnBuyRequests(user, criteriaVO, offset, limit, sortCriteria);
    }

    @Override
    public SellRequestSearchResultVO findOwnSellRequests(AuthenticatedUserVO user,
            SellRequestSearchCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, NicHandleNotFoundException {
        return service.findOwnSellRequests(user, criteriaVO, offset, limit, sortCriteria);
    }

    @Override
    public long registerBuyRequest(AuthenticatedUserVO user, String domainName, String domainHolder,
            long domainOwnerTypeId, NicHandleEditVO newNicHandleVO, PaymentMethod paymentMethod,
            CreditCardVO creditCardVO, String remark)
            throws InvalidCountryException, HolderClassNotExistException, CardPaymentException,
            ClassDoesNotMatchCategoryException, TransactionInvalidStateForSettlement, EmptyRemarkException,
            DomainHolderMandatoryException, HolderCategoryNotExistException, DomainNotFoundException,
            InvalidEmailException, AccountNotActiveException, NicHandleNotFoundException,
            NotEnoughDepositFundsException, InvalidCountyException, AccountNotFoundException,
            DomainHolderTooLongException, DomainIllegalStateException, TransactionNotFoundException,
            AuthenticationException, GenericValidationException, SessionExpiredException,
            DomainNotAvailableForUserException, ExportException, OwnerTypeNotExistException,
            CharityOwnerTypeNotAllowed {
        return service.registerBuyRequest(user, domainName, domainHolder, domainOwnerTypeId, newNicHandleVO,
                paymentMethod, creditCardVO, remark);
    }

    @Override
    public long registerSellRequest(AuthenticatedUserVO user, String domainName, String authCode,
            PaymentMethod paymentMethod, CreditCardVO creditCard)
            throws AuthenticationException, SessionExpiredException, DomainLockedException,
            DomainTransferPendingException, DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            NotEnoughDepositFundsException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            InvalidAuthCodeException, DomainNotAvailableForUserException, NicHandleException, EmptyRemarkException,
            TooManyTicketsException, SellRequestExistsException {
                return service.registerSellRequest(user, domainName, authCode, paymentMethod, creditCard);
    }

    @Override
    public void modifyBuyRequest(AuthenticatedUserVO user, long buyRequestId, String domainHolder,
            NicHandleEditVO newNicHandleVO, String remark)
            throws AuthenticationException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            DomainHolderMandatoryException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed, SessionExpiredException {
        service.modifyBuyRequest(user, buyRequestId, domainHolder, newNicHandleVO, remark);
    }

    @Override
    public void cancelBuyRequest(AuthenticatedUserVO user, long buyRequestId)
            throws AuthenticationException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestCheckedOutException, SessionExpiredException {
        service.cancelBuyRequest(user, buyRequestId);
    }

    @Override
    public boolean verifyAuthCode(AuthenticatedUserVO user, String domainName, String authCode)
            throws AuthenticationException, SessionExpiredException {
        return service.verifyAuthCode(user, domainName, authCode);
    }

}
