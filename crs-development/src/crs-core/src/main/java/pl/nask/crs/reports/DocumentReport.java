package pl.nask.crs.reports;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

public class DocumentReport extends AbstractTicketReport {

    private Integer documentsCount;

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ Integer getTicketRevisionsCount() {
        return null;
    }

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ String getTicketType() {
        return null;
    }

    /*>>>@Pure*/
    @Override
    public /*>>>@Nullable*/ Integer getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(Integer documentsCount) {
        this.documentsCount = documentsCount;
    }

}
