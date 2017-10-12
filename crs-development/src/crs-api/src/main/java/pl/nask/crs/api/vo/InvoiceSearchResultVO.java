package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.PlainInvoice;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceSearchResultVO {

    private long totalResults;
    private List<InvoiceVO> results = new ArrayList<InvoiceVO>();

    public InvoiceSearchResultVO() {}

    public InvoiceSearchResultVO(LimitedSearchResult<PlainInvoice> limitedSearchResult) {
        this.totalResults = limitedSearchResult.getTotalResults();
        for (PlainInvoice invoice : limitedSearchResult.getResults()) {
            results.add(new InvoiceVO(invoice));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<InvoiceVO> getResults() {
        return results;
    }
}
