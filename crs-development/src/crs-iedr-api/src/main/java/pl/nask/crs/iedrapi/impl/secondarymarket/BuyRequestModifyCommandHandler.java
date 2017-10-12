package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.County;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestCheckedOutException;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestFrozenAsPassed;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.ModifyType;

import static pl.nask.crs.xml.Constants.IEAPICOM_NAMESPACE;

public class BuyRequestModifyCommandHandler extends AbstractSecondaryMarketCommandHandler<ModifyType> {

    @Override
    public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        long buyRequestId = command.getId().longValue();
        try {
            BuyRequest buyRequest = getSecondaryMarketAppService().getBuyRequest(auth.getUser(), buyRequestId);
            NewNicHandle contact;
            if (command.getContact() != null) {
                contact = getNewNicHandle(command.getContact());
            } else {
                final County adminCounty = buyRequest.getAdminCounty();
                contact = new NewNicHandle(buyRequest.getAdminName(), buyRequest.getAdminCompanyName(),
                        buyRequest.getAdminEmail(), buyRequest.getAdminAddress(), buyRequest.getAdminCountry().getId(),
                        adminCounty != null ? adminCounty.getId() : 0, buyRequest.getPhones(), buyRequest.getFaxes(),
                        null, null);
            }
            String holderName, remark = null;
            if (command.getHolder() != null) {
                holderName = command.getHolder().getHolderName();
                remark = command.getHolder().getHolderRemarks();
            } else {
                holderName = buyRequest.getDomainHolder();
            }
            if (Validator.isEmpty(remark)) {
                remark = "Modified with IEDR-API";
            }
            getSecondaryMarketAppService().modifyBuyRequest(auth.getUser(), buyRequestId, holderName, contact, remark);
            return ResponseTypeFactory.success();
        } catch (IedrApiException e) {
            throw e;
        } catch (Exception e) {
            throw mapException(e, command);
        }
    }

    private IedrApiException mapException(Exception ee, ModifyType command) {
        try {
            throw ee;
        } catch (BuyRequestNotFoundException e) {
            return handleBuyRequestNotFound(e.getBuyRequestId());
        } catch (BuyRequestFrozenAsPassed e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.BUY_REQUEST_ALREADY_ACCEPPTED);
        } catch (InvalidCountryException e) {
            return new ParamValuePolicyErrorException(ReasonCode.COUNTRY_DOES_NOT_EXIST,
                    new Value("country", IEAPICOM_NAMESPACE, command.getContact().getCountry()), e);
        } catch (InvalidCountyException e) {
            return new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_EXIST,
                    new Value("county", IEAPICOM_NAMESPACE, command.getContact().getCounty()), e);
        } catch (DomainHolderTooLongException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.HOLDER_NAME_TOO_LONG,
                    new Value("holderName", IEAPICOM_NAMESPACE, command.getHolder().getHolderName()), e);
        } catch (BuyRequestCheckedOutException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.BUY_REQUEST_IN_USE);
        } catch (DomainHolderMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY, e);
        } catch (InvalidEmailException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.CONTACT_EMAIL_IS_INVALID,
                    new Value("email", IEAPICOM_NAMESPACE, e.getEmail()), e);
        } catch (Exception e) {
            return new CommandFailed(e);
        }
    }
}
