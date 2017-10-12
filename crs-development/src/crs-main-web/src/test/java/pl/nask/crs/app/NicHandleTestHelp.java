package pl.nask.crs.app;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.nichandle.NicHandle;

public class NicHandleTestHelp {

    public static NewNicHandle createNewNicHandle() {
        List<String> faxes = Arrays.asList("fax 1", "fax 2", "fax 3");
        List<String> phones = Arrays.asList("phone 1", "phone 2");
        return new NewNicHandle("new name", "new company name", "new@email.ee", "new address", 121, 14, phones, faxes,
                null, null);
    }

    public static NewNicHandle createNewNicHandle(NicHandle nh) {
        return new NewNicHandle(nh.getName(), nh.getCompanyName(), nh.getEmail(), nh.getAddress(),
                nh.getCountry().getId(), nh.getCounty().getId(), nh.getPhones(), nh.getFaxes(), nh.getVatNo(),
                nh.getVatCategory());
    }

}
