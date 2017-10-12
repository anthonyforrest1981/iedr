package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;

public class TicketEmailParameters extends AbstractTicketEmailParameters {

    public TicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket) {
        super(username, ticket);
    }

    public TicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket, int daysRemaining, int daysPassed) {
        super(username, ticket, daysRemaining, daysPassed);
    }

    public List<ParameterName> getParameterNames() {
        List<ParameterName> parameterNames = new ArrayList<ParameterName>(super.getParameterNames());
        parameterNames.addAll(Arrays.<ParameterName>asList(ParameterNameEnum.BILL_C_NAME,
                ParameterNameEnum.BILL_C_CO_NAME, ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.REGISTRAR_NAME));
        return parameterNames;
    }

    /**
     * Returns the parameter
     *
     * @param paramName
     * @return searched value, can be null
     */
    @Override
    public /*>>>@Nullable*/ String getParameterValue(ParameterNameEnum paramName) {
        DomainOperation domainOp = ticket.getOperation();
        switch (paramName) {
            case BILL_C_NAME:
            case REGISTRAR_NAME:
                return getNewName(domainOp.getBillingContactsField().get(0));
            case BILL_C_CO_NAME:
                final Contact contact = domainOp.getBillingContactsField().get(0).getNewValue();
                assert contact != null : "@AssumeAssertion(nullness)";
                return contact.getCompanyName();
            case BILL_C_EMAIL:
                return getNewEmail(domainOp.getBillingContactsField().get(0));
            default:
                return super.getParameterValue(paramName);
        }
    }
}
