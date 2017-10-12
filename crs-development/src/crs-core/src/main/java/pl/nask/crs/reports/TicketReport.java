package pl.nask.crs.reports;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

public class TicketReport extends AbstractTicketReport {

    private /*>>>@Nullable*/ Integer ticketRevisionsCount;
    private /*>>>@Nullable*/ String ticketType;

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ Integer getTicketRevisionsCount() {
        return ticketRevisionsCount;
    }

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ String getTicketType() {
        return ticketType;
    }

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ Integer getDocumentsCount() {
        return null;
    }

    public void setTicketRevisionsCount(Integer ticketRevisionsCount) {
        this.ticketRevisionsCount = ticketRevisionsCount;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

}
