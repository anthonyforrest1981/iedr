package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;

public class TransferTicketEmailParameters extends AbstractTicketEmailParameters {

    public TransferTicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket) {
        super(username, ticket);
    }

    public TransferTicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket, int daysRemaining,
            int daysPassed) {
        super(username, ticket, daysRemaining, daysPassed);
    }

    @Override
    public String getAccountRelatedNicHandle(boolean gaining) {
        if (gaining) {
            assert ticket.getOperation().getBillingContactsField().get(0).getNewValue() != null : "@AssumeAssertion(nullness)";
            return ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
        } else {
            assert ticket.getOperation().getBillingContactsField().get(0).getCurrentValue() != null : "@AssumeAssertion(nullness)";
            return ticket.getOperation().getBillingContactsField().get(0).getCurrentValue().getNicHandle();
        }
    }

    public List<ParameterName> getParameterNames() {
        List<ParameterName> parameterNames = new ArrayList<ParameterName>(super.getParameterNames());
        parameterNames.addAll(Arrays.<ParameterName>asList(ParameterNameEnum.LOSING_BILL_C_NAME,
                ParameterNameEnum.LOSING_BILL_C_EMAIL, ParameterNameEnum.GAINING_BILL_C_NAME,
                ParameterNameEnum.GAINING_BILL_C_EMAIL));
        return parameterNames;
    }

    /**
     * Returns the parameter
     *
     * @param name
     * @return searched value, can be null
     */
    public /*>>>@Nullable*/ String getParameterValue(String name, boolean html) {
        Validator.assertNotNull(name, "parameter name");
        ParameterNameEnum ticketName = ParameterNameEnum.forName(name);
        DomainOperation domainOp = ticket.getOperation();
        switch (ticketName) {
            case LOSING_BILL_C_NAME:
                return getName(domainOp.getBillingContactsField().get(0).getCurrentValue());
            case LOSING_BILL_C_EMAIL:
                return getEmail(domainOp.getBillingContactsField().get(0).getCurrentValue());
            case GAINING_BILL_C_NAME:
                return getName(domainOp.getBillingContactsField().get(0).getNewValue());
            case GAINING_BILL_C_EMAIL:
                return getEmail(domainOp.getBillingContactsField().get(0).getNewValue());
            default:
                return super.getParameterValue(ticketName);
        }
    }

}
