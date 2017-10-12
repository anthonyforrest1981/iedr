package pl.nask.crs.web.email;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.interceptor.validation.SkipValidation;

import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class EmailGroupAction extends AuthenticatedUserAwareAction {

    private EmailGroup group;
    private List<EmailDisabler> emailDisablers;

    private long id;

    private String previousAction;

    private EmailGroupAppService service;
    private EmailDisablerAppService emailDisablerAppService;

    public EmailGroupAction(EmailGroupAppService service, EmailDisablerAppService emailDisablerAppService) {
        this.service = service;
        this.emailDisablerAppService = emailDisablerAppService;
    }

    @SkipValidation
    public String view() throws AccessDeniedException {
        group = service.get(getUser(), id);
        return SUCCESS;
    }

    @SkipValidation
    public String input() throws AccessDeniedException {
        group = service.get(getUser(), id);

        emailDisablers = emailDisablerAppService.getAllOfEmailGroup(getUser(), id);

        return INPUT;
    }

    @SkipValidation
    public String newGroup() {
        group = new EmailGroup();
        return INPUT;
    }

    public String update() throws AccessDeniedException {
        service.update(getUser(), group);
        return SUCCESS;
    }

    public String create() throws AccessDeniedException {
        service.create(getUser(), group);
        id = group.getId();
        return SUCCESS;
    }

    @SkipValidation
    public String delete() throws AccessDeniedException {
        service.delete(getUser(), group);
        return SUCCESS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EmailGroup getGroup() {
        return group;
    }

    public void setGroup(EmailGroup group) {
        this.group = group;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public boolean actionWillBeDestructive() {
        return !emailDisablers.isEmpty();
    }

    public int getAffectedUsersCount() {
        Set<String> affectedUsers = new HashSet<String>();
        for (EmailDisabler disabler : emailDisablers) {
            affectedUsers.add(disabler.getNicHandle());
        }
        return affectedUsers.size();
    }

    public int getDisablingsCount() {
        return emailDisablers.size();
    }
}
