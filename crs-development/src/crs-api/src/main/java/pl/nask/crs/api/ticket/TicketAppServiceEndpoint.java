package pl.nask.crs.api.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.TicketSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketHolderChangeException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.user.service.UserSearchService;

public class TicketAppServiceEndpoint extends WsSessionAware implements CRSTicketAppService {
    private static final String DEFAULT_HOSTMASTERS_REMARK = "changed with NRC (crs-ws)";
    private TicketAppService service;
    private UserSearchService userSearchService;

    @SuppressWarnings("nullness")
    public TicketAppServiceEndpoint() {}

    public void setService(TicketAppService service) {
        this.service = service;
    }

    public void setUserSearchService(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.ticket.CRSTicketAppService#view(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
    */
    @Override
    public TicketVO view(/*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new TicketVO(service.view(completeUser, ticketId));
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.ticket.CRSTicketAppService#edit(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
     */
    @Override
    public TicketVO edit(/*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new TicketVO(service.edit(completeUser, ticketId));
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.ticket.CRSTicketAppService#update(pl.nask.crs.api.vo.AuthenticatedUserVO, long, pl.nask.crs.api.vo.FailureReasonsVO, java.lang.String, boolean)
    */
    @Override
    public void update(/*>>>@Nullable*/ AuthenticatedUserVO user, long ticketId, /*>>>@Nullable*/
            TicketEditVO ticketEditVO, /*>>>@Nullable*/ String remark, boolean clikPaid)
            throws AuthenticationException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            SessionExpiredException, TicketHolderChangeException, DomainNotFoundException {
        ValidationHelper.validate(user);
        ValidationHelper.validate(ticketEditVO);
        ValidationHelper.validateNonNull(remark, "remark");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        try {
            service.updateAsOwner(completeUser, ticketId, ticketEditVO.toTicketEdit(), remark,
                    DEFAULT_HOSTMASTERS_REMARK, clikPaid, false);
        } catch (TicketEditFlagException e) {
            // Should never happen because forceUpdate=true indicate ticket edit flag validation will be skipped.
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TicketSearchResultVO find(/*>>>@Nullable*/ AuthenticatedUserVO user, /*>>>@Nullable*/
            TicketSearchCriteriaVO searchCriteriaVO, long offset, long limit, /*>>>@Nullable*/
            List<SortCriterion> sortCriteria)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, GenericValidationException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        ValidationHelper.validate(searchCriteriaVO);

        TicketSearchCriteria searchCriteria = searchCriteriaVO.toSearchCriteria();
        LimitedSearchResult<Ticket> result = service.findOwn(user, searchCriteria, offset, limit, sortCriteria);
        List<TicketVO> list = new ArrayList<>();
        for (Ticket t : result.getResults()) {
            list.add(new TicketVO(t));
        }

        TicketSearchResultVO res = new TicketSearchResultVO(list, result.getTotalResults());
        return res;
    }

}
