package pl.nask.crs.app.commons;

public enum TicketSource {

    CONSOLE("Console"),
    API("API");

    private String source;

    TicketSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return this.source;
    }

}
