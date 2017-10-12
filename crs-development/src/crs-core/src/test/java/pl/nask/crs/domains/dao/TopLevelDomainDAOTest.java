package pl.nask.crs.domains.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.domains.AbstractContextAwareTest;

public class TopLevelDomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    TopLevelDomainDAO topLevelDomainDAO;

    @Test
    public void testUnnormalizedTopLevelDomain() {
        List<String> topLevelDomains = topLevelDomainDAO.getAll();
        Assert.assertTrue(topLevelDomains.contains("UNNO\u0212MALI\u017dED"));
    }
}
