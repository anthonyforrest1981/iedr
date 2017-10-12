package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.RequiredParameterMissingException;

import ie.domainregistry.ieapi_registranttransferbuyrequest_1.CreateType;
import ie.domainregistry.ieapicom_1.ContactType;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class BuyRequestValidationHelper {

    public static void commandPlainCheck(CreateType command) throws IedrApiException {
        if (isEmpty(command.getDomainName())) {
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);
        }
        if (command.getHolder() == null || isEmpty(command.getHolder().getHolderName())) {
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY);
        }
        if (isEmpty(command.getHolder().getHolderType())) {
            throw new RequiredParameterMissingException(ReasonCode.HOLDER_TYPE_MANDATORY);
        }
        if (command.getMethod() == null) {
            throw new RequiredParameterMissingException(ReasonCode.INVALID_PAYMENT_METHOD);
        }
        commandPlainCheck(command.getContact());
    }

    public static void commandPlainCheck(ContactType contactType) throws IedrApiException {
        if (contactType == null || isEmpty(contactType.getName())) {
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_NAME_REQUIRED);
        }
        if (isEmpty(contactType.getAddr())) {
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_ADDRESS_REQUIRED);
        }
        if (isEmpty(contactType.getCountry())) {
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_COUNTRY_REQUIRED);
        }
        if (isEmpty(contactType.getVoice())) {
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_VOICE_IS_MANDATORY_FIELD);
        }
        if (isEmpty(contactType.getEmail())) {
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_EMAIL_IS_MANDATORY_FIELD);
        }
    }

}
