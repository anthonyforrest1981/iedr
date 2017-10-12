package pl.nask.crs.reports.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.reports.AbstractTicketReport;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.ReportType;

public class ReportsSearchCriteria implements SearchCriteria<AbstractTicketReport> {

    private ReportType reportType;
    private ReportTimeGranulation reportTimeGranulation;

    private String hostmasterName;
    private Date from;
    private Date to;

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportTimeGranulation getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public void setReportTimeGranulation(ReportTimeGranulation reportTimeGranulation) {
        this.reportTimeGranulation = reportTimeGranulation;
    }

    public String getHostmasterName() {
        return hostmasterName;
    }

    public void setHostmasterName(String hostmasterName) {
        this.hostmasterName = hostmasterName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = DateUtils.startOfDay(from);
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = DateUtils.endOfDay(to);
    }
}
