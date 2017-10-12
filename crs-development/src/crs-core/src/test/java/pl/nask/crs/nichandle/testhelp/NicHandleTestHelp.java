package pl.nask.crs.nichandle.testhelp;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

public class NicHandleTestHelp {

    public static NicHandleSearchCriteria criteria1;
    public static HistoricalNicHandleSearchCriteria histCriteria1;
    public static String nicHandleIdAA11;

    static {
        criteria1 = new NicHandleSearchCriteria();
        histCriteria1 = new HistoricalNicHandleSearchCriteria();
        criteria1.setNicHandleId("AAE");
        histCriteria1.setNicHandleId("AA11-IEDR");
        nicHandleIdAA11 = "AA11-IEDR";
    }

    public static void compareNicHandleList(List<NicHandle> actualNH, List<NicHandle> expectedNH) {
        assertEquals(actualNH.size(), expectedNH.size());
        for (int i = 0; i < actualNH.size(); i++) {
            compareNicHandle(actualNH.get(i), expectedNH.get(i));
        }
    }

    public static void compareHistoricalNHList(List<HistoricalObject<NicHandle>> expected,
            List<HistoricalObject<NicHandle>> actual) {
        if (expected == null)
            assertNull(actual);
        else {
            assertEquals(actual.size(), expected.size());
            for (int i = 0; i < actual.size(); i++) {
                compareHistoricalNicHandles(actual.get(i), expected.get(i));
            }
        }
    }

    public static void compareHistoricalNicHandles(HistoricalObject actual, HistoricalObject expected) {
        assertEquals(expected.getChangeDate().getTime(), actual.getChangeDate().getTime());
        assertEquals(expected.getChangedBy(), actual.getChangedBy());
        compareNicHandle((NicHandle) actual.getObject(), (NicHandle) expected.getObject());
    }

    public static void compareNicHandle(NicHandle actual, NicHandle expected) {
        if (actual == null)
            assertNull(expected);
        else {
            compareNicHandlesNoDates(actual, expected);
            assertEquals("StatusChangeDate", DateUtils.truncate(expected.getStatusChangeDate(), Calendar.DATE),
                    DateUtils.truncate(actual.getStatusChangeDate(), Calendar.DATE));
            assertEquals("RegDate", DateUtils.truncate(expected.getRegistrationDate(), Calendar.DATE),
                    DateUtils.truncate(actual.getRegistrationDate(), Calendar.DATE));
            assertEquals("ChangeDate", DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND),
                    DateUtils.truncate(actual.getChangeDate(), Calendar.SECOND));
        }
    }

    private static void compareNicHandlesNoDates(NicHandle actual, NicHandle expected) {
        assertEquals("VatCategory", expected.getVatCategory(), actual.getVatCategory());
        assertEquals("NicHandleId", expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals("Name", expected.getName(), actual.getName());
        assertEquals("Account.id", expected.getAccount().getId(), actual.getAccount().getId());
        assertEquals("Company Name", expected.getCompanyName(), actual.getCompanyName());
        assertEquals("Address", expected.getAddress(), actual.getAddress());
        assertEquals("County", expected.getCounty().getId(), actual.getCounty().getId());
        assertEquals("Country", expected.getCountry().getId(), actual.getCountry().getId());
        assertEquals("Email", expected.getEmail(), actual.getEmail());
        assertEquals("Status", expected.getStatus(), actual.getStatus());
        assertEquals("isBillCInd()", expected.isBillCInd(), actual.isBillCInd());
        assertEquals("NicHandleRemark", expected.getNicHandleRemark(), actual.getNicHandleRemark());
        assertEquals("Creator", expected.getCreator(), actual.getCreator());
        assertEquals("Faxes", expected.getFaxes(), actual.getFaxes());
        assertEquals("Phones", expected.getPhones(), actual.getPhones());
        assertEquals("VatNo", expected.getVatNo(), actual.getVatNo());
    }

    public static void compareNewNicHandle(NicHandle nicHandle, NewNicHandle newNicHandle) {
        assertEquals("Name", newNicHandle.getName(), nicHandle.getName());
        assertEquals("Company Name", newNicHandle.getCompanyName(), nicHandle.getCompanyName());
        assertEquals("Email", newNicHandle.getEmail(), nicHandle.getEmail());
        assertEquals("Address", newNicHandle.getAddress(), nicHandle.getAddress());
        assertEquals("Country", newNicHandle.getCountryId(), nicHandle.getCountry().getId());
        assertEquals("County", newNicHandle.getCountyId(), nicHandle.getCounty().getId());
        assertEquals("Faxes", newNicHandle.getFaxes(), nicHandle.getFaxes());
        assertEquals("Phones", newNicHandle.getPhones(), nicHandle.getPhones());
        assertEquals("Vat Number", newNicHandle.getVatNo(), nicHandle.getVatNo());
        assertEquals("Vat Category", newNicHandle.getVatCategory(), nicHandle.getVatCategory());
    }

    public static NewNicHandle createNewNicHandle() {
        List<String> faxes = Arrays.asList("fax 1", "fax 2", "fax 3");
        List<String> phones = Arrays.asList("phone 1", "phone 2");
        return new NewNicHandle("new name", "new company name", "new@email.ee", "new address", 121, 14, phones, faxes,
                null, null);
    }

    public static NewNicHandle createNewNicHandleWithVat() {
        List<String> faxes = Arrays.asList("fax 1", "fax 2", "fax 3");
        List<String> phones = Arrays.asList("phone 1", "phone 2");
        return new NewNicHandle("new name", "new company name", "new@email.ee", "new address", 121, 14, phones, faxes,
                "123456789", "A");
    }

    public static NewNicHandle createNewNicHandle(NicHandle nh) {
        return new NewNicHandle(nh.getName(), nh.getCompanyName(), nh.getEmail(), nh.getAddress(),
                nh.getCountry().getId(), nh.getCounty().getId(), nh.getPhones(), nh.getFaxes(), nh.getVatNo(),
                nh.getVatCategory());
    }

}
