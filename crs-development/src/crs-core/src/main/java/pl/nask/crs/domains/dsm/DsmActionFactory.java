package pl.nask.crs.domains.dsm;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.dsm.actions.*;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

public class DsmActionFactory {
    private ApplicationConfig appConfig;
    private EmailTemplateSender emailTemplateSender;
    private ContactDAO contactDAO;
    private NicHandleDAO nicHandleDAO;

    public DsmActionFactory(ApplicationConfig appConfig, EmailTemplateSender emailTemplateSender,
            ContactDAO contactDAO, NicHandleDAO nicHandleDAO) {
        this.appConfig = appConfig;
        this.emailTemplateSender = emailTemplateSender;
        this.contactDAO = contactDAO;
        this.nicHandleDAO = nicHandleDAO;
    }

    public DsmAction actionFor(InternalDsmAction internalAction) {
        DsmAction action = getActionForName(internalAction.getActionName());
        action.parseAndSetActionParameters(internalAction.getActionParam());
        return action;
    }

    private DsmAction getActionForName(String actionName) {
        switch (actionName) {
            case "SetRenewalDate":
                return new SetRenewalDate();
            case "SetSuspensionDateCurrent":
                return new SetSuspensionDateCurrent(appConfig);
            case "SetDeletionDate":
                return new SetDeletionDate(appConfig);
            case "ClearDeletionDate":
                return new ClearDeletionDate();
            case "ClearSuspensionDate":
                return new ClearSuspensionDate();
            case "SetSuspensionDateRenewal":
                return new SetSuspensionDateRenewal(appConfig);
            case "RollRenewalDate":
                return new RollRenewalDate();
            case "RollRenewalDateOne":
                return new RollRenewalDate(1);
            case "Email":
                return new Email(nicHandleDAO, emailTemplateSender);
            case "EmailCond":
                return new EmailCond(nicHandleDAO, emailTemplateSender);
            case "ClearAuthCode":
                return new ClearAuthCode();
            case "ApplySecondaryMarketChanges":
                return new ApplySecondaryMarketChanges(contactDAO);
            case "ApplySecondaryMarketChangesForDirect":
                return new ApplySecondaryMarketChangesForDirect(contactDAO);
            default:
                return null;
        }
    }

}
