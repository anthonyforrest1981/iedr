package pl.nask.crs.iedrapi.impl.domain;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.SNameType;

public class DomainInfoCommandHandler extends AbstractDomainCommandHandler<SNameType> {

    @Override
    public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        String name = command.getName();
        boolean inclAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCode());
        boolean fillIpv6 = TypeConverter.commandFieldToBoolean(command.isFillNsAddr6());
        boolean forceGen = TypeConverter.commandFieldToBoolean(command.isAuthCodeForceGeneration());

        if (forceGen) {
            try {
                getCommonAppService().generateOrProlongAuthCode(auth.getUser(), name);
            } catch (Exception e) {
                throw mapException(e);
            }
        }
        Domain domain = findDomain(auth.getUser(), name);
        validateAccountId(getAccountId(auth.getUser()), domain);
        adjustDomainRenewalDateWithReservationData(auth.getUser(), domain);
        return ResponseTypeFactory.success(DomainConversionHelper.makeInfo(domain, inclAuthCode, fillIpv6));
    }

}
