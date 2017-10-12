package pl.nask.crs.secondarymarket.services;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.*;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public interface SecondaryMarketService {

    boolean canDomainBePurchased(String domainName, String creatorNH);

    boolean canDomainBeSold(String domainName, String creatorNH);

    BuyRequest getBuyRequest(long buyRequestId) throws BuyRequestNotFoundException;

    SellRequest getSellRequest(long sellRequestId) throws SellRequestNotFoundException;

    List<HistoricalObject<BuyRequest>> getBuyRequestHistory(long buyRequestId);

    HistoricalObject<BuyRequest> getHistoricalBuyRequest(long changeId);

    HistoricalObject<SellRequest> getHistoricalSellRequest(long changeId);

    List<BuyRequest> findBuyRequests(BuyRequestSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<BuyRequest> findBuyRequests(BuyRequestSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    List<SellRequest> findSellRequests(SellRequestSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<SellRequest> findSellRequests(SellRequestSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    LimitedSearchResult<HistoricalObject<BuyRequest>> findHistoricalBuyRequests(
            HistoricalBuyRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<HistoricalObject<SellRequest>> findHistoricalSellRequests(
            HistoricalSellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    long registerBuyRequest(AuthenticatedUser user, String domainName, String domainHolder, long domainOwnerTypeId,
            NewNicHandle newNicHandle, PaymentMethod paymentMethod, CreditCard creditCard, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            TransactionInvalidStateForSettlement, NotEnoughDepositFundsException, NicHandleNotFoundException,
            TransactionNotFoundException, HolderCategoryNotExistException, InvalidCountryException,
            HolderClassNotExistException, InvalidCountyException, AccountNotFoundException, InvalidEmailException,
            AccountNotActiveException, EmptyRemarkException, DomainHolderTooLongException,
            ClassDoesNotMatchCategoryException, DomainHolderMandatoryException, DomainNotAvailableForUserException,
            ExportException, OwnerTypeNotExistException, CharityOwnerTypeNotAllowed;

    long registerSellRequest(AuthenticatedUser user, String domainName, String authCode, PaymentMethod paymentMethod,
            CreditCard creditCard, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            NotEnoughDepositFundsException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            InvalidAuthCodeException, DomainNotAvailableForUserException, DomainLockedException, NicHandleException,
            EmptyRemarkException, TooManyTicketsException, DomainTransferPendingException, SellRequestExistsException;

    void modifyBuyRequest(OpInfo opInfo, long buyRequestId, String domainHolder, NewNicHandle newNicHandle)
            throws BuyRequestNotFoundException, InvalidCountryException, InvalidCountyException,
            AccountNotFoundException, InvalidEmailException, DomainHolderTooLongException, AccountNotActiveException,
            DomainHolderMandatoryException, EmptyRemarkException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed;

    void modifyBuyRequestAsHostmaster(OpInfo opInfo, long buyRequestId, String domainHolder, long domainHolderClassId,
            long domainHolderCategoryId, NewNicHandle newNicHandle, FailureReason domainNameFR,
            FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR,
            FailureReason adminEmailFR, FailureReason adminCompanyNameFR, FailureReason adminAddressFR,
            FailureReason adminCountryFR, FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            InvalidCountryException, InvalidCountyException, AccountNotFoundException,
            ClassDoesNotMatchCategoryException,
            InvalidEmailException, DomainHolderTooLongException, AccountNotActiveException,
            DomainHolderMandatoryException, EmptyRemarkException, BuyRequestNotCheckedOutToUserException,
            BuyRequestFrozenAsPassed;

    void acceptBuyRequest(OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            DomainNotFoundException, EmailException;

    void rejectBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            EmptyRemarkException, BadBuyRequestStatusException, DomainNotFoundException, NicHandleNotFoundException,
            EmailException, DomainIllegalStateException;

    void saveBuyRequest(OpInfo opInfo, long buyRequestId, FailureReason domainNameFR, FailureReason domainHolderFR,
            FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR, FailureReason adminEmailFR,
            FailureReason adminCompanyNameFR, FailureReason adminAddressFR, FailureReason adminCountryFR,
            FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            EmptyRemarkException;

    void updateStatus(OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus)
            throws BuyRequestNotFoundException, BuyRequestFrozenAsPassed, EmptyRemarkException;

    void cancelBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestFrozenAsPassed, DomainIllegalStateException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException, BuyRequestCheckedOutException;

    void invalidateBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BadBuyRequestStatusException, EmailException, DomainNotFoundException,
            NicHandleNotFoundException, BuyRequestUsedInSaleException, DomainIllegalStateException;

    boolean isBuyRequestUsedInSale(long buyRequestId) throws BuyRequestNotFoundException;

    boolean verifyAuthCode(String domainName, String authCode);

    void regenerateAndResendAuthCode(OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestNotPassed, EmailException, DomainNotFoundException;

    void checkoutBuyRequest(OpInfo opInfo, long buyRequestId)
            throws BuyRequestCheckedOutException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed;

    void reassignBuyRequest(OpInfo opInfo, long buyRequestId, String newCheckoutToNicHandle)
            throws BuyRequestNotCheckedOutToUserException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            UserCannotCheckoutBuyRequestException;

    void checkinBuyRequest(OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BadBuyRequestStatusException,
            BuyRequestFrozenAsPassed;

    void cancelSellRequest(AuthenticatedUser user, long sellRequestId, OpInfo opInfo)
            throws SellRequestNotFoundException, DomainIllegalStateException, DomainNotFoundException;

    void completeSellRequest(AuthenticatedUser user, long sellRequestId, OpInfo opInfo)
            throws SellRequestNotFoundException, DomainNotFoundException, DomainNotAvailableForUserException,
            AccountNotFoundException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException, NicHandleException,
            DomainIllegalStateException, EmailException, DocumentGeneralException;

    List<BuyRequestNotification> findBuyRequestNotifications(BuyRequestNotificationType notificationType);

    void sendBuyRequestNotification(OpInfo opInfo, BuyRequestNotification notification)
            throws DomainNotFoundException, NicHandleNotFoundException, EmailException;

    List<Long> findBuyRequestsWithCompletedSales();

    void deleteBuyRequestWithCompletedSale(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws BuyRequestNotFoundException, DomainNotFoundException, NicHandleNotFoundException, EmailException;

    void deleteBuyRequestWithExpiredAuthCode(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws BuyRequestNotFoundException, DomainIllegalStateException, DomainNotFoundException,
            EmailException;

    void cleanupBuyRequest(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestNotFoundException;

    void deleteAllRequests(AuthenticatedUser user, Domain domain, OpInfo opInfo);

}
