package pl.nask.crs.iedrapi.impl;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.County;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.xml.Constants;

import static pl.nask.crs.xml.Constants.IEAPICOM_NAMESPACE;

public class AbstractCommandHandler {
    private CommandHandlerHelper helper;

    public CommandHandlerHelper getHelper() {
        return helper;
    }

    public void setHelper(CommandHandlerHelper helper) {
        this.helper = helper;
    }

    public long getAccountId(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        return helper.getAccountId(user);
    }

    public String getAccountName(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        return helper.getAccountName(user);
    }

    public void validatePage(long total, long offset) throws ParameterValueRangeErrorException {
        helper.validatePage(total, offset);
    }

    public NicHandleAppService getNicHandleAppService() {
        return helper.getNicHandleAppService();
    }

    public AuthenticationService getAuthService() {
        return helper.getAuthService();
    }

    public DomainAppService getDomainAppService() {
        return helper.getDomainAppService();
    }

    public PaymentAppService getPaymentAppService() {
        return helper.getPaymentAppService();
    }

    public TicketAppService getTicketAppService() {
        return helper.getTicketAppService();
    }

    public Ticket getTicketForDomain(AuthenticatedUser user, String domainName)
            throws CommandFailed, AuthorizationErrorException {
        try {
            return helper.getTicketForDomain(user, domainName);
        } catch (AccessDeniedException e) {
            throw new AuthorizationErrorException(ReasonCode.TICKET_IS_MANAGED_BY_ANOTHER_RESELLER,
                    new Value("name", Constants.IEAPI_TICKET_NAMESPACE, domainName));
        }
    }

    public CommonAppService getCommonAppService() {
        return helper.getCommonAppService();
    }

    public SecondaryMarketAppService getSecondaryMarketAppService() {
        return helper.getSecondaryMarketAppService();
    }

    public ServicesRegistry getServicesRegistry() {
        return helper.getServicesRegistry();
    }

    protected int findCountryId(String countryName, String commandNamespace) throws ParamValuePolicyErrorException {
        try {
            return getHelper().getCountryFactory().getCountryByName(countryName).getId();
        } catch (InvalidCountryException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTRY_DOES_NOT_EXIST, new Value("country",
                    commandNamespace, countryName));
        }
    }

    protected int findCountyId(String countyName, String commandNamespace) throws ParamValuePolicyErrorException {
        if (Validator.isEmpty(countyName)) {
            return County.NOT_SPECIFIED;
        }
        try {
            return getHelper().getCountryFactory().getCountyByName(countyName).getId();
        } catch (InvalidCountyException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_EXIST, new Value("county",
                    commandNamespace, countyName));
        }
    }

    protected long findOwnerTypeId(AuthenticatedUserVO user, String ownerTypeName)
            throws ParameterValueRangeErrorException, AuthorizationErrorException {
        try {
            return getCommonAppService().getOwnerTypeByName(user, ownerTypeName).getId();
        } catch (OwnerTypeNotExistException e) {
            throw new ParameterValueRangeErrorException(ReasonCode.HOLDER_TYPE_DOESNT_EXIST,
                    new Value("holderType", IEAPICOM_NAMESPACE, ownerTypeName), e);
        } catch (AccessDeniedException e) {
            throw new AuthorizationErrorException(e);
        }
    }

    protected Domain findDomain(AuthenticatedUserVO user, String domainName, String commandNamespace, String fieldName)
            throws IedrApiException {
        if (Validator.isEmpty(domainName))
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);
        try {
            Domain d = getDomainAppService().viewPlain(user, domainName);
            if (d.getDsmState().getNrpStatus() == NRPStatus.Deleted) {
                throw new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value(fieldName,
                        commandNamespace, domainName));
            } else {
                return d;
            }
        } catch (DomainNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value(fieldName,
                    commandNamespace, domainName));
        } catch (AccessDeniedException e) {
            throw new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER,
                    new Value(fieldName, commandNamespace, domainName));
        }
    }

}
