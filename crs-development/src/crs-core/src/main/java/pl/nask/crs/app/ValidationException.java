package pl.nask.crs.app;

/**
 * @author: Marcin Tkaczyk
 */
public abstract class ValidationException extends Exception {

    protected ValidationException() {}

    protected ValidationException(Throwable e) {
        super(e);
    }

    @Override
    public abstract String getMessage();
}
