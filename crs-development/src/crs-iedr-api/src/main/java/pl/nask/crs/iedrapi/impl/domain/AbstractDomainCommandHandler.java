package pl.nask.crs.iedrapi.impl.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.commons.TicketSource;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.commons.register.CharityRegistrationNotPossibleException;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.AuthcodeGenerationDomainStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.RenewalModeUnableToModifyException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.xml.Constants;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import ie.domainregistry.ieapi_domain_1.ContactType;
import ie.domainregistry.ieapi_domain_1.CreateType;

public abstract class AbstractDomainCommandHandler<T> extends AbstractCommandHandler implements APICommandHandler<T> {

    public static final String DOMAIN_NAMESPACE = Constants.IEAPI_DOMAIN_NAMESPACE;
    public static final Logger LOG = Logger.getLogger(AbstractDomainCommandHandler.class);

    protected void validateAccountId(long id, Domain domain) throws IedrApiException {
        if (id != domain.getResellerAccount().getId())
            throw new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name",
                    DOMAIN_NAMESPACE, domain.getName()));
    }

    protected Domain findDomain(AuthenticatedUserVO user, String domainName) throws IedrApiException {
        return findDomain(user, domainName, DOMAIN_NAMESPACE, "name");
    }

    protected boolean isCharityDomain(String domainName) throws DomainNotFoundException {
        Domain d = getHelper().getDomainUnsafe(domainName);
        return (d.getDsmState().getDomainHolderType() == DomainHolderType.Charity);
    }

    protected IedrApiException mapException(Exception ee) {
        try {
            throw ee;
        } catch (OwnerTypeNotExistException e) {
            return new ParameterValueRangeErrorException(ReasonCode.HOLDER_TYPE_DOESNT_EXIST, new Value("holderType",
                    DOMAIN_NAMESPACE, e.getOwnerType()), e);
        } catch (DomainNameExistsOrPendingException e) {
            return new ObjectExistException(ReasonCode.DOMAIN_NAME_EXISTS_OR_PENDING, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainAlreadyManagedByResellerException e) {
            return new DataManagementPolicyViolationException(ReasonCode.DOMAIN_ALREADY_MANAGED_BY_RESELLER, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (TooFewNameserversException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_DNS, e);
        } catch (TooManyNameserversException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_DNS, e);
        } catch (DuplicatedNameserverException e) {
            return new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_DNS_NAME, new Value("nsName",
                    DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (NameserverNameSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR, new Value("nsName",
                    DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (IpSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.IP_SYTAX_ERROR, new Value("nsAddr", DOMAIN_NAMESPACE,
                    e.getIpAddress()), e);
        } catch (GlueNotAllowedException e) {
            return new DataManagementPolicyViolationException(ReasonCode.GLUE_NOT_ALLOWED, new Value("nsName",
                    DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (GlueRequiredException e) {
            return new DataManagementPolicyViolationException(ReasonCode.GLUE_IS_REQUIRED, new Value("nsName",
                    DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (TooFewContactsException e) {
            return DomainValidationHelper.mapExceptionForTooFewContacts(e);
        } catch (TooManyContactsException e) {
            return DomainValidationHelper.mapExceptionForTooManyContacts(e);
        } catch (DuplicatedContactException e) {
            return new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_CONTACT_ID, new Value("contact",
                    DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (ContactSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.CONTACT_ID_SYNTAX_ERROR, new Value("contact",
                    DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (NicHandleNotFoundException e) {
            return new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact",
                    DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (DomainNameSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.DOMAIN_NAME_SYNTAX_ERROR, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainNameMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY, e);
        } catch (DomainHolderMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY, e);
        } catch (HolderRemarkTooLongException e) {
            return new RequiredParameterMissingException(ReasonCode.HOLDER_REMARK_TOO_LONG, e);
        } catch (DomainManagedByAnotherResellerException e) {
            return new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainModificationPendingException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_PENDING, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainTransferPendingException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_TRANSFER_PENDING, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainNotFoundException e) {
            return new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (NotAdmissiblePeriodException e) {
            return new ParamValuePolicyErrorException(ReasonCode.PERIOD_VALUE_NOT_ADMISSIBLE, e);
        } catch (CharityRegistrationNotPossibleException e) {
            return new ParamValuePolicyErrorException(ReasonCode.CHARITY_REGISTRATION_NOT_POSSIBLE, e);
        } catch (DomainNotBillableException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainLockedException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (SellRequestExistsException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (RenewalModeUnableToModifyException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (AuthcodeGenerationDomainStateException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.CANNOT_GENERATE_AUTHCODE_FOR_DOMAIN,
                    new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainInNRPException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIsCharityException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION,
                    new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIllegalStateException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION,
                    new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (HostNotConfiguredException e) {
            return new ParamValuePolicyErrorException(ReasonCode.HOST_NOT_CONFIGURED_FOR_DOMAIN, e.getFatalMessage());
        } catch (AccessDeniedException e) {
            return new AuthorizationErrorException(e);
        } catch (DomainIsNotCharityException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.CHARITY_TRANSFER_NOT_POSSIBLE, new Value(
                    "name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (InvalidAuthCodeException e) {
            return new AuthorizationErrorException(ReasonCode.INVALID_AUTHCODE, e);
        } catch (NicHandleRecreateException e) {
            return new DataManagementPolicyViolationException(ReasonCode.ADMIN_CONTACT_CANNOT_BE_DUPLICATED, e);
        } catch (CharityCodeTooLongException e) {
            return new ParameterValueRangeErrorException(ReasonCode.INVALID_CHARITY_CODE);
        } catch (Exception e) {
            return new CommandFailed(e);
        }
    }

    public IedrApiException mapPaymentException(CardPaymentException e) {
        return new CommandFailed(e);
    }

    public RegistrationRequestVO prepareRegistrationRequest(AuthenticatedUser user, CreateType command)
            throws HolderClassNotExistException, HolderCategoryNotExistException, NameserverNameSyntaxException,
            OwnerTypeNotExistException, AccessDeniedException {
        RegistrationRequestVO request = new RegistrationRequestVO();
        request.setDomainName(command.getName());
        request.setDomainHolder(command.getHolder().getHolderName());
        long ownerTypeId = getCommonAppService().getOwnerTypeByName(user, command.getHolder().getHolderType()).getId();
        request.setDomainOwnerTypeId(ownerTypeId);

        String remark = command.getHolder().getHolderRemarks();
        if (remark == null || remark.equals("")) {
            remark = "Ticket created via API";
        }
        request.setRequestersRemark(remark);

        List<String> adminContacts = new ArrayList<>();
        for (ContactType contact : command.getContact()) {
            switch (contact.getType()) {
                case ADMIN:
                    adminContacts.add(contact.getValue());
                    break;
                case TECH:
                    request.setTechContactNicHandle(contact.getValue());
                    break;
            }
        }
        request.setAdminContactNicHandles(adminContacts);

        DomainConversionHelper.updateNs(request, command.getNs());

        if (command.getPeriod() != null) {
            request.setPeriod(command.getPeriod().getValue());
            request.setPeriodType(PeriodType.valueOf(command.getPeriod().getUnit().name()));
        }

        boolean isCharity = !Validator.isEmpty(command.getChy());
        if (isCharity) {
            request.setCharityCode(command.getChy());
        }
        if (command.getPayFromDeposit() != null) {
            request.setAutorenewMode(command.getPayFromDeposit().isAutorenewEnabled());
        }

        request.setTicketSource(TicketSource.API);

        return request;

    }

    public AppDataType prepareResponse(String domainName, long ticketId) {
        AppDataType res = new AppDataType();
        res.setName(domainName);
        res.setAppNumber(BigInteger.valueOf(ticketId));

        Calendar c = Calendar.getInstance();
        res.setAppDate(c.getTime());
        c.add(Calendar.DATE, getServicesRegistry().getApplicationConfig().getTicketExpirationPeriod());
        res.setExDate(c.getTime());
        return res;
    }

    protected void adjustDomainRenewalDateWithReservationData(AuthenticatedUser user, Domain domain)
            throws AccessDeniedException {
        domain.setRenewalDate(getRenewalDateOfReservationData(user, domain.getName(), domain.getRenewalDate()));
    }

    protected Date getRenewalDateOfReservationData(AuthenticatedUser user, String domainName, Date initialDate)
            throws AccessDeniedException {
        // API should pretend that renewal dates are already moved if payment
        // is just waiting for invoicing job (ready for settlement)
        final int offset = 0;
        final int limit = Integer.MAX_VALUE;
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setCancelled(false);
        LimitedSearchResult<Reservation> reservations = getPaymentAppService().findDomainReservations(user, domainName,
                criteria, offset, limit, null);
        int months = 0;
        for (Reservation reservation : reservations.getResults()) {
            months += reservation.getDurationMonths();
        }
        return DateUtils.addMonths(initialDate, months);
    }

}
