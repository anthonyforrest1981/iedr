package pl.nask.crs.ticket.response;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketResponse {

    private long id;

    private /*>>>@Nullable*/ String title;

    private /*>>>@Nullable*/ String text;

    public TicketResponse() {}

    public TicketResponse(/*>>>@Nullable*/ String title, /*>>>@Nullable*/ String text) {
        this.title = title;
        this.text = text;
    }

    public TicketResponse(long id, /*>>>@Nullable*/ String title, /*>>>@Nullable*/ String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public /*>>>@Nullable*/ String getTitle() {
        return title;
    }

    public void setTitle(/*>>>@Nullable*/ String title) {
        this.title = title;
    }

    public /*>>>@Nullable*/ String getText() {
        return text;
    }

    public void setText(/*>>>@Nullable*/ String text) {
        this.text = text;
    }
}
