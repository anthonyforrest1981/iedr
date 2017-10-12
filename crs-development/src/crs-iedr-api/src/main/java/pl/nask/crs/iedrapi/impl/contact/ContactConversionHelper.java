package pl.nask.crs.iedrapi.impl.contact;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import pl.nask.crs.api.vo.NicHandleVO;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;

import ie.domainregistry.ieapi_contact_1.InfDataType;
import ie.domainregistry.ieapi_contact_1.StatusType;

public class ContactConversionHelper {

    public static InfDataType makeInfo(NicHandle res) {
        InfDataType t = new InfDataType();
        t.setAccount(BigInteger.valueOf(res.getAccount().getId()));
        t.setAddr(res.getAddress());
        t.setCompanyName(res.getCompanyName());
        t.setCountry(res.getCountry().getName());
        t.setCounty(res.getCounty().getName());
        t.setCrDate(res.getRegistrationDate());
        t.setEmail(res.getEmail());
        t.setFax(TypeConverter.listToString(res.getFaxes()));
        t.setId(res.getNicHandleId());
        t.setName(res.getName());
        t.setStatus(StatusType.fromValue(res.getStatus().name()));
        t.setVoice(TypeConverter.listToString(res.getPhones()));

        return t;
    }

    public static List<String> nhToId(List<NicHandleVO> nhlist) {
        List<String> res = new LinkedList<String>();
        for (NicHandleVO nh : nhlist) {
            res.add(nh.getNicHandleId());
        }
        return res;
    }
}
