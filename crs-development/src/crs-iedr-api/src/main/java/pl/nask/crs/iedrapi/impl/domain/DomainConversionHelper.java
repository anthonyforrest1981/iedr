package pl.nask.crs.iedrapi.impl.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.api.vo.AbstractTicketRequestVO;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.nameservers.Nameserver;

import ie.domainregistry.ieapi_domain_1.*;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public final class DomainConversionHelper {

    private DomainConversionHelper() {}

    public static InfDataType makeInfo(Domain res, boolean inclAuthCode, boolean includeIpv6Glue) {
        InfDataType result = new InfDataType();
        result.setAccount(BigInteger.valueOf(res.getResellerAccount().getId()));
        result.setStatus(convertDsmState(res.getDsmState(), res.isZonePublished()));

        HolderType holder = new HolderType();
        holder.setHolderName(res.getHolder());
        holder.setHolderRemarks(res.getRemark());
        result.setHolder(holder);

        List<Nameserver> nameservers = res.getNameservers();
        for (Nameserver n : nameservers) {
            if (!includeIpv6Glue && isEmpty(n.getIpv4Address()) && !isEmpty(n.getIpv6Address())) {
                // if nameserver includes only ipv6 glue it should be skipped altogether if
                // fillIpv6 is not set.
                continue;
            }
            NsType ns = new NsType();
            ns.setNsName(n.getName());
            if (!isEmpty(n.getIpv4Address())) {
                ns.setNsAddr(n.getIpv4Address());
            }
            if (includeIpv6Glue && !isEmpty(n.getIpv6Address())) {
                ns.setNsAddr6(n.getIpv6Address());
            }
            result.getNs().add(ns);
        }

        for (Contact c : res.getAdminContacts()) {
            ContactType cType = new ContactType();
            cType.setType(ContactAttrType.ADMIN);
            cType.setValue(c.getNicHandle());
            result.getContact().add(cType);
        }
        //TODO: what about billing contact?
        //        for (ContactVO c : res.getDomain().getBillingContacts()) {
        //        ContactType cType = new ContactType();

        //            cType.setType(ContactAttrType.);
        //            cType.setValue(c.getName());
        //            result.getContact().add(cType);
        //        }
        for (Contact c : res.getTechContacts()) {
            ContactType cType = new ContactType();
            cType.setType(ContactAttrType.TECH);
            cType.setValue(c.getNicHandle());
            result.getContact().add(cType);
        }

        result.setName(res.getName());
        result.setRegDate(res.getRegistrationDate());
        result.setRenDate(res.getRenewalDate());
        result.setSuspendDate(res.getSuspensionDate());
        result.setDeleteDate(res.getDeletionDate());
        result.setLockDate(res.getLockingDate());
        result.setLockRenewDate(res.getLockingRenewalDate());

        if (inclAuthCode) {
            result.setAuthCode(res.getAuthCode());
        }

        return result;
    }

    public static DsmStateType convertDsmState(DsmState dsmState, boolean isPublished) {
        if (dsmState == null) {
            dsmState = DsmState.initialState();
        }
        DsmStateType state = new DsmStateType();
        state.setHolderType(convertHolderType(dsmState));
        state.setRenewalStatus(convertNrp(dsmState));
        state.setPublished(convertPublished(isPublished));
        state.setRenewalMode(convertRenMode(dsmState));
        state.setLocked(convertLocked(dsmState));
        return state;
    }

    private static PublishedType convertPublished(boolean isPublished) {
        return isPublished ? PublishedType.Y : PublishedType.N;
    }

    private static DsmLocked convertLocked(DsmState dsmState) {
        if (dsmState.getLocked() != null && dsmState.getLocked()) {
            return DsmLocked.Y;
        } else {
            return DsmLocked.N;
        }
    }

    private static DsmRenewalMode convertRenMode(DsmState dsmState) {
        switch (dsmState.getRenewalMode()) {
            case Autorenew:
                return DsmRenewalMode.AUTORENEW;
            case NoAutorenew:
                return DsmRenewalMode.NO_AUTORENEW;
            case RenewOnce:
                return DsmRenewalMode.RENEW_ONCE;
            case NA:
                return DsmRenewalMode.N_A;
            default:
                throw new IllegalArgumentException("Illegal renewal mode: " + dsmState.getRenewalMode());
        }
    }

    private static DsmNrp convertNrp(DsmState dsmState) {
        switch (dsmState.getNrpStatus()) {
            case Active:
            case TransferPendingActive:
            case InvoluntaryMailedPaymentPending:
            case InvoluntarySuspendedPaymentPending:
            case TransferPendingInvNRP:
            case TransferPendingVolNRP:
                return DsmNrp.ACTIVE;
            case InvoluntaryMailed:
                return DsmNrp.INVOLUNTARY_NRP;
            case VoluntaryMailed:
                return DsmNrp.VOLUNTARY_NRP;
            case InvoluntarySuspended:
                return DsmNrp.INVOLUNTARY_NRP_SUSPENDED;
            case VoluntarySuspended:
                return DsmNrp.VOLUNTARY_NRP_SUSPENDED;
            case NA:
                return DsmNrp.N_A;
            case Deleted:
            default:
                throw new IllegalArgumentException("Cannot map " + dsmState + " onto IEDR-API DsmState type");
        }
    }

    private static DsmHolderType convertHolderType(DsmState dsmState) {
        switch (dsmState.getDomainHolderType()) {
            case Billable:
                return DsmHolderType.BILLABLE;
            case Charity:
                return DsmHolderType.CHARITY;
            case IEDRUnpublished:
                return DsmHolderType.IEDR_UNPUBLISHED;
            case IEDRPublished:
                return DsmHolderType.IEDR_PUBLISHED;
            case NonBillable:
                return DsmHolderType.NON_BILLABLE;
            case NA:
                return DsmHolderType.N_A;
            default:
                throw new IllegalArgumentException("Illegal domain holder type: " + dsmState.getDomainHolderType());
        }
    }

    public static List<Nameserver> toNameserverList(List<NsType> nsTypeList) throws NameserverNameSyntaxException {
        if (nsTypeList == null)
            return null;
        List<Nameserver> res = new ArrayList<>();
        for (NsType ns : nsTypeList) {
            if (Validator.isEmpty(ns.getNsName())) {
                throw new NameserverNameSyntaxException(ns.getNsName());
            }
            Nameserver nsVO = new Nameserver(ns.getNsName(), ns.getNsAddr(), ns.getNsAddr6());
            res.add(nsVO);
        }
        return res;
    }

    public static void updateNs(AbstractTicketRequestVO request, List<NsType> nsList)
            throws NameserverNameSyntaxException {
        request.resetToNameservers(toNameserverList(nsList));
    }
}
