package pl.nask.crs.api.domain;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.app.domains.PushQSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.ExtendedDomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

@ContextConfiguration(locations = {"/crs-api-config.xml"})
public class DomainPushQTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    CRSDomainAppService service;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    PushQSupportService pushQSupportService;

    @Resource
    PaymentService paymentService;

    @Resource
    DepositService depositService;

    @Resource
    DomainDAO domainDAO;

    @Resource
    ExtendedDomainDAO extendedDomainDAO;

    @Resource
    HistoricalDomainDAO historicalDomainDAO;

    @Test
    public void pushQTest() throws Exception {
        AuthenticatedUser user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        List<Domain> toDelete = getDomainToDelete();
        List<Domain> deletionDatePassing = getDomainWithDeletionDatePassed();
        List<Domain> suspensionDatePassing = getDomainWithSuspensionDatePassed();
        List<Domain> suspensionDateExpected = getDomainWithSuspensionDateExpected();
        List<Domain> renewalDatePassing = getDomainWithRenewalDatePassed();
        AssertJUnit.assertEquals("to delete", 2, toDelete.size());
        AssertJUnit.assertEquals("deletion date passing", 2, deletionDatePassing.size());
        AssertJUnit.assertEquals("suspension date passing", 2, suspensionDatePassing.size());
        AssertJUnit.assertEquals("suspension date expected", 0, suspensionDateExpected.size());
        AssertJUnit.assertEquals("renewal date passing", 45, renewalDatePassing.size());

        String domainWithPendingReservation = "createDomainRegistrarBasic.ie";
        String olderDomainWithPendingReservation = "createCCDomain.ie";
        String newlyTransferred = "transferred.ie";
        String previouslyTransferred = "transferred2.ie";

        setRenewalDateWithPendingReservation(domainWithPendingReservation, 1);
        setRenewalDateWithPendingReservation(olderDomainWithPendingReservation, 2);
        setTransferDate(newlyTransferred, 0);
        setTransferDate(previouslyTransferred, 1);

        pushQSupportService.pushQ(user, new OpInfo(user));

        toDelete = getDomainToDelete();
        deletionDatePassing = getDomainWithDeletionDatePassed();
        suspensionDatePassing = getDomainWithSuspensionDatePassed();
        suspensionDateExpected = getDomainWithSuspensionDateExpected();
        renewalDatePassing = getDomainWithRenewalDatePassed();
        AssertJUnit.assertEquals("after job: to delete", 0, toDelete.size());
        AssertJUnit.assertEquals("after job: deletion date passing", 2, deletionDatePassing.size());
        // Setting suspension date basing on passed renewal date is no longer used
        // Now it is always set 39 days from the current date
        AssertJUnit.assertEquals("after job: suspension date passing", 0, suspensionDatePassing.size());
        // Domains in DSM states: 1, 17, 25, 49, 65, 81, 1041, 2065 (except pending reservations and new transfers)
        AssertJUnit.assertEquals("after job: suspension date expected", 40, suspensionDateExpected.size());
        // Domains in DSM states: 113, 121
        AssertJUnit.assertEquals("after job: renewal date passing", 5, renewalDatePassing.size());
        verifyNRP(olderDomainWithPendingReservation);
        verifyNotNRP(domainWithPendingReservation);
        verifyNRP(previouslyTransferred);
        verifyNotNRP(newlyTransferred);
    }

    private List<Domain> getDomainToDelete() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Deleted);
        return domainSearchService.findAll(criteria, null);
    }

    private List<Domain> getDomainWithRenewalDatePassed() {
        Date yesterday = DateUtils.getCurrDate(-1);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewalTo(yesterday);
        criteria.setNrpStatuses(NRPStatus.Active);
        return domainSearchService.findAll(criteria, null);
    }

    private List<Domain> getDomainWithSuspensionDatePassed() {
        Date yesterday = DateUtils.getCurrDate(-1);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspensionTo(yesterday);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed);
        return domainSearchService.findAll(criteria, null);
    }

    private List<Domain> getDomainWithSuspensionDateExpected() {
        Date currentDate = DateUtils.getCurrDate(0);
        Date expectedSuspension = DateUtils.getCurrDate(39);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspensionFrom(currentDate);
        criteria.setSuspensionTo(expectedSuspension);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed);
        return domainSearchService.findAll(criteria, null);
    }

    private List<Domain> getDomainWithDeletionDatePassed() {
        Date yesterday = DateUtils.getCurrDate(-1);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDeletionTo(yesterday);
        criteria.setNrpStatuses(NRPStatus.InvoluntarySuspended, NRPStatus.VoluntarySuspended);
        return domainSearchService.findAll(criteria, null);
    }

    private void setRenewalDateWithPendingReservation(String domainName, int daysAfterRenewal) {
        ExtendedDomain domain = extendedDomainDAO.get(domainName);
        AssertJUnit.assertTrue(domain.hasPendingReservations());
        domain.setRenewalDate(DateUtils.getCurrDate(-daysAfterRenewal));
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName()));
    }

    private void setTransferDate(String domainName, int daysAfterTransfer) {
        Domain domain = domainDAO.get(domainName);
        domain.setTransferDate(DateUtils.getCurrDate(-daysAfterTransfer));
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName()));
    }

    private void verifyNRP(String domainName) {
        Domain domain = domainDAO.get(domainName);
        AssertJUnit.assertEquals(18, domain.getDsmState().getId());
        AssertJUnit.assertEquals(DateUtils.startOfDay(DateUtils.getCurrDate(39)), domain.getSuspensionDate());
    }

    private void verifyNotNRP(String domainName) {
        Domain domain = domainDAO.get(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        AssertJUnit.assertNull(domain.getSuspensionDate());
    }

}
