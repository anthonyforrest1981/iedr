package pl.nask.crs.api.validation;

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;

import static org.testng.Assert.*;

import static pl.nask.crs.api.validation.Utf8Validator.validated;

@SuppressWarnings("nullness")
public class Utf8ValidatorTest {

    @Test
    public void testNull() throws IncorrectUtf8FormatException {
        assertNull(validated((String) null));
        assertNull(validated((AuthenticatedUserVO) null));
        assertNull(validated(null));
    }

    @Test
    public void testPrimitives() throws IncorrectUtf8FormatException {
        assertEquals((int) validated(1), 1);
        assertEquals((long) validated(1l), 1l);
        assertEquals((boolean) validated(true), true);
        assertEquals((boolean) validated(false), false);
    }

    @Test
    public void testPrimitiveWrappers() throws IncorrectUtf8FormatException {
        assertEquals(validated(Integer.valueOf(1)), Integer.valueOf(1));
        assertEquals(validated(Long.valueOf(1)), Long.valueOf(1));
        assertEquals(validated(Boolean.valueOf(false)), Boolean.valueOf(false));
        assertEquals(validated(Boolean.valueOf(true)), Boolean.valueOf(true));
    }

    @Test
    public void testEnum() throws IncorrectUtf8FormatException {
        assertEquals(validated(TicketRequest.PeriodType.Y), TicketRequest.PeriodType.Y);
    }

    @Test
    public void testStringUnnormalized() throws IncorrectUtf8FormatException {
        assertEquals(validated("unno\u0307rmalized"), "unnȯrmalized");
    }

    @Test(expectedExceptions = IncorrectUtf8FormatException.class)
    public void test4byteString() throws IncorrectUtf8FormatException {
        String str = new String(Character.toChars(Integer.parseInt("10348", 16)));
        validated(str);
    }

    @Test
    public void testAuthenticatedUserVO() throws IncorrectUtf8FormatException {
        AuthenticatedUserVO user = new AuthenticatedUserVO();
        assertNotNull(validated(user));
        assertNull(validated(user).getAuthenticationToken());
        assertNull(validated(user).getSuperUserName());
        assertNull(validated(user).getUsername());
        assertFalse(validated(user).isPasswordChangeRequired());

        user.setUsername("Use\u0302rname");
        user.setAuthenticationToken("To\u0303ken");
        user.setPasswordChangeRequired(true);
        assertTrue(validated(user).isPasswordChangeRequired());
        assertEquals(validated(user).getUsername(), "Usêrname");
        assertEquals(validated(user).getAuthenticationToken(), "Tõken");
        assertNull(validated(user).getSuperUserName());
    }

    @Test
    public void testNameserverVO() throws IncorrectUtf8FormatException {
        NameserverVO nameserverVO = new NameserverVO();
        nameserverVO.setName("Na\u0301me");
        nameserverVO.setIpv4Address("I\u0304pv4");
        nameserverVO.setIpv6Address("I\u0302pv6");
        final NameserverVO validated = validated(nameserverVO);
        assertEquals(validated.getName(), "Náme");
        assertEquals(validated.getIpv4Address(), "Īpv4");
        assertEquals(validated.getIpv6Address(), "Îpv6");
    }

    @Test
    public void testArrayOfStrings() throws IncorrectUtf8FormatException {
        String[] strings = new String[] {"fi\u0301rst", "se\u0301cond", "thi\u0301rd"};
        final String[] validated = validated(strings);
        assertEquals(validated.length, 3);
        assertEquals(validated[0], "fírst");
        assertEquals(validated[1], "sécond");
        assertEquals(validated[2], "thírd");
    }

    @Test
    public void testArrayOfObjects() throws IncorrectUtf8FormatException {
        NameserverVO[] nameservers = new NameserverVO[] {
                new NameserverVO("Na\u0301me 1", "I\u0304pv4 1", "I\u0302pv6 1"),
                new NameserverVO("Na\u0301me 2", "I\u0304pv4 2", "I\u0302pv6 2"),
                new NameserverVO("Na\u0301me 3", "I\u0304pv4 3", "I\u0302pv6 3")};
        final NameserverVO[] validated = validated(nameservers);
        assertEquals(validated.length, 3);
        assertEquals(validated[0].getName(), "Náme 1");
        assertEquals(validated[0].getIpv4Address(), "Īpv4 1");
        assertEquals(validated[0].getIpv6Address(), "Îpv6 1");
        assertEquals(validated[1].getName(), "Náme 2");
        assertEquals(validated[1].getIpv4Address(), "Īpv4 2");
        assertEquals(validated[1].getIpv6Address(), "Îpv6 2");
        assertEquals(validated[2].getName(), "Náme 3");
        assertEquals(validated[2].getIpv4Address(), "Īpv4 3");
        assertEquals(validated[2].getIpv6Address(), "Îpv6 3");
    }

    @Test
    public void testListOfStrings() throws IncorrectUtf8FormatException {
        List<String> strings = Arrays.asList("fi\u0301rst", "se\u0301cond", "thi\u0301rd");
        assertEquals(validated(strings).size(), 3);
        assertEquals(validated(strings).get(0), "fírst");
        assertEquals(validated(strings).get(1), "sécond");
        assertEquals(validated(strings).get(2), "thírd");
    }

    @Test
    public void testListOfObjects() throws IncorrectUtf8FormatException {
        List<NameserverVO> nameservers = Arrays.asList(
                new NameserverVO("Na\u0301me 1", "I\u0304pv4 1", "I\u0302pv6 1"), new NameserverVO("Na\u0301me 2",
                        "I\u0304pv4 2", "I\u0302pv6 2"), new NameserverVO("Na\u0301me 3", "I\u0304pv4 3",
                        "I\u0302pv6 3"));
        final List<NameserverVO> validated = validated(nameservers);
        assertEquals(validated.size(), 3);
        assertEquals(validated.get(0).getName(), "Náme 1");
        assertEquals(validated.get(0).getIpv4Address(), "Īpv4 1");
        assertEquals(validated.get(0).getIpv6Address(), "Îpv6 1");
        assertEquals(validated.get(1).getName(), "Náme 2");
        assertEquals(validated.get(1).getIpv4Address(), "Īpv4 2");
        assertEquals(validated.get(1).getIpv6Address(), "Îpv6 2");
        assertEquals(validated.get(2).getName(), "Náme 3");
        assertEquals(validated.get(2).getIpv4Address(), "Īpv4 3");
        assertEquals(validated.get(2).getIpv6Address(), "Îpv6 3");
    }

    @Test
    public void testSetOfObjects() throws IncorrectUtf8FormatException {
        Set<NameserverVO> nameservers = new HashSet<>(Arrays.asList(new NameserverVO("Na\u0301me 1", "I\u0304pv4 1",
                "I\u0302pv6 1"), new NameserverVO("Na\u0301me 2", "I\u0304pv4 2", "I\u0302pv6 2"), new NameserverVO(
                "Na\u0301me 3", "I\u0304pv4 3", "I\u0302pv6 3")));
        final Set<NameserverVO> validated = validated(nameservers);
        assertEquals(validated.size(), 3);
        final SortedSet<NameserverVO> sorted = new TreeSet<>(new Comparator<NameserverVO>() {
            @Override
            public int compare(NameserverVO o1, NameserverVO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        sorted.addAll(validated);
        Iterator<NameserverVO> nss = sorted.iterator();
        NameserverVO ns = nss.next();
        assertEquals(ns.getName(), "Náme 1");
        assertEquals(ns.getIpv4Address(), "Īpv4 1");
        assertEquals(ns.getIpv6Address(), "Îpv6 1");
        ns = nss.next();
        assertEquals(ns.getName(), "Náme 2");
        assertEquals(ns.getIpv4Address(), "Īpv4 2");
        assertEquals(ns.getIpv6Address(), "Îpv6 2");
        ns = nss.next();
        assertEquals(ns.getName(), "Náme 3");
        assertEquals(ns.getIpv4Address(), "Īpv4 3");
        assertEquals(ns.getIpv6Address(), "Îpv6 3");
    }

    @Test
    public void testSetOfStrings() throws IncorrectUtf8FormatException {
        Set<String> strings = new TreeSet<>(Arrays.asList("fi\u0301rst", "se\u0301cond", "thi\u0301rd"));
        final Set<String> validated = validated(strings);
        assertEquals(validated.size(), 3);
        assertTrue(validated.containsAll(Arrays.asList("fírst", "sécond", "thírd")));
    }

    @Test
    public void testTransferRequestVO() throws IncorrectUtf8FormatException {
        TransferRequestVO transferRequest = new TransferRequestVO();
        transferRequest.setDomainName("do\u0301main name");
        transferRequest.setDomainHolder("Do\u0301main holder");
        transferRequest.setDomainOwnerTypeId(99L);
        transferRequest.setAdminContactNicHandles(Arrays.asList("A\u0302dmin 1 nic handle",
                "A\u0302dmin 2 nic handle"));
        transferRequest.setTechContactNicHandle("Te\u0303ch nic handle");
        transferRequest.setNameservers(Arrays.asList(new Nameserver("ns.na\u0301me1.ie", "i\u0304pv4.1",
                "i\u0304pv6.1"), new Nameserver("ns.na\u0301me2.ie", "i\u0304pv4.2", "i\u0304pv6.2"),
                new Nameserver("ns.na\u0301me3.ie", "i\u0304pv4.3", "i\u0304pv6.3")));
        transferRequest.setRequestersRemark("Re\u0302mark");
        transferRequest.setPeriod(1);
        transferRequest.setPeriodType(TicketRequest.PeriodType.Y);
        transferRequest.setAuthCode("a\u0307uthcode");

        final TransferRequestVO validated = validated(transferRequest);
        assertEquals(validated.getDomainName(), "dómain name");
        assertEquals(validated.getDomainHolder(), "Dómain holder");
        assertEquals(validated.getAdminContactNicHandles().get(0), "Âdmin 1 nic handle");
        assertEquals(validated.getAdminContactNicHandles().get(1), "Âdmin 2 nic handle");
        assertEquals(validated.getTechContactNicHandle(), "Tẽch nic handle");
        final List<Nameserver> validatedNameservers = validated.getNameservers();
        assertEquals(validatedNameservers.size(), 3);
        assertEquals(validatedNameservers.get(0).getName(), "ns.náme1.ie");
        assertEquals(validatedNameservers.get(0).getIpv4Address(), "īpv4.1");
        assertEquals(validatedNameservers.get(0).getIpv6Address(), "īpv6.1");
        assertEquals(validatedNameservers.get(1).getName(), "ns.náme2.ie");
        assertEquals(validatedNameservers.get(1).getIpv4Address(), "īpv4.2");
        assertEquals(validatedNameservers.get(1).getIpv6Address(), "īpv6.2");
        assertEquals(validatedNameservers.get(2).getName(), "ns.náme3.ie");
        assertEquals(validatedNameservers.get(2).getIpv4Address(), "īpv4.3");
        assertEquals(validatedNameservers.get(2).getIpv6Address(), "īpv6.3");
        assertEquals(validated.getRequestersRemark(), "Rêmark");
        assertEquals(validated.getRegPeriod().getMonths(), 12);
        assertEquals(validated.getPeriodType(), TicketRequest.PeriodType.Y);
        assertEquals(validated.getAuthCode(), "ȧuthcode");
    }

    @Test
    public void testObjectWithDate() throws IncorrectUtf8FormatException {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        Date deletionFrom = DateUtils.addDays(new Date(), -10);
        Date deletionTo = DateUtils.addDays(new Date(), -5);
        criteria.setDeletionFrom(deletionFrom);
        criteria.setDeletionTo(deletionTo);
        assertEquals(validated(criteria).getDeletionFrom(), criteria.getDeletionFrom());
        assertEquals(validated(criteria).getDeletionTo(), criteria.getDeletionTo());
    }
}
