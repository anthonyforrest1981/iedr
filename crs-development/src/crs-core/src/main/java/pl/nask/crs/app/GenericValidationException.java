package pl.nask.crs.app;

public class GenericValidationException extends ValidationException {

    private final String customMessage;

    public GenericValidationException(String s) {
        this.customMessage = s;
    }

    @Override
    public String getMessage() {
        return customMessage;
    }
}
