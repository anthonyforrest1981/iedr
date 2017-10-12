package pl.nask.crs.api.common;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketSource;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
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
import pl.nask.crs.entities.OwnerType;
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

import static pl.nask.crs.api.converter.Converter.toNameserversList;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

public class CommonAppServiceEndpoint extends WsSessionAware implements CRSCommonAppService {

    private CommonAppService commonAppService;

    public void setCommonAppService(CommonAppService commonAppService) {
        this.commonAppService = commonAppService;
    }

    @Override
    public long registerDomain(AuthenticatedUserVO user, RegistrationRequestVO request, CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, NicHandleException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            AuthenticationException, TooManyTicketsException, ExportException, DepositNotFoundException,
            OwnerTypeNotExistException {
        ValidationHelper.validate(user);
        validateSession(user);
        if (creditCard != null)
            ValidationHelper.validate(creditCard);
        request.setTicketSource(TicketSource.CONSOLE);
        return commonAppService.registerDomain(user, request, creditCard == null ? null : creditCard.toCreditCard());
    }

    @Override
    public long transferDomain(AuthenticatedUserVO user, TransferRequestVO request, CreditCardVO creditCard)
            throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, InvalidAuthCodeException,
            TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, EmptyRemarkException,
            AuthenticationException, SessionExpiredException, DomainIllegalStateException, TooManyTicketsException,
            ExportException, DepositNotFoundException {
        ValidationHelper.validate(user);
        validateSession(user);
        request.setTicketSource(TicketSource.CONSOLE);
        return commonAppService.transfer(user, request, creditCard == null ? null : creditCard.toCreditCard());
    }

    @Override
    public void cancel(AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, CancelTicketException, CardPaymentException,
            AuthenticationException, SessionExpiredException, DomainNotFoundException, DomainIllegalStateException {
        ValidationHelper.validate(user);
        validateSession(user);
        commonAppService.cancelTicketAsOwner(user, ticketId);
    }

    @Override
    public Long modifyDomain(AuthenticatedUserVO user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<NameserverVO> nameservers,
            RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleException, ValidationException, DnsCheckProcessingException,
            HostNotConfiguredException, SessionExpiredException, AuthenticationException, TooManyTicketsException {
        ValidationHelper.validate(user);
        AuthenticatedUser fullUser = validateSessionAndRetrieveFullUserInfo(user);
        return commonAppService.modifyDomain(fullUser, domainName, domainHolder, adminContacts, techContacts,
                toNameserversList(nameservers), renewalMode, customerRemark);
    }

    @Override
    public void modifyNameservers(AuthenticatedUserVO user, List<String> domainNames, List<NameserverVO> nameservers,
            String hostmasterRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser fullUser = validateSessionAndRetrieveFullUserInfo(user);
        commonAppService.modifyNameservers(fullUser, domainNames, toNameserversList(nameservers), hostmasterRemark);
    }

    @Override
    public NameserverValidationVO validateNameservers(List<String> domainNames, List<NameserverVO> nameservers,
            String username, boolean sendError)
            throws AccessDeniedException, SessionExpiredException, AuthenticationException {
        try {
            commonAppService.validateNameservers(domainNames, toNameserversList(nameservers), username, sendError);
            return new NameserverValidationVO(NameserverValidationVO.Status.OK, "");
        } catch (HostNotConfiguredException e) {
            return new NameserverValidationVO(NameserverValidationVO.Status.DNS_CONFIGURATION_ERROR,
                    e.getFullOutputMessage());
        } catch (DnsCheckProcessingException e) {
            return new NameserverValidationVO(NameserverValidationVO.Status.DNS_EXEC_FAILED, e.getMessage());
        }
    }

    @Override
    public boolean isTransferPossible(AuthenticatedUserVO user, String domainName)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return commonAppService.isTransferPossible(user, domainName);
    }

    @Override
    public PaymentSummaryVO reauthoriseTransaction(AuthenticatedUserVO user, long transactionId,
            CreditCardVO creditCard)
            throws AccessDeniedException, UserNotAuthenticatedException, SessionExpiredException,
            InvalidSessionTokenException, TransactionNotFoundException, TicketNotFoundException,
            NotAdmissiblePeriodException, CardPaymentException, DomainNotFoundException, NicHandleNotFoundException,
            AuthenticationException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new PaymentSummaryVO(commonAppService.reauthoriseTransaction(user, transactionId,
                creditCard.toCreditCard()));
    }

    @Override
    public boolean verifyAuthCode(AuthenticatedUserVO user, String domainName, String authCode, int failureCount)
            throws AccessDeniedException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException {
        try {
            commonAppService.verifyAuthCode(user, domainName, authCode, failureCount);
            return true;
        } catch (InvalidAuthCodeException e) {
            return false;
        }
    }

    @Override
    public AuthCodeVO generateOrProlongAuthCode(AuthenticatedUserVO user, String domainName)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, DomainLockedException,
            AuthcodeGenerationDomainStateException {
        final AuthCode authCode = commonAppService.generateOrProlongAuthCode(user, domainName);
        return new AuthCodeVO(authCode.getAuthCode(), authCode.getValidUntil());
    }

    @Override
    public void sendAuthCodeByEmail(AuthenticatedUserVO user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AccessDeniedException, DomainLockedException, AuthcodeGenerationDomainStateException {
        commonAppService.sendAuthCodeByEmail(user, domainName);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AuthCodePortalEmailException, AuthCodePortalLimitException, IllegalArgumentException,
            DomainLockedException, AuthcodeGenerationDomainStateException {
        commonAppService.sendAuthCodeFromPortal(domainName, emailAddress);
    }

    @Override
    public ApplicationConfigurationVO getApplicationConfiguration() throws AccessDeniedException {
        return new ApplicationConfigurationVO(commonAppService.getApplicationConfiguration());
    }

    @Override
    public List<OwnerTypeVO> getOwnerTypes(AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return toOwnerTypeVOList(commonAppService.getOwnerTypes(completeUser));
    }

    private List<OwnerTypeVO> toOwnerTypeVOList(List<OwnerType> ownerTypes) {
        return Lists.transform(ownerTypes, new Function<OwnerType, OwnerTypeVO>() {
            @Override
            public OwnerTypeVO apply(OwnerType ownerType) {
                return new OwnerTypeVO(ownerType);
            }
        });
    }

}
