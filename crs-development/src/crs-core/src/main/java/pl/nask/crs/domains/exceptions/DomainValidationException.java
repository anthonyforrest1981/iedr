package pl.nask.crs.domains.exceptions;

import pl.nask.crs.app.ValidationException;

public abstract class DomainValidationException extends ValidationException {

    private String domainName;
    private String errorMessage;
    private String additionalMessage;

    public DomainValidationException(String message, String domainName) {
        super();
        this.domainName = domainName;
        this.errorMessage = message;
    }

    public DomainValidationException(String message, String domainName, String additionalMessage) {
        this(message, domainName);
        this.additionalMessage = additionalMessage;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getMessage() {
        if (additionalMessage == null) {
            return String.format("%s: %s", errorMessage, domainName);
        } else {
            return String.format("%s: %s, %s", errorMessage, domainName, additionalMessage);
        }
    }

    public String getRawErrorMessage() {
        return errorMessage;
    }

}
