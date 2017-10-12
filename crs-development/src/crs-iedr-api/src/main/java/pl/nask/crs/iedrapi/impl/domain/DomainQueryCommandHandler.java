package pl.nask.crs.iedrapi.impl.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.QueryType;
import ie.domainregistry.ieapi_domain_1.ResDataType;
import ie.domainregistry.ieapi_domain_1.ResDomainType;

public class DomainQueryCommandHandler extends AbstractDomainCommandHandler<QueryType> {

    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {

        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);

        int page = command.getPage();
        long offset = TypeConverter.pageToOffset(page);
        boolean adjustRenewalDate = true;

        long totalResults;
        boolean attachReservationInfo = TypeConverter.commandFieldToBoolean(command.isAttachReservationInfo());
        boolean attachAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCode());
        boolean forceAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCodeForceGeneration());
        List<ResDomainType> resDomainList;
        if (command.getTransfer() != null) {
            PlainDomainSearchCriteria transferCriteria = new PlainDomainSearchCriteria();
            transferCriteria.setTransferFrom(command.getTransfer().getFrom());
            transferCriteria.setTransferTo(command.getTransfer().getTo());
            LimitedSearchResult<PlainDomain> transferredSearchRes;
            switch (command.getTransfer().getType()) {
                case INBOUND:
                    transferredSearchRes = getDomainAppService().findTransferredInDomains(auth.getUser(),
                            transferCriteria, offset, IedrApiConfig.getPageSize(), null);
                    break;
                case OUTBOUND:
                    transferredSearchRes = getDomainAppService().findTransferredAwayDomains(auth.getUser(),
                            transferCriteria, offset, IedrApiConfig.getPageSize(), null);
                    // outgoing transfers will be using historical data, should not show current renewalDate
                    adjustRenewalDate = false;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid transfer type: " + command.getTransfer().getType());

            }
            totalResults = transferredSearchRes.getTotalResults();
            resDomainList = getTransferredResponseType(auth.getUser(), transferredSearchRes.getResults(),
                    attachAuthCode, forceAuthCode, adjustRenewalDate);
        } else {
            long accountNumber = getAccountId(auth.getUser());
            ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
            criteria.setAccountId(accountNumber);

            if (command.getDomainStatus() != null) {
                switch (command.getDomainStatus()) {
                    case ACTIVE:
                        criteria.setActive(true);
                        break;
                    case NRP:
                        criteria.setActive(false);
                        break;
                }
            }

            if (command.getContact() != null) {
                criteria.setNicHandle(command.getContact().getValue());
                if (command.getContact().getType() != null) {
                    switch (command.getContact().getType()) {
                        case ADMIN:
                            criteria.setContactType(Arrays.asList(ContactType.ADMIN));
                            break;
                        case TECH:
                            criteria.setContactType(Arrays.asList(ContactType.TECH));
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid contact type: "
                                    + command.getContact().getType());
                    }
                }
            }

            criteria.setRenewalTo(command.getRenewalDateRangeEnd());
            criteria.setRenewalFrom(command.getRenewalDateRangeStart());
            excludeNRPStatusDeletedDomains(criteria);
            LimitedSearchResult<ExtendedDomain> searchRes = getDomainAppService().findExtended(auth.getUser(),
                    criteria, offset, IedrApiConfig.getPageSize(), null);
            totalResults = searchRes.getTotalResults();
            resDomainList = getResponseType(auth.getUser(), searchRes.getResults(), attachReservationInfo,
                    attachAuthCode, forceAuthCode, adjustRenewalDate);
        }

        validatePage(totalResults, offset);
        if (totalResults == 0) {
            return ResponseTypeFactory.successNoRes();
        }
        ResDataType res = new ResDataType();
        res.setPage(page);
        res.setTotalPages(TypeConverter.totalResultsToPages(totalResults));
        res.getDomain().addAll(resDomainList);
        return ResponseTypeFactory.success(res);
    }

    private void excludeNRPStatusDeletedDomains(ExtendedDomainSearchCriteria criteria) {
        if (Validator.isEmpty(criteria.getNrpStatuses())) {
            criteria.setNrpStatuses(NRPStatus.values());
        }
        criteria.removeNRPStatus(NRPStatus.Deleted);
    }

    private List<ResDomainType> getResponseType(AuthenticatedUser user, List<ExtendedDomain> results,
            boolean attachReservationInfo, boolean attachAuthCode, boolean forceAuthCode, boolean adjustRenewalDate)
            throws IedrApiException, AccessDeniedException {
        List<ResDomainType> res = new ArrayList<ResDomainType>();
        for (ExtendedDomain domain : results) {
            String authcode = getAuthcode(user, domain.getName(), domain.getAuthCode(), attachAuthCode, forceAuthCode);
            Date renewalDate = getRenewalDate(user, domain.getName(), domain.getRenewalDate(), adjustRenewalDate);
            res.add(rdt(domain, attachReservationInfo, authcode, renewalDate));
        }
        return res;
    }

    private List<ResDomainType> getTransferredResponseType(AuthenticatedUser user, List<PlainDomain> results,
            boolean attachAuthCode, boolean forceAuthCode, boolean adjustRenewalDate)
            throws IedrApiException, AccessDeniedException {
        List<ResDomainType> res = new ArrayList<ResDomainType>();
       for (PlainDomain domain : results) {
            String authcode = getAuthcode(user, domain.getName(), domain.getAuthCode(), attachAuthCode, forceAuthCode);
            Date renewalDate = getRenewalDate(user, domain.getName(), domain.getRenewalDate(), adjustRenewalDate);
            res.add(rdt(domain, authcode, renewalDate));
        }
        return res;
    }

    private String getAuthcode(AuthenticatedUser user, String domainName, String initialAuthCode,
            boolean attachAuthCode, boolean forceAuthCode) throws IedrApiException {
        String authCode = initialAuthCode;
        if (forceAuthCode) {
            try {
                authCode =  getCommonAppService().generateOrProlongAuthCode(user, domainName).getAuthCode();
            } catch (Exception e) {
                throw mapException(e);
            }
        }
        if (!attachAuthCode) {
            authCode = null;
        }
        return authCode;
    }

    private Date getRenewalDate(AuthenticatedUser user, String domainName, Date initialDate,
            boolean adjustRenewalDate) throws AccessDeniedException {
        Date date = initialDate;
        if (adjustRenewalDate) {
            date = getRenewalDateOfReservationData(user, domainName, initialDate);
        }
        return date;
    }

    private ResDomainType rdt(ExtendedDomain domain, boolean attachReservationInfo, String authCode,
            Date renewalDate) {
        ResDomainType t = new ResDomainType();
        t.setDeleteDate(domain.getDeletionDate());
        t.setName(domain.getName());
        t.setRegDate(domain.getRegistrationDate());
        t.setRenDate(renewalDate);
        t.setStatus(DomainConversionHelper.convertDsmState(domain.getDsmState(), domain.isZonePublished()));
        t.setSuspendDate(domain.getSuspensionDate());
        t.setTransDate(domain.getTransferDate());
        t.setLockDate(domain.getLockingDate());
        t.setLockRenewDate(domain.getLockingRenewalDate());
        t.setAuthCode(authCode);
        if (attachReservationInfo) {
            t.setReservationPending(domain.hasPendingReservations());
        }
        return t;
    }

    private ResDomainType rdt(PlainDomain d, String authCode, Date renewalDate) {
        ResDomainType t = new ResDomainType();
        t.setDeleteDate(d.getDeletionDate());
        t.setName(d.getName());
        t.setRegDate(d.getRegistrationDate());
        t.setRenDate(renewalDate);
        t.setStatus(DomainConversionHelper.convertDsmState(d.getDsmState(), d.isZonePublished()));
        t.setSuspendDate(d.getSuspensionDate());
        t.setTransDate(d.getTransferDate());
        t.setLockDate(d.getLockingDate());
        t.setLockRenewDate(d.getLockingRenewalDate());
        t.setAuthCode(authCode);
        return t;
    }
}
