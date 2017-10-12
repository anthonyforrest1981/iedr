package pl.nask.crs.web.email;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class EmailTemplateAction extends AuthenticatedUserAwareAction {

    EmailTemplate template;
    int id;
    List<EmailGroup> groups;

    private String previousAction;

    EmailTemplateAppService service;
    EmailGroupAppService groupService;
    EmailDisablerAppService disablerAppService;

    public EmailTemplateAction(EmailTemplateAppService service, EmailGroupAppService groupService,
            EmailDisablerAppService disablerAppService) {
        this.service = service;
        this.groupService = groupService;
        this.disablerAppService = disablerAppService;
    }

    public String view() throws AccessDeniedException {
        template = service.get(getUser(), id);
        return SUCCESS;
    }

    public String input() throws AccessDeniedException {
        template = service.get(getUser(), id);

        return INPUT;
    }

    public String save() throws AccessDeniedException {
        if (id != template.getId()) {
            addActionError("Edited template id");
            Logger.getLogger(EmailTemplateAction.class).warn(
                    "Non-matching template ids in save, looks like someone is trying to hand-edit input data");
            return ERROR;
        }

        service.saveEditableFields(getUser(), template);
        return SUCCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmailTemplate getTemplate() {
        return template;
    }

    public void setTemplate(EmailTemplate template) {
        this.template = template;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public List<EmailGroup> getGroups() throws AccessDeniedException {
        if (groups == null) {
            groups = groupService.getAllGroups(getUser());
        }
        return groups;
    }

    public int getAffectedUsersCount() throws AccessDeniedException {
        List<EmailDisabler> emailDisablers = disablerAppService.getAllOfTemplate(getUser(), id);

        Set<String> affectedUsers = new HashSet<String>();
        for (EmailDisabler disabler : emailDisablers) {
            affectedUsers.add(disabler.getNicHandle());
        }
        return affectedUsers.size();
    }

}
