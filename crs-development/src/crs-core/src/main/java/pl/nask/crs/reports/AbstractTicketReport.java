package pl.nask.crs.reports;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.Date;

public abstract class AbstractTicketReport {

    private /*>>>@Nullable*/ String hostmasterName;
    private /*>>>@Nullable*/ Date reportForDate;

    public void setHostmasterName(String hostmasterName) {
        this.hostmasterName = hostmasterName;
    }

    public void setReportForDate(Date reportForDate) {
        this.reportForDate = reportForDate;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getHostmasterName() {
        return hostmasterName;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ Date getReportForDate() {
        return reportForDate;
    }

    /*>>>@Pure*/
    abstract public /*>>>@Nullable*/ Integer getTicketRevisionsCount();

    /*>>>@Pure*/
    abstract public /*>>>@Nullable*/ String getTicketType();

    /*>>>@Pure*/
    abstract public /*>>>@Nullable*/ Integer getDocumentsCount();

}
