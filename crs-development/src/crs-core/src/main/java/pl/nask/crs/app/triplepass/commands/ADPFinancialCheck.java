package pl.nask.crs.app.triplepass.commands;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.app.triplepass.email.FinancialCheckEmailParameters;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.ReservationNotFoundException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketService;

public class ADPFinancialCheck extends FinancialCheck {
    private final static Logger LOG = Logger.getLogger(ADPFinancialCheck.class);

    protected Long reservationId;

    public ADPFinancialCheck(Ticket ticket, Long reservationId, TicketService ticketService,
            PaymentService paymentService, PaymentSearchService paymentSearchService,
            EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService,
            DepositService depositService, DomainSearchService domainSearchService) {
        super(ticket, ticketService, paymentService, paymentSearchService, emailTemplateSender, nicHandleSearchService,
                depositService, domainSearchService);
        this.reservationId = reservationId;
    }

    @Override
    public void performFinancialCheck(AuthenticatedUser user, OpInfo opInfo) throws FinancialCheckException {
        try {
            String billingNH = ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
            String domainName = ticket.getOperation().getDomainNameField().getNewValue();
            boolean emailNotificationAlreadySent = (ticket.getFinancialStatus() == FinancialStatus.STALLED);

            if (reservationId == null) {
                reservationId = paymentService.authorizeADPPaymentForTicket(billingNH, domainName, ticket.getDomainPeriod(),
                        getOperationType(), ticket.getId());
            }
            Reservation reservation = paymentService.lockForUpdate(reservationId);
            ExtendedDeposit extendedDeposit = getOrInitDeposit(billingNH);
            Transaction transaction = paymentSearchService.getTransaction(reservation.getTransactionId());

            if (transaction.isInvalidated()) {
                setFinancialStatusStalled(ticket.getId(), opInfo);
            } else if (hasRegistrarSufficientFunds(extendedDeposit, reservation.getTotal())) {
                setReservationReadyAndTicketFinancialPassed(ticket, reservation, opInfo);
                sendConfirmationEmail(user, billingNH, transaction.getOrderId(), domainName, reservation.getTotal());
            } else {
                setFinancialStatusStalled(ticket.getId(), opInfo);
                if (!emailNotificationAlreadySent) {
                    sendNotificationEmail(user, extendedDeposit, domainName, reservation.getTotal());
                }
            }
        } catch (NotAdmissiblePeriodException e) {
            throw new FinancialCheckException(e);
        } catch (TicketNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (NicHandleNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (ReservationNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (TransactionNotFoundException e) {
            throw new FinancialCheckException(e);
        }
    }

    private void sendNotificationEmail(AuthenticatedUser user, Deposit deposit, String domainName, BigDecimal value) {
        try {
            DomainOperation.DomainOperationType operationType = ticket.getOperation().getType();
            NicHandle billingNH = nicHandleSearchService.getNicHandle(deposit.getNicHandleId());
            int years = ticket.getDomainPeriod().getYears();
            String domainHolder = ticket.getOperation().getDomainHolderField().getNewValue();
            TransactionDetails details;
            FinancialCheckEmailParameters params;
            switch (operationType) {
                case REG:
                    details = new TransactionDetails(domainName, domainHolder, years, OperationType.REGISTRATION, value);
                    params = new FinancialCheckEmailParameters(user.getUsername(), billingNH, domainName, operationType,
                            null, details, value);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_NREG.getId(),
                            params);
                    break;
                case XFER:
                    Domain d = domainSearchService.getDomain(domainName);
                    details = new TransactionDetails(domainName, domainHolder, years, OperationType.TRANSFER,
                            d.getRegistrationDate(), d.getRenewalDate(), value);
                    params = new FinancialCheckEmailParameters(user.getUsername(), billingNH, domainName, operationType,
                            null, details, value);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER.getId(),
                            params);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation type: " + operationType);
            }
        } catch (Exception e) {
            LOG.warn("Problem with email during financial check occured ", e);
        }
    }

    private void sendConfirmationEmail(AuthenticatedUser user, String billingNHName, String orderId, String domainName,
            BigDecimal value) {
        try {
            DomainOperation.DomainOperationType operationType = ticket.getOperation().getType();
            if (operationType == DomainOperation.DomainOperationType.REG) {
                NicHandle billingNH = nicHandleSearchService.getNicHandle(billingNHName);
                int years = ticket.getDomainPeriod().getYears();
                String domainHolder = ticket.getOperation().getDomainHolderField().getNewValue();
                TransactionDetails details = new TransactionDetails(domainName, domainHolder, years,
                        OperationType.REGISTRATION, value);
                FinancialCheckEmailParameters params = new FinancialCheckEmailParameters(user.getUsername(), billingNH,
                        domainName, operationType, orderId, details, value);
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_ADP_MONEY_RESEVED.getId(), params);
            }
        } catch (Exception e) {
            LOG.warn("Problem with email during financial check occured ", e);
        }
    }

    private OperationType getOperationType() {
        switch (ticket.getOperation().getType()) {
            case REG:
                return OperationType.REGISTRATION;
            case XFER:
                return OperationType.TRANSFER;
            default:
                throw new IllegalStateException("Illegal ticket type: " + ticket.getOperation().getType());
        }
    }

}
