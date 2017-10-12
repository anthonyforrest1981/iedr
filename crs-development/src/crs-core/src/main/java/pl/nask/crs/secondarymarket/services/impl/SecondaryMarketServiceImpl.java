package pl.nask.crs.secondarymarket.services.impl;

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.AuthcodeGenerator;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.*;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.SecondaryMarketStatus;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.CancelSellRequest;
import pl.nask.crs.domains.dsm.events.CompleteSellRequest;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.nichandle.service.impl.helper.NicHandleValidator;
import pl.nask.crs.nichandle.service.impl.helper.PasswordHelper;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.secondarymarket.*;
import pl.nask.crs.secondarymarket.dao.*;
import pl.nask.crs.secondarymarket.emails.BuyRequestExpirationEmailParameters;
import pl.nask.crs.secondarymarket.emails.SecondaryMarketRequestEmailParameters;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.services.SecondaryMarketService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class SecondaryMarketServiceImpl implements SecondaryMarketService {

    private static final String AUTHCODE_PREFIX = "RT";
    private static final int AUTHCODE_LENGTH = 12;
    private static final ImmutableSet<BuyRequestStatus> FORBIDDEN_REJECT_STATUSES =
            Sets.immutableEnumSet(BuyRequestStatus.PASSED);
    private static final ImmutableSet<BuyRequestStatus> FORBIDDEN_CHECKIN_STATUSES =
            Sets.immutableEnumSet(BuyRequestStatus.PASSED, BuyRequestStatus.CANCELLED);
    public static final int DOCUMENTS_BATCH_SIZE = 100;
    private static Logger LOG = Logger.getLogger(SecondaryMarketServiceImpl.class);

    private BuyRequestDAO buyRequestDAO;
    private HistoricalBuyRequestDAO historicalBuyRequestDAO;
    private SellRequestDAO sellRequestDAO;
    private HistoricalSellRequestDAO historicalSellRequestDAO;
    private BuyRequestNotificationDAO buyRequestNotificationDAO;
    private PaymentService paymentService;
    private EntityService entityService;
    private TicketSearchService ticketSearchService;
    private TicketService ticketService;
    private AccountSearchService accountSearchService;
    private DomainSearchService domainSearchService;
    private DomainService domainService;
    private NicHandleSearchService nicHandleSearchService;
    private NicHandleService nicHandleService;
    private DomainStateMachine domainStateMachine;
    private EmailTemplateSender emailTemplateSender;
    private CountryFactory countryFactory;
    private ApplicationConfig applicationConfig;
    private AuthorizationService authService;
    private DocumentService documentService;

    public SecondaryMarketServiceImpl(BuyRequestDAO buyRequestDAO, HistoricalBuyRequestDAO historicalBuyRequestDAO,
            SellRequestDAO sellRequestDAO, HistoricalSellRequestDAO historicalSellRequestDAO,
            BuyRequestNotificationDAO buyRequestNotificationDAO, PaymentService paymentService,
            EntityService entityService, TicketSearchService ticketSearchService,
            TicketService ticketService, AccountSearchService accountSearchService,
            DomainSearchService domainSearchService, DomainService domainService,
            NicHandleSearchService nicHandleSearchService, NicHandleService nicHandleService,
            DomainStateMachine domainStateMachine, EmailTemplateSender emailTemplateSender,
            CountryFactory countryFactory, ApplicationConfig applicationConfig, AuthorizationService authService) {
        Validator.assertNotNull(buyRequestDAO, "buyRequestDAO");
        Validator.assertNotNull(historicalBuyRequestDAO, "historicalBuyRequestDAO");
        Validator.assertNotNull(sellRequestDAO, "sellRequestDAO");
        Validator.assertNotNull(historicalSellRequestDAO, "historicalSellRequestDAO");
        Validator.assertNotNull(buyRequestNotificationDAO, "buyRequestNotificationDAO");
        Validator.assertNotNull(paymentService, "paymentService");
        Validator.assertNotNull(entityService, "entityService");
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(ticketService, "ticketService");
        Validator.assertNotNull(accountSearchService, "accountSearchService");
        Validator.assertNotNull(domainSearchService, "domainSearchService");
        Validator.assertNotNull(domainService, "domainService");
        Validator.assertNotNull(nicHandleSearchService, "nicHandleSearchService");
        Validator.assertNotNull(nicHandleService, "nicHandleService");
        Validator.assertNotNull(domainStateMachine, "domainStateMachine");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(countryFactory, "countryFactory");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        Validator.assertNotNull(authService, "authorizationService");
        this.buyRequestDAO = buyRequestDAO;
        this.historicalBuyRequestDAO = historicalBuyRequestDAO;
        this.sellRequestDAO = sellRequestDAO;
        this.historicalSellRequestDAO = historicalSellRequestDAO;
        this.buyRequestNotificationDAO = buyRequestNotificationDAO;
        this.paymentService = paymentService;
        this.entityService = entityService;
        this.ticketSearchService = ticketSearchService;
        this.ticketService = ticketService;
        this.accountSearchService = accountSearchService;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.domainStateMachine = domainStateMachine;
        this.emailTemplateSender = emailTemplateSender;
        this.countryFactory = countryFactory;
        this.applicationConfig = applicationConfig;
        this.authService = authService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public boolean canDomainBePurchased(String domainName, String creatorNH) {
        Domain domain;
        try {
            domain = domainSearchService.getDomain(domainName);
        } catch (DomainNotFoundException e) {
            return false;
        }
        try {
            validateIfDomainCanBePurchased(domain, creatorNH);
        } catch (NicHandleNotFoundException | DomainNotAvailableForUserException e) {
            return false;
        }
        return domainStateMachine.validateEvent(domainName, DsmEventName.RegisterBuyRequest);
    }

    @Override
    public boolean canDomainBeSold(String domainName, String creatorNH) {
        return buyRequestExistsForDomain(domainName) &&
                domainStateMachine.validateEvent(domainName, DsmEventName.RegisterSellRequest);
    }

    @Override
    public BuyRequest getBuyRequest(long buyRequestId) throws BuyRequestNotFoundException {
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        if (buyRequest == null) {
            throw new BuyRequestNotFoundException(buyRequestId);
        }
        return buyRequest;
    }

    @Override
    public SellRequest getSellRequest(long sellRequestId) throws SellRequestNotFoundException {
        SellRequest sellRequest = sellRequestDAO.get(sellRequestId);
        if (sellRequest == null) {
            throw new SellRequestNotFoundException(sellRequestId);
        }
        return sellRequest;
    }

    @Override
    public List<HistoricalObject<BuyRequest>> getBuyRequestHistory(long buyRequestId) {
        HistoricalBuyRequestSearchCriteria criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setBuyRequestId(buyRequestId);
        return historicalBuyRequestDAO.find(criteria, Arrays.asList(new SortCriterion("buyHistChangeDate", false),
                new SortCriterion("buyHistChangeId", false))).getResults();
    }

    @Override
    public HistoricalObject<BuyRequest> getHistoricalBuyRequest(long changeId) {
        return historicalBuyRequestDAO.get(changeId);
    }

    @Override
    public HistoricalObject<SellRequest> getHistoricalSellRequest(long changeId) {
        return historicalSellRequestDAO.get(changeId);
    }

    @Override
    public List<BuyRequest> findBuyRequests(BuyRequestSearchCriteria criteria, List<SortCriterion> sortBy) {
        return buyRequestDAO.find(criteria, sortBy).getResults();
    }

    @Override
    public LimitedSearchResult<BuyRequest> findBuyRequests(BuyRequestSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        return buyRequestDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public List<SellRequest> findSellRequests(SellRequestSearchCriteria criteria, List<SortCriterion> sortBy) {
        adjustSellRequestCriteria(criteria);
        return sellRequestDAO.find(criteria, sortBy).getResults();
    }

    @Override
    public LimitedSearchResult<SellRequest> findSellRequests(SellRequestSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        adjustSellRequestCriteria(criteria);
        return sellRequestDAO.find(criteria, offset, limit, sortBy);
    }

    private void adjustSellRequestCriteria(SellRequestSearchCriteria criteria) {
        if (criteria != null) {
            int period = applicationConfig.getSecondaryMarketCountdownPeriod();
            if (criteria.getCompleteFrom() != null) {
                Date createdFrom = criteria.getCreatedFrom();
                Date newCreatedFrom = DateUtils.addDays(criteria.getCompleteFrom(), -period);
                if (createdFrom == null || createdFrom.compareTo(newCreatedFrom) < 0) {
                    criteria.setCreatedFrom(newCreatedFrom);
                }
            }
            if (criteria.getCompleteTo() != null) {
                Date createdTo = criteria.getCreatedTo();
                Date newCreatedTo = DateUtils.addDays(criteria.getCompleteTo(), -period);
                if (createdTo == null || createdTo.compareTo(newCreatedTo) > 0) {
                    criteria.setCreatedTo(newCreatedTo);
                }
            }
        }
    }

    @Override
    public LimitedSearchResult<HistoricalObject<BuyRequest>> findHistoricalBuyRequests(
            HistoricalBuyRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return historicalBuyRequestDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<HistoricalObject<SellRequest>> findHistoricalSellRequests(
            HistoricalSellRequestSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return historicalSellRequestDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public long registerBuyRequest(AuthenticatedUser user, String domainName, String domainHolder,
            long domainOwnerTypeId, NewNicHandle newNicHandle, PaymentMethod paymentMethod, CreditCard creditCard,
            OpInfo opInfo) throws DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            TransactionInvalidStateForSettlement, NotEnoughDepositFundsException, NicHandleNotFoundException,
            TransactionNotFoundException, HolderCategoryNotExistException, HolderClassNotExistException,
            InvalidCountryException, InvalidCountyException, AccountNotFoundException, InvalidEmailException,
            AccountNotActiveException, EmptyRemarkException, DomainHolderTooLongException,
            ClassDoesNotMatchCategoryException, DomainHolderMandatoryException, DomainNotAvailableForUserException,
            ExportException, OwnerTypeNotExistException, CharityOwnerTypeNotAllowed {
        Domain domain = domainService.lock(domainName);
        validateIfDomainCanBePurchased(domain, opInfo.getUserName());
        OwnerType ownerType = entityService.getOwnerType(domainOwnerTypeId);
        EntityClass domainClass = entityService.getClass(ownerType.getClassId());
        EntityCategory domainCategory = entityService.getCategory(ownerType.getCategoryId());
        Country country = countryFactory.getCountry(newNicHandle.getCountryId());
        County county = countryFactory.getCounty(newNicHandle.getCountyId());
        Account account = domain.getResellerAccount();
        validateNewBuyRequest(domainHolder, ownerType, domainClass, domainCategory, account.getId(), newNicHandle,
                country, county, paymentMethod, creditCard, opInfo);
        long buyRequestId = createBuyRequestAndRunDsm(user, domainName, account, domainHolder, domainClass,
                domainCategory, newNicHandle, country, county, opInfo);
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        OpInfo exportOpInfo = new OpInfo(user, "NicHandle was exported due to registrant transfer");
        nicHandleService.triggerExport(user.getUsername(), exportOpInfo);
        paymentService.executePaymentForSecondaryMarketRequest(user, domainName, paymentMethod, creditCard,
                OperationType.BUY_REQUEST, buyRequestId, opInfo);
        sendBuyRequestRegistrationEmails(domain, buyRequest, user.getUsername(), opInfo);
        return buyRequestId;
    }

    @Override
    public void checkoutBuyRequest(OpInfo opInfo, long buyRequestId)
            throws BuyRequestCheckedOutException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestNotPassed(buyRequest);
        if (buyRequest.isCheckedOut()) {
            throw new BuyRequestCheckedOutException();
        }
        buyRequest.setCheckedOutTo(opInfo.getUserName());
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    @Override
    public void reassignBuyRequest(OpInfo opInfo, long buyRequestId, String newCheckoutToNicHandle)
            throws BuyRequestNotCheckedOutToUserException, BuyRequestNotFoundException, BuyRequestFrozenAsPassed,
            UserCannotCheckoutBuyRequestException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestNotPassed(buyRequest);
        validateNewHostmaster(newCheckoutToNicHandle);
        if (!buyRequest.isCheckedOut()) {
            throw new BuyRequestNotCheckedOutToUserException(buyRequest, opInfo.getActorName());
        }
        buyRequest.setCheckedOutTo(newCheckoutToNicHandle);
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    private void validateNewHostmaster(final String newHostmasterHandle) throws UserCannotCheckoutBuyRequestException {
        try {
            authService.authorize(newHostmasterHandle, new NamedPermissionQuery(
                    SecondaryMarketAppService.class.getCanonicalName() + ".checkoutBuyRequest"));
        } catch (PermissionDeniedException e) {
            throw new UserCannotCheckoutBuyRequestException("Cannot reassign buy request to user " + newHostmasterHandle
                    + " because the user doesn't have checkout permission");
        }
    }

    @Override
    public void checkinBuyRequest(OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BadBuyRequestStatusException,
            BuyRequestFrozenAsPassed {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestCheckinNewStatus(buyRequest, newStatus);
        validateBuyRequestCheckedOutTo(opInfo, buyRequest);
        validateBuyRequestNotPassed(buyRequest);
        setCheckinBuyRequest(opInfo.getRemark(), buyRequest, newStatus);
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    private void setCheckinBuyRequest(String remark, BuyRequest buyRequest, BuyRequestStatus newStatus) {
        buyRequest.setCheckedOutTo(null);
        buyRequest.setHostmasterRemark(remark);
        buyRequest.setStatus(newStatus);
    }

    private void updateBuyRequestWithHistory(OpInfo opInfo, BuyRequest request, Date changeDate) {
        long changeId = historicalBuyRequestDAO.create(request, changeDate, opInfo.getActorName());
        buyRequestDAO.updateUsingHistory(changeId);
    }

    private long createBuyRequestAndRunDsm(AuthenticatedUser user, String domainName, Account account,
            String domainHolder, EntityClass domainClass, EntityCategory domainCategory, NewNicHandle newNicHandle,
            Country country, County county, OpInfo opInfo)
            throws DomainIllegalStateException, DomainNotFoundException {
        String creatorNH = user.getUsername();
        Date creationDate = new Date();
        BuyRequest buyRequest = new BuyRequest(domainName, creatorNH, account, domainHolder, domainClass,
                domainCategory, opInfo.getRemark(), newNicHandle.getName(), newNicHandle.getEmail(),
                newNicHandle.getCompanyName(), newNicHandle.getAddress(), newNicHandle.getPhones(),
                newNicHandle.getFaxes(), country, county, creationDate);
        buyRequestDAO.create(buyRequest);
        updateBuyRequestWithHistory(opInfo, buyRequest, creationDate);
        domainStateMachine.handleEvent(user, domainName, DsmEventName.RegisterBuyRequest, opInfo);
        return buyRequest.getId();
    }

    private void validateIfDomainCanBePurchased(Domain domain, String creatorNH)
            throws NicHandleNotFoundException, DomainNotAvailableForUserException {
        NicHandle creator = nicHandleSearchService.getNicHandle(creatorNH);
        Account creatorAccount = creator.getAccount();
        Account domainAccount = domain.getResellerAccount();
        boolean isCreatorDirect = nicHandleSearchService.isNicHandleDirect(creator);
        if (!isCreatorDirect && creatorAccount.getId() != domainAccount.getId()) {
            throw new DomainNotAvailableForUserException(domain.getName(), creatorNH);
        }
    }

    private void validateNewBuyRequest(String domainHolder, OwnerType ownerType, EntityClass domainClass,
            EntityCategory domainCategory, long accountNumber, NewNicHandle newNicHandle, Country country,
            County county, PaymentMethod paymentMethod, CreditCard creditCard, OpInfo opInfo)
            throws InvalidCountyException, AccountNotFoundException, InvalidEmailException, AccountNotActiveException,
            EmptyRemarkException, ClassDoesNotMatchCategoryException, DomainHolderTooLongException,
            DomainHolderMandatoryException, CharityOwnerTypeNotAllowed {
        validateRemark(opInfo.getRemark());
        validateOwnerType(ownerType);
        validateBuyRequest(domainHolder, domainClass, domainCategory, accountNumber, newNicHandle, country, county);
        validatePaymentDetails(paymentMethod, creditCard);
    }

    private void validateBuyRequest(String domainHolder, EntityClass domainClass, EntityCategory domainCategory,
            long accountNumber, NewNicHandle newNicHandle, Country country, County county)
            throws InvalidCountyException, AccountNotFoundException, InvalidEmailException, AccountNotActiveException,
            ClassDoesNotMatchCategoryException, DomainHolderTooLongException, DomainHolderMandatoryException {
        validateHolder(domainHolder, domainClass, domainCategory);
        NicHandleValidator.validateNicHandle(newNicHandle.getName(), newNicHandle.getEmail(), newNicHandle.getAddress(),
                country, county, accountNumber, countryFactory, accountSearchService);
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    private void validateOwnerType(OwnerType ownerType) throws CharityOwnerTypeNotAllowed {
        if (ownerType.isCharity()) {
            throw new CharityOwnerTypeNotAllowed();
        }
    }

    private void validateHolder(String domainHolder, EntityClass domainClass, EntityCategory domainCategory)
            throws DomainHolderTooLongException, DomainHolderMandatoryException, ClassDoesNotMatchCategoryException {
        ValidationHelper.validateHolder(domainHolder);
        entityService.validateHolderEntities(domainClass, domainCategory);
    }

    private void validatePaymentDetails(PaymentMethod paymentMethod, CreditCard creditCard) {
        switch (paymentMethod) {
            case ADP:
                if (creditCard != null) {
                    throw new IllegalArgumentException("Unhandled payment method");
                }
                break;
            case CC:
                if (creditCard == null) {
                    throw new IllegalArgumentException("Unhandled payment method");
                }
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid payment method: %s", paymentMethod));
        }
    }

    private void sendBuyRequestRegistrationEmails(Domain domain, BuyRequest buyRequest, String username,
            OpInfo opInfo) {
        try {
            if (domain.getDsmState().getSecondaryMarketStatus() == SecondaryMarketStatus.NoProcess) {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.FIRST_BUY_REQUEST_REGISTERED.getId(),
                        new SecondaryMarketRequestEmailParameters(domain, buyRequest, opInfo.getActorName()));
            }
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(username);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REGISTERED.getId(),
                    new SecondaryMarketRequestEmailParameters(domain, buyRequest, nicHandle, opInfo.getActorName()));
        } catch (Exception e) {
            LOG.error("Failed to send buy request registration emails", e);
        }
    }

    @Override
    public long registerSellRequest(AuthenticatedUser user, String domainName, String authCode,
            PaymentMethod paymentMethod, CreditCard creditCard, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException, CardPaymentException,
            NotEnoughDepositFundsException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            InvalidAuthCodeException, DomainNotAvailableForUserException, DomainLockedException, NicHandleException,
            EmptyRemarkException, TooManyTicketsException, DomainTransferPendingException, SellRequestExistsException {
        Domain domain = domainService.lock(domainName);
        BuyRequest buyRequest = getBuyRequestByAuthCode(domainName, authCode);
        validateRequestAccount(buyRequest, domain);
        try {
            ticketSearchService.validateTicketPending(domainName);
        } catch (DomainModificationPendingException e) {
            ticketService.cancelModificationTicket(user, domainName, new OpInfo(user,
                    "Mod Ticket cancelled by a Registrant Transfer Sell Request"));
        }
        validateSellRequestExists(domainName);
        long sellRequestId = createSellRequestAndRunDsm(user, domainName, buyRequest, opInfo);
        paymentService.executePaymentForSecondaryMarketRequest(user, domainName, paymentMethod, creditCard,
                OperationType.SELL_REQUEST, sellRequestId, opInfo);
        sendSellRequestRegistrationEmail(user, domain, buyRequest);
        return sellRequestId;
    }

    @Override
    public void modifyBuyRequest(OpInfo opInfo, long buyRequestId, String domainHolder, NewNicHandle newNicHandle)
            throws BuyRequestNotFoundException, InvalidCountryException, InvalidCountyException,
            AccountNotFoundException, InvalidEmailException, DomainHolderTooLongException, AccountNotActiveException,
            DomainHolderMandatoryException, EmptyRemarkException, BuyRequestCheckedOutException,
            BuyRequestFrozenAsPassed {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestNotCheckedOut(buyRequest);
        try {
            doUpdate(opInfo, true, buyRequest, domainHolder, buyRequest.getHolderClass().getId(),
                    buyRequest.getHolderCategory().getId(), newNicHandle);
        } catch (HolderClassNotExistException | HolderCategoryNotExistException |
                ClassDoesNotMatchCategoryException e) {
            LOG.error("Class and category should always exist and match when modifying buy request as owner");
            throw new IllegalStateException();
        }
    }

    @Override
    public void modifyBuyRequestAsHostmaster(OpInfo opInfo, long buyRequestId, String domainHolder,
            long domainHolderClassId, long domainHolderCategoryId, NewNicHandle newNicHandle,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            InvalidCountryException, InvalidCountyException, AccountNotFoundException,
            ClassDoesNotMatchCategoryException,
            InvalidEmailException, DomainHolderTooLongException, AccountNotActiveException,
            DomainHolderMandatoryException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            EmptyRemarkException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestCheckedOutTo(opInfo, buyRequest);
        fillFailureReasons(buyRequest, domainNameFR, domainHolderFR, categoryFR, classFR, adminNameFR, adminEmailFR,
                adminCompanyNameFR, adminAddressFR, adminCountryFR, adminCountyFR, phonesFR, faxesFR);
        doUpdate(opInfo, false, buyRequest, domainHolder, domainHolderClassId, domainHolderCategoryId, newNicHandle);
    }

    @Override
    public void acceptBuyRequest(OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            DomainNotFoundException, EmailException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestCheckedOutTo(opInfo, buyRequest);
        validateBuyRequestNotPassed(buyRequest);
        cleanFailureReasons(buyRequest);
        setCheckinBuyRequest(opInfo.getRemark(), buyRequest, BuyRequestStatus.PASSED);
        generateAuthcodeAndUpdateBuyRequest(opInfo, buyRequest);
        sendBuyRequestAcceptedEmail(opInfo, buyRequest);
    }

    private void cleanFailureReasons(BuyRequest buyRequest) {
        fillFailureReasons(buyRequest, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public void saveBuyRequest(OpInfo opInfo, long buyRequestId, FailureReason domainNameFR,
            FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR,
            FailureReason adminEmailFR, FailureReason adminCompanyNameFR, FailureReason adminAddressFR,
            FailureReason adminCountryFR, FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            EmptyRemarkException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestCheckedOutTo(opInfo, buyRequest);
        validateBuyRequestNotPassed(buyRequest);
        validateRemark(opInfo.getRemark());
        fillFailureReasons(buyRequest, domainNameFR, domainHolderFR, categoryFR, classFR, adminNameFR, adminEmailFR,
                adminCompanyNameFR, adminAddressFR, adminCountryFR, adminCountyFR, phonesFR, faxesFR);
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    @Override
    public void updateStatus(OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus)
            throws BuyRequestNotFoundException, BuyRequestFrozenAsPassed, EmptyRemarkException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestNotPassed(buyRequest);
        validateRemark(opInfo.getRemark());
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        buyRequest.setStatus(newStatus);
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    @Override
    public void rejectBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId, BuyRequestStatus newStatus,
            FailureReason domainNameFR, FailureReason domainHolderFR, FailureReason categoryFR, FailureReason classFR,
            FailureReason adminNameFR, FailureReason adminEmailFR, FailureReason adminCompanyNameFR,
            FailureReason adminAddressFR, FailureReason adminCountryFR, FailureReason adminCountyFR,
            FailureReason phonesFR, FailureReason faxesFR)
            throws BuyRequestNotFoundException, BuyRequestNotCheckedOutToUserException, BuyRequestFrozenAsPassed,
            EmptyRemarkException, BadBuyRequestStatusException, DomainNotFoundException, NicHandleNotFoundException,
            EmailException, DomainIllegalStateException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestCheckedOutTo(opInfo, buyRequest);
        validateBuyRequestNotPassed(buyRequest);
        validateRemark(opInfo.getRemark());
        validateBuyRequestRejectNewStatus(buyRequest, newStatus);
        fillFailureReasons(buyRequest, domainNameFR, domainHolderFR, categoryFR, classFR, adminNameFR, adminEmailFR,
                adminCompanyNameFR, adminAddressFR, adminCountryFR, adminCountyFR, phonesFR, faxesFR);
        if (newStatus == BuyRequestStatus.CANCELLED) {
            cancelBuyRequestByHostmaster(user, opInfo, buyRequest);
        } else {
            setCheckinBuyRequest(opInfo.getRemark(), buyRequest, newStatus);
            updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
            EmailTemplateNamesEnum emailTemplate = EmailTemplateNamesEnum.BUY_REQUEST_QUERY;
            if (newStatus == BuyRequestStatus.HOLD_UPDATE) {
                emailTemplate = EmailTemplateNamesEnum.BUY_REQUEST_HOLD_UPDATE;
            }
            sendBuyRequestEmail(opInfo, buyRequest, emailTemplate);
        }
    }

    private void fillFailureReasons(BuyRequest buyRequest, FailureReason domainNameFR, FailureReason domainHolderFR,
            FailureReason categoryFR, FailureReason classFR, FailureReason adminNameFR, FailureReason adminEmailFR,
            FailureReason adminCompanyNameFR, FailureReason adminAddressFR, FailureReason adminCountryFR,
            FailureReason adminCountyFR, FailureReason phonesFR, FailureReason faxesFR) {
        buyRequest.setDomainNameFR(domainNameFR);
        buyRequest.setDomainHolderFR(domainHolderFR);
        buyRequest.setHolderCategoryFR(categoryFR);
        buyRequest.setHolderClassFR(classFR);
        buyRequest.setAdminNameFR(adminNameFR);
        buyRequest.setAdminEmailFR(adminEmailFR);
        buyRequest.setAdminCompanyNameFR(adminCompanyNameFR);
        buyRequest.setAdminAddressFR(adminAddressFR);
        buyRequest.setAdminCountryFR(adminCountryFR);
        buyRequest.setAdminCountyFR(adminCountyFR);
        buyRequest.setPhonesFR(phonesFR);
        buyRequest.setFaxesFR(faxesFR);
    }

    private void generateAuthcodeAndUpdateBuyRequest(OpInfo opInfo, BuyRequest buyRequest) {
        boolean retry = false;
        do {
            try {
                Date date = new Date();
                buyRequest.setAuthcode(generateBuyRequestAuthcode());
                buyRequest.setAuthcodeCreationDate(date);
                updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    LOG.info("Duplicate authcode, it's amazing! Retrying");
                    retry = true;
                } else {
                    throw e;
                }
            }
        } while (retry);
    }

    private String generateBuyRequestAuthcode() {
        return AUTHCODE_PREFIX + AuthcodeGenerator.generateValidated(AUTHCODE_LENGTH - AUTHCODE_PREFIX.length());
    }

    @Override
    public void cancelBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestFrozenAsPassed, DomainIllegalStateException,
            DomainNotFoundException, NicHandleNotFoundException, EmailException, BuyRequestCheckedOutException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        validateBuyRequestNotPassed(buyRequest);
        validateBuyRequestNotCheckedOut(buyRequest);
        deleteBuyRequestAndRunDsm(user, opInfo, buyRequest);
        sendBuyRequestEmail(opInfo, buyRequest, EmailTemplateNamesEnum.BUY_REQUEST_REMOVED);
    }

    private void cancelBuyRequestByHostmaster(AuthenticatedUser user, OpInfo opInfo, BuyRequest buyRequest)
            throws  DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException {
        buyRequest.setCheckedOutTo(null);
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        deleteBuyRequestAndRunDsm(user, opInfo, buyRequest);
        sendBuyRequestEmail(opInfo, buyRequest, EmailTemplateNamesEnum.BUY_REQUEST_REMOVED);
    }

    @Override
    public void invalidateBuyRequest(AuthenticatedUser user, OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BadBuyRequestStatusException, DomainNotFoundException,
            NicHandleNotFoundException, EmailException, BuyRequestUsedInSaleException, DomainIllegalStateException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        if (buyRequest.getAuthcode() == null) {
            throw new BadBuyRequestStatusException(buyRequest, BuyRequestStatus.CANCELLED);
        }
        validateBuyRequestNotUsedInSale(buyRequest);
        deleteBuyRequestAndRunDsm(user, opInfo, buyRequest);
        sendBuyRequestEmail(opInfo, buyRequest, EmailTemplateNamesEnum.BUY_REQUEST_INVALIDATED);
    }

    @Override
    public boolean isBuyRequestUsedInSale(long buyRequestId) {
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        criteria.setBuyRequestId(buyRequestId);
        return sellRequestDAO.exists(criteria);
    }

    @Override
    public boolean verifyAuthCode(String domainName, String authCode) {
        try {
            getBuyRequestByAuthCode(domainName, authCode);
        } catch (InvalidAuthCodeException e) {
            return false;
        }
        return true;
    }

    @Override
    public void regenerateAndResendAuthCode(OpInfo opInfo, long buyRequestId)
            throws BuyRequestNotFoundException, BuyRequestNotPassed, EmailException, DomainNotFoundException {
        BuyRequest buyRequest = getBuyRequest(buyRequestId);
        if (buyRequest.getAuthcode() == null) {
            throw new BuyRequestNotPassed();
        }
        buyRequest.setHostmasterRemark("Regenerated AuthCode");
        generateAuthcodeAndUpdateBuyRequest(opInfo, buyRequest);
        sendBuyRequestAcceptedEmail(opInfo, buyRequest);
    }

    private void doUpdate(OpInfo opInfo, boolean changeByClient, BuyRequest buyRequest, String domainHolder,
            long domainHolderClassId, long domainHolderCategoryId, NewNicHandle newNicHandle)
            throws HolderClassNotExistException, HolderCategoryNotExistException, InvalidCountryException,
            InvalidCountyException, AccountNotFoundException, InvalidEmailException, AccountNotActiveException,
            ClassDoesNotMatchCategoryException, DomainHolderTooLongException, DomainHolderMandatoryException,
            BuyRequestFrozenAsPassed, EmptyRemarkException {
        validateBuyRequestNotPassed(buyRequest);
        validateRemark(opInfo.getRemark());
        EntityCategory domainCategory = entityService.getCategory(domainHolderCategoryId);
        EntityClass domainClass = entityService.getClass(domainHolderClassId);
        Country country = countryFactory.getCountry(newNicHandle.getCountryId());
        County county = countryFactory.getCounty(newNicHandle.getCountyId());
        validateBuyRequest(domainHolder, domainClass, domainCategory, buyRequest.getAccount().getId(), newNicHandle,
                country, county);
        setBuyRequestWithValues(changeByClient, opInfo.getRemark(), domainHolder, newNicHandle, buyRequest, domainClass,
                domainCategory, country, county);
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
    }

    private void setBuyRequestWithValues(boolean changeByClient, String remark, String domainHolder,
            NewNicHandle newNicHandle, BuyRequest buyRequest, EntityClass domainClass, EntityCategory domainCategory,
            Country country, County county) {
        buyRequest.setDomainHolder(domainHolder);
        buyRequest.setHolderClass(domainClass);
        buyRequest.setHolderCategory(domainCategory);
        buyRequest.setAdminName(newNicHandle.getName());
        buyRequest.setAdminAddress(newNicHandle.getAddress());
        buyRequest.setAdminCompanyName(newNicHandle.getCompanyName());
        buyRequest.setAdminEmail(newNicHandle.getEmail());
        buyRequest.setAdminCountry(country);
        buyRequest.setAdminCounty(county);
        if (changeByClient) {
            buyRequest.setRemark(remark);
            buyRequest.setStatus(BuyRequestStatus.RENEW);
        } else {
            buyRequest.setHostmasterRemark(remark);
        }
        buyRequest.setPhones(newNicHandle.getPhones());
        buyRequest.setFaxes(newNicHandle.getFaxes());
    }

    private BuyRequest getBuyRequestByAuthCode(String domainName, String authCode) throws InvalidAuthCodeException {
        BuyRequest buyRequest = buyRequestDAO.getByAuthcode(authCode);
        if (buyRequest == null || !buyRequest.getDomainName().equals(domainName)) {
            throw new InvalidAuthCodeException(domainName);
        }
        return buyRequest;
    }

    private void deleteBuyRequest(BuyRequest buyRequest, OpInfo opInfo) {
        buyRequest.setAuthcode(null);
        buyRequest.setAuthcodeCreationDate(null);
        buyRequest.setStatus(BuyRequestStatus.CANCELLED);
        buyRequest.setHostmasterRemark(opInfo.getRemark());
        updateBuyRequestWithHistory(opInfo, buyRequest, new Date());
        buyRequestDAO.deleteById(buyRequest.getId());
    }

    private void deleteBuyRequestAndRunDsm(AuthenticatedUser user, OpInfo opInfo, BuyRequest buyRequest)
            throws DomainIllegalStateException, DomainNotFoundException {
        deleteBuyRequest(buyRequest, opInfo);
        if (!buyRequestExistsForDomain(buyRequest.getDomainName())) {
            domainStateMachine.handleEvent(user, buyRequest.getDomainName(), DsmEventName.CancelLastBuyRequest, opInfo);
        }
    }

    private void validateRequestAccount(BuyRequest buyRequest, Domain domain)
            throws DomainNotAvailableForUserException {
        if (buyRequest.getAccount().getId() != domain.getResellerAccount().getId()) {
            throw new DomainNotAvailableForUserException(domain.getName(), buyRequest.getCreatorNH());
        }
    }

    private void validateBuyRequestCheckedOutTo(OpInfo opInfo, BuyRequest buyRequest)
            throws BuyRequestNotCheckedOutToUserException {
        if (!buyRequest.isCheckedOut() || !buyRequest.getCheckedOutTo().equals(opInfo.getUserName())) {
            throw new BuyRequestNotCheckedOutToUserException(buyRequest, opInfo.getUserName());
        }
    }

    private void validateBuyRequestNotCheckedOut(BuyRequest buyRequest) throws BuyRequestCheckedOutException {
        if (buyRequest.isCheckedOut()) {
            throw new BuyRequestCheckedOutException();
        }
    }

    private void validateBuyRequestNotPassed(BuyRequest buyRequest) throws BuyRequestFrozenAsPassed {
        if (buyRequest.getAuthcode() != null) {
            throw new BuyRequestFrozenAsPassed(buyRequest);
        }
    }

    private void validateBuyRequestCheckinNewStatus(BuyRequest buyRequest, BuyRequestStatus newStatus)
            throws BadBuyRequestStatusException {
        if  (newStatus == null || FORBIDDEN_CHECKIN_STATUSES.contains(newStatus)) {
            throw new BadBuyRequestStatusException(buyRequest, newStatus);
        }
    }

    private void validateBuyRequestRejectNewStatus(BuyRequest buyRequest, BuyRequestStatus newStatus)
            throws BadBuyRequestStatusException {
        if  (newStatus == null || FORBIDDEN_REJECT_STATUSES.contains(newStatus)) {
            throw new BadBuyRequestStatusException(buyRequest, newStatus);
        }
    }

    private void validateSellRequestExists(String domainName) throws SellRequestExistsException {
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        criteria.setDomainName(domainName);
        if (sellRequestDAO.exists(criteria)) {
            throw new SellRequestExistsException(domainName);
        }
    }

    private void validateBuyRequestNotUsedInSale(BuyRequest buyRequest) throws BuyRequestUsedInSaleException {
        if (isBuyRequestUsedInSale(buyRequest.getId())) {
            throw new BuyRequestUsedInSaleException(buyRequest);
        }
    }

    private void updateSellRequestWithHistory(OpInfo opInfo, SellRequest sellRequest, Date changeDate) {
        long changeId = historicalSellRequestDAO.create(sellRequest, changeDate, opInfo.getActorName());
        sellRequestDAO.updateUsingHistory(changeId);
    }

    private long createSellRequestAndRunDsm(AuthenticatedUser user, String domainName, BuyRequest buyRequest,
            OpInfo opInfo)
                    throws DomainIllegalStateException, DomainNotFoundException {
        SellRequest sellRequest = new SellRequest(user.getUsername(), new Date(), buyRequest);
        sellRequestDAO.create(sellRequest);
        updateSellRequestWithHistory(opInfo, sellRequest, sellRequest.getCreationDate());
        domainStateMachine.handleEvent(user, domainName, DsmEventName.RegisterSellRequest, opInfo);
        return sellRequest.getId();
    }

    private boolean buyRequestExistsForDomain(String domainName) {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName(domainName);
        return buyRequestDAO.exists(criteria);
    }

    private void sendBuyRequestAcceptedEmail(OpInfo opInfo, BuyRequest buyRequest)
            throws EmailException, DomainNotFoundException {
        Domain domain = domainSearchService.getDomain(buyRequest.getDomainName());
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_ACCEPTED.getId(),
                new BuyRequestExpirationEmailParameters(domain, buyRequest, buyRequest.getAuthcodeCreationDate(),
                        applicationConfig.getSecondaryMarketAuthcodeExpirationPeriod(), opInfo.getActorName()));
    }

    private void sendSellRequestRegistrationEmail(AuthenticatedUser user, Domain domain, BuyRequest buyRequest) {
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_REGISTERED.getId(),
                    new SecondaryMarketRequestEmailParameters(domain, buyRequest,
                            applicationConfig.getSecondaryMarketCountdownPeriod(), user.getUsername()));
        } catch (Exception e) {
            LOG.error("Failed to send sell request registration email", e);
        }
    }

    private void sendSellRequestCancellationEmail(AuthenticatedUser user, Domain domain, BuyRequest buyRequest) {
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_CANCELLED.getId(),
                    new SecondaryMarketRequestEmailParameters(domain, buyRequest,
                            applicationConfig.getSecondaryMarketCountdownPeriod(), user.getUsername()));
        } catch (Exception e) {
            LOG.error("Failed to send sell request cancellation email", e);
        }
    }

    @Override
    public void cancelSellRequest(AuthenticatedUser user, long sellRequestId, OpInfo opInfo)
            throws SellRequestNotFoundException, DomainIllegalStateException, DomainNotFoundException {
        SellRequest sellRequest = lockSellRequest(sellRequestId);
        String domainName = sellRequest.getDomainName();
        sellRequest.setStatus(SellRequestStatus.CANCELLED);
        updateSellRequestWithHistory(opInfo, sellRequest, new Date());
        sellRequestDAO.deleteById(sellRequestId);
        DsmEvent event = new CancelSellRequest(sellRequest, applicationConfig.getSecondaryMarketCountdownPeriod());
        domainStateMachine.handleEvent(user, domainName, event, opInfo);
    }

    private BuyRequest lockBuyRequest(long buyRequestId) throws BuyRequestNotFoundException {
        if (!buyRequestDAO.lock(buyRequestId)) {
            throw new BuyRequestNotFoundException(buyRequestId);
        }
        return buyRequestDAO.get(buyRequestId);
    }

    private SellRequest lockSellRequest(long sellRequestId) throws SellRequestNotFoundException {
        if (!sellRequestDAO.lock(sellRequestId)) {
            throw new SellRequestNotFoundException(sellRequestId);
        }
        return sellRequestDAO.get(sellRequestId);
    }

    @Override
    public void completeSellRequest(AuthenticatedUser user, long sellRequestId, OpInfo opInfo)
            throws SellRequestNotFoundException, DomainNotFoundException, DomainNotAvailableForUserException,
            AccountNotFoundException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException, NicHandleException,
            EmailException, DomainIllegalStateException, DocumentGeneralException {
        Date completionDate = new Date();
        SellRequest sellRequest = lockSellRequest(sellRequestId);
        String domainName = sellRequest.getDomainName();
        Domain domain = domainSearchService.getDomain(domainName);
        validateRequestAccount(sellRequest.getBuyRequest(), domain);
        String nicHandle;
        // Completing directs' requests is considered to be a transfer, unlike completing registrar's requests,
        // for which billing contact remains unchanged
        if (nicHandleSearchService.isNicHandleDirect(sellRequest.getCreatorNH())) {
            nicHandle = createDirectNicHandle(sellRequest.getBuyRequest(), domain, opInfo);
            createTransferRecord(domain, nicHandle, completionDate);
            nicHandleService.triggerExport(nicHandle, opInfo);
        } else {
            nicHandle = createNicHandle(sellRequest.getBuyRequest(), opInfo);
        }
        moveDocumentsToDomain(sellRequest.getBuyRequest(), domain);
        cleanSellRequestAndRunDsm(user, sellRequest, nicHandle, completionDate, opInfo);
        sendSellRequestCompletionEmail(opInfo, domain, sellRequest.getBuyRequest());
    }

    private void moveDocumentsToDomain(BuyRequest buyRequest, Domain domain) throws DocumentGeneralException {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setBuyRequestId(buyRequest.getId());
        SearchResult<Document> documentsSearchResult = documentService.findDocuments(criteria, null);
        for (Document doc: documentsSearchResult.getResults()) {
            Set<String> newDomains = new TreeSet<>(doc.getDomains());
            newDomains.add(domain.getName());
            doc.setDomains(new ArrayList<>(newDomains));
            documentService.update(doc);
        }
    }

    private String createNicHandle(BuyRequest buyRequest, OpInfo opInfo)
            throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException,
            AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, InvalidEmailException {
        NewNicHandle newNicHandle = new NewNicHandle(buyRequest.getAdminName(), buyRequest.getAdminCompanyName(),
                buyRequest.getAdminEmail(), buyRequest.getAdminAddress(), buyRequest.getAdminCountry().getId(),
                buyRequest.getAdminCounty().getId(), buyRequest.getPhones(), buyRequest.getFaxes(), null, null);
        return nicHandleService.createNicHandle(buyRequest.getAccount().getId(), newNicHandle, opInfo, true)
                .getNicHandleId();
    }

    private String createDirectNicHandle(BuyRequest buyRequest, Domain domain, OpInfo opInfo)
            throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException,
            AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, InvalidEmailException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException {
        String password = PasswordHelper.generateNewPassword(16);
        String nicHandleId = nicHandleService.createDirectAccount(buyRequest.getAdminName(),
                buyRequest.getAdminCompanyName(), buyRequest.getAdminEmail(), buyRequest.getAdminAddress(),
                buyRequest.getAdminCountry().getId(), buyRequest.getAdminCounty().getId(), buyRequest.getPhones(),
                buyRequest.getFaxes(), null, opInfo, password, false, true).getNicHandleId();
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.SECONDARY_MARKET_NEW_DIRECT_PASSWORD.getId(),
                new ContactPasswordEmailParameters(nicHandleId, buyRequest.getAdminEmail(), password, domain,
                        opInfo.getActorName()));
        return nicHandleId;
    }

    private void createTransferRecord(Domain domain, String nicHandle, Date transferDate) {
        domainService.createTransferRecord(domain.getName(), transferDate, domain.getBillingContact().getNicHandle(),
                nicHandle);
    }

    private void cleanSellRequestAndRunDsm(AuthenticatedUser user, SellRequest sellRequest, String nicHandle,
            Date completionDate, OpInfo opInfo)
                    throws DomainIllegalStateException, DomainNotFoundException {
        deleteSellRequest(sellRequest, completionDate, opInfo);
        deleteBuyRequest(sellRequest.getBuyRequest(), opInfo);
        DsmEvent event = new CompleteSellRequest(sellRequest, nicHandle, completionDate);
        domainStateMachine.handleEvent(user, sellRequest.getDomainName(), event, opInfo);
    }

    private void deleteSellRequest(SellRequest sellRequest, Date completionDate, OpInfo opInfo) {
        sellRequest.setStatus(SellRequestStatus.COMPLETED);
        updateSellRequestWithHistory(opInfo, sellRequest, completionDate);
        sellRequestDAO.deleteById(sellRequest.getId());
    }

    private void sendSellRequestCompletionEmail(OpInfo opInfo, Domain domain, BuyRequest buyRequest) {
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_COMPLETED.getId(),
                    new SecondaryMarketRequestEmailParameters(domain, buyRequest, opInfo.getActorName()));
        } catch (Exception e) {
            LOG.error("Failed to send sell request registration emails", e);
        }
    }

    @Override
    public List<BuyRequestNotification> findBuyRequestNotifications(BuyRequestNotificationType notificationType) {
        List<BuyRequestNotification> notifications = new ArrayList<>();
        List<Integer> requestExpirationNotificationPeriods = getExpirationNotificationPeriods(notificationType);
        requestExpirationNotificationPeriods.add(0);
        Collections.sort(requestExpirationNotificationPeriods);
        int requestExpirationPeriod = getExpirationPeriod(notificationType);
        Date currentDate = new Date();
        for (int i = 1; i < requestExpirationNotificationPeriods.size(); i++) {
            int minDaysToExpiration = requestExpirationNotificationPeriods.get(i - 1) + 1;
            int maxDaysToExpiration = requestExpirationNotificationPeriods.get(i);
            notifications.addAll(getNotificationsForPeriod(notificationType, minDaysToExpiration, maxDaysToExpiration,
                    requestExpirationPeriod, currentDate));
        }
        return notifications;
    }

    private List<Integer> getExpirationNotificationPeriods(BuyRequestNotificationType notificationType) {
        switch (notificationType) {
            case REQUEST:
                return applicationConfig.getTicketExpirationNotificationPeriods();
            case AUTHCODE:
                return applicationConfig.getSecondaryMarketAuthcodeExpirationNotificationPeriods();
            default:
                throw new IllegalArgumentException((String.format("Invalid notification type: %s", notificationType)));
        }
    }

    private int getExpirationPeriod(BuyRequestNotificationType notificationType) {
        switch (notificationType) {
            case REQUEST:
                return applicationConfig.getTicketExpirationPeriod();
            case AUTHCODE:
                return applicationConfig.getSecondaryMarketAuthcodeExpirationPeriod();
            default:
                throw new IllegalArgumentException((String.format("Invalid notification type: %s", notificationType)));
        }
    }

    private EmailTemplateNamesEnum getNotificationTemplate(BuyRequestNotificationType notificationType) {
        switch (notificationType) {
            case REQUEST:
                return EmailTemplateNamesEnum.BUY_REQUEST_EXPIRATION;
            case AUTHCODE:
                return EmailTemplateNamesEnum.BUY_REQUEST_AUTHCODE_EXPIRATION;
            default:
                throw new IllegalArgumentException((String.format("Invalid notification type: %s", notificationType)));
        }
    }

    private Date getNotificationStartDate(BuyRequestNotificationType notificationType, BuyRequest buyRequest) {
        switch (notificationType) {
            case REQUEST:
                return buyRequest.getCreationDate();
            case AUTHCODE:
                return buyRequest.getAuthcodeCreationDate();
            default:
                throw new IllegalArgumentException((String.format("Invalid notification type: %s", notificationType)));
        }
    }

    @Override
    public void sendBuyRequestNotification(OpInfo opInfo, BuyRequestNotification notification)
            throws DomainNotFoundException, NicHandleNotFoundException, EmailException {
        boolean notificationNotSent = !buyRequestNotificationDAO.notificationSent(notification.getBuyRequest().getId(),
                notification.getNotificationPeriod(), notification.getNotificationType());
        if (notificationNotSent) {
            EmailTemplateNamesEnum template = getNotificationTemplate(notification.getNotificationType());
            int requestExpirationPeriod = getExpirationPeriod(notification.getNotificationType());
            BuyRequest buyRequest = notification.getBuyRequest();
            Domain domain = domainSearchService.getDomain(buyRequest.getDomainName());
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(buyRequest.getCreatorNH());
            EmailParameters parameters = new BuyRequestExpirationEmailParameters(domain, buyRequest, nicHandle,
                    getNotificationStartDate(notification.getNotificationType(), buyRequest), requestExpirationPeriod,
                    opInfo.getActorName());
            buyRequestNotificationDAO.create(notification);
            emailTemplateSender.sendEmail(template.getId(), parameters);
        }
    }

    @Override
    public List<Long> findBuyRequestsWithCompletedSales() {
        int period = applicationConfig.getSecondaryMarketAfterSalePeriod();
        Date dateTo = DateUtils.addDays(new Date(), -period);
        return buyRequestDAO.findCompletedSales(dateTo);
    }

    @Override
    public void deleteBuyRequestWithCompletedSale(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws BuyRequestNotFoundException, DomainNotFoundException, NicHandleNotFoundException, EmailException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        deleteBuyRequest(buyRequest, opInfo);
        sendBuyRequestEmail(opInfo, buyRequest, EmailTemplateNamesEnum.BUY_REQUEST_REMOVED_ANOTHER_SALE_COMPLETED);
    }

    @Override
    public void deleteBuyRequestWithExpiredAuthCode(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws BuyRequestNotFoundException, DomainIllegalStateException, DomainNotFoundException,
            EmailException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        deleteBuyRequestAndRunDsm(user, opInfo, buyRequest);
    }

    private List<BuyRequestNotification> getNotificationsForPeriod(BuyRequestNotificationType notificationType,
            int minDaysToExpiration, int maxDaysToExpiration, int requestExpirationPeriod, Date currentDate) {
        BuyRequestSearchCriteria criteria = getExpiringBuyRequestsCriteria(notificationType, minDaysToExpiration,
                maxDaysToExpiration, requestExpirationPeriod, currentDate);
        List<BuyRequest> expiringBuyRequests = buyRequestDAO.find(criteria, null).getResults();
        List<BuyRequestNotification> notifications = new ArrayList<>();
        for (BuyRequest buyRequest : expiringBuyRequests) {
            notifications.add(new BuyRequestNotification(buyRequest, maxDaysToExpiration, notificationType));
        }
        return notifications;
    }

    private BuyRequestSearchCriteria getExpiringBuyRequestsCriteria(BuyRequestNotificationType notificationType,
            int minDaysToExpiration, int maxDaysToExpiration, int requestExpirationPeriod, Date currentDate) {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        Date createdFrom = DateUtils.addDays(currentDate, minDaysToExpiration - requestExpirationPeriod);
        Date createdTo = DateUtils.addDays(currentDate, maxDaysToExpiration - requestExpirationPeriod);
        switch (notificationType) {
            case REQUEST:
                criteria.setCreationDateFrom(createdFrom);
                criteria.setCreationDateTo(createdTo);
                criteria.setStatuses(BuyRequestStatus.valuesExcept(BuyRequestStatus.PASSED,
                        BuyRequestStatus.CANCELLED));
                break;
            case AUTHCODE:
                criteria.setAuthCodeCreationDateFrom(createdFrom);
                criteria.setAuthCodeCreationDateTo(createdTo);
                break;
            default:
                throw new IllegalArgumentException((String.format("Invalid notification type: %s", notificationType)));
        }
        return criteria;
    }

    @Override
    public void cleanupBuyRequest(AuthenticatedUser user, long buyRequestId, OpInfo opInfo)
            throws DomainIllegalStateException, DomainNotFoundException, NicHandleNotFoundException, EmailException,
            BuyRequestNotFoundException {
        BuyRequest buyRequest = lockBuyRequest(buyRequestId);
        deleteBuyRequestAndRunDsm(user, opInfo, buyRequest);
        sendBuyRequestEmail(opInfo, buyRequest, EmailTemplateNamesEnum.BUY_REQUEST_EXPIRED);
    }

    @Override
    public void deleteAllRequests(AuthenticatedUser user, Domain domain, OpInfo opInfo) {
        SellRequestSearchCriteria sellRequestCriteria = new SellRequestSearchCriteria();
        sellRequestCriteria.setDomainName(domain.getName());
        for (SellRequest sellRequest : sellRequestDAO.find(sellRequestCriteria).getResults()) {
            BuyRequest buyRequest = sellRequest.getBuyRequest();
            deleteSellRequest(sellRequest, new Date(), opInfo);
            deleteBuyRequest(buyRequest, opInfo);
            sendSellRequestCancellationEmail(user, domain, buyRequest);
        }
        BuyRequestSearchCriteria buyRequestCriteria = new BuyRequestSearchCriteria();
        buyRequestCriteria.setDomainName(domain.getName());
        for (BuyRequest buyRequest : buyRequestDAO.find(buyRequestCriteria).getResults()) {
            EmailTemplateNamesEnum template = (buyRequest.getAuthcode() == null)
                    ? EmailTemplateNamesEnum.BUY_REQUEST_REMOVED : EmailTemplateNamesEnum.BUY_REQUEST_INVALIDATED;
            deleteBuyRequest(buyRequest, opInfo);
            try {
                sendBuyRequestEmail(opInfo, buyRequest, template);
            } catch (Exception e) {
                LOG.error(String.format("Failed to send a cancellation email for buy request: %s",
                        buyRequest.getId()), e);
            }
        }
    }

    private void sendBuyRequestEmail(OpInfo opInfo, BuyRequest buyRequest, EmailTemplateNamesEnum template)
            throws DomainNotFoundException, NicHandleNotFoundException, EmailException {
        Domain domain = domainSearchService.getDomain(buyRequest.getDomainName());
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(buyRequest.getCreatorNH());
        emailTemplateSender.sendEmail(template.getId(), new SecondaryMarketRequestEmailParameters(domain, buyRequest,
                nicHandle, opInfo.getActorName()));
    }

}
