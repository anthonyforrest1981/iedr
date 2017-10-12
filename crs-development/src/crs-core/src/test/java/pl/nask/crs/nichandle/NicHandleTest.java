package pl.nask.crs.nichandle;

import java.util.Arrays;
import java.util.Date;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;

public class NicHandleTest {

    @Test
    public void getPhonesAsStringTest() {
        NicHandle nh = createNHAAE359();
        AssertJUnit.assertEquals("123, 234", nh.getPhonesAsString());
    }

    public static NicHandle createNHAAE359() {
        NicHandle nh = new NicHandle("AAE359-IEDR", "Iron Mountain Inc", new Account(1, "GUEST ACCOUNT", "IH4-IEDR"),
                "Iron Mountain, Inc.", "NHAddress000007", null, null, null, null, "NHEmail000007@server.xxx",
                NicHandleStatus.Active, new Date(1050530400000L), new Date(61558), new Date(1213632700000L), true,
                "Transfer to Iron Mountain, order id 20080616162237-1052-nicorette", "AAE359-IEDR", null, "S", true);
        nh.setPhones(Arrays.asList("123", "234"));
        return nh;
    }
}
