package pl.nask.crs.api.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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

/**
 *
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 *
 * Set of services to access and manipulate Ticket objects.
 *
 * <p><b><i>user</i> parameter is required by all services</b></p>
 *
 * @author Artur Gniadzik
 *
 */
@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSTicketAppService {

    /**
     * Returns ticket object for specified ticket id.
     *
     * @param user authentication token
     * @param ticketId ticket id to be view
     *
     * @return full ticket object
     * @throws AccessDeniedException
     * @throws TicketNotFoundException if no ticket with given id exists
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    TicketVO view(@WebParam(name = "user") /*>>>@Nullable*/ AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    /**
     * Returns a ticket to edit
     *
     * @param user authentication token
     * @param ticketId ticket id to edit
     * @return
     * @throws AccessDeniedException
     * @throws TicketNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    TicketVO edit(@WebParam(name = "user") /*>>>@Nullable*/ AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    void update(@WebParam(name = "user") /*>>>@Nullable*/ AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId,
            @WebParam(name = "domainOperations") /*>>>@Nullable*/ TicketEditVO domainOperation, @WebParam(
                    name = "remark") /*>>>@Nullable*/ String remark, @WebParam(name = "clikPaid") boolean clikPaid)
            throws AuthenticationException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            SessionExpiredException, IncorrectUtf8FormatException, TicketHolderChangeException, DomainNotFoundException;

    /**
     * Returns ticket list. Result is limited by offset and limit parameters.
     *
     * @param user authentication token, required
     * @param searchCriteria ticket search criteria, optional
     * @param offset
     * @param limit maximum number of tickets to be returned
     * @param sortCriteria the list of sort criteria, optional. Possible values of 'orderBy' are:
     * id, type, domainName, domainNameFR, domainHolder, domainHolderFR, resellerAccountId, resellerAccountName,
     *  agreementSigned, ticketEdit, resellerAccountFR, domainHolderClass, domainHolderClassFR,
     *  domainHolderCat, domainHolderCatFR,
     *  adminContact1NH, adminContact1Name, adminContact1Email, adminContact1CompanyName, adminContact1Country, adminContact1FR,
     *  adminContact2NH, adminContact2Name, adminContact2Email, adminContact2CompanyName, adminContact2Country, adminContact2FR,
     *   techContactNH, techContactName, techContactEmail, techContactCompanyName, techContactCountry, techContactFR,
     *   billingContactNH, billingContactName, billingContactEmail, billingContactCompanyName, billingContactCountry, billingContactFR,
     *   creatorNH, creatorName, creatorEmail, creatorCompanyName, creatorCountry,
     *   ns1, ns1FR, ip1, ip1FR,
     *   ns2, ns2FR, ip2, ip2FR,
     *   ns3, ns3FR, ip3, ip3FR,
     *   adminStatus, adminStatusChangeDate,
     *   techStatus, techStatusChangeDate,
     *  checkedOut, checkedOutToNH, checkedOutToName,
     *   renewalDate, changeDate,
     *   requestersRemark,
     *   hostmastersRemark,
     *   creationDate,
     *   clikPaid
     *
     * @return
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    TicketSearchResultVO find(@WebParam(name = "user") /*>>>@Nullable*/ AuthenticatedUserVO user, @WebParam(
            name = "searchCriteria") /*>>>@Nullable*/ TicketSearchCriteriaVO searchCriteria,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit, @WebParam(
                    name = "sortCriteria") /*>>>@Nullable*/ List<SortCriterion> sortCriteria)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, GenericValidationException, IncorrectUtf8FormatException,
            NicHandleNotFoundException;

}
