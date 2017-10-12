package pl.nask.crs.nichandle.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;
import pl.nask.crs.nichandle.dao.ibatis.objects.Telecom;
import pl.nask.crs.nichandle.dao.ibatis.objects.TelecomType;

public class NicHandleConverter extends AbstractConverter<InternalNicHandle, NicHandle> {

    private List<Telecom> getSortedTelecoms(List<Telecom> unsorted) {
        List<Telecom> sorted = new ArrayList<>(unsorted);
        Collections.sort(sorted, new Comparator<Telecom>() {
            @Override
            public int compare(Telecom o1, Telecom o2) {
                if (o1.getType() == o2.getType()) {
                    return Integer.compare(o1.getOrder(), o2.getOrder());
                } else {
                    return o1.getType() == TelecomType.PHONE ? 1 : -1;
                }
            }
        });
        return sorted;
    }

    protected NicHandle _to(InternalNicHandle internalNicHandle) {

        List<String> phones = new ArrayList<>();
        List<String> faxes = new ArrayList<>();
        List<Telecom> telecoms = getSortedTelecoms(internalNicHandle.getTelecoms());
        for (Telecom telecom : telecoms) {
            if (telecom.getType() == TelecomType.FAX) {
                faxes.add(telecom.getNumber());
            } else {
                phones.add(telecom.getNumber());
            }
        }
        Account account = new Account(internalNicHandle.getAccountNumber(), internalNicHandle.getAccountName(),
                internalNicHandle.getAccountBillingContact());
        account.setAgreementSigned(internalNicHandle.isAgreementSigned());
        account.setTicketEdit(internalNicHandle.isTicketEdit());
        return new NicHandle(internalNicHandle.getNicHandleId(), internalNicHandle.getName(), account,
                internalNicHandle.getCompanyName(), internalNicHandle.getAddress(), phones.isEmpty() ? null : phones,
                faxes.isEmpty() ? null : faxes, internalNicHandle.getCountry(), internalNicHandle.getCounty(),
                internalNicHandle.getEmail(), internalNicHandle.getStatus(), internalNicHandle.getStatusChangeDate(),
                internalNicHandle.getRegistrationDate(), internalNicHandle.getChangeDate(),
                internalNicHandle.isBillCInd(), internalNicHandle.getNicHandleRemark(), internalNicHandle.getCreator(),
                internalNicHandle.getVatNo(), internalNicHandle.getVatCategory(), internalNicHandle.isExported());
    }

    protected InternalNicHandle _from(NicHandle nicHandle) {
        List<Telecom> telecoms = new ArrayList<>();
        if (nicHandle.getPhones() != null) {
            for (int i = 0; i < nicHandle.getPhones().size(); i++) {
                String phoneNumber = nicHandle.getPhones().get(i);
                telecoms.add(new Telecom(phoneNumber, TelecomType.PHONE, i));
            }
        }
        if (nicHandle.getFaxes() != null) {
            for (int i = 0; i < nicHandle.getFaxes().size(); i++) {
                String faxNumber = nicHandle.getFaxes().get(i);
                telecoms.add(new Telecom(faxNumber, TelecomType.FAX, i));
            }
        }

        return new InternalNicHandle(nicHandle.getNicHandleId(), nicHandle.getName(), nicHandle.getAccount().getId(),
                nicHandle.getAccount().getName(), nicHandle.getCompanyName(), nicHandle.getAddress(), telecoms,
                nicHandle.getCountry(), nicHandle.getCounty(), nicHandle.getEmail(), nicHandle.getStatus(),
                nicHandle.getStatusChangeDate(), nicHandle.getRegistrationDate(), nicHandle.getChangeDate(),
                nicHandle.isBillCInd(), nicHandle.getNicHandleRemark(), nicHandle.getCreator(), nicHandle.getVatNo(),
                nicHandle.getVatCategory(), nicHandle.isExported());
    }

}
