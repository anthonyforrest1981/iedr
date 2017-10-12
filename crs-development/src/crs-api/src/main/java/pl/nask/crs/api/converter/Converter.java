package pl.nask.crs.api.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.commons.Period;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.domains.nameservers.Nameserver;

public final class Converter {
    private Converter() {}

    public static List<NameserverVO> toNameserverVOList(List<Nameserver> nameservers) {
        if (nameservers == null)
            return new ArrayList<>();

        ArrayList<NameserverVO> newNameservers = new ArrayList<>(nameservers.size());
        for (Nameserver ns : nameservers) {
            newNameservers.add(new NameserverVO(ns));
        }
        return newNameservers;
    }

    public static List<String> convertContacts(List<Contact> contacts) {
        if (contacts != null) {
            return ContactUtils.contactsAsStrings(contacts);
        } else {
            return null;
        }

    }

    public static Map<String, Period> convertDomainsWithPeriodToMap(List<DomainWithPeriodVO> domains) {
        HashMap<String, Period> ret = new HashMap<String, Period>();
        for (DomainWithPeriodVO domain : domains) {
            ret.put(domain.getDomainName(), Period.fromYears(domain.getPeriodInYears()));
        }
        return ret;
    }

    public static List<Nameserver> toNameserversList(List<NameserverVO> nameservers) {
        ArrayList<Nameserver> res = new ArrayList<Nameserver>();
        if (nameservers != null) {
            for (NameserverVO ns : nameservers) {
                res.add(new Nameserver(ns.getName(), ns.getIpv4Address(), ns.getIpv6Address()));
            }
        }
        return res;
    }
}
