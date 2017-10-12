package pl.nask.crs.scheduler.jobs;

import org.apache.log4j.Logger;

import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

public class TicketAndTransactionCleanupJob extends AbstractJob {

    private final static Logger LOG = Logger.getLogger(TicketAndTransactionCleanupJob.class);

    private final CommonAppService commonAppService;
    private final PaymentAppService paymentAppService;
    private final TicketAppService ticketAppService;
    private final SecondaryMarketAppService secondaryMarketAppService;

    public TicketAndTransactionCleanupJob(CommonAppService commonAppService, PaymentAppService paymentAppService,
            TicketAppService ticketAppService, SecondaryMarketAppService secondaryMarketAppService) {
        this.commonAppService = commonAppService;
        this.paymentAppService = paymentAppService;
        this.ticketAppService = ticketAppService;
        this.secondaryMarketAppService = secondaryMarketAppService;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        LOG.info("Ticket cleanup started.");
        OpInfo opInfo = new OpInfo(user);
        for (Ticket ticket : ticketAppService.findTicketsToCleanup(user)) {
            cleanupTicket(user, opInfo, ticket);
        }
        for (Transaction t : paymentAppService.findTransactionsToCleanup(user)) {
            cleanupTransaction(user, t);
        }
        for (BuyRequest buyRequest : secondaryMarketAppService.findBuyRequestsToCleanup(user)) {
            cleanupBuyRequest(user, buyRequest, opInfo);
        }
        LOG.info("Ticket cleanup finished");
    }

    private void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, Ticket ticket) {
        try {
            commonAppService.cleanupTicket(user, opInfo, ticket.getId());
        } catch (Exception e) {
            LOG.error("Exception during cleanup of ticket id: " + ticket.getId(), e);
        }
    }

    private void cleanupTransaction(AuthenticatedUser user, Transaction transaction) {
        try {
            paymentAppService.cleanupTransaction(user, transaction);
        } catch (Exception e) {
            LOG.error("Exception during cleanup of transaction " + transaction.getOrderId(), e);
        }
    }

    private void cleanupBuyRequest(AuthenticatedUser user, BuyRequest buyRequest, OpInfo opInfo) {
        try {
            secondaryMarketAppService.cleanupBuyRequest(user, buyRequest.getId(), opInfo);
        } catch (Exception e) {
            LOG.error("Exception during cleanup of buy request " + buyRequest.getId(), e);
        }
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
