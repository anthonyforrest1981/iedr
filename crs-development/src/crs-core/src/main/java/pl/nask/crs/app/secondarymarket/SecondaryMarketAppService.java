package pl.nask.crs.app.secondarymarket;

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
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public interface SecondaryMarketAppService {

    boolean canDomainBePurchased(AuthenticatedUser user, String domainName) throws AccessDeniedException;

    boolean canDomainBeSold(AuthenticatedUser user, String domainName) throws AccessDeniedException;

    BuyRequest getBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException;

    SellRequest getSellRequest(AuthenticatedUser user, long id)
            throws SellRequestNotFoundException, AccessDeniedException;

    List<HistoricalObject<BuyRequest>> getBuyRequestHistory(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException;

    HistoricalObject<BuyRequest> getHistoricalBuyRequest(AuthenticatedUser user, long changeId)
            throws AccessDeniedException;

    HistoricalObject<SellRequest> getHistoricalSellRequest(AuthenticatedUser user, long changeId)
            throws AccessDeniedException;

    LimitedSearchResult<BuyRequest> findBuyRequests(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<SellRequest> findSellRequests(AuthenticatedUser user, SellRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<BuyRequest> findOwnBuyRequests(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy)
    throws AccessDeniedException, NicHandleNotFoundException;

    LimitedSearchResult<SellRequest> findOwnSellRequests(AuthenticatedUser user, SellRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy)
            throws NicHandleNotFoundException, AccessDeniedException;

    LimitedSearchResult<HistoricalObject<BuyRequest>> findHistoricalBuyRequests(AuthenticatedUser user,
            HistoricalBuyRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    LimitedSearchResult<HistoricalObject<SellRequest>> findHistoricalSellRequests(AuthenticatedUser user,
            HistoricalSellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    long registerBuyRequest(AuthenticatedUser user, String domainName, String domainHolder, long domainOwnerTypeId,
            NewNicHandle newNicHandle, PaymentMethod paymentMethod, CreditCard creditCard, String remark)
            throws InvalidCountryException, HolderClassNotExistException, InvalidEmailException,
            DomainHolderTooLongException, ClassDoesNotMatchCategoryException, TransactionInvalidStateForSettlement,
            EmptyRemarkException, DomainHolderMandatoryException, DomainNotFoundException, CardPaymentException,
            AccountNotActiveException, NicHandleNotFoundException, NotEnoughDepositFundsException,
            InvalidCountyException, AccountNotFoundException, HolderCategoryNotExistException,
            DomainIllegalStateException, TransactionNotFoundException, AccessDeniedException,
            DomainNotAvailableForUserException, ExportException, OwnerTypeNotExistException, CharityOwnerTypeNotAllowed;

    long registerSellRequest(AuthenticatedUser user, String domainName, String authCode, PaymentMethod paymentMethod,
            CreditCard creditCard)
            throws DomainLockedException, DomainTransferPendingException, DomainNotFoundException,
            DomainIllegalStateException, CardPaymentException, NotEnoughDepositFundsException,
            TransactionInvalidStateForSettlement, TransactionNotFoundException, InvalidAuthCodeException,
            DomainNotAvailableForUserException, NicHandleException, EmptyRemarkException, TooManyTicketsException,
            SellRequestExistsException, AccessDeniedException;

    void modifyBuyRequest(AuthenticatedUser user, long buyRequestId, String domainHolder, NewNicHandle newNicHandle,
            String remark)
            throws AccessDeniedException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            DomainHolderMandatoryException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed;

    void modifyBuyRequestAsHostmaster(AuthenticatedUser user, long buyRequestId, String domainHolder,
            long domainHolderClassId, long domainHolderCategoryId, NewNicHandle newNicHandle,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFRm, String remark)
            throws AccessDeniedException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            ClassDoesNotMatchCategoryException, DomainHolderMandatoryException, HolderCategoryNotExistException,
            HolderClassNotExistException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestNotCheckedOutToUserException,
            BuyRequestFrozenAsPassed;

    void acceptBuyRequest(AuthenticatedUser user, long buyRequestId, String remark)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException,
            BuyRequestFrozenAsPassed, EmailException, DomainNotFoundException;

    void rejectBuyRequest(AuthenticatedUser user, long buyRequestId, BuyRequestStatus newStatus, String remark,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR)
            throws AccessDeniedException, BuyRequestFrozenAsPassed, EmptyRemarkException, BuyRequestNotFoundException,
            BuyRequestNotCheckedOutToUserException, BadBuyRequestStatusException, DomainNotFoundException,
            NicHandleNotFoundException, EmailException, DomainIllegalStateException;

    void saveBuyRequest(AuthenticatedUser user, long buyRequestId, String remark, FailureReason domainNameFR,
            FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR,
            FailureReason adminEmailFR, FailureReason adminCompanyNameFR, FailureReason adminAddressFR,
            FailureReason adminCountryFR, FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR)
            throws AccessDeniedException, BuyRequestFrozenAsPassed, BuyRequestNotFoundException,
            BuyRequestNotCheckedOutToUserException, EmptyRemarkException;

    void cancelBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestCheckedOutException;

    boolean isBuyRequestUsedInSale(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException;

    void invalidateBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BadBuyRequestStatusException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException, BuyRequestUsedInSaleException,
            DomainIllegalStateException;

    boolean verifyAuthCode(AuthenticatedUser user, String domainName, String authCode) throws AccessDeniedException;

    void regenerateAndResendAuthCode(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotPassed, EmailException,
            DomainNotFoundException;

    void checkoutBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestCheckedOutException, BuyRequestNotFoundException,
            BuyRequestFrozenAsPassed;

    void reassignBuyRequest(AuthenticatedUser user, long buyRequestId, String newCheckoutToNicHandle)
            throws AccessDeniedException, BuyRequestNotCheckedOutToUserException, BuyRequestNotFoundException,
            BuyRequestFrozenAsPassed, UserCannotCheckoutBuyRequestException;

    void checkinBuyRequest(AuthenticatedUser user, long buyRequestId, BuyRequestStatus newStatus)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException,
            BadBuyRequestStatusException, BuyRequestFrozenAsPassed;

    void cancelSellRequest(AuthenticatedUser user, long sellRequestId)
            throws AccessDeniedException, SellRequestNotFoundException, DomainIllegalStateException,
            DomainNotFoundException;

    List<SellRequest> findSellRequestsToComplete(AuthenticatedUser user) throws AccessDeniedException;

    void completeSellRequest(AuthenticatedUser user, long sellRequestId)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            SellRequestNotFoundException, DomainNotFoundException, DomainNotAvailableForUserException,
            EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException,
            ExportException, InvalidEmailException, NicHandleException, DomainIllegalStateException, EmailException,
            DocumentGeneralException;

    List<BuyRequestNotification> findBuyRequestNotifications(AuthenticatedUser user,
            BuyRequestNotificationType notificationType)
            throws AccessDeniedException;

    void sendBuyRequestNotification(AuthenticatedUser user, BuyRequestNotification notification)
            throws AccessDeniedException, DomainNotFoundException, NicHandleNotFoundException, EmailException;

    List<Long> findBuyRequestsWithCompletedSales(AuthenticatedUser user) throws AccessDeniedException;

    List<BuyRequest> findBuyRequestsWithExpiredAuthCode(AuthenticatedUser user) throws AccessDeniedException;

    void deleteBuyRequestWithCompletedSale(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, DomainNotFoundException,
            NicHandleNotFoundException, EmailException;

    void deleteBuyRequestWithExpiredAuthCode(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, DomainIllegalStateException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException;

    List<BuyRequest> findBuyRequestsToCleanup(AuthenticatedUser user) throws AccessDeniedException;

    void cleanupBuyRequest(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws AccessDeniedException, DomainIllegalStateException, DomainNotFoundException,
            BuyRequestNotFoundException, NicHandleNotFoundException, EmailException;

}
