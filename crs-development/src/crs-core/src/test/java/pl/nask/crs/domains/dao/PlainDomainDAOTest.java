package pl.nask.crs.domains.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;

import static org.testng.AssertJUnit.assertEquals;

import static pl.nask.crs.commons.DateTestHelper.makeDate;

public class PlainDomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    HistoricalDomainDAO histDomainDAO;

    @Autowired
    PlainDomainDAO plainDomainDAO;

    @Test
    public void testLimitedFindByDomainName() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(1, found.getTotalResults());
        assertEquals(1, found.getResults().size());
    }

    @Test
    public void testLimitedFindByAccount() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(4, found.getTotalResults());
        assertEquals(4, found.getResults().size());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDomainHolder("astleba");
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(5, found.getTotalResults());
        assertEquals(5, found.getResults().size());
    }

    @Test
    public void testLimitedFindNicHandle() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("AA");
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(27, found.getTotalResults());
        assertEquals(10, found.getResults().size());
    }

    @Test
    public void testLimitedFindByLockingDate() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setLockFrom(DateUtils.addMonths(new Date(), -10));
        criteria.setLockTo(DateUtils.addMonths(new Date(), -4));
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(4, found.getTotalResults());
        assertEquals(4, found.getResults().size());
    }

    @Test
    public void testLimitedFindByLockingRenewalDate() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setLockRenewalFrom(new Date());
        criteria.setLockRenewalTo(DateUtils.addMonths(new Date(), 6));
        LimitedSearchResult<PlainDomain> found = plainDomainDAO.find(criteria, 0, 10, null);
        assertEquals(5, found.getTotalResults());
        assertEquals(5, found.getResults().size());
    }

    @Test
    public void findBySuspendedDateTest() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setSuspensionFrom(makeDate(2005, 5, 6));
        criteria.setSuspensionTo(makeDate(2008, 5, 6));
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(2, result.getTotalResults());

        criteria = new PlainDomainSearchCriteria();
        criteria.setSuspensionFrom(makeDate(2008, 5, 6));
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(2, result.getTotalResults());

        criteria = new PlainDomainSearchCriteria();
        criteria.setSuspensionTo(makeDate(2005, 5, 6));
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByDeletedDateTest() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDeletionFrom(makeDate(2005, 5, 6));
        criteria.setDeletionTo(makeDate(2008, 5, 6));
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(4, result.getTotalResults());

        criteria = new PlainDomainSearchCriteria();
        criteria.setDeletionFrom(makeDate(2008, 5, 6));
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());

        criteria = new PlainDomainSearchCriteria();
        criteria.setDeletionTo(makeDate(2005, 5, 6));
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByTransferDateTest() {
        Date pastDate = DateUtils.addYears(new Date(), -2);
        Date futureDate = DateUtils.addYears(new Date(), 2);
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setTransferFrom(pastDate);
        criteria.setTransferTo(futureDate);
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 3);

        criteria = new PlainDomainSearchCriteria();
        criteria.setTransferFrom(futureDate);
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 0);

        criteria = new PlainDomainSearchCriteria();
        criteria.setTransferTo(pastDate);
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
    }

    @Test
    public void findByClassAndCategoryTest() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setHolderClassId(5L);
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 7);

        criteria = new PlainDomainSearchCriteria();
        criteria.setHolderClassName("Unincorporated Association");
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 7);

        criteria = new PlainDomainSearchCriteria();
        criteria.setHolderCategoryId(4L);
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 7);

        criteria = new PlainDomainSearchCriteria();
        criteria.setHolderCategoryName("Registered Trade Mark Name");
        result = plainDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 7);
    }

    @Test
    public void findTransferredDomainsTest() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("ACB865-IEDR");
        LimitedSearchResult<PlainDomain> transferredDomains = plainDomainDAO.findTransferredInDomains(
                criteria, 0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(2, transferredDomains.getTotalResults());
        Assert.assertEquals(2, transferredDomains.getResults().size());
        transferredDomains = plainDomainDAO.findTransferredAwayDomains(criteria, 0, 10,
                Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(3, transferredDomains.getTotalResults());
        Assert.assertEquals(3, transferredDomains.getResults().size());
    }

    @Test
    public void findTransferredInDomainTestRenDt() {
        // setup made-up history of transfers
        Date currentDate = DateUtils.addMilliseconds(new Date(), 999);
        Date oldRenewalDate = DateUtils.addDays(currentDate, -5);
        Date middleRenewalDate = DateUtils.addDays(currentDate, -1);
        Date currentRenewalDate = DateUtils.addDays(currentDate, 1);
        Date firstTransfer = DateUtils.addDays(currentDate, -14);
        Date secondTransfer = DateUtils.addDays(firstTransfer, 7);

        String domainName = "transferDomainUC007SC05.ie";
        String losingNH = "AAE553-IEDR";
        Contact losingContact = new Contact(losingNH);
        String gainingNH = "IDL2-IEDR";
        Contact gainingContact = new Contact(gainingNH);
        Domain d = domainDAO.get(domainName);
        d.setRenewalDate(oldRenewalDate);
        d.setBillingContacts(Collections.singletonList(losingContact));
        domainDAO.updateUsingHistory(histDomainDAO.create(d, firstTransfer, TestOpInfo.DEFAULT.getActorName()));
        domainDAO.createTransferRecord(domainName, firstTransfer, losingNH, gainingNH);
        d.setBillingContacts(Collections.singletonList(gainingContact));
        d.setRenewalDate(middleRenewalDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, secondTransfer, TestOpInfo.DEFAULT.getActorName()));
        domainDAO.createTransferRecord(domainName, secondTransfer, losingNH, gainingNH);
        d.setRenewalDate(currentRenewalDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        // Whole history is setup, now we can actually test it
        // with current should always return current renewal date, no matter the direction.
        PlainDomainSearchCriteria criteriaForGaining = new PlainDomainSearchCriteria();
        criteriaForGaining.setBillingNH(gainingNH);
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.findTransferredInDomains(criteriaForGaining, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 4);
        PlainDomain d1 = result.getResults().get(2);
        PlainDomain d2 = result.getResults().get(3);
        Assert.assertEquals(d1.getRenewalDate(), DateUtils.truncate(currentRenewalDate, Calendar.DATE));
        Assert.assertEquals(d2.getRenewalDate(), DateUtils.truncate(currentRenewalDate, Calendar.DATE));
        Assert.assertEquals(d1.getTransferDate(), DateUtils.truncate(firstTransfer, Calendar.SECOND));
        Assert.assertEquals(d2.getTransferDate(), DateUtils.truncate(secondTransfer, Calendar.SECOND));
        Assert.assertEquals(d1.getRegistrationDate(), d.getRegistrationDate());
        Assert.assertEquals(d2.getRegistrationDate(), d.getRegistrationDate());
        Assert.assertEquals(d1.getHolder(), d.getHolder());
        Assert.assertEquals(d2.getHolder(), d.getHolder());

        PlainDomainSearchCriteria criteriaForLosing = new PlainDomainSearchCriteria();
        criteriaForLosing.setBillingNH(losingNH);
        result = plainDomainDAO.findTransferredAwayDomains(criteriaForLosing, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 2);
        d1 = result.getResults().get(0);
        d2 = result.getResults().get(1);
        Assert.assertEquals(d1.getRenewalDate(), DateUtils.truncate(oldRenewalDate, Calendar.DATE));
        Assert.assertEquals(d2.getRenewalDate(), DateUtils.truncate(middleRenewalDate, Calendar.DATE));
        Assert.assertEquals(d1.getTransferDate(), DateUtils.truncate(firstTransfer, Calendar.SECOND));
        Assert.assertEquals(d2.getTransferDate(), DateUtils.truncate(secondTransfer, Calendar.SECOND));
        Assert.assertEquals(d1.getRegistrationDate(), d.getRegistrationDate());
        Assert.assertEquals(d2.getRegistrationDate(), d.getRegistrationDate());
        Assert.assertEquals(d1.getHolder(), d.getHolder());
        Assert.assertEquals(d2.getHolder(), d.getHolder());
    }

    @Test
    public void findTransferDateWithNonEmptyHoursAnsMinutes() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDomainName("rough-dates.ie");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2004, Calendar.AUGUST, 29);
        Date transferDate = calendar.getTime();
        criteria.setTransferFrom(transferDate);
        criteria.setTransferTo(transferDate);
        criteria.setBillingNH("APITEST-IEDR");
        LimitedSearchResult<PlainDomain> transferredDomains = plainDomainDAO.findTransferredAwayDomains(
                criteria, 0, 10, null);
        Assert.assertEquals(1, transferredDomains.getTotalResults());
        Assert.assertEquals(1, transferredDomains.getResults().size());
    }

    @Test
    public void testLooseUtf8ValidationTransferredDomains() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("IDL2-IEDR\01");
        LimitedSearchResult<PlainDomain> result = plainDomainDAO.findTransferredAwayDomains(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
    }

    @Test
    public void creatingTransferRecordShouldAffectNumberOfTransferredDomains() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("NTG1-IEDR");
        LimitedSearchResult<PlainDomain> transferredDomains = plainDomainDAO.findTransferredAwayDomains(criteria,
                0, 10, Arrays.asList(new SortCriterion("domainName", true)));
        long numberOfResults = transferredDomains.getTotalResults();

        final Date transferDate = DateUtils.setMilliseconds(new Date(), 999);
        domainDAO.createTransferRecord("castlebargolfclub.ie", transferDate, "NTG1-IEDR", "NH4-IEDR");

        transferredDomains = plainDomainDAO.findTransferredAwayDomains(criteria, 0, 10,
                Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(numberOfResults + 1, transferredDomains.getTotalResults());
    }

    @Test
    public void testFindDomainByHolderType() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        assertEquals(85, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainHolderTypes(DomainHolderType.Charity);
        assertEquals(3, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainHolderTypes(DomainHolderType.NonBillable);
        assertEquals(0, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainHolderTypes(DomainHolderType.Billable, DomainHolderType.Charity,
                DomainHolderType.NonBillable);
        assertEquals(88, plainDomainDAO.find(criteria, 0, 200, null).getTotalResults());
    }

    @Test
    public void testFindDomainByRenewalMode() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        assertEquals(82, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainRenewalModes(RenewalMode.RenewOnce);
        assertEquals(1, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainRenewalModes(RenewalMode.Autorenew);
        assertEquals(5, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew, RenewalMode.RenewOnce, RenewalMode.Autorenew);
        assertEquals(88, plainDomainDAO.find(criteria, 0, 200, null).getTotalResults());
    }

    @Test
    public void testFindDomainByNrpStatus() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Active);
        assertEquals(72, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed);
        assertEquals(6, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setNrpStatuses(NRPStatus.VoluntaryMailed);
        assertEquals(4, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setNrpStatuses(NRPStatus.InvoluntarySuspended);
        assertEquals(3, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setNrpStatuses(NRPStatus.VoluntarySuspended);
        assertEquals(2, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.VoluntarySuspended);
        assertEquals(87, plainDomainDAO.find(criteria, 0, 200, null).getTotalResults());
        criteria.setActive(true);
        assertEquals(72, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setActive(false);
        assertEquals(18, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
    }

    @Test
    public void testFindDomainByLockingStatus() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setLocked(true);
        assertEquals(8, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
    }

    @Test
    public void testFindDomainBySecondaryMarketStatus() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        assertEquals(72, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NA);
        assertEquals(57, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.BuyRequestRegistered);
        assertEquals(7, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        assertEquals(9, plainDomainDAO.find(criteria, 0, 100, null).getTotalResults());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess, SecondaryMarketStatus.NA,
                SecondaryMarketStatus.BuyRequestRegistered, SecondaryMarketStatus.SellRequestRegistered);
        assertEquals(145, plainDomainDAO.find(criteria, 0, 200, null).getTotalResults());
    }
}
