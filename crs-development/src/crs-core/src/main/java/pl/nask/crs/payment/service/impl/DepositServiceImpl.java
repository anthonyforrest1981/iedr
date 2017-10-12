package pl.nask.crs.payment.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.HistoricalDepositDAO;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.email.DepositCorrectionParams;
import pl.nask.crs.payment.email.PaymentEmailParameters;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import static pl.nask.crs.commons.MoneyUtils.add;
import static pl.nask.crs.commons.MoneyUtils.getRoundedAndScaledValue;
import static pl.nask.crs.commons.MoneyUtils.subtract;

public class DepositServiceImpl implements DepositService {

    private final static String CURRENCY = "EUR";

    private static Logger LOG = Logger.getLogger(DepositServiceImpl.class);

    private final DepositDAO depositDAO;
    private final HistoricalDepositDAO historicalDepositDAO;
    private final DepositFundsExporter doaExporter;
    private final EmailTemplateSender emailTemplateSender;
    private final NicHandleSearchService nicHandleSearchService;
    private final NicHandleService nicHandleService;
    private final ReservationDAO reservationDAO;
    private final CardPaymentService cardPaymentService;
    private final ApplicationConfig appConfig;

    public DepositServiceImpl(DepositDAO depositDAO, HistoricalDepositDAO historicalDepositDAO, DepositFundsExporter doaExporter,
            EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService,
            NicHandleService nicHandleService, ReservationDAO reservationDAO, CardPaymentService cardPaymentService, ApplicationConfig appConfig) {
        super();
        this.depositDAO = depositDAO;
        this.historicalDepositDAO = historicalDepositDAO;
        this.doaExporter = doaExporter;
        this.emailTemplateSender = emailTemplateSender;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.reservationDAO = reservationDAO;
        this.cardPaymentService = cardPaymentService;
        this.appConfig = appConfig;
    }

    @Override
    public ExtendedDeposit initDeposit(String nicHandleId) {
        Deposit deposit = depositDAO.get(nicHandleId);
        if (deposit == null) {
            Deposit newDeposit = Deposit.newInstance(nicHandleId, new Date(), BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, DepositTransactionType.INIT, "", null, "deposit initialization");
            createDepositAndHistory(newDeposit);
            return new ExtendedDeposit(newDeposit, BigDecimal.ZERO);
        } else {
            throw new IllegalStateException("Deposit exists for nicHandle: " + nicHandleId);
        }
    }

    @Override
    public LimitedSearchResult<ExtendedDeposit> findDeposits(DepositSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        LimitedSearchResult<Deposit> searchResult = depositDAO.find(criteria, offset, limit, sortBy);
        List<ExtendedDeposit> extendedDepositList = new ArrayList<ExtendedDeposit>(searchResult.getResults().size());
        for (Deposit deposit : searchResult.getResults()) {
            ExtendedDeposit extendedDeposit = getDepositIncludingReservations(deposit);
            extendedDepositList.add(extendedDeposit);
        }
        return new LimitedSearchResult<>(criteria, offset, limit, extendedDepositList, searchResult.getTotalResults());
    }

    @Override
    public LimitedSearchResult<Deposit> findHistoricalDeposits(DepositSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        limit = limit == 0 ? Long.MAX_VALUE : limit;
        LimitedSearchResult<HistoricalObject<Deposit>> histResult =
                historicalDepositDAO.find((SearchCriteria) criteria, offset, limit, sortBy);
        List<Deposit> result = new ArrayList<>();
        for (HistoricalObject<Deposit> histDeposit : histResult.getResults()) {
            result.add(histDeposit.getObject());
        }
        return new LimitedSearchResult<>(criteria, limit, offset, result, histResult.getTotalResults());
    }

    @Override
    public ExtendedDeposit depositFunds(AuthenticatedUser user, OpInfo opInfo, String nicHandle,
            BigDecimal valueInStandardCurrencyUnit, CreditCard card)
            throws CardPaymentException, ExportException, DepositBelowLimitException, DepositOverLimitException {
        Deposit deposit = null;
        RealexTransactionIdentifier transactionIdentifier = null;
        try {
            validateDeposit(valueInStandardCurrencyUnit);
            PaymentRequest paymentRequest = PaymentRequest.createPaymentRequestWithStandardCurrencyUnit(CURRENCY,
                    valueInStandardCurrencyUnit, card);
            transactionIdentifier = cardPaymentService.authorisePaymentTransaction(paymentRequest);
            deposit = increaseDeposit(nicHandle, valueInStandardCurrencyUnit, transactionIdentifier.getOrderId(),
                    DepositTransactionType.TOPUP, null, null);
            LOG.debug("after changes applied");
            cardPaymentService.settleRealexAuthorisation(transactionIdentifier);
            LOG.debug("after commit");
            performExport(opInfo, nicHandle, valueInStandardCurrencyUnit, deposit.getTransactionDate(),
                    transactionIdentifier.getOrderId());
            sendTopUpEmail(user, transactionIdentifier.getOrderId(), valueInStandardCurrencyUnit, nicHandle);
            LOG.debug("after sent email");
        } catch (CardPaymentException e) {
            if (isPaymentTransactionAuthenticated(transactionIdentifier)) {
                try {
                    cardPaymentService.cancelRealexAuthorisation(transactionIdentifier);
                } catch (CardPaymentException e1) {
                    LOG.error("Can't void Payment Transaction: " + e1.getMessage());
                }
            }
            throw e;
        } catch (NicHandleNotFoundException e) {
            //should never happen
            LOG.error("Cannot send top up confirmation mail: " + e);
        }
        return getDepositIncludingReservations(deposit);
    }

    private void validateDeposit(BigDecimal value) throws DepositBelowLimitException, DepositOverLimitException {
        BigDecimal minLimit = appConfig.getDepositMinLimit();
        BigDecimal maxLimit = appConfig.getDepositMaxLimit();
        if (value.compareTo(minLimit) < 0) {
            throw new DepositBelowLimitException(minLimit);
        }
        if (value.compareTo(maxLimit) > 0) {
            throw new DepositOverLimitException(maxLimit);
        }
    }

    @Override
    public ExtendedDeposit viewDeposit(String nicHandleId) throws DepositNotFoundException {
        Deposit deposit = depositDAO.get(nicHandleId);
        if (deposit == null)
            throw new DepositNotFoundException(nicHandleId);
        return getDepositIncludingReservations(deposit);
    }

    @Override
    public LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset,
            long limit) {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId(nicHandle);
        criteria.setTransactionDateFrom(fromDate);
        criteria.setTransactionDateTo(toDate);
        criteria.setTransactionType(DepositTransactionType.TOPUP);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("transactionDate", false));

        LimitedSearchResult<HistoricalObject<Deposit>> deposits =
                historicalDepositDAO.find((SearchCriteria) criteria, offset, limit, sortBy);
        List<DepositTopUp> topUps = new ArrayList<>();
        for (HistoricalObject<Deposit> deposit : deposits.getResults()) {
            DepositTopUp topUp = new DepositTopUp();
            topUp.setClosingBalance(deposit.getObject().getCloseBal());
            topUp.setOperationDate(deposit.getObject().getTransactionDate());
            topUp.setOrderId(deposit.getObject().getOrderId());
            topUp.setTopUpAmount(deposit.getObject().getTransactionAmount());
            topUps.add(topUp);
        }

        return new LimitedSearchResult<>(criteria, limit, offset, topUps, deposits.getTotalResults());
    }

    @Override
    public Deposit correctDeposit(OpInfo opInfo, String nicHandle, BigDecimal amountInStandardCurrencyUnit,
            String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException {
        Deposit deposit;
        String username = opInfo.getUserName();
        String orderId = generateADPOrderId();
        if (amountInStandardCurrencyUnit.signum() > 0) {
            deposit = increaseDeposit(nicHandle, amountInStandardCurrencyUnit, orderId, DepositTransactionType.MANUAL,
                    username, remark);
        } else {
            try {
                ExtendedDeposit old = viewDeposit(nicHandle);
                if (MoneyUtils.add(old.getCloseBalMinusReservations(), amountInStandardCurrencyUnit).signum() < 0) {
                    throw new NotEnoughDepositFundsException(
                            "Cannot reduce deposit below the available funds (check unsettled reservations)");
                }
                deposit = reduceDeposit(nicHandle, amountInStandardCurrencyUnit.negate(), orderId,
                        DepositTransactionType.MANUAL, username, remark);
            } catch (DepositNotFoundException e) {
                throw new NotEnoughDepositFundsException("The deposit is empty", e);
            }
        }
        performExport(opInfo, nicHandle, amountInStandardCurrencyUnit, deposit.getTransactionDate(), orderId);
        sendCorrectionEmail(orderId, amountInStandardCurrencyUnit, nicHandle, remark, username);
        return deposit;
    }

    @Override
    public Deposit depositFundsOffline(AuthenticatedUser user, OpInfo opInfo, String nicHandle,
            BigDecimal amountInStandardCurrencyUnit, String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException,
            DepositBelowLimitException, DepositOverLimitException {
        validateDeposit(amountInStandardCurrencyUnit);
        String username = opInfo.getUserName();
        String orderId = generateADPOrderId();
        Deposit deposit = increaseDeposit(nicHandle, amountInStandardCurrencyUnit, orderId,
                DepositTransactionType.TOPUP, username, remark);
        performExport(opInfo, nicHandle, amountInStandardCurrencyUnit, deposit.getTransactionDate(), orderId);
        sendTopUpEmail(user, orderId, amountInStandardCurrencyUnit, nicHandle);
        return deposit;
    }

    private void performExport(OpInfo opInfo, String nicHandle, BigDecimal amount, Date transactionDate, String orderId)
            throws NicHandleNotFoundException, ExportException {
        nicHandleService.triggerExport(nicHandle, opInfo);
        doaExporter.exportDOA(nicHandle, amount, transactionDate, orderId);
    }

    private void sendCorrectionEmail(String orderId, BigDecimal valueInStandardUnit, String nicHandleName,
            String description, String username) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleName);
            EmailParameters params = new DepositCorrectionParams(orderId, getRoundedAndScaledValue(valueInStandardUnit),
                    nicHandle, description, username);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DEPOSIT_CORRECTION.getId(), params);
        } catch (Exception e) {
            LOG.error("Cannot send correction confirmation mail: " + e);
        }
    }

    private ExtendedDeposit getDepositIncludingReservations(Deposit deposit) {
        List<Reservation> reservations = getReadyForSettlementNotSettledReservations(deposit.getNicHandleId());
        BigDecimal totalReservations = BigDecimal.ZERO;
        for (Reservation reservation : reservations) {
            totalReservations = add(totalReservations, reservation.getTotal());
        }

        return new ExtendedDeposit(deposit, totalReservations);
    }

    private void sendTopUpEmail(AuthenticatedUser user, String orderId, BigDecimal valueInStandardUnit,
            String nicHandleName) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleName);
            PaymentEmailParameters params = new PaymentEmailParameters(user.getUsername(), orderId,
                    getRoundedAndScaledValue(valueInStandardUnit), nicHandle, null, null);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.TOP_UP.getId(), params);
        } catch (Exception e) {
            LOG.error("Cannot send top up confirmation mail: " + e);
        }
    }

    private List<Reservation> getReadyForSettlementNotSettledReservations(String billingNH) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH(billingNH);
        criteria.setPaymentMethod(PaymentMethod.ADP);
        return reservationDAO.getAllReservations(criteria);
    }

    private Deposit increaseDeposit(String nicHandleId, BigDecimal amount, String orderId,
            DepositTransactionType transType, String correctorNH, String remark) {
        Date transactionDate = new Date();
        if (!depositDAO.lock(nicHandleId)) {
            Deposit newDeposit = Deposit.newInstance(nicHandleId, transactionDate, amount, amount, amount, transType,
                    orderId, correctorNH, remark);
            createDepositAndHistory(newDeposit);
            return newDeposit;
        } else {
            Deposit deposit = depositDAO.get(nicHandleId);
            Deposit increasedDeposit = Deposit.newInstance(nicHandleId, transactionDate, deposit.getCloseBal(),
                    add(deposit.getCloseBal(), amount), amount, transType, orderId, correctorNH, remark);
            updateDepositAndHistory(increasedDeposit);
            return increasedDeposit;
        }
    }

    public static String generateADPOrderId() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Random r = new Random();
        return formatter.format(new Date()) + "-D-" + r.nextInt(9999999);
    }

    @Override
    public Deposit reduceDeposit(String nicHandleId, BigDecimal amountInStandardCurrencyUnit, String orderId,
            DepositTransactionType transType, String correctorNH, String remark) throws NotEnoughDepositFundsException {
        if (depositDAO.lock(nicHandleId)) {
            Deposit deposit = depositDAO.get(nicHandleId);
            if (deposit.getCloseBal().compareTo(amountInStandardCurrencyUnit) < 0)
                throw new NotEnoughDepositFundsException();
            Deposit reducedDeposit = Deposit.newInstance(deposit.getNicHandleId(), new Date(), deposit.getOpenBal(),
                    subtract(deposit.getCloseBal(), amountInStandardCurrencyUnit), amountInStandardCurrencyUnit.negate(),
                    transType, orderId, correctorNH, remark);
            updateDepositAndHistory(reducedDeposit);
            return reducedDeposit;
        } else {
            throw new NotEnoughDepositFundsException();
        }
    }

    private void createDepositAndHistory(Deposit newDeposit) {
        depositDAO.create(newDeposit);
        updateDepositAndHistory(newDeposit);
    }

    private void updateDepositAndHistory(Deposit deposit) {
        long changeId = historicalDepositDAO.create(deposit, deposit.getTransactionDate(), deposit.getCorrectorNH());
        depositDAO.updateUsingHistory(changeId);
    }

    private boolean isPaymentTransactionAuthenticated(RealexTransactionIdentifier transactionIdentifier) {
        return (transactionIdentifier != null && transactionIdentifier.getAuthcode() != null);
    }
}
