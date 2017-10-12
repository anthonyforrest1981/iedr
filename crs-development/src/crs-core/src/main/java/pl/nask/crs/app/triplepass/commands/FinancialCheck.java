package pl.nask.crs.app.triplepass.commands;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.ExtendedDeposit;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketService;

public abstract class FinancialCheck {

    private static final Logger LOG = Logger.getLogger(FinancialCheck.class);

    protected final Ticket ticket;

    protected final TicketService ticketService;

    protected final PaymentService paymentService;

    protected final PaymentSearchService paymentSearchService;

    protected final DepositService depositService;

    protected EmailTemplateSender emailTemplateSender;

    protected NicHandleSearchService nicHandleSearchService;

    protected DomainSearchService domainSearchService;

    protected FinancialCheck(Ticket ticket, TicketService ticketService, PaymentService paymentService,
            PaymentSearchService paymentSearchService, EmailTemplateSender emailTemplateSender,
            NicHandleSearchService nicHandleSearchService, DepositService depositService,
            DomainSearchService domainSearchService) {
        this.ticket = ticket;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.paymentSearchService = paymentSearchService;
        this.emailTemplateSender = emailTemplateSender;
        this.nicHandleSearchService = nicHandleSearchService;
        this.depositService = depositService;
        this.domainSearchService = domainSearchService;
    }

    protected FinancialCheck(Ticket ticket, TicketService ticketService, PaymentService paymentService,
            PaymentSearchService paymentSearchService, DepositService depositService) {
        this.ticket = ticket;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.paymentSearchService = paymentSearchService;
        this.depositService = depositService;
    }

    public abstract void performFinancialCheck(AuthenticatedUser user, OpInfo opInfo)
            throws FinancialCheckException, DomainIllegalStateException;

    protected void setReservationReadyAndTicketFinancialPassed(Ticket t, Reservation reservation, OpInfo opInfo)
            throws TicketNotFoundException {
        setReservationAndTransactionReady(reservation);
        setFinancialStatusPassed(t.getId(), opInfo);
    }

    protected void setFinancialStatusPassed(long id, OpInfo opInfo) throws TicketNotFoundException {
        LOG.info("Setting financial status to Passed, ticketId=" + id);
        ticketService.updateFinancialStatus(id, FinancialStatus.PASSED, opInfo);
    }

    protected void setFinancialStatusStalled(long ticketId, OpInfo opInfo) throws TicketNotFoundException {
        LOG.info("Setting financial status to Stalled, ticketId=" + ticketId);
        ticketService.updateFinancialStatus(ticketId, FinancialStatus.STALLED, opInfo);
    }

    protected void setReservationAndTransactionReady(Reservation reservation) {
        try {
            reservation.setReadyForSettlement(true);
            paymentService.updateReservation(reservation);
            paymentService.setTransactionFinanciallyPassed(reservation.getTransactionId());
        } catch (TransactionNotFoundException e) {
            throw new IllegalStateException("Transaction not found for reservation", e);
        }
    }

    protected ExtendedDeposit getOrInitDeposit(String billingNH) {
        try {
            return depositService.viewDeposit(billingNH);
        } catch (DepositNotFoundException e) {
            return depositService.initDeposit(billingNH);
        }
    }

    protected boolean hasRegistrarSufficientFunds(ExtendedDeposit extendedDeposit, BigDecimal amountWithVat) {
        return extendedDeposit.getCloseBalMinusReservations().compareTo(amountWithVat) == 0
                || extendedDeposit.getCloseBalMinusReservations().compareTo(amountWithVat) == 1;
    }

    protected boolean hasRegistrarSufficientFunds(String billingNH, BigDecimal amountWithVat) {
        try {
            ExtendedDeposit extendedDeposit = depositService.viewDeposit(billingNH);
            return hasRegistrarSufficientFunds(extendedDeposit, amountWithVat);
        } catch (DepositNotFoundException e) {
            return false;
        }
    }

    protected boolean isTransactionInvalidated(long transactionId) {
        try {
            Transaction transaction = paymentSearchService.getTransaction(transactionId);
            return transaction.isInvalidated();
        } catch (TransactionNotFoundException e) {
            LOG.error("Transaction not found, transactionId=" + transactionId);
            throw new IllegalStateException("Transaction not found, transactionId=" + transactionId);
        }
    }
}
