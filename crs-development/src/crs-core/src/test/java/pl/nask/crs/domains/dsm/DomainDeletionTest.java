package pl.nask.crs.domains.dsm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

public class DomainDeletionTest extends AbstractContextAwareTest {
    @Autowired
    private DomainStateMachine dsm;

    @Autowired
    private HistoricalDomainService hist;

    @Autowired
    private DomainSearchService domainSearchService;

    @Autowired
    private DomainService domainService;

    @Resource
    private AuthenticationService authenticationService;

    private AuthenticatedUser user;

    int finalDsmState = 387;
    String domainName = "castlebargolfclub.ie";

    @BeforeMethod
    public void setUpUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void domainDeletionShouldLeaveTraceInDomainHist() throws Exception {
        OpInfo opInfo = TestOpInfo.DEFAULT;
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());

        HistoricalDomainSearchCriteria crit = new HistoricalDomainSearchCriteria();
        crit.setDomainName(domainName);
        LimitedSearchResult<HistoricalObject<Domain>> history = hist.findHistory(crit, 0, 1000, null);
        AssertJUnit.assertFalse(containsDsmState(history.getResults(), finalDsmState));

        dsm.handleEvent(user, domainName, DsmEventName.RenewalDatePasses, opInfo);
        dsm.handleEvent(user, domainName, DsmEventName.SuspensionDatePasses, opInfo);
        dsm.handleEvent(user, domainName, DsmEventName.DeletionDatePasses, opInfo);

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(385, domain.getDsmState().getId());

        dsm.handleEvent(user, domainName, DsmEventName.DeletedDomainRemoval, opInfo);
        history = hist.findHistory(crit, 0, 1000, null);
        AssertJUnit.assertTrue(containsDsmState(history.getResults(), 385));
        AssertJUnit.assertTrue(containsDsmState(history.getResults(), finalDsmState));
    }

    @Test
    public void domainDeletionShouldLeaveTraceInDeletedDomainsReport() throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        // for the sake of the test: set the renewal date so the counted deletion date is in the past but not more than one year.
        domain.setRenewalDate(DateUtils.getCurrDate(-90)); //90 days ago
        OpInfo opInfo = TestOpInfo.DEFAULT;
        domainService.save(domain, opInfo);

        assertDeletedDomainsContainDomainName(false);

        dsm.handleEvent(user, domainName, DsmEventName.RenewalDatePasses, opInfo);
        assertDeletedDomainsContainDomainName(false);
        dsm.handleEvent(user, domainName, DsmEventName.SuspensionDatePasses, opInfo);
        assertDeletedDomainsContainDomainName(false);
        dsm.handleEvent(user, domainName, DsmEventName.DeletionDatePasses, opInfo);
        assertDeletedDomainsContainDomainName(false);
        dsm.handleEvent(user, domainName, DsmEventName.DeletedDomainRemoval, opInfo);
        assertDeletedDomainsContainDomainName(true);
    }

    private void assertDeletedDomainsContainDomainName(boolean contains) {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();

        LimitedSearchResult<DeletedDomain> deletedDomains = domainSearchService.findDeletedDomains(criteria, 0, 1000,
                null);
        boolean found = false;
        for (DeletedDomain d : deletedDomains.getResults()) {
            found = found || d.getName().equalsIgnoreCase(domainName);
        }

        AssertJUnit.assertEquals(contains, found);
    }

    private boolean containsDsmState(List<HistoricalObject<Domain>> results, int stateId) {
        for (HistoricalObject<Domain> d : results) {
            if (d.getObject().getDsmState().getId() == stateId)
                return true;
        }
        return false;
    }

}
