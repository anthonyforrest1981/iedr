package pl.nask.crs.app.domains;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.RelatedDomains;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.WhoisProcessingException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.country.Country;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.*;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

/**
 * @author Kasia Fulara
 */
public interface DomainAppService extends AppSearchService<Domain, DomainSearchCriteria> {

    ExtendedDomainInfo view(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException;

    Domain viewPlain(AuthenticatedUser user, String domainName) throws AccessDeniedException, DomainNotFoundException;

    List<Domain> getAll(AuthenticatedUser user, List<String> selected)
            throws AccessDeniedException, DomainNotFoundException;

    ExtendedDomainInfo edit(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException;

    void save(AuthenticatedUser user, Domain domain)
            throws AccessDeniedException, NicHandleException, DomainNotFoundException, ValidationException,
            EmptyRemarkException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    // Redeclare! it's needed for the permissions to work properly
    @Override
    LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Domain> searchFull(AuthenticatedUser user, DomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Domain> searchFullWithLockingActive(AuthenticatedUser user, DomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Domain> findOwn(AuthenticatedUser user, DomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<PlainDomain> findOwnPlain(AuthenticatedUser user, PlainDomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException;

    LimitedSearchResult<ExtendedDomain> findOwnExtended(AuthenticatedUser user, ExtendedDomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException;

    LimitedSearchResult<PlainDomain> findTransferredInDomains(AuthenticatedUser user,
            PlainDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException;

    LimitedSearchResult<PlainDomain> findTransferredAwayDomains(AuthenticatedUser user,
            PlainDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException;

    LimitedSearchResult<ExtendedDomain> findExtended(AuthenticatedUser user, ExtendedDomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException;

    LimitedSearchResult<ExtendedDomain> findDomainsForCurrentRenewal(AuthenticatedUser user,
            RenewalDateType renewalDateType, ExtendedDomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy)
                    throws AccessDeniedException;

    LimitedSearchResult<ExtendedDomain> findDomainsForFutureRenewal(AuthenticatedUser user, int month,
            ExtendedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException;

    LimitedSearchResult<NsReport> getNsReports(AuthenticatedUser user, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    void enterVoluntaryNRP(AuthenticatedUser user, String... domainName)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException;

    void removeFromVoluntaryNRP(AuthenticatedUser user, String... domainNames)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException;

    boolean isEventValid(AuthenticatedUser user, String domainName, DsmEventName eventName)
            throws AccessDeniedException;

    DomainAvailability checkAvailability(AuthenticatedUser user, String domainName) throws AccessDeniedException;

    void runDeleteProcess(AuthenticatedUser user, OpInfo opInfo, String domainName) throws AccessDeniedException;

    void runRenewalDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) throws AccessDeniedException;

    void runSuspensionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) throws AccessDeniedException;

    void runDeletionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) throws AccessDeniedException;

    void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, String remark)
            throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException, AccessDeniedException;

    void forceDSMState(AuthenticatedUser user, List<String> domainNames, int state, String remark)
            throws DomainNotFoundException, EmptyRemarkException, AccessDeniedException;

    public List<Integer> getDsmStates(AuthenticatedUser user) throws AccessDeniedException;

    void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, String remark)
            throws AccessDeniedException, EmptyRemarkException, DomainIllegalStateException,
            ReservationPendingException, DomainNotFoundException;

    void lock(AuthenticatedUser user, String domainName, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException,
            TemplateNotFoundException, EmailSendingException, TemplateInstantiatingException,
            DomainModificationPendingException, DomainTransferPendingException, TooManyTicketsException;

    void unlock(AuthenticatedUser user, String domainName, String hostmastersRemark, boolean disableLockingService)
            throws AccessDeniedException, EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException,
            TemplateNotFoundException, EmailSendingException, TemplateInstantiatingException;

    void rollLockRenewalDates(AuthenticatedUser user, Map<String, Date> domainsNewLockingRenewalDates)
            throws AccessDeniedException, EmailSendingException, TemplateNotFoundException,
            TemplateInstantiatingException, DomainIllegalStateException, DomainLockingRenewalDateOutOfBoundsException;

    void revertToBillable(AuthenticatedUser user, List<String> domainNames)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException;

    void runNotificationProcess(AuthenticatedUser user) throws AccessDeniedException;

    List<String> checkPayAvailable(AuthenticatedUser user, List<String> domainNames) throws AccessDeniedException;

    void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode)
            throws AccessDeniedException, DomainNotFoundException, DomainIllegalStateException;

    List<Country> getCountries(AuthenticatedUser user);

    boolean isCharity(AuthenticatedUser user, String domainName) throws AccessDeniedException, DomainNotFoundException;

    LimitedSearchResult<DeletedDomain> findDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<DeletedDomain> findOwnDeletedDomains(AuthenticatedUser user,
            DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException;

    LimitedSearchResult<Domain> findDomainAutorenewals(AuthenticatedUser user, DomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException, UserNotAuthenticatedException;

    List<Domain> findDomainsToAuthCodeCleanup(AuthenticatedUser user) throws AccessDeniedException;

    void authCodeCleanup(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainLockedException, NicHandleNotFoundException,
            NicHandleNotActiveException, DomainNotFoundException, EmptyRemarkException;

    List<Domain> findDomainsToAuthCodePortalCleanup(AuthenticatedUser user) throws AccessDeniedException;

    void authCodePortalCleanup(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainLockedException, NicHandleNotFoundException,
            NicHandleNotActiveException, DomainNotFoundException, EmptyRemarkException;

    void sendAuthCodeByEmail(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException, AuthcodeGenerationDomainStateException;

    void bulkExportAuthCodes(AuthenticatedUser user, List<String> list)
            throws BulkExportAuthCodesException, BulkExportAuthCodesTotalException, AccessDeniedException;

    List<DomainCountForContact> findDomainCountForContact(AuthenticatedUser user,
            DomainCountForContactSearchCriteria criteria) throws AccessDeniedException;

    void validateDomainToModify(AuthenticatedUser user, String domainName, boolean isRenewalModeChange)
            throws AccessDeniedException, DomainNotFoundException, NicHandleNotFoundException, DomainLockedException,
            DomainManagedByAnotherResellerException, DomainNotBillableException, SellRequestExistsException;

    void sendWhoisDataEmail(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, IOException, InterruptedException, WhoisProcessingException;

    RelatedDomains getRelatedDomains(AuthenticatedUser user, String domainHolder, int limit, String exceptDomain)
            throws AccessDeniedException;
}
