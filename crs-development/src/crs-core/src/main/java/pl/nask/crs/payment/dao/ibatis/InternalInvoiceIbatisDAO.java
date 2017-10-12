package pl.nask.crs.payment.dao.ibatis;

import java.util.*;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.PlainInvoice;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.payment.PlainInvoiceSearchCriteria;
import pl.nask.crs.payment.dao.ibatis.objects.InternalInvoice;

public class InternalInvoiceIbatisDAO extends GenericIBatisDAO<InternalInvoice, Integer> {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "id");
        sortMap.put("invoiceNumber", "invoiceNumber");
        sortMap.put("accountName", "accountName");
        sortMap.put("accountNumber", "accountNumber");
        sortMap.put("address1", "address1");
        sortMap.put("address2", "address2");
        sortMap.put("address3", "address3");
        sortMap.put("billingNicHandle", "billingNicHandle");
        sortMap.put("billingNicHandleName", "billingNicHandleName");
        sortMap.put("completed", "completed");
        sortMap.put("countryName", "countryName");
        sortMap.put("countyName", "countyName");
        sortMap.put("crsVersion", "crsVersion");
        sortMap.put("invoiceDate", "invoiceDate");
        sortMap.put("totalCost", "totalCost");
        sortMap.put("totalNetAmount", "totalNetAmount");
        sortMap.put("totalVatAmount", "totalVatAmount");
        sortMap.put("settlementDate", "settlementDate");
        sortMap.put("paymentMethod", "paymentMethod");
    }

    public InternalInvoiceIbatisDAO() {
        setGetQueryId("invoice.selectInvoiceById");
        setLockQueryId("invoice.selectInvoiceLockedById");
        setCreateQueryId("invoice.insertInvoice");
        setCountFindQueryId("invoice.countFindInvoices");
        setUpdateQueryId("invoice.updateInvoice");

        setSortMapping(sortMap);
    }

    public List<InternalInvoice> getAll() {
        return performQueryForList("invoice.selectAll");
    }

    public int createInvoice(InternalInvoice internalInvoice) {
        performInsert("invoice.insertInvoice", internalInvoice);
        return internalInvoice.getId();
    }

    public List<DomainInfo> getInvoiceInfo(String invoiceNumber) {
        return performQueryForList("invoice.getInvoiceInfo", invoiceNumber);
    }

    public InternalInvoice getByNumber(String invoiceNumber) {
        return (InternalInvoice) performQueryForObject("invoice.selectInvoiceByNumber", invoiceNumber);
    }

    private List<SortCriterion> initDefaultSort(List<SortCriterion> sortBy) {
        if (Validator.isEmpty(sortBy)) {
            return Arrays.asList(new SortCriterion("invoiceNumber", true));
        } else {
            return sortBy;
        }
    }

    public LimitedSearchResult<PlainInvoice> findPlain(PlainInvoiceSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        sortBy = initDefaultSort(sortBy);
        int total = performCount("invoice.countFindInvoices", criteria);
        List<PlainInvoice> list;
        if (total == 0) {
            list = new ArrayList<>();
        } else {
            FindParameters params = new FindParameters(criteria).setOrderBy(sortBy).setLimit(offset, limit);
            list = performQueryForList("invoice.findPlainInvoices", params);
        }
        return new LimitedSearchResult<>(criteria, limit, offset, list, total);
    }
}
