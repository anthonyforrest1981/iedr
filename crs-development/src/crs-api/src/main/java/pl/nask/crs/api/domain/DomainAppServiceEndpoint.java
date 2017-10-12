package pl.nask.crs.api.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.*;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.domains.RenewalDateType;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.*;

/**
 * Endpoint for the {@link DomainAppService}
 *
 * @author Artur Gniadzik
 *
 */
public class DomainAppServiceEndpoint extends WsSessionAware implements CRSDomainAppService {

    private DomainAppService service;

    public void setService(DomainAppService service) {
        this.service = service;
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.domain.CRSDomainAppService#view(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String)
    */
    @Override
    public ExtendedDomainInfoVO view(AuthenticatedUserVO user, String domainName)
            throws AccessDeniedException, DomainNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(domainName, "domain.name");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        ExtendedDomainInfoVO ret = new ExtendedDomainInfoVO(service.view(completeUser, domainName));
        setAdditionalFlags(user, ret.getDomain());
        return ret;
    }

    private void setAdditionalFlags(AuthenticatedUserVO user, DomainVO domain) throws AccessDeniedException {
        if (domain.getDsmState().isVoluntaryNRP()) {
            boolean flag = service.isEventValid(user, domain.getName(), DsmEventName.RemoveFromVoluntaryNRP);
            domain.setRemoveFromVoluntaryNRPPossible(flag);
        }
        boolean flag = service.isEventValid(user, domain.getName(), DsmEventName.EnterVoluntaryNRP);
        domain.setEnterVoluntaryNRPPossible(flag);
    }

    @Override
    public DomainSearchResultVO findDomains(AuthenticatedUserVO user, DomainSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            GenericValidationException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new DomainSearchResultVO(service.findOwn(user, criteria.toDomainSearchCriteria(), offset, limit,
                orderBy));
    }

    @Override
    public PlainDomainSearchResultVO findPlainDomains(AuthenticatedUserVO user, PlainDomainSearchCriteriaVO criteria,
            long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PlainDomainSearchResultVO(service.findOwnPlain(user, criteria.toPlainDomainSearchCriteria(),
                offset, limit, orderBy));
    }

    @Override
    public ExtendedDomainSearchResultVO findExtendedDomains(AuthenticatedUserVO user,
            ExtendedDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, GenericValidationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ExtendedDomainSearchResultVO(service.findOwnExtended(user,
                criteria.toExtendedDomainSearchCriteria(), offset, limit, orderBy));
    }

    @Override
    public PlainDomainSearchResultVO findDeletedDomains(AuthenticatedUserVO user,
            DeletedDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PlainDomainSearchResultVO(service.findOwnDeletedDomains(user, criteria.toSearchCriteria(),
                offset, limit, orderBy));
    }

    @Override
    public PlainDomainSearchResultVO findTransferredInDomains(AuthenticatedUserVO user,
            PlainDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PlainDomainSearchResultVO(service.findTransferredInDomains(user,
                criteria.toPlainDomainSearchCriteria(), offset, limit, sortBy));
    }

    @Override
    public PlainDomainSearchResultVO findTransferredAwayDomains(AuthenticatedUserVO user,
            PlainDomainSearchCriteriaVO criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PlainDomainSearchResultVO(service.findTransferredAwayDomains(user,
                criteria.toPlainDomainSearchCriteria(), offset, limit, sortBy));
    }

    @Override
    public ExtendedDomainSearchResultVO findDomainsForCurrentRenewal(AuthenticatedUserVO user,
            RenewalDateType renewalDateType, ExtendedDomainSearchCriteriaVO criteriaVO, long offset, long limit,
            List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, AuthenticationException,
            SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        Validator.assertNotNull(renewalDateType, "renewalDateType");
        return new ExtendedDomainSearchResultVO(service.findDomainsForCurrentRenewal(user, renewalDateType,
                criteriaVO.toExtendedDomainSearchCriteria(), offset, limit, sortBy));
    }

    @Override
    public ExtendedDomainSearchResultVO findDomainsForFutureRenewal(AuthenticatedUserVO user, int month,
            ExtendedDomainSearchCriteriaVO criteriaVO, long offset, long limit,
            List<SortCriterion> sortBy)
            throws DomainNotFoundException, NotAdmissiblePeriodException, SessionExpiredException,
            AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new ExtendedDomainSearchResultVO(service.findDomainsForFutureRenewal(user, month,
                criteriaVO.toExtendedDomainSearchCriteria(), offset, limit, sortBy));
    }

    @Override
    public void enterVoluntaryNRP(AuthenticatedUserVO user, String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.enterVoluntaryNRP(user, domainNames);
    }

    @Override
    public void removeFromVoluntaryNRP(AuthenticatedUserVO user, String... domainNames)
            throws SessionExpiredException, AuthenticationException, DomainNotFoundException,
            DomainIllegalStateException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.removeFromVoluntaryNRP(user, domainNames);
    }

    @Override
    public boolean isEventValid(AuthenticatedUserVO user, String domainName, DsmEventName eventName)
            throws SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return service.isEventValid(user, domainName, eventName);
    }

    @Override
    public DomainAvailabilityVO checkAvailability(AuthenticatedUserVO user, String domainName)
            throws AccessDeniedException {
        return new DomainAvailabilityVO(service.checkAvailability(user, domainName));
    }

    @Override
    public void revertToBillable(AuthenticatedUserVO user, List<String> domainNames)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException {
        service.revertToBillable(user, domainNames);
    }

    @Override
    public NsReportSearchResultVO getNsReports(AuthenticatedUserVO user, NsReportSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> sortBy) throws SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new NsReportSearchResultVO(service.getNsReports(user, criteria.toSearchCriteria(), offset, limit, sortBy));
    }

    @Override
    public List<String> checkPayAvailable(AuthenticatedUserVO user, List<String> domainNames)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        List<String> res = new LinkedList<String>();
        for (String domainName : domainNames) {
            try {
                List<String> partialRes = service.checkPayAvailable(user, Arrays.asList(domainName));
                res.addAll(partialRes);
            } catch (AccessDeniedException e) {
                res.add(domainName + " (user has no permission for paying for this domain)");
            }
        }
        return res;
    }

    @Override
    public List<String> checkModificationAvailable(AuthenticatedUserVO user, List<String> domainNames,
            boolean isRenewalModeChange)
            throws AuthenticationException, SessionExpiredException, NicHandleNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        List<String> res = new LinkedList<String>();
        for (String domainName : domainNames) {
            try {
                service.validateDomainToModify(user, domainName, isRenewalModeChange);
            } catch (AccessDeniedException e) {
                res.add(domainName + " (user has no permission for this domain)");
            } catch (DomainLockedException e) {
                res.add(domainName + " (domain is locked)");
            } catch (SellRequestExistsException e) {
                res.add(domainName + " (sell request exists for the domain)");
            } catch (DomainManagedByAnotherResellerException e) {
                res.add(domainName + " (domain managed by another reseller)");
            } catch (DomainNotBillableException e) {
                res.add(domainName + " (domain not billable)");
            } catch (DomainNotFoundException e) {
                res.add(domainName + " (domain does not exist)");
            }
        }
        return res;
    }

    @Override
    public void modifyRenewalMode(AuthenticatedUserVO user, List<String> domainNames, RenewalMode renewalMode)
            throws AuthenticationException, SessionExpiredException, DomainNotFoundException,
            DomainIllegalStateException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.modifyRenewalMode(user, domainNames, renewalMode);
    }

    @Override
    public List<CountryVO> getCountries(AuthenticatedUserVO user) {
        // user MAY be null here - this method provides static data only
        return toCountriesVOList(service.getCountries(user));
    }

    private List<CountryVO> toCountriesVOList(List<Country> countries) {
        return Lists.transform(countries, new Function<Country, CountryVO>() {
            @Override
            public CountryVO apply(Country country) {
                return new CountryVO(country);
            }
        });
    }

    private List<CountyVO> toCountiesVOList(List<County> counties) {
        return Lists.transform(counties, new Function<County, CountyVO>() {
            @Override
            public CountyVO apply(County county) {
                return new CountyVO(county);
            }
        });
    }


    @Override
    public boolean isCharity(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return service.isCharity(user, domainName);
    }

    @Override
    public DomainCountForContactSearchResultVO findDomainCountForContact(AuthenticatedUserVO user,
            DomainCountForContactSearchCriteriaVO criteria) throws AccessDeniedException {
        return new DomainCountForContactSearchResultVO(service.findDomainCountForContact(user,
                criteria.toSearchCriteria()));
    }
}
