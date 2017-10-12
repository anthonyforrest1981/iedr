package pl.nask.crs.payment.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface InvoiceDAO extends GenericDAO<Invoice, Integer> {

    /**
     * This is for testing purposes only! Do not use this method in the production code!
     */
    List<Invoice> getAll();

    int createInvoice(Invoice invoice);

    List<DomainInfo> getInvoiceInfo(String invoiceNumber);

    Invoice getByNumber(String invoiceNumber);

    LimitedSearchResult<PlainInvoice> findPlain(PlainInvoiceSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);
}
