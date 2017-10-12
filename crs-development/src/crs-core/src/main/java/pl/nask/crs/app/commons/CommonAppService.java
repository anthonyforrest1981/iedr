package pl.nask.crs.app.commons;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

public interface CommonAppService {

    long registerDomain(AuthenticatedUser user, TicketRequest request, CreditCard creditCard)
            throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException, ExportException, DepositNotFoundException, OwnerTypeNotExistException;

    long transfer(AuthenticatedUser user, TicketRequest request, CreditCard creditCard)
            throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, InvalidAuthCodeException,
            IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            AccessDeniedException, EmptyRemarkException, DomainIllegalStateException, TooManyTicketsException,
            ExportException, DepositNotFoundException;

    void cancelTicketAsOwner(AuthenticatedUser user, long ticketId)
            throws TicketNotFoundException, CancelTicketException, CardPaymentException, AccessDeniedException,
            DomainNotFoundException, DomainIllegalStateException;

    /**
     * Modify domain identified by domainName param. If modified field is null no modification will be applied.
     * If success and holder or contacts are modified returns new modification ticket id.
     */
    Long modifyDomain(AuthenticatedUser user, String domainName, String domainHolder, List<String> adminContacts,
            List<String> techContacts, List<Nameserver> nameservers, RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleException, ValidationException, DnsCheckProcessingException,
            HostNotConfiguredException, TooManyTicketsException;

    void modifyNameservers(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers,
            String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException;

    void validateNameservers(List<String> domains, List<Nameserver> nameservers, String username, boolean sendError)
            throws HostNotConfiguredException, DnsCheckProcessingException, AccessDeniedException;

    void zonePublished(AuthenticatedUser user, List<String> domainNames) throws AccessDeniedException;

    void zoneUnpublished(AuthenticatedUser user, List<String> domainNames) throws AccessDeniedException;

    void zoneCommit(AuthenticatedUser user) throws AccessDeniedException;

    /**
     * Checks, if the domain may be transferred to the users account (which means, that the domain is assigned to a different account and no transfer tickets are open)
     * Does not check the DSM state
     * @param user
     * @param domainName
     * @return
     */
    boolean isTransferPossible(AuthenticatedUser user, String domainName) throws AccessDeniedException;

    void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, long ticketId)
            throws TicketNotFoundException, CardPaymentException, TicketEmailException, TransactionNotFoundException,
            DomainNotFoundException, DomainIllegalStateException, AccessDeniedException;

    PaymentSummary reauthoriseTransaction(AuthenticatedUser user, long transactionId, CreditCard creditCard)
            throws DomainNotFoundException, TransactionNotFoundException, TicketNotFoundException,
            NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException, AccessDeniedException;

    void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount)
            throws InvalidAuthCodeException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, AccessDeniedException;

    AuthCode generateOrProlongAuthCode(AuthenticatedUser user, String domainName)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, DomainLockedException,
            AuthcodeGenerationDomainStateException;

    void sendAuthCodeByEmail(AuthenticatedUser user, String domainName)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, DomainLockedException,
            AuthcodeGenerationDomainStateException;

    void sendAuthCodeFromPortal(String domainName, String emailAddress)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AuthCodePortalEmailException, AuthCodePortalLimitException,
            DomainLockedException, AuthcodeGenerationDomainStateException;

    ApplicationConfiguration getApplicationConfiguration();

    OwnerType getOwnerTypeByName(AuthenticatedUser user, String name) throws AccessDeniedException,
            OwnerTypeNotExistException;

    List<OwnerType> getOwnerTypes(AuthenticatedUser user) throws AccessDeniedException;

}
