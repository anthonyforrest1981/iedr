package pl.nask.crs.app.secondarymarket.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.*;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.services.SecondaryMarketService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.service.UserSearchService;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class SecondaryMarketAppServiceImpl implements SecondaryMarketAppService {

    public static final String DEFAULT_ACCEPT_REMARK = "This application has been accepted";
    private final SecondaryMarketService secondaryMarketService;
    private final NicHandleSearchService nicHandleSearchService;
    private final UserSearchService userSearchService;
    private final ApplicationConfig applicationConfig;

    public SecondaryMarketAppServiceImpl(SecondaryMarketService secondaryMarketService,
            NicHandleSearchService nicHandleSearchService,  UserSearchService userSearchService,
            ApplicationConfig applicationConfig) {
        this.secondaryMarketService = secondaryMarketService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.userSearchService = userSearchService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canDomainBePurchased(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.canDomainBePurchased(domainName, user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canDomainBeSold(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.canDomainBeSold(domainName, user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public BuyRequest getBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException {
        validateLoggedIn(user);
        return secondaryMarketService.getBuyRequest(buyRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public SellRequest getSellRequest(AuthenticatedUser user, long id)
            throws SellRequestNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.getSellRequest(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricalObject<BuyRequest>> getBuyRequestHistory(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.getBuyRequestHistory(buyRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricalObject<BuyRequest> getHistoricalBuyRequest(AuthenticatedUser user, long changeId)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.getHistoricalBuyRequest(changeId);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricalObject<SellRequest> getHistoricalSellRequest(AuthenticatedUser user, long changeId)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.getHistoricalSellRequest(changeId);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<BuyRequest> findBuyRequests(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findBuyRequests(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<SellRequest> findSellRequests(AuthenticatedUser user, SellRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findSellRequests(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<BuyRequest> findOwnBuyRequests(AuthenticatedUser user, BuyRequestSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        applyOwnFilter(criteria, user.getUsername());
        return secondaryMarketService.findBuyRequests(criteria, offset, limit, sortBy);
    }

    private void applyOwnFilter(BuyRequestSearchCriteria criteria, String nicHandleId)
            throws NicHandleNotFoundException {
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        if (nicHandleSearchService.isNicHandleDirect(nicHandle)) {
            criteria.setCreatorNH(nicHandleId);
        } else {
            criteria.setBuyerAccountId(nicHandle.getAccount().getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<SellRequest> findOwnSellRequests(AuthenticatedUser user,
            SellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws NicHandleNotFoundException, AccessDeniedException {
        validateLoggedIn(user);
        applyOwnFilter(criteria, user.getUsername());
        return secondaryMarketService.findSellRequests(criteria, offset, limit, sortBy);
    }

    private void applyOwnFilter(SellRequestSearchCriteria criteria, String nicHandleId)
            throws NicHandleNotFoundException {
        User user = userSearchService.get(nicHandleId);
        if (user.hasGroup(Level.Registrar) || user.hasGroup(Level.SuperRegistrar)) {
            criteria.setAccountId(nicHandleSearchService.getNicHandle(nicHandleId).getAccount().getId());
        } else {
            criteria.setContactNH(nicHandleId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<HistoricalObject<BuyRequest>> findHistoricalBuyRequests(AuthenticatedUser user,
            HistoricalBuyRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findHistoricalBuyRequests(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<HistoricalObject<SellRequest>> findHistoricalSellRequests(AuthenticatedUser user,
            HistoricalSellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findHistoricalSellRequests(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long registerBuyRequest(AuthenticatedUser user, String domainName, String domainHolder,
            long domainOwnerTypeId, NewNicHandle newNicHandle, PaymentMethod paymentMethod, CreditCard creditCard,
            String remark) throws InvalidCountryException, HolderClassNotExistException, InvalidEmailException,
            DomainHolderTooLongException, ClassDoesNotMatchCategoryException, TransactionInvalidStateForSettlement,
            EmptyRemarkException, DomainHolderMandatoryException, DomainNotFoundException, CardPaymentException,
            AccountNotActiveException, NicHandleNotFoundException, NotEnoughDepositFundsException,
            InvalidCountyException, AccountNotFoundException, HolderCategoryNotExistException,
            DomainIllegalStateException, TransactionNotFoundException, AccessDeniedException,
            DomainNotAvailableForUserException, ExportException, OwnerTypeNotExistException,
            CharityOwnerTypeNotAllowed {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, remark);
        return secondaryMarketService.registerBuyRequest(user, domainName, domainHolder, domainOwnerTypeId,
                newNicHandle, paymentMethod, creditCard, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long registerSellRequest(AuthenticatedUser user, String domainName, String authCode,
            PaymentMethod paymentMethod, CreditCard creditCard)
            throws DomainLockedException, DomainTransferPendingException, DomainNotFoundException,
            DomainIllegalStateException, CardPaymentException, NotEnoughDepositFundsException,
            TransactionInvalidStateForSettlement, TransactionNotFoundException, InvalidAuthCodeException,
            DomainNotAvailableForUserException, NicHandleException, EmptyRemarkException, TooManyTicketsException,
            SellRequestExistsException, AccessDeniedException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        return secondaryMarketService
                .registerSellRequest(user, domainName, authCode, paymentMethod, creditCard, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyBuyRequest(AuthenticatedUser user, long buyRequestId, String domainHolder,
            NewNicHandle newNicHandle, String remark)
            throws AccessDeniedException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            DomainHolderMandatoryException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, remark);
        secondaryMarketService.modifyBuyRequest(opInfo, buyRequestId, domainHolder, newNicHandle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyBuyRequestAsHostmaster(AuthenticatedUser user, long buyRequestId, String domainHolder,
            long domainHolderClassId, long domainHolderCategoryId, NewNicHandle newNicHandle,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR, String remark)
            throws AccessDeniedException, InvalidCountyException, AccountNotActiveException, EmptyRemarkException,
            ClassDoesNotMatchCategoryException, DomainHolderMandatoryException, HolderCategoryNotExistException,
            HolderClassNotExistException, InvalidEmailException, AccountNotFoundException, InvalidCountryException,
            BuyRequestNotFoundException, DomainHolderTooLongException, BuyRequestNotCheckedOutToUserException,
            BuyRequestFrozenAsPassed {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, remark);
        secondaryMarketService.modifyBuyRequestAsHostmaster(opInfo, buyRequestId, domainHolder, domainHolderClassId,
                domainHolderCategoryId, newNicHandle, domainNameFR, domainHolderFR, categoryFR, classFR, adminNameFR,
                adminEmailFR, adminCompanyNameFR, adminAddressFR, adminCountryFR, adminCountyFR, phonesFR, faxesFR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptBuyRequest(AuthenticatedUser user, long buyRequestId, String remark)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException,
            BuyRequestFrozenAsPassed, EmailException, DomainNotFoundException {
        validateLoggedIn(user);
        if (Validator.isEmpty(remark)) {
            remark = DEFAULT_ACCEPT_REMARK;
        }
        OpInfo opInfo = new OpInfo(user, remark);
        secondaryMarketService.acceptBuyRequest(opInfo, buyRequestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectBuyRequest(AuthenticatedUser user, long buyRequestId, BuyRequestStatus newStatus, String remark,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR)
            throws AccessDeniedException, BuyRequestFrozenAsPassed, EmptyRemarkException,
            BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BadBuyRequestStatusException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException, DomainIllegalStateException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, remark);
        secondaryMarketService
                .rejectBuyRequest(user, opInfo, buyRequestId, newStatus, domainNameFR, domainHolderFR, categoryFR,
                        classFR, adminNameFR, adminEmailFR, adminCompanyNameFR, adminAddressFR, adminCountryFR,
                        adminCountyFR, phonesFR, faxesFR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBuyRequest(AuthenticatedUser user, long buyRequestId, String remark, FailureReason domainNameFR,
            FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR,
            FailureReason adminEmailFR, FailureReason adminCompanyNameFR, FailureReason adminAddressFR,
            FailureReason adminCountryFR, FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestFrozenAsPassed, BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException,
            AccessDeniedException, EmptyRemarkException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, remark);
        secondaryMarketService
                .saveBuyRequest(opInfo, buyRequestId, domainNameFR, domainHolderFR, categoryFR, classFR, adminNameFR,
                        adminEmailFR, adminCompanyNameFR, adminAddressFR, adminCountryFR, adminCountyFR, phonesFR,
                        faxesFR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestCheckedOutException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        secondaryMarketService.cancelBuyRequest(user, opInfo, buyRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBuyRequestUsedInSale(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException {
        validateLoggedIn(user);
        return secondaryMarketService.isBuyRequestUsedInSale(buyRequestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelSellRequest(AuthenticatedUser user, long sellRequestId)
            throws AccessDeniedException, SellRequestNotFoundException, DomainIllegalStateException,
            DomainNotFoundException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        secondaryMarketService.cancelSellRequest(user, sellRequestId, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidateBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BadBuyRequestStatusException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException, BuyRequestUsedInSaleException,
            DomainIllegalStateException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        secondaryMarketService.invalidateBuyRequest(user, opInfo, buyRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyAuthCode(AuthenticatedUser user, String domainName, String authCode)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.verifyAuthCode(domainName, authCode);
    }

    @Override
    @Transactional
    public void regenerateAndResendAuthCode(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotPassed, EmailException,
            DomainNotFoundException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        secondaryMarketService.regenerateAndResendAuthCode(opInfo, buyRequestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkoutBuyRequest(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestCheckedOutException, BuyRequestNotFoundException,
            BuyRequestFrozenAsPassed {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Checkout");
        secondaryMarketService.checkoutBuyRequest(opInfo, buyRequestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reassignBuyRequest(AuthenticatedUser user, long buyRequestId, String newCheckoutToNicHandle)
            throws AccessDeniedException, BuyRequestNotCheckedOutToUserException, BuyRequestNotFoundException,
            BuyRequestFrozenAsPassed, UserCannotCheckoutBuyRequestException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Reassign");
        secondaryMarketService.reassignBuyRequest(opInfo, buyRequestId, newCheckoutToNicHandle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkinBuyRequest(AuthenticatedUser user, long buyRequestId, BuyRequestStatus newStatus)
            throws AccessDeniedException, BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException,
            BadBuyRequestStatusException, BuyRequestFrozenAsPassed {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Checkin");
        secondaryMarketService.checkinBuyRequest(opInfo, buyRequestId, newStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellRequest> findSellRequestsToComplete(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        int period = applicationConfig.getSecondaryMarketCountdownPeriod();
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), -period));
        return secondaryMarketService.findSellRequests(criteria, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeSellRequest(AuthenticatedUser user, long sellRequestId)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            SellRequestNotFoundException, DomainNotFoundException, DomainNotAvailableForUserException,
            EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException,
            ExportException, InvalidEmailException, NicHandleException, DomainIllegalStateException, EmailException,
            DocumentGeneralException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Completing registrant transfer");
        secondaryMarketService.completeSellRequest(user, sellRequestId, opInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyRequestNotification> findBuyRequestNotifications(AuthenticatedUser user,
            BuyRequestNotificationType notificationType)
            throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findBuyRequestNotifications(notificationType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendBuyRequestNotification(AuthenticatedUser user, BuyRequestNotification notification)
            throws AccessDeniedException, DomainNotFoundException, NicHandleNotFoundException, EmailException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        secondaryMarketService.sendBuyRequestNotification(opInfo, notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findBuyRequestsWithCompletedSales(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return secondaryMarketService.findBuyRequestsWithCompletedSales();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyRequest> findBuyRequestsWithExpiredAuthCode(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        int period = applicationConfig.getSecondaryMarketAuthcodeExpirationPeriod();
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateTo(DateUtils.addDays(new Date(), -period));
        return secondaryMarketService.findBuyRequests(criteria, null);
    }

    public void deleteBuyRequestWithCompletedSale(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, DomainNotFoundException,
            NicHandleNotFoundException, EmailException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Sale completed");
        secondaryMarketService.deleteBuyRequestWithCompletedSale(user, buyRequestId, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBuyRequestWithExpiredAuthCode(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException, DomainIllegalStateException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Authcode expired");
        secondaryMarketService.deleteBuyRequestWithExpiredAuthCode(user, buyRequestId, opInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyRequest> findBuyRequestsToCleanup(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        final int period = applicationConfig.getTicketExpirationPeriod();
        criteria.setCreationDateTo(DateUtils.addDays(new Date(), -period));
        criteria.setStatuses(BuyRequestStatus.valuesExcept(BuyRequestStatus.PASSED));
        return secondaryMarketService.findBuyRequests(criteria, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanupBuyRequest(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws AccessDeniedException, DomainIllegalStateException, DomainNotFoundException,
            BuyRequestNotFoundException, NicHandleNotFoundException, EmailException {
        validateLoggedIn(user);
        secondaryMarketService.cleanupBuyRequest(user, buyRequestId, opInfo);
    }

}
