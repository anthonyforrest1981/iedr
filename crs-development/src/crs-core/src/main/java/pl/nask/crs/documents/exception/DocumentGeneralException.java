package pl.nask.crs.documents.exception;

public class DocumentGeneralException extends Exception {

    public DocumentGeneralException() {}

    public DocumentGeneralException(String s) {
        super(s);
    }

    public DocumentGeneralException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DocumentGeneralException(Throwable throwable) {
        super(throwable);
    }
}
