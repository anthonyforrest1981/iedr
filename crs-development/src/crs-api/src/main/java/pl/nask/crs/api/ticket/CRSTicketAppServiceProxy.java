package pl.nask.crs.api.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.TicketSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidSessionTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketHolderChangeException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

@WebService(
    serviceName = "CRSTicketAppService",
    endpointInterface = "pl.nask.crs.api.ticket.CRSTicketAppService",
    portName = "CRSTicketAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSTicketAppServiceProxy implements CRSTicketAppService {
    private CRSTicketAppService service;

    public void setService(CRSTicketAppService service) {
        this.service = service;
    }

    @SuppressWarnings("nullness")
    public CRSTicketAppServiceProxy() {}

    public TicketVO view(
    /*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.view(user, ticketId);
    }

    public TicketVO edit(
    /*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.edit(user, ticketId);
    }

    public void update(
    /*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId,
    /*>>>@Nullable*/ TicketEditVO domainOperation,
    /*>>>@Nullable*/ String remark, boolean clikPaid)
            throws AuthenticationException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            SessionExpiredException, IncorrectUtf8FormatException, TicketHolderChangeException,
            DomainNotFoundException {
        service.update(user, ticketId, domainOperation, remark, clikPaid);
    }

    public TicketSearchResultVO find(
    /*>>>@Nullable*/ AuthenticatedUserVO user,
    /*>>>@Nullable*/ TicketSearchCriteriaVO searchCriteria, long offset, long limit,
    /*>>>@Nullable*/ List<SortCriterion> sortCriteria)
            throws SessionExpiredException, AuthenticationException, GenericValidationException,
            IncorrectUtf8FormatException, NicHandleNotFoundException {
        return service.find(user, searchCriteria, offset, limit, sortCriteria);
    }

}
