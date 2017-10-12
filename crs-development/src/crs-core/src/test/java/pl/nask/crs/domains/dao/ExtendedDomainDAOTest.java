package pl.nask.crs.domains.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.payment.PaymentMethod;

import static pl.nask.crs.commons.DateTestHelper.makeDate;

public class ExtendedDomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    ExtendedDomainDAO extendedDomainDAO;

    @Test
    public void findDomainForCurrentRenewalTest() {
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setActive(true);
        criteria.setCurrentRenewal(true);
        criteria.setRenewalFrom(makeDate(2005, 5, 6));
        LimitedSearchResult<ExtendedDomain> result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(result.getTotalResults(), 13);
        Assert.assertEquals(result.getResults().size(), 5);

        criteria.setRenewalFrom(null);
        criteria.setRenewalTo(makeDate(2009, 5, 6));
        result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(result.getTotalResults(), 10);
        Assert.assertEquals(result.getResults().size(), 5);

        criteria.setRenewalFrom(makeDate(2009, 5, 6));
        criteria.setRenewalTo(makeDate(2023, 5, 6));
        result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(result.getTotalResults(), 2);
        Assert.assertEquals(result.getResults().size(), 2);

        criteria.setRenewalFrom(null);
        criteria.setRenewalTo(null);
        criteria.setDomainName("paydomain.ie");
        result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void findDomainForFutureRenewalTest() {
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setFutureRenewal(true);
        criteria.setRenewalMonth(5);
        LimitedSearchResult<ExtendedDomain> result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(result.getTotalResults(), 2);
        Assert.assertEquals(result.getResults().size(), 2);

        criteria.setRenewalMonth(1);
        result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(0, result.getTotalResults());
        Assert.assertEquals(0, result.getResults().size());
    }

    @Test
    public void findDomainForFutureRenewalWithPendingReservationTest() {
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setFutureRenewal(true);
        criteria.setRenewalMonth(8);
        LimitedSearchResult<ExtendedDomain> result = extendedDomainDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        ExtendedDomain domain = result.getResults().get(0);
        AssertJUnit.assertTrue(domain.hasPendingReservations());
    }

    @Test
    public void pendingReservationsInExtendedDomains() {
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        SearchResult<ExtendedDomain> result = extendedDomainDAO.find(criteria);

        Set<String> domainsWithCCReservations = new HashSet<String>() {
            {
                add("createccdomain.ie");
            }
        };
        Set<String> domainsWithADPReservations = new HashSet<String>() {
            {
                add("futuredomena3.ie");
            }
        };

        for (ExtendedDomain domain : result.getResults()) {
            if (domain.getPendingReservationPaymentMethod() == PaymentMethod.CC) {
                domainsWithCCReservations.remove(domain.getName());
                System.err.println(domain.getName());
            }
            if (domain.getPendingReservationPaymentMethod() == PaymentMethod.ADP) {
                domainsWithADPReservations.remove(domain.getName());
                System.err.println(domain.getName());
            }
        }

        AssertJUnit.assertTrue("There should be a domain with the pendingReservations set to true",
                domainsWithCCReservations.isEmpty() && domainsWithADPReservations.isEmpty());
    }
}
