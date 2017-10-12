package pl.nask.crs.payment.service.impl;

import java.math.BigDecimal;
import java.util.*;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.ReservationHistDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.DomainPricingDictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.secondarymarket.SecondaryMarketPrice;
import pl.nask.crs.secondarymarket.dao.SecondaryMarketPriceDAO;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.VatDictionary;
import pl.nask.crs.vat.dao.VatDAO;

public class PaymentSearchServiceImpl implements PaymentSearchService {

    private final ReservationDAO reservationDAO;
    private final ReservationHistDAO reservationHistDAO;
    private final TransactionDAO transactionDAO;
    private final TransactionHistDAO transactionHistDAO;
    private final DomainPricingDAO domainPricingDAO;
    private final SecondaryMarketPriceDAO secondaryMarketPriceDAO;
    private final VatDAO vatDAO;
    private final DepositService depositService;
    private final NicHandleSearchService nicHandleSearchService;
    private final ApplicationConfig applicationConfig;

    public PaymentSearchServiceImpl(ReservationDAO reservationDAO, ReservationHistDAO reservationHistDAO,
            TransactionDAO transactionDAO, TransactionHistDAO transactionHistDAO, DomainPricingDAO domainPricingDAO,
            SecondaryMarketPriceDAO secondaryMarketPriceDAO, VatDAO vatDAO, DepositService depositService,
            NicHandleSearchService nicHandleSearchService, ApplicationConfig applicationConfig) {
        this.reservationDAO = reservationDAO;
        this.reservationHistDAO = reservationHistDAO;
        this.transactionDAO = transactionDAO;
        this.transactionHistDAO = transactionHistDAO;
        this.domainPricingDAO = domainPricingDAO;
        this.secondaryMarketPriceDAO = secondaryMarketPriceDAO;
        this.vatDAO = vatDAO;
        this.depositService = depositService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    public Reservation getReservationForTicket(long ticketId) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setTicketId((int) ticketId);
        criteria.setReauthorized(false);
        List<Reservation> ret = reservationDAO.getAllReservations(criteria);
        return ret.isEmpty() ? null : ret.get(0);
    }

    @Override
    public LimitedSearchResult<Reservation> findReservations(ReservationSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        return reservationDAO.getReservations(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<Reservation> findHistoricalReservations(String billingNH,
            ReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        criteria.setBillingNH(billingNH);
        return reservationHistDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public ReservationTotals getNotSettledReservationsTotals(String nicHandle, boolean adp) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setSettled(false);
        criteria.setCancelled(false);
        criteria.setBillingNH(nicHandle);
        if (adp) {
            criteria.setPaymentMethod(PaymentMethod.ADP);
        } else {
            criteria.setPaymentMethod(PaymentMethod.CC);
        }
        return reservationDAO.getTotals(criteria);
    }

    @Override
    public LimitedSearchResult<ExtendedReservation> findExtendedReservations(ExtendedReservationSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        return reservationDAO.findExtended(criteria, offset, limit, sortBy);
    }

    @Override
    public Transaction getTransaction(long transactionId) throws TransactionNotFoundException {
        Transaction transaction = transactionDAO.get(transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException();
        }
        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactions(TransactionSearchCriteria criteria, List<SortCriterion> sortBy) {
        return transactionDAO.findAllTransactions(criteria, sortBy);
    }

    @Override
    public LimitedSearchResult<Transaction> findHistoricalTransactions(String billingNH,
            TransactionSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        criteria.setBillingNH(billingNH);
        return transactionHistDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public List<TransactionInfo> getReadyADPTransactionsReport(String billingNH) throws DepositNotFoundException {
        Validator.assertNotEmpty(billingNH, "billingNH");
        TransactionSearchCriteria readyNotSettledCriteria = new TransactionSearchCriteria();
        readyNotSettledCriteria.setBillingNH(billingNH);
        readyNotSettledCriteria.setReadyForSettlement(true);
        readyNotSettledCriteria.setSettlementEnded(false);
        readyNotSettledCriteria.setPaymentMethod(PaymentMethod.ADP);
        List<Transaction> transactions = findAllTransactions(readyNotSettledCriteria,
                Arrays.asList(new SortCriterion("financiallyPassedDate", true)));

        ExtendedDeposit extendedDeposit = depositService.viewDeposit(billingNH);
        BigDecimal availableBalance = MoneyUtils.getRoundedAndScaledValue(extendedDeposit.getCloseBal());

        List<TransactionInfo> ret = new ArrayList<TransactionInfo>(transactions.size());
        for (Transaction transaction : transactions) {
            BigDecimal transactionTotal = transaction.getTotalCost();
            availableBalance = availableBalance.subtract(transactionTotal);
            ret.add(new TransactionInfo(transaction.getFinanciallyPassedDate(), transaction.getOrderId(), transaction
                    .getOperationType(), transactionTotal, availableBalance));
        }
        return ret;
    }

    @Override
    public List<DomainInfo> getTransactionInfo(long transactionId) {
        return transactionHistDAO.getTransactionInfo(transactionId);
    }

    @Override
    public List<DomainInfo> getTransactionInfo(String orderId) {
        return transactionHistDAO.getTransactionInfo(orderId);
    }

    @Override
    public LimitedSearchResult<ReauthoriseTransaction> getTransactionToReauthorise(String billingNH, long offset,
            long limit, List<SortCriterion> sortBy) throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(billingNH);
        criteria.setInvalidated(true);
        criteria.setReauthorised(false);
        criteria.setPaymentMethod(PaymentMethod.CC);
        criteria.setTicketExists(true);
        LimitedSearchResult<Transaction> transactions = transactionDAO.find(criteria, offset, limit, sortBy);
        return prepareTransactionsToReauthorise(transactions);
    }

    private LimitedSearchResult<ReauthoriseTransaction> prepareTransactionsToReauthorise(
            LimitedSearchResult<Transaction> transactions)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        if (transactions.getResults().isEmpty()) {
            List<ReauthoriseTransaction> empty = Collections.emptyList();
            return new LimitedSearchResult<>(transactions.getCriteria(), transactions.getLimit(),
                    transactions.getOffset(), empty, 0);
        } else {
            List<ReauthoriseTransaction> reauthoriseTransactions = new ArrayList<>(transactions
                    .getResults().size());
            for (Transaction t : transactions.getResults()) {
                if (t.getReservations().size() > 1)
                    throw new IllegalStateException(
                            "Transaction to reauthorise has to many reservations. Transaction id=" + t.getId());
                Reservation oldReservation = t.getReservations().get(0);
                Period period = Period.fromMonths(oldReservation.getDurationMonths());
                ProductPriceWithVat productPriceWithVat = getProductPriceWithVat(period, oldReservation.getOperationType(),
                        oldReservation.getNicHandleId());
                ReauthoriseTransaction rt = new ReauthoriseTransaction(t.getId(), t.getOperationType(),
                        oldReservation.getDomainName(), oldReservation.getTotal(), oldReservation.getNetAmount(),
                        oldReservation.getVatAmount(), t.getOrderId(), productPriceWithVat.getTotal(),
                        productPriceWithVat.getNetAmount(), productPriceWithVat.getVatAmount(), Period.fromMonths(
                        oldReservation.getDurationMonths()).getYears());
                reauthoriseTransactions.add(rt);
            }
            return new LimitedSearchResult<>(transactions.getCriteria(), transactions.getLimit(),
                    transactions.getOffset(), reauthoriseTransactions, transactions.getTotalResults());
        }
    }

    @Override
    public BigDecimal getVatRate(String nicHandleId) {
        return getVat(nicHandleId).getVatRate();
    }

    private Vat getVat(String nicHandleId) {
        String vatCategory = getVatCategory(nicHandleId);
        Dictionary<String, Vat> vatDictionary = new VatDictionary(vatDAO, new Date());
        Vat vat = vatDictionary.getEntry(vatCategory);
        if (vat == null) {
            throw new IllegalStateException("vat not defined for operation!");
        }
        return vat;
    }

    @Override
    public List<DomainPrice> getDomainPricing(String nicHandleId) throws NicHandleNotFoundException {
        List<DomainPrice> allPrices = new DomainPricingDictionary(domainPricingDAO).getEntries();
        List<DomainPrice> directPrices = new ArrayList<>();
        List<DomainPrice> registrarPrices = new ArrayList<>();
        for (DomainPrice price : allPrices) {
            if (price.isDirect()) {
                directPrices.add(price);
            } else {
                registrarPrices.add(price);
            }
        }
        return nicHandleSearchService.isNicHandleDirect(nicHandleId) ? directPrices : registrarPrices;
    }

    @Override
    public DomainPrice getProductPrice(Period period, OperationType operationType, String nicHandleId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        int periodInYears = period.getYears();
        Boolean isRenewal = null;
        Boolean isRegistration = null;
        boolean isDirect = nicHandleSearchService.isNicHandleDirect(nicHandleId);

        switch (operationType) {
            case REGISTRATION:
                isRegistration = true;
                break;
            case RENEWAL:
            case TRANSFER:
                isRenewal = true;
                break;
            default:
                throw new IllegalStateException("Illegal operation type: " + operationType);
        }

        DomainPrice domainPrice = domainPricingDAO.getDomainPrice(periodInYears, new Date(), isRegistration, isRenewal,
                isDirect);
        if (domainPrice == null) {
            throw new NotAdmissiblePeriodException(periodInYears);
        }
        return domainPrice;
    }

    @Override
    public ProductPriceWithVat getProductPriceWithVat(Period period, OperationType operationType, String nicHandleId)
            throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        Vat vat = getVat(nicHandleId);
        DomainPrice productPrice = getProductPrice(period, operationType, nicHandleId);
        ProductPriceWithVat pwv = new ProductPriceWithVat(period, productPrice.getId(), productPrice.getPrice(), vat);
        return pwv;
    }

    @Override
    public PriceWithVat getRequestPriceWithVat(OperationType operationType, String nicHandleId)
            throws NicHandleNotFoundException {
        Vat vat = getVat(nicHandleId);
        CustomerType customerType = nicHandleSearchService.isNicHandleDirect(nicHandleId) ?
                CustomerType.Direct : CustomerType.Registrar;
        SecondaryMarketPrice requestPrice = secondaryMarketPriceDAO.getPriceByTypes(operationType, customerType);
        PriceWithVat pwv = new PriceWithVat(requestPrice.getAmount(), vat);
        return pwv;
    }

    private String getVatCategory(String nicHandleId) {
        try {
            NicHandle nh = nicHandleSearchService.getNicHandle(nicHandleId);
            String vatCategory = nh.getVatCategory();
            if (Validator.isEmpty(vatCategory)) {
                throw new IllegalStateException("Vat category not defined for nicHandle: " + nicHandleId);
            } else {
                return vatCategory;
            }
        } catch (NicHandleNotFoundException e) {
            //should never happen
            throw new IllegalStateException("NicHandle not found");
        }
    }

}
