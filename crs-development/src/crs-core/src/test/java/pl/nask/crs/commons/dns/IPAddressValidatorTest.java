package pl.nask.crs.commons.dns;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.commons.dns.IPAddressValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidIPv4AddressException;
import pl.nask.crs.commons.dns.exceptions.InvalidIPv6AddressException;

public class IPAddressValidatorTest {

    @DataProvider
    public static Object[][] validIPv6Addresses() {
        return new Object[][] { {"fe80:0000:0000:0000:1322:33FF:FE44:5566"}, {"2001:0000:0:fd:123:ffff:0:0"},
                {"2001:0db8:0000:0000:0000::1428:57ab"}, {"2001:0db8:0:0:0:0:1428:57ab"}, {"2001:0db8:0:0::1428:57ab"},
                {"2001:0db8::1428:57ab"}, {"2001:db8::1428:57ab"}, {"f::"}, {"::f"}, {"::f:128"}, {"::1.2.255.254"},
                {"::ffff:0:0"}, {"::ffff:0:1.2.3.4"}, {"1:2:3:4:5:6:1.2.3.4"}};
    }

    @DataProvider
    public static Object[][] invalidIPv6Addresses() {
        return new Object[][] { {":::"}, {":f:"}, {"2001::0000:0:fd:123:ffff::0:0"}, {"1:2:3:4:5:1.2.3.4"},
                {"2001:0000:0:::123:0"}, {"1:2:3:4:5:6:7:1.2.3.4"}};
    }

    @DataProvider
    public static Object[][] validIPv4Addresses() {
        return new Object[][] { {"0.0.0.0"}, {"255.255.255.255"}, {"1.2.3.4"}};
    }

    @DataProvider
    public static Object[][] invalidIPv4Addresses() {
        return new Object[][] { {"0.0.0.0.0"}, {"0.0.0"}, {"256.255.255.255"}, {"1.2.3.4."}, {".1.2.3.4"}, {"1..2.3.4"}};
    }

    @Test(dataProvider = "validIPv6Addresses")
    public void testValidIPv6(String ipv6Address) throws Exception {
        IPAddressValidator.validateIPv6(ipv6Address);
    }

    @Test(dataProvider = "invalidIPv6Addresses", expectedExceptions = InvalidIPv6AddressException.class)
    public void testInvalidIPv6(String ipv6Address) throws Exception {
        IPAddressValidator.validateIPv6(ipv6Address);
    }

    @Test(dataProvider = "validIPv4Addresses")
    public void testValidIPv4(String ipv4Address) throws Exception {
        IPAddressValidator.validateIPv4(ipv4Address);
    }

    @Test(dataProvider = "invalidIPv4Addresses", expectedExceptions = InvalidIPv4AddressException.class)
    public void testInvalidIPv4(String ipv4Address) throws Exception {
        IPAddressValidator.validateIPv4(ipv4Address);
    }
}
