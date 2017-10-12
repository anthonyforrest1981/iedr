package pl.nask.crs.domains.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class BulkTransferRequestTest {

    @Test(expectedExceptions = Exception.class)
    public void isFullyCompletedFailsIfTransferNotInitialized() {
        BulkTransferRequest req = new BulkTransferRequest();
        req.isFullyCompleted();
    }

    @Test
    public void isFullyCompletedShouldReturnTrueWhenAllDomainsAreTransferred() {
        BulkTransferRequest req = new BulkTransferRequest();
        List<BulkTransferDomain> requestedDomains = new ArrayList<BulkTransferDomain>();
        requestedDomains.add(new BulkTransferDomain("domain1.ie", new Date()));
        requestedDomains.add(new BulkTransferDomain("domain2.ie", new Date()));
        req.setRequestedDomains(requestedDomains);
        AssertJUnit.assertTrue("Transfer fully completed", req.isFullyCompleted());
    }

    @Test
    public void isFullyCompletedShouldReturnFalseWhenNotAllDomainsAreTransferred() {
        BulkTransferRequest req = new BulkTransferRequest();
        List<BulkTransferDomain> requestedDomains = new ArrayList<BulkTransferDomain>();
        requestedDomains.add(new BulkTransferDomain("domain1.ie", null));
        requestedDomains.add(new BulkTransferDomain("domain2.ie", new Date()));
        req.setRequestedDomains(requestedDomains);
        AssertJUnit.assertFalse("Transfer not fully completed", req.isFullyCompleted());
    }

    @Test
    public void isClosedShouldReturnFalseIfCompletionDateIsNotSet() {
        BulkTransferRequest req = new BulkTransferRequest();
        AssertJUnit.assertFalse(req.isClosed());
    }

    @Test
    public void isClosedShouldReturnTrueIfCompletionDateIsSet() {
        BulkTransferRequest req = new BulkTransferRequest();
        req.setCompletionDate(new Date());
        AssertJUnit.assertTrue(req.isClosed());
    }
}
