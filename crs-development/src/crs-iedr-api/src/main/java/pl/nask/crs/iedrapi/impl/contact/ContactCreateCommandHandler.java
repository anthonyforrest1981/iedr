package pl.nask.crs.iedrapi.impl.contact;

import java.util.Date;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
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
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.CreDataType;
import ie.domainregistry.ieapi_contact_1.CreateType;

public class ContactCreateCommandHandler extends AbstractContactCommandHandler<CreateType> {

    @Override
    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ContactValidationHelper.check(command);
        ApiValidator.assertNoError(callback);
        NewNicHandle nnh = new NewNicHandle(command.getName(), command.getCompanyName(), command.getEmail(),
                command.getAddr(), findCountryId(command.getCountry(), CONTACT_NAMESPACE),
                findCountyId(command.getCounty(), CONTACT_NAMESPACE),
                TypeConverter.stringToListNoDuplicates(command.getVoice()),
                TypeConverter.stringToListNoDuplicates(command.getFax()), null,
                null);

        String hostmastersRemark = "Created - via API";
        try {
            NicHandle createdNH = null;
            try {
                createdNH = getNicHandleAppService().createNicHandleOwnAccount(auth.getUser(), nnh, hostmastersRemark,
                        false);
            } catch (NicHandleEmailException e) {
                createdNH = getNicHandleAppService().get(auth.getUser(), e.getNicHandleId());
            }
            CreDataType res = new CreDataType();
            res.setId(createdNH.getNicHandleId());
            Date creationDate = createdNH.getRegistrationDate();
            res.setCrDate(creationDate);
            return ResponseTypeFactory.success(res);
        } catch (AccountNotActiveException e) {
            // can not add nichandle to inactive account
            throw new DataManagementPolicyViolationException(ReasonCode.INACTIVE_ACCOUNT);
        } catch (CountyRequiredException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_IS_REQUIRED_FOR_THE_COUNTRY, new Value("county",
                    CONTACT_NAMESPACE, command.getCounty()));
        } catch (InvalidCountyException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_MATCH_COUNTRY, new Value("county",
                    CONTACT_NAMESPACE, command.getCounty()));
        } catch (Exception e) {
            throw new CommandFailed(e);
        }
    }

}
