package pl.nask.crs.api.triplepass;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.common.CRSCommonAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketHistorySearchService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

import mockit.Mocked;
import mockit.NonStrictExpectations;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public abstract class TriplePassTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    TicketService ticketService;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    TicketHistorySearchService ticketHistorySearchService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    DomainService domainService;

    @Resource
    TicketAppService ticketAppService;

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    DomainStateMachine dsm;

    @Resource
    HistoricalDomainService historicalDomainService;

    @Resource
    PaymentSearchService paymentSearchService;

    @Resource
    NicHandleSearchService nicHandleSearchService;

    @Resource
    AccountSearchService accountSearchService;

    @Resource
    TriplePassSupportService triplePassSupportService;

    @Resource
    DepositService depositService;

    @Mocked
    EmailTemplateSenderImpl emailSender;

    /**
     * fails, if the ticket with the given id is still present in the history
     * @param ticketId
     */
    public void assertTicketClosed(long ticketId) {
        try {
            Ticket t = ticketSearchService.getTicket(ticketId);
            AssertJUnit.fail("Ticket is not closed. Got ticket: " + info(t));
        } catch (TicketNotFoundException e) {
            // this was desired
            // check, if the last ticket in the history was fully passed
            List<HistoricalObject<Ticket>> hist = ticketHistorySearchService.getTicketHistory(ticketId);
            AssertJUnit.assertFalse(hist.isEmpty());
            Ticket t = hist.get(0).getObject();
            AssertJUnit.assertEquals(AdminStatus.PASSED, t.getAdminStatus());
            AssertJUnit.assertEquals(TechStatus.PASSED, t.getTechStatus());
            AssertJUnit.assertEquals(FinancialStatus.PASSED, t.getFinancialStatus());
        }
    }

    private String info(Ticket t) {
        return String.format("admin: %s, tech: %s, financial: %s", t.getAdminStatus().getDescription(), t
                .getTechStatus().getDescription(), t.getFinancialStatus().getDescription());
    }

    protected void expectInsufficientDepositNotificationWillBeSent(final EmailTemplateNamesEnum template,
            final int count) throws Exception {
        new NonStrictExpectations() {
            {
                emailSender.sendEmail(template.getId(), withInstanceOf(EmailParameters.class));
                times = count;
            }
        };

    }

    protected void updateStatuses(long ticketId, AdminStatus newAdminStatus, TechStatus newTechStatus,
            FinancialStatus newFinancialStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        ticketService.updateAdminStatus(ticketId, newAdminStatus, opInfo);
        ticketService.updateTechStatus(ticketId, newTechStatus, opInfo);
        ticketService.updateFinancialStatus(ticketId, newFinancialStatus, opInfo);
    }

}
