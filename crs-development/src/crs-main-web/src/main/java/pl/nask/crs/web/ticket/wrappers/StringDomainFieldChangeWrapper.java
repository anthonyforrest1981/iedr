package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class StringDomainFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<String> {

    public StringDomainFieldChangeWrapper(SimpleDomainFieldChange<String> orig, DomainOperationType opType) {
        super(orig, opType);
    }

    public String getValue() {
        return getNewValue();
    }

    public void setValue(String value) {
        setNewValue(value);
    }
}
