package pl.nask.crs.payment.service.impl;

import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailException;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.*;
import pl.nask.crs.domains.dsm.events.PaymentSettledEvent;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.email.InvoiceEmailParameters;
import pl.nask.crs.payment.email.PaymentEmailParameters;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.secondarymarket.emails.SecondaryMarketPaymentEmailParameters;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.ProductPriceWithVat;

public class PaymentServiceImpl implements PaymentService {

    private static Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

    private final static String CURRENCY = "EUR";

    private final ReservationDAO reservationDAO;
    private final TransactionDAO transactionDAO;

    private final DepositService depositService;
    private final CardPaymentService cardService;

    private final DomainSearchService domainSearchService;
    private final DomainService domainService;
    private final TicketSearchService ticketSearchService;
    private final NicHandleSearchService nicHandleSearchService;
    private final PaymentSearchService paymentSearchService;

    private final DomainStateMachine dsm;
    private final ApplicationConfig applicationConfig;
    private final EmailTemplateSender emailTemplateSender;

    public PaymentServiceImpl(ReservationDAO reservationDAO, TransactionDAO transactionDAO,
            DepositService depositService, CardPaymentService cardService, DomainSearchService domainSearchService,
            DomainService domainService, TicketSearchService ticketSearchService,
            NicHandleSearchService nicHandleSearchService, PaymentSearchService paymentSearchService,
            DomainStateMachine dsm, ApplicationConfig applicationConfig, EmailTemplateSender emailTemplateSender) {
        Validator.assertNotNull(reservationDAO, "reservation DAO");
        Validator.assertNotNull(transactionDAO, "transactionDAO");
        Validator.assertNotNull(depositService, "depositService");
        Validator.assertNotNull(cardService, "cardService");
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(domainService, "domain service");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(nicHandleSearchService, "nicHandle search service");
        Validator.assertNotNull(paymentSearchService, "paymentSearchService");
        Validator.assertNotNull(dsm, "domainStateMachine");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        this.reservationDAO = reservationDAO;
        this.transactionDAO = transactionDAO;
        this.depositService = depositService;
        this.cardService = cardService;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.ticketSearchService = ticketSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.paymentSearchService = paymentSearchService;
        this.dsm = dsm;
        this.applicationConfig = applicationConfig;
        this.emailTemplateSender = emailTemplateSender;
    }

    @Override
    public Reservation lockForUpdate(long id) throws ReservationNotFoundException {
        if (reservationDAO.lock(id)) {
            return reservationDAO.get(id);
        } else {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationDAO.update(reservation);
    }

    @Override
    public long authorizeADPPaymentForTicket(final String nicHandleId, String domainName, final Period period,
            final OperationType operationType, final Long ticketId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        final ProductPriceWithVat productPriceWithVat =
                paymentSearchService.getProductPriceWithVat(period, operationType, nicHandleId);
        Payment totalPayment = new Payment(productPriceWithVat);
        ReservationObjectFactory reservationFactory = new ReservationObjectFactory() {
            @Override
            public Reservation getReservation(String domainName, Long transactionId) {
                return Reservation.newInstanceForTicket(nicHandleId, domainName, period.getMonths(),
                        productPriceWithVat, ticketId, transactionId, operationType, PaymentMethod.ADP);
            }
        };
        TransactionWithReservationsSummary summary = createADPTransactionAndReservations(Arrays.asList(domainName),
                totalPayment, reservationFactory, false, false);
        return summary.getReservationId(domainName);
    }

    @Override
    public void authorizeCCPaymentForTicket(AuthenticatedUser user, final String nicHandleId, String domainName,
            String domainHolder, final Period period, final OperationType operationType, CreditCard creditCard,
            final Long ticketId)
            throws NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException {
        final ProductPriceWithVat productPriceWithVat =
                paymentSearchService.getProductPriceWithVat(period, operationType, nicHandleId);
        Payment totalPayment = new Payment(productPriceWithVat);

        ReservationObjectFactory reservationFactory = new ReservationObjectFactory() {
            @Override
            public Reservation getReservation(String domainName, Long transactionId) {
                return Reservation.newInstanceForTicket(nicHandleId, domainName, period.getMonths(),
                        productPriceWithVat, ticketId, transactionId, operationType, PaymentMethod.CC);
            }
        };

        TransactionWithReservationsSummary summary = authorizeCCPaymentForDomains(creditCard, Arrays.asList(domainName),
                totalPayment, reservationFactory, false, false);
        try {
            sendTicketPreAuthorisationEmail(user, nicHandleId, domainName, domainHolder, period.getYears(),
                    productPriceWithVat.getTotal(), summary.getOrderId(), operationType);
        } catch (Exception e) {
            handleExceptionAfterRealexAuthorization(summary, e);
            throw e;
        }
    }

    @Override
    public PaymentSummary authorizePaymentForRenewal(AuthenticatedUser user, OpInfo opInfo,
            Map<String, Period> domainsWithPeriods, PaymentMethod paymentMethod, CreditCard creditCard,
            boolean allowRenewalReservations)
            throws DomainNotFoundException, NicHandleNotFoundException, DomainIncorrectStateForPaymentException,
            DuplicatedDomainException, NotAdmissiblePeriodException, CardPaymentException, DomainIllegalStateException,
            ReservationPendingException, NotEnoughDepositFundsException {
        Map<String, Domain> domainMap = loadDomainsForPayment(domainsWithPeriods.keySet());
        String nicHandleId = opInfo.getUserName();
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        validateAndLockDomainsForRenewal(domainMap.values(), allowRenewalReservations);
        Map<String, ProductPriceWithVat> pricePerDomain = prepareRenewalPricePerDomain(nicHandleId, domainsWithPeriods);
        Payment totalPayment = new Payment(new ArrayList<>(pricePerDomain.values()));

        TransactionWithReservationsSummary summary;
        switch (paymentMethod) {
            case ADP:
                summary = authorizeADPPaymentForRenewal(nicHandleId, totalPayment, pricePerDomain);
                break;
            case CC:
                summary = authorizeCCPaymentForRenewal(nicHandleId, totalPayment, pricePerDomain, creditCard);
                break;
            default:
                throw new IllegalArgumentException("payment method not allowed");
        }

        try {
            TransactionDetails transactionDetails =
                    new TransactionDetails(pricePerDomain, OperationType.RENEWAL, domainMap);
            runPaymentInitEvent(user, domainsWithPeriods.keySet(), opInfo, paymentMethod, transactionDetails,
                    totalPayment, summary.getOrderId());
            sendRenewalPreAuthorizationEmail(user, nicHandleId, transactionDetails, totalPayment, summary.getOrderId(),
                    paymentMethod);
            return new PaymentSummary(pricePerDomain, totalPayment, summary.getOrderId(), domainMap);
        } catch (Exception e) {
            if (paymentMethod == PaymentMethod.CC) {
                handleExceptionAfterRealexAuthorization(summary, e);
            }
            throw e;
        }
    }

    @Override
    public PaymentSummary autorenew(AuthenticatedUser user, String domainName)
            throws NicHandleNotFoundException, DomainNotFoundException, DomainIncorrectStateForPaymentException,
            NotAdmissiblePeriodException, NotEnoughDepositFundsException, DomainIllegalStateException,
            ReservationPendingException {
        try {
            Domain domain = domainSearchService.getDomain(domainName);
            RenewalMode rm = domain.getDsmState().getRenewalMode();

            if (rm == RenewalMode.Autorenew || rm == RenewalMode.RenewOnce) {
                String billingNhId = domain.getResellerAccount().getBillingContact().getNicHandle();
                NicHandle billingNh = nicHandleSearchService.getNicHandle(billingNhId);
                verifyReservationNotRenewal(billingNhId, domainName);
                OpInfo opInfo = new OpInfo(billingNh);
                Map<String, Period> domainsWithPeriods = Collections.singletonMap(domainName, Period.fromYears(1));
                return authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, false);
            } else {
                LOG.debug("Domain " + domainName + " in incorrect DSM state: " + domain.getDsmState().getId());
                throw new DomainIncorrectStateForPaymentException(domainName,
                        domain.getDsmState().getNrpStatus().shortDescription());
            }
        } catch (DuplicatedDomainException | CardPaymentException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void executePaymentForSecondaryMarketRequest(AuthenticatedUser user, String domainName,
            PaymentMethod paymentMethod, CreditCard creditCard, OperationType operationType, long requestId,
            OpInfo opInfo)
            throws CardPaymentException, NicHandleNotFoundException, NotEnoughDepositFundsException,
            DomainNotFoundException, TransactionInvalidStateForSettlement, TransactionNotFoundException,
            DomainIllegalStateException {
        TransactionWithReservationsSummary summary = authorizePaymentForSecondaryMarketRequest(domainName,
                opInfo.getUserName(), paymentMethod, creditCard, operationType);
        try {
            Transaction transaction = lockTransaction(summary.getTransactionId());
            finalizePayment(transaction);
            settleReservations(user, opInfo, transaction.getReservations());
            sendSecondaryMarketRequestPaymentEmail(domainName, paymentMethod, summary, operationType, requestId,
                    opInfo);
        } catch (Exception e) {
            if (paymentMethod == PaymentMethod.CC) {
                handleExceptionAfterRealexAuthorization(summary, e);
            }
            throw e;
        }
    }

    private TransactionWithReservationsSummary authorizeCCPaymentForRenewal(final String nicHandleId,
            Payment totalPayment, final Map<String, ProductPriceWithVat> pricePerDomain, CreditCard creditCard)
            throws CardPaymentException {
        ReservationObjectFactory reservationFactory = new ReservationObjectFactory() {
            @Override
            public Reservation getReservation(String domainName, Long transactionId) {
                ProductPriceWithVat productPriceWithVat = pricePerDomain.get(domainName);
                Period period = productPriceWithVat.getPeriod();
                return Reservation.newInstanceForRenewal(nicHandleId, domainName, period.getMonths(),
                        productPriceWithVat, transactionId, PaymentMethod.CC);
            }
        };
        return authorizeCCPaymentForDomains(creditCard, new ArrayList<>(pricePerDomain.keySet()), totalPayment,
                reservationFactory, true, false);
    }

    private TransactionWithReservationsSummary authorizeADPPaymentForRenewal(final String nicHandleId,
            Payment totalPayment, final Map<String, ProductPriceWithVat> pricePerDomain)
            throws NotEnoughDepositFundsException {
        checkRegistrarHasSufficientFunds(nicHandleId, totalPayment.getTotal());
        ReservationObjectFactory reservationFactory = new ReservationObjectFactory() {
            @Override
            public Reservation getReservation(String domainName, Long transactionId) {
                ProductPriceWithVat productPriceWithVat = pricePerDomain.get(domainName);
                Period period = productPriceWithVat.getPeriod();
                return Reservation.newInstanceForRenewal(nicHandleId, domainName, period.getMonths(),
                        productPriceWithVat, transactionId, PaymentMethod.ADP);
            }
        };
        return createADPTransactionAndReservations(new ArrayList<>(pricePerDomain.keySet()), totalPayment,
                reservationFactory, true, false);
    }

    private TransactionWithReservationsSummary authorizePaymentForSecondaryMarketRequest(final String domainName,
            final String creatorNH, final PaymentMethod paymentMethod, CreditCard creditCard,
            final OperationType operationType)
            throws NicHandleNotFoundException, CardPaymentException, NotEnoughDepositFundsException {
        final PriceWithVat priceWithVat = paymentSearchService.getRequestPriceWithVat(operationType, creatorNH);
        Payment totalPayment = new Payment(priceWithVat);
        ReservationObjectFactory reservationFactory = new ReservationObjectFactory() {
            @Override
            public Reservation getReservation(String domainName, Long transactionId) {
                return Reservation.newInstanceForSecondaryMarketRequest(creatorNH, domainName, priceWithVat,
                        transactionId, operationType, paymentMethod);
            }
        };
        TransactionWithReservationsSummary summary;
        switch (paymentMethod) {
            case ADP:
                checkRegistrarHasSufficientFunds(creatorNH, totalPayment.getTotal());
                summary = createADPTransactionAndReservations(Arrays.asList(domainName), totalPayment,
                        reservationFactory, true, true);
                break;
            case CC:
                summary = authorizeCCPaymentForDomains(creditCard, Arrays.asList(domainName), totalPayment,
                        reservationFactory, true, true);
                break;
            default:
                throw new IllegalArgumentException("payment method not allowed");
        }
        return summary;
    }

    private TransactionWithReservationsSummary authorizeCCPaymentForDomains(CreditCard creditCard, List<String> domains,
            Payment totalPayment, ReservationObjectFactory reservationFactory, boolean setTransactionFinanciallyPassed,
            boolean setTransactionSettled)
            throws CardPaymentException {
        PaymentRequest paymentRequest = PaymentRequest
                .createPaymentRequestWithStandardCurrencyUnit(CURRENCY, totalPayment.getTotal(), creditCard);
        RealexTransactionIdentifier transactionIdentifier = cardService.authorisePaymentTransaction(paymentRequest);

        String realexAuthcode = transactionIdentifier.getAuthcode();
        String realexPasref = transactionIdentifier.getPasref();
        String orderId = transactionIdentifier.getOrderId();
        try {
            return createCCTransactionAndReservations(domains, totalPayment, realexAuthcode, realexPasref, orderId,
                    reservationFactory, setTransactionFinanciallyPassed, setTransactionSettled);
        } catch (Exception e) {
            handleExceptionAfterRealexAuthorization(realexAuthcode, realexPasref, orderId, e);
            throw e;
        }
    }

    private void handleExceptionAfterRealexAuthorization(String realexAuthcode, String realexPasref, String orderId,
            Exception e) {
        LOG.info("An error occured after performing a Realex authorization. Trying to cancel it.", e);
        try {
            cardService.cancelRealexAuthorisation(
                    new RealexTransactionIdentifier(realexAuthcode, orderId, realexPasref));
        } catch (Exception e1) {
            String message = String.format("Failed to cancel Realex authorization. Transaction details:\n" +
                    "Order ID: %s\nAuthcode: %s\nPasref: %s", orderId, realexAuthcode, realexPasref);
            LOG.error(message, e1);
        }
    }

    private void handleExceptionAfterRealexAuthorization(TransactionWithReservationsSummary summary, Exception e) {
        handleExceptionAfterRealexAuthorization(summary.getRealexAuthcode(), summary.getRealexPasref(),
                summary.getOrderId(), e);
    }

    private Map<String, Domain> loadDomainsForPayment(Set<String> domainNames)
            throws DomainNotFoundException {
        Map<String, Domain> res = new HashMap<>();
        for (String domainName : domainNames) {
            res.put(domainName, domainSearchService.getDomain(domainName));
        }
        return res;
    }

    private void validateAndLockDomainsForRenewal(Collection<Domain> domains, boolean allowRenewalReservations)
            throws DomainNotFoundException, DuplicatedDomainException, DomainIncorrectStateForPaymentException,
            ReservationPendingException {
        if (domains == null || domains.size() == 0) {
            throw new IllegalArgumentException("Domains list cannot be empty");
        }
        Domain duplicatedDomain = Validator.getDuplicates(domains);
        if (duplicatedDomain != null) {
            throw new DuplicatedDomainException(duplicatedDomain.getName());
        }
        for (Domain domain : domains) {
            checkDomainStateForRenewal(domain.getName());
            checkPendingReservations(domain, allowRenewalReservations);
        }
    }

    private void checkDomainStateForRenewal(String domainName)
            throws DomainIncorrectStateForPaymentException, DomainNotFoundException {
        if (!dsm.validateEventAndLockDomain(domainName, DsmEventName.PaymentInitiated)) {
            throw new DomainIncorrectStateForPaymentException(domainName, domainSearchService.getDomain(domainName)
                    .getDsmState().getNrpStatus().shortDescription());
        }
    }

    private void checkPendingReservations(Domain domain, boolean allowRenewalReservations)
            throws ReservationPendingException {
        if (allowRenewalReservations) {
            // NOTE payment pending in extendedDomain (which is used is the other branch)
            // takes into account only reservations that are ready for settlement. This branch
            // checks all reservations. It's possible that checking only ready for settlement ones
            // is an issue, to be confirmed with iedr.
            ReservationSearchCriteria criteria = new ReservationSearchCriteria();
            criteria.setDomainName(domain.getName());
            List<Reservation> reservationsForDomain = reservationDAO.getAllReservations(criteria);
            for (Reservation r : reservationsForDomain) {
                if (r.getOperationType() != OperationType.RENEWAL) {
                    throw new ReservationPendingException(domain.getName(),
                            "Pending reservation of type " + r.getOperationType().getTypeName());
                }
            }
        } else {
            domainService.assertNoPendingReservations(domain.getName(), "Pending reservarions");
        }
    }

    private void checkRegistrarHasSufficientFunds(String billingNhId, BigDecimal amountWithVat)
            throws NotEnoughDepositFundsException {
        boolean insufficientFunds;
        try {
            ExtendedDeposit deposit = depositService.viewDeposit(billingNhId);
            BigDecimal closeIncludingReservations = deposit.getCloseBalMinusReservations();
            insufficientFunds = closeIncludingReservations.compareTo(amountWithVat) == -1;
        } catch (DepositNotFoundException e) {
            insufficientFunds = true;
        }
        if (insufficientFunds) {
            throw new NotEnoughDepositFundsException();
        }
    }

    private void verifyReservationNotRenewal(String billingNhId, String domainName)
            throws DomainIncorrectStateForPaymentException {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH(billingNhId);
        criteria.setDomainName(domainName);
        criteria.setOperationType(OperationType.RENEWAL);
        if (reservationDAO.exists(criteria)) {
            LOG.debug("Domain " + domainName + " has a readyForSettlement reservation for a RENEWAL operation");
            throw new DomainIncorrectStateForPaymentException(domainName, "Pending reservation");
        }
    }

    private Map<String, ProductPriceWithVat> prepareRenewalPricePerDomain(String nicHandleId,
            Map<String, Period> domainsWithPeriods)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        Map<String, ProductPriceWithVat> pricePerDomain = new HashMap<>();
        for (Map.Entry<String, Period> entry : domainsWithPeriods.entrySet()) {
            String domainName = entry.getKey();
            Period period = entry.getValue();
            ProductPriceWithVat productPrice =
                    paymentSearchService.getProductPriceWithVat(period, OperationType.RENEWAL, nicHandleId);
            pricePerDomain.put(domainName, productPrice);
        }
        return pricePerDomain;
    }

    private void runPaymentInitEvent(AuthenticatedUser user, Set<String> domainNames, OpInfo opInfo,
            PaymentMethod payMethod, TransactionDetails transactionDetails, Payment transactionValue, String orderId)
            throws DomainNotFoundException, DomainIllegalStateException {
        DsmEvent event = new PaymentInitiated(payMethod, transactionDetails, transactionValue.getTotal(), orderId);
        for (String domainName : domainNames) {
            dsm.handleEvent(user, domainName, event, opInfo);
        }
    }

    private TransactionWithReservationsSummary createADPTransactionAndReservations(List<String> domains,
            Payment totalPayment, ReservationObjectFactory reservationFactory,
            boolean setTransactionFinanciallyPassed, boolean setTransactionSettled) {
        String orderId = DepositServiceImpl.generateADPOrderId();
        long transactionId = createADPTransaction(totalPayment.getTotal(), totalPayment.getFee(), totalPayment.getVat(),
                orderId, setTransactionSettled);
        Map<String, Long> domainReservations = createReservationsAndFinanciallyPassTransaction(transactionId, domains,
                reservationFactory, setTransactionFinanciallyPassed);
        return new TransactionWithReservationsSummary(transactionId, orderId, totalPayment, domainReservations, null,
                null);
    }

    private long createADPTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId,
            boolean settled) {
        Transaction transaction = Transaction.newInstance(total, net, vat, orderId, null, null, settled);
        return transactionDAO.createTransaction(transaction);
    }

    private TransactionWithReservationsSummary createCCTransactionAndReservations(List<String> domains,
            Payment totalPayment, String realexAuthcode, String realexPasref, String orderId,
            ReservationObjectFactory reservationFactory, boolean setTransactionFinanciallyPassed,
            boolean setTransactionSettled) {
        long transactionId = createCCTransaction(totalPayment.getTotal(), totalPayment.getFee(), totalPayment.getVat(),
                orderId, realexAuthcode, realexPasref, setTransactionSettled);
        Map<String, Long> domainReservations = createReservationsAndFinanciallyPassTransaction(transactionId, domains,
                reservationFactory, setTransactionFinanciallyPassed);
        return new TransactionWithReservationsSummary(transactionId, orderId, totalPayment, domainReservations,
                realexAuthcode, realexPasref);
    }

    private long createCCTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId,
            String realexAuthcode, String realexPasref, boolean settled) {
        Transaction transaction = Transaction.newInstance(total, net, vat, orderId, realexAuthcode, realexPasref,
                settled);
        return transactionDAO.createTransaction(transaction);
    }

    private Map<String, Long> createReservationsAndFinanciallyPassTransaction(long transactionId, List<String> domains,
            ReservationObjectFactory reservationFactory, boolean setTransactionFinanciallyPassed) {
        Map<String, Long> domainReservations = new HashMap<>();
        for (String domainName : domains) {
            Reservation reservation = reservationFactory.getReservation(domainName, transactionId);
            long reservationId = reservationDAO.createReservation(reservation);
            domainReservations.put(domainName, reservationId);
        }
        if (setTransactionFinanciallyPassed) {
            try {
                setTransactionFinanciallyPassed(transactionId);
            } catch (TransactionNotFoundException e) {
                throw new IllegalStateException("Transaction not found", e);
            }
        }
        return domainReservations;
    }

    private abstract class ReservationObjectFactory {

        public abstract Reservation getReservation(String domainName, Long transactionId);
    }

    private void sendTicketPreAuthorisationEmail(AuthenticatedUser user, String nicHandleId, String domainName,
            String domainHolder, int years, BigDecimal transactionValue, String orderId, OperationType operationType) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
            TransactionDetails details = new TransactionDetails(domainName, domainHolder, years, operationType,
                    transactionValue);
            PaymentEmailParameters parameters = new PaymentEmailParameters(user.getUsername(), orderId,
                    transactionValue, nicHandle, domainName, details);
            switch (operationType) {
                case REGISTRATION:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_NREG_PRE_AUTH.getId(), parameters);
                    break;
                case TRANSFER:
                    if (nicHandleSearchService.isNicHandleDirect(nicHandle)) {
                        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_XFER_PRE_AUTH_DIRECT.getId(),
                                parameters);
                    } else {
                        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CC_XFER_PRE_AUTH_REGISTRAR.getId(), parameters);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type: " + operationType);
            }
        } catch (Exception e) {
            LOG.error("Cannot send registration cc pre auth mail", e);
        }
    }

    private void sendRenewalPreAuthorizationEmail(AuthenticatedUser user, String nicHandleId,
            TransactionDetails transactionDetails, Payment totalPayment, String orderId, PaymentMethod paymentMethod) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
            PaymentEmailParameters parameters = new PaymentEmailParameters(user.getUsername(), orderId,
                    totalPayment.getTotal(), nicHandle, null, transactionDetails);
            switch (paymentMethod) {
                case ADP:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INIT_RENEWAL_ADP.getId(), parameters);
                    break;
                case CC:
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INIT_RENEWAL_CC.getId(), parameters);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
            }
        } catch (Exception e) {
            LOG.error("Cannot send renewal payment initialization email", e);
        }
    }

    private void sendSecondaryMarketRequestPaymentEmail(String domainName, PaymentMethod paymentMethod,
            TransactionWithReservationsSummary summary, OperationType operationType, long requestId, OpInfo opInfo) {
        Payment totalPayment = summary.getTotalPayment();
        String creatorNH = opInfo.getUserName();
        try {
            NicHandle creator = nicHandleSearchService.getNicHandle(creatorNH);
            Domain domain = domainSearchService.getDomain(domainName);
            SecondaryMarketPaymentEmailParameters parameters =
                    new SecondaryMarketPaymentEmailParameters(opInfo.getActorName(),
                            domain, requestId, totalPayment.getTotal(), creator);
            int templateId;
            switch (operationType) {
                case BUY_REQUEST:
                    switch (paymentMethod) {
                        case ADP:
                            templateId = EmailTemplateNamesEnum.BUY_REQUEST_ADP_PAYMENT.getId();
                            break;
                        case CC:
                            templateId = EmailTemplateNamesEnum.BUY_REQUEST_CC_PAYMENT.getId();
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
                    }
                    break;
                case SELL_REQUEST:
                    switch (paymentMethod) {
                        case ADP:
                            templateId = EmailTemplateNamesEnum.SELL_REQUEST_ADP_PAYMENT.getId();
                            break;
                        case CC:
                            templateId = EmailTemplateNamesEnum.SELL_REQUEST_CC_PAYMENT.getId();
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid request type: " + operationType);
            }
            emailTemplateSender.sendEmail(templateId, parameters);
        } catch (Exception e) {
            LOG.error("Cannot send buy request payment email", e);
        }
    }

    @Override
    public void cancelTransaction(AuthenticatedUser user, long transactionId, TransactionCancelStrategy cancelStrategy)
            throws CardPaymentException, TransactionNotFoundException {
        Transaction transaction = lockTransaction(transactionId);
        cancelTransaction(user, transaction, cancelStrategy);
    }

    private void cancelTransaction(AuthenticatedUser user, Transaction transaction,
            TransactionCancelStrategy cancelStrategy) throws CardPaymentException {
        if (!transaction.isCancelled()) {
            if (!transaction.isADPTransaction()) {
                try {
                    cancelCCTransaction(user, transaction);
                } catch (CardPaymentException e) {
                    cancelStrategy.handleRealexError(transaction, e);
                }
            }
            setReservationsCancelled(transaction.getReservations());
            setTransactionCancelled(transaction);
            runPaymentCancelledEventForDomainsInTransaction(user, transaction);
        }
        if (cancelStrategy.shouldPurgeTransaction()) {
            purgeTransaction(transaction);
        }
    }

    private void cancelCCTransaction(AuthenticatedUser user, Transaction transaction) throws CardPaymentException {
        cardService.cancelRealexAuthorisation(new RealexTransactionIdentifier(transaction.getRealexAuthCode(),
                transaction.getOrderId(), transaction.getRealexPasRef()));
        sendCancellationEmail(user, transaction);
    }

    private void sendCancellationEmail(AuthenticatedUser user, Transaction transaction) {
        try {
            Reservation reservation = transaction.getReservations()
                    .get(0);//there should be exactly one reservation for this kind of transaction TODO what if not (renewal transaction)
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(transaction.getBillNicHandleId());
            TransactionDetails transactionDetails;
            PaymentEmailParameters parameters;
            switch (reservation.getOperationType()) {
                case REGISTRATION:
                    String holder = ticketSearchService.getTicket(reservation.getTicketId()).getOperation()
                            .getDomainHolderField().getNewValue();
                    transactionDetails = new TransactionDetails(reservation.getDomainName(), holder, Period.fromMonths(
                            reservation.getDurationMonths()).getYears(), reservation.getOperationType(),
                            reservation.getTotal());
                    parameters = new PaymentEmailParameters(user.getUsername(), transaction.getOrderId(),
                            reservation.getTotal(), nicHandle, reservation.getDomainName(), transactionDetails);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_CC_PREAUTH_RELEASED.getId(), parameters);
                    break;
                case TRANSFER:
                    Domain d = domainSearchService.getDomain(reservation.getDomainName());
                    transactionDetails = new TransactionDetails(reservation.getDomainName(), d.getHolder(), Period
                            .fromMonths(reservation.getDurationMonths()).getYears(), reservation.getOperationType(),
                            reservation.getTotal());
                    parameters = new PaymentEmailParameters(user.getUsername(), transaction.getOrderId(),
                            reservation.getTotal(), nicHandle, reservation.getDomainName(), transactionDetails);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.XFER_CC_PREAUTH_RELEASED.getId(), parameters);
                    break;
                case RENEWAL:
                    //skip
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type: " + reservation.getOperationType());
            }
        } catch (Exception e) {
            LOG.error("Cannot send CC cancellation email: " + e);
        }
    }

    private void purgeTransaction(Transaction transaction) {
        String bill = transaction.getBillNicHandleId();
        String orderId = transaction.getOrderId();
        String type = transaction.getOperationType().getTypeName();
        String domains = Joiner.on(", ").join(
                Iterables.transform(transaction.getReservations(), new Function<Reservation, String>() {
                    @Override
                    public String apply(Reservation reservation) {
                        return reservation.getDomainName();
                    }
                }));
        String s = "Removing transaction:" +
                "\n\tOrder-id: " + orderId +
                "\n\tBill NH:  " + bill +
                "\n\tType:     " + type +
                "\n\tDomains:  " + domains;
        LOG.info(s);
        for (Reservation reservation : transaction.getReservations()) {
            reservationDAO.deleteById(reservation.getId());
        }
        transactionDAO.deleteById(transaction.getId());
    }

    private void setReservationsCancelled(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            final long reservationId = r.getId();
            if (reservationDAO.lock(reservationId)) {
                Reservation reservation = reservationDAO.get(reservationId);
                if (reservation.isSettled()) {
                    throw new IllegalStateException("Reservation is settled");
                }
                reservation.setReadyForSettlement(false);
                reservationDAO.update(reservation);
            }
        }
    }

    private void setTransactionCancelled(Transaction transaction) {
        transaction.markCancelled();
        transactionDAO.update(transaction);
    }

    private void runPaymentCancelledEventForDomainsInTransaction(AuthenticatedUser user, Transaction transaction) {
        for (Reservation reservation : transaction.getReservations()) {
            OperationType operationType = reservation.getOperationType();
            switch (operationType) {
                case REGISTRATION:
                    // There are no domains in case of a registration reservation.
                    break;
                default:
                    String domainName = reservation.getDomainName();
                    String orderId = transaction.getOrderId();
                    runPaymentCancelledEventForDomain(user, domainName, orderId, operationType);
            }
        }
    }

    private void runPaymentCancelledEventForDomain(AuthenticatedUser user, String domainName, String orderId,
            OperationType operationType) {
        DsmEvent event = new PaymentCancelledEvent();
        OpInfo opInfo = new OpInfo(user, "Transaction cancelled");
        try {
            dsm.handleEvent(user, domainName, event, opInfo);
        } catch (DomainIllegalStateException e) {
            LOG.error(String.format("Failed to run PaymentCancelled event for the '%s' domain. The related transaction "
                    + "with order ID '%s' was cancelled. If the domain has the payment pending status it will remain "
                    + "in this state.", domainName, orderId), e);
        } catch (DomainNotFoundException e) {
            LOG.error(String.format("Failed to run PaymentCancelled event for the '%s' domain. The domain was not "
                    + "found. A domain should be present for a reservation of type '%s'. The related transaction with "
                    + "order ID '%s' was cancelled.", domainName, operationType.getTypeName(), orderId));
        }
    }

    @Override
    public void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId)
            throws TransactionNotFoundException, TransactionInvalidStateForSettlement, DomainNotFoundException,
            DomainIllegalStateException {
        Transaction transaction = lockTransaction(transactionId);
        validateTransactionToSettle(transaction);
        try {
            List<Reservation> reservations = transaction.getReservations();
            finalizePayment(transaction);
            settleReservations(user, opInfo, reservations);
            setTransactionSettlementEnded(transaction);
        } catch (CardPaymentException e) {
            LOG.warn(String.format("Cannot settle Realex transaction, orderId=%s", transaction.getOrderId()), e);
            if (e.getType().equals(CardPaymentException.Type.FAILED_TRANSACTION)) {
                handleClientFailingCardTransactionSettlement(user, transaction);
            }
            sendSettlementFailureEmail(user, transaction);
        } catch (NotEnoughDepositFundsException e) {
            LOG.warn(String.format("Not enough deposit funds to settle transaction, orderId=%s, billingNH=%s",
                    transaction.getOrderId(), getBillingNh(transaction.getReservations())), e);
            sendSettlementFailureEmail(user, transaction);
        } catch (DomainIllegalStateException | RuntimeException e) {
            if (!transaction.isADPTransaction()) {
                LOG.error("Realex transaction has been settled and will not be rolled back!", e);
            }
            throw e;
        }
    }

    private void validateTransactionToSettle(Transaction transaction) throws TransactionInvalidStateForSettlement {
        if (transaction.isCancelled() || !transaction.isSettlementStarted() || transaction.isSettlementEnded()
                || !transaction.isFinanciallyPassed()) {
            throw new TransactionInvalidStateForSettlement(transaction.toString());
        }
        List<Reservation> reservations = transaction.getReservations();
        if (Validator.isEmpty(reservations)) {
            throw new IllegalArgumentException("Transaction (transactionId=" + transaction.getId()
                    + ") does not have any reservations");
        }
    }

    private void finalizePayment(Transaction transaction) throws CardPaymentException, NotEnoughDepositFundsException {
        if (transaction.isADPTransaction()) {
            List<Reservation> reservations = transaction.getReservations();
            depositService.reduceDeposit(getBillingNh(reservations), transaction.getTotalCost(),
                    transaction.getOrderId(), DepositTransactionType.SETTLEMENT, null, null);
        } else {
            cardService.settleRealexAuthorisation(new RealexTransactionIdentifier(transaction.getRealexAuthCode(),
                    transaction.getOrderId(), transaction.getRealexPasRef()));
        }
    }

    private String getBillingNh(List<Reservation> reservations) {
        return reservations.get(0).getNicHandleId();
    }

    private void setTransactionSettlementEnded(Transaction transaction) {
        transaction.setSettlementEnded(true);
        transactionDAO.update(transaction);
    }

    private void settleReservations(AuthenticatedUser user, OpInfo opInfo,
            List<Reservation> reservations) throws DomainIllegalStateException {
        Date currDate = new Date();
        for (Reservation r : reservations) {
            final long reservationId = r.getId();
            if (reservationDAO.lock(reservationId)) {
                Reservation reservation = reservationDAO.get(reservationId);
                settleReservation(user, opInfo, reservation, currDate);
            }
        }
    }

    private void settleReservation(AuthenticatedUser user, OpInfo opInfo, Reservation reservation, Date settledDate)
            throws DomainIllegalStateException {
        validateReservationToSettle(reservation);

        Domain domain;
        try {
            domain = domainSearchService.getDomain(reservation.getDomainName());
        } catch (DomainNotFoundException e) {
            LOG.error("Data inconsistent! Cannot find domain " + reservation.getDomainName(), e);
            throw new IllegalStateException("Data inconsistent! Cannot find domain " + reservation.getDomainName(), e);
        }

        Date renewalDateBeforeEvent = domain.getRenewalDate();
        if (shouldRunPaymentRelatedEvents(reservation)) {
            Period period = Period.fromMonths(reservation.getDurationMonths());
            dsm.handleEvent(user, domain, new PaymentSettledEvent(period.getYears()), opInfo);
        }
        Date renewalDateAfterEvent = domain.getRenewalDate();

        reservation.markSettled(settledDate);
        reservation.setStartDate(renewalDateBeforeEvent);
        reservation.setEndDate(renewalDateAfterEvent);
        reservationDAO.update(reservation);
    }

    private void validateReservationToSettle(Reservation reservation) {
        if (reservation.isSettled()) {
            throw new IllegalStateException(
                    "Reservation already settled! reservationId=" + reservation.getId());
        }
        if (!reservation.isReadyForSettlement()) {
            throw new IllegalStateException("Reservation not ready for settlement! reservationId="
                    + reservation.getId());
        }
    }

    private boolean shouldRunPaymentRelatedEvents(Reservation reservation) {
        return reservation.getOperationType() == OperationType.REGISTRATION ||
                reservation.getOperationType() == OperationType.RENEWAL ||
                reservation.getOperationType() == OperationType.TRANSFER;
    }

    private void handleClientFailingCardTransactionSettlement(AuthenticatedUser user, Transaction transaction) {
        LOG.info("Settlement error's code suggests client's fault. Transaction will be cancelled and removed "
                + "from the database.");
        TransactionCancelStrategy cancelStrategy = new TransactionCancelStrategy() {
            @Override
            public void handleRealexError(Transaction transaction, CardPaymentException e) {
                LOG.warn(String.format("Failed to cancel the Realex authorization, it will have to be cancelled "
                                + "manually. AuthCode: %s, PasRef: %s, OrderId: %s.",
                        transaction.getRealexAuthCode(), transaction.getRealexPasRef(), transaction.getOrderId()));
            }

            @Override
            public boolean shouldPurgeTransaction() {
                return true;
            }
        };
        try {
            cancelTransaction(user, transaction, cancelStrategy);
        } catch (CardPaymentException e) {
            // All exceptions should be handled by the cancel strategy.
            LOG.error("Unhandled realex error when cancelling transaction", e);
        }
    }

    private void sendSettlementFailureEmail(AuthenticatedUser user, Transaction transaction) {
        try {
            int templateId = transaction.isADPTransaction() ? EmailTemplateNamesEnum.INVOICE_FAILURE_ADP.getId() :
                    EmailTemplateNamesEnum.INVOICE_FAILURE_CC.getId();
            String billingNhId = getBillingNh(transaction.getReservations());
            EmailParameters params = new InvoiceEmailParameters(nicHandleSearchService.getNicHandle(billingNhId),
                    new Date(), user.getUsername());
            emailTemplateSender.sendEmail(templateId, params);
        } catch (NicHandleNotFoundException | EmailException e) {
            LOG.error("Couldn't send the settlement failure email", e);
        }
    }

    @Override
    public boolean invalidateTransactionsIfNeeded(AuthenticatedUser user, long transactionId)
            throws TransactionNotFoundException, NotAdmissiblePeriodException, CardPaymentException,
            NicHandleNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            if (transaction.isCancelled() || transaction.isFinanciallyPassed() || transaction.isSettlementStarted()
                    || transaction.isSettlementEnded() || transaction.isInvalidated()) {
                throw new IllegalStateException("Transaction invalid state for invalidation transactionId="
                        + transactionId);
            }
            if (isTransactionInvalidationNeeded(transaction)) {
                invalidateTransaction(user, transaction);
                return true;
            } else {
                return false;
            }
        } else {
            throw new TransactionNotFoundException();
        }
    }

    private boolean isTransactionInvalidationNeeded(Transaction transaction)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        if (transaction.getReservations() == null || transaction.getReservations().isEmpty()) {
            throw new IllegalStateException("There is no reservations for transaction id=" + transaction.getId());
        }
        for (Reservation reservation : transaction.getReservations()) {
            Period period = Period.fromMonths(reservation.getDurationMonths());
            ProductPriceWithVat productPriceWithVat = paymentSearchService
                    .getProductPriceWithVat(period, reservation.getOperationType(), reservation.getNicHandleId());
            if (isVatOrProductPriceChanged(reservation, productPriceWithVat)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVatOrProductPriceChanged(Reservation reservation, ProductPriceWithVat productPriceWithVat) {
        return reservation.getVatId() != productPriceWithVat.getVat().getId()
                || reservation.getNetAmount().compareTo(productPriceWithVat.getNetAmount()) != 0;
    }

    private void invalidateTransaction(AuthenticatedUser user, Transaction transaction) throws CardPaymentException {
        transaction.markInvalidated();
        cancelTransaction(user, transaction, TransactionCancelStrategy.LEAVE_AS_CANCELLED);
    }

    @Override
    public PaymentSummary reauthoriseTransaction(Transaction transaction, Ticket ticket, CreditCard creditCard)
            throws DomainNotFoundException, NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException {
        Reservation oldReservation = transaction.getReservations().get(0);
        Period period = Period.fromMonths(oldReservation.getDurationMonths());
        ProductPriceWithVat productPriceWithVat = paymentSearchService
                .getProductPriceWithVat(period, oldReservation.getOperationType(), oldReservation.getNicHandleId());

        PaymentRequest paymentRequest = PaymentRequest.createPaymentRequestWithStandardCurrencyUnit(CURRENCY,
                productPriceWithVat.getTotal(), creditCard);
        RealexTransactionIdentifier transactionIdentifier = cardService.authorisePaymentTransaction(paymentRequest);
        String realexAuthcode = transactionIdentifier.getAuthcode();
        String realexPasref = transactionIdentifier.getPasref();
        String orderId = transactionIdentifier.getOrderId();

        Reservation newReservation;
        String domainName = oldReservation.getDomainName();
        long newTransactionId;
        if (isTriplePassed(ticket)) {
            newTransactionId = recreateTransaction(productPriceWithVat.getTotal(), productPriceWithVat.getNetAmount(),
                    productPriceWithVat.getVatAmount(), orderId, true, realexAuthcode, realexPasref);
            newReservation = Reservation.recreatedInstanceForTicket(oldReservation.getNicHandleId(), domainName,
                    oldReservation.getDurationMonths(), productPriceWithVat, oldReservation.getTicketId(),
                    newTransactionId,
                    oldReservation.getOperationType(), PaymentMethod.CC, true);
        } else {
            newTransactionId = recreateTransaction(productPriceWithVat.getTotal(), productPriceWithVat.getNetAmount(),
                    productPriceWithVat.getVatAmount(), orderId, false, realexAuthcode, realexPasref);
            newReservation = Reservation.recreatedInstanceForTicket(oldReservation.getNicHandleId(), domainName,
                    oldReservation.getDurationMonths(), productPriceWithVat, oldReservation.getTicketId(),
                    newTransactionId,
                    oldReservation.getOperationType(), PaymentMethod.CC, false);
        }
        reservationDAO.createReservation(newReservation);
        markTransactionReauthorised(transaction, newTransactionId);
        return new PaymentSummary(domainName, null, null, productPriceWithVat.getPeriod().getYears(),
                productPriceWithVat.getNetAmount(), productPriceWithVat.getVatAmount(), productPriceWithVat.getTotal(),
                orderId);
    }

    private void markTransactionReauthorised(Transaction oldTransaction, long newTransactionId) {
        oldTransaction.setReauthorisedId(newTransactionId);
        transactionDAO.update(oldTransaction);
    }

    private boolean isTriplePassed(Ticket ticket) {
        return ticket.getAdminStatus() == AdminStatus.PASSED
                && ticket.getTechStatus() == TechStatus.PASSED
                && ticket.getFinancialStatus() == FinancialStatus.PASSED;
    }

    private long recreateTransaction(BigDecimal total, BigDecimal net, BigDecimal vat, String orderId,
            boolean financiallyPassed, String realexAuthcode, String realexPasref) {
        return transactionDAO.createTransaction(Transaction.recreatedInstance(total, net, vat, orderId,
                financiallyPassed ? new Date() : null, realexAuthcode, realexPasref));
    }

    @Override
    public void setTransactionFinanciallyPassed(long transactionId) throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            Transaction transaction = transactionDAO.get(transactionId);
            transaction.setFinanciallyPassedDate(new Date());
            transactionDAO.update(transaction);
        } else {
            throw new TransactionNotFoundException();
        }
    }

    @Override
    public void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException {
        Transaction transaction = lockTransaction(transactionId);
        setTransactionStartedSettlement(transaction);
    }

    private void setTransactionStartedSettlement(Transaction transaction) {
        transaction.setSettlementStarted(true);
        transactionDAO.update(transaction);
    }

    private Transaction lockTransaction(long transactionId) throws TransactionNotFoundException {
        if (transactionDAO.lock(transactionId)) {
            return transactionDAO.get(transactionId);
        } else {
            throw new TransactionNotFoundException();
        }
    }

}
