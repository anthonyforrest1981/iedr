package pl.nask.crs.commons.pdfmerge;

public class PdfMergeToolException extends Exception {
    public PdfMergeToolException(String message) {
        super(message);
    }

    public PdfMergeToolException(Throwable e) {
        super(e);
    }
}
