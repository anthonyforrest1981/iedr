package pl.nask.crs.app.tickets;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.List;

import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketModel {

    private /*>>>@Nullable*/ Ticket ticket;

    private TicketInfo additionalInfo;

    private List<HistoricalObject<Ticket>> history;

    public TicketModel(/*>>>@Nullable*/ Ticket ticket, TicketInfo additionalInfo, List<HistoricalObject<Ticket>> history) {
        this.ticket = ticket;
        this.additionalInfo = additionalInfo;
        this.history = history;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /*>>>@Pure*/
    public TicketInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(TicketInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /*>>>@Pure*/
    public List<HistoricalObject<Ticket>> getHistory() {
        return history;
    }

    public void setHistory(List<HistoricalObject<Ticket>> history) {
        this.history = history;
    }

}
