package pl.nask.crs.api.common;

import java.util.List;

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
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidSessionTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

@WebService(
    serviceName = "CRSCommonAppService",
    endpointInterface = "pl.nask.crs.api.common.CRSCommonAppService",
    portName = "CRSCommonAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSCommonAppServiceProxy implements CRSCommonAppService {
    private CRSCommonAppService service;

    public void setService(CRSCommonAppService service) {
        this.service = service;
    }

    @Override
    public long registerDomain(AuthenticatedUserVO user, RegistrationRequestVO request, CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, NicHandleException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            AuthenticationException, TooManyTicketsException, IncorrectUtf8FormatException, ExportException,
            DepositNotFoundException, OwnerTypeNotExistException {
        return service.registerDomain(user, request, creditCard);
    }

    @Override
    public long transferDomain(AuthenticatedUserVO user, TransferRequestVO request, CreditCardVO creditCard)
            throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, InvalidAuthCodeException,
            TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, EmptyRemarkException,
            AuthenticationException, SessionExpiredException, DomainIllegalStateException, TooManyTicketsException,
            IncorrectUtf8FormatException, ExportException, DepositNotFoundException {
        return service.transferDomain(user, request, creditCard);
    }

    @Override
    public boolean isTransferPossible(AuthenticatedUserVO user, String domainName)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.isTransferPossible(user, domainName);
    }

    @Override
    public void cancel(AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, CancelTicketException, CardPaymentException,
            AuthenticationException, SessionExpiredException, DomainNotFoundException, DomainIllegalStateException,
            IncorrectUtf8FormatException {
        service.cancel(user, ticketId);
    }

    @Override
    public Long modifyDomain(AuthenticatedUserVO user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<NameserverVO> nameservers,
            RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleException, ValidationException, DnsCheckProcessingException,
            HostNotConfiguredException, SessionExpiredException, AuthenticationException, TooManyTicketsException,
            IncorrectUtf8FormatException {
        return service.modifyDomain(user, domainName, domainHolder, adminContacts, techContacts, nameservers,
                renewalMode, customerRemark);
    }

    @Override
    public void modifyNameservers(AuthenticatedUserVO user, List<String> domainName, List<NameserverVO> nameservers,
            String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        service.modifyNameservers(user, domainName, nameservers, hostmasterRemark);
    }

    @Override
    public NameserverValidationVO validateNameservers(List<String> domainName, List<NameserverVO> nameservers,
            String username, boolean sendError)
            throws AccessDeniedException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.validateNameservers(domainName, nameservers, username, sendError);
    }

    @Override
    public PaymentSummaryVO reauthoriseTransaction(AuthenticatedUserVO user, long transactionId, CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, NicHandleNotFoundException, TransactionNotFoundException,
            TicketNotFoundException, NotAdmissiblePeriodException, CardPaymentException, DomainNotFoundException,
            AuthenticationException, IncorrectUtf8FormatException {
        return service.reauthoriseTransaction(user, transactionId, creditCard);
    }

    @Override
    public boolean verifyAuthCode(AuthenticatedUserVO user, String domainName, String authCode, int failureCount)
            throws AccessDeniedException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException, IncorrectUtf8FormatException {
        return service.verifyAuthCode(user, domainName, authCode, failureCount);
    }

    @Override
    public AuthCodeVO generateOrProlongAuthCode(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, IncorrectUtf8FormatException, DomainLockedException,
            AuthcodeGenerationDomainStateException {
        return service.generateOrProlongAuthCode(user, domainName);
    }

    @Override
    public void sendAuthCodeByEmail(AuthenticatedUserVO user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AccessDeniedException, IncorrectUtf8FormatException, DomainLockedException,
            AuthcodeGenerationDomainStateException {
        service.sendAuthCodeByEmail(user, domainName);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AuthCodePortalEmailException, AuthCodePortalLimitException, IncorrectUtf8FormatException,
            DomainLockedException, AuthcodeGenerationDomainStateException {
        service.sendAuthCodeFromPortal(domainName, emailAddress);
    }

    @Override
    public ApplicationConfigurationVO getApplicationConfiguration()
            throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.getApplicationConfiguration();
    }

    @Override
    public List<OwnerTypeVO> getOwnerTypes(AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.getOwnerTypes(user);
    }

}
