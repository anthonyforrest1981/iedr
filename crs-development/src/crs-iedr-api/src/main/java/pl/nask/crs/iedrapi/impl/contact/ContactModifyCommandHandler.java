package pl.nask.crs.iedrapi.impl.contact;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.country.CountyRequiredException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.VatModificationException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.ModifyType;

public class ContactModifyCommandHandler extends AbstractContactCommandHandler<ModifyType> {

    @Override
    public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {

        ContactValidationHelper.check(command);
        ApiValidator.assertNoError(callback);

        String nhid = command.getId();
        NicHandle nicHandle = getNicHandle(auth.getUser(), nhid);
        NewNicHandle nnh = new NewNicHandle(nicHandle.getName(), command.getCompanyName(), command.getEmail(),
                command.getAddr(), findCountryId(command.getCountry(), CONTACT_NAMESPACE),
                findCountyId(command.getCounty(), CONTACT_NAMESPACE),
                TypeConverter.stringToListNoDuplicates(command.getVoice()),
                TypeConverter.stringToListNoDuplicates(command.getFax()), null, null);

        try {
            getNicHandleAppService().modifyNicHandleOwnAccount(auth.getUser(), nhid, nnh, "Updated - via API");
        } catch (NicHandleEmailException e) {
            // no error
        } catch (CountyRequiredException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_IS_REQUIRED_FOR_THE_COUNTRY, new Value("county",
                    CONTACT_NAMESPACE, command.getCounty()));
        } catch (InvalidCountyException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_MATCH_COUNTRY, new Value("county",
                    CONTACT_NAMESPACE, command.getCounty()));
        } catch (VatModificationException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTRY_CHANGE_FORBIDDEN, new Value("county",
                    CONTACT_NAMESPACE, command.getCounty()));
        } catch (Exception e) {
            // no other exceptions should be thrown since validation was made earlier
            throw new CommandFailed(e);
        }

        return ResponseTypeFactory.success();

    }

}
