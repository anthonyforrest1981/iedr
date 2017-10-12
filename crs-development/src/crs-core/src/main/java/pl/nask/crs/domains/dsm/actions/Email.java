package pl.nask.crs.domains.dsm.actions;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

public class Email extends AbstractDsmAction {
    private static final Logger LOG = Logger.getLogger(Email.class);
    protected EmailTemplateSender templateSender;
    protected NicHandleDAO nicHandleDAO;
    protected int emailTemplateId;

    public Email(NicHandleDAO nicHandleDAO, EmailTemplateSender templateSender) {
        this.nicHandleDAO = nicHandleDAO;
        this.templateSender = templateSender;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event)
            throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException,
            EmailSendingException {
        EmailParameters params = getEmailParameters(user, domain, event);
        sendEmail(emailTemplateId, params);
    }

    private void sendEmail(int emailTemplateId, EmailParameters params) {
        try {
            templateSender.sendEmail(emailTemplateId, params);
        } catch (IllegalArgumentException e) {
            logError(String.format("Error building email (template id=%s): ", emailTemplateId), e);
        } catch (TemplateNotFoundException e) {
            logError(String.format("Cannot find template (id=%s)", emailTemplateId), e);
        } catch (TemplateInstantiatingException e) {
            logError(String.format("Cannot build template (id=%s) with given parameters", emailTemplateId), e);
        } catch (EmailSendingException e) {
            logError(String.format("Error sending email, template (id=%s)", emailTemplateId), e);
        }
    }

    private void logError(String detailedMsg, Exception e) {
        LOG.error("Error sending email with DSM action: " + this + ", " + detailedMsg, e);
    }

    private EmailParameters getEmailParameters(AuthenticatedUser user, Domain domain, DsmEvent event) {
        DomainEmailParameters domainEmailParameters = new DomainEmailParameters(user.getUsername(), nicHandleDAO,
                domain);

        if (event.hasParameter(DsmEvent.OLD_BILL_C)) {
            domainEmailParameters.setLosingBillingContact((pl.nask.crs.contacts.Contact) event
                    .getParameter(DsmEvent.OLD_BILL_C));
        }
        if (event.hasParameter(DsmEvent.NEW_BILL_C)) {
            domainEmailParameters.setGainingBillingContact((pl.nask.crs.contacts.Contact) event
                    .getParameter(DsmEvent.NEW_BILL_C));
        }
        if (event.hasParameter(DsmEvent.TRANSACTION_DETAIL)) {
            domainEmailParameters.setParameter(ParameterNameEnum.TRANSACTION_DETAIL,
                    event.getParameter(DsmEvent.TRANSACTION_DETAIL));
        }
        if (event.hasParameter(DsmEvent.TRANSACTION_VALUE)) {
            domainEmailParameters.setParameter(ParameterNameEnum.TRANSACTION_VALUE,
                    MoneyUtils.getRoundedAndScaledValue((BigDecimal) event.getParameter(DsmEvent.TRANSACTION_VALUE), 2));
        }
        if (event.hasParameter(DsmEvent.ORDER_ID)) {
            domainEmailParameters.setParameter(ParameterNameEnum.ORDER_ID, event.getParameter(DsmEvent.ORDER_ID));
        }
        if (event.hasParameter(DsmEvent.TICKET)) {
            domainEmailParameters.setParameter(ParameterNameEnum.TICKET_ID,
                    ((Ticket) event.getParameter(DsmEvent.TICKET)).getId());
        }
        if (event.hasParameter(DsmEvent.BUY_REQUEST)) {
            domainEmailParameters.setBuyRequest((BuyRequest) event.getParameter(DsmEvent.BUY_REQUEST));
        }
        if (event.hasParameter(DsmEvent.SELL_REQUEST)) {
            domainEmailParameters.setSellRequest((SellRequest) event.getParameter(DsmEvent.SELL_REQUEST));
        }
        if (event.hasParameter(DsmEvent.COUNTDOWN_PERIOD)) {
            domainEmailParameters.setParameter(ParameterNameEnum.COUNTDOWN_PERIOD,
                    event.getParameter(DsmEvent.COUNTDOWN_PERIOD));
        }
        return domainEmailParameters;
    }

    @Override
    public void parseAndSetActionParameters(String actionParam) {
        super.parseAndSetActionParameters(actionParam);
        parseAndSetEmailActionParameters(actionParam);
    }

    protected void parseAndSetEmailActionParameters(String actionParam) {
        emailTemplateId = Integer.parseInt(actionParam);
    }

}
