package pl.nask.crs.domains.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.domains.transfer.BulkTransferDomain;

public class BulkTransferDAOTest extends AbstractContextAwareTest {
    @Resource
    BulkTransferDAO dao;

    private long gaining = 668;
    private long losing = 667;

    @Test
    public void testCreateTransferRequest() {
        Long transferId = dao.createBulkTransferProcess(losing, 668, "rema\u0308rks");
        AssertJUnit.assertNotNull("bulkTransferId", transferId);
        BulkTransferRequest request = dao.get(transferId);
        Assert.assertEquals(request.getRemarks(), "remärks");
    }

    @Test
    public void testCreateTwoRequests() {
        long firstTransferId = dao.createBulkTransferProcess(losing, gaining, "firstTransfer");
        long secondTransferId = dao.createBulkTransferProcess(losing, gaining, "secondTransfer");
        Assert.assertFalse(firstTransferId == secondTransferId, "transfer ids must be different");
    }

    @Test
    public void testAddDomainsToOpenRequest() {
        // open a request first
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDo\u0308main");
        dao.addDomainToTransfer(transferId, "secondDomain");
        BulkTransferRequest request = dao.get(transferId);
        Assert.assertTrue(request.getDomainNames().contains("firstDömain"));
    }

    @Test(expectedExceptions = Exception.class)
    public void testAddDomainTwice() {
        // open a request first
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDo\u0308main");
        BulkTransferRequest tr = dao.get(transferId);
        AssertJUnit.assertEquals("number of domains in transfer", 1, tr.getRequestedDomains().size());

        dao.addDomainToTransfer(transferId, "firstDömain");
        tr = dao.get(transferId);
        AssertJUnit.assertEquals("number of domains in transfer shouldn't change", 1, tr.getRequestedDomains().size());
    }

    @Test
    public void testMarkDomainAsTransferred() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDo\u0308main");
        dao.addDomainToTransfer(transferId, "secondDomain");

        dao.markDomainAsTransferred(transferId, "firstDo\u0308main", "test", new Date());
    }

    @Test
    public void testRemoveDomainFromTransfer() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDo\u0308main");
        dao.addDomainToTransfer(transferId, "secondDo\u0308main");

        dao.markDomainAsTransferred(transferId, "firstDömain", "test", new Date());

        dao.removeDomainFromTransfer(transferId, "seco\u0308ndDomain");

        BulkTransferRequest tr = dao.get(transferId);
        AssertJUnit.assertEquals(1, tr.getRequestedDomains().size());
    }

    @Test
    public void testRemoveDomainFromTransferFailed() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDomain");
        dao.addDomainToTransfer(transferId, "secondDomain");

        dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

        dao.removeDomainFromTransfer(transferId, "firstDomain");

        BulkTransferRequest tr = dao.get(transferId);
        AssertJUnit.assertEquals(2, tr.getRequestedDomains().size());
    }

    @Test
    public void testListTransfers() {
        dao.find(null);
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

        SearchResult<BulkTransferRequest> res = dao.find(null);
        AssertJUnit.assertFalse("result not empty", res.getResults().isEmpty());
    }

    @Test
    public void testGetTransfer() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

        dao.addDomainToTransfer(transferId, "firstDomain");
        dao.addDomainToTransfer(transferId, "secondDomain");

        dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

        BulkTransferRequest req = dao.get(transferId);

        AssertJUnit.assertEquals("id", transferId, req.getId());
        AssertJUnit.assertEquals("gainingAccount", gaining, req.getGainingAccount());
        AssertJUnit.assertEquals("losingAccount", losing, req.getLosingAccount());
        AssertJUnit.assertEquals("Remarks", "remarks", req.getRemarks());
        AssertJUnit.assertNotNull("Domains not null", req.getRequestedDomains());
        AssertJUnit.assertEquals("number of domains", 2, req.getRequestedDomains().size());
    }

    @Test
    public void testCheckIfNotTransferred() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");

        dao.addDomainToTransfer(transferId, "firstDo\u0308main");
        dao.addDomainToTransfer(transferId, "secondDomain");

        dao.markDomainAsTransferred(transferId, "firstDomain", "test", new Date());

        AssertJUnit.assertFalse(dao.isNotTransferred(transferId, "firstDömain"));
        AssertJUnit.assertTrue(dao.isNotTransferred(transferId, "secondDomain"));
    }

    @Test
    public void testLockTransfer() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        AssertJUnit.assertTrue(dao.lock(transferId));
        BulkTransferRequest tr1 = dao.get(transferId);
        AssertJUnit.assertEquals(transferId, tr1.getId());
    }

    @Test
    public void testCloseTransfer() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        final Date closingDate = DateUtils.setMilliseconds(new Date(), 999);
        dao.closeTransfer(transferId, "te\u0308st", closingDate);
        BulkTransferRequest tr = dao.get(transferId);
        AssertJUnit.assertTrue("transfer closed", tr.isClosed());
        AssertJUnit.assertEquals("closing date", DateUtils.truncate(closingDate, Calendar.SECOND),
                tr.getCompletionDate());
        Assert.assertEquals(tr.getHostmasterNh(), "tëst");

        // try again: second closing should take no effect
        dao.closeTransfer(transferId, "test", DateUtils.addDays(closingDate, 1));
        tr = dao.get(transferId);
        AssertJUnit.assertTrue("closing date didn't change", DateUtils.isSameDay(closingDate, tr.getCompletionDate()));
    }

    @Test
    public void closingDateShouldBeTruncated() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        final Date closingDate = DateUtils.setMilliseconds(new Date(), 999);
        dao.closeTransfer(transferId, "test", closingDate);
        BulkTransferRequest tr = dao.get(transferId);
        AssertJUnit.assertEquals("closing date", DateUtils.truncate(closingDate, Calendar.SECOND),
                tr.getCompletionDate());
    }

    @Test
    public void transferDateShouldBeTruncated() {
        long transferId = dao.createBulkTransferProcess(losing, gaining, "remarks");
        dao.addDomainToTransfer(transferId, "firstDomain");
        dao.addDomainToTransfer(transferId, "secondDomain");
        final Date transferDate = DateUtils.setMilliseconds(new Date(), 999);
        dao.markDomainAsTransferred(transferId, "firstDomain", "test", transferDate);
        final List<BulkTransferDomain> requestedDomains = dao.get(transferId).getRequestedDomains();
        BulkTransferDomain bulkTransferDomain = null;
        for (BulkTransferDomain domain : requestedDomains) {
            if (domain.getName().equals("firstDomain")) {
                bulkTransferDomain = domain;
            }
        }
        AssertJUnit
                .assertEquals(DateUtils.truncate(transferDate, Calendar.SECOND), bulkTransferDomain.getTransferDate());
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        BulkTransferRequest transfer = dao.get(990l);
        Assert.assertEquals(transfer.getLosingAccountName(), "Irish Domains");
        Assert.assertEquals(transfer.getGainingAccountName(), "Irish Domäins");
        Assert.assertEquals(transfer.getRemarks(), "Unnormalized rëmark");
        Assert.assertEquals(transfer.getDomainNames().size(), 1);
        Assert.assertTrue(transfer.getDomainNames().contains("www.domäinaddress.xxx"));
    }

}
