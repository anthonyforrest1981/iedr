package pl.nask.crs.domains.dsm;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.permissions.Helper;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dsm.events.*;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.services.TicketService;

public class DomainStateMachineTest extends AbstractContextAwareTest {
    private String domainName = "dsm0.ie";

    private DsmEvent validEvent = new CreateBillableDomainRegistrar(new Date());
    private DsmEvent invalidEvent = EnterVoluntaryNRP.getInstance();

    @Autowired
    DomainStateMachine dsm;

    @Autowired
    private DsmDAO dsmDao;

    @Autowired
    DsmTestDAO dsmTestDAO;

    @Autowired
    private DsmActionFactory dsmActionFactory;

    @Autowired
    private DomainDAO domainDao;

    @Autowired
    private HistoricalDomainDAO historicalDomainDao;

    @Autowired
    private TicketDAO ticketDao;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BuyRequestDAO buyRequestDAO;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ApplicationConfig appConfig;

    private AuthenticatedUser user;

    @BeforeMethod
    public void setUpUser() throws Exception {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void testValidateEventWithValidName() {
        boolean res = dsm.validateEvent(domainName, validEvent.getName());
        AssertJUnit.assertTrue("Event is valid", res);
    }

    @Test
    public void testValidateEventWithInvalidName() {
        boolean res = dsm.validateEvent(domainName, invalidEvent.getName());
        AssertJUnit.assertFalse("Event is valid", res);
    }

    @Test
    public void testGetValidEventNames() {
        List<String> eventNames = dsm.getValidEventNames(domainName);
        AssertJUnit.assertEquals("Number of valid events for domain in DSM_State==0", 5, eventNames.size());
        AssertJUnit.assertTrue(DsmEventName.CreateBillableDomainRegistrar.name(),
                eventNames.contains(DsmEventName.CreateBillableDomainRegistrar.name()));
    }

    @Test
    public void testHandleValidEvent() throws DomainNotFoundException, DomainIllegalStateException {
        Domain domain = domainDao.get(domainName);
        AssertJUnit.assertTrue(domain.getDsmState().getId() == 0);
        // this will change the domain state
        dsm.handleEvent(user, domainName, validEvent, TestOpInfo.DEFAULT);

        domain = domainDao.get(domainName);
        AssertJUnit.assertTrue(domain.getDsmState().getId() != 0);
    }

    @Test
    public void testCheckDsmStateAfterEvent() throws DomainNotFoundException, DomainIllegalStateException {
        Domain domain = domainDao.get(domainName);
        DsmState state = domain.getDsmState();
        AssertJUnit.assertEquals(0, state.getId());
        AssertJUnit.assertEquals(CustomerType.NA, state.getCustomerType());
        AssertJUnit.assertEquals(DomainHolderType.NA, state.getDomainHolderType());
        AssertJUnit.assertEquals(NRPStatus.NA, state.getNrpStatus());
        AssertJUnit.assertEquals(RenewalMode.NA, state.getRenewalMode());
        Assert.assertNull(state.getLocked());
        // this will change the domain state
        dsm.handleEvent(user, domainName, validEvent, TestOpInfo.DEFAULT);

        domain = domainDao.get(domainName);
        state = domain.getDsmState();
        AssertJUnit.assertEquals(17, state.getId());
        AssertJUnit.assertEquals(CustomerType.Registrar, state.getCustomerType());
        AssertJUnit.assertEquals(DomainHolderType.Billable, state.getDomainHolderType());
        AssertJUnit.assertEquals(NRPStatus.Active, state.getNrpStatus());
        AssertJUnit.assertEquals(RenewalMode.NoAutorenew, state.getRenewalMode());
        AssertJUnit.assertEquals(false, (boolean) state.getLocked());
    }

    @Test
    public void testCheckDsmStateInDomainHist() throws DomainNotFoundException, DomainIllegalStateException {
        dsm.handleEvent(user, domainName, validEvent, TestOpInfo.DEFAULT);
        HistoricalDomainSearchCriteria crit = new HistoricalDomainSearchCriteria();
        crit.setDomainName(domainName);

        SearchResult<HistoricalObject<Domain>> res = historicalDomainDao.find(crit);
        AssertJUnit.assertEquals(2, res.getResults().size());
        AssertJUnit.assertEquals(0, res.getResults().get(0).getObject().getDsmState().getId());
        AssertJUnit.assertEquals(17, res.getResults().get(1).getObject().getDsmState().getId());
    }

    @Test
    public void test() {
        boolean valid = dsm.validateEvent("createCCDomain.ie", DsmEventName.TransferRequest);
        AssertJUnit.assertTrue(valid);
    }

    @Test
    public void testTransitionActions() {
        DsmTransition trans = dsmDao.getTransitionFor("castlebargolfclub.ie", DsmEventName.Lock);
        List<DsmAction> preActions = trans.getPreActions(dsmActionFactory);
        List<DsmAction> postActions = trans.getPostActions(dsmActionFactory);
        Assert.assertEquals(preActions.size(), 1, preActions.toString());
        Assert.assertEquals(postActions.size(), 1, postActions.toString());
    }

    @Test
    public void testTransitionEnterVolNRP() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date expectedSuspensionDate = DateUtils.addDays(new Date(), appConfig.getNRPConfig().getNrpMailedPeriod() + 1);
        Date expectedDeletionDate = DateUtils.addDays(expectedSuspensionDate, appConfig.getNRPConfig()
                .getNrpSuspendedPeriod());
        DsmEvent event = EnterVoluntaryNRP.getInstance();
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        List<DsmState> validStatesForVolNRP = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(validStatesForVolNRP.isEmpty());
        for (DsmState dsmState : validStatesForVolNRP) {
            Domain domain = domainDao.get(domainName);
            domain.setSuspensionDate(null);
            domain.setDeletionDate(null);
            updateDomainWithState(domain, dsmState);
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertTrue(domain.getDsmState().getNrpStatus().isNRP());
            Assert.assertTrue(DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate),
                    "Expected suspensionDate to be: " + expectedSuspensionDate + " but got "
                            + domain.getSuspensionDate());
            Assert.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), expectedDeletionDate),
                    "Expected deletionDate to be: " + expectedDeletionDate + " but got " + domain.getDeletionDate());
        }
    }

    @Test
    public void testTransitionActiveToNRP() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date expectedSuspensionDate = DateUtils.addDays(new Date(), appConfig.getNRPConfig().getNrpMailedPeriod() + 1);
        Date expectedDeletionDate = DateUtils.addDays(expectedSuspensionDate, appConfig.getNRPConfig()
                .getNrpSuspendedPeriod());
        DsmEvent event = new RenewalDatePasses();
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive);
        criteria.setHolderTypes(DomainHolderType.Billable);
        List<DsmState> statesValidForTransitionToNrp = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(statesValidForTransitionToNrp.isEmpty());
        for (DsmState dsmState : statesValidForTransitionToNrp) {
            Domain domain = domainDao.get(domainName);
            domain.setSuspensionDate(null);
            domain.setDeletionDate(null);
            updateDomainWithState(domain, dsmState);
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertTrue(domain.getDsmState().getNrpStatus().isNRP());
            Assert.assertTrue(DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate),
                    String.format("Expected suspensionDate to be %s but got %s for DSM State %s",
                            expectedSuspensionDate, domain.getSuspensionDate(), dsmState.getId()));
            Assert.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), expectedDeletionDate),
                    String.format("Expected deletionDate to be %s but got %s for DSM State %s",
                            expectedDeletionDate, domain.getDeletionDate(), dsmState.getId()));
        }
    }

    @Test
    public void testTransitionNRPMailedToSuspended() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date suspensionDate = new Date();
        Date deletionDate = DateUtils.addDays(suspensionDate, appConfig.getNRPConfig().getNrpSuspendedPeriod());
        DsmEvent event = new SuspensionDatePasses();
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed,
                NRPStatus.InvoluntaryMailedPaymentPending);
        List<DsmState> statesValidForNRPMailedToSuspendedTransition = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(statesValidForNRPMailedToSuspendedTransition.isEmpty());
        for (DsmState dsmState : statesValidForNRPMailedToSuspendedTransition) {
            Domain domain = domainDao.get(domainName);
            domain.setSuspensionDate(suspensionDate);
            domain.setDeletionDate(deletionDate);
            updateDomainWithState(domain, dsmState);
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertTrue(domain.getDsmState().getNrpStatus().isNRP());
            Assert.assertNull(domain.getSuspensionDate());
            Assert.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), deletionDate),
                    "Expected deletionDate to be: " + deletionDate + " but got " + domain.getDeletionDate());
        }
    }

    @Test
    public void testTransitionNRPToActive() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date suspensionDate;
        Date deletionDate;
        Map<DsmEvent, List<DsmState>> nrpToActiveEventsMap = prepareNrpToActiveEventsMap();

        for (Map.Entry<DsmEvent, List<DsmState>> entry : nrpToActiveEventsMap.entrySet()) {
            DsmEvent event = entry.getKey();
            List<DsmState> dsmStates = entry.getValue();
            Assert.assertFalse(dsmStates.isEmpty());
            for (DsmState dsmState : dsmStates) {
                Domain domain = domainDao.get(domainName);
                if (dsmState.getNrpStatus() == NRPStatus.VoluntaryMailed
                        || dsmState.getNrpStatus() == NRPStatus.InvoluntaryMailed) {
                    suspensionDate = new Date();
                    deletionDate = DateUtils.addDays(suspensionDate, appConfig.getNRPConfig().getNrpSuspendedPeriod());
                } else {
                    suspensionDate = null;
                    deletionDate = new Date();
                }
                domain.setSuspensionDate(suspensionDate);
                domain.setDeletionDate(deletionDate);
                updateDomainWithState(domain, dsmState);
                dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
                domain = domainDao.get(domainName);
                Assert.assertFalse(domain.getDsmState().getNrpStatus().isNRP());
                Assert.assertNull(domain.getSuspensionDate());
                Assert.assertNull(domain.getDeletionDate());
            }
        }
    }

    private Map<DsmEvent, List<DsmState>> prepareNrpToActiveEventsMap() {
        // Non-transfer events that make a domain active when applied
        DsmStateSearchCriteria criteria;
        Map<DsmEvent, List<DsmState>> eventsMap = new HashMap<>();

        criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setNrpStatuses(NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended);
        eventsMap.put(new PaymentSettledEvent(1), dsmTestDAO.findDsmStates(criteria));

        criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable, DomainHolderType.NonBillable);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        eventsMap.put(new ParameterlessEvent(DsmEventName.SetCharity), dsmTestDAO.findDsmStates(criteria));

        criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable, DomainHolderType.Charity);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        eventsMap.put(new ParameterlessEvent(DsmEventName.SetNonBillable), dsmTestDAO.findDsmStates(criteria));

        criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended);
        eventsMap.put(new RemoveFromVoluntaryNRP(), dsmTestDAO.findDsmStates(criteria));
        return eventsMap;
    }

    private List<? extends DsmEvent> createTransferEventList() throws Exception {
        String domainName = "castlebargolfclub.ie";
        TicketRequest request = Helper.prepareTicketRequest(domainName, "AA11-IEDR");
        long ticketId = ticketService.createTransferTicket(user, request, 103L);
        Ticket ticket = ticketDao.get(ticketId);
        return Arrays.asList(new TransferToDirect(ticket), new TransferToRegistrar(ticket),
                new TransferWithAutorenewToRegistrar(ticket), new TransferCancellation(ticket));
    }

    @Test
    public void testTransferInvalidForNonTransferStates() throws Exception {
        String domainName = "castlebargolfclub.ie";
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended, NRPStatus.InvoluntaryMailedPaymentPending,
                NRPStatus.InvoluntarySuspendedPaymentPending);
        List<DsmState> nonTransferDsmStates = dsmTestDAO.findDsmStates(criteria);
        List<? extends DsmEvent> transferEvents = createTransferEventList();
        Assert.assertFalse(nonTransferDsmStates.isEmpty());
        for (DsmState dsmState : nonTransferDsmStates) {
            for (DsmEvent event : transferEvents) {
                Domain domain = domainDao.get(domainName);
                updateDomainWithState(domain, dsmState);
                Assert.assertFalse(dsm.validateEvent(domainName, event));
            }
        }
    }

    @Test
    public void testTransferTransitionNRPMailed() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date suspensionDate = new Date();
        Date deletionDate = DateUtils.addDays(suspensionDate, appConfig.getNRPConfig().getNrpSuspendedPeriod());
        DsmEventName event = DsmEventName.TransferRequest;
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        List<DsmState> nrpMailedStates = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(nrpMailedStates.isEmpty());
        for (DsmState dsmState : nrpMailedStates) {
            Domain domain = domainDao.get(domainName);
            domain.setSuspensionDate(suspensionDate);
            domain.setDeletionDate(deletionDate);
            updateDomainWithState(domain, dsmState);
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertTrue(domain.getDsmState().getNrpStatus().isNRP());
            Assert.assertNull(domain.getSuspensionDate());
            Assert.assertNull(domain.getDeletionDate());
        }
    }

    @Test
    public void testTransferTransitionNRPSuspended() throws Exception {
        String domainName = "castlebargolfclub.ie";
        Date deletionDate = new Date();
        DsmEventName event = DsmEventName.TransferRequest;
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.InvoluntaryMailed, NRPStatus.VoluntaryMailed);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        List<DsmState> nrpSuspendedStates = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(nrpSuspendedStates.isEmpty());
        for (DsmState dsmState : nrpSuspendedStates) {
            Domain domain = domainDao.get(domainName);
            domain.setSuspensionDate(null);
            domain.setDeletionDate(deletionDate);
            updateDomainWithState(domain, dsmState);
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertTrue(domain.getDsmState().getNrpStatus().isNRP());
            Assert.assertNull(domain.getSuspensionDate());
            Assert.assertNull(domain.getDeletionDate());
        }
    }

    @Test
    public void testVNRPInvalidForTransferStates() throws Exception {
        String domainName = "castlebargolfclub.ie";
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP,
                NRPStatus.TransferPendingInvNRP);
        List<DsmState> transferStates = dsmTestDAO.findDsmStates(criteria);
        Assert.assertFalse(transferStates.isEmpty());
        for (DsmState dsmState : transferStates) {
            Domain domain = domainDao.get(domainName);
            updateDomainWithState(domain, dsmState);
            Assert.assertFalse(dsm.validateEvent(domainName, DsmEventName.EnterVoluntaryNRP));
        }
    }

    @Test
    public void testCompletingSellRequestResetsHolderAndRenewalMode() throws Exception {
        String domainName = "castlebargolfclub.ie";
        BuyRequest buyRequest = buyRequestDAO.get(4L);
        SellRequest sellRequest = new SellRequest(user.getUsername(), new Date(), buyRequest);
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Charity, DomainHolderType.NonBillable);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        List<DsmState> validStates = dsmTestDAO.findDsmStates(criteria);
        criteria = new DsmStateSearchCriteria();
        criteria.setRenewalModes(RenewalMode.RenewOnce, RenewalMode.Autorenew);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        validStates.addAll(dsmTestDAO.findDsmStates(criteria));
        Assert.assertFalse(validStates.isEmpty());
        for (DsmState dsmState : validStates) {
            Domain domain = domainDao.get(domainName);
            updateDomainWithState(domain, dsmState);
            DsmEvent event = new CompleteSellRequest(sellRequest, "AHK010-IEDR", new Date());
            dsm.handleEvent(user, domainName, event, TestOpInfo.DEFAULT);
            domain = domainDao.get(domainName);
            Assert.assertEquals(domain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.NoProcess);
            Assert.assertEquals(domain.getDsmState().getDomainHolderType(), DomainHolderType.Billable);
            Assert.assertEquals(domain.getDsmState().getRenewalMode(), RenewalMode.NoAutorenew);
        }
    }

    @Test
    public void testStatesForCreateBillableDomainRegistrar() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(0);
        testStatesForEvent(DsmEventName.CreateBillableDomainRegistrar, criteria);
    }

    @Test
    public void testStatesForCreateBillableDomainDirect() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(0);
        testStatesForEvent(DsmEventName.CreateBillableDomainDirect, criteria);
    }

    @Test
    public void testStatesForCreateCharityDomainRegistrar() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(0);
        testStatesForEvent(DsmEventName.CreateCharityDomainRegistrar, criteria);
    }

    @Test
    public void testStatesForCreateCharityDomainDirect() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(0);
        testStatesForEvent(DsmEventName.CreateCharityDomainDirect, criteria);
    }

    @Test
    public void testStatesForDeletedDomainRemoval() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(385);
        testStatesForEvent(DsmEventName.DeletedDomainRemoval, criteria);
    }

    @Test
    public void testStatesForRenewalDatePasses() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP);
        testStatesForEvent(DsmEventName.RenewalDatePasses, criteria);
    }

    @Test
    public void testStatesForSuspensionDatePasses() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.VoluntaryMailed, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntaryMailedPaymentPending);
        testStatesForEvent(DsmEventName.SuspensionDatePasses, criteria);
    }

    @Test
    public void testStatesForDeletionDatePasses() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.VoluntarySuspended, NRPStatus.InvoluntarySuspended,
                NRPStatus.InvoluntarySuspendedPaymentPending);
        testStatesForEvent(DsmEventName.DeletionDatePasses, criteria);
    }

    @Test
    public void testStatesForEnterVoluntaryNRP() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.EnterVoluntaryNRP, criteria);
    }

    @Test
    public void testStatesForRemoveFromVoluntaryNRP() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.TransferPendingVolNRP);
        testStatesForEvent(DsmEventName.RemoveFromVoluntaryNRP, criteria);
    }

    @Test
    public void testStatesForPaymentInitiated() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended);
        testStatesForEvent(DsmEventName.PaymentInitiated, criteria);
    }

    @Test
    public void testStatesForPaymentSettled() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.VoluntaryMailed,
                NRPStatus.VoluntarySuspended, NRPStatus.TransferPendingVolNRP, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP,
                NRPStatus.InvoluntaryMailedPaymentPending, NRPStatus.InvoluntarySuspendedPaymentPending);
        testStatesForEvent(DsmEventName.PaymentSettled, criteria);
    }

    @Test
    public void testStatesForPaymentCancelled() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.VoluntaryMailed,
                NRPStatus.VoluntarySuspended, NRPStatus.TransferPendingVolNRP, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP,
                NRPStatus.InvoluntaryMailedPaymentPending, NRPStatus.InvoluntarySuspendedPaymentPending);
        testStatesForEvent(DsmEventName.PaymentCancelled, criteria);
    }

    @Test
    public void testStatesForTransferRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended, NRPStatus.InvoluntaryMailedPaymentPending,
                NRPStatus.InvoluntarySuspendedPaymentPending);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        testStatesForEvent(DsmEventName.TransferRequest, criteria);
    }

    @Test
    public void testStatesForTransferCancellation() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP,
                NRPStatus.TransferPendingInvNRP);
        testStatesForEvent(DsmEventName.TransferCancellation, criteria);
    }

    @Test
    public void testStatesForTransferToDirect() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP,
                NRPStatus.TransferPendingInvNRP);
        testStatesForEvent(DsmEventName.TransferToDirect, criteria);
    }

    @Test
    public void testStatesForTransferToRegistrar() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP,
                NRPStatus.TransferPendingInvNRP);
        testStatesForEvent(DsmEventName.TransferToRegistrar, criteria);
    }

    @Test
    public void testStatesForSetAutoRenew() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setCustomerTypes(CustomerType.Registrar);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP,
                NRPStatus.InvoluntaryMailedPaymentPending, NRPStatus.InvoluntarySuspendedPaymentPending);
        criteria.setRenewalModes(RenewalMode.NoAutorenew, RenewalMode.RenewOnce);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetAutoRenew, criteria);
    }

    @Test
    public void testStatesForSetNoAutoRenew() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setCustomerTypes(CustomerType.Registrar);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP,
                NRPStatus.InvoluntaryMailedPaymentPending, NRPStatus.InvoluntarySuspendedPaymentPending);
        criteria.setRenewalModes(RenewalMode.Autorenew, RenewalMode.RenewOnce);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetNoAutoRenew, criteria);
    }

    @Test
    public void testStatesForSetOnceAutoRenew() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setCustomerTypes(CustomerType.Registrar);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive);
        criteria.setRenewalModes(RenewalMode.Autorenew, RenewalMode.NoAutorenew);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetOnceAutoRenew, criteria);
    }

    @Test
    public void testStatesForSetCharity() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable, DomainHolderType.NonBillable);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetCharity, criteria);
    }

    @Test
    public void testStatesForSetBillable() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Charity, DomainHolderType.NonBillable);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetBillable, criteria);
    }

    @Test
    public void testStatesForSetNonBillable() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setHolderTypes(DomainHolderType.Billable, DomainHolderType.Charity);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.SetNonBillable, criteria);
    }

    @Test
    public void testStatesForSetIEDRUnpublished() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        List<DsmState> validStates = dsmTestDAO.findDsmStates(criteria);
        criteria = new DsmStateSearchCriteria();
        criteria.setId(628);
        validStates.addAll(dsmTestDAO.findDsmStates(criteria));
        testStatesForEvent(DsmEventName.SetIEDRUnpublished, validStates);
    }

    @Test
    public void testStatesForSetIEDRPublished() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.TransferPendingActive, NRPStatus.InvoluntaryMailed,
                NRPStatus.InvoluntarySuspended, NRPStatus.TransferPendingInvNRP);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        List<DsmState> validStates = dsmTestDAO.findDsmStates(criteria);
        criteria = new DsmStateSearchCriteria();
        criteria.setId(386);
        validStates.addAll(dsmTestDAO.findDsmStates(criteria));
        testStatesForEvent(DsmEventName.SetIEDRPublished, validStates);
    }

    @Test
    public void testStatesForLock() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.Lock, criteria);
    }

    @Test
    public void testStatesForUnlock() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(true);
        testStatesForEvent(DsmEventName.Unlock, criteria);
    }

    @Test
    public void testStatesForCreateBillableAutorenewedDomainRegistrar() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setId(0);
        testStatesForEvent(DsmEventName.CreateBillableAutorenewedDomainRegistrar, criteria);
    }

    @Test
    public void testStatesForTransferWithAutorenewToRegistrar() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setHolderTypes(DomainHolderType.Billable);
        criteria.setNrpStatuses(NRPStatus.TransferPendingActive, NRPStatus.TransferPendingVolNRP,
                NRPStatus.TransferPendingInvNRP);
        testStatesForEvent(DsmEventName.TransferWithAutorenewToRegistrar, criteria);
    }

    @Test
    public void testStatesForBulkTransfer() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setCustomerTypes(CustomerType.Registrar);
        criteria.setNrpStatuses(NRPStatus.Active, NRPStatus.VoluntaryMailed, NRPStatus.VoluntarySuspended,
                NRPStatus.InvoluntaryMailed, NRPStatus.InvoluntarySuspended, NRPStatus.InvoluntaryMailedPaymentPending,
                NRPStatus.InvoluntarySuspendedPaymentPending);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        testStatesForEvent(DsmEventName.BulkTransfer, criteria);
    }

    @Test
    public void testStatesForRegisterBuyRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setNrpStatuses(NRPStatus.Active);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.RegisterBuyRequest, criteria);
    }

    @Test
    public void testStatesForCancelLastBuyRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.CancelLastBuyRequest, criteria);
    }

    @Test
    public void testStatesForRegisterSellRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setLocked(false);
        criteria.setNrpStatuses(NRPStatus.Active);
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess,
                SecondaryMarketStatus.BuyRequestRegistered);
        testStatesForEvent(DsmEventName.RegisterSellRequest, criteria);
    }

    @Test
    public void testStatesForCancelSellRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        testStatesForEvent(DsmEventName.CancelSellRequest, criteria);
    }

    @Test
    public void testStatesForApplySellRequest() {
        DsmStateSearchCriteria criteria = new DsmStateSearchCriteria();
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        testStatesForEvent(DsmEventName.CompleteSellRequest, criteria);
    }

    private void updateDomainWithState(Domain domain, DsmState dsmState) {
        domainDao.updateUsingHistory(historicalDomainDao.create(domain, dsmState.getId(), new Date(), "TEST-IEDR"));
    }

    private void testStatesForEvent(DsmEventName eventName, DsmStateSearchCriteria criteria) {
        List<DsmState> validStates = dsmTestDAO.findDsmStates(criteria);
        testStatesForEvent(eventName, validStates);
    }

    private void testStatesForEvent(DsmEventName eventName, List<DsmState> validStates) {
        Assert.assertFalse(validStates.isEmpty());
        for (DsmState dsmState : validStates) {
            Domain domain = domainDao.get(domainName);
            updateDomainWithState(domain, dsmState);
            Assert.assertTrue(dsm.validateEvent(domainName, eventName),
                    String.format("Expected %s event to be VALID for domain in state %s", eventName, dsmState.getId()));
        }
        checkEventNotValidForAllOtherStates(eventName, validStates);
    }

    private void checkEventNotValidForAllOtherStates(DsmEventName eventName, List<DsmState> validStates) {
        List<DsmState> allStates = dsmTestDAO.findDsmStates(new DsmStateSearchCriteria());
        List<DsmState> invalidStates = new ArrayList<>();
        Set<Integer> validStateIds = new HashSet<>();
        for (DsmState dsmState : validStates) {
            validStateIds.add(dsmState.getId());
        }
        for (DsmState dsmState : allStates) {
            if (!validStateIds.contains(dsmState.getId())) {
                invalidStates.add(dsmState);
            }
        }
        Assert.assertFalse(invalidStates.isEmpty());
        for (DsmState dsmState : invalidStates) {
            Domain domain = domainDao.get(domainName);
            updateDomainWithState(domain, dsmState);
            Assert.assertFalse(dsm.validateEvent(domainName, eventName),
                    String.format("Expected %s event to be INVALID for domain in state %s",
                            eventName, dsmState.getId()));
        }
    }

}
