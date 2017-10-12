package pl.nask.crs.api.common;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSCommonAppService {

    /**
     * @returns Id of the registration ticket
     */
    @WebMethod
    long registerDomain(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "registrationRequest") RegistrationRequestVO request,
            @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, NicHandleException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            AuthenticationException, TooManyTicketsException, ExportException, IncorrectUtf8FormatException,
            DepositNotFoundException, OwnerTypeNotExistException;

    @WebMethod
    long transferDomain(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "registrationRequest") TransferRequestVO request,
            @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, InvalidAuthCodeException,
            TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, AccessDeniedException,
            EmptyRemarkException, AuthenticationException, SessionExpiredException, DomainIllegalStateException,
            TooManyTicketsException, ExportException, IncorrectUtf8FormatException, DepositNotFoundException;

    @WebMethod
    boolean isTransferPossible(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    void cancel(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(name = "ticketId") long ticketId)
            throws AccessDeniedException, TicketNotFoundException, CancelTicketException, CardPaymentException,
            AuthenticationException, SessionExpiredException, DomainNotFoundException, DomainIllegalStateException,
            IncorrectUtf8FormatException;

    @WebMethod
    Long modifyDomain(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName, @WebParam(name = "domainHolder") String domainHolder,
            @WebParam(name = "adminContacts") List<String> adminContacts,
            @WebParam(name = "techContacts") List<String> techContacts,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers,
            @WebParam(name = "renewalMode") RenewalMode renewalMode,
            @WebParam(name = "customerRemark") String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleException, ValidationException, DnsCheckProcessingException,
            HostNotConfiguredException, SessionExpiredException, AuthenticationException, TooManyTicketsException,
            IncorrectUtf8FormatException;

    @WebMethod
    void modifyNameservers(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainName,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers,
            @WebParam(name = "hostmasterRemark") String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    NameserverValidationVO validateNameservers(@WebParam(name = "domainNames") List<String> domainName,
            @WebParam(name = "nameservers") List<NameserverVO> nameservers,
            @WebParam(name = "username") String username, @WebParam(name = "sendError") boolean sendError)
            throws AccessDeniedException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    PaymentSummaryVO reauthoriseTransaction(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "transactionId") long transactionId, @WebParam(name = "creditCard") CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, NicHandleNotFoundException, TransactionNotFoundException,
            TicketNotFoundException, NotAdmissiblePeriodException, CardPaymentException, DomainNotFoundException,
            AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    boolean verifyAuthCode(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName, @WebParam(name = "authCode") String authCode, @WebParam(
                    name = "failureCount") int failureCount)
            throws AccessDeniedException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, IncorrectUtf8FormatException;

    @WebMethod
    AuthCodeVO generateOrProlongAuthCode(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "domainName") String domainName)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, IncorrectUtf8FormatException, DomainLockedException,
            AuthcodeGenerationDomainStateException;

    @WebMethod
    void sendAuthCodeByEmail(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AccessDeniedException, IncorrectUtf8FormatException, DomainLockedException,
            AuthcodeGenerationDomainStateException;

    @WebMethod
    void sendAuthCodeFromPortal(@WebParam(name = "domainName") String domainName,
            @WebParam(name = "emailAddress") String emailAddress)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AuthCodePortalEmailException, AuthCodePortalLimitException, IncorrectUtf8FormatException,
            DomainLockedException, AuthcodeGenerationDomainStateException;

    @WebMethod
    ApplicationConfigurationVO getApplicationConfiguration() throws AccessDeniedException, IncorrectUtf8FormatException;

    @WebMethod
    List<OwnerTypeVO> getOwnerTypes(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;
}
