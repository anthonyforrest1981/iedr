package pl.nask.crs.iedrapi.impl.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import pl.nask.crs.app.tickets.exceptions.TooFewContactsException;
import pl.nask.crs.app.tickets.exceptions.TooManyContactsException;
import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.iedrapi.exceptions.*;

import ie.domainregistry.ieapi_domain_1.*;

import static pl.nask.crs.commons.dns.DomainNameValidator.getCanonicalName;
import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class DomainValidationHelper {

    public static void commandPlainCheck(ModifyType command) throws IedrApiException {
        if (command.getChg() != null && command.getAdd() != null && command.getRem() != null
                && command.getChg().getHolder() == null && command.getChg().getRenewalMode() == null
                && command.getAdd().getContact().size() == 0 && command.getRem().getContact().size() == 0
                && command.getAdd().getNs().size() == 0 && command.getRem().getNs().size() == 0)
            throw new DataManagementPolicyViolationException(ReasonCode.NOTHING_TO_CHANGE);
    }

    public static void commandPlainCheck(CreateType command)
            throws IedrApiException {
        // attribute unit of domain:period other than 'y' => PARAMETER_VALUE_POLICY_ERROR (Reason 157)
        if (command.getPeriod() != null && !command.getPeriod().getUnit().value().equals("y")) {
            throw new ParamValuePolicyErrorException(ReasonCode.PERIOD_UNIT_MONTH_NOT_ALLOWED);
        }

        if (command.getCreditCard() != null && command.getPayFromDeposit() != null) {
            throw new DataManagementPolicyViolationException(
                    ReasonCode.ACCOUNT_CARDHOLDER_AND_FROMDEPOSIT_CANT_BE_USED_SIMULTANEOUSLY);
        }

        // empty or missing domain:holderName => REQUIRED_PARAMETER_MISSING (Reason 151)
        if (command.getHolder() == null || isEmpty(command.getHolder().getHolderName())) {
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY);
        }

        // empty or missing domain:holderType => REQUIRED_PARAMETER_MISSING (Reason 181)
        if (isEmpty(command.getHolder().getHolderType())) {
            throw new RequiredParameterMissingException(ReasonCode.HOLDER_TYPE_MANDATORY);
        }
    }

    public static void commandPlainCheck(MNameType command) throws IedrApiException {
        // empty or missing domain:name => REQUIRED_PARAMETER_MISSING (Reason 150)
        if (command.getName() == null || command.getName().size() == 0)
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);
    }

    public static void commandPlainCheck(QueryType command) throws IedrApiException {
        // value of page attribute out of range => PARAMETER_VALUE_RANGE_ERROR (Reason 802)
        if (command.getPage() <= 0)
            throw new ParameterValueRangeErrorException(ReasonCode.ATTRIBUTE_PAGE_IS_OUT_OF_RANGE);

        if (command.getTransfer() != null) {
            if (command.getTransfer().getType() == null) {
                throw new RequiredParameterMissingException(ReasonCode.ATTRIBUTE_TYPE_IS_MANDATORY_FOR_TRANSFERS_QRY);
            }
        }
    }

    public static void commandPlainCheck(TransferType command) throws IedrApiException {
        // empty or missing domain:authCode => REQUIRED_PARAMETER_MISSING (Reason 281)
        if (isEmpty(command.getAuthCode()))
            throw new RequiredParameterMissingException(ReasonCode.AUTHCODE_IS_MANDATORY);
    }

    public static List<NsType> prepareNssValidateDelegation(ModifyType command, List<Nameserver> currentNameservers,
            String nameSpace) throws IedrApiException {
        List<NsType> newNameservers = new ArrayList<>();
        // it's a view of newNameservers, not a separate list!
        Collection<String> newNameserversCanonicalNamesView = Collections2.transform(newNameservers, new Function<NsType, String>() {
            @Override
            public String apply(NsType nsType) {
                return getCanonicalName(nsType.getNsName());
            }
        });
        for (Nameserver ns : currentNameservers) {
            NsType nsVO = new NsType();
            nsVO.setNsName(ns.getName());
            nsVO.setNsAddr(ns.getIpv4Address());
            nsVO.setNsAddr6(ns.getIpv6Address());
            newNameservers.add(nsVO);
        }
        if (isCommandRemoveNameserver(command)) {
            for (NsNameType nsNameType : command.getRem().getNs()) {
                final String canonicalName = getCanonicalName(nsNameType.getNsName());
                if (!newNameserversCanonicalNamesView.contains(canonicalName)) {
                    throw new DataManagementPolicyViolationException(ReasonCode.DOMAIN_NOT_DELEGATED_TO_HOST_TO_REMOVE,
                            new Value("nsName", nameSpace, nsNameType.getNsName()));
                } else {
                    // we're using the fact that removing from view modifies source list
                    newNameserversCanonicalNamesView.remove(canonicalName);
                }
            }
        }
        if (isCommandAddNameserver(command)) {
            for (NsType nsType : command.getAdd().getNs()) {
                if (newNameserversCanonicalNamesView.contains(getCanonicalName(nsType.getNsName())))
                    throw new DataManagementPolicyViolationException(
                            ReasonCode.DOMAIN_ALREADY_DELEGATED_TO_HOST_TO_ADD, new Value("nsName", nameSpace,
                                    nsType.getNsName()));
                newNameservers.add(nsType);
            }
        }

        return newNameservers;
    }

    private static boolean isCommandAddNameserver(ModifyType command) {
        return command.getAdd() != null && command.getAdd().getNs() != null;
    }

    private static boolean isCommandRemoveNameserver(ModifyType command) {
        return command.getRem() != null && command.getRem().getNs() != null;
    }

    public static boolean isDomainNameSyntaxValid(String domainName) {
        try {
            DomainNameValidator.validateIedrName(domainName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static IedrApiException mapExceptionForTooFewContacts(TooFewContactsException e) {
        switch (e.getType()) {
            case ADMIN:
                return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_ADMIN_CONTACTS, e);
            case TECH:
                return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_TECH_CONTACTS, e);
            default:
                return new CommandFailed(e);
        }
    }

    public static IedrApiException mapExceptionForTooManyContacts(TooManyContactsException e) {
        switch (e.getType()) {
            case ADMIN:
                return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_ADMIN_CONTACTS, e);
            case TECH:
                return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_TECH_CONTACTS, e);
            default:
                return new CommandFailed(e);
        }
    }
}
