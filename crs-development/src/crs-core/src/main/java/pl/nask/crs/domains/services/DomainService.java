package pl.nask.crs.domains.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exceptions.WhoisProcessingException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.country.Country;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DomainService {

    Domain lock(String domainName) throws DomainNotFoundException;

    void save(Domain domain, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleException, ValidationException, EmptyRemarkException,
            ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    void updateDomainAndHistory(Domain domain, OpInfo opInfo);

    void updateDomainAndHistory(Domain domain, int targetState, OpInfo opInfo);

    void enterVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames)
            throws DomainNotFoundException, DomainIllegalStateException;

    void removeFromVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames)
            throws DomainNotFoundException, DomainIllegalStateException;

    boolean isEventValid(String domainName, DsmEventName eventName);

    void zonePublished(List<String> domainNames);

    void zoneUnpublished(List<String> domainNames);

    void zoneCommit();

    void runRenewalDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    void runSuspensionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    void runDeletionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    void runDeletedDomainRemoval(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException;

    void forceDSMState(List<String> domainNames, int state, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException;

    List<Integer> getDsmStates();

    void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType, OpInfo opInfo)
            throws EmptyRemarkException, DomainIllegalStateException, ReservationPendingException,
            DomainNotFoundException;

    void unlock(AuthenticatedUser user, String domainName, OpInfo opInfo, boolean disableLockingService)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException, EmailSendingException,
            TemplateNotFoundException, TemplateInstantiatingException;

    void lock(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException, EmailSendingException,
            TemplateNotFoundException, TemplateInstantiatingException, DomainModificationPendingException,
            DomainTransferPendingException, TooManyTicketsException;

    void rollLockRenewalDates(Map<String, Date> domainsNewLockingRenewalDates, OpInfo opInfo)
            throws EmailSendingException, TemplateNotFoundException, TemplateInstantiatingException,
            DomainIllegalStateException, DomainLockingRenewalDateOutOfBoundsException;

    void revertToBillable(AuthenticatedUser user, List<String> domainNames, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    void sendNotification(Domain domain, AuthenticatedUser user, int period) throws DomainEmailException;

    void sendNotification(Domain domain, AuthenticatedUser user) throws DomainEmailException;

    List<String> checkEventAvailable(List<String> domainNames, DsmEventName eventName);

    void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException;

    List<Country> getCountries();

    void modifyRemark(AuthenticatedUser user, String domainName, String remark)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException;

    void sendAuthCodeByEmail(String domainName, OpInfo opInfo)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException, AuthcodeGenerationDomainStateException;

    void sendAuthCodeFromPortal(String domainName, String emailAddress, OpInfo opInfo)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AuthCodePortalLimitException, DomainLockedException,
            AuthcodeGenerationDomainStateException;

    void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode)
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException;

    void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount)
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException;

    AuthCode getOrCreateAuthCode(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException, AuthcodeGenerationDomainStateException;

    void clearAuthCode(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException;

    void clearAuthCodePortalCount(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException;

    void sendBulkAuthCodesByEmail(AuthenticatedUser user, Contact billingContact, Contact adminContact,
            List<Domain> domainList)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException;

    void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC);

    void updateNameserversForDomainList(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers,
            String customerRemark)
            throws NicHandleNotFoundException, NicHandleNotActiveException, DomainNotFoundException,
            EmptyRemarkException, DomainLockedException;

    void create(NewDomain newDomain, OpInfo opInfo);

    void delete(AuthenticatedUser user, Domain domain, OpInfo opInfo);

    void sendWhoisDataEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, IOException,
            InterruptedException, WhoisProcessingException;

    void assertNoPendingReservations(String domainName, String errorMsg) throws ReservationPendingException;

}
