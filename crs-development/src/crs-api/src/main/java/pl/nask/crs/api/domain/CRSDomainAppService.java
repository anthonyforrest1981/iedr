package pl.nask.crs.api.domain;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.*;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.RenewalDateType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidSessionTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSDomainAppService {

    /**
     * Returns detailed domain info containing domain object, list of realted domains, list of pending domains
     * and information if domain has tickets or documents. This method works like #edit method but has different permissions.
     *
     * @param user authentication token, required
     * @param domainName viewed domain name, required
     * @return
     * @throws AccessDeniedException
     * @throws DomainNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    ExtendedDomainInfoVO view(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws AccessDeniedException, DomainNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    /**
     * Searches for domains matching given criteria in the given order. Domain objects from the result don't carry information about contacts or nameservers.
     * Result is limited by offset and limit parameters. Full DomainVO is returned
     *
     * @param user authentication token (required)
     * @param criteria domain search criteria, required
     * @param offset, required
     * @param limit maximum number of domains to be returned, required
     * @param orderBy sorting criteria, optional
     * @return
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    DomainSearchResultVO findDomains(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") DomainSearchCriteriaVO criteria, @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            GenericValidationException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    PlainDomainSearchResultVO  findPlainDomains(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") PlainDomainSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException, IncorrectUtf8FormatException;

    @WebMethod
    ExtendedDomainSearchResultVO  findExtendedDomains(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") ExtendedDomainSearchCriteriaVO criteria, @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException, IncorrectUtf8FormatException;

    @WebMethod
    PlainDomainSearchResultVO findDeletedDomains(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") DeletedDomainSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    PlainDomainSearchResultVO findTransferredInDomains(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") PlainDomainSearchCriteriaVO criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    PlainDomainSearchResultVO findTransferredAwayDomains(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") PlainDomainSearchCriteriaVO criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    /**
     * Moves domains into voluntary NRP
     *
     * @param user
     * @param domainNames
     */
    void enterVoluntaryNRP(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException;

    /**
     * Removes domains from voluntary NRP
     *
     * @param user
     * @param domainNames
     */
    void removeFromVoluntaryNRP(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException;

    @WebMethod
    ExtendedDomainSearchResultVO findDomainsForCurrentRenewal(@WebParam(name = "user")
            AuthenticatedUserVO user, @WebParam(name = "renewalDateType") RenewalDateType renewalDateType,
            @WebParam(name = "criteria") ExtendedDomainSearchCriteriaVO criteria,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, AuthenticationException,
            SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    ExtendedDomainSearchResultVO findDomainsForFutureRenewal(@WebParam(name = "user")
            AuthenticatedUserVO user, @WebParam(name = "month") int month,
            @WebParam(name = "criteria") ExtendedDomainSearchCriteriaVO criteria,
            @WebParam(name = "offset") long offset, @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    boolean isEventValid(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName, @WebParam(name = "eventName") DsmEventName eventName)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    DomainAvailabilityVO checkAvailability(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "domainName") String domainName) throws AccessDeniedException, IncorrectUtf8FormatException;

    /**
     * Allows to revert the billable domain state to 'billable'
     */
    @WebMethod
    void revertToBillable(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException,
            IncorrectUtf8FormatException;

    @WebMethod
    public NsReportSearchResultVO getNsReports(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "criteria") NsReportSearchCriteriaVO criteria, @WebParam(name = "offset") long offset, @WebParam(
            name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    List<String> checkPayAvailable(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    List<String> checkModificationAvailable(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "domainNames") List<String> domainNames,
            @WebParam(name = "isRenewalModeChange") boolean isRenewalModeChange)
            throws AuthenticationException, SessionExpiredException, NicHandleNotFoundException,
            IncorrectUtf8FormatException;

    @WebMethod
    void modifyRenewalMode(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames,
            @WebParam(name = "renewalMode") RenewalMode renewalMode)
            throws AuthenticationException, SessionExpiredException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException;

    @WebMethod
    List<CountryVO> getCountries(@WebParam(name = "user") AuthenticatedUserVO user) throws IncorrectUtf8FormatException;

    @WebMethod
    boolean isCharity(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    DomainCountForContactSearchResultVO findDomainCountForContact(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") DomainCountForContactSearchCriteriaVO criteria)
            throws AccessDeniedException, IncorrectUtf8FormatException;

}
