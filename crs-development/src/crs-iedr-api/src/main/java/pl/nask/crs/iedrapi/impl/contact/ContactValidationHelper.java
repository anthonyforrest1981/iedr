package pl.nask.crs.iedrapi.impl.contact;

import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.RequiredParameterMissingException;

import ie.domainregistry.ieapi_contact_1.CreateType;
import ie.domainregistry.ieapi_contact_1.ModifyType;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class ContactValidationHelper {

    public static void check(CreateType command) throws IedrApiException {
        if (isEmpty(command.getName()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_NAME_REQUIRED);

        if (isEmpty(command.getAddr()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_ADDRESS_REQUIRED);

        if (isEmpty(command.getCountry()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_COUNTRY_REQUIRED);

        if (isEmpty(command.getVoice()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_VOICE_IS_MANDATORY_FIELD);

        if (isEmpty(command.getEmail()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_EMAIL_IS_MANDATORY_FIELD);
    }

    public static void check(ModifyType command) throws IedrApiException {
        if (isEmpty(command.getAddr()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_ADDRESS_REQUIRED);

        if (isEmpty(command.getCountry()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_COUNTRY_REQUIRED);

        if (isEmpty(command.getVoice()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_VOICE_IS_MANDATORY_FIELD);

        if (isEmpty(command.getEmail()))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_EMAIL_IS_MANDATORY_FIELD);

    }

}
