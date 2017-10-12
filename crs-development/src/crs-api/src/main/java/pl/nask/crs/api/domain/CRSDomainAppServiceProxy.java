package pl.nask.crs.api.domain;

import java.util.List;

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

@WebService(
    serviceName="CRSDomainAppService",
    endpointInterface="pl.nask.crs.api.domain.CRSDomainAppService",
    portName = "CRSDomainAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSDomainAppServiceProxy implements CRSDomainAppService {
    private CRSDomainAppService service;

    public void setService(CRSDomainAppService service) {
        this.service = service;
    }

    public ExtendedDomainInfoVO view(AuthenticatedUserVO user, String domainName)
            throws AccessDeniedException, DomainNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.view(user, domainName);
    }

    public DomainSearchResultVO findDomains(AuthenticatedUserVO user, DomainSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            GenericValidationException, AuthenticationException, IncorrectUtf8FormatException {
        return service.findDomains(user, criteria, offset, limit, orderBy);
    }

    public PlainDomainSearchResultVO findPlainDomains(AuthenticatedUserVO user, PlainDomainSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException, IncorrectUtf8FormatException {
        return service.findPlainDomains(user, criteria, offset, limit, orderBy);
    }

    public ExtendedDomainSearchResultVO findExtendedDomains(AuthenticatedUserVO user, ExtendedDomainSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException, IncorrectUtf8FormatException {
        return service.findExtendedDomains(user, criteria, offset, limit, orderBy);
    }

    public PlainDomainSearchResultVO findDeletedDomains(AuthenticatedUserVO user,
            DeletedDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException {
        return service.findDeletedDomains(user, criteria, offset, limit, orderBy);
    }

    public PlainDomainSearchResultVO findTransferredInDomains(AuthenticatedUserVO user,
            PlainDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.findTransferredInDomains(user, criteria, offset, limit, sortBy);
    }

    public PlainDomainSearchResultVO findTransferredAwayDomains(AuthenticatedUserVO user,
            PlainDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.findTransferredAwayDomains(user, criteria, offset, limit, sortBy);
    }

    public void enterVoluntaryNRP(AuthenticatedUserVO user, String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException {
        service.enterVoluntaryNRP(user, domainNames);
    }

    public void removeFromVoluntaryNRP(AuthenticatedUserVO user, String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException {
        service.removeFromVoluntaryNRP(user, domainNames);
    }

    public ExtendedDomainSearchResultVO findDomainsForCurrentRenewal(AuthenticatedUserVO user,
            RenewalDateType renewalDateType, ExtendedDomainSearchCriteriaVO criteria, long offset, long limit,
            List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, AuthenticationException,
            SessionExpiredException, IncorrectUtf8FormatException {
        return service.findDomainsForCurrentRenewal(user, renewalDateType, criteria, offset, limit, sortBy);
    }

    public ExtendedDomainSearchResultVO findDomainsForFutureRenewal(AuthenticatedUserVO user, int month,
            ExtendedDomainSearchCriteriaVO criteria, long offset, long limit,
            List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException {
        return service.findDomainsForFutureRenewal(user, month, criteria, offset, limit, sortBy);
    }

    public boolean isEventValid(AuthenticatedUserVO user, String domainName, DsmEventName eventName)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.isEventValid(user, domainName, eventName);
    }

    public DomainAvailabilityVO checkAvailability(AuthenticatedUserVO user, String domainName)
            throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.checkAvailability(user, domainName);
    }

    public void revertToBillable(AuthenticatedUserVO user, List<String> domainNames)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException,
            IncorrectUtf8FormatException {
        service.revertToBillable(user, domainNames);
    }

    public NsReportSearchResultVO getNsReports(AuthenticatedUserVO user, NsReportSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> sortBy)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.getNsReports(user, criteria, offset, limit, sortBy);
    }

    public List<String> checkPayAvailable(AuthenticatedUserVO user, List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.checkPayAvailable(user, domainNames);
    }

    @Override
    public List<String> checkModificationAvailable(AuthenticatedUserVO user, List<String> domainNames,
            boolean isRenewalModeChange)
            throws AuthenticationException, SessionExpiredException, NicHandleNotFoundException,
            IncorrectUtf8FormatException {
        return service.checkModificationAvailable(user, domainNames, isRenewalModeChange);
    }

    public void modifyRenewalMode(AuthenticatedUserVO user, List<String> domainNames, RenewalMode renewalMode)
            throws AuthenticationException, SessionExpiredException, DomainNotFoundException,
            DomainIllegalStateException, IncorrectUtf8FormatException {
        service.modifyRenewalMode(user, domainNames, renewalMode);
    }

    public List<CountryVO> getCountries(AuthenticatedUserVO user) throws IncorrectUtf8FormatException {
        return service.getCountries(user);
    }

    public boolean isCharity(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.isCharity(user, domainName);
    }

    public DomainCountForContactSearchResultVO findDomainCountForContact(AuthenticatedUserVO user,
            DomainCountForContactSearchCriteriaVO criteria) throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.findDomainCountForContact(user, criteria);
    }

}
