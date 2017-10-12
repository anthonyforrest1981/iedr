package pl.nask.crs.domains.services;

import java.util.Arrays;
import java.util.List;

import mockit.Delegate;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.search.AbstractPlainDomainSearchCriteria;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

public class BulkTransferServiceTest extends AbstractContextAwareTest {
    @Autowired
    BulkTransferService service;

    @Autowired
    HistoricalDomainDAO historicalDomainDAO;

    @Autowired
    DomainSearchService domainSearchService;

    @Autowired
    DomainService domainService;

    @Autowired
    NicHandleService nicHandleService;

    @Autowired
    AuthenticationService authenticationService;

    @Mocked
    EmailTemplateSenderImpl emailTemplateSender;

    private long gainingAccount = 101;
    private long losingAccount = 113; // has only one domain: mhcb.ie
    private String domainName = "mhcb.ie";

    private String hostmaster = "IDL2-IEDR";

    @Test
    public void bulkTransferShouldLeaveTraceInDomainHist() throws Exception {
        // having
        prepareDomainsForTransfer();
        int domainHistCount = historicalDomainDAO.count(new HistoricalDomainSearchCriteria(domainName));
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH(hostmaster);
        LimitedSearchResult<PlainDomain> transfers = domainSearchService.findTransferredInDomains(criteria, 0, 1000,
                null);
        // when
        makeBulkTransferWithDomain(true);
        // Two hist entries will be created: one when the domain is prepared for the bulk transfer
        // and another when DSM event is completed
        int newDomainHistCount = historicalDomainDAO.count(new HistoricalDomainSearchCriteria(domainName));
        LimitedSearchResult<PlainDomain> newTransfers = domainSearchService.findTransferredInDomains(criteria, 0, 1000,
                null);
        AssertJUnit.assertEquals(domainHistCount + 2, newDomainHistCount);
        AssertJUnit.assertEquals(transfers.getTotalResults() + 1, newTransfers.getTotalResults());
    }

    @Test
    public void bulkTransferShouldCreateATransferRecord() throws Exception {
        prepareDomainsForTransfer();
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH(hostmaster);
        LimitedSearchResult<PlainDomain> transfers = domainSearchService
                .findTransferredInDomains(criteria, 0, 1000, null);
        Domain beforeTransfer = domainSearchService.getDomain(domainName);
        makeBulkTransferWithDomain(true);
        LimitedSearchResult<PlainDomain> newTransfers = domainSearchService
                .findTransferredInDomains(criteria, 0, 1000, null);
        AssertJUnit.assertEquals(transfers.getTotalResults() + 1, newTransfers.getTotalResults());
        PlainDomain transferredDomain = newTransfers.getResults().get(newTransfers.getResults().size() - 1);
        Domain currentDomain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(currentDomain.getTransferDate(), transferredDomain.getTransferDate());
        AssertJUnit.assertTrue(currentDomain.getChangeDate().compareTo(transferredDomain.getTransferDate()) >= 0);
        AssertJUnit
                .assertNotSame(currentDomain.getResellerAccount().getId(), beforeTransfer.getResellerAccount().getId());
    }

    private void prepareDomainsForTransfer() throws Exception {
        prepareDomainsForTransfer(17);
    }

    private void prepareDomainsForTransfer(int dsmState) throws Exception {
        domainService.forceDSMState(Arrays.asList(domainName), dsmState, new TestOpInfo(hostmaster, null, "test")); // make sure the domain is in the right state
        nicHandleService.alterStatus("TDI2-IEDR", NicHandleStatus.Active, new TestOpInfo(hostmaster, "test"));
    }

    private long makeBulkTransferWithDomain(boolean transferAll) throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "ws");
        long transferId = service.createBulkTransferProcess(losingAccount, gainingAccount, "new bulk transfer request");
        service.addDomains(transferId, Arrays.asList(domainName));
        if (transferAll) {
            service.transferAll(user, transferId);
        } else {
            service.transferValid(user, transferId);
        }
        return transferId;
    }

    @Test
    public void emailId84ShouldBeSendWhenClosingTransferRequest() throws Exception {
        // having
        prepareDomainsForTransfer();
        long transferId = makeBulkTransferWithDomain(false);
        // expect
        expectationsForEmail84();
        // when
        service.closeTransferRequest(transferId, "nostmaster");
    }

    private void expectationsForEmail84() throws Exception {
        new NonStrictExpectations() {
            {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.BULK_TRANSFER_COMPLETED.getId(),
                        withInstanceOf(EmailParameters.class));
                minTimes = 1;
                result = new Delegate() {
                    void validate(int templateId, EmailParameters params) {
                        Assert.assertNotNull(params.getParameterValue(ParameterNameEnum.BILL_C_NAME.getName(), false));
                    }
                };
            }
        };
    }

    @Test
    public void emailId84ShouldBeSendWhenForcedCloseOfTransferRequest() throws Exception {
        // having
        prepareDomainsForTransfer();
        long transferId = makeBulkTransferWithDomain(false);
        // expect
        expectationsForEmail84();
        // when
        service.forceCloseTransferRequest(transferId, "nostmaster");
    }

    @Test
    public void emailId84ShouldBeSendWhenTransferringAllDomains() throws Exception {
        // having
        prepareDomainsForTransfer();
        // expect
        expectationsForEmail84();
        // when
        long transferId = makeBulkTransferWithDomain(true);
    }

    @Test
    public void lockedDomainShouldNotBeTransferred() throws Exception {
        prepareDomainsForTransfer();
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false,
                null, true, "ws");
        domainService.lock(user, domainName, new TestOpInfo(user.getUsername(), "Remark"));
        Domain domain = domainSearchService.getDomain(domainName);
        Assert.assertTrue(domain.getDsmState().getLocked());
        try {
            makeBulkTransferWithDomain(true);
        } catch (BulkTransferValidationException e) {
            List<String> errors = e.getValidationErrors();
            Assert.assertEquals(errors.size(), 1);
            Assert.assertEquals(errors.get(0), String.format("%s is locked", domainName));
            return;
        }
        Assert.fail("Bulk transfer should have failed");
    }

    @Test
    public void domainShouldGetNoAutorenewOnTransfer() throws Exception {
        prepareDomainsForTransfer(81);
        Domain domain = domainSearchService.getDomain(domainName);
        Assert.assertEquals(domain.getDsmState().getRenewalMode(), RenewalMode.Autorenew);
        makeBulkTransferWithDomain(true);
        domain = domainSearchService.getDomain(domainName);
        Assert.assertEquals(domain.getDsmState().getRenewalMode(), RenewalMode.NoAutorenew);
    }

}
