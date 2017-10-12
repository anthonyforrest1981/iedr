package pl.nask.crs.iedrapi.impl.ticket;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.QueryType;
import ie.domainregistry.ieapi_ticket_1.ResDataType;

public class TicketQueryCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<QueryType> {

    @Override
    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);

        TicketSearchCriteria searchCriteria = createSearchCriteria(auth, command);
        long offset = TypeConverter.pageToOffset(command.getPage());
        LimitedSearchResult<Ticket> res = findOwnTickets(auth, searchCriteria, offset);

        if (res.getTotalResults() == 0) {
            return ResponseTypeFactory.successNoRes();
        }
        validatePage(res.getTotalResults(), offset);

        ResDataType resDataType = new ResDataType();
        resDataType.setPage(command.getPage());
        resDataType.setTotalPages(TypeConverter.totalResultsToPages(res.getTotalResults()));
        for (Ticket t : res.getResults()) {
            final String domainName = t.getOperation().getDomainNameField().getNewValue();
            assert domainName != null : "@AssumeAssertion(nullness)";
            resDataType.getDomain().add(domainName);
        }

        ResponseType cres = ResponseTypeFactory.success(resDataType);
        return cres;
    }

    private TicketSearchCriteria createSearchCriteria(AuthData auth, QueryType command)
            throws AccessDeniedException, IedrApiException {
        TicketSearchCriteria searchCriteria = new TicketSearchCriteria();
        searchCriteria.setAccountId(getAccountId(auth.getUser()));
        searchCriteria.setCustomerStatus(CustomerStatus.NEW);
        switch (command.getType()) {
            case ALL:
                break;
            case TRANSFERS:
                searchCriteria.setTicketType(DomainOperationType.XFER);
                break;
            case MODIFICATIONS:
                searchCriteria.setTicketType(DomainOperationType.MOD);
                break;
            case REGISTRATIONS:
                searchCriteria.setTicketType(DomainOperationType.REG);
                break;
            default:
                throw new CommandUseError();
        }
        return searchCriteria;
    }

    private LimitedSearchResult<Ticket> findOwnTickets(AuthData auth, TicketSearchCriteria searchCriteria, long offset)
            throws AccessDeniedException, IedrApiException {
        try {
            return getTicketAppService().findOwn(auth.getUser(), searchCriteria, offset, IedrApiConfig.getPageSize(),
                    null);
        } catch (NicHandleNotFoundException e) {
            throw new CommandFailed(e);
        }
    }

}
