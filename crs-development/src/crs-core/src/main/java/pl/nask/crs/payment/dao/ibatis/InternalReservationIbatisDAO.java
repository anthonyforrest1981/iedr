package pl.nask.crs.payment.dao.ibatis;

import java.util.*;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.dao.ibatis.objects.InternalExtendedReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservationTotals;

public class InternalReservationIbatisDAO extends GenericIBatisDAO<InternalReservation, Long> {
    private SequentialNumberGenerator idGenerator;

    private Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "id");
        sortMap.put("nicHandleId", "nicHandleId");
        sortMap.put("domainName", "domainName");
        sortMap.put("durationMonths", "durationMonths");
        sortMap.put("creationDate", "creationDate");
        sortMap.put("productId", "productId");
        sortMap.put("amount", "amount");
        sortMap.put("vatId", "vatId");
        sortMap.put("vatCategory", "vatCategory");
        sortMap.put("vatFromDate", "vatFromDate");
        sortMap.put("vatRate", "vatRate");
        sortMap.put("vatAmount", "vatAmount");
        sortMap.put("readyForSettlement", "readyForSettlement");
        sortMap.put("settled", "settled");
        sortMap.put("settledDate", "settledDate");
        sortMap.put("ticketId", "ticketId");
        sortMap.put("transactionId", "transactionId");
        sortMap.put("operationType", "operationType");
        sortMap.put("startDate", "startDate");
        sortMap.put("endDate", "endDate");
        sortMap.put("paymentMethod", "paymentMethod");
        sortMap.put("total", "total");
        sortMap.put("orderId", "orderId");
        sortMap.put("financialStatus", "financialStatus");
    }

    private static final Map<String, String> extendedSortMap = new HashMap<>();
    {
        extendedSortMap.put("invoiceNumber", "invoiceNumber");
        extendedSortMap.put("domainName", "domainName");
        extendedSortMap.put("billingNicHandle", "billingNicHandle");
        extendedSortMap.put("billingNicHandleName", "billingNicHandleName");
        extendedSortMap.put("paymentMethod", "paymentMethod");
        extendedSortMap.put("customerType", "customerType");
        extendedSortMap.put("operationType", "operationType");
        extendedSortMap.put("settledDate", "settledDate");
        extendedSortMap.put("invoiceDate", "invoiceDate");
        extendedSortMap.put("creationDate", "creationDate");
        extendedSortMap.put("startDate", "startDate");
        extendedSortMap.put("durationMonths", "durationMonths");
        extendedSortMap.put("renewalDate", "renewalDate");
        extendedSortMap.put("productId", "productId");
        extendedSortMap.put("netAmount", "netAmount");
        extendedSortMap.put("vatAmount", "vatAmount");
        extendedSortMap.put("vatId", "vatId");
        extendedSortMap.put("vatCategory", "vatCategory");
        extendedSortMap.put("vatFromDate", "vatFromDate");
        extendedSortMap.put("vatRate", "vatRate");
        extendedSortMap.put("total", "total");
        extendedSortMap.put("orderId", "orderId");
    }

    public InternalReservationIbatisDAO() {
        setUpdateQueryId("reservation.updateReservation");
        setDeleteQueryId("reservation.deleteReservationById");
        setLockQueryId("reservation.getLockedReservationById");
        setGetQueryId("reservation.getReservationById");
        setDeleteQueryId("reservation.deleteReservationById");
        setFindQueryId("reservation.findReservations");
        setCountFindQueryId("reservation.countFindReservations");
        setSortMapping(sortMap);
    }

    public long createReservation(InternalReservation internalReservation) {
        long id = idGenerator.getNextId();
        internalReservation.setId(id);
        performInsert("reservation.insertReservation", internalReservation);
        return id;
    }

    public List<InternalReservation> getAllReservations(SearchCriteria<InternalReservation> criteria) {
        FindParameters findParameters = new FindParameters(criteria);
        return performQueryForList("reservation.findReservations", findParameters);
    }

    public LimitedSearchResult<InternalReservation> getReservations(
            SearchCriteria<InternalReservation> reservationSearchCriteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        return performFind("reservation.findReservations", "reservation.countFindReservations",
                reservationSearchCriteria, offset, limit, sortBy);
    }

    public InternalReservationTotals getTotalsForDeposit(ReservationSearchCriteria criteria) {
        FindParameters params = new FindParameters(criteria);
        return performQueryForObject("reservation.getTotals", params);
    }

    public LimitedSearchResult<InternalExtendedReservation> findExtended(
            SearchCriteria<InternalExtendedReservation> criteria, long offset, long limit, List<SortCriterion> sortBy) {
        sortBy = initDefaultSort(sortBy);
        FindParameters parameters =
                new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy, extendedSortMap);
        List<InternalExtendedReservation> list = null;
        Integer total = performQueryForObject("reservation.countFindExtendedReservations", parameters);
        if (total == 0) {
            list = Collections.emptyList();
        } else {
            list = performQueryForList("reservation.findExtendedReservations", parameters);
        }
        return new LimitedSearchResult<>(criteria, limit, offset, list, total);
    }

    public void setIdGenerator(SequentialNumberGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    private List<SortCriterion> initDefaultSort(List<SortCriterion> sortBy) {
        if (Validator.isEmpty(sortBy)) {
            return Arrays.asList(new SortCriterion("invoiceNumber", true));
        } else {
            return sortBy;
        }
    }
}
